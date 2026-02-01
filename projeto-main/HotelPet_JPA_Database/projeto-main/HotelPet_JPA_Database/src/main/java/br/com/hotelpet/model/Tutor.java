package br.com.hotelpet.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_tutor")
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String cpf;
    private String telefone;

    // JPA: Um tutor tem muitos pets
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.REMOVE)
    private List<Animal> pets = new ArrayList<>();

    public Tutor() {}
    public Tutor(String nome, String cpf, String telefone) {
        this.nome = nome; this.cpf = cpf; this.telefone = telefone;
    }

    public void adicionarPet(Animal animal) {
        animal.setTutor(this); // Vincula o pet ao tutor
        this.pets.add(animal);
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Animal> getPets() {
        return pets;
    }

    public void setPets(List<Animal> pets) {
        this.pets = pets;
    }
}