package simulador;

/**
 * Clase que simula un proceso.
 *
 * @author J Alfonso Martínez Baeza
 * @version 2.1.19102020
 */
public class Proceso {
    /**
     * Id único para cada proceso.
     */
    private final int id;
    /**
     * Localidades que ocupa el proceso.
     */
    private final int espacio;
    /**
     * Nombre del proceso.
     */
    private final String nombre;
    /**
     * Instrucciones totales que puede ejecutar el proceso.
     */
    private final int instTotales;
    /**
     * Variable calculada de las instrucciones que faltan por ejecutar.
     */
    private int instRestantes;
    /**
     * Variable calculada de las instrucciones que ha ejecutado cada proceso.
     */
    private int instEjecutadas;
    /**
     * Localidad inicial.
     */
    private final int inicio;
    /**
     * Localidad final.
     */
    private final int fin;

    /**
     * Constructor de la clase
     *
     * @param nombre  Nombre del proceso.
     * @param id      Id único.
     * @param espacio Localidades de memoria requeridas.
     * @param inicio  Localidad inicial.
     * @param fin     Localidad final.
     */
    public Proceso(String nombre, int id, int espacio, int inicio, int fin) {
        this.nombre = nombre;
        this.id = id;
        this.espacio = espacio;
        this.instTotales = (int) (Math.random() * 20) + 10;
        this.instRestantes = this.instTotales;
        this.instEjecutadas = 0;
        this.inicio = inicio;
        this.fin = fin;
    }

    /**
     * Método getter de la variable id.
     *
     * @return Regresa el valor de la variable id.
     */
    public int getId() {
        return id;
    }

    /**
     * Método getter de la variable espacio.
     *
     * @return Regresa las localidades de memoria que ocupa.
     */
    public int getEspacio() {
        return espacio;
    }

    /**
     * Método getter de la variable nombre.
     *
     * @return Regresa el nombre del proceso.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método getter de la variable instTotales.
     *
     * @return Regresa el número de instrucciones que contiene un proceso.
     */
    public int getInstTotales() {
        return instTotales;
    }

    /**
     * Método getter de la variable instRestantes.
     *
     * @return Regresa el número de instrucciones que faltan por ejecutar.
     */
    public int getInstRestantes() {
        return instRestantes;
    }

    /**
     * Método setter de la variable instRestantes.
     *
     * @param instRestantes Recibe el nuevo valor de las instrucciones que faltan por ejecutarse.
     */
    public void setInstRestantes(int instRestantes) {
        this.instRestantes = instRestantes;
    }

    /**
     * Método getter de la variable instEjecutadas.
     *
     * @return Regresa el número de instrucciones que ha ejecutado el proceso.
     */
    public int getInstEjecutadas() {
        return instEjecutadas;
    }

    /**
     * Método setter de la variable instRestantes.
     *
     * @param instEjecutadas Recibe el nuevo valor de las instrucciones que se han ejecutado.
     */
    public void setInstEjecutadas(int instEjecutadas) {
        this.instEjecutadas = instEjecutadas;
    }

    /**
     * Método getter de la variable inicio.
     *
     * @return Regresa la localidad inicial que ocupa el proceso.
     */
    public int getInicio() {
        return inicio;
    }

    /**
     * Método getter de la variable fin.
     *
     * @return Regresa la localidad final que ocupa el proceso.
     */
    public int getFin() {
        return fin;
    }
}
