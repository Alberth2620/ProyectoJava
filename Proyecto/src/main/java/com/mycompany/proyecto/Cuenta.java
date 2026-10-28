package com.mycompany.proyecto;

/**
 * Clase que representa una Cuenta Bancaria en HiperBanco.
 * Mantiene la referencia directa a su propietario (Cliente) y su registro
 * histórico
 * de transacciones en un arreglo estático de tamaño fijo.
 */
public class Cuenta {

    private int numeroCuenta;
    private Cliente propietario;
    private TipoCuenta tipoCuenta;
    private String fechaApertura;
    private double saldoInicial;
    private double saldoActual;
    private Transaccion[] transacciones;
    private int contadorTransacciones;

    /**
     * Constructor principal de la cuenta según la especificación técnica.
     *
     * @param numeroCuenta  Número único autoincremental (>= 4700).
     * @param propietario   Referencia directa al objeto Cliente dueño de la cuenta.
     * @param tipoCuenta    Tipo de cuenta (enum TipoCuenta).
     * @param fechaApertura Fecha de apertura formato dd/MM/yyyy.
     * @param saldoInicial  Monto inicial depositado (>= 0).
     */
    public Cuenta(int numeroCuenta, Cliente propietario, TipoCuenta tipoCuenta, String fechaApertura,
            double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.propietario = propietario;
        this.tipoCuenta = tipoCuenta;
        this.fechaApertura = fechaApertura;
        this.saldoInicial = saldoInicial;
        this.saldoActual = saldoInicial;
        this.transacciones = new Transaccion[50];
        this.contadorTransacciones = 0;
    }

    /**
     * Constructor por defecto para inicialización o pruebas.
     */
    public Cuenta() {
        this(0, null, TipoCuenta.Ahorros, "", 0.0);
    }

    /**
     * Agrega una nueva transacción al arreglo fijo de transacciones de la cuenta.
     *
     * @param t Transacción a registrar.
     * @return true si se agregó con éxito, false si el arreglo ya alcanzó su
     *         capacidad máxima (50).
     */
    public boolean agregarTransaccion(Transaccion t) {
        if (contadorTransacciones < 50) {
            transacciones[contadorTransacciones] = t;
            contadorTransacciones++;
            return true;
        }
        return false; // Arreglo de transacciones lleno
    }

    // Getters y Setters completos

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Cliente getPropietario() {
        return propietario;
    }

    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Transaccion[] getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(Transaccion[] transacciones) {
        this.transacciones = transacciones;
    }

    public int getContadorTransacciones() {
        return contadorTransacciones;
    }

    public void setContadorTransacciones(int contadorTransacciones) {
        this.contadorTransacciones = contadorTransacciones;
    }

    /**
     * Imprime en consola el detalle completo de la cuenta y sus transacciones.
     */
    public void mostrarEnConsola() {
        System.out.println("==============================================");
        System.out.println("Número de Cuenta: " + numeroCuenta);
        System.out.println("Propietario:      "
                + (propietario != null ? propietario.getNombreCompleto() + " [ID: " + propietario.getId() + "]"
                        : "Sin propietario"));
        System.out.println("Tipo de Cuenta:   " + tipoCuenta);
        System.out.println("Fecha Apertura:   " + fechaApertura);
        System.out.printf("Saldo Inicial:    $%,.2f%n", saldoInicial);
        System.out.printf("Saldo Actual:     $%,.2f%n", saldoActual);
        System.out.println("Transacciones:    " + contadorTransacciones + "/50");
        if (contadorTransacciones == 0) {
            System.out.println("  -> Sin transacciones");
        } else {
            System.out.println("  -> Detalle de Transacciones:");
            for (int i = 0; i < contadorTransacciones; i++) {
                System.out.print("     [" + (i + 1) + "] ");
                transacciones[i].mostrarEnConsola();
            }
        }
        System.out.println("==============================================");
    }

    @Override
    public String toString() {
        return "Cuenta #" + numeroCuenta + " [" + tipoCuenta + "] - Saldo Actual: $" + saldoActual
                + (propietario != null ? " - Dueño: " + propietario.getNombreCompleto() : "");
    }
}
