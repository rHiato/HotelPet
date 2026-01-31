package br.com.hotelpet.model;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GATO")
public class Gato extends Animal {
    private boolean usaCaixaDeAreia;

    public boolean isCaixaAreia() {
        return usaCaixaDeAreia;
    }

    public void setUsaCaixaDeAreia(Boolean usaCaixaDeAreia) {
        this.usaCaixaDeAreia = usaCaixaDeAreia;
    }
}