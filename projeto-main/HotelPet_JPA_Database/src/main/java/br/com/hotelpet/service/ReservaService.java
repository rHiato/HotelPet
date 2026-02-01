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

import java.time.temporal.ChronoUnit;
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

    public ReservaResponseDTO criarReserva(ReservaRequestDTO reservaDados) {
        Tutor tutor = tutorRepository.findById(reservaDados.tutorId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tutor não encontrado"));
        Animal animal = animalRepository.findById(reservaDados.petId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Pet não encontrado"));
        if (!animal.getTutor().getId().equals(tutor.getId())) {
            throw new ValidacaoException("Este pet não pertence ao tutor informado!");
        }
        if (reservaDados.dataDeSaida().isBefore(reservaDados.dataDeEntrada())) {
            throw new ValidacaoException("A data de saída não pode ser menor que a data de entrada!");
        }
        long dias = ChronoUnit.DAYS.between(reservaDados.dataDeEntrada(), reservaDados.dataDeSaida());
        Acomodacao acomodacao;

        //Caso entre e saia no mesmo dia será cobrado uma diaria.
        if (dias == 0) dias = 1;

        if ("SUITE_LUXO".equalsIgnoreCase(reservaDados.tipoAcomodacao())) {
            acomodacao = new SuiteLuxo();
        } else if ("CANIL_STANDARD".equalsIgnoreCase(reservaDados.tipoAcomodacao())) {
            acomodacao = new CanilStandard();
        } else {
            throw new ValidacaoException("Acomodação inválida");
        }
        double valorTotal = acomodacao.calcularDiaria() * dias;
        Reserva reserva = new Reserva(tutor, animal, reservaDados.tipoAcomodacao(), reservaDados.dataDeEntrada(), reservaDados.dataDeSaida(), valorTotal);
        reservaRepository.save(reserva);
        return new ReservaResponseDTO(
                reserva.getId(),
                tutor.getNome(),
                animal.getNome(),
                reservaDados.tipoAcomodacao(),
                reservaDados.dataDeEntrada(),
                reservaDados.dataDeSaida(),
                dias,
                valorTotal,
                "Reserva realizada com sucesso!"

        );
    }

    public List<ReservaResponseDTO> listarReservas() {
        return reservaRepository.findAll().stream()
                .map(reserva -> {
                    long dias = ChronoUnit.DAYS.between(reserva.getDataDeEntrada(), reserva.getDataDeSaida());
                    if (dias == 0) dias = 1;
                    return new ReservaResponseDTO(

                            reserva.getId(),
                            reserva.getTutor().getNome(),
                            reserva.getAnimal().getNome(),
                            reserva.getTipoDeAcomodacao(),
                            reserva.getDataDeEntrada(),
                            reserva.getDataDeSaida(),
                            dias,
                            reserva.getValorTotal(),
                            "Listado com sucesso"
                    );
                })
                .collect(Collectors.toList());
    }

    public ReservaResponseDTO buscarPorIdOuFalhar(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada"));
        long dias = ChronoUnit.DAYS.between(reserva.getDataDeEntrada(), reserva.getDataDeSaida());
        if (dias == 0) dias = 1;
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getTutor().getNome(),
                reserva.getAnimal().getNome(),
                reserva.getTipoDeAcomodacao(),
                reserva.getDataDeEntrada(),
                reserva.getDataDeSaida(),
                dias,
                reserva.getValorTotal(),
                "Encontrado"
        );
    }

    public void deletarReserva(Integer id) {
        if (!reservaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Reserva não encontrada");
        }
        reservaRepository.deleteById(id);
    }

    public ReservaResponseDTO atualizarReserva(Integer id, ReservaRequestDTO reservaDados) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada"));

        //Atualiza Tutor
        if (reservaDados.tutorId() != null) {
            Tutor novoTutor = tutorRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Tutor não encontrado"));
            reserva.setTutor(novoTutor);
        }

        //Atualiza Pet
        if (reservaDados.petId() != null) {
            Animal novoPet = animalRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Pet não encontrado"));
            reserva.setAnimal(novoPet);
        }

        boolean precisaRecalcular = false;

        //verifica se atualizou a acomodação
        if (reservaDados.tipoAcomodacao() != null && !reservaDados.tipoAcomodacao().equals(reserva.getTipoDeAcomodacao())) {
            reserva.setTipoDeAcomodacao(reservaDados.tipoAcomodacao());
            precisaRecalcular = true;
        }
        // verifica se atualizou a data de entrada
        if (reservaDados.dataDeEntrada() != null && !reservaDados.dataDeEntrada().equals(reserva.getDataDeEntrada())) {
            reserva.setDataDeEntrada(reservaDados.dataDeEntrada());
            precisaRecalcular = true;
        }
        //Verifice se atualizou a data de saida
        if (reservaDados.dataDeSaida() != null && !reservaDados.dataDeSaida().equals(reserva.getDataDeSaida())) {
            reserva.setDataDaReserva(reservaDados.dataDeSaida());
            precisaRecalcular = true;
        }
        if (precisaRecalcular) {
            if (reserva.getDataDeSaida().isBefore(reserva.getDataDeEntrada())) {
                throw new ValidacaoException("A nova data de saída não pode ser menor que a entrada!");
            }
            long dias = ChronoUnit.DAYS.between(reserva.getDataDeEntrada(), reserva.getDataDeSaida());
            if (dias == 0) dias = 1;
            Acomodacao acomodacao;
            if ("SUITE_LUXO".equalsIgnoreCase(reserva.getTipoDeAcomodacao())) {
                acomodacao = new SuiteLuxo();
            } else {
                acomodacao = new CanilStandard();
            }

            double novoValor = acomodacao.calcularDiaria() * dias;
            reserva.setValorTotal(novoValor);
        }
        reservaRepository.save(reserva);

        // Calcula dias finais apenas para exibição no DTO
        long diasFinais = ChronoUnit.DAYS.between(reserva.getDataDeEntrada(), reserva.getDataDeSaida());
        if (diasFinais == 0) diasFinais = 1;

        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getTutor().getNome(),
                reserva.getAnimal().getNome(),
                reserva.getTipoDeAcomodacao(),
                reserva.getDataDeEntrada(),
                reserva.getDataDeSaida(),
                diasFinais,
                reserva.getValorTotal(),
                "Reserva atualizada e valor recalculado!");
    }
}