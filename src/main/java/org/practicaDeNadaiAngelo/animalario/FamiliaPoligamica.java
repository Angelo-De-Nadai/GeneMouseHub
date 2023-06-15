package org.practicaDeNadaiAngelo.animalario;

import org.practicaDeNadaiAngelo.excepciones.NegativeInputExeption;

import java.time.LocalDate;
import java.util.*;

/**
 * Familia de ratones compuesta por un padre, un grupo de crías y un grupo de
 * madres.
 *
 * @author Angelo De Nadai
 */
public class FamiliaPoligamica extends Familia {

    private Set<Raton> madres;

    public FamiliaPoligamica(Raton padre) {
        super(padre);
        madres = new HashSet<Raton>();
    }

    public Set<Raton> getMadres() {
        return madres;
    }

    /**
     * Añade una madre a la familia.
     *
     * @param madre
     */
    @Override
    public void anadirMadre(Raton madre) {
        madres.add(madre);
    }

    /**
     * Genera hijos causal(ratones) a partir del padre ratón y la madre ratón.
     *
     * @param fechaProcreacion
     * @throws NegativeInputExeption
     */
    @Override
    public Collection<Raton> procrear(LocalDate fechaProcreacion) throws NegativeInputExeption {
        if (getPadre().esEsteril()) {
            return null;
        }
        Collection<Raton> hijosCamada = new LinkedList<>();
        Iterator<Raton> iterator = madres.iterator();
        while (iterator.hasNext()) {
            Raton madre = iterator.next();
            if (!madre.esEsteril()) {
                int random = new Random().nextInt(100);
                int crias = 0;
                if (random < 10) {
                    crias = 2;
                } else if (random < 25) {
                    crias = 3;
                } else if (random < 45) {
                    crias = 4;
                } else if (random < 60) {
                    crias = 5;
                } else if (random < 75) {
                    crias = 6;
                } else if (random < 95) {
                    crias = 7;
                } else {
                    crias = 8;
                }
                while (crias-- != 0) {
                    Raton hijo = generaHijo(madre, fechaProcreacion);
                    getHijos().add(hijo);
                    hijosCamada.add(hijo);
                }
            }
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
        for (Raton raton : getHijos()) {
            h += "\t\t\t\t" + raton.toString();
        }
        String m = "";
        for (Raton raton : madres) {
            m += "\t\t\t\t" + raton.toString();
        }
        return "FamiliaPoligamica" + "\n\t\t\tpadre:\n\t\t\t\t" + getPadre() + "\t\t\tmadres:\n" + m + "\t\t\thijos:\n"
                + h;
    }

}
