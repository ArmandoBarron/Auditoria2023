/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guardarfirmas;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author jacielHernandez
 */
public class GuardarFirmas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        if (args.length != 1) {
            System.out.println("Es necesario ingresar la ruta del archivo json por cargar");
        } else {
            
            String fileName = args[0];
            //"D:/jacie/Documents/AuditoriaCINVESTAV/Entregables/Jornada Electoral/firmas y llave publica/20180702_194134_ALL_oficial3_firmas.json";
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String contents = new String(Files.readAllBytes(Paths.get(fileName)));
            System.out.println(contents);
            boolean respuesta = enviarJsonFirmado(contents);
        }
        
    }
   
    public static boolean enviarJsonFirmado(String cadenaJson){
        
        
        
        boolean respuesta = true;
        String USER_AGENT = "Mozilla/5.0";
        
        //Config conf = new Config("Config.txt");
        
//Es necesario cambiar por un servidor
        //String SERVER_PATH = "http://auditoria.tamps.cinvestav.mx/Auditoria2022/EA/";
        String SERVER_PATH = "http://127.0.0.1/Auditoria2023/EA/";
        //String SERVER_PATH = "http://127.0.0.1/web/EA/";
        //String SERVER_PATH = "http://disys0.tamps.cinvestav.mx/2021Auditoria/EA/";
        String TOKEN_APP = "f3cfbaae0d5bbdb106538d83edeb0faf4e9d5716a377890028cb4befe3191040";
        try {
            //Codificar el json a URL
            cadenaJson = URLEncoder.encode(cadenaJson, "UTF-8");
            //Generar la URL
            String url = SERVER_PATH+"webservice.php";
            //Creamos un nuevo objeto URL con la url donde queremos enviar el JSON
            URL obj = new URL(url);
            //Creamos un objeto de conexión
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //Añadimos la cabecera
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            //Creamos los parametros para enviar
            String urlParameters = "accion=insertar&entidad=proveedor&json="+cadenaJson+"&token="+TOKEN_APP;
            // Enviamos los datos por POST
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            //Capturamos la respuesta del servidor
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //Mostramos la respuesta del servidor por consola
            System.out.println(response);
            respuesta = true;
            //cerramos la conexión
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        return respuesta;
    }
    
}
