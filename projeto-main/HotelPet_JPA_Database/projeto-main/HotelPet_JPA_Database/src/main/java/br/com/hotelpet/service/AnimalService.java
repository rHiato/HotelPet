package br.com.hotelpet.service;

import br.com.hotelpet.dto.PetRequestDTO;
import br.com.hotelpet.dto.PetResponseDTO;
import br.com.hotelpet.infra.exception.EntidadeNaoEncontradaException;
import br.com.hotelpet.infra.exception.ValidacaoException;
import br.com.hotelpet.model.Animal;
import br.com.hotelpet.model.Cachorro;
import br.com.hotelpet.model.Gato;
import br.com.hotelpet.model.Tutor;
import br.com.hotelpet.repository.AnimalRepository;
import br.com.hotelpet.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AnimalService {
  @Autowired
  private  AnimalRepository animalRepository;
  @Autowired
  private  TutorRepository tutorRepository;

    public PetResponseDTO adicionarPet(PetRequestDTO petsDados) {
        Tutor tutor = tutorRepository.findById(petsDados.tutorId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tutor não encontrado"));

        Animal animal;
        if ("CACHORRO".equalsIgnoreCase(petsDados.tipo())) {
            Cachorro c = new Cachorro();
            c.setRaca(petsDados.raca());
            c.setSociavel(petsDados.sociavel() != null ? petsDados.sociavel() : false);
            animal = c;
        } else if ("GATO".equalsIgnoreCase(petsDados.tipo())) {
            Gato g = new Gato();
            g.setUsaCaixaDeAreia(petsDados.usaCaixaDeAreia() != null ? petsDados.usaCaixaDeAreia() : false);
            animal = g;
        } else {
            throw new ValidacaoException("Animal inválido");
        }

        animal.setNome(petsDados.nome());
        animal.setPeso(petsDados.peso());
        animal.setIdade(petsDados.idade());
        animal.setTutor(tutor);

        animalRepository.save(animal);
        return new PetResponseDTO(animal);

    }

    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
    }
    public List<PetResponseDTO> listaTodos(){
        return animalRepository.findAll().stream()
                .map(PetResponseDTO::new)
                .collect(Collectors.toList());
    }

    public PetResponseDTO buscarPorIdOuFalhar(Integer id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pet de id:" + id + " não encontrado"));
        return new PetResponseDTO(animal);
    }

    public void deletarPet(Integer id){
        if (!animalRepository.existsById(id)){
         throw new RuntimeException("Pet inexistente");
        }
        else animalRepository.deleteById(id);
    }

    public PetResponseDTO atualizarPet(Integer id, PetRequestDTO petsDados){
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new  EntidadeNaoEncontradaException("Pet não encontrado"));
        animal.setNome(petsDados.nome());
        animal.setPeso(petsDados.peso());
        animal.setIdade(petsDados.idade());
        if (animal instanceof Cachorro) {
            if (petsDados.sociavel() != null) {
                ((Cachorro) animal).setSociavel(petsDados.sociavel());
            }
        } else if (animal instanceof Gato) {
            if (petsDados.usaCaixaDeAreia() != null) {
                ((Gato) animal).setUsaCaixaDeAreia(petsDados.usaCaixaDeAreia());
            }
        }
        animalRepository.save(animal);
        return new PetResponseDTO(animal);
    }

}

