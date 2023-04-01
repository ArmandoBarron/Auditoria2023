/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hreyes
 */
public class EditorCsv {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private String csvName;


    //Encabezado
    private static final String FILE_HEADER = "NombreCarpeta, NombreArchivo, RequiereSegmentacion, idTrabajador, SHA3-256";

    public EditorCsv(String fileName) {
        
        this.csvName = fileName;
        verificarExistenciaArchivo();

    }

    private void verificarExistenciaArchivo() {
        File archivo = new File(this.csvName);
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
            fileWriter = new FileWriter(this.csvName, true);
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
            fileWriter = new FileWriter(csvName, true);
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
