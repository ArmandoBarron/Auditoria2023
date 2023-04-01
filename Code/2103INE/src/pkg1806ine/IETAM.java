package pkg1806ine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static javafx.application.Platform.exit;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import static java.lang.System.exit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hreyes
 */
public class IETAM {
    
    private final String ruta;
    private final String firmasOriginales;
    private final String firmasPorValidar;
    
    public IETAM(String args[]){
        this.ruta = args[1];
        this.firmasOriginales = args[2];
        this.firmasPorValidar = args[3];
    }
    
    public void realizarRevisionFirmas(){
        PublicKey pk = null;
        CifradoAsimetrico ca = new CifradoAsimetrico();
        
     
        System.out.println("\tSe leerá la llave pública desde '" + ruta + "'");


        if(existeArchivo(ruta)){
            pk = ca.getPublic(ruta);
        } else {
            System.out.println("\tLa ruta '" + ruta + "' no existe, ingrese una ruta valida");
            exit(0);
        }
        
        LinkedList<DTORegistroProveedor> original;
        LinkedList<DTORegistroProveedor> porVerificar = null;
        System.out.println("Sin conexion");
        DBConnection db = new DBConnection();
        db.getMysqlConnection();
        System.out.println("Conexion establecida");
        original = db.obtenerDatosProveedor(firmasOriginales); 
        
        
        verificarFirmaDigital(original, pk, ca);
        
        //if (!firmasPorValidar.toUpperCase().equals("ALL")){
            porVerificar = db.obtenerDatosProveedor(firmasPorValidar);
            if(porVerificar.isEmpty() || original.isEmpty()){
                System.out.println("No se obtuvieron los datos");
            } else {
                verificarFirmaDigital(porVerificar, pk, ca);
                generarReporte(original, porVerificar,firmasOriginales,firmasPorValidar);
            }
            
        /*} else {
            for (int i = 0; i < 4; i ++) {
                porVerificar = null;
                switch (i) {
                    case 0:
                        porVerificar = db.obtenerDatosProveedor("S1");
                        break;
                    case 1:
                        porVerificar = db.obtenerDatosProveedor("S2");
                        break;
                    case 2:
                        porVerificar = db.obtenerDatosProveedor("S3");
                        break;
                    case 3:
                        porVerificar = db.obtenerDatosProveedor("JE");
                        break;
                    default:
                        break;
                }
                
                if (porVerificar.size() > 0) {
                    verificarFirmaDigital(porVerificar, pk, ca);
                    generarReporte(original, porVerificar,firmasOriginales,firmasPorValidar);
                }
            }
        }*/
        
        db.closeMysqlConnection();
        
        
        
    }
    
    public void generarReporte(LinkedList<DTORegistroProveedor> original,
            LinkedList<DTORegistroProveedor> porVerificar, String firmasoriginales, String firmasporvalidar){
        
        String Cabecera = "NombreArchivo, NombreCiudad, Firma Original, Nombre del Evento, Firma Evento, Verificación Integridad, Timestamp";
        EditorCsv miEditor = new EditorCsv(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) +firmasoriginales+"_"+firmasporvalidar+"_validacion.csv");
        System.out.println(Cabecera);
        
        int mayor = 0, menor = 0;
        
        if (original.size() >= porVerificar.size()) {
            mayor = original.size();
            menor = porVerificar.size();
        } else {
            mayor = porVerificar.size();
            menor = original.size();
        }
        
        for (int i = 0; i < mayor; i ++) {
            String linea = "";
            String timeStamp;
            
            DTORegistroProveedor uno;
            DTORegistroProveedor dos;
            
            if (mayor == original.size()) {
                uno = original.get(i);
            } else {
                uno = porVerificar.get(i);
            }            
            
            if (uno.getNombreArchivo().toUpperCase().equals("LOG.TXT")){
                linea += uno.getNombreArchivo() + ", " + uno.getNombreCiudad() + ", ";
                linea += uno.getHashVerificado() + ", ";

                if (uno.isFirmaValida()) {
                    linea += "OK, ";
                } else {
                    linea += "NO, ";
                }

                boolean esta = false;
                for (int j = 0; j < menor; j++) {
                    
                    if (mayor == original.size()) {
                        dos = porVerificar.get(j);
                    } else {
                        dos = original.get(j);
                    }

                    if (uno.getNombreArchivo().equals(dos.getNombreArchivo())) {
                        linea += dos.getNombrePrueba() + " y " + uno.getNombrePrueba() +", ";
                        linea += dos.getHashVerificado() + ", ";
                        if (dos.isFirmaValida()) {
                            linea += "OK, ";
                        } else { linea += "NO, "; }

                        if(uno.isFirmaValida() && dos.isFirmaValida()) {
                            if (!uno.getHash().equals(dos.getHash())){
                                timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                                linea += "SI, ";
                                linea += timeStamp;
                            } else { 
                                timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                                linea += "NO, "; 
                                linea += timeStamp;
                            }
                        } else { 
                            timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                            linea += "NO, "; 
                            linea += timeStamp;
                        } 
                        esta = true;
                        break;
                    }   
                }

                if (!esta) {
                    timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                    if(original.size() >= porVerificar.size()) {
                        linea += porVerificar.get(0).getNombrePrueba()+ ", ";
                    } else {
                        linea += original.get(0).getNombrePrueba()+ ", ";
                    }

                    linea += "N/A, Archivo no encontrado, NO, ";
                    linea += timeStamp;
                }
            }  else {
                linea += uno.getNombreArchivo() + ", " + uno.getNombreCiudad() + ", ";
                linea += uno.getHashVerificado() + ", ";


                if (uno.isFirmaValida()) {
                    linea += "OK, ";
                } else {
                    linea += "NO, ";
                }

                boolean esta = false;
                for (int j = 0; j < menor; j++) {


                    if (mayor == original.size()) {
                        dos = porVerificar.get(j);
                    } else {
                        dos = original.get(j);
                    }

                    if (uno.getNombreArchivo().equals(dos.getNombreArchivo())) {
                        linea += dos.getNombrePrueba() + " y " + uno.getNombrePrueba() +", ";
                        linea += dos.getHashVerificado() + ", ";
                        if (dos.isFirmaValida()) {
                            linea += "OK, ";
                        } else { linea += "NO, "; }

                        if(uno.isFirmaValida() && dos.isFirmaValida()) {
                            if (uno.getHash().equals(dos.getHash())){
                                timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                                linea += "SI, ";
                                linea += timeStamp;
                            } else { 
                                timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                                linea += "NO, "; 
                                linea += timeStamp;
                            }
                        } else { 
                            timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                            linea += "NO, "; 
                            linea += timeStamp;
                        } 
                        esta = true;
                        break;
                    }   
                }

                if (!esta) {
                    timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                    if(original.size() >= porVerificar.size()) {
                        linea += porVerificar.get(0).getNombrePrueba()+ ", ";
                    } else {
                        linea += original.get(0).getNombrePrueba()+ ", ";
                    }

                    linea += "N/A, Archivo no encontrado, NO, ";
                    linea += timeStamp;
                } 
            }
            
            
            
            System.out.println(linea);
            miEditor.escribirLinea(linea);
        }
        
    }
    
    private boolean requiereDescargar(){
        boolean response = false;
        
        if(this.ruta.toUpperCase().equals("N/A")){
            response = true;
        }
        
        return response;
    }
    
    private boolean existenFirmas(){
        boolean response = false;
        
        switch(firmasPorValidar.toUpperCase()){
            case "S1":
                response = true;
            break;
            case "S2":
                response = true;
            break;
            case "S3":
                response = true;
            break;
            case "JE":
                response = true;
            break;
        }
        
        return response;
        
    }
    
    private boolean existeOr(){
        boolean response = false;
        
        if (firmasOriginales.toUpperCase().equals("ORIGINAL")){
            response = true;
        }
        
        return response;
    }
    
    private boolean existeArchivo (String pathfile){
        boolean response = false;
        
        File arhivo = new File(pathfile);
        if (arhivo.exists() && arhivo.isFile()){
            response = true;
        }
        
        return response;
        
    }
    
     
     
    private void verificarFirmaDigital(
            LinkedList<DTORegistroProveedor> listado, PublicKey pk,
            CifradoAsimetrico ca
    ){
        
        for (DTORegistroProveedor actual: listado){
            
            try {
                
                String hashRecuperado;
                hashRecuperado = ca.decryptText( actual.getHashFirmado(), pk);
                
                if (hashRecuperado.equals(actual.getHash())){
                    actual.setFirmaValida(true);
                } 
                
                    actual.setHashVerificado(hashRecuperado);
                
            } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException ex) {
                Logger.getLogger(IETAM.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        }
        
    }
    
    
    
}
