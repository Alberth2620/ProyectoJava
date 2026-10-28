package com.mycompany.proyecto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 * Clase principal y controladora del sistema bancario "HiperBanco".
 * Maneja el flujo de menús mediante JOptionPane y estructuras POO con arreglos estáticos.
 * Hito Semana 8 (Avance 1).
 *
 * @author alber
 */
public class Proyecto {

    // Estructuras de almacenamiento estático (Requerimiento de curso - POO y arreglos fijos)
    private static Cliente[] clientes = new Cliente[30];
    private static Cuenta[] cuentas = new Cuenta[30];

    // Contadores de control de arreglos
    private static int contadorClientes = 0;
    private static int contadorCuentas = 0;

    // Consecutivos globales autoincrementales
    private static int consecutivoUsuario = 40;
    private static int consecutivoCuenta = 4700;
    private static int consecutivoTransaccion = 10000;

    // Formato de fecha del sistema (dd/MM/yyyy)
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        // Carga inicial de datos simulados para el hito de la Semana 8 (2 instancias sin transacciones)
        inicializarDatosSimulados();

        int opcion = 0;
        do {
            String menuPrincipal = """
                    ==============================
                       HIPERBANCO - SISTEMA CORE
                    ==============================
                    1. BANCO
                    2. CLIENTES
                    3. SALIR
                    
                    Seleccione una opción:
                    """;

            opcion = pedirEntero(menuPrincipal);

            switch (opcion) {
                case 1:
                    menuBanco();
                    break;

                case 2:
                    iniciarSesionCliente();
                    break;

                case 3:
                case -1: // Si el usuario presiona Cancelar o cierra la ventana
                    JOptionPane.showMessageDialog(null, "¡Gracias por utilizar HiperBanco!\nCerrando el sistema...", "HiperBanco", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("\n[SISTEMA] Sesión finalizada por el usuario.");
                    opcion = 3;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.", "Error", JOptionPane.WARNING_MESSAGE);
            }

        } while (opcion != 3);
    }

    // =========================================================================
    //  INICIALIZACIÓN DE DATOS SIMULADOS (SEMANA 8 - AVANCE 1)
    // =========================================================================

    /**
     * Carga 2 instancias simuladas de Cliente y 2 de Cuenta sin transacciones
     * para verificar la correcta salida en consola y el funcionamiento inicial.
     */
    private static void inicializarDatosSimulados() {
        String fechaHoy = LocalDate.now().format(FORMATO_FECHA);

        // Cliente 1
        Cliente c1 = new Cliente("1011", "Carlos Ruiz", "8888-1111", "carlos.ruiz@banco.com", "Carlos" + consecutivoUsuario++);
        c1.setClave("Pass123");
        c1.setEstado(EstadoCliente.Activo);
        clientes[contadorClientes++] = c1;

        // Cuenta 1 ligada a Cliente 1
        Cuenta q1 = new Cuenta(consecutivoCuenta++, c1, TipoCuenta.Ahorros, fechaHoy, 500.0);
        cuentas[contadorCuentas++] = q1;

        // Cliente 2
        Cliente c2 = new Cliente("1022", "Maria Lopez", "8888-2222", "maria.lopez@banco.com", "Maria" + consecutivoUsuario++);
        c2.setClave("Clave456");
        c2.setEstado(EstadoCliente.Activo);
        clientes[contadorClientes++] = c2;

        // Cuenta 2 ligada a Cliente 2
        Cuenta q2 = new Cuenta(consecutivoCuenta++, c2, TipoCuenta.Corriente, fechaHoy, 1200.0);
        cuentas[contadorCuentas++] = q2;

        System.out.println("=============================================================");
        System.out.println(" [SISTEMA] HiperBanco inicializado exitosamente.");
        System.out.println(" [SISTEMA] Se han precargado 2 clientes y 2 cuentas simuladas.");
        System.out.println("=============================================================\n");
    }

    // =========================================================================
    //  MÓDULO 1: MENÚ BANCO (ADMINISTRATIVO)
    // =========================================================================

    private static void menuBanco() {
        int opt = 0;
        do {
            String menu = """
                    ---- Menú Bancario Administrativo ----
                    1. Mostrar clientes
                    2. Mostrar cuentas y transacciones
                    3. Agregar nuevo cliente
                    4. Agregar nueva cuenta
                    5. Buscar cliente
                    6. Buscar cuenta
                    7. Generar reportes
                    8. Volver
                    
                    Seleccione una opción:
                    """;

            opt = pedirEntero(menu);

            switch (opt) {
                case 1:
                    mostrarClientes();
                    break;
                case 2:
                    mostrarCuentasYTransacciones();
                    break;
                case 3:
                    agregarNuevoCliente();
                    break;
                case 4:
                    agregarNuevaCuenta();
                    break;
                case 5:
                    buscarCliente();
                    break;
                case 6:
                    buscarCuenta();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null,
                            "Módulo de Reportes Analíticos (Reporte 1, 2 y 3).\nDisponible para la Entrega Final.",
                            "Reportes", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 8:
                case -1:
                    // Regresar al menú principal
                    opt = 8;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.", "Error", JOptionPane.WARNING_MESSAGE);
            }

        } while (opt != 8);
    }

    /**
     * Opción 1: Muestra todos los clientes registrados en la consola del sistema.
     */
    private static void mostrarClientes() {
        if (contadorClientes == 0) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados en el sistema.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        System.out.println("========================================================");
        System.out.println("            LISTA GENERAL DE CLIENTES REGISTRADOS       ");
        System.out.println("========================================================");
        System.out.println("Total de clientes: " + contadorClientes + " / " + clientes.length);
        for (int i = 0; i < contadorClientes; i++) {
            System.out.println("\n--- Cliente #" + (i + 1) + " ---");
            clientes[i].mostrarEnConsola();
        }
        System.out.println("========================================================\n");

        JOptionPane.showMessageDialog(null,
                "Se han desplegado " + contadorClientes + " clientes en la consola del sistema (System.out).\nRevise la pestaña de salida de su entorno.",
                "Clientes Desplegados", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Opción 2: Muestra todas las cuentas y sus transacciones en la consola.
     */
    private static void mostrarCuentasYTransacciones() {
        if (contadorCuentas == 0) {
            JOptionPane.showMessageDialog(null, "No hay cuentas registradas en el sistema.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        System.out.println("========================================================");
        System.out.println("        LISTA DE CUENTAS BANCARIAS Y TRANSACCIONES      ");
        System.out.println("========================================================");
        System.out.println("Total de cuentas: " + contadorCuentas + " / " + cuentas.length);
        for (int i = 0; i < contadorCuentas; i++) {
            System.out.println("\n--- Cuenta #" + (i + 1) + " ---");
            cuentas[i].mostrarEnConsola();
        }
        System.out.println("========================================================\n");

        JOptionPane.showMessageDialog(null,
                "Se han desplegado " + contadorCuentas + " cuentas en la consola del sistema (System.out).\nRevise la pestaña de salida de su entorno.",
                "Cuentas Desplegadas", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Opción 3: Registro de un nuevo cliente con validaciones estrictas.
     */
    private static void agregarNuevoCliente() {
        if (contadorClientes >= clientes.length) {
            JOptionPane.showMessageDialog(null, "Capacidad máxima de clientes alcanzada (" + clientes.length + ").", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. ID y verificación de unicidad
        String id = pedirTexto("Ingrese la identificación (ID) del cliente:");
        if (id == null || id.trim().isEmpty()) {
            return;
        }
        id = id.trim();

        if (buscarClientePorId(id) != null) {
            JOptionPane.showMessageDialog(null, "Cliente ya agregado en el sistema (ID duplicado).", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Nombre Completo
        String nombre = pedirTexto("Ingrese el nombre completo del cliente:");
        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }
        nombre = nombre.trim();

        // Extraer el primer nombre para el usuario
        String primerNombre = nombre.split(" ")[0];

        // 3. Teléfono con formato estricto 0000-0000
        String telefono = pedirTexto("Ingrese el teléfono (formato 0000-0000):");
        if (telefono == null) {
            return;
        }
        telefono = telefono.trim();
        if (!telefono.matches("\\d{4}-\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Formato de teléfono inválido. Debe tener 8 dígitos con guion: 0000-0000.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Correo electrónico con '@' y '.'
        String correo = pedirTexto("Ingrese el correo electrónico:");
        if (correo == null) {
            return;
        }
        correo = correo.trim();
        int arrobaPos = correo.indexOf('@');
        int puntoPos = correo.lastIndexOf('.');
        if (arrobaPos <= 0 || puntoPos <= arrobaPos + 1 || puntoPos >= correo.length() - 1) {
            JOptionPane.showMessageDialog(null, "Correo inválido. Debe contener '@' y un punto posterior al '@'.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 5. Generación de credenciales automáticas
        String nuevoUsuario = primerNombre + consecutivoUsuario++;
        Cliente nuevoCliente = new Cliente(id, nombre, telefono, correo, nuevoUsuario);

        clientes[contadorClientes++] = nuevoCliente;

        System.out.println("\n[SISTEMA] Cliente registrado exitosamente:");
        nuevoCliente.mostrarEnConsola();
        nuevoCliente.imprimirTarjetaAcceso();

        JOptionPane.showMessageDialog(null,
                "¡Cliente registrado con éxito!\n\n"
                + "ID: " + nuevoCliente.getId() + "\n"
                + "Nombre: " + nuevoCliente.getNombreCompleto() + "\n"
                + "Usuario generado: " + nuevoCliente.getUsuario() + "\n"
                + "Clave: Sin configurar (se configurará en el primer acceso)\n"
                + "Estado: " + nuevoCliente.getEstado() + "\n"
                + "Tarjeta de acceso 4x5 generada en consola.",
                "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Opción 4: Registro de una nueva cuenta ligada a un cliente (Regla 0..1).
     */
    private static void agregarNuevaCuenta() {
        if (contadorCuentas >= cuentas.length) {
            JOptionPane.showMessageDialog(null, "Capacidad máxima de cuentas alcanzada (" + cuentas.length + ").", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Solicitar ID del cliente propietario
        String idCliente = pedirTexto("Ingrese el ID del cliente propietario de la cuenta:");
        if (idCliente == null || idCliente.trim().isEmpty()) {
            return;
        }
        idCliente = idCliente.trim();

        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Validación de regla de negocio 0..1 (Unicidad: un cliente sólo puede tener una cuenta)
        if (buscarCuentaPorCliente(cliente) != null) {
            JOptionPane.showMessageDialog(null, "El cliente ya posee una cuenta registrada.", "Validación 0..1", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Selección de Tipo de Cuenta
        String[] opcionesTipo = {"Corriente", "Ahorros", "Inversión", "Planilla"};
        int seleccionTipo = JOptionPane.showOptionDialog(
                null,
                "Seleccione el tipo de cuenta:",
                "Tipo de Cuenta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesTipo,
                opcionesTipo[0]
        );

        if (seleccionTipo == JOptionPane.CLOSED_OPTION) {
            return;
        }

        TipoCuenta tipoSeleccionado;
        switch (seleccionTipo) {
            case 0 -> tipoSeleccionado = TipoCuenta.Corriente;
            case 1 -> tipoSeleccionado = TipoCuenta.Ahorros;
            case 2 -> tipoSeleccionado = TipoCuenta.Inversion;
            default -> tipoSeleccionado = TipoCuenta.Planilla;
        }

        // 4. Saldo inicial (debe ser >= 0)
        double saldoInicial = -1;
        while (saldoInicial < 0) {
            String saldoStr = pedirTexto("Ingrese el saldo inicial (mayor o igual a cero):");
            if (saldoStr == null) {
                return;
            }
            try {
                saldoInicial = Double.parseDouble(saldoStr.trim());
                if (saldoInicial < 0) {
                    JOptionPane.showMessageDialog(null, "El saldo debe ser mayor o igual a cero.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // 5. Creación de la cuenta con consecutivo >= 4700 y fecha del sistema
        int numCuenta = consecutivoCuenta++;
        String fechaHoy = LocalDate.now().format(FORMATO_FECHA);

        Cuenta nuevaCuenta = new Cuenta(numCuenta, cliente, tipoSeleccionado, fechaHoy, saldoInicial);
        cuentas[contadorCuentas++] = nuevaCuenta;

        System.out.println("\n[SISTEMA] Cuenta creada exitosamente:");
        nuevaCuenta.mostrarEnConsola();

        JOptionPane.showMessageDialog(null,
                "¡Cuenta creada con éxito!\n\n"
                + "Número de Cuenta: " + nuevaCuenta.getNumeroCuenta() + "\n"
                + "Propietario: " + cliente.getNombreCompleto() + " (" + cliente.getId() + ")\n"
                + "Tipo: " + nuevaCuenta.getTipoCuenta() + "\n"
                + "Fecha Apertura: " + nuevaCuenta.getFechaApertura() + "\n"
                + "Saldo Inicial: $" + String.format("%.2f", nuevaCuenta.getSaldoActual()),
                "Cuenta Creada", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Opción 5: Buscar cliente por ID.
     */
    private static void buscarCliente() {
        String id = pedirTexto("Ingrese el ID del cliente a buscar:");
        if (id == null || id.trim().isEmpty()) {
            return;
        }
        id = id.trim();

        Cliente c = buscarClientePorId(id);
        if (c == null) {
            JOptionPane.showMessageDialog(null, "El cliente con el ID: " + id + " no se encuentra registrado en el sistema.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("\n[BÚSQUEDA] Cliente encontrado:");
        c.mostrarEnConsola();

        Cuenta cuentaAsociada = buscarCuentaPorCliente(c);
        String infoCuenta = (cuentaAsociada != null)
                ? "Cuenta #" + cuentaAsociada.getNumeroCuenta() + " (" + cuentaAsociada.getTipoCuenta() + ") - Saldo: $" + cuentaAsociada.getSaldoActual()
                : "Sin cuenta registrada";

        String[] opciones = {"[Actualizar]", c.getEstado() == EstadoCliente.Activo ? "[Desactivar]" : "[Activar]", "[Cancelar]"};
        int eleccion = JOptionPane.showOptionDialog(
                null,
                "Datos del Cliente:\n"
                + "ID: " + c.getId() + "\n"
                + "Nombre: " + c.getNombreCompleto() + "\n"
                + "Teléfono: " + c.getTelefono() + "\n"
                + "Correo: " + c.getCorreo() + "\n"
                + "Usuario: " + c.getUsuario() + "\n"
                + "Estado: " + c.getEstado() + "\n"
                + "Cuenta: " + infoCuenta + "\n\n"
                + "¿Desea realizar alguna acción?",
                "Detalle de Cliente",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (eleccion == 0) { // Actualizar
            actualizarDatosCliente(c);
        } else if (eleccion == 1) { // Alternar Estado
            if (c.getEstado() == EstadoCliente.Activo) {
                c.setEstado(EstadoCliente.Inactivo);
                JOptionPane.showMessageDialog(null, "Cliente desactivado exitosamente.");
            } else {
                c.setEstado(EstadoCliente.Activo);
                JOptionPane.showMessageDialog(null, "Cliente activado exitosamente.");
            }
        }
    }

    /**
     * Opción 6: Buscar cuenta por número de cuenta.
     */
    private static void buscarCuenta() {
        String input = pedirTexto("Ingrese el número de cuenta a buscar (>= 4700):");
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        int numCuenta;
        try {
            numCuenta = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de cuenta inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cuenta q = buscarCuentaPorNumero(numCuenta);
        if (q == null) {
            JOptionPane.showMessageDialog(null, "La cuenta con el número: " + numCuenta + " no se encuentra registrada en el sistema.", "No Encontrada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("\n[BÚSQUEDA] Cuenta encontrada:");
        q.mostrarEnConsola();

        String[] opciones = {"[Ver en Consola]", "[Cancelar]"};
        int eleccion = JOptionPane.showOptionDialog(
                null,
                "Datos de la Cuenta:\n"
                + "Número: " + q.getNumeroCuenta() + "\n"
                + "Propietario: " + (q.getPropietario() != null ? q.getPropietario().getNombreCompleto() : "N/A") + "\n"
                + "Tipo: " + q.getTipoCuenta() + "\n"
                + "Fecha Apertura: " + q.getFechaApertura() + "\n"
                + "Saldo Actual: $" + String.format("%.2f", q.getSaldoActual()) + "\n"
                + "Transacciones: " + q.getContadorTransacciones() + "\n",
                "Detalle de Cuenta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (eleccion == 0) {
            q.mostrarEnConsola();
            JOptionPane.showMessageDialog(null, "Historial y detalles impresos en consola.");
        }
    }

    // =========================================================================
    //  MÓDULO 2: MENÚ CLIENTES (PORTAL DE AUTOSERVICIO Y LOGIN)
    // =========================================================================

    /**
     * Flujo de autenticación del cliente:
     * 1. Solicita usuario o ID.
     * 2. Valida estado activo.
     * 3. Configura clave si está vacía o valida contraseña.
     * 4. Muestra tarjeta de acceso y accede al submenú del cliente.
     */
    private static void iniciarSesionCliente() {
        String usuarioOId = pedirTexto("Ingrese su nombre de usuario o número de ID:");
        if (usuarioOId == null || usuarioOId.trim().isEmpty()) {
            return;
        }
        usuarioOId = usuarioOId.trim();

        Cliente cliente = buscarClientePorUsuarioOId(usuarioOId);
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "No hay ningún cliente con el usuario o ID: " + usuarioOId, "Error de Acceso", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cliente.getEstado() == EstadoCliente.Inactivo) {
            JOptionPane.showMessageDialog(null, "El cliente está inactivo. Contacte al banco.", "Acceso Bloqueado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si la clave no está configurada, solicitar configuración inicial
        if (cliente.getClave() == null || cliente.getClave().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Bienvenido(a) " + cliente.getNombreCompleto() + ".\nEsta es su primera autenticación. Por favor configure su contraseña.",
                    "Configuración de Clave", JOptionPane.INFORMATION_MESSAGE);

            boolean claveValida = false;
            while (!claveValida) {
                String nuevaClave = pedirTexto("Ingrese su nueva clave (6-10 caracteres, letras y números):");
                if (nuevaClave == null) {
                    return;
                }
                nuevaClave = nuevaClave.trim();

                // Validaciones de seguridad: 6-10 caracteres, al menos un dígito y al menos una letra
                boolean cumpleLongitud = nuevaClave.length() >= 6 && nuevaClave.length() <= 10;
                boolean tieneNumero = nuevaClave.matches(".*\\d.*");
                boolean tieneLetra = nuevaClave.matches(".*[a-zA-Z].*");

                if (!cumpleLongitud || !tieneNumero || !tieneLetra) {
                    int reintentar = JOptionPane.showOptionDialog(
                            null,
                            "La clave no cumple con las condiciones mínimas de seguridad:\n"
                            + "- Entre 6 y 10 caracteres\n"
                            + "- Al menos una letra (a-z, A-Z)\n"
                            + "- Al menos un número (0-9)",
                            "Clave Insegura",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            new String[]{"[Agregar otra clave]", "[Cancelar]"},
                            "[Agregar otra clave]"
                    );
                    if (reintentar != 0) {
                        return;
                    }
                    continue;
                }

                // Confirmación de clave
                String confirmacion = pedirTexto("Confirme de nuevo su clave:");
                if (confirmacion == null) {
                    return;
                }
                if (!nuevaClave.equals(confirmacion.trim())) {
                    JOptionPane.showMessageDialog(null, "Las claves no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                cliente.setClave(nuevaClave);
                claveValida = true;
                JOptionPane.showMessageDialog(null, "¡Contraseña configurada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // Verificación de clave con hasta 3 intentos fallidos
            int intentos = 0;
            boolean autenticado = false;
            while (intentos < 3 && !autenticado) {
                String claveIngresada = pedirTexto("Ingrese su clave de acceso (Intento " + (intentos + 1) + "/3):");
                if (claveIngresada == null) {
                    return;
                }
                if (cliente.getClave().equals(claveIngresada.trim())) {
                    autenticado = true;
                } else {
                    intentos++;
                    if (intentos < 3) {
                        JOptionPane.showMessageDialog(null, "Clave incorrecta. Intentos restantes: " + (3 - intentos), "Error", JOptionPane.WARNING_MESSAGE);
                    } else {
                        cliente.setEstado(EstadoCliente.Inactivo);
                        JOptionPane.showMessageDialog(null, "Ha superado los 3 intentos fallidos. Su usuario ha sido bloqueado (Inactivo).", "Bloqueo de Seguridad", JOptionPane.ERROR_MESSAGE);
                        System.out.println("\n[SEGURIDAD] Cliente bloqueado por intentos fallidos: " + cliente.getId() + " - " + cliente.getNombreCompleto());
                        return;
                    }
                }
            }
        }

        // Mostrar tarjeta de acceso en consola para referencia
        System.out.println("\n[AUTENTICACIÓN] Tarjeta de Acceso del cliente " + cliente.getNombreCompleto() + ":");
        cliente.imprimirTarjetaAcceso();

        // Acceder al menú del cliente autenticado
        menuCliente(cliente);
    }

    /**
     * Submenú del cliente autenticado.
     */
    private static void menuCliente(Cliente cliente) {
        int opt = 0;
        do {
            String menu = """
                    ---- Menú Cliente: %s ----
                    1. Transacciones
                    2. Mi cuenta
                    3. Actualizar datos
                    4. Volver a menú principal
                    
                    Seleccione una opción:
                    """.formatted(cliente.getNombreCompleto());

            opt = pedirEntero(menu);

            switch (opt) {
                case 1:
                    gestionarTransaccionesCliente(cliente);
                    break;
                case 2:
                    mostrarMiCuenta(cliente);
                    break;
                case 3:
                    actualizarDatosCliente(cliente);
                    break;
                case 4:
                case -1:
                    opt = 4;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.", "Error", JOptionPane.WARNING_MESSAGE);
            }

        } while (opt != 4);
    }

    /**
     * Muestra la cuenta del cliente autenticado en consola y pantalla.
     */
    private static void mostrarMiCuenta(Cliente cliente) {
        Cuenta cuenta = buscarCuentaPorCliente(cliente);
        if (cuenta == null) {
            JOptionPane.showMessageDialog(null, "El cliente no posee una cuenta registrada.", "Sin Cuenta", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        System.out.println("\n========================================================");
        System.out.println("                 INFORMACIÓN DE MI CUENTA               ");
        System.out.println("========================================================");
        cuenta.mostrarEnConsola();
        System.out.println("========================================================\n");

        JOptionPane.showMessageDialog(null,
                "--- MI CUENTA ---\n"
                + "Número de Cuenta: " + cuenta.getNumeroCuenta() + "\n"
                + "Tipo de Cuenta: " + cuenta.getTipoCuenta() + "\n"
                + "Fecha de Apertura: " + cuenta.getFechaApertura() + "\n"
                + "Saldo Inicial: $" + String.format("%.2f", cuenta.getSaldoInicial()) + "\n"
                + "Saldo Actual: $" + String.format("%.2f", cuenta.getSaldoActual()) + "\n"
                + "Transacciones Registradas: " + cuenta.getContadorTransacciones() + "\n\n"
                + "Detalles completos impresos en la consola.",
                "Mi Cuenta", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Operaciones transaccionales básicas del cliente (Depósito, Retiro).
     */
    private static void gestionarTransaccionesCliente(Cliente cliente) {
        Cuenta cuenta = buscarCuentaPorCliente(cliente);
        if (cuenta == null) {
            JOptionPane.showMessageDialog(null, "El cliente no posee una cuenta registrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] opciones = {"[DEPÓSITO]", "[RETIRO]", "[CANCELAR]"};
        int eleccion = JOptionPane.showOptionDialog(
                null,
                "Saldo actual disponible: $" + String.format("%.2f", cuenta.getSaldoActual()) + "\nSeleccione la operación a realizar:",
                "Módulo de Transacciones",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        String fechaHoy = LocalDate.now().format(FORMATO_FECHA);

        if (eleccion == 0) { // Depósito
            String montoStr = pedirTexto("Ingrese el monto a depositar:");
            if (montoStr == null) return;
            try {
                double monto = Double.parseDouble(montoStr.trim());
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(null, "El monto debe ser mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                cuenta.setSaldoActual(cuenta.getSaldoActual() + monto);
                Transaccion t = new Transaccion(consecutivoTransaccion++, fechaHoy, TipoTransaccion.Deposito, monto, "Depósito en efectivo", cuenta.getSaldoActual());
                cuenta.agregarTransaccion(t);

                System.out.println("\n[TRANSACCIÓN REALIZADA]");
                t.mostrarEnConsola();
                JOptionPane.showMessageDialog(null, "¡Depósito exitoso!\nNuevo saldo: $" + String.format("%.2f", cuenta.getSaldoActual()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (eleccion == 1) { // Retiro
            String montoStr = pedirTexto("Ingrese el monto a retirar:");
            if (montoStr == null) return;
            try {
                double monto = Double.parseDouble(montoStr.trim());
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(null, "El monto debe ser mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (monto > cuenta.getSaldoActual()) {
                    JOptionPane.showMessageDialog(null, "Saldo insuficiente. Saldo actual: $" + String.format("%.2f", cuenta.getSaldoActual()), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                cuenta.setSaldoActual(cuenta.getSaldoActual() - monto);
                Transaccion t = new Transaccion(consecutivoTransaccion++, fechaHoy, TipoTransaccion.Retiro, monto, "Retiro en efectivo", cuenta.getSaldoActual());
                cuenta.agregarTransaccion(t);

                System.out.println("\n[TRANSACCIÓN REALIZADA]");
                t.mostrarEnConsola();
                JOptionPane.showMessageDialog(null, "¡Retiro exitoso!\nNuevo saldo: $" + String.format("%.2f", cuenta.getSaldoActual()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Permite actualizar el nombre, teléfono o correo de un cliente.
     */
    private static void actualizarDatosCliente(Cliente c) {
        String[] opciones = {"Nombre", "Teléfono", "Correo", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(
                null,
                "Seleccione el dato a actualizar para " + c.getNombreCompleto() + ":",
                "Actualizar Datos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (eleccion == 0) {
            String nuevoNombre = pedirTexto("Nuevo nombre completo:");
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                c.setNombreCompleto(nuevoNombre.trim());
                JOptionPane.showMessageDialog(null, "Nombre actualizado correctamente.");
            }
        } else if (eleccion == 1) {
            String nuevoTel = pedirTexto("Nuevo teléfono (formato 0000-0000):");
            if (nuevoTel != null && nuevoTel.trim().matches("\\d{4}-\\d{4}")) {
                c.setTelefono(nuevoTel.trim());
                JOptionPane.showMessageDialog(null, "Teléfono actualizado correctamente.");
            } else if (nuevoTel != null) {
                JOptionPane.showMessageDialog(null, "Formato inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (eleccion == 2) {
            String nuevoCorreo = pedirTexto("Nuevo correo electrónico:");
            if (nuevoCorreo != null && nuevoCorreo.contains("@") && nuevoCorreo.contains(".")) {
                c.setCorreo(nuevoCorreo.trim());
                JOptionPane.showMessageDialog(null, "Correo actualizado correctamente.");
            } else if (nuevoCorreo != null) {
                JOptionPane.showMessageDialog(null, "Correo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // =========================================================================
    //  MÉTODOS DE BÚSQUEDA Y CONTROL DE DOMINIO (REGLAS 0..1 Y UNICIDAD)
    // =========================================================================

    public static Cliente buscarClientePorId(String id) {
        if (id == null) return null;
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i] != null && clientes[i].getId().equalsIgnoreCase(id.trim())) {
                return clientes[i];
            }
        }
        return null;
    }

    public static Cliente buscarClientePorUsuarioOId(String identificador) {
        if (identificador == null) return null;
        String busqueda = identificador.trim();
        for (int i = 0; i < contadorClientes; i++) {
            if (clientes[i] != null) {
                if (clientes[i].getUsuario().equalsIgnoreCase(busqueda) || clientes[i].getId().equalsIgnoreCase(busqueda)) {
                    return clientes[i];
                }
            }
        }
        return null;
    }

    public static Cuenta buscarCuentaPorNumero(int numeroCuenta) {
        for (int i = 0; i < contadorCuentas; i++) {
            if (cuentas[i] != null && cuentas[i].getNumeroCuenta() == numeroCuenta) {
                return cuentas[i];
            }
        }
        return null;
    }

    /**
     * Búsqueda por Inversión de Referencia:
     * Recorre el arreglo Cuenta[] y compara el ID del propietario (Regla 0..1).
     */
    public static Cuenta buscarCuentaPorCliente(Cliente cliente) {
        if (cliente == null) return null;
        for (int i = 0; i < contadorCuentas; i++) {
            if (cuentas[i] != null && cuentas[i].getPropietario() != null) {
                if (cuentas[i].getPropietario().getId().equalsIgnoreCase(cliente.getId())) {
                    return cuentas[i];
                }
            }
        }
        return null; // El cliente no posee cuenta (Regla 0..1)
    }

    // =========================================================================
    //  MÉTODOS AUXILIARES SEGUROS PARA ENTRADA CON JOPTIONPANE
    // =========================================================================

    private static int pedirEntero(String mensaje) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, mensaje, "HiperBanco", JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                return -1; // Usuario canceló o cerró el diálogo
            }
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número entero válido.", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static String pedirTexto(String mensaje) {
        String input = JOptionPane.showInputDialog(null, mensaje, "HiperBanco", JOptionPane.QUESTION_MESSAGE);
        if (input == null) {
            return null; // Usuario canceló
        }
        return input.trim();
    }
}
