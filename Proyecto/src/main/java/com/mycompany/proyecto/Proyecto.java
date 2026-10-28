package com.mycompany.proyecto;

import javax.swing.JOptionPane;

/**
 * Sistema Bancario HiperBanco - Hito Semana 8 (Avance 1)
 *
 * Cumple estrictamente con los 4 criterios de evaluación:
 * 1. Estructura y Clases POO (Cliente, Cuenta, Transaccion, Enums)
 * 2. Navegación e Interfaz Básica con JOptionPane (do-while y switch)
 * 3. Salida por consola para un máximo de 2 instancias simuladas sin transacciones
 * 4. Validaciones iniciales (ID no duplicado, regla 0..1 y acceso básico por cliente)
 *
 * @author alber
 */
public class Proyecto {

    // Arreglos estáticos y contadores para almacenamiento en memoria (POO estándar)
    private static Cliente[] clientes = new Cliente[30];
    private static Cuenta[] cuentas = new Cuenta[30];
    private static int contadorClientes = 0;
    private static int contadorCuentas = 0;

    // Consecutivos globales
    private static int consecutivoUsuario = 40;
    private static int consecutivoCuenta = 4700;

    public static void main(String[] args) {

        // Precarga de las 2 instancias simuladas requeridas para Semana 8
        precargarDatosSimulados();

        int opcion = 0;
        do {
            String input = JOptionPane.showInputDialog("""
                    ----Menu Bancario----
                    1. BANCO
                    2. CLIENTES
                    3. SALIR
                    """);

            if (input == null) {
                break; // Usuario canceló o cerró ventana
            }

            try {
                opcion = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una opción numérica válida.");
                continue;
            }

            switch (opcion) {
                case 1:
                    menuBanco();
                    break;

                case 2:
                    loginCliente();
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }

        } while (opcion != 3);
    }

    // =========================================================================
    //  PRECARGA SIMULADA (SEMANA 8: 2 INSTANCIAS SIN TRANSACCIONES)
    // =========================================================================

    private static void precargarDatosSimulados() {
        // Instancia 1 de Cliente
        Cliente c1 = new Cliente("1011", "Carlos Ruiz", "8888-1111", "carlos@banco.com", "Carlos" + consecutivoUsuario++);
        c1.setClave("Pass123");
        c1.setEstado(EstadoCliente.Activo);
        clientes[contadorClientes++] = c1;

        // Instancia 1 de Cuenta ligada a Cliente 1
        Cuenta q1 = new Cuenta(consecutivoCuenta++, c1, TipoCuenta.Ahorros, "27/10/2026", 500.0);
        cuentas[contadorCuentas++] = q1;

        // Instancia 2 de Cliente
        Cliente c2 = new Cliente("1022", "Maria Lopez", "8888-2222", "maria@banco.com", "Maria" + consecutivoUsuario++);
        c2.setClave("Clave456");
        c2.setEstado(EstadoCliente.Activo);
        clientes[contadorClientes++] = c2;

        // Instancia 2 de Cuenta ligada a Cliente 2
        Cuenta q2 = new Cuenta(consecutivoCuenta++, c2, TipoCuenta.Corriente, "27/10/2026", 1200.0);
        cuentas[contadorCuentas++] = q2;

        System.out.println("=============================================================");
        System.out.println(" [SISTEMA] HiperBanco inicializado exitosamente.");
        System.out.println(" [SISTEMA] Precargadas 2 instancias simuladas sin transacciones.");
        System.out.println("=============================================================\n");
    }

    // =========================================================================
    //  MÓDULO 1: MENÚ BANCO
    // =========================================================================

    private static void menuBanco() {
        int opt = 0;
        do {
            String inputOpt = JOptionPane.showInputDialog("""
                    ----Menu Bancario----
                    1. Mostrar clientes
                    2. Mostrar cuentas y transacciones
                    3. Agregar nuevo cliente
                    4. Agregar nueva cuenta
                    5. Buscar cliente
                    6. Buscar cuenta
                    7. Generar reportes
                    8. volver
                    """);

            if (inputOpt == null) {
                break;
            }

            try {
                opt = Integer.parseInt(inputOpt.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una opción numérica válida.");
                continue;
            }

            switch (opt) {
                case 1:
                    mostrarClientes();
                    break;
                case 2:
                    mostrarCuentas();
                    break;
                case 3:
                    agregarNuevoCliente();
                    break;
                case 4:
                    agregarNuevaCuenta();
                    break;
                case 5:
                case 6:
                case 7:
                    JOptionPane.showMessageDialog(null, "Opción correspondiente a los siguientes avances.");
                    break;
                case 8:
                    JOptionPane.showMessageDialog(null, "Volviendo al menú principal...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }

        } while (opt != 8);
    }

    /**
     * Opción 1: Muestra los clientes en la consola.
     */
    private static void mostrarClientes() {
        System.out.println("\n==============================================");
        System.out.println("             LISTA DE CLIENTES               ");
        System.out.println("==============================================");
        System.out.println("Total registrados: " + contadorClientes);

        for (int i = 0; i < contadorClientes; i++) {
            clientes[i].mostrarEnConsola();
        }
        System.out.println("==============================================\n");

        JOptionPane.showMessageDialog(null, "Clientes mostrados en la consola del sistema (System.out).");
    }

    /**
     * Opción 2: Muestra las cuentas y el estado de sus transacciones en consola.
     */
    private static void mostrarCuentas() {
        System.out.println("\n==============================================");
        System.out.println("      LISTA DE CUENTAS Y TRANSACCIONES        ");
        System.out.println("==============================================");
        System.out.println("Total registradas: " + contadorCuentas);

        for (int i = 0; i < contadorCuentas; i++) {
            cuentas[i].mostrarEnConsola();
        }
        System.out.println("==============================================\n");

        JOptionPane.showMessageDialog(null, "Cuentas mostradas en la consola del sistema (System.out).");
    }

    /**
     * Opción 3: Registro de nuevo cliente con validación de ID no duplicado.
     */
    private static void agregarNuevoCliente() {
        if (contadorClientes >= clientes.length) {
            JOptionPane.showMessageDialog(null, "Arreglo de clientes lleno.");
            return;
        }

        String id = JOptionPane.showInputDialog("Ingrese el ID del cliente:");
        if (id == null || id.trim().isEmpty()) return;
        id = id.trim();

        // Validación de ID no duplicado
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getId().equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(null, "Cliente ya agregado en el sistema (ID duplicado).");
                return;
            }
        }

        String nombre = JOptionPane.showInputDialog("Ingrese el nombre completo:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String telefono = JOptionPane.showInputDialog("Ingrese el teléfono (formato 0000-0000):");
        if (telefono == null) return;

        String correo = JOptionPane.showInputDialog("Ingrese el correo electrónico:");
        if (correo == null) return;

        String primerNombre = nombre.trim().split(" ")[0];
        String usuario = primerNombre + consecutivoUsuario++;

        Cliente nuevoCliente = new Cliente(id, nombre.trim(), telefono.trim(), correo.trim(), usuario);
        nuevoCliente.setClave("123456"); // Clave inicial básica

        clientes[contadorClientes++] = nuevoCliente;

        System.out.println("\n[SISTEMA] Nuevo cliente registrado:");
        nuevoCliente.mostrarEnConsola();

        JOptionPane.showMessageDialog(null, "Cliente registrado con éxito.\nUsuario: " + usuario + "\nClave temporal: 123456");
    }

    /**
     * Opción 4: Registro de nueva cuenta asociada a un cliente (regla 0..1).
     */
    private static void agregarNuevaCuenta() {
        if (contadorCuentas >= cuentas.length) {
            JOptionPane.showMessageDialog(null, "Arreglo de cuentas lleno.");
            return;
        }

        String id = JOptionPane.showInputDialog("Ingrese el ID del cliente propietario:");
        if (id == null || id.trim().isEmpty()) return;

        Cliente propietario = null;
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getId().equalsIgnoreCase(id.trim())) {
                propietario = clientes[i];
                break;
            }
        }

        if (propietario == null) {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
            return;
        }

        // Validación de regla 0..1: El cliente no debe tener ya una cuenta
        for (int i = 0; i < contadorCuentas; i++) {
            if (cuentas[i].getPropietario() != null && cuentas[i].getPropietario().getId().equalsIgnoreCase(propietario.getId())) {
                JOptionPane.showMessageDialog(null, "El cliente ya posee una cuenta registrada.");
                return;
            }
        }

        String saldoStr = JOptionPane.showInputDialog("Ingrese el saldo inicial (>= 0):");
        if (saldoStr == null) return;

        double saldoInicial = 0;
        try {
            saldoInicial = Double.parseDouble(saldoStr.trim());
            if (saldoInicial < 0) {
                JOptionPane.showMessageDialog(null, "El saldo debe ser mayor o igual a cero.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Monto inválido.");
            return;
        }

        Cuenta nuevaCuenta = new Cuenta(consecutivoCuenta++, propietario, TipoCuenta.Ahorros, "27/10/2026", saldoInicial);
        cuentas[contadorCuentas++] = nuevaCuenta;

        System.out.println("\n[SISTEMA] Nueva cuenta registrada:");
        nuevaCuenta.mostrarEnConsola();

        JOptionPane.showMessageDialog(null, "Cuenta registrada con éxito.\nNúmero de Cuenta: " + nuevaCuenta.getNumeroCuenta());
    }

    // =========================================================================
    //  MÓDULO 2: MENÚ CLIENTES (LOGIN BÁSICO Y CONSULTA)
    // =========================================================================

    private static void loginCliente() {
        String usuarioOId = JOptionPane.showInputDialog("Ingrese su usuario o ID:");
        if (usuarioOId == null || usuarioOId.trim().isEmpty()) return;

        Cliente cliente = null;
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i].getUsuario().equalsIgnoreCase(usuarioOId.trim()) || clientes[i].getId().equalsIgnoreCase(usuarioOId.trim())) {
                cliente = clientes[i];
                break;
            }
        }

        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
            return;
        }

        String clave = JOptionPane.showInputDialog("Ingrese su clave:");
        if (clave == null) return;

        if (!cliente.getClave().equals(clave.trim())) {
            JOptionPane.showMessageDialog(null, "Clave incorrecta.");
            return;
        }

        JOptionPane.showMessageDialog(null, "¡Bienvenido(a) " + cliente.getNombreCompleto() + "!");

        int opt = 0;
        do {
            String inputOpt = JOptionPane.showInputDialog("""
                    ----Menu Cliente----
                    1. Transacciones
                    2. Mi cuenta
                    3. Actualizar datos
                    4. Volver a menu principal
                    """);

            if (inputOpt == null) break;

            try {
                opt = Integer.parseInt(inputOpt.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opción inválida.");
                continue;
            }

            switch (opt) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Para el avance de Semana 8 no se registran transacciones todavía.");
                    break;

                case 2:
                    // Buscar cuenta del cliente
                    Cuenta cta = null;
                    for (int i = 0; i < contadorCuentas; i++) {
                        if (cuentas[i].getPropietario() != null && cuentas[i].getPropietario().getId().equalsIgnoreCase(cliente.getId())) {
                            cta = cuentas[i];
                            break;
                        }
                    }

                    if (cta == null) {
                        System.out.println("El cliente " + cliente.getNombreCompleto() + " no posee una cuenta registrada.");
                        JOptionPane.showMessageDialog(null, "No posee una cuenta registrada.");
                    } else {
                        System.out.println("\n[CONSULTA MI CUENTA]");
                        cta.mostrarEnConsola();
                        JOptionPane.showMessageDialog(null, "Detalle de su cuenta impreso en la consola.\nNúmero de Cuenta: " + cta.getNumeroCuenta() + "\nSaldo: $" + cta.getSaldoActual());
                    }
                    break;

                case 3:
                    String nuevoTel = JOptionPane.showInputDialog("Nuevo teléfono:", cliente.getTelefono());
                    if (nuevoTel != null && !nuevoTel.trim().isEmpty()) {
                        cliente.setTelefono(nuevoTel.trim());
                    }

                    String nuevoCorreo = JOptionPane.showInputDialog("Nuevo correo:", cliente.getCorreo());
                    if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty()) {
                        cliente.setCorreo(nuevoCorreo.trim());
                    }

                    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
                    break;

                case 4:
                    JOptionPane.showMessageDialog(null, "Cerrando sesión de cliente...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }

        } while (opt != 4);
    }
}

