# Management-Animal-Farm

The "Algorithms and Data Structures" project is based on an animal facility where transgenic mice are bred, reflecting the characteristics described in the "ProjectRules.pdf" document.

Analysis and description of the application: The application has been divided into four packages:
animalario: This package handles the management of Java files related to the main entities of the project. All attributes are marked as "private" to prevent direct external access and can only be accessed through the established methods of the classes.

**The classes are as follows:**
- **Raton (Mouse):** Represents the mouse object with attributes such as code, date of birth, weight, sex, temperature, description, chromosome 1, and chromosome 2. The class allows external reading for all attributes, and modification is only allowed for weight, temperature, and chromosome mutations. It includes three functions:
    - esEsteril: Verifies the sterility of the mouse by returning a boolean value.
    - esPolygamous: Checks if the mouse is polygamous by returning a boolean value.
    - esMaduro: Checks if the mouse is mature (age > 45 days) based on the provided date and returns a boolean value.

- **Población (Population):** Represents a population of mice with attributes such as name, administrator, number of days in the animal facility, male mouse matrix, female mouse matrix, and family matrix. All attributes can be read externally. The class includes the following functions:
    - anadirRaton: Adds a new mouse to an existing population.
    - anadirFamilia: Adds a family to the family matrix if it is not null.
    - ordenaAlfabeticamente: Sorts a list of mice alphabetically (by code).
    - ordenaPorFecha: Sorts a list of mice by birth date.
    - ordenaPorPeso: Sorts a list of mice by weight in descending order.
    - getCodigosRatones: Lists all the codes of mice in a population.
    - eliminarRaton: Removes a mouse from a population based on its reference code.
    - creaFamilias: Generates families based on the mice present in the population.
    - procrearFamilias: Generates random families using the mice from the population.

- **Familia (abstract):** Represents a family of mice with attributes such as father and a matrix of children. All attributes can be read externally. The class includes the following functions:
    - procrear: Generates offspring (mice) causally based on the father mouse and the mother mouse.
    - anadirMadre: Abstract method to add a mother mouse to the family.
    - anadirHijo: Adds a child mouse to the family.

- FamiliaMachoEsteril: This object extends the Familia class, inheriting its attributes and methods.

- FamiliaNormal: This object extends the Familia class, inheriting its attributes and methods.

- FamiliaPoligamica: This object extends the Familia class, inheriting its attributes and methods.

- **Cromosoma (Chromosome):** Represents a chromosome object of a mouse with attributes such as Chromosome Type and mutation. All attributes can be externally read, while writing is not possible for the Chromosome Type attribute.

- TipoCromosoma (ChromosomeType): Represents an Enum object that can have values X or Y.

- Sexo (Sex): Represents an Enum object that can have values MASCULINO (male) or FEMENINO (female).

**auxiliar:** Contains the "FuncionesAuxiliares" class where functions that interact with the user and do not directly belong to an object are stored for organizational purposes. These functions include:
- abrirArchivo: Opens the specified file as a parameter and returns the population of mice stored within.
- guardarArchivo: Saves a population in an already opened file.
- guardarArchivoComo: Saves a population in a new file.
- editar

**Raton:** Modifies the parameters of a mouse.
- creaPoblacion: Creates a population of mice.
- creaRaton: Creates a new mouse.
- creaPoblacionVirtual: Creates a population based on the provided data.
- imprimirEstadisticas: Converts an integer statistics matrix into a string.
- listarRatonesOrdenados: Asks in which order to sort the mice in the given population and returns the sorted list.

main: Contains only the Main class where a practical menu is displayed to interact with the application.

**Exceptions:**
The package contains custom exceptions specifically created for handling application errors. These include:
- NumDiasException: Error that may occur when trying to modify the "days in the animal facility" attribute of the Population class, if an unacceptable value is entered (negative number or greater than 270).
- RatonNoEncontrado: Error that may occur when attempting to remove a mouse with a nonexistent code in the "eliminaRaton" method of the Population class.
- NegativeInputExeption: Error that occurs when a negative number is entered in a field where a positive value is required.

**Design decisions:**
The application was developed based on the UML design of the project, which led to the decision of using 9 entities, one of which has already been implemented in Java by the java.time.LocalDate class. All entities have been inserted into the animalario package and are connected as represented in the UML diagram. Specifically, LocalDate, Sexo, and Chromosome are attributes of the Raton class. ChromosomeType is an attribute of Chromosome, while Raton is used to create a matrix corresponding to an attribute of Población. The types of Familias are attributes of the Poblacion object.

**Integrity checks and exceptions:**
The main checks present in the application are related to input data from the terminal. There is always a try-catch control for input errors (InputMismatchException). Additionally, there are controls for the proper compilation and modification of objects, implementing both predefined Java exceptions and custom classes for specific error types. Examples include NumDiasException and RatonNoEncontrado, whose specifications are described in the Exceptions section. Other exceptions implemented in the project include NullPointerException, NegativeInputExeption, and ClassNotFoundException to handle additional functions.

**Sorting and searching techniques:**
For sorting, the quickSort algorithm was used, which can efficiently sort many objects and is considered the most efficient algorithm for this purpose.

**UML:**![IMG_0163](https://github.com/Angelo-De-Nadai/Management-Animal-Farm/assets/80247207/dba30f62-b556-4678-8b79-e9b7674ce8b2)

**Conclusions:**
The project consumed a lot of my time; I worked 5 to 10 hours every day for a week. I invested a significant amount of time in planning, developing sorting algorithms, file saving, and debugging. Overall, I am satisfied with the final product obtained.
