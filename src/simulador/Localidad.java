package simulador;

/**
 * Clase que simula una localidad de memoria.
 *
 * @author J Alfonso Martínez Baeza
 * @version 1.0.19102020
 */
public class Localidad {
    /**
     * Variable que indica si se ha visitado la localidad.
     */
    private boolean visitada;
    /**
     * Variable que indica si una localidad contiene información o no.
     */
    private boolean ocupada;
    /**
     * Guarda una "X" si la localidad está ocupada y una cadena vacía en caso contrario.
     */
    private char contenido;

    /**
     * Cada nueva localidad se inicializa como no visitada, no ocupada y sin ningún contenido.
     */
    public Localidad(){
        visitada = false;
        ocupada = false;
        contenido = '-';
    }

    /**
     * Método getter de la variable visitada.
     * @return Regresa "true" o "false"
     */
    public boolean isVisitada() {
        return visitada;
    }

    /**
     * Método setter de la variable visitada.
     * @param visitada Recibe el nuevo valor que tendrá dicha variable.
     */
    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    /**
     * Método getter de la variable ocupada.
     * @return Regresa "true" o "false"
     */
    public boolean isOcupada() {
        return ocupada;
    }

    /**
     * Método setter de la variable ocupada.
     * @param ocupada Recibe el nuevo valor que tendrá dicha variable.
     */
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    /**
     * Método getter del contenido de la localidad de memoria.
     * @return Devuelve una X si esta ocupada y un espacio en blanco en caso contrario.
     */
    public char getContenido() {
        return contenido;
    }

    /**
     * Método setter del contenido de la localidad.
     * @param contenido Recibe el nuevo contenido de la localidad de memoria.
     */
    public void setContenido(char contenido) {
        this.contenido = contenido;
    }
}
