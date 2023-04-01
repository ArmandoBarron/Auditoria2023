/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1806ine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hreyes
 */
public class Config {

    private String ipsServer;
    private String fileName;
    private BufferedReader br;
    
    public Config(String fileName){
        this.fileName = fileName;
        readConfigurationFile();
    }
    
   
    
    public String getIpServer(){
        return this.ipsServer;
    }
    
    private void readConfigurationFile(){
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(fileName));
            
             while ((sCurrentLine = br.readLine()) != null) {
                 
                 String data [] = sCurrentLine.split(":");
                 
                 switch(data[0]){
                    case "ip":
                        
                        ipsServer = data[1];
                        
                        
                        break;
                    
                 }
                 
             }
             
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
