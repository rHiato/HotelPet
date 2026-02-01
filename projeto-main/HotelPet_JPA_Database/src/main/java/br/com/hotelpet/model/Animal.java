package br.com.hotelpet.model;
import jakarta.persistence.*;
import java.time.LocalDate;

// JPA: Mapeamento de Herança (Tabela Única para simplificar)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_animal")
public abstract class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private int idade;
    private double peso;

    // JPA: Relacionamento Muitos-para-Um
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    public Animal() {
    }

    public Animal(Integer id, String nome, int idade, double peso, Tutor tutor) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.tutor = tutor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public Tutor getTutor() {
        return tutor;
    }
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

}