package com.mycompany.proyecto;

/**
 * Clase utilitaria y de dominio para la Tarjeta de Acceso de seguridad (Matriz
 * 4x5).
 * Implementa la lógica de generación, visualización y resolución de coordenadas
 * requeridas para el segundo factor de autenticación de HiperBanco.
 */
public class TarjetaAcceso {

    public static final int FILAS = 4;
    public static final int COLUMNAS = 5;

    private int[][] matriz;

    /**
     * Constructor que inicializa una nueva matriz aleatoria de 4x5.
     */
    public TarjetaAcceso() {
        this.matriz = generarMatriz();
    }

    /**
     * Constructor con matriz existente.
     *
     * @param matriz Matriz 4x5 de enteros.
     */
    public TarjetaAcceso(int[][] matriz) {
        this.matriz = matriz;
    }

    /**
     * Genera una matriz bidimensional de 4 filas x 5 columnas
     * con valores enteros aleatorios en el rango [10, 99].
     *
     * @return Matriz int[4][5]
     */
    public static int[][] generarMatriz() {
        int[][] nuevaMatriz = new int[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                nuevaMatriz[i][j] = (int) (Math.random() * 90) + 10; // Rango 10 - 99
            }
        }
        return nuevaMatriz;
    }

    /**
     * Imprime en consola la matriz dada con encabezados de columnas (A - E) y filas
     * (1 - 4).
     *
     * @param matriz Matriz de 4x5 a imprimir.
     */
    public static void imprimirMatriz(int[][] matriz) {
        if (matriz == null) {
            System.out.println("Tarjeta de acceso no inicializada.");
            return;
        }
        System.out.println("=== TARJETA DE ACCESO ===");
        System.out.println("      A   B   C   D   E");
        for (int i = 0; i < matriz.length; i++) {
            System.out.print("  " + (i + 1) + " ");
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf("%4d", matriz[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Obtiene el valor numérico en una coordenada dada en formato alfanumérico (ej:
     * "B3", "E2", "D4").
     *
     * @param matriz     Matriz de 4x5.
     * @param coordenada Coordenada con letra de columna (A-E) y número de fila
     *                   (1-4).
     *                   rn Valor e
     * 
     *                   ic static int obtenerValorPorCoordenada(int[][]
     *                   if (matriz == null || coordenad
     *                   return -1;
     * 
     *                   String coord = coordenad
     *                   char colChar = coord.cha
     * 
     * 
     * 
     *                   col = colChar - 'A';
     *                   i
     * 
     * 
     * 
     *                   }
     *                   return -1;
     * 
     *                   }
     * 
     *                   /**
     *                   Genera una coordenada al azar en formato alfanumérico (ej:
     *                   "B3").
     *
     * @return String de la coordenada.
     */
    public static String generarCoordenadaAleatoria() {
        char col = (char) ('A' + (int) (Math.random() * COLUMNAS));
        int row = 1 + (int) (Math.random() * FILAS);
        return "" + col + row;
    }

    // Getters y Setters de instancia

    public int[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }
}
