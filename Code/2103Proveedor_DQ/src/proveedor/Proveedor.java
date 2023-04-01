/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.simple.JSONObject;

/**
 *
 * @author hreyes
 */
public class Proveedor {
    
    private GeneradorLlaves gl;
    private CifradoAsimetrico ca;
    private String rutaLlaves;
    private String nombrePrueba;
    private String nombreCiudad;
    private int NumChuks=4;
    
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
    
    public boolean realizarFirma(String rutaArchivos, String rutaLlavePrivada, String nombrePrueba){
        boolean respuesta = false;
        
        File carpeta = new File(rutaArchivos);
        File archivoLlavePrivada = new File(rutaLlavePrivada);
        
      
        boolean verificacion = carpeta.exists();
        boolean verificacion2 = archivoLlavePrivada.exists();
        
        if(verificacion && verificacion2) {//Existen las rutas
            verificacion = carpeta.isDirectory();
            verificacion2 = archivoLlavePrivada.isFile();
            if(verificacion && verificacion2) { //Es una carpeta y un archivo
                try {
                    
                    ca = new CifradoAsimetrico();
                    JSONObject datosFirmados;
                    File archivos [];
                    archivos = carpeta.listFiles();
                    PrivateKey llavePrivada = ca.getPrivate(rutaLlavePrivada);                    
                    datosFirmados = firmarArchivos(archivos, llavePrivada);
                    
                    if(datosFirmados != null) {
                        File fichero=new File(nombrePrueba+".json");
                        try (FileWriter escribir = new FileWriter(fichero,true)) {
                            escribir.write(datosFirmados.toJSONString());
                        }                      
                    }
            
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            } else {
                System.out.print("La ruta de origen no corresponde a una carpeta");
                System.out.println(" o la ruta de la llave no corresponde a un archivo");
            }
        } else {
            System.out.println("La ruta de entrada o la llave no existe");
        }
        
        return respuesta;    
    }
    
    public JSONObject firmarArchivos(File [] archivos, PrivateKey llavePrivada){
        
        int contador = 0;
        int totalArchivos = archivos.length;
        DTODatosProveedor dp = new DTODatosProveedor(totalArchivos);
        dp.setNombrePeueba(nombrePrueba);
        dp.setNombreCiudad(nombreCiudad);
        AdministradorJson aj = new AdministradorJson();
        JSONObject jsonFirmas = new JSONObject();
        
        //Codigo del editor de CSV
        String Cabecera = "NombreCarpeta, NombreArchivo, RequiereSegmentación, idTrabajador, hash trabajador, SHA3-256, Timestamp";
        EditorCsv miEditor = new EditorCsv(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())+"_"+nombrePrueba+"_SHA3256.csv");
        System.out.println(Cabecera);
        String linea = new String();
        String lineafolder = new String();
        String lineaConquer = new String();
        String timeStampCsv;
        
        for (File archivo : archivos) {
            
            linea = archivo.getParent() + ", " + archivo.getName() + ", ";
            
            if (archivo.isFile()){
                
                System.out.println("Se esta procesando el archivo " + archivo.getName());
                
                try {
                    long tinicio, tfin, tproceso;
                    tinicio = System.currentTimeMillis();
                        byte[] contenido = AdministradorArchivos.readBytesFromFile(archivo.getAbsolutePath());
                    tfin = System.currentTimeMillis(); 
                    tproceso = tfin - tinicio;
                    System.out.println("Tiempo de lectura: " + tproceso + " ms");
                    int Workers = this.NumChuks; //chunks         
                    int chunkSize = contenido.length/Workers; // chunk size to divide
                    int Sobrante = contenido.length%Workers; // bytes sobrantes
                    int[] Chunks = new int [Workers];
                    byte[] BytesForHashing; 
                       byte [] hash ;
                    String sha256 ;

                    if (contenido.length>(1024*1024)) //si es mayor a 1 Mb se hace por chunks
                    {
                        linea += "si, ";
                        String lineasWorkers[] = new String[4];
                        
                        System.out.println("Es necesaria la segmentación: Ejecutando divide y venceras");
                        System.out.println("Archivo dividido en "+Workers+" chunks");

                        for (int i = 0; i < Workers; i++) {
                            Chunks[i]=chunkSize;
                            lineasWorkers[i] = new String();
                            lineasWorkers[i] += linea;
                            if ((i+1)==Workers){
                               Chunks[i]+=Sobrante;
                            }
                        }
                        
                        tinicio = System.currentTimeMillis();
                        List<byte[]> CHUNKS= divideArray(contenido, Chunks);
                        contenido = new byte[0];
                        System.gc();
                        ArrayList<Worker> Lista_trabajadores = new ArrayList<Worker>();

                        for (byte[] stack: CHUNKS){ // se crean los hilos

                            Worker W = new Worker();
                            W.setContenido(stack);
                            W.start();
                            Lista_trabajadores.add(W);
                        }         

                        // se espera que terminen para unir los sha
                        for (int i = 0; i < Lista_trabajadores.size(); i++) {
                               try { 
                                   Lista_trabajadores.get(i).join();
                               } catch (InterruptedException ex) {
                                   Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
                               }
                        }


                        tfin = System.currentTimeMillis();
                        tproceso = tfin-tinicio;
                        System.out.println("Tiempo de división: " +tproceso + " ms");

                        //tinicio = System.currentTimeMillis();
                        // se unen los resultados   
                        String ConcatHASH = new String();   
                        for (int i = 0; i < Lista_trabajadores.size(); i++) {
                            ConcatHASH+= Lista_trabajadores.get(i).GetHash(); //por cada json generado
                            //System.out.println("Trabajador(" + i + "): " + Lista_trabajadores.get(i).GetHash());
                            lineasWorkers[i] += i + ", " + Lista_trabajadores.get(i).GetHash();
                            Lista_trabajadores.get(i).cleanContenido();
                            miEditor.escribirLinea(lineasWorkers[i]);
                        }      
                        BytesForHashing=ConcatHASH.getBytes();

                        CHUNKS = new ArrayList<byte[]>();
                        System.gc();

                        //tfin = System.currentTimeMillis();
                        //tproceso = tfin-tinicio;
                        contenido = new byte[0];
                        
                        // se saca un hash
                        tinicio = System.currentTimeMillis();
                        hash = Hash.getStringMessageDigest(BytesForHashing, "SHA3-256");
                        sha256 = Hash.toHexadecimal(hash);
                        tfin = System.currentTimeMillis();
                        tproceso = tfin - tinicio;
                        System.out.println("Tiempo de hash SHA3-256: " + tproceso + " ms");
                        
                        linea += "0-3, " + sha256;
                        miEditor.escribirLinea(linea);
                    }
                    else{
                        linea += "no, ";
                        System.out.println("No es necesaria la segmentación");
                        BytesForHashing = contenido;
                        // se saca un hash
                        tinicio = System.currentTimeMillis();
                        hash = Hash.getStringMessageDigest(BytesForHashing, "SHA3-256");
                        sha256 = Hash.toHexadecimal(hash);
                        tfin = System.currentTimeMillis();
                        tproceso = tfin - tinicio;
                        System.out.println("Tiempo de hash SHA3-256: " + tproceso + " ms");
                        
                        linea += "0, " + sha256;
                        miEditor.escribirLinea(linea);
                    }

                    


                    String hashFirmado = ca.encryptText(sha256, llavePrivada);
                    //System.out.println(archivo.getName() + " " + sha256 + " "+ hashFirmado);
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
            
            } else {
                
                System.out.println("Se esta procesando la carpeta '" + archivo.getName() + "'");
                
                
                
                File filesInFolder [];
                filesInFolder = archivo.listFiles();
                String completeHash = new String();
                byte [] BytesForHashing;
                byte [] hash;
                String sha256 ="";
                
                for (File currentFile : filesInFolder) {
              
                    lineafolder = currentFile.getParent() +", "+ currentFile.getName() + ", ";
                    System.out.println("Procesando el Archivo " + archivo.getName() + "/" + currentFile.getName());
                    
                    try {
                        long tinicio, tfin, tproceso;
                        tinicio = System.currentTimeMillis();
                            byte[] contenido = AdministradorArchivos.readBytesFromFile(currentFile.getAbsolutePath());
                        tfin = System.currentTimeMillis(); 
                        tproceso = tfin - tinicio;
                        System.out.println("Tiempo de lectura: " + tproceso + " ms");
                        int Workers = this.NumChuks; //chunks         
                        int chunkSize = contenido.length/Workers; // chunk size to divide
                        int Sobrante = contenido.length%Workers; // bytes sobrantes
                        int[] Chunks = new int [Workers];
                        //byte[] BytesForHashing; 

                        if (contenido.length>(1024*1024)) //si es mayor a 1 Mb se hace por chunks
                        {
                            
                            
                        lineafolder += "si, ";
                        String lineasWorkers[] = new String[4];
                            
                            System.out.println("Es necesaria la segmentación: Ejecutando divide y venceras");
                            System.out.println("Archivo dividido en "+Workers+" chunks");

                            for (int i = 0; i < Workers; i++) {
                                Chunks[i]=chunkSize;
                                lineasWorkers[i] = new String();
                                lineasWorkers[i] += lineafolder;
                                if ((i+1)==Workers){
                                   Chunks[i]+=Sobrante;
                                }
                            }
                            tinicio = System.currentTimeMillis();
                            List<byte[]> CHUNKS= divideArray(contenido, Chunks);
                            contenido = new byte[0];
                            System.gc();
                            ArrayList<Worker> Lista_trabajadores = new ArrayList<Worker>();

                            for (byte[] stack: CHUNKS){ // se crean los hilos

                                Worker W = new Worker();
                                W.setContenido(stack);
                                W.start();
                                Lista_trabajadores.add(W);
                            }         

                            // se espera que terminen para unir los sha
                            for (int i = 0; i < Lista_trabajadores.size(); i++) {
                                   try { 
                                       Lista_trabajadores.get(i).join();
                                   } catch (InterruptedException ex) {
                                       Logger.getLogger(Ejecutable.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                            }


                            tfin = System.currentTimeMillis();
                            tproceso = tfin-tinicio;
                            System.out.println("Tiempo de división: " +tproceso + " ms");

                            String contentDC = new String();
                            
                            for (int i = 0; i < Lista_trabajadores.size(); i++) {
                                contentDC += Lista_trabajadores.get(i).GetHash(); 
                                //System.out.println("Trabajador(" + i + "): " + Lista_trabajadores.get(i).GetHash());
                                lineasWorkers[i] += i + "," + Lista_trabajadores.get(i).GetHash();
                                miEditor.escribirLinea(lineasWorkers[i]);
                                Lista_trabajadores.get(i).cleanContenido();
                            }      

                            CHUNKS = new ArrayList<byte[]>();
                            System.gc();
                            byte  [] dcHash = Hash.getStringMessageDigest(contentDC.getBytes(), "SHA3-256");;
                            String stringDCHash = Hash.toHexadecimal(dcHash);
                            //System.out.println(currentFile.getName() + " " + stringDCHash);
                            
                            lineafolder += "0-3," + stringDCHash;

                            contenido = new byte[0];
                            
                            completeHash+=stringDCHash;
                            BytesForHashing = completeHash.getBytes();
                            hash = Hash.getStringMessageDigest(BytesForHashing, "SHA3-256");
                            sha256 = Hash.toHexadecimal(hash);
                            miEditor.escribirLinea(lineafolder);
                        
                        } else {
                            lineafolder += "no, ";
                            System.out.println("No es necesaria la segmentación");
                            byte [] comHash = Hash.getStringMessageDigest(contenido, "SHA3-256");
                            completeHash +=  Hash.toHexadecimal(comHash);
                            //System.out.println(currentFile.getName() + " " + Hash.toHexadecimal(comHash));
                            BytesForHashing = completeHash.getBytes();
                            hash = Hash.getStringMessageDigest(BytesForHashing, "SHA3-256");
                            sha256 = Hash.toHexadecimal(hash);
                            lineafolder += "0," + Hash.toHexadecimal(comHash);
                            miEditor.escribirLinea(lineafolder);
                        }
                        

                    } catch (Exception ex) {
                        Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                    }    
                    
                }
                
                try { 
                        

                    lineaConquer = archivo.getAbsolutePath() + ", " + archivo.getName() + ", " + "Hash carpeta, 0," + sha256 ;
                    miEditor.escribirLinea(lineaConquer);
                        String hashFirmado;
                        hashFirmado = ca.encryptText(sha256, llavePrivada);
                        //System.out.println(archivo.getName() + " " + sha256 + " "+ hashFirmado);
                      //  System.out.println(hashFirmado);   
                        dp.setNombre(contador, archivo.getName());   
                        dp.setHash(contador, sha256);   
                        dp.setHashFirmado(contador, hashFirmado);
                        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                        dp.setTimesTamp(contador, timeStamp);
                        contador++;
                        
                                    

                        //System.out.println(linea);
                        
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoSuchPaddingException ex) {
                        Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalBlockSizeException ex) {
                        Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BadPaddingException ex) {
                        Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidKeyException ex) {
                        Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
            }
            
            
            
        }
        
        jsonFirmas = aj.generarJsonProveedor(dp);
        return jsonFirmas;
        
        
    }
    //[0,1,2,3,4,5,6,7,8,9]
    
    public static List<byte[]> divideArray(byte[] source, int[] chunksize) {

    List<byte[]> result = new ArrayList<byte[]>();
    int start = 0;
    int j=0;
    while (start < source.length) {
        int end = Math.min(source.length, start + chunksize[j]);
        result.add(Arrays.copyOfRange(source, start, end));
        start += chunksize[j];
        j++;
    }

    return result;
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

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
    
    public void setNumWorkers(int NumChuks) {
        this.NumChuks = NumChuks;
    }
}
