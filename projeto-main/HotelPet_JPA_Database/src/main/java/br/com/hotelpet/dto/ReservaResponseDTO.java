package br.com.hotelpet.dto;

import br.com.hotelpet.model.Reserva;

public record ReservaResponseDTO(
        Integer idReserva,
        String nomeTutor,
        String nomePet,
        String acomodacao,
        int dias,
        double valorTotal,
        String mensagem
) {
}