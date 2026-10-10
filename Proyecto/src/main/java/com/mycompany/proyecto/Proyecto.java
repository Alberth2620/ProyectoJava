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
            "Agregar");
            
            if (opcion==0) {
                System.out.println("administrativo");
            } 
            
            if (opcion==1) {
                System.out.println("clientes");
            } 
            
            if (opcion==2) {
                System.out.println("saliendo....");
                validacion=(JOptionPane.showConfirmDialog(null,"Estimado cliente esta seguro de su seleccion :\n"));   // alt + 92 para backslage  \n
        
            } 
            
            
            else {
            }
          
        }
        JOptionPane.showMessageDialog(null, "Saliendo.... ");
        
        
        
        
    }
}
