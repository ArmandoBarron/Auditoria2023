package proveedor;

import sha3.Keccak;


import static sha3.Parameters.SHA3_224;
import static sha3.Parameters.SHA3_256;
import static sha3.Parameters.SHA3_384;
import static sha3.Parameters.SHA3_512;

public class Hash{ 
    
   
   public static String toHexadecimal(byte[] digest){ 
      String hash = ""; 
      for(byte aux : digest) { 
         int b = aux & 0xff; 
         if (Integer.toHexString(b).length() == 1) hash += "0"; 
         hash += Integer.toHexString(b); 
      } 
      return hash; 
   }      
 
   public static byte[] getStringMessageDigest(byte[] buffer, String algorithm){ 
        byte[] digest = null;
        Keccak keccak = new Keccak();
        switch(algorithm.toUpperCase()){
            case "SHA3-224":
                digest = keccak.getHash(buffer, SHA3_224);
            break;
            case "SHA3-256":
                digest = keccak.getHash(buffer, SHA3_256);
            break;
            case "SHA3-384":
                digest = keccak.getHash(buffer, SHA3_384);
            break;
            case "SHA3-512":
                digest = keccak.getHash(buffer, SHA3_512);
            break;
        }
   
        return digest; 
   }
   
   
   
   
}
