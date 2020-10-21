package simulador;

import java.util.*;

import static colores.Colores.*;

/**
 * Clase que simula el administrador de procesos.
 *
 * @author J Alfonso Martínez Baeza
 * @version 2.2.20102020
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
    private boolean esContigua;

    /**
     * Constructor de la clase Administrador.
     */
    public Administrador() {
        id = (int) (Math.random() * 2000) + 1000;
        colaProcesos = new LinkedList<>();
        terminadosCorrectamente = new ArrayList<>();
        procesosEliminados = new ArrayList<>();
        memoria = new Memoria(2048);
        actual = 0;
        esContigua = true;
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
            // Ciclo para buscar localidades vacías.
            if (actual == memoria.getTablaMemoria().length) {
                actual = 0;
            }
            while (!sePuedeCrear(actual, actual + n)) {
                // Es necesario un condicional para poder salir del ciclo en caso de que haya memoria suficiente pero
                // no sea contigua, ya que de ser así, entraríamos en un loop infinito.
                if (memoria.getTablaMemoria()[actual].isVisitada()) {
                    // Guardamos un indicador de que el proceso no puede crearse
                    esContigua = false;
                    break;
                }
                // En cada vuelta marcamos como visitada la localidad actual.
                memoria.getTablaMemoria()[actual].setVisitada(true);
                // Avanzamos un espacio mientras no estemos en la ultima localidad, pues de ser así regresaremos a la
                // posición 0 del arreglo de memoria.
                if (actual + 1 < memoria.getTablaMemoria().length - 1) {
                    actual++;
                } else {
                    actual = 0;
                }
            }
            // Antes de salir del ciclo volvemos a marcar las localidades como NO visitadas para poder
            // visitarlas posteriormente.
            for (Localidad loc : memoria.getTablaMemoria()) {
                loc.setVisitada(false);
            }
            if (esContigua) {
                System.out.print("\nNombre del proceso: ");
                addProcess(new Proceso(sc.next(), id++, n, actual, actual + n));
                System.out.println("Tamaño:\t" + colorear(n, ANSI_CYAN));
                System.out.println("Localidades: [" + colorear(decToHex(actual + 1), ANSI_CYAN) + " - "
                        + colorear(decToHex(actual + n), ANSI_CYAN) + "]");
                actual += n;
            } else {
                System.out.println(colorear("\nNo hay memoria contigua", ANSI_YELLOW));
                System.out.println("Memoria requerida:\t" + colorear(n, ANSI_CYAN));
                System.out.println("Memoria disponible:\t" + colorear(memoria.memoriaDisponible, ANSI_CYAN));
                System.out.println("\nRecomendaciones:");
                System.out.println(colorear("*", ANSI_RED) + " Libere memoria ejecutando o matando procesos");
                esContigua = true;
            }
        } else {
            System.out.println(colorear("\nNo hay memoria suficiente", ANSI_RED));
            System.out.println("Memoria requerida:\t" + colorear(n, ANSI_CYAN));
            System.out.println("Memoria disponible:\t" + colorear(memoria.memoriaDisponible, ANSI_CYAN));
            System.out.println("\nRecomendaciones:");
            if (memoria.memoriaDisponible > 0) {
                System.out.println(colorear("*", ANSI_RED) + " Intente con un proceso más pequeño ");
            }
            System.out.println(colorear("*", ANSI_RED) + " Libere memoria ejecutando o matando procesos");
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
        System.out.printf("\nProcesos en espera de ser ejecutados: "
                + ANSI_CYAN + "%6d" + ANSI_RESET, colaProcesos.size());
        System.out.printf("\nProcesos terminados exitosamente: "
                + ANSI_CYAN + "%10d" + ANSI_RESET, terminadosCorrectamente.size());
        for (Proceso p : terminadosCorrectamente) {
            System.out.print("\n\t" + p.getNombre());
        }
        System.out.printf("\nProcesos terminados antes de tiempo: "
                + ANSI_CYAN + "%7d" + ANSI_RESET, procesosEliminados.size());
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
        System.out.println("[" + colorear("-", ANSI_GREEN) + "] Localidad vacía");
        System.out.println("[" + colorear("o", ANSI_RED) + "] Localidad ocupada");

        for (int i = 0; i < memoria.getTablaMemoria().length; i++) {
            if (i % 16 == 0) {
                System.out.println();
            }
            System.out.printf("%-6s", decToHex(i + 1));
            if (memoria.getTablaMemoria()[i].getContenido() == 'o') {
                System.out.printf("[" + ANSI_RED + "%1c" + ANSI_RESET + "] ", memoria.getTablaMemoria()[i].getContenido());
            } else {
                System.out.printf("[" + ANSI_GREEN + "%1c" + ANSI_RESET + "] ", memoria.getTablaMemoria()[i].getContenido());
            }
        }
        System.out.printf("\n\nMemoria ocupada: " + ANSI_RED + "%11d" + ANSI_RESET, (memoria.memoriaTotal - memoria.memoriaDisponible));
        System.out.printf("\nMemoria disponible: " + ANSI_GREEN + "%8d\n" + ANSI_RESET, memoria.memoriaDisponible);
        System.out.println("\nLocalidades ocupadas por procesos:");
        System.out.print("\nProceso         | Inicio |  Fin  | Memoria utilizada");
        System.out.print("\n----------------|--------|-------|-------------------");
        for (Proceso p : colaProcesos) {
            System.out.printf("\n%-15s |  %-5s | %-5s |  %8d ", p.getNombre(), decToHex(p.getInicio() + 1), decToHex(p.getFin()), p.getEspacio());
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
                    System.out.printf(" %-15s" + colorear(" *", ANSI_RED), p.getNombre());
                } else {
                    System.out.printf(" %-15s", p.getNombre());
                }
                System.out.println();
            }
            System.out.println("\n" + colorear(" *", ANSI_RED) + " Proceso activo");
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
            System.out.println("\nEjecutando proceso " + colorear(p.getNombre(), ANSI_GREEN));
            System.out.println(colorear(p.getInstEjecutadas(), ANSI_CYAN) + "/" + p.getInstTotales() + " instrucciones ejecutadas");
            if (p.getInstRestantes() == 0) {
                System.out.println("\nProceso " + colorear(p.getNombre(), ANSI_GREEN) + " finalizado con éxito");
                System.out.println("Se liberaron " + colorear(p.getEspacio(), ANSI_CYAN) + " localidades de memoria");
                System.out.println("Localidades liberadas: [" + colorear(decToHex(p.getInicio() + 1), ANSI_CYAN)
                        + " - " + colorear(decToHex(p.getFin()), ANSI_CYAN) + "]");
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
            System.out.printf("\nId:" + ANSI_YELLOW + "%34d" + ANSI_RESET, p.getId());
            System.out.printf("\nNombre:" + ANSI_GREEN + "%30s" + ANSI_RESET, p.getNombre());
            System.out.println();
            System.out.printf("\nInstrucciones totales:" + ANSI_CYAN + "%15d" + ANSI_RESET, p.getInstTotales());
            System.out.printf("\nInstrucciones ejecutadas:" + ANSI_CYAN + "%12d" + ANSI_RESET, p.getInstEjecutadas());
            System.out.println();
            System.out.printf("\nMemoria utilizada:" + ANSI_CYAN + "%19d" + ANSI_RESET, p.getEspacio());
            System.out.printf("\nLocalidad inicial:" + ANSI_CYAN + "%19s" + ANSI_RESET, decToHex(p.getInicio() + 1));
            System.out.printf("\nLocalidad final:" + ANSI_CYAN + "%21s" + ANSI_RESET, decToHex(p.getFin()));
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
            System.out.println("\nOmitiendo proceso " + colorear(p.getNombre(), ANSI_PURPLE));
            colaProcesos.add(p);
            Proceso q = colaProcesos.element();
            System.out.println("Proceso " + colorear(q.getNombre(), ANSI_GREEN) + " listo para ejecutarse");
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
            System.out.println("\nMatando proceso " + colorear(p.getNombre(), ANSI_GREEN));
            System.out.println(colorear(p.getInstTotales() - p.getInstEjecutadas(), ANSI_CYAN)
                    + " instrucciones sin ejecutar");
            System.out.println("Se liberaron " + colorear(p.getEspacio(), ANSI_CYAN) + " localidades de memoria");
            System.out.println("Localidades liberadas: [" + colorear(decToHex(p.getInicio() + 1), ANSI_CYAN) + " - "
                    + colorear(decToHex(p.getFin()), ANSI_CYAN) + "]");
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

    private String decToHex(int decimal) {
        int d = decimal;
        StringBuilder hexadecimal = new StringBuilder();
        String caracteresHexadecimales = "0123456789abcdef";
        while (decimal > 0) {
            int residuo = decimal % 16;
            hexadecimal.insert(0, caracteresHexadecimales.charAt(residuo)); // Agregar a la izquierda
            decimal /= 16;
        }
        if (d < 16) {
            hexadecimal.insert(0, "0x00");
        } else if (d < 256) {
            hexadecimal.insert(0, "0x0");
        } else {
            hexadecimal.insert(0, "0x");
        }
        return hexadecimal.toString();
    }
}
