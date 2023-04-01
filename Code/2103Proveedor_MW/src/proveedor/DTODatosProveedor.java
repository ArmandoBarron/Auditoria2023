/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedor;


/**
 *
 * @author hreyes
 */
public class DTODatosProveedor {
    
    private int numeroArchivos;
    private String nombrePrueba;
    private String nombreCiudad;
    private String nombre[];
    private String hash[];
    private String hashFirmado[];
    private String timesTamp[];
    
    public DTODatosProveedor(int numeroArchivos){
        this.numeroArchivos = numeroArchivos;
        this.nombre = new String[numeroArchivos];
        this.hash = new String[numeroArchivos];
        this.hashFirmado = new String[numeroArchivos];
        this.timesTamp = new String[numeroArchivos];
    }
    
    public void setNombre(int i, String nombre){
        this.nombre[i] = nombre;
    }
    
    public void setHash(int i, String hash){
        this.hash[i] = hash;
    }
    
    public void setHashFirmado(int i, String hashFirmado){
        this.hashFirmado[i] = hashFirmado;
    }

    public int getNumeroArchivos() {
        return numeroArchivos;
    }

    public String[] getHash() {
        return hash;
    }

    public String[] getHashFirmado() {
        return hashFirmado;
    }
    
    
    public String getUnHash(int posicion){
        return hash[posicion];
    }
    
    
    public String getUnHashFirmado(int posicion){
        return hashFirmado[posicion];
    }
    
    public String getUnNombre(int posicion){
        return nombre[posicion];
    }

    public String getNombrePrueba() {
        return nombrePrueba;
    }

    public void setNombrePeueba(String nombrePrueba) {
        this.nombrePrueba = nombrePrueba;
    }

    public void setTimesTamp(int i, String timesTamp) {
        this.timesTamp[i] = timesTamp;
    }
    
    public String getUnTimestamp(int contador) {
        return this.timesTamp[contador];
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
    
    
    
    
    
}
