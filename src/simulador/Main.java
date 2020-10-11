package simulador;

import java.util.*;

/**
 * Programa que simula la ejecución de procesos en un sistema operativo.
 *
 * @author J Alfonso Martinez Baeza
 * @version 1.0.10102020
 */
public class Main {

    /**
     * Funcion principal.
     * @param args Puede recibir argumentos que serán accesibles a través de este arreglo.
     */
    public static void main(String[] args) {
        Administrador admin = new Administrador();
        Scanner sc = new Scanner(System.in);
        int op = 0;

        while (op != 8) {
            System.out.println();
            System.out.println("1: Crear nuevo proceso");
            System.out.println("2: Ver estado actual del sistema");
            System.out.println("3: Imprimir cola de procesos");
            System.out.println("4: Ejecutar proceso actual");
            System.out.println("5: Ver proceso actual");
            System.out.println("6: Pasar al proceso siguiente");
            System.out.println("7: Matar proceso actual");
            System.out.println("8: Matar todo y terminar");
            System.out.print(">> ");
            op = sc.nextInt();
            switch (op) {
                case 1:
                    admin.crearProceso(sc);
                    break;
                case 2:
                    admin.verEstadoActual();
                    break;
                case 3:
                    admin.verColaDeProcesos();
                    break;
                case 4:
                    admin.ejecutarProcesoActual();
                    break;
                case 5:
                    admin.verProcesoActual();
                    break;
                case 6:
                    admin.omitirProcesoActual();
                    break;
                case 7:
                    admin.matarProcesoActual();
                    break;
                case 8:
                    admin.matarTodoYTerminar();
                    break;
                default:
                    System.out.println("Ingrese una opción válida");
                    break;
            }
        }
    }

}
