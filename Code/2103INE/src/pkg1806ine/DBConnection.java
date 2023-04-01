/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1806ine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hreyes
 */
public class DBConnection {
    
    private Connection conexion;
    
    private String db = "2023Auditoria";
    //private String url = "jdbc:mysql://adaptivez.org.mx/"+db;
    private String url = "jdbc:mysql://127.0.0.1:3306/"+db;
    private String user = "AuditPHP";
    private String pass = "@udi20215963H";
    
    public void getMysqlConnection(){
        System.out.println("Conectandose con BD ");

        conexion = null;

       /*try{

           Class.forName("com.mysql.jdbc.Driver");

           //conexion = DriverManager.getConnection(this.url, this.user, this.pass);
           conexion = DriverManager.getConnection(this.url+"?");
       }catch(SQLException ex){

           System.out.println(ex);

       } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }*/
       try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de MySQL: " + ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
       
    }
    
    public void closeMysqlConnection(){
        
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    

    public LinkedList<DTORegistroProveedor> obtenerDatosProveedor(String nombrePrueba){
        LinkedList<DTORegistroProveedor> respuesta;
        try {
            respuesta = new LinkedList<>();
            String sqlConsulta;
            
            sqlConsulta = "SELECT idRegistrosProveedor,NombreArchivo, NombreCiudad, NombrePrueba, Hash, HashFirmado FROM RegistrosProveedor WHERE NombrePrueba LIKE '%"+nombrePrueba+"%'";
            
            
            
            
            Statement statement = this.conexion.createStatement();
            ResultSet result = statement.executeQuery(sqlConsulta);
            
            while (result.next()){
            
                DTORegistroProveedor actual = new DTORegistroProveedor(
                        result.getInt("idRegistrosProveedor"),
                        result.getString("NombreArchivo"),
                        result.getString("NombreCiudad"),
                        result.getString("NombrePrueba"),
                        result.getString("Hash"),
                        result.getString("HashFirmado")
                );
                
                respuesta.add(actual);
            }
            
                    
            return respuesta;
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        

        
    }

    
    
    
    
    
}
