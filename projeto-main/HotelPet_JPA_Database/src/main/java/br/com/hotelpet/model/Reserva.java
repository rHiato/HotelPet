package br.com.hotelpet.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_Reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Tutor_ID")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "Pet_ID")
    private Animal animal;

    private String tipoDeAcomodacao;
    private int dias;
    private double valorTotal;
    private LocalDate dataDaReserva = LocalDate.now();

    public Reserva(Tutor tutor, Animal animal, String s, int dias, double valorTotal) {
    }

    public Reserva(Integer id, Tutor tutor, Animal animal, String tipoDeAcomodacao, int dias, double valorTotal) {
        this.id = id;
        this.tutor = tutor;
        this.animal = animal;
        this.tipoDeAcomodacao = tipoDeAcomodacao;
        this.dias = dias;
        this.valorTotal = valorTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getTipoDeAcomodacao() {
        return tipoDeAcomodacao;
    }

    public void setTipoDeAcomodacao(String tipoDeAcomodacao) {
        this.tipoDeAcomodacao = tipoDeAcomodacao;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataDaReserva() {
        return dataDaReserva;
    }

    public void setDataDaReserva(LocalDate dataDaReserva) {
        this.dataDaReserva = dataDaReserva;
    }
}
