package com.mycompany.proyecto;

/**
 * Clase que representa una Transacción bancaria en HiperBanco.
 * Audita depósitos, retiros, compras y transferencias.
 */
public class Transaccion {

    private int numeroTransaccion;
    private String fecha;
    private TipoTransaccion tipo;
    private double monto;
    private String detalle;
    private double saldoResultante;

    /**
     * Constructor con todos los atributos de la transacción.
     *
     * @param numeroTransaccion Consecutivo único a partir de 10000.
     * @param fecha             Fecha del sistema de la operación.
     * @param tipo              Tipo de transacción (enum TipoTransaccion).
     * @param monto             Monto monetario positivo (> 0).
     * @param detalle           Contexto o descripción operativa.
     * @param saldoResultante   Balance existente en la cuenta post-operación.
     */
    public Transaccion(int numeroTransaccion, String fecha, TipoTransaccion tipo, double monto, String detalle,
            double saldoResultante) {
        this.numeroTransaccion = numeroTransaccion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.monto = monto;
        this.detalle = detalle;
        this.saldoResultante = saldoResultante;
    }

    /**
     * Constructor por defecto.
     */
    public Transaccion() {
        this(0, "", TipoTransaccion.Deposito, 0.0, "", 0.0);
    }

    // Getters y Setters

    public int getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(int numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(double saldoResultante) {
        this.saldoResultante = saldoResultante;
    }

    /**
     * Muestra el detalle de la transacción en consola en una sola línea formateada.
     */
    public void mostrarEnConsola() {
        System.out.printf(
                "Transacción #%d | Fecha: %s | Tipo: %-22s | Monto: $%,10.2f | Saldo Post: $%,10.2f | Detalle: %s%n",
                numeroTransaccion, fecha, tipo, monto, saldoResultante, detalle);
    }

    @Override
    public String toString() {
        return "Transacción #" + numeroTransaccion + " [" + tipo + "] Monto: $" + monto + " | Detalle: " + detalle
                + " | Saldo Resultante: $" + saldoResultante;
    }
}
