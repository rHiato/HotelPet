package br.com.hotelpet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record ReservaRequestDTO(
        @JsonProperty("tutor-Id") Integer tutorId, // Quem está pagando
        @JsonProperty("pet-Id") Integer petId,     // Quem vai ficar hospedado
        LocalDate dataDeEntrada,
        LocalDate dataDeSaida,
        String tipoAcomodacao
) {

}