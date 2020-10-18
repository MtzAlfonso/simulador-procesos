package simulador;

/**
 * Clase que simula la memoria y sus operaciones.
 *
 * @author J Alfonso Martinez Baeza
 * @version 2.1.17102020
 */
public class Memoria {

    /**
     * Memoria total, su valor no cambiará a lo largo de la ejecución.
     */
    public int memoriaTotal;
    /**
     * Variable que nos indica la cantidad de memoria que tenemos disponible para crear nuevos procesos.
     */
    public int memoriaDisponible;
    /**
     * Arreglo que simula las localidades de memoria.
     */
    public Localidad[] tablaMemoria;

    /**
     * Al intanciarse se declara el valor fijo de la memoria total y se hace una copia en la variable de memoria
     * disponible, tambien se llena la tabla de memoria con instancias de la clase Localidad.
     */
    public Memoria() {
        this.memoriaTotal = 2048;
        this.memoriaDisponible = this.memoriaTotal;
        this.tablaMemoria = new Localidad[this.memoriaTotal];
        for (int i = 0; i < this.memoriaTotal; i++) {
            Localidad l = new Localidad();
            this.tablaMemoria[i] = l;
        }
    }

    /**
     * Se encarga de elegir de manera aleatoria un número dentro del conjunto [16,32,64,128].
     *
     * @param n variable de tipo entero que servirá de switch para elegir de manera aleatoria la cantidad de memoria
     *          que ocupará cada proceso.
     * @return regresa 16, 32, 64 o 128 en funcion del parámetro recibido.
     */
    public int asignarMemoria(int n) {
        int num = switch (n) {
            case 0 -> 16;
            case 1 -> 32;
            case 2 -> 64;
            default -> 128;
        };
        return num;
    }

    /**
     * Se encarga de reasignar a la memoria principal el espacio utilizado una vez que el proceso finaliza, marca las
     * localidades como disponibles y cambia su contenido a una cadena vacía.
     *
     * @param p Proceso actual.
     */
    public void liberarMemoria(Proceso p) {
        this.memoriaDisponible += p.getEspacio();
        for (int i = p.getInicio(); i < p.getFin(); i++) {
            this.tablaMemoria[i].contenido = "";
            this.tablaMemoria[i].setOcupada(false);
        }
    }

    /**
     * Se encarga de colocar una "X" en el contenido de la localidad y marca las localidades como ocupadas.
     *
     * @param p Proceso actual.
     */
    public void llenarMemoria(Proceso p) {
        for (int i = p.getInicio(); i < p.getFin(); i++) {
            this.tablaMemoria[i].contenido = "X";
            this.tablaMemoria[i].setOcupada(true);
        }
    }
}
