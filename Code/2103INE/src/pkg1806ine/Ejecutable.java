/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1806ine;

import java.io.IOException;

/**
 *
 * @author hreyes
 */
public class Ejecutable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        if(args.length != 4){
            System.out.println("Número de parámetros incorrecto. Debe ingresar:");
            //validar //accion
            //ruta llavero
            //original
            //evento
            System.out.println("1.- Nombre de acción (validar)");
            System.out.println("2.- Ruta de la llave pública (n/a si se desea descargar desde el servicio de almacenamiento)");
            System.out.println("3.- Nombre de las firmas originales (Original)");
            System.out.println("4.- Nombre de las firmas a validar (S1, S2, S3 o JE)");
        } else {
            
            
            if (args[0].toUpperCase().equals("VALIDAR")) {
                System.out.println("Iniciando el proceso de validación");
                IETAM ietam = new IETAM(args);
                ietam.realizarRevisionFirmas();
                
            } else {
                System.out.println("Opción no disponible");
            }
            
            
        }
        
    }
    
}
