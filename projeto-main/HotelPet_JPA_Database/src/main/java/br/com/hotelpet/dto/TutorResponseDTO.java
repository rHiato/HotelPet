package br.com.hotelpet.dto;
import br.com.hotelpet.model.Tutor;
public record TutorResponseDTO(
        Integer id,
        String nome,
        String cpf,
        String telefone,
        int quantidadePets) {
    public TutorResponseDTO(Tutor tutor) {
        this(tutor.getId(),
                tutor.getNome(),
                tutor.getCpf(),
                tutor.getTelefone(),
                tutor.getPets() != null ? tutor.getPets().size() : 0);
    }
}