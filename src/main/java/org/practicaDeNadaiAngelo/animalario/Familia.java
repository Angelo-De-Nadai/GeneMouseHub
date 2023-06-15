package org.practicaDeNadaiAngelo.animalario;

import org.practicaDeNadaiAngelo.excepciones.NegativeInputExeption;

import java.time.LocalDate;
import java.util.*;

/**
 * Familia de ratones compuesta por un padre y un grupo de crías.
 *
 * @author Angelo De Nadai
 */
public abstract class Familia {

    private Raton padre;
    private Set<Raton> hijos;

    public Familia(Raton padre) {
        this.padre = padre;
        this.hijos = new HashSet<Raton>();
    }

    public Raton getPadre() {
        return padre;
    }

    public Set<Raton> getHijos() {
        return hijos;
    }

    /**
     * Genera hijos causal(ratones) a partir del padre ratón y la madre ratón.
     *
     * @param fechaProcreacion
     * @throws NegativeInputExeption
     */

    public abstract Collection<Raton> procrear(LocalDate fechaProcreacion) throws NegativeInputExeption;

    /**
     * Genera hijos causal(ratones) a partir del padre ratón y la madre ratón.
     *
     * @param fechaProcreacion
     * @throws NegativeInputExeption
     */
    public abstract Collection<Raton> procrear(LocalDate fechaProcreacion, Raton padre) throws NegativeInputExeption;

    /**
     * Añade una madre ratón a la familia.
     *
     * @param madre Raton
     */
    public abstract void anadirMadre(Raton madre);

    /**
     * Añade un hijo ratón a la familia.
     *
     * @param hijo Raton
     */
    public void anadirHijo(Raton hijo) {
        if (hijo != null) {
            hijos.add(hijo);
        }
    }

    protected Raton generaHijo(Raton madre, LocalDate dn) throws NegativeInputExeption {
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
}
