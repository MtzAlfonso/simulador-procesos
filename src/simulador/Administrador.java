package simulador;

import java.util.*;

/**
 * Clase encargada de simular el administrador de procesos.
 *
 * @author J Alfonso Martinez Baeza
 * @version 1.0.10102020
 */
public class Administrador {

    /**
     * Cola de procesos listos.
     */
    private Queue<Proceso> colaProcesos;
    /**
     * Lista de procesos terminados exitosamente.
     */
    private ArrayList<Proceso> terminadosConExito;
    /**
     * Lista de procesos eliminados antes de terminar.
     */
    private ArrayList<Proceso> procesosEliminados;
    /**
     * Instancia de la clase Memoria
     */
    private Memoria memoria;
    /**
     * Variable que asignará un id único a cada nuevo proceso.
     */
    private int id = (int) (Math.random() * 2000) + 1000;


    /**
     * Constructor de la clase.
     */
    public Administrador() {
        this.colaProcesos = new LinkedList<>();
        this.terminadosConExito = new ArrayList<>();
        this.procesosEliminados = new ArrayList<>();
        this.memoria = new Memoria();
    }

    /**
     * Verifica que haya memoria disponible en funcion de la cantidad de memoria requerida por
     * el nuevo proceso, en caso de que la memoria no alcance se muestra un mensaje en pantalla indicando cuanto se
     * requiere y cuanto resta.
     *
     * @param sc permite al usuario ingresar el nombre del nuevo proceso creado
     */
    public void crearProceso(Scanner sc) {
        int n = this.memoria.asignarMemoria((int) (Math.random() * 4));

        if (this.memoria.memoriaDisponible >= n) {
            System.out.print("\nNombre del proceso: ");
            this.addProcess(new Proceso(sc.next(), id++, n));
        } else {
            System.out.println("\nNo hay memoria suficiente");
            System.out.println("Memoria requerida:\t" + n);
            System.out.println("Memoria disponible:\t" + this.memoria.memoriaDisponible);
            System.out.println("Libere memoria ejecutando o matando procesos");
        }
    }

    /**
     * Función auxiliar para la creación de procesos, recibe un objeto Proceso, le asigna memoria de manera aleatoria y
     * lo encola en la cola de procesos.
     *
     * @param proceso instancia que será encolada en la cola de procesos
     */
    public void addProcess(Proceso proceso) {
        this.colaProcesos.add(proceso);
        this.memoria.memoriaDisponible -= proceso.getEspacio();
    }

    /**
     * Imprime en pantalla la información de los procesos listos.
     */
    public void verEstadoActual() {
        System.out.println("\nProcesos en espera de ser ejecutados:\t" + this.colaProcesos.size());
        System.out.println("Procesos terminados exitosamente:\t\t" + this.terminadosConExito.size());
        for (Proceso p : terminadosConExito) {
            System.out.println("\t" + p.getNombre());
        }
        System.out.println("Procesos terminados antes de tiempo:\t" + this.procesosEliminados.size());
        for (Proceso p : procesosEliminados) {
            System.out.println("\t" + p.getNombre());
        }
        System.out.println("Memoria ocupada:\t" + (this.memoria.memoriaTotal - this.memoria.memoriaDisponible));
        System.out.println("Memoria disponible:\t" + this.memoria.memoriaDisponible);
    }

    /**
     * Muestra las localidades ocupadas por cada proceso.
     */
    public void verEstadoMemoria(){
        System.out.println("\nMemoria:\n");
    }


    /**
     * Imprime en pantalla el nombre de los procesos en espera.
     */
    public void verColaDeProcesos() {
        if (this.colaProcesos.size() != 0) {
            System.out.println("\nProcesos listos:\n");
            System.out.println("\tId\t\t|\tNombre");
            System.out.println("\t--------|--------------");
            for (Proceso p : colaProcesos) {
                System.out.print("\t" + p.getId() + "\t|");
                if (p == this.colaProcesos.element()) {
                    System.out.print("\t" + p.getNombre() + " *");
                } else {
                    System.out.print("\t" + p.getNombre());
                }
                System.out.println("\n\t--------|--------------");
            }
            System.out.println("\n\t* Proceso activo");
        } else {
            System.out.println("\nNo hay procesos en espera");
        }
    }

    /**
     * Ejecuta 5 instrucciones del proceso, después de esas 5 instrucciones automáticamente lo coloca al final de la
     * cola de listos y si al ejecutar esas 5 instrucciones llega a su finalización muestra un mensaeje de
     * terminado y libera la memoria
     */
    public void ejecutarProcesoActual() {
        if (this.colaProcesos.size() > 0) {
            Proceso p = this.colaProcesos.poll();
            int restantes = p.getInstRestantes();
            int ejecutadas = p.getInstEjecutadas();
            if (restantes < 5) {
                p.setInstEjecutadas(ejecutadas + restantes);
                p.setInstRestantes(0);
            } else {
                p.setInstEjecutadas(ejecutadas + 5);
                p.setInstRestantes(restantes - 5);
            }
            System.out.println("\nProceso " + p.getNombre() + " en ejecución");
            System.out.println(p.getInstEjecutadas()+"/"+p.getInstTotales()+" instrucciones ejecutadas");
            if (p.getInstRestantes() == 0) {
                System.out.println("\nProceso " + p.getNombre() + " finalizado con éxito");
                System.out.println("Se liberaron " + p.getEspacio() + " localidades de memoria");
                this.memoria.liberarMemoria(p);
                this.terminadosConExito.add(p);
            } else {
                this.colaProcesos.add(p);
            }
        } else {
            System.out.println("\nNo hay procesos en espera");
        }
    }

    /**
     * Imprime en pantalla la información del proceso más próximo a ser ejecutado.
     */
    public void verProcesoActual() {
        if (this.colaProcesos.size() > 0) {
            Proceso p = this.colaProcesos.element();
            System.out.println("\nId:\t\t\t" + p.getId());
            System.out.println("Nombre:\t\t" + p.getNombre());
            System.out.println("\nInstrucciones totales:\t\t" + p.getInstTotales());
            System.out.println("Instrucciones ejecutadas:\t" + p.getInstEjecutadas());
            System.out.println("\nMemoria utilizada:\t" + p.getEspacio());
        } else {
            System.out.println("\nNo hay procesos en espera");
        }
    }

    /**
     * Envía el proceso actual al final de la cola sin ejecutar instrucciones y coloca el siguiente como activo
     */
    public void omitirProcesoActual() {
        if (this.colaProcesos.size() != 0) {
            Proceso p = this.colaProcesos.poll();
            System.out.println("\nOmitiendo proceso " + p.getNombre());
            this.colaProcesos.add(p);
        } else {
            System.out.println("\nNo hay procesos en espera");
        }
    }

    /**
     * Termina el proceso actual y libera la memoria que utilizaba. En caso de que la cola de procesos esté vacía
     * muestra un mensaje indicando que no hay procesos en espera.
     */
    public void matarProcesoActual() {
        if (this.colaProcesos.size() > 0) {
            Proceso p = this.colaProcesos.poll();
            System.out.println("\nMatando proceso " + p.getNombre());
            System.out.println("Se liberaron " + p.getEspacio() + " localidades de memoria");
            this.memoria.liberarMemoria(p);
            this.procesosEliminados.add(p);
        } else {
            System.out.println("\nNo hay procesos en espera");
        }
    }

    /**
     * Finaliza el programa y muestra información de los procesos que había en espera.
     */
    public void matarTodoYTerminar() {
        System.out.println("\nFinalizando procesos...\n");
        for (Proceso p : colaProcesos) {
            System.out.println("Nombre:\t" + p.getNombre());
            System.out.println("Memoria ocupada:\t" + p.getEspacio());
            System.out.println();
        }
    }


}
