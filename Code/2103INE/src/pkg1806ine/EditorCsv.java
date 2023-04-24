/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1806ine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hreyes
 * @modifier Jbarron
 */
public class EditorCsv {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private String fileName;

    //Encabezado
    //private static final String FILE_HEADER = "NombreArchivo, NombreCiudad, SHA256Original, Firma Original, Nombre del Evento, SHA256Evento, Firma Evento, Verificación Integridad, Timestamp";
    private static final String FILE_HEADER = "NombreArchivo, NombreCiudad, SHA256Original, Firma Original, Nombre del Evento, SHA256Evento, Firma Evento, Verificación Integridad, Timestamp, Componente, Veredicto, Apariciones, Padre, Total";

    public EditorCsv(String fileName) {
        
        this.fileName = fileName;
        verificarExistenciaArchivo();

    }

    private void verificarExistenciaArchivo() {
        File archivo = new File(this.fileName);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                escribirPrimeraLinea();
            } catch (IOException ex) {
                Logger.getLogger(EditorCsv.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    private void escribirPrimeraLinea(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(this.fileName, true);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(EditorCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void escribirLinea(String linea) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName, true);
            //Write the CSV file header
            fileWriter.append(linea);
            fileWriter.append(NEW_LINE_SEPARATOR);
        } catch (IOException ex) {
            Logger.getLogger(EditorCsv.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error al escribir la linea en la bitacora");
            }
        }

    }

}
