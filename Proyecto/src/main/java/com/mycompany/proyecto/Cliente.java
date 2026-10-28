package com.mycompany.proyecto;

public class Cliente {

    private String id;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private String usuario;
    private String clave;
    private EstadoCliente estado;
    private int[][] tarjetaAcceso;

    /**
     * Constructor principal con parámetros según la especificación técnica.
     *
     * @param id             Identificación única nacional o pasaporte.
     * @param nombreCompleto Nombre y apellidos del cliente.
     * @param telefono       Teléfono de contacto (formato 0000-0000).
     * @param correo         Correo electrónico personal.
     * @param usuario        Identificador de inicio de sesión autogenerado.
     */
    public Cliente(String id, String nombreCompleto, String telefono, String correo, String usuario) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.usuario = usuario;
        this.clave = ""; // Clave inicial vacía obligatoria
        this.estado = EstadoCliente.Activo;
        this.tarjetaAcceso = generarTarjetaAcceso();
    }

    /**
     * Constructor por defecto para inicialización o pruebas.
     */
    public Cliente() {
        this("", "", "", "", "");
    }

    /**
     * Genera una matriz bidimensional de 4x5 cargada con enteros aleatorios en el
     * rango [10, 99].
     *
     * @return Matriz de enteros de 4 filas por 5 columnas.
     */
    private int[][] generarTarjetaAcceso() {
        int[][] matriz = new int[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                matriz[i][j] = (int) (Math.random() * 90) + 10; // Rango 10 - 99
            }
        }
        return matriz;
    }

    // Getters y Setters completos

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public EstadoCliente getEstado() {
        return estado;
    }

    public void setEstado(EstadoCliente estado) {
        this.estado = estado;
    }

    public int[][] getTarjetaAcceso() {
        return tarjetaAcceso;
    }

    public void setTarjetaAcceso(int[][] tarjetaAcceso) {
        this.tarjetaAcceso = tarjetaAcceso;
    }

    /**
     * Imprime en consola la tarjeta de acceso con formato de matriz 4x5.
     */
    public void imprimirTarjetaAcceso() {
        System.out.println("=== TARJETA DE ACCESO ===");
        System.out.println("      A   B   C   D   E");
        if (tarjetaAcceso != null) {
            for (int i = 0; i < 4; i++) {
                System.out.print("  " + (i + 1) + " ");
                for (int j = 0; j < 5; j++) {
                    System.out.printf("%4d", tarjetaAcceso[i][j]);
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * Muestra la información del cliente en consola siguiendo las reglas de
     * visualización:
     * Si la clave es vacía, imprime "Sin configurar". Si tiene caracteres, imprime
     * asteriscos ****.
     */
    public void mostrarEnConsola() {
        String claveOculta = (clave == null || clave.isEmpty())
                ? "Sin configurar"
                : clave.replaceAll(".", "*");
        System.out.println("----------------------------------------------");
        System.out.println("ID:              " + id);
        System.out.println("Nombre Completo: " + nombreCompleto);
        System.out.println("Teléfono:        " + telefono);
        System.out.println("Correo:          " + correo);
        System.out.println("Usuario:         " + usuario);
        System.out.println("Clave:           " + claveOculta);
        System.out.println("Estado:          " + estado);
        System.out.println("----------------------------------------------");
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombreCompleto=" + nombreCompleto + ", usuario=" + usuario + ", estado="
                + estado + '}';
    }
}
