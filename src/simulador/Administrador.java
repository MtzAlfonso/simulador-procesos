package simulador;

import java.lang.reflect.Array;
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
     * Variable que indica la última localidad de memoria utilizada por el programa.
     */
    private int actual;

    private boolean sePuedeCrearProceso;

    /**
     * Constructor de la clase.
     */
    public Administrador() {
        this.colaProcesos = new LinkedList<>();
        this.terminadosConExito = new ArrayList<>();
        this.procesosEliminados = new ArrayList<>();
        this.memoria = new Memoria();
        this.actual = 0;
        this.sePuedeCrearProceso = true;
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
            if (actual + 1 >= this.memoria.tablaMemoria.length) {
                actual = 0;
            }
            // Ciclo para buscar localidades vacias, es decir, si su valor es 0.
            while (this.memoria.tablaMemoria[actual].contenido != 0 || !this.sePuedeCrear(actual, actual + n)) {
                /*
                Mientras no estemos en la ultima localidad iremos recorriendo el arreglo hasta encontrar un 0
                 */
                if (this.memoria.tablaMemoria[actual].isVisitada()) {
                    this.sePuedeCrearProceso = false;
                    for (Localidad l : this.memoria.tablaMemoria) {
                        l.setVisitada(false);
                    }
                    break;
                }
                this.memoria.tablaMemoria[actual].setVisitada(true);
                if (actual + 1 < this.memoria.tablaMemoria.length) {
                    actual++;
                } else {
                    actual = 0;
                }
            }
            if (this.sePuedeCrearProceso) {
                System.out.print("\nNombre del proceso: ");
                // Se modificó el constructor para recibir como parametros la primera y la ultima localidad de memoria.
                this.addProcess(new Proceso(sc.next(), id++, n, actual, actual + n));
                actual += n;
            } else {
                System.out.println("No hay memoria contigua... intente nuevamente");
                this.sePuedeCrearProceso = true;
            }
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
        //
        this.memoria.llenarMemoria(proceso);
        //
    }

    public boolean sePuedeCrear(int inicio, int fin) {
        for (int i = inicio; i < fin; i++) {
            try {
                if (this.memoria.tablaMemoria[i].contenido != 0) {
                    return false;
                }
            } catch (Exception e){
                return false;
            }
        }
        return true;
    }

    /**
     * Imprime en pantalla la información de los procesos listos.
     */
    public void verEstadoActual() {
        System.out.printf("\nProcesos en espera de ser ejecutados: %6d", this.colaProcesos.size());
        System.out.printf("\nProcesos terminados exitosamente: %10d", this.terminadosConExito.size());
        for (Proceso p : terminadosConExito) {
            System.out.print("\n\t" + p.getNombre());
        }
        System.out.printf("\nProcesos terminados antes de tiempo: %7d", this.procesosEliminados.size());
        for (Proceso p : procesosEliminados) {
            System.out.print("\n\t" + p.getNombre());
        }
        System.out.printf("\nMemoria ocupada: %11d", (this.memoria.memoriaTotal - this.memoria.memoriaDisponible));
        System.out.printf("\nMemoria disponible: %8d\n", this.memoria.memoriaDisponible);
    }

    /**
     * Muestra las localidades ocupadas por cada proceso.
     */
    public void verEstadoMemoria() {
        System.out.println("\nTabla de localidades de memoria:");
        for (int i = 0; i < this.memoria.tablaMemoria.length; i++) {
            if (i % 8 == 0) {
                System.out.println();
            }
            System.out.printf("%5d | %5d |", i + 1, this.memoria.tablaMemoria[i].contenido);
        }
        System.out.println("\n\nLocalidades ocupadas por procesos:");
        System.out.printf(" Memoria | Inicio |  Fin  |  Proceso");
        for (Proceso p : this.colaProcesos) {
            System.out.printf("\n %7d | %6d | %5d | %10s ", p.getEspacio(), p.getInicio() + 1, p.getFin(), p.getNombre());
        }
        System.out.println();
    }


    /**
     * Imprime en pantalla el nombre de los procesos en espera.
     */
    public void verColaDeProcesos() {
        if (this.colaProcesos.size() != 0) {
            System.out.println("\nProcesos listos:\n");
            System.out.println("\tId\t|\tNombre");
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
            System.out.println(p.getInstEjecutadas() + "/" + p.getInstTotales() + " instrucciones ejecutadas");
            if (p.getInstRestantes() == 0) {
                System.out.println("\nProceso " + p.getNombre() + " finalizado con éxito");
                System.out.println("Se liberaron " + p.getEspacio() + " localidades de memoria");
                this.memoria.liberarMemoria(p);
                this.actual = p.getInicio();
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
            System.out.printf("\nId:%34d", p.getId());
            System.out.printf("\nNombre:%30s", p.getNombre());
            System.out.printf("\nInstrucciones totales:%15d", p.getInstTotales());
            System.out.printf("\nInstrucciones ejecutadas:%12d", p.getInstEjecutadas());
            System.out.printf("\nMemoria utilizada:%19d", p.getEspacio());
            System.out.printf("\nLocalidad inicial:%19d", p.getInicio() + 1);
            System.out.printf("\nLocalidad final:%21d", p.getFin());
            System.out.println();
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
            Proceso q = this.colaProcesos.element();
            System.out.println("Proceso " + q.getNombre() + " listo para ejecutarse");
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
            this.actual = p.getInicio();
            this.procesosEliminados.add(p);
        } else {
            System.out.println("\nNo hay procesos en espera");
        }
    }

    /**
     * Finaliza el programa y muestra información de los procesos que había en espera.
     */
    public void matarTodoYTerminar() {
        System.out.println("\nFinalizando procesos...");
        while (this.colaProcesos.size() > 0) {
            //this.verProcesoActual();
            this.matarProcesoActual();
        }
    }
}
