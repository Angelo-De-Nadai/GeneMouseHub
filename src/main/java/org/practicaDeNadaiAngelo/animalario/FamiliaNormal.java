package org.practicaDeNadaiAngelo.animalario;

import org.practicaDeNadaiAngelo.excepciones.NegativeInputExeption;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Familia de ratones compuesta por un padre, una madre y un grupo de crías.
 *
 * @author Angelo De Nadai
 */
public class FamiliaNormal extends Familia {
    private Raton madre;

    public FamiliaNormal(Raton padre, Raton madre) {
        super(padre);
        this.madre = madre;
    }

    public Raton getMadre() {
        return madre;
    }
    /**
     * Añade madre a la familia(solo por cargar madre desde un fichero
     * @param madre
     */
    public void setMadre(Raton madre) {
        this.madre = madre;
    }

    /**
     * Añade una madre a la familia.
     * @param madre
     */
    @Override
    public void anadirMadre(Raton madre) {
        this.madre = madre;
    }

    /**
     * Genera hijos causal(ratones) a partir del padre ratón y la madre ratón.
     *
     * @param fechaProcreacion
     * @throws NegativeInputExeption
     */
    @Override
    public Collection<Raton> procrear(LocalDate fechaProcreacion) throws NegativeInputExeption {
        if (madre.esEsteril() || getPadre().esEsteril()) {
            return null;
        }
        Collection<Raton> hijosCamada = new LinkedList<>();
        int random = new Random().nextInt(100);
        int crias = 0;
        if (random < 5) {
            crias = 2;
        } else if (random < 15) {
            crias = 3;
        }else if (random < 30) {
            crias = 4;
        }else if (random < 50) {
            crias = 5;
        }else if (random < 70) {
            crias = 6;
        }else if (random < 85) {
            crias = 7;
        }else if (random < 90) {
            crias = 8;
        }else {
            crias = 9;
        }
        while (crias-- != 0) {
            Raton hijo = generaHijo(madre,fechaProcreacion);
            getHijos().add(hijo);
            hijosCamada.add(hijo);
        }
        return hijosCamada;
    }

    @Override
    public Collection<Raton> procrear(LocalDate fechaProcreacion, Raton padre) throws NegativeInputExeption {
        return new LinkedList<Raton>();
    }

    @Override
    public String toString() {
        String h = "";
        for(Raton raton : getHijos()){
            h += "\t\t\t\t" + raton.toString();
        }
        return "FamiliaNormal" +
                "\n\t\t\tpadre:\n\t\t\t\t" + getPadre() +
                "\t\t\tmadre:\n\t\t\t\t" + madre +
                "\t\t\thijos:\n" + h;
    }
}