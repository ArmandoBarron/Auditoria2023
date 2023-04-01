package proveedor;

/*
 * Files.java         

 * This module is part of the --DET-ABE API--, a Java library  
 * of the SSCABE project (Secure Storage and Sharing of data in the 
 * Cloud by using Attribute based encryption).
 * The DET-ABE library is free software. This library is distributed 
 * on an "AS IS" basis,WITHOUT WARRANTY OF ANY KIND, either expressed 
 * or implied.
 
 * Copyright (c) Miguel Morales-Sandoval (morales.sandoval.miguel@gmail.com)
 * Created:           October 2, 2014
 * Last modification: 
 */

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author      Miguel Morales-Sandoval    
 * @version     1.0
 * @since       2015-02-20

 */
public class AdministradorArchivos{

/** reads and returns the stream of bytes of a file with the name given as input.
 *
 */   
   public static byte[] readBytesFromFile(String strFilePath) {   
      try{
         File file = new File(strFilePath);   
         byte memoria[] = new byte[(int)file.length()];
         FileInputStream fis = new FileInputStream(strFilePath);   
         fis.read(memoria);   
         fis.close();
         return memoria;
      }
      catch(Exception ex){
         System.out.println("IOFiles MODULE: Exception : " + ex);
         return null;
      }         
   }
   
   /**
    * stores an array of bytes in a file with the name given as input.
    */
   public static boolean storeBytesInFile(byte[] datos, String strFilePath) {   
        boolean respuesta = false;
           try {
                 FileOutputStream fos = new FileOutputStream(strFilePath);   
                 fos.write(datos);
                 fos.close();      
                 respuesta = true;

            } catch (IOException ex) {
                Logger.getLogger(AdministradorArchivos.class.getName()).log(Level.SEVERE, null, ex);
            } catch(Exception ex){
                System.out.println("IOFiles MODULE: Exception : " + ex);         
            }   
        return respuesta;
    }

/**
 * tests if the file with name given as input exists or not in the filesystem.
 */      
   public static boolean testIfExistsFile(String filename){
   
      File file = new File(filename);
      if (file.exists())
         return true;
      else 
         return false;
   }
   
   /**
    * tests if two files have the same content
    */
    
   public static boolean isFileBinaryEqual(String file1, String file2) throws IOException
   {
   
      File first = new File(file1);
      File second = new File(file2);
      // TODO: Test: Missing test
      boolean retval = false;
      final int BUFFER_SIZE = 65536;
      
      if ((first.exists()) && (second.exists()) 
         && (first.isFile()) && (second.isFile()))
      {
         if (first.getCanonicalPath().equals(second.getCanonicalPath()))
         {
            retval = true;
         }
         else
         {
            FileInputStream firstInput = null;
            FileInputStream secondInput = null;
            BufferedInputStream bufFirstInput = null;
            BufferedInputStream bufSecondInput = null;
         
            try
            {            
               firstInput = new FileInputStream(first); 
               secondInput = new FileInputStream(second);
               bufFirstInput = new BufferedInputStream(firstInput, BUFFER_SIZE); 
               bufSecondInput = new BufferedInputStream(secondInput, BUFFER_SIZE);
            
               int firstByte;
               int secondByte;
               
               while (true)
               {
                  firstByte = bufFirstInput.read();
                  secondByte = bufSecondInput.read();
                  if (firstByte != secondByte)
                  {
                     break;
                  }
                  if ((firstByte < 0) && (secondByte < 0))
                  {
                     retval = true;
                     break;
                  }
               }
            }
            finally
            {
               try
               {
                  if (bufFirstInput != null)
                  {
                     bufFirstInput.close();
                  }
               }
               finally
               {
                  if (bufSecondInput != null)
                  {
                     bufSecondInput.close();
                  }
               }
            }
         }
      }
      
      return retval;
   }

}