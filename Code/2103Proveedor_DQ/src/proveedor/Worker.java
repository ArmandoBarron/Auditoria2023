/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedor;

/**
 *
 * @author juan_
 */
public class Worker extends Thread  {
    
    private byte[] Chunk;
    private String sha256;
    public void run()
    {
        byte [] hash = Hash.getStringMessageDigest(this.Chunk, "SHA3-256");
        this.sha256 = Hash.toHexadecimal(hash);
    }
    
    
    public void setContenido(byte[] Chunk) {
        this.Chunk = Chunk;
    }
    
    public void cleanContenido (){
        this.Chunk = new byte[0];
        System.gc();
    }
    
   public String GetHash() {
        return this.sha256;
    }
    
}
