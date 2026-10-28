# **ESPECIFICACIONES TÉCNICAS Y REQUERIMIENTOS DE SOFTWARE**

# **Sistema Bancario Core en Java — "HiperBanco"**

Documento unificado de arquitectura, modelo de dominio, lógica de control, diagramas de flujo y guía de implementación para desarrollo con Swing/JOptionPane y arreglos estáticos en Java.

---

# **1\. DESCRIPCIÓN GENERAL Y CAPACIDADES DEL SISTEMA**

El sistema **HiperBanco** es una solución de software monolítica desarrollada en Java para la gestión integral de clientes, cuentas bancarias, procesamiento de transacciones financieras y generación de reportes analíticos. El sistema opera completamente mediante la arquitectura de **Programación Orientada a Objetos (POO)** y la interfaz de usuario se gestiona mediante cuadros de diálogo interactivos contextuales (`JOptionPane`), manteniendo las salidas detalladas y los registros impresos en la consola del sistema (System.out).

## **1.1 Restricciones de Almacenamiento y Persistencia**

* **Memoria Volátil:** El sistema opera **exclusivamente en memoria RAM** durante el ciclo de vida del proceso de la Java Virtual Machine (JVM). No se permite el uso de Bases de Datos Relacionales (SQL), NoSQL, persistencia en disco mediante archivos (`.txt`, `.json`, `.ser`) ni sistemas de archivos remotos.  
* **Estructuras Fijas:** Se prohíbe el uso del framework de colecciones dinámicas de Java (`java.util.ArrayList`, `java.util.HashMap`, `java.util.LinkedList`, etc.). El almacenamiento dinámico de datos se simula mediante **arreglos unidimensionales estáticos** de tamaño fijo y **matrices bidimensionales**.

## **1.2 Resumen de Capacidades del Sistema**

| Módulo / Elemento | Responsabilidad Principal | Capacidad Máxima y Estructura |
| :---- | :---- | :---- |
| **Cliente** | Entidad de identidad, credenciales, estado de acceso y matriz de seguridad. | Arreglo `Cliente[30]`. (Avance 1: hasta 2 instancias). |
| **Cuenta** | Entidad bancaria, balance financiero, tipo de producto y registro histórico de movimientos. | Arreglo `Cuenta[30]`. Relación máxima de 1 cuenta por cliente. |
| **Transaccion** | Registro auditable de cambios de saldo (Depósitos, Retiros, Compras, Transferencias). | Arreglo interno por cuenta `Transaccion[50]`. |
| **Tarjeta de Acceso** | Matriz de seguridad bidimensional de doble factor para autenticación. | Matriz estática de números enteros `int[4][5]` por cliente. |

## **1.3 Regla de Oro Architectural: Relación de Dominio Cliente \- Cuenta**

1. **Multiplicidad Opción Cero a Uno (0..1):** Un objeto `Cliente` puede existir sin tener asignada una `Cuenta`. Si posee una `Cuenta`, esta debe ser **única**. Ningún cliente puede ser propietario de dos o más cuentas dentro del sistema.  
2. **Navegabilidad Unidireccional Directa:** Para prevenir ciclos de dependencia circular en memoria y problemas de serialización en consola:  
   * La clase `Cuenta` almacena una referencia explícita a su propietario mediante el atributo `private Cliente propietario`.  
   * La clase `Cliente` **NO** tiene ningún atributo o referencia hacia la clase `Cuenta`.  
3. **Búsqueda por Inversión de Referencia:** Para determinar la cuenta de un cliente, el controlador debe iterar el arreglo global `Cuenta[]` y comparar si `cuentas[i].getPropietario() == clienteObjetivo` o comparar sus IDs únicos.  
4. **Validación de Unicidad:** Antes de instanciar una nueva cuenta, el sistema debe garantizar mediante un método de búsqueda que el cliente seleccionado no esté ya asociado a una cuenta existente en el arreglo global de cuentas.

---

# **2\. ARQUITECTURA POO Y CONSTRUCCIÓN INTERNA DE CLASES**

Todas las clases del dominio deben seguir el principio de **Encapsulamiento**. Los atributos serán declarados con el modificador de acceso `private`, exponiéndose únicamente mediante métodos de acceso (`getters`) y modificación (`setters`) que incluyan las validaciones del negocio correspondiente.       \+--------------------+                    \+-----------------------+

       \|      Cliente       \|                    \|        Cuenta         \|

       \+--------------------+                    \+-----------------------+

       \| \- id: String       \| 1                0..1 \| \- numeroCuenta: int   \|

       \| \- nombre: String   \| \<-------------------\| \- saldoActual: double \|

       \| \- clave: String    \|  (propietario)     \| \- tipo: TipoCuenta    \|

       \| \- estado: Estado   \|                    \+-----------------------+

       \+--------------------+                                \| 1

                                                             \|

                                                             \| 0..50

                                                             v

                                                 \+-----------------------+

                                                 \|      Transaccion      \|

                                                 \+-----------------------+

                                                 \| \- numeroTrans: int    \|

                                                 \| \- monto: double       \|

                                                 \| \- tipo: TipoTrans...  \|

                                                 \+-----------------------+

## **2.1 Enumerados (`enum`)**

Se definen tres enumerados tipados para restringir el dominio de datos y evitar inconsistencias por cadenas arbitrarias. La selección de estos valores en la interfaz se realizará mediante botones personalizados en diálogos de `JOptionPane`.public enum EstadoCliente {

    Activo,

    Inactivo

}

public enum TipoCuenta {

    Corriente,

    Ahorros,

    Inversion,

    Planilla

}

public enum TipoTransaccion {

    Deposito,

    Retiro,

    Transferencia\_enviada,

    Transferencia\_recibida,

    Compra

}

## **2.2 Clase `Cliente`**

### **Atributos Encapsulados**

* `private String id`: Identificación única nacional o pasaporte. No modificable tras la creación.  
* `private String nombreCompleto`: Nombre y apellidos del cliente.  
* `private String telefono`: Teléfono de contacto con formato obligatorio `0000-0000`.  
* `private String correo`: Correo electrónico personal.  
* `private String usuario`: Identificador de inicio de sesión autogenerado (`PrimerNombre + ConsecutivoNum`).  
* `private String clave`: Contraseña de acceso. Inicializada como cadena vacía `""`.  
* `private EstadoCliente estado`: Estado operativo del cliente (`Activo` o `Inactivo`).  
* `private int[][] tarjetaAcceso`: Matriz bidimensional de \$4 \\times 5\$ cargada con enteros aleatorios en el rango \$\[10, 99\]\$.

### **Definición Técnica y Métodos**

public class Cliente {

    private String id;

    private String nombreCompleto;

    private String telefono;

    private String correo;

    private String usuario;

    private String clave;

    private EstadoCliente estado;

    private int\[\]\[\] tarjetaAcceso;

    public Cliente(String id, String nombreCompleto, String telefono, String correo, String usuario) {

        this.id \= id;

        this.nombreCompleto \= nombreCompleto;

        this.telefono \= telefono;

        this.correo \= correo;

        this.usuario \= usuario;

        this.clave \= ""; // Clave inicial vacía obligatoria

        this.estado \= EstadoCliente.Activo;

        this.tarjetaAcceso \= generarTarjetaAcceso();

    }

    private int\[\]\[\] generarTarjetaAcceso() {

        int\[\]\[\] matriz \= new int\[4\]\[5\];

        for (int i \= 0; i \< 4; i++) {

            for (int j \= 0; j \< 5; j++) {

                matriz\[i\]\[j\] \= (int) (Math.random() \* 90\) \+ 10; // Rango 10 \- 99

            }

        }

        return matriz;

    }

    // Getters y Setters completos

    public String getId() { return id; }

    public String getNombreCompleto() { return nombreCompleto; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto \= nombreCompleto; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono \= telefono; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo \= correo; }

    public String getUsuario() { return usuario; }

    public String getClave() { return clave; }

    public void setClave(String clave) { this.clave \= clave; }

    public EstadoCliente getEstado() { return estado; }

    public void setEstado(EstadoCliente estado) { this.estado \= estado; }

    public int\[\]\[\] getTarjetaAcceso() { return tarjetaAcceso; }

}

## **2.3 Clase `Cuenta`**

### **Atributos Encapsulados**

* `private int numeroCuenta`: Autoincremental único a partir de `4700`.  
* `private Cliente propietario`: Referencia directa al objeto `Cliente`.  
* `private TipoCuenta tipoCuenta`: Valor del enumerado `TipoCuenta`.  
* `private String fechaApertura`: Fecha del sistema formato `dd/MM/yyyy`.  
* `private double saldoInicial`: Monto de apertura (\$\\ge 0\$).  
* `private double saldoActual`: Balance disponible para operaciones (\$\\ge 0\$).  
* `private Transaccion[] transacciones`: Arreglo estático de tamaño fijo `new Transaccion[50]`.  
* `private int contadorTransacciones`: Apuntador al número de transacciones registradas.

### **Definición Técnica y Métodos**

public class Cuenta {

    private int numeroCuenta;

    private Cliente propietario;

    private TipoCuenta tipoCuenta;

    private String fechaApertura;

    private double saldoInicial;

    private double saldoActual;

    private Transaccion\[\] transacciones;

    private int contadorTransacciones;

    public Cuenta(int numeroCuenta, Cliente propietario, TipoCuenta tipoCuenta, String fechaApertura, double saldoInicial) {

        this.numeroCuenta \= numeroCuenta;

        this.propietario \= propietario;

        this.tipoCuenta \= tipoCuenta;

        this.fechaApertura \= fechaApertura;

        this.saldoInicial \= saldoInicial;

        this.saldoActual \= saldoInicial;

        this.transacciones \= new Transaccion\[50\];

        this.contadorTransacciones \= 0;

    }

    public boolean agregarTransaccion(Transaccion t) {

        if (contadorTransacciones \< 50\) {

            transacciones\[contadorTransacciones\] \= t;

            contadorTransacciones++;

            return true;

        }

        return false; // Arreglo de transacciones lleno

    }

    // Getters y Setters

    public int getNumeroCuenta() { return numeroCuenta; }

    public Cliente getPropietario() { return propietario; }

    public TipoCuenta getTipoCuenta() { return tipoCuenta; }

    public String getFechaApertura() { return fechaApertura; }

    public double getSaldoInicial() { return saldoInicial; }

    public double getSaldoActual() { return saldoActual; }

    public void setSaldoActual(double saldoActual) { this.saldoActual \= saldoActual; }

    public Transaccion\[\] getTransacciones() { return transacciones; }

    public int getContadorTransacciones() { return contadorTransacciones; }

}

## **2.4 Clase `Transaccion`**

### **Atributos Encapsulados**

* `private int numeroTransaccion`: Autoincremental único a partir de `10000`.  
* `private String fecha`: Fecha del sistema de la operación.  
* `private TipoTransaccion tipo`: Clasificación de la transacción.  
* `private double monto`: Valor monetario positivo (\$\> 0\$).  
* `private String detalle`: Contexto o descripción operativa.  
* `private double saldoResultante`: Balance existente en la cuenta post-operación.

### **Definición Técnica y Métodos**

public class Transaccion {

    private int numeroTransaccion;

    private String fecha;

    private TipoTransaccion tipo;

    private double monto;

    private String detalle;

    private double saldoResultante;

    public Transaccion(int numeroTransaccion, String fecha, TipoTransaccion tipo, double monto, String detalle, double saldoResultante) {

        this.numeroTransaccion \= numeroTransaccion;

        this.fecha \= fecha;

        this.tipo \= tipo;

        this.monto \= monto;

        this.detalle \= detalle;

        this.saldoResultante \= saldoResultante;

    }

    // Getters

    public int getNumeroTransaccion() { return numeroTransaccion; }

    public String getFecha() { return fecha; }

    public TipoTransaccion getTipo() { return tipo; }

    public double getMonto() { return monto; }

    public String getDetalle() { return detalle; }

    public double getSaldoResultante() { return saldoResultante; }

}

---

# **3\. PRECARGA AUTOMÁTICA DE DATOS (AVANCE 2 \- SEMANA 11\)**

Al iniciarse el programa principal, se ejecuta un método estático de inicialización que poblará los arreglos `Cliente[30]` y `Cuenta[30]`. Los consecutivos globales del sistema deben quedar configurados para continuar linealmente desde el último valor precargado.

## **3.1 Requisitos Estrictos de la Precarga**

1. **Clientes:** Cargar exactamente 10 instancias en el arreglo `Cliente[]`.  
2. **Cuentas:** Cargar exactamente 9 instancias en el arreglo `Cuenta[]`. El cliente \$10^{\\circ}\$ debe quedar intencionalmente sin cuenta.  
3. **Variedad de Tipos:** Se deben instanciar cuentas con los 4 tipos del enum (`Corriente`, `Ahorros`, `Inversion`, `Planilla`).  
4. **Estados de Cliente:** Al menos 1 de los clientes con cuenta asociada debe ser precargado en estado `Inactivo`.  
5. **Transacciones Historizadas:** Distribuir operaciones reales en las cuentas, calculando correctamente el `saldoResultante`. Al menos 1 cuenta debe quedar con 0 transacciones (únicamente `saldoInicial`).  
6. **Integridad de Consecutivos:**  
   * **Consecutivo Usuarios:** Inicia en `40` (El cliente 10 tendrá el usuario con sufijo `49`). Siguiente disponible: `50`.  
   * **Consecutivo Cuentas:** Inicia en `4700` (La cuenta 9 tendrá el ID `4708`). Siguiente disponible: `4709`.  
   * **Consecutivo Transacciones:** Inicia en `10000`. Si se precargan \$N\$ transacciones, el siguiente ID autogenerado será \$10000 \+ N\$.  
7. **Estado de Claves:** Al menos un cliente `Activo` debe poseer la clave vacía `""` para pruebas de la primera autenticación. Los demás deben poseer claves válidas (ej: `"Pass123"`).

## **3.2 Tabla de Datos de Precarga Recomendada**

| \# | Cliente (ID / Nombre) | Usuario | Estado | Cuenta | Tipo Cuenta | Saldo Inicial | Saldo Actual | Transacciones Creadas |
| :---- | :---- | :---- | :---- | :---- | :---- | :---- | :---- | :---- |
| **1** | `1011` \- Carlos Ruiz | `Carlos40` | `Activo` | `4700` | `Ahorros` | \$500.00 | \$850.00 | \+\$350.00 (Depósito) |
| **2** | `1022` \- Maria Lopez | `Maria41` | `Activo` | `4701` | `Corriente` | \$1,200.00 | \$1,000.00 | \-\$200.00 (Retiro) |
| **3** | `1033` \- Juan Gomez | `Juan42` | `Inactivo` | `4702` | `Planilla` | \$300.00 | \$300.00 | *Sin transacciones* |
| **4** | `1044` \- Ana Castro | `Ana43` | `Activo` | `4703` | `Inversion` | \$5,000.00 | \$6,500.00 | \+\$1,500.00 (Depósito) |
| **5** | `1055` \- Luis Morales | `Luis44` | `Activo` | `4704` | `Ahorros` | \$150.00 | \$100.00 | \-\$50.00 (Compra) |
| **6** | `1066` \- Sofia Solis | `Sofia45` | `Activo` | `4705` | `Corriente` | \$800.00 | \$1,100.00 | \+\$300.00 (Depósito) |
| **7** | `1077` \- Diego Vargas | `Diego46` | `Activo` | `4706` | `Planilla` | \$450.00 | \$350.00 | \-\$100.00 (Retiro) |
| **8** | `1088` \- Elena Delgado | `Elena47` | `Activo` | `4707` | `Inversion` | \$2,000.00 | \$2,000.00 | *Sin transacciones* |
| **9** | `1099` \- Roberto Solano | `Roberto48` | `Activo` | `4708` | `Ahorros` | \$90.00 | \$140.00 | \+\$50.00 (Depósito) |
| **10** | `1100` \- Lucia Mendez | `Lucia49` | `Activo` | *Ninguna* | N/A | N/A | N/A | N/A |

---

# **4\. FLUJO DE PANTALLAS Y MENÚS INTERACTIVOS (`JOptionPane`)**

La interfaz de usuario del sistema debe implementarse usando diálogos de selección visual de JOptionPane mediante JOptionPane.showOptionDialog o JOptionPane.showInputDialog.

## **4.1 Pantalla Principal de Entrada**

Ciclo do-while que ejecuta la entrada inicial:

* Botones principales: **`[BANCO]`**, **`[CLIENTES]`**, **`[SALIR]`**.  
* Si el usuario selecciona `[SALIR]` o cierra la ventana (`CLOSED_OPTION`), el sistema imprime un mensaje de despedida en consola y ejecuta `System.exit(0)`.

String\[\] opciones \= {"\[BANCO\]", "\[CLIENTES\]", "\[SALIR\]"};

int seleccion \= JOptionPane.showOptionDialog(

    null, "Seleccione el módulo de acceso:", "HiperBanco \- Sistema Principal",

    JOptionPane.DEFAULT\_OPTION, JOptionPane.INFORMATION\_MESSAGE, null, opciones, opciones\[0\]

);

---

## **4.2 Módulo `[BANCO]` (Menú Administrativo)**

Despliega un menú con 8 opciones en formato de botones o lista desplegable de selección:\[1. Mostrar Clientes\]          \[2. Mostrar Cuentas/Trans\]

\[3. Agregar Nuevo Cliente\]     \[4. Agregar Nueva Cuenta\]

\[5. Buscar Cliente\]            \[6. Buscar Cuenta\]

\[7. Generar Reportes\]          \[8. Volver\]

### **Reglas de Negocio Operativas de cada Opción:**

1. **Mostrar Clientes:**  
   * Recorre `Cliente[]` imprimiendo en consola.  
   * Formato de clave: Si es `""`, imprime `"Sin configurar"`. Si contiene caracteres, imprime asteriscos `****` equivalentes a la longitud real de la clave (`clave.replaceAll(".", "*")`).  
2. **Mostrar Cuentas y Transacciones:**  
   * Recorre `Cuenta[]` imprimiendo en consola.  
   * Si la cuenta no tiene transacciones (`contadorTransacciones == 0`), imprime `"Sin transacciones"`. De lo contrario, itera el sub-arreglo imprimiendo cada objeto `Transaccion`.  
3. **Agregar Nuevo Cliente:**  
   * **ID:** Solicita y verifica unicidad en `Cliente[]`. Si existe: `"Cliente ya agregado en el sistema"`.  
   * **Nombre Completo:** Entrada estándar. Extrae el primer nombre previo al primer espacio para el `usuario`.  
   * **Teléfono:** Valida expresión regular o formato estricto `0000-0000` (\$8\$ dígitos y guion en posición índice \$4\$).  
   * **Correo:** Valida existencia de `'@'` y un punto `'.'` posterior al `'@'`. Error: `"Correo inválido"`.  
   * **Generación de credenciales:** Asigna `usuario = PrimerNombre + consecutivoUsuarios++`, `clave = ""`, `estado = Activo` y genera matriz aleatoria de \$4 \\times 5\$.  
4. **Agregar Nueva Cuenta:**  
   * **Propietario:** Solicita el ID del cliente. Si no lo encuentra: `"Cliente no encontrado"`.  
   * **Validación 0..1:** Busca en `Cuenta[]` si el ID ingresado ya es propietario de una cuenta. Si la tiene: `"El cliente ya posee una cuenta registrada"`.  
   * **Tipo de Cuenta:** Botones `[Cuenta corriente]`, `[Ahorros]`, `[Inversión]`, `[Planilla]`.  
   * **Saldo Inicial:** Solicita valor numérico. Si es \$\< 0\$: `"El saldo debe ser mayor o igual a cero"`.  
   * Autogenera `numeroCuenta` consecutivo (\$\\ge 4700\$) y la fecha actual del sistema.  
5. **Buscar Cliente:**  
   * Solicita ID. Si no existe: `"El cliente con el ID: XXXX no se encuentra registrado en el sistema"`.  
   * Si existe, presenta sus datos y los botones:  
     * `[Actualizar]`: Submenú para actualizar Nombre, Teléfono o Correo. (El `usuario` original **permanece inmutable**).  
     * `[Desactivar]` / `[Activar]`: Botón contextual según `estado`. Cambia el estado del objeto.  
     * `[Cancelar]`: Vuelve al Menú Bancario.  
6. **Buscar Cuenta:**  
   * Solicita número de cuenta. Si no existe: `"La cuenta con el número: XXXX no se encuentra registrada en el sistema"`.  
   * Si existe, muestra datos generales y botones: `[Transacciones]` (Imprime historial en consola) y `[Cancelar]`.  
7. **Generar Reportes:** Abre el submenú de 4 reportes (detallado en sección 4.2.1).  
8. **Volver:** Regresa a la Pantalla Principal.

---

### **4.2.1 Submódulo de Reportes Analíticos**

                                  \+-----------------------+

                                  \|   Menú de Reportes    \|

                                  \+-----------------------+

                                              \|

      \+-------------------+-------------------+-------------------+-------------------+

      \|                   \|                   \|                   \|                   \|

      v                   v                   v                   v                   v

\+-----------+       \+-----------+       \+-----------+       \+-----------+       \+-----------+

\| Reporte 1 \|       \| Reporte 2 \|       \| Reporte 3 \|       \| Volver    \|       \| Cancelar  \|

\+-----------+       \+-----------+       \+-----------+       \+-----------+       \+-----------+

* **Reporte 1: Clientes por Estado y Tipo de Cuenta**  
  * **Filtro Estado:** Botones `[Activo]`, `[Inactivo]`, `[Todos]`.  
  * **Filtro Tipo Cuenta:** Botones `[Corriente]`, `[Ahorros]`, `[Inversión]`, `[Planilla]`, `[Todos]`.  
  * **Salida Consola:** Lista ID, Nombre, Estado, Número de Cuenta, Tipo y Saldo Actual. Si el cliente no posee cuenta y el filtro de tipo es `[Todos]`, debe incluirse indicando `"Sin cuenta"`. Muestra el total de registros coincidentes.  
* **Reporte 2: Cuentas por Rango de Saldo**  
  * **Entradas:** Saldo Mínimo (\$\\ge 0\$) y Saldo Máximo (\$\\ge \\text{Saldo Mínimo}\$).  
  * **Filtro Tipo Cuenta:** Mismos botones de tipo de cuenta \+ `[Todos]`.  
  * **Salida Consola:** Muestra Número de Cuenta, Nombre del Propietario, Tipo y Saldo Actual. Imprime total de cuentas encontradas y la **Sumatoria Total de Saldos**.  
* **Reporte 3: Transacciones por Tipo y Monto**  
  * **Entradas:** Selección del `TipoTransaccion` (los 5 tipos \+ `[Todos]`) y Monto Mínimo (\$\\ge 0\$).  
  * **Procesamiento:** Recorre todas las cuentas e itera sus arreglos internos de transacciones.  
  * **Salida Consola:** Número de Cuenta, Propietario, Número de Transacción, Fecha, Tipo, Monto y Detalle. Imprime el total de transacciones encontradas.

---

## **4.3 Módulo `[CLIENTES]` (Portal de Autoservicio)**

\[Inicio de Sesión\]

       \|

       v

  Validar Usuario \----(No existe)----\> \[Error: Usuario no encontrado\]

       \|

  (Inactivo)

       \+-------------\> \[Error: Cliente Inactivo\]

       \|

   (Activo)

       \|

   ¿Tiene Clave?

    /         \\

 (No)         (Sí)

  /             \\

v                 v

\[Configurar Clave\] \[Validar Clave\] \--(3 Fallos)--\> \[Bloquear Cliente (Inactivo)\]

  \\               /

   v             v

\[Validar Tarjeta Acceso (Matriz 4x5)\]

       \|

       v

\[Menú Opciones Cliente\]

  ├── \[TRANSACCIONES\]

  ├── \[MI CUENTA\]

  ├── \[ACTUALIZAR\]

  └── \[VOLVER\]

### **Algoritmo Paso a Paso de Autenticación:**

1. **Ingreso de Usuario:**  
   * Si no existe en `Cliente[]`: Muestra `"No hay ningún cliente con el usuario: XXXXX"` y reintenta.  
   * Si el estado del cliente es `Inactivo`: Muestra `"Cliente está inactivo"` y aborta/reintenta.  
2. **Configuración Inicial de Clave (Si `clave.equals("")`):**  
   * Pide nueva contraseña mediante diálogo de entrada.  
   * **Reglas de Seguridad Obligatorias:**  
     1. Longitud \$\\ge 6\$ caracteres.  
     2. Longitud \$\\le 10\$ caracteres.  
     3. Contener al menos un dígito numérico (`0-9`).  
     4. Contener al menos una letra alfabetica (`a-z`, `A-Z`).  
   * Si no cumple: Muestra `"La clave no cumple con las condiciones mínimas de seguridad"` y presenta botones `[Agregar otra clave]` o `[Cancelar]`.  
   * **Confirmación:** Solicita reingresar la clave. Si no coincide: `"Las claves no coinciden"` con botones `[Confirmar de nuevo la clave]` o `[Cancelar]`.  
   * Al validar exitosamente, guarda la contraseña en el atributo del objeto `Cliente`.  
3. **Verificación de Clave (Si ya tiene clave configurada):**  
   * Solicita la clave de acceso.  
   * Permite hasta **3 intentos fallidos**. Si los agota, asigna `cliente.setEstado(EstadoCliente.Inactivo)`, muestra mensaje de bloqueo por seguridad y retorna al Menú Principal.  
4. **Autenticación mediante Tarjeta de Acceso (Matriz Bidimensional):**  
   * Imprime en la consola la matriz del cliente con encabezados de columna y fila:

\=== TARJETA DE ACCESO \===

      A   B   C   D   E

  1  45  82  19  34  90

  2  12  67  43  88  21

  3  99  35  54  76  11

  4  20  41  63  85  23

* El sistema selecciona al azar 3 coordenadas distintas de la matriz (ej. `B3`, `E2`, `D4`).  
* Muestra un `JOptionPane` pidiendo las 3 claves separadas por guiones.  
* *Ejemplo:* Para `B3` (35), `E2` (21) y `D4` (85), el string correcto ingresado debe ser `"35-21-85"`.  
* Si falla: `"Acceso incorrecto"`, permite reintentar. Si acierta: Accede al submenú de cliente.

---

### **4.3.1 Menú de Opciones del Cliente**

Despliega mensaje contextual: `"Bienvenido(a) [NombreCompleto]"` y los botones:  
`[TRANSACCIONES]`, `[MI CUENTA]`, `[ACTUALIZAR]`, `[VOLVER]`.

1. **`[TRANSACCIONES]`:**  
   * Localiza la cuenta del cliente. Si no posee cuenta: Muestra `"El cliente no posee una cuenta registrada"`.  
   * Muestra botones de operaciones: `[DEPÓSITO]`, `[RETIRO]`, `[TRANSFERENCIA]`, `[COMPRA]`, `[CANCELAR]`.  
   * **Lógica de Depósito:**  
     * Pide monto \$\> 0\$. Valida que `contadorTransacciones < 50`.  
     * Operación: `saldoActual += monto`.  
     * Crea `Transaccion(id, fecha, Deposito, monto, "Depósito en efectivo", saldoActual)`.  
   * **Lógica de Retiro:**  
     * Pide monto \$\> 0\$. Valida que `monto <= saldoActual`. Si no: `"Saldo insuficiente"` con opciones `[Ingresar otro monto]` o `[Cancelar]`.  
     * Operación: `saldoActual -= monto`.  
     * Crea `Transaccion(id, fecha, Retiro, monto, "Retiro en efectivo", saldoActual)`.  
   * **Lógica de Transferencia:**  
     * Solicita número de cuenta destino.  
     * Validaciones: La cuenta destino debe existir y ser diferente a la cuenta de origen. Si no existe: `"El número de cuenta: XXXX no está registrado en el sistema"`.  
     * Pide monto (\$\> 0\$ y \$\\le \\text{saldoActual}\$).  
     * **Validación de Espacio Dual:** Garantiza que tanto la cuenta origen como la cuenta destino tengan `contadorTransacciones < 50`.  
     * **Ejecución Atómica:**  
       1. `origen.saldoActual -= monto`  
       2. `destino.saldoActual += monto`  
       3. Crea en Origen: `Transaccion(id1, fecha, Transferencia_enviada, monto, "Transferencia enviada a cuenta: " + numDestino, origen.saldoActual)`  
       4. Crea en Destino: `Transaccion(id2, fecha, Transferencia_recibida, monto, "Transferencia recibida de cuenta: " + numOrigen, destino.saldoActual)`  
   * **Lógica de Compra:**  
     * Pide monto (\$\> 0\$ y \$\\le \\text{saldoActual}\$) y un texto descriptivo del comercio o detalle.  
     * Operación: `saldoActual -= monto`.  
     * Crea `Transaccion(id, fecha, Compra, monto, detalle, saldoActual)`.  
2. **`[MI CUENTA]`:**  
   * Imprime en la **consola** el detalle completo de la cuenta del cliente activo y el desglose de todas sus transacciones registradas.  
3. **`[ACTUALIZAR]`:**  
   * Permite cambiar Nombre completo, Teléfono, Correo o Clave actual (aplicando las mismas validaciones de seguridad y confirmación).  
4. **`[VOLVER]`:**  
   * Cierra la sesión activa del cliente y regresa a la Pantalla Principal.

---

# **5\. RESTRICCIONES ACADÉMICAS Y REGLAS DEL CURSO**

Para garantizar el cumplimiento con los requerimientos evaluativos del curso:

1. **Prohibición de Frameworks de Colecciones:** Se prohíbe explícitamente el uso de `java.util.ArrayList`, `Vector`, `LinkedList`, `HashMap`, `HashSet` o cualquier otra estructura de datos dinámica. La gestión de arreglos se hará mediante arreglos de tamaño fijo (`T[]`) con contadores primitivos de posición (`int`).  
2. **Uso Exclusivo de Java Standard Edition (SE):** Solo se permiten librerías nativas del JDK (`javax.swing.JOptionPane`, `java.time.LocalDate`, `java.time.format.DateTimeFormatter`).  
3. **Manejo Riguroso de Cierre de Diálogos:** Toda invocación a `JOptionPane` debe controlar el retorno `-1` o `JOptionPane.CLOSED_OPTION` para evitar lanzamientos de excepciones tipo `NullPointerException` en tiempo de ejecución.  
4. **Validación Numérica:** Todo parseo de texto a número (`Integer.parseInt`, `Double.parseDouble`) debe estar cubierto dentro de un bloque `try-catch (NumberFormatException e)` para evitar la caída del sistema ante entradas alfanuméricas inválidas.

---

# **6\. CRONOGRAMA DE ENTREGAS Y RÚBRICAS DETALLADAS**

## **Avance 1 — Semana 8 (5% del total del curso / 100 Puntos)**

\[Estructura y Clases POO\] \---------------------- 40 pts

\[Menús e Interfaz Básica\] \---------------------- 40 pts

\[Consola y Registros Iniciales\] \---------------- 10 pts

\[Validaciones y Login Básico\] \------------------ 10 pts

* **Estructura y Clases POO (40 pts):** Implementación de clases `Cliente`, `Cuenta`, `Transaccion` y enumerados con encapsulamiento estricto (`private`), constructores y métodos `get/set`.  
* **Navegación e Interfaz (40 pts):** Menús visibles y funcionales implementados con `JOptionPane` mediante estructuras de control `do-while` y `switch`.  
* **Salida por Consola (10 pts):** Despliegue correcto de datos en consola para un máximo de 2 instancias simuladas sin transacciones.  
* **Validaciones Iniciales (10 pts):** Registro exitoso de hasta 2 clientes y 2 cuentas con validación de ID no duplicado y acceso básico por cliente.

---

## **Avance 2 — Semana 11 (5% del total del curso / 100 Puntos)**

\[Arreglos Fijos y Precarga\] \-------------------- 10 pts

\[Visualización Consola Completa\] \--------------- 20 pts

\[Altas y Validaciones Múltiples\] \-------------- 30 pts

\[Seguridad y Matriz de Acceso\] \----------------- 20 pts

\[Búsqueda, Edición y Activación\] \--------------- 20 pts

* **Arreglos y Precarga Automática (10 pts):** Carga automática inicial de 10 clientes, 9 cuentas y transacciones en arreglos de `Cliente[30]`, `Cuenta[30]` y `Transaccion[50]`.  
* **Visualización Completa (20 pts):** Imprimir con formato estructurado en consola todos los clientes, cuentas y transacciones precargadas.  
* **Validaciones Múltiples (30 pts):** Adición de clientes y cuentas con comprobación estricta de formatos (teléfono, correo, unicidad de cuenta \$0..1\$, montos positivos).  
* **Módulo de Autenticación de Cliente (20 pts):** Flujo de creación de clave por primera vez (condiciones de 6-10 caracteres, letras y números) y validación exitosa de la matriz de acceso de \$4 \\times 5\$.  
* **Búsqueda y Mantenimiento (20 pts):** Métodos de búsqueda por ID y número de cuenta, actualización de datos personales y activación/desactivación dinámica de clientes.

---

## **Entrega Final — Semana 14 (30% del total: 15% Código \+ 15% Defensa Oral)**

\[Funcionalidades Administrativas\] \-------------- 10 pts

\[Módulo Operativo de Transacciones\] \------------ 25 pts

\[Submódulo de Reportes Analíticos\] \------------- 15 pts

\[Defensa Oral y Dominio de Código\] \------------- 50 pts

* **Funcionalidades del Banco (10 pts):** Funcionamiento libre de errores de todas las opciones del Módulo `[BANCO]`.  
* **Módulo de Transacciones (25 pts):** Procesamiento de Depósitos, Retiros, Compras y Transferencias bancarias atómicas con actualización inmediata de saldos e historial.  
* **Módulo de Reportes (15 pts):** Generación de los 3 reportes requeridos aplicando filtros dinámicos e impresión de sumatorias en consola.  
* **Defensa Oral del Software (50 pts):** Explicación individualizada del código fuente, arquitectura orientada a objetos, paso de parámetros por referencia y algoritmos de búsqueda utilizados.

---

# **7\. GUÍA DE IMPLEMENTACIÓN RÁPIDA (CONEXIÓN Y CONTROLADORES)**

Para facilitar el desarrollo del sistema en entornos como NetBeans, IntelliJ IDEA o Eclipse, se estructuran las variables globales de control que deben vivir dentro del controlador principal de la aplicación:public class HiperBancoApp {

    // Estructuras de Almacenamiento Estático (Requerimiento de Curso)

    private static Cliente\[\] clientes \= new Cliente\[30\];

    private static Cuenta\[\] cuentas \= new Cuenta\[30\];

    

    // Contadores de Control de Arreglos

    private static int contadorClientes \= 0;

    private static int contadorCuentas \= 0;

    

    // Consecutivos Autoincrementales Globales

    private static int consecutivoUsuario \= 40;

    private static int consecutivoCuenta \= 4700;

    private static int consecutivoTransaccion \= 10000;

    public static void main(String\[\] args) {

        ejecutarPrecargaDatos(); // Carga de los 10 clientes y 9 cuentas iniciales

        desplegarMenuPrincipal();

    }

    

    // Métodos de control de menús y lógica...

}

## **Algoritmo General de Búsqueda por Referencia (Ejemplo)**

public static Cuenta buscarCuentaPorPropietario(Cliente cliente) {

    for (int i \= 0; i \< contadorCuentas; i++) {

        if (cuentas\[i\].getPropietario().getId().equals(cliente.getId())) {

            return cuentas\[i\]; // Retorna la referencia directa en memoria

        }

    }

    return null; // El cliente no posee cuenta (Regla 0..1)

}  
