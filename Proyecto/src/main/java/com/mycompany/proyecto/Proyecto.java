package com.mycompany.proyecto;
import javax.swing.JOptionPane;

/**
 *
 * @author alber
 */
public class Proyecto {

    public static void main(String[] args) {
        //Inicializacion de menu principal primera version   
        int opcion=0;
        int validacion=0;
        while(opcion !=2 || validacion==1){
            
            opcion = JOptionPane.showOptionDialog(
            null,
            "Seleccione una opción:",
            "Menú",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"BANCO", "CLIENTES", "Salir"},
            "");
            
            if (opcion==0) {
                
                System.out.println("administrativo");
                
                int opcionBanco = JOptionPane.showOptionDialog(
                null,
                "Seleccione una opción:",
                "Menú",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Mostrar Clientes", 
                    "Cuentas y Transacciones", 
                    "Nuevo Cliente",
                    "Nueva Cuenta",
                    "Agregar Nueva Cuenta",
                    "Buscar Cliente",
                    "Buscar Cuenta",
                    "Generar Reportes",
                    "Volver"},
                  "");
                    
                
                switch (opcionBanco) {
                    case 0:
                        // Funcion mostrarclientes
                        JOptionPane.showMessageDialog(null, "Saliendo1.... ");
                        break;
                    case 1:
                        // funcion cuentasTransacciones
                        JOptionPane.showMessageDialog(null, "Saliendo2.... ");
                        break;
                    case 2:
                        //Nuevo Cliente 
                        JOptionPane.showMessageDialog(null, "Saliendo3.... ");
                        break;
                        
                    case 3:
                        // funcion nueva cuenta
                        JOptionPane.showMessageDialog(null, "Saliendo4.... ");
                        break;
                    case 4:
                        
                        // funcion buscar cliente
                        JOptionPane.showMessageDialog(null, "Saliendo5.... ");
                        break;
                        
                    case 5:
                        // funcion buscar cuenta 
                        JOptionPane.showMessageDialog(null, "Saliendo6.... ");
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(null, "Saliendo7.... ");
                        // funcion generar reportes 
                        break;
                        
                    case 7:
                        // volver
                        JOptionPane.showMessageDialog(null, "Saliendo8.... ");
                        break;
                    case 8:
                        JOptionPane.showMessageDialog(null, "Main ");
                        
                        // validar con funcion proximamente 
                        
                        
                        
                        validacion=(JOptionPane.showConfirmDialog(null,"Estimado usuario esta seguro de volver al menu principal :\n"+
                                                                   "Si tiene un proceso sin guardar se perdera"));   // alt + 92 para backslage  \n
                        if (validacion==1) {
                            opcionBanco=opcionBanco;
                        } else {
                        }
                        
                        
                        
                        
                        break;
                    default:
                        throw new AssertionError();
                }
                
                
                
                
                
                
            } 
            
            if (opcion==1) {
                System.out.println("clientes");
                
                
                
                
                
                
                
                
            } 
            
            if (opcion==2) {
                System.out.println("saliendo....");
                // validar con funcion proximamente 
                validacion=(JOptionPane.showConfirmDialog(null,"Estimado cliente esta seguro de su seleccion :\n"));   // alt + 92 para backslage  \n
        
            } 
            
            
            else {
            }
          
        }
        JOptionPane.showMessageDialog(null, "Saliendo.... ");
        
        
        
        
    }
}
