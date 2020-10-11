package simulador;

/**
 * Clase encargada de simular el manejo de memoria.
 *
 * @author J Alfonso Martinez Baeza
 * @version 1.0.10102020
 */
public class Memoria {

    /**
     * Memoria total, su valor no cambiará a lo largo de la ejecución.
     */
    public final int memoriaTotal;
    /**
     * Variable que nos indica la cantidad de memoria que tenemos disponible para crear nuevos procesos.
     */
    public int memoriaDisponible;

    /**
     * Al intanciarse se declara el valor fijo de la memoria total y se hace una copia en la variable de memoria
     * disponible.
     */
    public Memoria() {
        this.memoriaTotal = 2048;
        this.memoriaDisponible = this.memoriaTotal;
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
     * Recibe el Proceso actual y se encarga de reasignar a la memoria principal el espacio utilizado una vez que el
     * proceso finaliza.
     *
     * @param p Proceso actual.
     */
    public void liberarMemoria(Proceso p) {
        this.memoriaDisponible += p.getEspacio();
    }

}
