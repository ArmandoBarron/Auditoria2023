package pkg1806ine;

import com.sun.tools.javac.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import org.json.simple.JSONObject;

import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
    
    public void realizarRevisionFirmas() throws FileNotFoundException, IOException{
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
        LinkedList<DTORegistroProveedor> porVerificar, String firmasoriginales, String firmasporvalidar) throws FileNotFoundException, IOException{
        
        String Cabecera = "NombreArchivo, NombreCiudad, Firma Original, Nombre del Evento, Firma Evento, Verificación Integridad, Timestamp, Componente, Veredicto, Apariciones";
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
            String NombreEvento="";
            
            DTORegistroProveedor uno;
            DTORegistroProveedor dos;
            
            if (mayor == original.size()) {
                uno = original.get(i);
            } else {
                uno = porVerificar.get(i);
            }            
            boolean Verificar_interior_hashes = false;
            String nombre_archivo_hashes= "hashes_inventario.txt";
            
            
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
                        NombreEvento=dos.getNombrePrueba() + " y " + uno.getNombrePrueba();
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
                                
                                if(uno.getNombreArchivo().equals(nombre_archivo_hashes)) {
                                    Verificar_interior_hashes = true; // new
                                }
                                    
                            }
                        } else { 
                            timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                            linea += "NO, "; 
                            linea += timeStamp;
                            if(uno.getNombreArchivo().equals(nombre_archivo_hashes)) {
                                Verificar_interior_hashes = true; // new
                            }
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

            // añadido para el INE
            if (Verificar_interior_hashes){
            System.out.println("leyendo archivos de hashes");
            JSONObject ListaOrigianal = LeerListaHashes(firmasoriginales, nombre_archivo_hashes);
            JSONObject ListaEvento = LeerListaHashes(firmasporvalidar, nombre_archivo_hashes);
            System.out.println("archivos de hashes leidos");
                ValidarListas(ListaOrigianal, ListaEvento, NombreEvento, uno.getNombreCiudad(),miEditor );
            }
                
            
        }
        
    }
    
    
    private void ValidarListas(JSONObject ListaO,JSONObject ListaE,String NombreEvento,String Ciudad,EditorCsv pointer_csv){
        Iterator iterator = ListaO.keySet().iterator(); 
        List<String> ToDelete = new ArrayList<String>();
        List<String> ToDeleteE = new ArrayList<String>();
        int cantidad_archivos = ListaO.keySet().size();
        while(iterator.hasNext()) {
            String key = (String) iterator.next();
            //System.out.println("validando "+ key);
            if (ListaE.containsKey(key)) { // validar que exista en el archivo de evento
                //System.out.println("se encontró");
                // si existe, se validan los conteos
                JSONObject temp_obj_original= (JSONObject) ListaO.get(key);
                JSONObject temp_obj_event= (JSONObject) ListaE.get(key);
                
                int counterO = (int) temp_obj_original.get("count");
                int counterE = (int) temp_obj_event.get("count");
                
                String path = (String) temp_obj_original.get("path");
                String componente = (String) temp_obj_original.get("component");


                
                String NuevaLinea ="";
                if (counterO > counterE || counterE > counterO){ // hay mas lineas en el original, por lo que no coinciden
                    
                    String razon_rechazo = "El conteo de hash no coincide. El hash se repite "+counterO+" en el archivo original y "+counterE+" en el archivo del evento.";
                    NuevaLinea = CrearLinea(path, Ciudad, key, key, NombreEvento, componente, razon_rechazo,counterO,cantidad_archivos);
                    pointer_csv.escribirLinea(NuevaLinea);
                }

                
               //se remueve la key de la lista
               ToDelete.add(key);
            }
            else{ // la key no esta en la lista del evento
                // hay que buscar el archivo
                //System.out.println("no se encontró. Buscando...");

                JSONObject temp_obj_original= (JSONObject) ListaO.get(key);
                String OriginalPath = (String) temp_obj_original.get("path");
                String OriginalComponente = (String) temp_obj_original.get("component");
                int conteo_aparciones = (int) temp_obj_original.get("count");
                
                Iterator temp_it = ListaE.keySet().iterator(); 
                String match="";
                while(temp_it.hasNext()) {
                    String key_event = (String) temp_it.next();
                    JSONObject temp_obj_event= (JSONObject) ListaE.get(key_event);
                    String path = (String) temp_obj_event.get("path");
                    if(OriginalPath.equals(path)){
                       //System.out.println("Encontrado");

                        match = key_event;
                        break;
                    }                   
                }
                String NuevaLinea;
                if("".equals(match)){ // el archivo no esta en el evento, por lo tanto se eliminó
                    String razon_rechazo;
                    if("File".equals(OriginalComponente)){razon_rechazo="No se encontró el archivo";}
                    else{razon_rechazo="No se encontró el contenedor";}
                    
                    NuevaLinea = CrearLinea(OriginalPath, Ciudad, key, "", NombreEvento, OriginalComponente, razon_rechazo, conteo_aparciones, cantidad_archivos);
                    ToDelete.add(key);
                }
                else{
                String razon_rechazo="los hash no coinciden.";
                    NuevaLinea = CrearLinea(OriginalPath, Ciudad, key, match, NombreEvento, OriginalComponente, razon_rechazo,conteo_aparciones,cantidad_archivos);
                    ToDelete.add(key);
                    ToDeleteE.add(match);
                }
                pointer_csv.escribirLinea(NuevaLinea);
            }
        } // fin del iterador de la lista original
        
        //borrar
        ListIterator<String> namesIterator = ToDelete.listIterator();
        // Traversing elements using next() method
        while (namesIterator.hasNext()) {
            String key_to_delete = namesIterator.next();
            ListaO.remove(key_to_delete);
            ListaE.remove(key_to_delete);
        }
        namesIterator = ToDeleteE.listIterator();
        while (namesIterator.hasNext()) {
            String key_to_delete = namesIterator.next();
            ListaE.remove(key_to_delete);
        }
        // ahora se revisa la lista del evento, para colocar todos lso hash sobrantes
        Iterator iteratorE = ListaE.keySet().iterator(); 
        
        while(iteratorE.hasNext()) {
            String keyE = (String) iteratorE.next();
            JSONObject temp_obj_event= (JSONObject) ListaE.get(keyE);
            String path = (String) temp_obj_event.get("path");
            String component = (String) temp_obj_event.get("component");
            int conteo_aparciones = (int) temp_obj_event.get("count");

            String razon_rechazo = "No se encontró un hash similar en el archivo original.";
            String NuevaLinea = CrearLinea(path, Ciudad, "", keyE, NombreEvento, component, razon_rechazo,conteo_aparciones,cantidad_archivos);
            pointer_csv.escribirLinea(NuevaLinea);

            
        }
    }
    
    private String CrearLinea(String Nombre, String Ciudad,String HashO, String HashE, String NombreEvento,String Componente,String reason,int Conteo, int total){
        String AgregarLinea="";
        AgregarLinea =  Nombre+", " + Ciudad + ", ";
        AgregarLinea += HashO+", OK, ";
        AgregarLinea += NombreEvento+", ";
        AgregarLinea += HashE+", OK, NO, ";
        AgregarLinea += new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())+", ";
        AgregarLinea += Componente+", "+reason+", "+Conteo+", hashes_inventario.txt";
        AgregarLinea += ", "+total;



        return AgregarLinea;
    }
    
    private JSONObject LeerListaHashes(String firmasoriginales, String nombre_archivo_hashes) throws IOException{
    
        String PathFileOriginal = firmasoriginales+"/"+nombre_archivo_hashes;
        FileReader input_original = new FileReader(PathFileOriginal);
        BufferedReader bufRead_original = new BufferedReader(input_original);
                
        String Line_original = null;

        Line_original = bufRead_original.readLine();
        int currentline = 0;
        JSONObject obj_original = new JSONObject();
        String component="File";
        while ((Line_original) != null)
        {    
            currentline++;
            //System.out.println(Line_original);
            // verificar si es contenedor o archivo
            if ("".equals(Line_original)){
                component="File";
                Line_original = bufRead_original.readLine();
            }
            
            if (Line_original.contains("Container:")){
                component="ContainerFile";
                Line_original = bufRead_original.readLine();
            }
            
            
            String[] arr_original = Line_original.split("  ");
            
            JSONObject temp_obj;
            if (arr_original.length == 2 & arr_original[0].length()==64){//if is a 256 hash
                
               if (obj_original.containsKey(arr_original[0])){
                   temp_obj= (JSONObject) obj_original.get(arr_original[0]);
                   int counter = (int) temp_obj.get("count");
                   //System.out.println(Line_original+". antes: "+counter+", ahora: "+ (counter++) );
                   
                   //System.out.println("antes de la suma: "+ temp_obj.get("count"));
                   counter++;
                   temp_obj.put("count", counter);
                   //System.out.println("Despues de la suma: "+ temp_obj.get("count"));
                   
                   
               }
               else{
                   temp_obj = new JSONObject();
                   temp_obj.put("path", arr_original[1]);
                   temp_obj.put("count", 1);
                   if (arr_original[1].contains(".db")){
                       temp_obj.put("component", "Database");
                   }
                   else{
                       temp_obj.put("component", component);
                   }
                   

               }
                obj_original.put(arr_original[0], temp_obj);


           }
           else{
              if (Line_original.contains("ID(sha256:")){ // es una imagen de contenedor
                  String[] arr_c = Line_original.split("	");
                  // 0 = hash, 1= size, 2 = name
                  //System.out.println(Line_original);
                  //System.out.println(arr_c[0]);

                  String hash_image = arr_c[0].substring(arr_c[0].indexOf('(') + 1, arr_c[0].indexOf(')'));
                  String name_image = arr_c[2].substring(arr_c[2].indexOf('(') + 1, arr_c[2].indexOf(')'));
                    
                  //System.out.println("image: "+name_image+". hash image : " + hash_image);

                  if (obj_original.containsKey(hash_image)){
                        temp_obj= (JSONObject) obj_original.get(hash_image);
                        int counter = (int) temp_obj.get("count");
                        counter++;
                        temp_obj.put("count", counter);
                   }
                   else{
                        temp_obj = new JSONObject();
                        temp_obj.put("path", name_image);
                        temp_obj.put("count", 1);
                        temp_obj.put("component", "ContainerImage");
                    }
                     obj_original.put(hash_image, temp_obj);
                  
              }
            
            
           }
        Line_original = bufRead_original.readLine();
        }
        return obj_original;
    
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
                    //String[] array1 = myLine.split(":");
                    //if(!(Line_original.equals(Line_verificar)))
                    //{
                        //System.out.println("LA SIGUIENTE LINEA ES DIFERENTE" +Line_original);
                    //    String[] arr_original = Line_original.split("  ");
                    //    String[] arr_verificar = Line_verificar.split("  ");
                    //    String linea_adicional="";
                    //    if (arr_original.length == 2 & arr_verificar.length==2 & arr_original[0].length()==64){ //if is a 256 hash
                    //       linea_adicional = "componente: " + arr_verificar[1] + ", " + uno.getNombreCiudad() + ", ";
                    //        linea_adicional += arr_original[0]+", OK, ";
                    //        linea_adicional +=  NombreEvento+", ";
                    //        linea_adicional +=  arr_verificar[0]+", OK, NO, ";
                    //        linea_adicional +=  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    //    }
                    //    else{
                    //        linea_adicional = "Line "+currentline+" in hases_inventario.txt, " + uno.getNombreCiudad() + ", ";
                    //        linea_adicional += Line_original+", OK, ";
                    //        linea_adicional +=  NombreEvento+", ";
                    //        linea_adicional +=  Line_verificar+", OK, NO, ";
                    //        linea_adicional +=  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    //    }
                    //    miEditor.escribirLinea(linea_adicional);
                    //}
                    //Line_original = bufRead_original.readLine();
                    //Line_verificar = bufRead_verificar.readLine();
                    //currentline++;