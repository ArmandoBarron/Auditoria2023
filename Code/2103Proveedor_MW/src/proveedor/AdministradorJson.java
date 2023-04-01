/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proveedor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author hreyes
 */


public class AdministradorJson {

    private JSONObject proveedor;    
    private JSONObject origen;    
    private JSONObject verificacion;


    
    public AdministradorJson(){
        
    }
    
    public JSONObject generarJsonProveedor(DTODatosProveedor dp){
        
        proveedor = new JSONObject();        
        
        proveedor.put("nombrePrueba", dp.getNombrePrueba());
        proveedor.put("numeroPares", dp.getNumeroArchivos());
        proveedor.put("nombreCiudad", dp.getNombreCiudad());
        for (int i = 0; i < dp.getNumeroArchivos(); i++ ) {
        //    System.out.println("H " + dp.getUnNombre(i) + " " + dp.getUnHash(i) + " " + dp.getUnHashFirmado(i));
            
            JSONArray datos = new JSONArray();
            datos.add(dp.getUnNombre(i));
            datos.add(dp.getUnHash(i));
            datos.add(dp.getUnHashFirmado(i));
            datos.add(dp.getUnTimestamp(i));
            proveedor.put(i, datos);
            
        }
        
        
        return proveedor;
        
    }
}
