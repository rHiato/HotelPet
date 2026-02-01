package br.com.hotelpet.dto;
import br.com.hotelpet.model.Animal;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PetRequestDTO(
        String tipo,
        String nome,
        @JsonProperty("tutor-Id") Integer tutorId,
        String raca,
        double peso,
        int idade,
        Boolean sociavel,
        Boolean usaCaixaDeAreia)
{
}