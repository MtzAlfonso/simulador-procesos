package colores;

/**
 * Contiene los códigos de colores para utilizar en el terminal.
 *
 * @author J Alfonso Martínez Baeza
 * @version 1.0.20102020
 */
public class Colores {
    /**
     * Rojo.
     */
    public static final String ANSI_RED = "\u001B[31m";
    /**
     * Verde.
     */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**
     * Amarillo.
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**
     * Morado.
     */
    public static final String ANSI_PURPLE = "\u001B[35m";
    /**
     * Cyan.
     */
    public static final String ANSI_CYAN = "\u001B[36m";
    /**
     * Default.
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Recibe una cadena de texto y el código de color y devuelve la cadena coloreada.
     *
     * @param palabra Cadena
     * @param color   Código de color
     * @return Cadena coloreada
     */
    public static String colorear(String palabra, String color) {
        return (color + palabra + ANSI_RESET);
    }

    /**
     * Recibe una variable y el código de color y devuelve la cadena coloreada.
     *
     * @param variable Cadena
     * @param color    Código de color
     * @return Variable coloreada
     */
    public static String colorear(int variable, String color) {
        return (color + variable + ANSI_RESET);
    }
}
