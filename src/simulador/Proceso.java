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
    private int instEjecutadas;

    public Proceso(String nombre, int id, int espacio) {
        this.nombre = nombre;
        this.id = id;
        this.espacio = espacio;
        this.instTotales = (int) (Math.random() * 20) + 10;
        this.instEjecutadas = 0;
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

    public void setInstTotales(int instTotales) {
        this.instTotales = instTotales;
    }

    public int getInstEjecutadas() {
        return instEjecutadas;
    }

    public void setInstEjecutadas(int instEjecutadas) {
        this.instEjecutadas = instEjecutadas;
    }
}
