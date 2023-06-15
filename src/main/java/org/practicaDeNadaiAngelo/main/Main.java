package org.practicaDeNadaiAngelo.main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.practicaDeNadaiAngelo.animalario.*;
import org.practicaDeNadaiAngelo.auxiliar.FuncionesAuxiliares;
import org.practicaDeNadaiAngelo.excepciones.NegativeInputExeption;
import org.practicaDeNadaiAngelo.excepciones.NumDiasException;
import org.practicaDeNadaiAngelo.excepciones.RatonNoEncontrado;

import static org.practicaDeNadaiAngelo.auxiliar.FuncionesAuxiliares.*;

/**
 * @author Angelo De Nadai
 */
public class Main {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        String nombreArchivo = null;
        Poblacion poblacion = null;
        boolean salir = false;
        do {
            try {
                System.out.println("1 - Abrir un archivo .csv de una población de ratones" +
                        "\n2 - Crear una nueva población de ratones\n3 - Crear una nueva población Virtual\n4 - Añadir un ratón a una población" +
                        "\n5 - Lista códigos de referencia de todos los ratones de la población\n6 - Elimina un ratón de la población" +
                        "\n7 - Modificar los datos de un ratón\n8 - Ver información detallada de un ratón\n9 - Simulacion de Montecarlo" +
                        "\n10 - Guardar\n11 - Guardar como\n0 - Salir\nOpción: ");
                int respuesta = kbd.nextInt();
                if (respuesta < 0 || respuesta > 11)
                    throw new IOException();
                switch (respuesta) {
                    case 1: //Abrir un archivo .csv de una población de ratones
                        System.out.println("Introduzcas el nombre del archivo para abrir: ");
                        clear(kbd);
                        nombreArchivo = kbd.nextLine() + ".csv";
                        poblacion = abrirArchivo(nombreArchivo);
                        break;
                    case 2: //Crear una nueva población de ratones
                        poblacion = creaPoblacion();
                        break;
                    case 3://Crear una nueva población Virtual
                        poblacion = creaPoblacionVirtual();
                        break;
                    case 4: //Añadir un ratón a una población
                        if (poblacion != null)
                            poblacion.anadirRaton(creaRaton());
                        else
                            System.out.println("Primero necesitas crear una población\n");
                        break;
                    case 5: //Lista códigos de referencia de todos los ratones de la población
                        if(poblacion != null) {
                            System.out.println(listarRatonesOrdenados(poblacion));
                        }else{
                            System.out.println("Primero necesitas crear una población\n");
                        }
                        break;
                    case 6: //Elimina un ratón de la población
                        System.out.println("Inserte el código del raton para eliminar:");
                        poblacion.eliminaRaton(kbd.nextInt());
                        break;
                    case 7: //Modificar los datos de un ratón
                        if(poblacion == null) {break;}
                        System.out.println("Inserte el código del raton para modificar: ");
                        int codigo = kbd.nextInt();
                        if (poblacion.getRaton(codigo) != null)
                            FuncionesAuxiliares.editarRaton(poblacion.getRaton(codigo));
                        else System.out.println("No hay ratón con este código");
                        break;
                    case 8: //Ver información detallada de un ratón
                        System.out.println("Inserte el código del raton: ");
                        System.out.println(poblacion.infoRaton(kbd.nextInt()));
                        break;
                    case 9: //Simulacion de Montecarlo
                        System.out.println("Introduzca fecha inicio simulacion en el formato dd/MM/yyyy: ");
                        clear(kbd);
                        int[][] estadisticas = poblacion.simulacionMontecarlo(LocalDate.parse(kbd.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        System.out.println(imprimirEstadisticas(estadisticas));
                        break;
                    case 10: //Guardar archivo
                        nombreArchivo = guardarArchivo(nombreArchivo, poblacion);
                        break;
                    case 11: //Guardar archivo como
                        nombreArchivo = guardarArchivoComo(poblacion);
                        break;
                    case 0://salir
                        salir = true;
                        System.out.println("Gracias, adiós");
                        break;
                }
            } catch (IOException ime) {
                System.err.println("Solo puede ingresar un número del 1 al 10");
                clear(kbd);
            } catch (RatonNoEncontrado e) {
                throw new RuntimeException(e);
            } catch (NullPointerException e) {
                throw new NullPointerException();
            } catch (NegativeInputExeption e) {
                throw new RuntimeException(e);
            } catch (NumDiasException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } while (!salir);
    }
}