package br.com.hotelpet.model;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CACHORRO")
public class Cachorro extends Animal {
    private String raca;
    private boolean sociavel;

    public String getRaca() {
        return raca; }
    public void setRaca(String raca) {
        this.raca = raca; }

    public boolean isSociavel() {
        return sociavel;
    }

    public void setSociavel(boolean sociavel) {
        this.sociavel = sociavel;
    }
}