package org.practicaDeNadaiAngelo.animalario;

import org.practicaDeNadaiAngelo.excepciones.NegativeInputExeption;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Familia de ratones compuesta por un padre, un grupo de crías y un grupo de
 * madres.
 *
 * @author Angelo De Nadai
 */
public class FamiliaMachoEsteril extends Familia {
    private Set<Raton> madres;

    public FamiliaMachoEsteril(Raton padre) {
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
     * @param padre            casual
     * @throws NegativeInputExeption
     */
    @Override
    public Collection<Raton> procrear(LocalDate fechaProcreacion, Raton padre) throws NegativeInputExeption {
        Iterator<Raton> iterator = madres.iterator();
        Collection<Raton> hijosCamada = new LinkedList<>();
        while (iterator.hasNext()) {
            Raton madre = iterator.next();
            if (!madre.esEsteril()) {
                int random = new Random().nextInt(100);
                int crias = 0;
                if (random < 15) {
                    crias = 2;
                } else if (random < 35) {
                    crias = 3;
                } else if (random < 70) {
                    crias = 4;
                } else if (random < 90) {
                    crias = 5;
                } else {
                    crias = 6;
                }
                while (crias-- != 0) {
                    // aqui genero los hijos con padre random no esteril
                    Raton hijo = generaHijo(madre, padre, fechaProcreacion);
                    // anadirHijo(hijo);
                    hijosCamada.add(hijo);

                    // il mio codice
                    getHijos().add(hijo);

                }
            }
        }
        return hijosCamada;
    }

    protected Raton generaHijo(Raton madre, Raton padre, LocalDate dn) throws NegativeInputExeption {
        int random = new Random().nextInt(100);
        Cromosoma c1;
        Cromosoma c2;
        Sexo sexo = Sexo.FEMENINO;
        if (random > 50) {
            c1 = padre.getC1();
            sexo = Sexo.MASCULINO;
        } else {
            c1 = padre.getC2();
        }
        random = new Random().nextInt(100);
        if (random < 50) {
            c2 = madre.getC1();
        } else {
            c2 = madre.getC2();
        }
        return new Raton(dn, new Random().nextInt(10) + 10, sexo, new Random().nextInt(20) + 10, "Description",
                c1.getMutado(), c2.getMutado());
    }

    @Override
    public Collection<Raton> procrear(LocalDate fechaProcreacion) throws NegativeInputExeption {
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
        return "FamiliaMachoEsteril" + "\n\t\t\tpadre:\n\t\t\t\t" + getPadre() + "\t\t\tmadres:\n" + m
                + "\t\t\thijos:\n" + h;
    }

}
