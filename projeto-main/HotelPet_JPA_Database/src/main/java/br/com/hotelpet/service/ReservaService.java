package br.com.hotelpet.service;
import br.com.hotelpet.dto.*;
import br.com.hotelpet.infra.exception.EntidadeNaoEncontradaException;
import br.com.hotelpet.model.*;
import br.com.hotelpet.infra.exception.ValidacaoException;
import br.com.hotelpet.repository.AnimalRepository;
import br.com.hotelpet.repository.ReservaRepository;
import br.com.hotelpet.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {
  @Autowired
    private TutorRepository tutorRepository;
  @Autowired
    private ReservaRepository reservaRepository;
  @Autowired
    private AnimalRepository animalRepository;

  public ReservaResponseDTO criarReserva(ReservaRequestDTO reservaDados){
      Tutor tutor = tutorRepository.findById(reservaDados.tutorId())
              .orElseThrow(()-> new  EntidadeNaoEncontradaException("Tutor não encontrado"));
      Animal animal = animalRepository.findById(reservaDados.petId())
              .orElseThrow(()-> new EntidadeNaoEncontradaException("Pet não encontrado"));
      if (!animal.getTutor().getId().equals(tutor.getId())) {
              throw new ValidacaoException("Este pet não pertence ao tutor informado!");
           }
      Acomodacao acomodacao;
      if ("SUITE_LUXO".equalsIgnoreCase(reservaDados.tipoAcomodacao())) {
          acomodacao = new SuiteLuxo();
      } else if ("CANIL_STANDARD".equalsIgnoreCase(reservaDados.tipoAcomodacao())) {
          acomodacao = new CanilStandard();
      } else {
          throw new ValidacaoException("Acomodação inválida");
      }
      double valorTotal = acomodacao.calcularDiaria() * reservaDados.dias();
      Reserva reserva = new Reserva(tutor,animal,reservaDados.tipoAcomodacao(),reservaDados.dias(),valorTotal);
      reservaRepository.save(reserva);
      return new ReservaResponseDTO(
              reserva.getId(),
              tutor.getNome(),
              animal.getNome(),
              reservaDados.tipoAcomodacao(),
              reservaDados.dias(),
              valorTotal,
              "Reserva realizada com sucesso!"

      );
  }

  public List<ReservaResponseDTO> listarReservas(){
      return reservaRepository.findAll().stream()
              .map(ReservaResponseDTO::new)
              .collect(Collectors.toList());
  }

}