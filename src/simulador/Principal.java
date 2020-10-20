package simulador;

import java.util.*;

/**
 * Clase que simula la ejecución de procesos en un sistema operativo.
 *
 * @author J Alfonso Martínez Baeza
 * @version 2.1.19102020
 */
public class Principal {

    /**
     * Función principal.
     * @param args Puede recibir argumentos que serán accesibles a través de este arreglo.
     */
    public static void main(String[] args) {
        Administrador admin = new Administrador();
        Scanner sc = new Scanner(System.in);
        int op;
        do {
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
                case 1 -> admin.crearProceso(sc);
                case 2 -> admin.verEstadoActual();
                case 3 -> admin.verEstadoMemoria();
                case 4 -> admin.verColaDeProcesos();
                case 5 -> admin.ejecutarProcesoActual();
                case 6 -> admin.verProcesoActual();
                case 7 -> admin.omitirProcesoActual();
                case 8 -> admin.matarProcesoActual();
                case 9 -> admin.matarTodoYTerminar();
                default -> System.out.println("\nIngrese una opción válida");
            }
        } while (op != 9);
    }

}
