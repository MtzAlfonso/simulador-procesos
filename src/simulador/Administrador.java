package simulador;

import java.util.*;

/**
 * Clase que simula el administrador de procesos.
 *
 * @author J Alfonso Martínez Baeza
 * @version 2.1.19102020
 */
public class Administrador {

    /**
     * Cola de procesos listos.
     */
    private final Queue<Proceso> colaProcesos;
    /**
     * Lista de procesos terminados exitosamente.
     */
    private final ArrayList<Proceso> terminadosCorrectamente;
    /**
     * Lista de procesos eliminados antes de terminar.
     */
    private final ArrayList<Proceso> procesosEliminados;
    /**
     * Instancia de la clase Memoria
     */
    private final Memoria memoria;
    /**
     * Variable que asignará un id único a cada nuevo proceso.
     */
    private int id;
    /**
     * Variable que indica la última localidad de memoria utilizada por el programa.
     */
    private int actual;
    /**
     * Variable para verificar si un proceso puede crearse.
     */
    private boolean sePuedeCrearProceso;

    /**
     * Constructor de la clase Administrador.
     */
    public Administrador() {
        id = (int) (Math.random() * 2000) + 1000;
        colaProcesos = new LinkedList<>();
        terminadosCorrectamente = new ArrayList<>();
        procesosEliminados = new ArrayList<>();
        memoria = new Memoria();
        actual = 0;
        sePuedeCrearProceso = true;
    }

    /**
     * Verifica que haya memoria disponible en función de la cantidad de memoria requerida por
     * el nuevo proceso, en caso de que la memoria no alcance se muestra un mensaje en pantalla indicando cuanto se
     * requiere y cuanto resta.
     *
     * @param sc permite al usuario ingresar el nombre del nuevo proceso creado
     */
    public void crearProceso(Scanner sc) {
        int n = memoria.asignarMemoria((int) (Math.random() * 4));

        if (memoria.memoriaDisponible >= n) {
            if (actual + 1 >= memoria.getTablaMemoria().length) {
                actual = 0;
            }
            // Ciclo para buscar localidades vacías.
            while (!sePuedeCrear(actual, actual + n)) {
                // Es necesario un condicional para poder salir del ciclo en caso de que haya memoria suficiente pero
                // no sea contigua, ya que de ser así, entraríamos en un loop infinito.
                if (memoria.getTablaMemoria()[actual].isVisitada()) {
                    // Antes de salir del ciclo volvemos a marcar las localidades como NO visitadas para poder agregar
                    // más procesos posteriormente y no tengamos problemas.
                    for (Localidad l : memoria.getTablaMemoria()) {
                        l.setVisitada(false);
                    }
                    // Guardamos un indicador de que el proceso no puede crearse
                    sePuedeCrearProceso = false;
                    break;
                }
                // En cada vuelta marcamos como visitada la localidad actual.
                memoria.getTablaMemoria()[actual].setVisitada(true);
                // Avanzamos un espacio mientras no estemos en la ultima localidad, pues de ser así regresaremos a la
                // posición 0 del arreglo de memoria.
                if (actual + 1 < memoria.getTablaMemoria().length) {
                    actual++;
                } else {
                    actual = 0;
                }
            }
            if (sePuedeCrearProceso) {
                System.out.print("\nNombre del proceso: ");
                addProcess(new Proceso(sc.next(), id++, n, actual, actual + n));
                System.out.println("Tamaño: " + n);
                System.out.println("Guardado en las localidades [" + (actual + 1) + " - " + (actual + n) + "]");
                actual += n;
            } else {
                System.out.println("\nNo hay memoria contigua");
                System.out.println("Memoria requerida:\t" + n);
                System.out.println("Memoria disponible:\t" + memoria.memoriaDisponible);
                System.out.println("\nRecomendaciones:");
                System.out.println("* Intente con un proceso más pequeño ");
                System.out.println("* Libere memoria ejecutando o matando procesos");
                sePuedeCrearProceso = true;
            }
        } else {
            System.out.println("\nNo hay memoria suficiente");
            System.out.println("Memoria requerida:\t" + n);
            System.out.println("Memoria disponible:\t" + memoria.memoriaDisponible);
            System.out.println("\nRecomendaciones:");
            System.out.println("* Libere memoria ejecutando o matando procesos");
        }
    }

    /**
     * Función auxiliar para la creación de procesos, recibe un objeto Proceso, le asigna memoria de manera aleatoria y
     * lo encola en la cola de procesos.
     *
     * @param proceso instancia que será encolada en la cola de procesos
     */
    public void addProcess(Proceso proceso) {
        colaProcesos.add(proceso);
        memoria.memoriaDisponible -= proceso.getEspacio();
        memoria.llenarMemoria(proceso);
    }

    /**
     * Función que verifica que las localidades requeridas por un proceso sean continuas.
     *
     * @param inicio Localidad inicial del proceso.
     * @param fin    Localidad final del proceso.
     * @return Regresa TRUE si son continuas y FALSE en caso contrario.
     */
    public boolean sePuedeCrear(int inicio, int fin) {
        for (int i = inicio; i < fin; i++) {
            try {
                if (memoria.getTablaMemoria()[i].isOcupada()) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Imprime en pantalla la información de los procesos listos.
     */
    public void verEstadoActual() {
        System.out.printf("\nProcesos en espera de ser ejecutados: %6d", colaProcesos.size());
        System.out.printf("\nProcesos terminados exitosamente: %10d", terminadosCorrectamente.size());
        for (Proceso p : terminadosCorrectamente) {
            System.out.print("\n\t" + p.getNombre());
        }
        System.out.printf("\nProcesos terminados antes de tiempo: %7d", procesosEliminados.size());
        for (Proceso p : procesosEliminados) {
            System.out.print("\n\t" + p.getNombre());
        }
        System.out.println();
    }

    /**
     * Muestra las localidades ocupadas por cada proceso.
     */
    public void verEstadoMemoria() {
        System.out.println("\nTabla de localidades de memoria:");
        System.out.println("[ ] Localidad vacía");
        System.out.println("[X] Localidad ocupada");

        for (int i = 0; i < memoria.getTablaMemoria().length; i++) {
            if (i % 16 == 0) {
                System.out.println();
            }
            System.out.printf("%4d ", i + 1);
            System.out.printf("[%1s] ", memoria.getTablaMemoria()[i].getContenido());
        }
        System.out.printf("\n\nMemoria ocupada: %11d", (memoria.memoriaTotal - memoria.memoriaDisponible));
        System.out.printf("\nMemoria disponible: %8d\n", memoria.memoriaDisponible);
        System.out.println("\nLocalidades ocupadas por procesos:");
        System.out.print("\nProceso         | Inicio |  Fin  | Memoria utilizada");
        System.out.print("\n----------------|--------|-------|-------------------");
        for (Proceso p : colaProcesos) {
            System.out.printf("\n%-15s | %6d | %5d |  %8d ", p.getNombre(), p.getInicio() + 1, p.getFin(), p.getEspacio());
        }
        System.out.println();
    }


    /**
     * Imprime en pantalla el nombre de los procesos en espera.
     */
    public void verColaDeProcesos() {
        if (colaProcesos.size() != 0) {
            System.out.println("\nProcesos listos:\n");
            System.out.println("   Id   |   Nombre");
            System.out.println("--------|------------------");
            for (Proceso p : colaProcesos) {
                System.out.printf("%6d  |", p.getId());
                if (p == colaProcesos.element()) {
                    System.out.printf(" %-15s *", p.getNombre());
                } else {
                    System.out.printf(" %-15s", p.getNombre());
                }
                //System.out.println("\n--------|--------------");
                System.out.println();
            }
            System.out.println("\n * Proceso activo");
        } else {
            System.out.println("\nNo hay procesos en espera");
        }
    }

    /**
     * Ejecuta 5 instrucciones del proceso, después de esas 5 instrucciones automáticamente lo coloca al final de la
     * cola de listos y si al ejecutar esas 5 instrucciones llega a su finalización muestra un mensaje de
     * terminado y libera la memoria
     */
    public void ejecutarProcesoActual() {
        if (colaProcesos.size() > 0) {
            Proceso p = colaProcesos.poll();
            int restantes = p.getInstRestantes();
            int ejecutadas = p.getInstEjecutadas();
            if (restantes < 5) {
                p.setInstEjecutadas(ejecutadas + restantes);
                p.setInstRestantes(0);
            } else {
                p.setInstEjecutadas(ejecutadas + 5);
                p.setInstRestantes(restantes - 5);
            }
            System.out.println("\nEjecutando proceso " + p.getNombre());
            System.out.println(p.getInstEjecutadas() + "/" + p.getInstTotales() + " instrucciones ejecutadas");
            if (p.getInstRestantes() == 0) {
                System.out.println("\nProceso " + p.getNombre() + " finalizado con éxito");
                System.out.println("Localidades liberadas: [" + (p.getInicio() + 1) + " - " + p.getFin() + "]");
                System.out.println("Se liberaron " + p.getEspacio() + " localidades de memoria");
                memoria.liberarMemoria(p);
                actual = p.getInicio();
                terminadosCorrectamente.add(p);
            } else {
                colaProcesos.add(p);
            }
        } else {
            System.out.println("\nNo hay procesos en espera");
        }
    }

    /**
     * Imprime en pantalla la información del proceso más próximo a ser ejecutado.
     */
    public void verProcesoActual() {
        if (colaProcesos.size() > 0) {
            Proceso p = colaProcesos.element();
            System.out.printf("\nNombre:%30s", p.getNombre());
            System.out.printf("\nId:%34d", p.getId());
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
        if (colaProcesos.size() != 0) {
            Proceso p = colaProcesos.poll();
            System.out.println("\nOmitiendo proceso " + p.getNombre());
            colaProcesos.add(p);
            Proceso q = colaProcesos.element();
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
        if (colaProcesos.size() > 0) {
            Proceso p = colaProcesos.poll();
            System.out.println("\nMatando proceso " + p.getNombre());
            System.out.println("Localidades liberadas: [" + (p.getInicio() + 1) + " - " + p.getFin() + "]");
            System.out.println("Se liberaron " + p.getEspacio() + " localidades de memoria");
            memoria.liberarMemoria(p);
            actual = p.getInicio();
            procesosEliminados.add(p);
        } else {
            System.out.println("\nNo hay procesos en espera");
        }
    }

    /**
     * Finaliza el programa y muestra información de los procesos que había en espera.
     */
    public void matarTodoYTerminar() {
        System.out.println("\nFinalizando procesos...");
        while (colaProcesos.size() > 0) {
            matarProcesoActual();
        }
    }
}
