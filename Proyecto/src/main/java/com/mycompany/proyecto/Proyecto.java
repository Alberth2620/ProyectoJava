package com.mycompany.proyecto;
import javax.swing.JOptionPane;

/**
 *
 * @author alber
 */
public class Proyecto {

    public static void main(String[] args) {
    
        int opcion=0;
        do { 
            opcion = Integer.parseInt(JOptionPane.showInputDialog("""
                                                             ----Menu Bancario----
                                                             1. BANCO
                                                             2. CLIENTES 
                                                             3. SALIR
                                                             
                                                             
                                                             """));
            
            switch (opcion) {
                case 1:
                    
                    
                        int opt =0; 
                        do {
                            opt = Integer.parseInt(JOptionPane.showInputDialog("""
                                                                             ----Menu Bancario----
                                                                             1. Mostrar clientes
                                                                             2. Mostrar cuentas y transacciones
                                                                             3. Agregar nuevo cliente
                                                                             4. Agregar nueva cuenta
                                                                             5. Buscar cliente
                                                                             6. Buscar cuenta
                                                                             7. Generar reportes 
                                                                             8. volver

                                                                             """));
                            switch (opt) {

                                     case 1:
                                         System.out.println("muestra clientes");
                                         Cliente elCliente1=new Cliente();
                                         
                                         Cliente elCliente2=new Cliente();
                                         
                                    break;
                                     case 2:
                                          Cuenta laCuenta1= new Cuenta();
                                          Cuenta laCuenta2= new Cuenta();
                                    break;
                                     case 3:

                                    break;
                                     case 4:

                                    break;
                                     case 5:

                                    break;
                                     case 6:

                                    break;
                                     case 7:

                                    break;
                                     case 8:
                                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");

                                    break;
                                default:
                                  JOptionPane.showMessageDialog(null, "Opción invalida.");

                            }

                        } while (opt != 8);
                    
                    break;
                    
                case 2:
                                do {
                                    opt = Integer.parseInt(JOptionPane.showInputDialog("""
                                                                                     ----Menu Cliente----
                                                                                     1. Transacciones
                                                                                     2. Mi cuenta
                                                                                     3. Actualizar datos
                                                                                     4. Volver a menu principal

                                                                                     """));
                                    switch (opt) {

                                             case 1:
                                                 System.out.println("muestra clientes");
                                            break;
                                             case 2:

                                            break;
                                             case 3:

                                            break;


                                           
                                             case 4:
                                            JOptionPane.showMessageDialog(null, "Saliendo del sistema...");

                                            break;
                                        default:
                                          JOptionPane.showMessageDialog(null, "Opción invalida.");

                                    }

                                } while (opt != 4);
                    
                    
                    break;
                    
                default:
                    throw new AssertionError();
            }
  
        } while (opcion != 3);
   
        
    }
}
