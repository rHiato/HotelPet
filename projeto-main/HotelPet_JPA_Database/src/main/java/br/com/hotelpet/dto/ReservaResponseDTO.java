package br.com.hotelpet.dto;

import java.time.LocalDate;

public record ReservaResponseDTO(
        Integer idReserva,
        String nomeTutor,
        String nomePet,
        String acomodacao,
        LocalDate dataDeEntrada,
        LocalDate DataDeSaida,
        Long totalDias,
        double valorTotal,
        String mensagem
) {
}