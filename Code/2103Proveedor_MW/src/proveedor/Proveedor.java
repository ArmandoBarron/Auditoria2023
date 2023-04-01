/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author hreyes
 */
public class Proveedor extends Thread {
    
    private GeneradorLlaves gl;
    private CifradoAsimetrico ca;
    private String rutaLlaves;
    private String nombrePrueba;
    private String nombreCiudad;
    private DTODatosProveedor ResultFirmas;
    
    private File[] archivos; 
    private String rutaLlavePrivada;
    
    
    public Proveedor(){
        
    }
    
    public boolean generarLlaves(){
        
        boolean respuesta = false;
        try {
            
            GeneradorLlaves kg = new GeneradorLlaves(1024);
            //Se concatena el nombre de la llave pública a la ruta ingresada
            String nombreLlavePublica = rutaLlaves + "LlavePublica";
            //Se concatena el nombre de la llave privada a la ruta ingresada
            String nombreLlavePrivada = rutaLlaves + "LlavePrivada";
            
            boolean r1, r2;
            
            //Se verifica que no existan las llaves en las rutas
            if (!AdministradorArchivos.testIfExistsFile(nombreLlavePublica) && 
                !AdministradorArchivos.testIfExistsFile(nombreLlavePrivada)){
                
                System.out.print(
                        "No existen las llaves en '" + rutaLlaves + "'."
                );
                
                System.out.println(
                        "Se procederá a generar las llaves por primera vez"
                );
                //Se generan las llaves
                kg.crearLlaves();
                
                //Se crean los objetos de archivos para las llaves
                File f1 = new File(nombreLlavePublica);
                File f2 = new File(nombreLlavePrivada);
                
                try {
                    //Se crean los archivos
                    f1.createNewFile();                    
                    f2.createNewFile();
                } catch (IOException ex) {
                    //Excepción en caso de que no se puedan crear los archivos
                    Logger.getLogger(
                        Proveedor.class.getName()).log(Level.SEVERE, null, ex
                    );
                }
                
                //Se almacena la llave pública en el archivo correspondiente
                r1 = AdministradorArchivos.storeBytesInFile(
                    kg.obtenerLlavePublica().getEncoded(), nombreLlavePublica
                );
                //Se almacena la llave pública en el archivo correspondiente
                r2 = AdministradorArchivos.storeBytesInFile(
                    kg.obtenerLlavePrivada().getEncoded(), nombreLlavePrivada
                );
                
                //Se verifica que los archivos hayan sido escritos correctamente
                if (!r1) {
                    System.out.println("Error al escribir la llave publica");
                }            
                if (!r2) {
                    System.out.println("Error al escribir la llave publica");
                }
                
                if (r1 && r2) {
                    System.out.println("Llave publica almacenada en " + rutaLlaves + "LlavePublica");            
                    System.out.println("Llave privada almacenada en " + rutaLlaves + "LlavePrivada"); 
                    respuesta = true;
                }
            } else {
                System.out.println("Los archivos de llaves ya existen");
            }
    
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
    }
    
    
    public void run()
    {
        realizarFirma(this.archivos,this.rutaLlavePrivada,this.nombrePrueba);
    }
    
    public boolean realizarFirma(File[] archivos, String rutaLlavePrivada, String nombrePrueba){
        boolean respuesta = false;
        
        File archivoLlavePrivada = new File(rutaLlavePrivada);
        
        boolean verificacion2 = archivoLlavePrivada.exists();
        
        if(verificacion2) {//Existen las rutas
            verificacion2 = archivoLlavePrivada.isFile();
            if(verificacion2) { //Es una carpeta y un archivo
                try {
                    ca = new CifradoAsimetrico();
                    DTODatosProveedor datosFirmados;
                    PrivateKey llavePrivada = ca.getPrivate(rutaLlavePrivada);   
                                 
                    datosFirmados = firmarArchivos(archivos, llavePrivada);
                    
                    this.ResultFirmas=datosFirmados;
                    
                   // if(datosFirmados != null) {
                   //     File fichero=new File(nombrePrueba+".json");
                   //     try (FileWriter escribir = new FileWriter(fichero,true)) {
                   //         escribir.write(datosFirmados.toJSONString());
                   //     }                      
                   // }
            
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            } else {
                System.out.println(" la ruta de la llave no corresponde a un archivo");
            }
        } else {
            System.out.println("La llave no existe");
        }
        
        return respuesta;    
    }
    
    public DTODatosProveedor firmarArchivos(File [] archivos, PrivateKey llavePrivada){
        
        int contador = 0;
        int totalArchivos = archivos.length;
        DTODatosProveedor dp = new DTODatosProveedor(totalArchivos);
        dp.setNombrePeueba(nombrePrueba);
        dp.setNombreCiudad(nombreCiudad);
        //AdministradorJson aj = new AdministradorJson();
        JSONObject jsonFirmas = new JSONObject();
        
        for (File archivo : archivos) {
            
            try {
                byte[] contenido = AdministradorArchivos.readBytesFromFile(archivo.getAbsolutePath());
                byte [] hash = Hash.getStringMessageDigest(contenido, "SHA3-256");
                String sha256 = Hash.toHexadecimal(hash);
                String hashFirmado = ca.encryptText(sha256, llavePrivada);
                System.out.println(archivo.getName() + " " + sha256 + " "+ hashFirmado);
              //  System.out.println(hashFirmado);   
                dp.setNombre(contador, archivo.getName());   
                dp.setHash(contador, sha256);   
                dp.setHashFirmado(contador, hashFirmado);
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                dp.setTimesTamp(contador, timeStamp);
                contador++;
                
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (BadPaddingException ex) {
                Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (InvalidKeyException ex) {
                Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (Exception ex) {
                Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            
        }
        
        //jsonFirmas = aj.generarJsonProveedor(dp);
        return dp;
        
        
    }
    
    public void recorrerCarpetaYFirmar(DTODatosProveedor dp){
        
    }
     
    public void establecerRutaLlaves(String rutaLlaves){
        this.rutaLlaves = rutaLlaves;
    }

    public String getNombrePrueba() {
        return nombrePrueba;
    }
    
    public void setNombrePrueba(String nombrePrueba) {
        this.nombrePrueba = nombrePrueba;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }
    
    public DTODatosProveedor getDatosFirmados() {
        return ResultFirmas;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
    
    public void setRutaLlavePrivada(String rutaLlavePrivada) {
        this.rutaLlavePrivada = rutaLlavePrivada;
    }
    
    public void setStack(File[] archivos) {
        this.archivos = archivos;
    }
    
    
}
