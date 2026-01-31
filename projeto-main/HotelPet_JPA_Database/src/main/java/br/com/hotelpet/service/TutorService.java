package br.com.hotelpet.service;
import br.com.hotelpet.dto.*;
import br.com.hotelpet.model.*;
import br.com.hotelpet.repository.AnimalRepository;
import br.com.hotelpet.repository.TutorRepository;
import br.com.hotelpet.infra.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import br.com.hotelpet.model.Gato;
@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AnimalRepository animalRepository;


    public TutorResponseDTO cadastrar(TutorRequestDTO dados) {
        Tutor tutor = new Tutor(dados.nome(), dados.cpf(), dados.telefone());
        tutorRepository.save(tutor);
        return new TutorResponseDTO(tutor);
    }

    public List<TutorResponseDTO> listar() {
        return tutorRepository.findAll().stream()
                .map(TutorResponseDTO::new)
                .collect(Collectors.toList());
    }

    public TutorResponseDTO buscarPorIdOuFalhar(Integer id) {
        Tutor tutor = tutorRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Tutor de" + id + "não encontrado"));
        return new TutorResponseDTO(tutor);
    }


    public void deletar(Integer id){
        if (!tutorRepository.existsById(id)){
            throw new RuntimeException("Tutor não encontrado");
        }
        else tutorRepository.deleteById(id);
    }

    public TutorResponseDTO atualizarTutor (Integer id, TutorRequestDTO tutorDados) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tutor não encontrado"));
        tutor.setNome(tutorDados.nome());
        tutor.setCpf(tutorDados.cpf());
        tutor.setTelefone(tutorDados.telefone());
        tutorRepository.save(tutor);
        return new TutorResponseDTO(tutor);
    }
}
