package simulador;

/**
 * Clase que simula una localidad de memoria.
 *
 * @author J Alfonso Martinez Baeza
 * @version 1.0.17102020
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
    public String contenido;

    /**
     * Cada nueva localidad se inicializa como no visitada, no ocupada y sin ningun contenido.
     */
    public Localidad(){
        this.visitada = false;
        this.ocupada = false;
        this.contenido = "";
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
}
