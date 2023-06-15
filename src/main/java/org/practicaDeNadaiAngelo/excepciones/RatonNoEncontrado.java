package org.practicaDeNadaiAngelo.excepciones;
/**
 * @author Angelo De Nadai
 */
public class RatonNoEncontrado extends Exception {
    public RatonNoEncontrado() {
        super("No hay ratón con este código");
    }
}
