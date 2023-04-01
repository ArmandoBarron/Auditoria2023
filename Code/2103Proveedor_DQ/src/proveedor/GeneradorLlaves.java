/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedor;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author Hugo G. Reyes-Anastacio
 * CINVESTAV Tamaulipas
 * @date 18-06-06
 * 
 */
public class GeneradorLlaves {
    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String rutaLlaves;
    
    public GeneradorLlaves(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);
    }
    
    public void crearLlaves() {
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }
    
    public PrivateKey obtenerLlavePrivada() {
        return this.privateKey;
    }

    public PublicKey obtenerLlavePublica() {
        return this.publicKey;
    }
    
    public String obtenerRutaLlaves(){
        return this.rutaLlaves;
    }
    
}
