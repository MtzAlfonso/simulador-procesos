package simulador;

/**
 * Clase para generar nuevos procesos.
 *
 * @author J Alfonso Martinez Baeza
 * @version 1.0.10102020
 */
public class Proceso {
    private int id;
    private int espacio;
    private String nombre;
    private int instTotales;
    private int instRestantes;
    private int instEjecutadas;
    private int inicio;
    private int fin;

    public Proceso(String nombre, int id, int espacio) {
        this.nombre = nombre;
        this.id = id;
        this.espacio = espacio;
        this.instTotales = (int) (Math.random() * 20) + 10;
        this.instRestantes = this.instTotales;
        this.instEjecutadas = 0;
    }

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

    public int getId() {
        return id;
    }

    public int getEspacio() {
        return espacio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getInstTotales() {
        return instTotales;
    }

    public int getInstRestantes() {
        return instRestantes;
    }

    public void setInstRestantes(int instRestantes) {
        this.instRestantes = instRestantes;
    }

    public int getInstEjecutadas() {
        return instEjecutadas;
    }

    public void setInstEjecutadas(int instEjecutadas) {
        this.instEjecutadas = instEjecutadas;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFin() {
        return fin;
    }
}
