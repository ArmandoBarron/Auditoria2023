/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author hreyes
 */
public class Ejecutable {

    /**
     * @param args the command line arguments
     */
        public static ArrayList<File[]> split_list(File list_files[], int n){
        ArrayList<ArrayList> resultArrays = new ArrayList<ArrayList>();
        int pivot = 0;
        // Creates n ArrayLists.
        for(int i = 0 ; i < n ; i++){
            resultArrays.add(new ArrayList<File>());
        }

        // Add element from list to new ArrayLists.
        while(pivot != list_files.length) {
            int p = pivot%n;
            resultArrays.get(p).add(list_files[pivot]);
            pivot++;
        }
        
        ArrayList<File[]> Final_array = new ArrayList<File[]>();
        for (ArrayList Lista_archivos : resultArrays) {    
            
            Final_array.add((File[]) Lista_archivos.toArray(new File[Lista_archivos.size()]));
        }    
          return Final_array;
        }
    
    public static void main(String[] args) {
        
        if(args.length == 0){
            System.out.println("Debe indicar el nombre de la operación:\ngenerarLlaves\nfirmarArchivos\n");
            exit(0);
        }
        
        String opcion = args[0]; 
        
        
        
        switch(opcion){
            case "generarLlaves":
                Proveedor pro = new Proveedor();
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
                    

                    
                    String rutaInventario = args[1];
                    String rutaLlavePrivada = args[2];
                    String nombrePrueba = args[3];
                    String nombreCiudad = args[4];
                    
                    
                    File carpeta = new File(rutaInventario);
                    boolean verificacion_e = carpeta.exists();
                    boolean verificacion_d = carpeta.isDirectory();
                    ArrayList<Proveedor> Lista_trabajadores = new ArrayList<Proveedor>();
                    if (verificacion_e && verificacion_d ){
                        Integer Workers= 4; // cantidad de trabajos en paralelo
                        File [] archivos = carpeta.listFiles();
                        Integer totalArchivos= archivos.length;
                        ArrayList<File[]> Listas_archivos = split_list(archivos, Workers);
                        
                        for (File[] stack: Listas_archivos){ // se crean los hilos
                            
                            Proveedor worker = new Proveedor();
                            worker.setNombrePrueba(nombrePrueba);
                            worker.setNombreCiudad(nombreCiudad);
                            // parametros 
                            worker.setRutaLlavePrivada(rutaLlavePrivada);
                            worker.setStack(stack);
                            worker.start();    
                            Lista_trabajadores.add(worker);
                        }
                        System.out.print("Trabajos en paralelo: "+Workers+"\n");

                        for (int i = 0; i < Lista_trabajadores.size(); i++) 
                        {
                            try { 
                                Lista_trabajadores.get(i).join();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        System.out.print("Los trabajadores terminaron su ejecución");
                        DTODatosProveedor dp = new DTODatosProveedor(totalArchivos);
                        dp.setNombrePeueba(nombrePrueba);
                        dp.setNombreCiudad(nombreCiudad);
                        Integer contador = 0;
                        AdministradorJson aj = new AdministradorJson();

                        for (int i = 0; i < Lista_trabajadores.size(); i++) 
                        {
                                DTODatosProveedor Temp= Lista_trabajadores.get(i).getDatosFirmados(); //por cada json generado
                                Integer ArchivosProcesados= Temp.getNumeroArchivos();
                                for (int j = 0; j < ArchivosProcesados; j++) 
                                {
                                    dp.setNombre(contador, Temp.getUnNombre(j));   
                                    dp.setHash(contador, Temp.getUnHash(j));   
                                    dp.setHashFirmado(contador,  Temp.getUnHashFirmado(j));
                                    dp.setTimesTamp(contador, Temp.getUnTimestamp(j));
                                    contador++;
                                }
                        }        
                        JSONObject datosFirmados=  aj.generarJsonProveedor(dp);
                        
                        if(datosFirmados != null) {
                            File fichero=new File(nombrePrueba+".json");
                             try (FileWriter escribir = new FileWriter(fichero,true)) {
                                escribir.write(datosFirmados.toJSONString());
                            } catch (IOException ex) {
                                Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
                            }                      
                        }


                        

                        
                    }
                    else{
                       System.out.print("La ruta de origen no corresponde a una carpeta o La ruta de entrada no ");
                    }

                    
                    // here

                    
                    
                }
                
                break;
            default:
                System.out.println("Opcion no disponible");
        }
        
    }
    
}
