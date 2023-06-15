package org.practicaDeNadaiAngelo.auxiliar;

import org.practicaDeNadaiAngelo.animalario.Raton;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.practicaDeNadaiAngelo.animalario.*;
import org.practicaDeNadaiAngelo.excepciones.NegativeInputExeption;
import org.practicaDeNadaiAngelo.excepciones.NumDiasException;

/**
 * Clase donde se guardan para fines organizativos las funciones del proyecto
 * que interactúan con el usuario y que no pertenecen directamente a un objeto
 *
 * @author Angelo De Nadai
 */
public class FuncionesAuxiliares {
    public static void main(String[] args)
            throws IOException, NegativeInputExeption, NumDiasException, ClassNotFoundException {
        Raton rr1 = new Raton(LocalDate.of(2021, 06, 5), 20, Sexo.MASCULINO, 30, "DESC1", true, true);
        Raton rr2 = new Raton(LocalDate.of(2020, 07, 8), 60, Sexo.FEMENINO, 40, "DESC2", false, true);
        Poblacion poblacion = new Poblacion("pob1", "ref1", 135);
        poblacion.anadirRaton(rr1);
        poblacion.anadirRaton(rr2);
        poblacion = abrirArchivo("prueba2.csv");
        System.out.println("Crear población:");
        poblacion = creaPoblacion();
        System.out.println("Crea un raton:");
        Raton raton = creaRaton();
        System.out.println("Agregar un raton a la población:");
        poblacion.anadirRaton(raton);
        System.out.println("Editar raton:");
        editarRaton(raton);
        System.out.println("Guarda en archivo:");
        String archivo = guardarArchivoComo(poblacion);
        guardarArchivo(archivo, poblacion);
        poblacion = abrirArchivo(archivo);
        System.out.println(poblacion.toString());

        Poblacion poblacion3 = creaPoblacionVirtual();
        poblacion3.simulacionMontecarlo(LocalDate.of(2020, 06, 5));
        poblacion3.procrearFamilias(LocalDate.of(2017, 06, 5));
        guardarArchivo("prova1.csv", poblacion3);

        Raton r1 = new Raton(LocalDate.of(2021, 06, 5), 20, Sexo.MASCULINO, 30, "DESC1", false, false);
        Raton h2 = new Raton(LocalDate.of(2021, 06, 5), 20, Sexo.FEMENINO, 30, "DESC1", false, false);
        Raton r3 = new Raton(LocalDate.of(2021, 06, 5), 20, Sexo.MASCULINO, 30, "DESC1", false, true);
        Raton h4 = new Raton(LocalDate.of(2021, 06, 5), 20, Sexo.FEMENINO, 30, "DESC1", false, false);
        Raton h5 = new Raton(LocalDate.of(2021, 06, 5), 20, Sexo.FEMENINO, 30, "DESC1", false, false);

        Poblacion poblacion2 = abrirArchivo("prova1.csv");
        System.out.println(poblacion2.toString());
    }

    static Scanner kbd = new Scanner(System.in);

    /**
     * Guardar una población en un nuevo archivo
     *
     * @param poblacion
     * @return nombreArchivo
     */

    public static String guardarArchivoComo(Poblacion poblacion) throws IOException {
        System.out.println("Cómo quieres llamar al archivo? (si ya existe se sobrescribirá): ");
        clear(kbd);
        return guardarArchivo(kbd.nextLine() + ".csv", poblacion);
    }

    /**
     * Modificar los parámetros de un ratòn
     *
     * @param raton
     */
    public static void editarRaton(Raton raton) {
        try {
            System.out.println("Qué quieres editar?\n" + raton.toString() + "\nOpciones:"
                    + "\n1 - peso\n2 - temperatura\n3 - description\n4 - mutamiento cromosoma 1"
                    + "\n5 - mutamiento comosoma 2\n6 - Borrar\nOpción: ");

            switch (kbd.nextInt()) {
                case 1:
                    System.out.println("Introduces el nuevo peso: ");
                    int peso = kbd.nextInt();
                    if (peso > 0) {
                        raton.setPeso(peso);
                    }
                    break;
                case 2:
                    System.out.println("Introduce la nueva temperatura: ");
                    raton.setTemp(kbd.nextFloat());
                    break;
                case 3:
                    System.out.println("Introduce la nueva descripción: ");
                    clear(kbd);
                    raton.setDescription(kbd.nextLine());
                    break;
                case 4:
                    System.out.print("Ahora el comosoma 1 está ");
                    if ((raton.getC1().getMutado())) {
                        System.out.println("mutado, quieres cambiarlo?(S/N): ");
                    } else {
                        System.out.println("no mutado, quieres cambiarlo?(S/N): ");
                    }
                    clear(kbd);
                    if (kbd.nextLine().toUpperCase().equals("S")) {
                        if (raton.getC1().getMutado()) {
                            raton.getC1().setMutado(false);
                        } else {
                            raton.getC1().setMutado(true);
                        }
                    }
                    break;
                case 5:
                    System.out.print("Ahora el comosoma 2 está ");
                    if ((raton.getC1().getMutado())) {
                        System.out.println("mutado, quieres cambiarlo?(S/N): ");
                    } else {
                        System.out.println("no mutado, quieres cambiarlo?(S/N): ");
                    }
                    clear(kbd);
                    if (kbd.nextLine().toUpperCase().equals("S")) {
                        if (raton.getC2().getMutado()) {
                            raton.getC2().setMutado(false);
                        } else {
                            raton.getC2().setMutado(true);
                        }
                    }
                    break;
                case 6:
                    break;
            }
        } catch (NegativeInputExeption e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea una población de ratones.
     *
     * @return población creada
     */
    public static Poblacion creaPoblacion() {
        try {
            System.out.println("Ingrese el nombre de la población: ");
            // clear(kbd);
            String nombre = kbd.nextLine();
            System.out.println("Ingrese el nombre del responsable: ");
            // clear(kbd);
            String responsable = kbd.nextLine();
            int numDias;
            do {
                System.out.println("Ingrese el número de días que la población permanece en el animalario(0-"
                        + Poblacion.MAX_DAYS + "):");
                numDias = kbd.nextInt();
            } while (numDias <= 0 || numDias > Poblacion.MAX_DAYS || numDias % Poblacion.MULTIPLE_DAYS != 0);
            Poblacion poblacion = new Poblacion(nombre, responsable, numDias);
            System.out.println("Agregar un raton a la población?(S/N) ");
            clear(kbd);
            String respuesta = kbd.nextLine();
            while (respuesta.toUpperCase().equals("S")) {
                poblacion.anadirRaton(creaRaton());
                System.out.println("Agregar otro raton a la población?(S/N) ");
                clear(kbd);
                respuesta = kbd.nextLine();
            }
            return poblacion;
        } catch (NegativeInputExeption e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * crear un raton
     *
     * @return raton creado
     */
    public static Raton creaRaton() throws NegativeInputExeption {
        try {
            System.out.println("Introduzca fecha de nacimiento en el formato dd/MM/yyyy: ");
            clear(kbd);
            LocalDate dn = LocalDate.parse(kbd.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            System.out.println("Introducir peso: ");
            int peso = kbd.nextInt();
            System.out.println("Ingrese el sexo (M-masculino, F-femenino): ");
            String resp = "";
            do {
                clear(kbd);
                resp = kbd.nextLine().toUpperCase();
            } while (!resp.equals("M") && !resp.equals("F"));
            Sexo sexo = null;
            if (resp.equals("M")) {
                sexo = Sexo.MASCULINO;
            } else {
                sexo = Sexo.FEMENINO;
            }
            System.out.println("Ingrese la temperatura: ");
            Float temp = kbd.nextFloat();
            System.out.println("Introducir descripción: ");
            clear(kbd);
            String description = kbd.nextLine();

            System.out.println("el primer cromosoma es mutado (true/false):");
            boolean mutado1 = kbd.nextBoolean();
            System.out.println("el segundo cromosoma es mutado (true/false):");
            boolean mutado2 = kbd.nextBoolean();
            return new Raton(dn, peso, sexo, temp, description, mutado1, mutado2);
        } catch (NegativeInputExeption e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea una población virtual basada en los datos que proporcionas
     *
     * @return Poblacion virtual
     * @throws NegativeInputExeption
     * @throws NumDiasException
     */
    public static Poblacion creaPoblacionVirtual() throws NegativeInputExeption, NumDiasException {
        System.out.println("Ingrese el nombre de la población virtual: ");
        // clear(kbd);
        String nombre = kbd.nextLine();
        System.out.println("Ingrese el nombre del responsable: ");
        // clear(kbd);
        String responsable = kbd.nextLine();
        int numDias;
        do {
            System.out.println("Ingrese el número de días que la población permanece en el animalario(0-"
                    + Poblacion.MAX_DAYS + "):");
            numDias = kbd.nextInt();
        } while (numDias <= 0 || numDias > Poblacion.MAX_DAYS || numDias % Poblacion.MULTIPLE_DAYS != 0);

        System.out.println("Ingrese el numero total de Ratones: ");// nr
        int nr = kbd.nextInt(); // numero total de ratones
        System.out.println("Ingrese el percentaje de machos: ");
        int m = kbd.nextInt(); // numero de machos
        System.out.println("Ingrese el porcentaje de machos estériles: ");
        int me = kbd.nextInt(); // numero de machos esteriles
        System.out.println("Ingrese el porcentaje de hombres propensos a la poligamia: ");
        int mp = kbd.nextInt(); // numero de machos poligamicos
        System.out.println("Ingrese el porcentaje de cromosomas mutilados de los ratones Hembras: ");
        int he = kbd.nextInt(); // número de cromosomas mutados hembras
        Poblacion poblacion = new Poblacion(nombre, responsable, numDias, nr, m, me, mp, he);
        return poblacion;
    }

    /**
     * Convierte una matriz de enteros de estadísticas en una Stringa
     *
     * @param array de estadisticas da convertir
     * @return String da imprimir de estadisticas
     */
    public static String imprimirEstadisticas(int[][] array) {
        String[] nombres = { "Ratones totales\t\t\t", "porcentaje de machos\t", "porcentaje de hembras\t",
                "machos normales\t\t\t", "machos polígamos\t\t", "machos estériles\t\t", "hembras normales\t\t",
                "hembras casi estériles\t", "hembras estériles\t\t" };
        StringBuilder sb = new StringBuilder();
        // Genera un array que representa las barras
        for (int i = 0; i < array.length; i++) {
            sb.append("Grafico " + i + ":\n" + nombres[0] + array[i][0] + "\n");
            for (int j = 1; j < array[i].length; j++) {
                String barra = "";
                for (int k = 0; k < array[i][j]; k++) {
                    barra += "|";
                }
                // Agreguemos la barra al StringBuilder
                sb.append(nombres[j] + " " + barra + " " + array[i][j] + "%\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * limpia el escáner
     *
     * @param scan
     */
    public static void clear(Scanner scan) {
        if (scan.hasNextLine()) {
            scan.nextLine();
        }
    }

    /**
     * Guardar una población en un archivo ya abierto
     *
     * @param nombreArchivo
     * @param poblacion
     * @return nombreArchivo
     */
    public static String guardarArchivo(String nombreArchivo, Poblacion poblacion) throws IOException {

        // Cree un nuevo objeto FileWriter para escribir en el archivo CSV
        FileWriter writer = new FileWriter(nombreArchivo);

        // Escribe la fila del encabezado
        writer.write("nombre,responsable,numDias\n");

        // Escribe los datos de la poblacion
        writer.write(
                String.format("%s,%s,%d\n", poblacion.getNombre(), poblacion.getResponsable(), poblacion.getNumDias()));

        // Escribe los datos de ratonesMachos
        writer.write("ratones\n");
        for (Raton raton : poblacion.getRatonesMachos()) {
            writeRatonData(writer, raton);
        }
        // Escribe los datos de ratonesHembras
        for (Raton raton : poblacion.getRatonesHembras()) {
            writeRatonData(writer, raton);
        }

        // Escribe los datos de las familias
        writer.write("familias\n");
        Iterator<Familia> iterator = poblacion.getFamilias().iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof FamiliaNormal) {
                writeFamiliaNormalData(writer, (FamiliaNormal) obj);
            } else if (obj instanceof FamiliaPoligamica) {
                writeFamiliaPoligamicaData(writer, (FamiliaPoligamica) obj);
            } else if (obj instanceof FamiliaMachoEsteril) {
                writeFamiliaMachoEsterilData(writer, (FamiliaMachoEsteril) obj);
            }
        }

        // Cerrar el FileWriter
        writer.close();
        return nombreArchivo;
    }

    private static void writeRatonData(FileWriter writer, Raton raton) throws IOException {
        writer.write(String.format("%d,%s,%d,%s,%d,%s,%b,%b\n", raton.getCod(), raton.getDn().toString(),
                raton.getPeso(), raton.getSexo().toString(), (int) (raton.getTemp()), raton.getDescription(),
                raton.getC1().getMutado(), raton.getC2().getMutado()));
    }

    private static void writeFamiliaNormalData(FileWriter writer, FamiliaNormal familia) throws IOException {
        writer.write("familiaNormal\n");
        writeRatonData(writer, familia.getPadre());
        writeRatonData(writer, familia.getMadre());
        writer.write("hijos\n");
        for (Raton hijo : familia.getHijos()) {
            writeRatonData(writer, hijo);
        }
    }

    private static void writeFamiliaPoligamicaData(FileWriter writer, FamiliaPoligamica familia) throws IOException {
        writer.write("familiaPoligamica\n");
        writeRatonData(writer, familia.getPadre());
        for (Raton madre : familia.getMadres()) {
            writeRatonData(writer, madre);
        }
        writer.write("hijos\n");
        for (Raton hijo : familia.getHijos()) {
            writeRatonData(writer, hijo);
        }
    }

    private static void writeFamiliaMachoEsterilData(FileWriter writer, FamiliaMachoEsteril familia)
            throws IOException {
        writer.write("familiaMachoEsteril\n");
        for (Raton madre : familia.getMadres()) {
            writeRatonData(writer, madre);
        }
        writer.write("hijos\n");
        for (Raton hijo : familia.getHijos()) {
            writeRatonData(writer, hijo);
        }
    }

    /**
     * Abre el archivo especificado como parámetro y devuelve la población de
     * ratones guardada dentro
     *
     * @param nombreArchivo
     * @return población de ratones guardada en el archivo
     */
    public static Poblacion abrirArchivo(String nombreArchivo)
            throws IOException, ClassNotFoundException, NegativeInputExeption {
        // Cree un nuevo objeto FileReader para leer el archivo CSV
        FileReader reader = new FileReader(nombreArchivo);

        // Cree un nuevo objeto BufferedReader para leer el archivo CSV línea por línea
        BufferedReader bufferedReader = new BufferedReader(reader);

        bufferedReader.readLine(); // Read the header row

        // Leer los datos de población
        String populationData = bufferedReader.readLine();
        String[] populationDataArray = populationData.split(",");
        String nombre = populationDataArray[0];
        String responsable = populationDataArray[1];
        int numDias = Integer.parseInt(populationDataArray[2]);
        Poblacion poblacion = new Poblacion(nombre, responsable, numDias);

        String line = bufferedReader.readLine(); // leer "ratones"
        line = bufferedReader.readLine(); // lee primero raton
        while (!line.equals("familias")) {
            poblacion.anadirRaton(readRatonData(line));
            line = bufferedReader.readLine();
        }

        // Leer los datos de las familias
        line = bufferedReader.readLine();
        while (line != null && !line.equals("")) {
            if (line.equals("familiaNormal")) {
                poblacion.anadirFamilia(readFamiliaNormalData(bufferedReader));
            } else if (line.equals("familiaPoligamica")) {
                poblacion.anadirFamilia(readFamiliaPoligamicaData(bufferedReader));
            } else if (line.equals("familiaMachoEsteril")) {
                Familia fam = readFamiliaMachoEsterilData(bufferedReader);
                poblacion.anadirFamilia(fam);
            }
            if (line != null && !line.equals("")) {
                bufferedReader.reset();
                line = bufferedReader.readLine();
            }
        }

        // cierro FileReader y BufferedReader
        bufferedReader.close();
        reader.close();
        return poblacion;
    }

    private static Raton readRatonData(String line) throws NegativeInputExeption {
        String[] ratonDataArray = line.split(",");

        int cod = Integer.parseInt(ratonDataArray[0]);
        LocalDate dn = LocalDate.parse(ratonDataArray[1]);
        int peso = Integer.parseInt(ratonDataArray[2]);
        Sexo sexo = Sexo.valueOf(ratonDataArray[3]);
        float temp = (float) Integer.parseInt(ratonDataArray[4]);
        String description = ratonDataArray[5];
        boolean c1Mutado = Boolean.parseBoolean(ratonDataArray[6]);
        boolean c2Mutado = Boolean.parseBoolean(ratonDataArray[7]);

        return new Raton(dn, peso, sexo, temp, description, c1Mutado, c2Mutado);
    }

    private static FamiliaNormal readFamiliaNormalData(BufferedReader bufferedReader)
            throws IOException, NegativeInputExeption {
        Raton padre = readRatonData(bufferedReader.readLine());
        Raton madre = readRatonData(bufferedReader.readLine());
        FamiliaNormal familia = new FamiliaNormal(padre, madre);
        String line = bufferedReader.readLine(); // lee hijos
        line = bufferedReader.readLine(); // lee primer hijo
        while (line != null && !line.equals("familiaMachoEsteril") && !line.equals("familiaPoligamica")
                && !line.equals("familiaNormal") && !line.equals("")) {
            familia.anadirHijo(readRatonData(line));
            bufferedReader.mark(1);
            line = bufferedReader.readLine();
        }
        return familia;
    }

    private static FamiliaPoligamica readFamiliaPoligamicaData(BufferedReader bufferedReader)
            throws IOException, NegativeInputExeption {
        Raton padre = readRatonData(bufferedReader.readLine());
        FamiliaPoligamica familia = new FamiliaPoligamica(padre);

        String line = bufferedReader.readLine();
        while (!line.equals("hijos")) {
            familia.anadirMadre(readRatonData(line));
            line = bufferedReader.readLine();
        }

        line = bufferedReader.readLine(); // lee linea hijos
        while (line != null && !line.equals("familiaMachoEsteril") && !line.equals("familiaPoligamica")
                && !line.equals("familiaNormal") && !line.equals("")) {
            familia.anadirHijo(readRatonData(line));
            bufferedReader.mark(2);
            line = bufferedReader.readLine();
        }
        return familia;
    }

    private static FamiliaMachoEsteril readFamiliaMachoEsterilData(BufferedReader bufferedReader)
            throws IOException, NegativeInputExeption {
        Raton padre = readRatonData(bufferedReader.readLine());
        FamiliaMachoEsteril familia = new FamiliaMachoEsteril(padre);

        String line = bufferedReader.readLine();
        while (!line.equals("hijos")) {
            familia.anadirMadre(readRatonData(line));
            line = bufferedReader.readLine();
        }

        line = bufferedReader.readLine(); // lee linea hijos
        while (line != null && !line.equals("familiaMachoEsteril") && !line.equals("familiaPoligamica")
                && !line.equals("familiaNormal") && !line.equals("")) {
            familia.anadirHijo(readRatonData(line));
            bufferedReader.mark(3);
            line = bufferedReader.readLine();
        }
        return familia;
    }

    /**
     * pregunta en qué orden ordenar los ratones de la población pasada como
     * parámetro devuelve la lista ordenada de ratones
     *
     * @param poblacion da ordenar
     * @return ArrayList ordenada de ratones
     */
    public static List<Raton> listarRatonesOrdenados(Poblacion poblacion) {
        int respuesta;
        try {
            System.out.println(
                    "Elija el parámetro por el cual ordenar los ratones para mostrar:\n1 - Código\n2 - Peso\n3 - Fecha\n0 - Salir\nOpción: ");
            respuesta = kbd.nextInt();
            if (respuesta < 0 || respuesta > 3)
                throw new IOException();
            switch (respuesta) {
                case 1:
                    return poblacion.ordenaAlfabeticamente();
                case 2:
                    return poblacion.ordenaPorPeso();
                case 3:
                    return poblacion.ordenaPorFecha();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<Raton>();
    }
}