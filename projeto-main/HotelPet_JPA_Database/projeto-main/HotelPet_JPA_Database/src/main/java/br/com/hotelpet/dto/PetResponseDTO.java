package br.com.hotelpet.dto;

import br.com.hotelpet.model.Animal;
import br.com.hotelpet.model.Cachorro;
import br.com.hotelpet.model.Gato;

public record PetResponseDTO(
        Integer id,
        String nome,
        String tipo,
        String raca,
        double peso,
        int idade,
        Boolean sociavel,
        Boolean usaCaixaDeAreia,
        String nomeTutor){
    public PetResponseDTO {
    }


    public PetResponseDTO(Animal animal){
        this(
                animal.getId(),
                animal.getNome(),
                (animal instanceof Cachorro) ? "Cachorro" : "Gato",
                (animal instanceof Cachorro) ? ((Cachorro) animal).getRaca() : "N/A", // Se for gato, mostra N/A
                animal.getPeso(),
                animal.getIdade(),
                (animal instanceof Cachorro) ? ((Cachorro) animal) .isSociavel() : null,
                (animal instanceof Gato) ? ((Gato) animal) .isCaixaAreia(): null,

                animal.getTutor().getNome()
        );
    }
}
