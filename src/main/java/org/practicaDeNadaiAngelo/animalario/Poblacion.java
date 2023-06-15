package org.practicaDeNadaiAngelo.animalario;

import org.practicaDeNadaiAngelo.excepciones.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * representa el objeto de una Población de Ratones con atributos (nombre,
 * administrador, número de días pasados en el animalario, matriz de ratones),
 * la clase permite que todos los atributos se lean desde el exterior, y
 * mientras que la escritura no es posible para el código, la fecha de
 * nacimiento y el sexo.
 *
 * @author Angelo De Nadai
 */
public class Poblacion implements Serializable {

    public static void main(String[] args) throws RatonNoEncontrado, NegativeInputExeption, NumDiasException {
        Raton r1 = new Raton(LocalDate.of(2021, 06, 5), 20, Sexo.MASCULINO, 30, "DESC1", true, true);
        Raton r2 = new Raton(LocalDate.of(2019, 07, 8), 60, Sexo.MASCULINO, 40, "DESC2", false, true);
        Raton r3 = new Raton(LocalDate.of(2021, 02, 5), 50, Sexo.FEMENINO, 30, "DESC3", true, true);
        Raton r4 = new Raton(LocalDate.of(2020, 10, 8), 30, Sexo.FEMENINO, 40, "DESC4", false, true);
        Raton newRaton = new Raton(LocalDate.of(2009, 04, 25), 30, Sexo.MASCULINO, 15, "DESC5", true, true);
        Poblacion poblacion = new Poblacion("pob1", "ref1", 135);
        poblacion.anadirRaton(r1);
        poblacion.anadirRaton(r2);
        poblacion.anadirRaton(r3);
        poblacion.anadirRaton(r4);
        poblacion.anadirRaton(newRaton);
        System.out.println("CÓDIGOS POBLACIÓN:");
        System.out.println(poblacion.getCodigosRatones());

        System.out.println("CÓDIGOS DE POBLACIÓN ELIMINACIÓN COD 1");
        poblacion.eliminaRaton(1);
        System.out.println(poblacion.getCodigosRatones());

        System.out.println("ORDENAMIENTO POBLACIÓN");
        System.out.println("Cod:\n" + poblacion.ordenaAlfabeticamente().toString());
        System.out.println("Peso:\n" + poblacion.ordenaPorPeso().toString());
        System.out.println("Fecha:\n" + poblacion.ordenaPorFecha().toString());
        Poblacion poblacion2 = new Poblacion("PoblacionVirtual", "Rodrigo", 180, 10, 50, 50, 50, 50);
        System.out.println("\nPoblacion Virtual:\n" + poblacion2.getCodigosRatones());
        poblacion2.creaFamilias();
        System.out.println("\nPOBLACION DESPUES DE LA CREACION DE LA FAMILIA :\n" + poblacion2.toString());

        poblacion2.procrearFamilias(LocalDate.of(2020, 1, 1));
        System.out.println("POBLACION DESPUES DE LA RIPRODUCION:\n" + poblacion2.toString());

        poblacion2.simulacionMontecarlo(LocalDate.of(2019, 8, 1));
        System.out.println("POBLACION DESPUES DE LA SIMULACION DE MONTECARLO:\n" + poblacion2.toString());
    }

    public static final int MAX_DAYS = 630;// días máximos que una población puede permanecer en la animalario
    public static final int MULTIPLE_DAYS = 45;// multiplo de los días máximos que una población puede permanecer en la
    // animalario
    private String nombre;
    private String responsable;
    private int numDias;
    private List<Raton> ratonesMachos;
    private List<Raton> ratonesHembras;
    private Set<Familia> familias;

    // constructor
    public Poblacion(String nombre, String responsable, int numDias) {
        this.nombre = nombre;
        this.responsable = responsable;
        this.numDias = numDias;
        this.ratonesMachos = new ArrayList<>();
        this.ratonesHembras = new ArrayList<>();
        this.familias = new HashSet<>();
    }

    // constructor Poblacion virtual
    public Poblacion(String nombre, String responsable, int numDias, int numeroRatones, int nMachos,
                     int machosEsteriles, int machosPoligamicos, int hembrasEsteriles)
            throws NegativeInputExeption, NumDiasException {
        this.nombre = nombre;
        this.responsable = responsable;
        setNumDias(numDias);
        this.ratonesMachos = new ArrayList<>();
        this.ratonesHembras = new ArrayList<>();
        poblacionVirtual(numeroRatones, nMachos, machosEsteriles, machosPoligamicos, hembrasEsteriles);
        this.familias = new HashSet<>();

    }

    private void poblacionVirtual(int numeroRatones, int pMachos, int machosEsteriles, int machosPoligamicos,
                                  int hembrasEsteriles) throws NegativeInputExeption {
        int random;
        Sexo sexo = Sexo.MASCULINO;
        boolean mutado1 = false;
        boolean mutado2 = false;

        for (int i = 0; i < numeroRatones; i++) {
            random = new Random().nextInt(100);
            if (pMachos > random) {
                sexo = Sexo.MASCULINO;
                random = new Random().nextInt(100);
                mutado1 = (machosEsteriles > random) ? true : false;
                random = new Random().nextInt(100);
                mutado2 = (machosPoligamicos > random) ? true : false;
            } else {
                sexo = Sexo.FEMENINO;
                random = new Random().nextInt(100);
                mutado1 = (hembrasEsteriles > random) ? true : false;
                random = new Random().nextInt(100);
                mutado2 = (hembrasEsteriles > random) ? true : false;
            }
            anadirRaton(new Raton(generaFechaCasual(), new Random().nextInt(10) + 10, sexo,
                    new Random().nextFloat(20) + 10, "Description", mutado1, mutado2));
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getNumDias() {
        return numDias;
    }

    private void setNumDias(int numDias) throws NumDiasException {
        if (numDias <= MAX_DAYS && numDias > 0 && numDias % MULTIPLE_DAYS == 0) {
            this.numDias = numDias;
        } else {
            throw new NumDiasException(
                    "Debes ingresar un número entre 1 y " + MAX_DAYS + " y múltiplo de " + MULTIPLE_DAYS);
        }
    }

    public List<Raton> getRatonesMachos() {
        return ratonesMachos;
    }

    public List<Raton> getRatonesHembras() {
        return ratonesHembras;
    }

    public Set<Familia> getFamilias() {
        return familias;
    }

    /**
     * 4. Añade un nuevo ratón a una población ya existente. si el raton es igual
     * a null, no se agrega nada
     *
     * @param newRaton
     *
     */
    public void anadirRaton(Raton newRaton) {
        if (newRaton == null) {
            return;
        }
        if (newRaton.getSexo() == Sexo.MASCULINO) {
            ratonesMachos.add(newRaton);
        } else {
            ratonesHembras.add(newRaton);
        }
    }

    /**
     * Se agrega una familia alla matriz familias si no es igual a null
     *
     * @param familia da agregar
     */
    public void anadirFamilia(Familia familia) {
        if (familia != null) {
            familias.add(familia);
        }
    }

    /**
     * 5. Lista todos los codigos de los ratones de una población
     *
     * @return ArrayList de Integer de los codigos de Ratones
     *
     */
    public List<Integer> getCodigosRatones() {
        ArrayList<Integer> codigos = new ArrayList<>();
        Iterator<Raton> raton = ratonesMachos.iterator();
        while (raton.hasNext()) {
            codigos.add(raton.next().getCod());
        }
        raton = ratonesHembras.iterator();
        while (raton.hasNext()) {
            codigos.add(raton.next().getCod());
        }
        return codigos;
    }

    /**
     * Ordenar una lista de ratones alfabéticamente(codigos)
     *
     * @return ArrayList de Ratones ordenado
     */
    public ArrayList<Raton> ordenaAlfabeticamente() {
        // fusiono los dos arrayList
        ArrayList<Raton> ratones = new ArrayList<>();
        ratones.addAll(ratonesMachos);
        ratones.addAll(ratonesHembras);

        quickSortedCod(ratones, 0, ratones.size() - 1);
        return ratones;
    }

    /**
     * Ordenar una lista de ratones por fecha nacimiento
     *
     * @return ArrayList de Ratones ordenado por fecha
     */
    public ArrayList<Raton> ordenaPorFecha() {
        // fusiono los dos arrayList
        ArrayList<Raton> ratones = new ArrayList<>();
        ratones.addAll(ratonesMachos);
        ratones.addAll(ratonesHembras);

        quickSortedFecha(ratones, 0, ratones.size() - 1);
        return ratones;
    }

    /**
     * Ordenar una lista de ratones por Peso(descendiendo)
     *
     * @return ArrayList de Ratones ordenado por peso(descendiendo)
     */
    public ArrayList<Raton> ordenaPorPeso() {
        // fusiono los dos arrayList
        ArrayList<Raton> ratones = new ArrayList<>();
        ratones.addAll(ratonesMachos);
        ratones.addAll(ratonesHembras);

        quickSortedPeso(ratones, 0, ratones.size() - 1);
        return ratones;
    }

    private void quickSortedCod(ArrayList<Raton> ratones, int izq, int der) {
        if (izq < der) {
            int pivotPosicion = new Random().nextInt(der - izq) + izq; // Genero un índice aleatorio para el pivot
            // porque mejora la eficiencia del algoritmo en
            // el caso promedio
            Raton pivot = ratones.get(pivotPosicion);
            intercambiador(ratones, pivotPosicion, der); // Muevo el pivot a la posición más a la derecha
            int indice = arrayDivisorCod(ratones, izq, der, pivot);

            quickSortedCod(ratones, izq, indice - 1); // Ordeno la sección izquierda
            quickSortedCod(ratones, indice + 1, der); // Ordeno la sección derecha
        }
    }

    private void quickSortedPeso(ArrayList<Raton> ratones, int izq, int der) {
        if (izq < der) {
            int pivotPosicion = new Random().nextInt(der - izq) + izq; // Genero un índice aleatorio para el pivot
            // porque mejora la eficiencia del algoritmo en
            // el caso promedio
            Raton pivot = ratones.get(pivotPosicion);
            intercambiador(ratones, pivotPosicion, der); // Muevo el pivot a la posición más a la derecha
            int indice = arrayDivisorPeso(ratones, izq, der, pivot);

            quickSortedPeso(ratones, izq, indice - 1); // Ordeno la sección izquierda
            quickSortedPeso(ratones, indice + 1, der); // Ordeno la sección derecha
        }
    }

    private void quickSortedFecha(ArrayList<Raton> ratones, int izq, int der) {
        if (izq < der) {
            int pivotPosicion = new Random().nextInt(der - izq) + izq; // Genero un índice aleatorio para el pivot
            // porque mejora la eficiencia del algoritmo en
            // el caso promedio
            Raton pivot = ratones.get(pivotPosicion);
            intercambiador(ratones, pivotPosicion, der); // Muevo el pivot a la posición más a la derecha
            int indice = arrayDivisorFecha(ratones, izq, der, pivot);

            quickSortedFecha(ratones, izq, indice - 1); // Ordeno la sección izquierda
            quickSortedFecha(ratones, indice + 1, der); // Ordeno la sección derecha
        }
    }

    private static int arrayDivisorCod(ArrayList<Raton> ratones, int izq, int max, Raton pivot) {
        int der = max - 1;

        while (izq < der) { // termina cuando los dos índices se encuentran
            while (izq < der && ratones.get(izq).getCod() <= pivot.getCod()) { // Busque un valor mayor que el pivot
                izq++;
            }
            while (izq < der && ratones.get(der).getCod() >= pivot.getCod()) { // Busque un valor más pequeño que el
                // pivot
                der--;
            }
            intercambiador(ratones, izq, der); // Se ordenan los valores encontrados
        }
        if (ratones.get(izq).getCod() > ratones.get(max).getCod()) {
            intercambiador(ratones, izq, max);
        } else {
            izq = max;
        }

        // intercambiador(ratones, izq, max);
        return izq;
    }

    private static int arrayDivisorPeso(ArrayList<Raton> ratones, int izq, int max, Raton pivot) {
        int der = max - 1;

        while (izq < der) { // termina cuando los dos índices se encuentran
            while (izq < der && ratones.get(izq).getPeso() >= pivot.getPeso()) { // Busque un valor mayor que el pivot
                izq++;
            }
            while (izq < der && ratones.get(der).getPeso() <= pivot.getPeso()) { // Busque un valor más pequeño que el
                // pivot
                der--;
            }
            intercambiador(ratones, izq, der); // Se ordenan los valores encontrados
        }
        if (ratones.get(izq).getPeso() < ratones.get(max).getPeso()) {
            intercambiador(ratones, izq, max);
        } else {
            izq = max;
        }
        // intercambiador(ratones, izq, max);
        return izq;
    }

    private static int arrayDivisorFecha(ArrayList<Raton> ratones, int izq, int max, Raton pivot) {
        int der = max - 1;

        while (izq < der) { // termina cuando los dos índices se encuentran
            while (izq < der && (ratones.get(izq).getDn().isBefore(pivot.getDn())
                    || ratones.get(izq).getDn().isEqual(pivot.getDn()))) { // Busque un valor mayor que el pivot
                izq++;
            }
            while (izq < der && (ratones.get(der).getDn().isAfter(pivot.getDn())
                    || ratones.get(der).getDn().isEqual(pivot.getDn()))) { // Busque un valor más pequeño que el pivot
                der--;
            }
            intercambiador(ratones, izq, der); // Se ordenan los valores encontrados
        }
        if (ratones.get(izq).getDn().isAfter(ratones.get(max).getDn())) {
            intercambiador(ratones, izq, max);
        } else {
            izq = max;
        }

        // intercambiador(ratones, izq, max);
        return izq;
    }

    /**
     * Inverte dos elementos de un ArrayList de ratones
     */
    private static void intercambiador(ArrayList<Raton> ratones, int i, int j) {
        Raton temp = ratones.get(i);
        ratones.set(i, ratones.get(j));
        ratones.set(j, temp);
    }

    /**
     * 6. Elimina un ratón de una población indicando su código de referencia si
     * no se encuentra, se lanza la excepción "RatonNoEncontrado"
     *
     * @param codigo
     *
     */
    public void eliminaRaton(int codigo) throws RatonNoEncontrado {
        boolean find = false;
        Raton r = getRaton(codigo);
        if (r != null) {
            if (r.getSexo() == Sexo.MASCULINO) {
                ratonesMachos.remove(getRaton(codigo));
                return;
            } else {
                ratonesHembras.remove(getRaton(codigo));
                return;
            }
        }
        throw new RatonNoEncontrado();
    }

    /**
     * Busca en la población el ratón con el código especificado, si no se
     * encuentra, se devuelve null
     *
     * @param codigo
     * @return raton
     */
    public Raton getRaton(int codigo) {
        Iterator<Raton> raton = ratonesMachos.iterator();
        Raton r;
        while (raton.hasNext()) {
            r = raton.next();
            if (r.getCod() == codigo) {
                return r;
            }
        }
        raton = ratonesHembras.iterator();
        while (raton.hasNext()) {
            r = raton.next();
            if (r.getCod() == codigo) {
                return r;
            }
        }
        return null;
    }

    /**
     * 8. Ver información detallada de un ratón, habiendo especificado previamente
     * su código de referencia.
     *
     * @param codigo del Raton
     * @return Strnig con las Info de un raton
     *
     */
    public String infoRaton(int codigo) {
        // INNECESARIO
        Raton raton = getRaton(codigo);
        if (raton != null) {
            return raton.toString() + " Esteril: " + raton.esEsteril() + ", Poligamo: " + raton.esPoligamo();
        }
        return "";
    }

    /**
     * Genera una fecha casual entre 01/01/2017 y el 31/12/2022
     *
     * @return Fecha casual en formato LocalDate
     */
    private static LocalDate generaFechaCasual() {
        long minFecha = LocalDate.of(2017, 1, 1).toEpochDay();
        long maxFecha = LocalDate.of(2022, 12, 31).toEpochDay();
        long fechaCasual = ThreadLocalRandom.current().nextLong(minFecha, maxFecha);
        return LocalDate.ofEpochDay(fechaCasual);
    }

    /**
     * genera familias basadas en los ratones presentes en la población
     */
    public void creaFamilias() {
        int nMachos = ratonesMachos.size() - 1;
        int nHembras = ratonesHembras.size() - 1;
        int random;
        while (nMachos != -1 && nHembras != -1) {
            if (ratonesMachos.get(nMachos).esPoligamo()) {
                Familia familia = (ratonesMachos.get(nMachos).esEsteril())
                        ? new FamiliaMachoEsteril(ratonesMachos.get(nMachos))
                        : new FamiliaPoligamica(ratonesMachos.get(nMachos));
                ratonesMachos.remove(nMachos--);
                familias.add(familia);
                do {
                    familia.anadirMadre(ratonesHembras.get(nHembras));
                    ratonesHembras.remove(nHembras--);
                    random = new Random().nextInt(10);
                } while (random < 5 && nHembras != -1);
            } else {
                familias.add(new FamiliaNormal(ratonesMachos.get(nMachos), ratonesHembras.get(nHembras)));
                ratonesMachos.remove(nMachos--);
                ratonesHembras.remove(nHembras--);
            }
        }
    }

    /**
     * Genera familias aleatorias con los ratones de la población
     *
     * @throws NegativeInputExeption
     */
    public LinkedList<Raton> procrearFamilias(LocalDate fecha) throws NegativeInputExeption {
        LinkedList<Raton> hijos = new LinkedList<>();
        Iterator<Familia> iterator = familias.iterator();
        Raton padreCasual;
        while (iterator.hasNext()) {
            Familia familia = iterator.next();
            if (familia.getPadre().esEsteril()) {
                if (hayNoEsteriles()) {
                    do {
                        padreCasual = ratonesMachos.get(new Random().nextInt(ratonesMachos.size()));
                    } while (padreCasual.esEsteril() && hayNoEsteriles());
                    LinkedList<Raton> hijosFamilia = (LinkedList<Raton>) familia.procrear(fecha, padreCasual);
                    if (hijosFamilia != null) {
                        hijos.addAll(hijosFamilia);
                    }
                }
            } else {
                LinkedList<Raton> hijosFamilia = (LinkedList<Raton>) familia.procrear(fecha);
                if (hijosFamilia != null) {
                    hijos.addAll(hijosFamilia);
                }
            }
        }
        return hijos;
    }

    private boolean hayNoEsteriles() {
        for (Raton macho : ratonesMachos) {
            if (!macho.esEsteril()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Simula la evolución a lo largo del tiempo de la población, generando familias
     * y procreandolas
     *
     * @param fechaInicio procreacion
     * @return estadisticas sobre la simulacion
     * @throws NegativeInputExeption
     */
    public int[][] simulacionMontecarlo(LocalDate fechaInicio) throws NegativeInputExeption {
        int var = 0;
        int[][] estadisticas = new int[numDias / MULTIPLE_DAYS][9];
        LinkedList<Raton> hijos = new LinkedList<>();
        while (var != numDias) {
            estadisticas[var / MULTIPLE_DAYS] = getStatistic();
            Iterator<Raton> ratones = hijos.iterator();
            Raton hijo;
            while (ratones.hasNext()) {
                hijo = ratones.next();
                if (hijo.esMaduro(fechaInicio.plusDays(var))) {
                    anadirRaton(hijo);
                    ratones.remove();
                }
            }
            creaFamilias();
            hijos.addAll(procrearFamilias(fechaInicio.plusDays(var)));
            var += MULTIPLE_DAYS;
        }
        return estadisticas;
    }

    private int[] getStatistic() {
        int[] array = new int[9];
        array[0] = ratonesMachos.size() + ratonesHembras.size(); // número de ratones total de la población
        if (ratonesMachos.size() != 0) {
            int nMachos = ratonesMachos.size(); // numero de machos
            int mNormales = 0, nPoli = 0, nEst = 0;
            for (int i = 0; i < ratonesMachos.size(); i++) {
                Raton raton = ratonesMachos.get(i);
                if (!raton.esEsteril() && !raton.esPoligamo()) {
                    mNormales++;
                } else if (raton.esPoligamo()) {
                    nPoli++;
                } else if (raton.esEsteril()) {
                    nEst++;
                }
            }
            array[1] = nMachos * 100 / array[0]; // porcentaje de machos
            array[3] = mNormales * 100 / array[0]; // porcentaje de machos normales
            array[4] = nPoli * 100 / array[0]; // porcentaje de machos poligamos
            array[5] = nEst * 100 / array[0]; // porcentaje de machos esteriels
            System.out.println(
                    "nMachos: " + nMachos + ", mNormales: " + mNormales + ", nPoli: " + nPoli + ", nEst: " + nEst);
        }
        if (ratonesHembras.size() != 0) {
            int nHembras = ratonesHembras.size();
            int hNorm = 0, hCasiEst = 0, hEst = 0;
            for (int i = 0; i < ratonesHembras.size(); i++) {
                Raton raton = ratonesHembras.get(i);
                if (!raton.getC1().getMutado() && !raton.getC2().getMutado()) {
                    hNorm++;
                } else if (!raton.esEsteril() && (raton.getC1().getMutado() || raton.getC2().getMutado())) {
                    hCasiEst++;
                } else if (raton.esEsteril()) {
                    hEst++;
                }
            }
            array[2] = nHembras * 100 / array[0]; // porcentaje de hembras
            array[6] = hNorm * 100 / array[0]; // porcentaje de hembras normales
            array[7] = hCasiEst * 100 / array[0]; // porcentaje de hembras que tienen un gen de esterilidad, pero son
            // fértiles
            array[8] = hEst * 100 / array[0]; // porcentaje de hembras esteriels
        }
        return array;
    }

    @Override
    public String toString() {
        String s = "";
        String f = "";
        for (Raton raton : ratonesMachos) {
            s += "\t\t" + raton.toString();
        }
        for (Raton raton : ratonesHembras) {
            s += "\t\t" + raton.toString();
        }
        for (Familia familia : familias) {
            f += "\t\t" + familia.toString();
        }
        return "Poblacion:" + "\n\tnombre='" + nombre + '\'' + "\n\tresponsable='" + responsable + '\'' + "\n\tnumDias="
                + numDias + "\n\tratones:\n" + s + "\tfamilias:\n" + f;
    }

}
