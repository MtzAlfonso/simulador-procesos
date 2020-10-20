package simulador;

/**
 * Clase que simula la memoria y sus operaciones.
 *
 * @author J Alfonso Martínez Baeza
 * @version 2.1.19102020
 */
public class Memoria {

    /**
     * Memoria total, su valor no cambiará a lo largo de la ejecución.
     */
    protected int memoriaTotal;
    /**
     * Variable que nos indica la cantidad de memoria que tenemos disponible para crear nuevos procesos.
     */
    protected int memoriaDisponible;
    /**
     * Arreglo que simula las localidades de memoria.
     */
    private final Localidad[] tablaMemoria;

    /**
     * Al crearse se declara el valor fijo de la memoria total y se hace una copia en la variable de memoria
     * disponible, también se llena la tabla de memoria con instancias de la clase Localidad.
     */
    public Memoria() {
        memoriaTotal = 2048;
        memoriaDisponible = memoriaTotal;
        tablaMemoria = new Localidad[memoriaTotal];
        for (int i = 0; i < memoriaTotal; i++) {
            Localidad loc = new Localidad();
            tablaMemoria[i] = loc;
        }
    }

    /**
     * Se encarga de elegir de manera aleatoria un número dentro del conjunto [16,32,64,128].
     *
     * @param n variable de tipo entero que servirá de switch para elegir de manera aleatoria la cantidad de memoria
     *          que ocupará cada proceso.
     * @return regresa 16, 32, 64 o 128 en función del parámetro recibido.
     */
    public int asignarMemoria(int n) {
        return switch (n) {
            case 0 -> 16;
            case 1 -> 32;
            case 2 -> 64;
            default -> 128;
        };
    }

    /**
     * Se encarga de reasignar a la memoria principal el espacio utilizado una vez que el proceso finaliza, marca las
     * localidades como disponibles y cambia su contenido a una cadena vacía.
     *
     * @param p Proceso actual.
     */
    public void liberarMemoria(Proceso p) {
        memoriaDisponible += p.getEspacio();
        for (int i = p.getInicio(); i < p.getFin(); i++) {
            tablaMemoria[i].setContenido("");
            tablaMemoria[i].setOcupada(false);
        }
    }

    /**
     * Se encarga de colocar una "X" en el contenido de la localidad y marca las localidades como ocupadas.
     *
     * @param p Proceso actual.
     */
    public void llenarMemoria(Proceso p) {
        for (int i = p.getInicio(); i < p.getFin(); i++) {
            tablaMemoria[i].setContenido("X");
            tablaMemoria[i].setOcupada(true);
        }
    }

    /**
     * Método getter del arreglo de localidades de memoria.
     *
     * @return Devuelve el arreglo de localidades.
     */
    public Localidad[] getTablaMemoria() {
        return tablaMemoria;
    }
}
