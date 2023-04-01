/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1806ine;


/**
 *
 * @author hreyes
 */
public class DTORegistroProveedor {
    
    private final int idRegistro;
    private final String nombreArchivo;
    private final String nombreCiudad;
    private final String nombrePrueba;
    private final String hash;
    private final String hashFirmado;
    private boolean firmaValida;
    private String hashVerificado;
    
    public DTORegistroProveedor(int idRegistro, String nombreArchivo,
            String nombreCiudad, String nombrePrueba, String hash, String hashFirmado){
        this.idRegistro = idRegistro;
        this.nombreArchivo = nombreArchivo;
        this.nombreCiudad = nombreCiudad;
        this.nombrePrueba = nombrePrueba;
        this.hash = hash;
        this.hashFirmado = hashFirmado;
        this.hashVerificado = null;
    }

    public void setFirmaValida(boolean firmaValida) {
        this.firmaValida = firmaValida;
    }
    
    public int getIdRegistro() {
        return idRegistro;
    }

    public String getNombrePrueba() {
        return nombrePrueba;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getHash() {
        return hash;
    }

    public String getHashFirmado() {
        return hashFirmado;
    }

    public boolean isFirmaValida() {
        return firmaValida;
    }

    public String getHashVerificado() {
        return hashVerificado;
    }

    public void setHashVerificado(String hashVerificado) {
        this.hashVerificado = hashVerificado;
    }
    
    
    
    
    
   
    
    
    
    
}
