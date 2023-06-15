package org.practicaDeNadaiAngelo.animalario;

import org.practicaDeNadaiAngelo.excepciones.NegativeInputExeption;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;

/**
 * Representa el objeto Raton con atributos (código, fecha de nacimiento, peso,
 * sexo, temperatura, descripción, cromosoma 1 y cromosoma 2), la clase permite
 * la lectura desde el exterior para todos los atributos, y mientras no es
 * posible escribir para el código, fecha de nacimiento y sexo Hay dos funciones
 * en la clase: esEsteril= verifica la esterilidad del ratón devolviendo un
 * booleano esPoligamo= comprobar la poligamia del ratón devolviendo un booleano
 *
 * @author Angelo De Nadai
 */
public class Raton implements Serializable {

    public static void main(String[] args) throws NegativeInputExeption {
        Raton raton = new Raton(LocalDate.of(2021, 06, 2), 20, Sexo.MASCULINO, 30, "DESC1", true, true);
        System.out.println(raton.toString() + "\nraton esEsteril:" + raton.esEsteril() + ", es poligamo: "
                + raton.esPoligamo() + ", es Adulto: " + raton.esMaduro(LocalDate.of(2021, 02, 2)));
    }

    public static int codigo = 1;

    private final int cod;
    private final LocalDate dn;
    private int peso;
    private final Sexo sexo;
    private float temp;
    private String description;
    private Cromosoma c1;
    private Cromosoma c2;

    // constructor
    public Raton(LocalDate dn, int peso, Sexo sexo, float temp, String description, boolean mutadoC1, boolean mutadoC2)
            throws NegativeInputExeption {
        this.cod = codigo++;
        if (dn.isBefore(LocalDate.now())) {
            this.dn = dn;
        } else {
            throw new IllegalArgumentException();
        }
        setPeso(peso);
        this.sexo = sexo;
        setTemp(temp);
        setDescription(description);
        c1 = new Cromosoma(TipoCromosoma.X, mutadoC1);
        if (sexo == Sexo.MASCULINO) {
            this.c2 = new Cromosoma(TipoCromosoma.Y, mutadoC2);
        } else {
            this.c2 = new Cromosoma(TipoCromosoma.X, mutadoC2);
        }
    }

    public int getCod() {
        return cod;
    }

    public LocalDate getDn() {
        return dn;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) throws NegativeInputExeption {
        if (peso < 0) {
            throw new NegativeInputExeption();
        }
        this.peso = peso;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) throws NegativeInputExeption {
        if (temp < 0) {
            throw new NegativeInputExeption();
        }
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cromosoma getC1() {
        return c1;
    }

    public Cromosoma getC2() {
        return c2;
    }

    @Override
    public String toString() {
        return "Raton{" + "cod=" + cod + ", dn=" + dn + ", peso=" + peso + ", sexo=" + sexo + ", temp=" + temp
                + ", description='" + description + '\'' + ", c1=" + c1.toString() + ", c2=" + c2.toString() + "}\n";
    }

    /**
     * Comproba si el ratón es estéril
     *
     * @return true si es verdadero, false si no es verdadero
     */
    public boolean esEsteril() {
        if (sexo == Sexo.MASCULINO && c1.getMutado() == true) {
            return true;
        }
        if (sexo == Sexo.FEMENINO && c1.getMutado() == true && c2.getMutado() == true) {
            return true;
        }
        return false;
    }

    /**
     * Comproba si el ratón es poligamo
     *
     * @return true si es verdadero, false si no es verdadero
     */
    public boolean esPoligamo() {
        if (sexo == Sexo.MASCULINO && c2.getMutado() == true) {
            return true;
        }
        return false;
    }

    /**
     * Comproba si el ratón es maduro(edad > 45 dias) comprobando con la fecha
     * ingresada
     *
     * @return true si es adulto, false si no es adulto
     * @param fecha comprobacion
     */
    public boolean esMaduro(LocalDate fecha) {
        if (ChronoUnit.DAYS.between(dn, fecha) > 45) {
            return true;
        } else {
            return false;
        }
    }

}
