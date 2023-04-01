/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedor;

import static java.lang.System.exit;

/**
 *
 * @author hreyes
 */
public class Ejecutable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if(args.length == 0){
            System.out.println("Debe indicar el nombre de la operación:\ngenerarLlaves\nfirmarArchivos\n");
            exit(0);
        }
        
        String opcion = args[0]; 
        
        
        Proveedor pro = new Proveedor();
        switch(opcion){
            case "generarLlaves":
                
                if(args.length != 3){
                    System.out.println("Debe ingresar el directorio en donde las llaves serán almacenadas, Por ejemplo: \n\tjava -jar 2103Proveedor.jar generarLlaves Llavero/ Original");
                    exit(0);
                } else {
                    String rutaLlaves = args[1];
                    String nombrePrueba = args[2];
                    
                    pro.establecerRutaLlaves(rutaLlaves);
                    pro.setNombrePrueba(nombrePrueba);
                    
                    if(pro.generarLlaves()){
                        System.out.println("Llaves fueron generadas correctamente");
                    } else {
                        System.out.println("Las Llaves no fueron generadas, revisar los mensajes mostrados");
                    }
                }
                
                break;
            case "firmarArchivos":
                
                if(args.length != 5){
                    System.out.println(
                        "Error: número de parámetros incorrectos."
                        + "\nEs necesario ingresar:\n\tla ruta de los archivos de entrada (Inventario/),"
                        + "\n\tla ruta de la llave privada (Llavero/LlavePrivada),"
                        + "\n\tel nombre de la prueba (S1, S2, S3, JE)"
                        + "\n\ty el nombre de la ciudad (CiudadVictoria)" );
                    exit(0);
                } else {
                    
                   
                    
                    long tinicio, tfinal, trespuesta;
                    tinicio = System.currentTimeMillis();
                    String rutaInventario = args[1];
                    String rutaLlavePrivada = args[2];
                    String nombrePrueba = args[3];
                    String nombreCiudad = args[4];
                    int Workers=4; // si se cambia la cantidad de trabajadores puede alterar el resultado de los hash
                    // no es lo mismo un archivo hasheado por 4 chunks que el mismo hasheado por 8 o 2.
                    // esto es porque:
                    // H(contenido) != H(H(chunk_1)+H(chunk_2)...H(chunk_n) ) | chunk_1+chunk_2...+chunk_n=contenido; 
                    pro.setNumWorkers(Workers);
                    pro.setNombrePrueba(nombrePrueba);
                    pro.setNombreCiudad(nombreCiudad);
                    pro.realizarFirma(rutaInventario, rutaLlavePrivada,nombrePrueba);    
                    tfinal = System.currentTimeMillis();
                    trespuesta = tfinal - tinicio;
                    
                   System.out.println("Tiempo de respuesta: " +trespuesta + " ms");
                }
                
                break;
            default:
                System.out.println("Opcion no disponible");
        }
        
    }
    
}
