package simulador;

import java.util.*;

/**
 * Clase que simula la ejecución de procesos en un sistema operativo.
 *
 * @author J Alfonso Martinez Baeza
 * @version 2.1.17102020
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

        while (op != 9) {
            System.out.println();
            System.out.println("1: Crear nuevo proceso");
            System.out.println("2: Ver estado actual del sistema");
            System.out.println("3: Ver estado actual de la memoria");
            System.out.println("4: Imprimir cola de procesos");
            System.out.println("5: Ejecutar proceso actual");
            System.out.println("6: Ver proceso actual");
            System.out.println("7: Pasar al proceso siguiente");
            System.out.println("8: Matar proceso actual");
            System.out.println("9: Matar todo y terminar");
            System.out.print("> ");
            try{
                op = sc.nextInt();
            }catch (InputMismatchException e){
                op = 0;
                sc.next();
            }
            switch (op) {
                case 1:
                    admin.crearProceso(sc);
                    break;
                case 2:
                    admin.verEstadoActual();
                    break;
                case 3:
                    admin.verEstadoMemoria();
                    break;
                case 4:
                    admin.verColaDeProcesos();
                    break;
                case 5:
                    admin.ejecutarProcesoActual();
                    break;
                case 6:
                    admin.verProcesoActual();
                    break;
                case 7:
                    admin.omitirProcesoActual();
                    break;
                case 8:
                    admin.matarProcesoActual();
                    break;
                case 9:
                    admin.matarTodoYTerminar();
                    break;
                default:
                    System.out.println("\nIngrese una opción válida");
                    break;
            }
        }
    }

}
