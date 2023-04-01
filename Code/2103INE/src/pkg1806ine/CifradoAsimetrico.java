/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1806ine;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.util.Base64;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author hreyes
 */
public class CifradoAsimetrico {
    private Cipher cifrador;
    
    public CifradoAsimetrico(){
        try {
            this.cifrador = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CifradoAsimetrico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(CifradoAsimetrico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public PrivateKey getPrivate(String filename)  {
        try {
            byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (IOException ex) {
            Logger.getLogger(CifradoAsimetrico.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CifradoAsimetrico.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(CifradoAsimetrico.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public PublicKey getPublic(String filename){
        try {
            byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (IOException ex) {
            Logger.getLogger(CifradoAsimetrico.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CifradoAsimetrico.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(CifradoAsimetrico.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    	
    public String encryptText(String msg, PrivateKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
	this.cifrador.init(Cipher.ENCRYPT_MODE, key);    
	//return Base64.getEncoder().encode(cifrador.doFinal(msg.getBytes("UTF-8")));
	return new String (Base64.getEncoder().encode(cifrador.doFinal(msg.getBytes("UTF-8"))));
        

//return cifrador.doFinal(msg.getBytes("UTF-8"));
    }
	
    public String decryptText(String msg, PublicKey key) throws InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{
	this.cifrador.init(Cipher.DECRYPT_MODE, key);
	return new String(cifrador.doFinal(Base64.getDecoder().decode(msg)), "UTF-8");
    }
	
    
}
