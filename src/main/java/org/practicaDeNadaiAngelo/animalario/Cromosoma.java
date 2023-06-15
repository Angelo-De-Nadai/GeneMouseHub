package org.practicaDeNadaiAngelo.animalario;

import java.io.Serializable;

/**
 * representa el objeto de un Cromosoma de un Ratón con atributos (Tipo de Cromosoma y mutación),
 * la clase permite leer desde el exterior para todos los atributos, y mientras que la escritura
 * no es posible para el atributo Tipo de Cromosoma
 *
 * @author Angelo De Nadai
 */
public class Cromosoma implements Serializable {
    private TipoCromosoma tipo;
    private Boolean mutado;

    //constructor
    public Cromosoma(TipoCromosoma tipo, Boolean mutado) {
        this.tipo = tipo;
        this.mutado = mutado;
    }

    //constructor por defecto
    public Cromosoma() {
        this.tipo = TipoCromosoma.X;
        this.mutado = false;
    }

    public TipoCromosoma getTipo() {
        return tipo;
    }

    public Boolean getMutado() {
        return mutado;
    }

    public void setMutado(Boolean mutado) {
        this.mutado = mutado;
    }

    @Override
    public String toString() {
        return "Cromosoma{" +
                "tipo=" + tipo +
                ", mutado=" + mutado +
                '}';
    }
}
