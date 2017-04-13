/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SheshoVega
 */
public class Database {
    Connection cnx = null;
        
    public Database() {
        this.getConnection();
        if( this.cnx != null){
            System.out.println("Conexion a BD Exitosa");
        }else{
            System.out.println("Conexion a BD Cerrada!");
        }
        
    }
       
    public Connection getConnection(){
        try{
            String servidor = PROTOCOLO+"//"+SERVIDOR+":"+PUERTO+"/"+BASEDATOS;
            String user = USUARIO;
            String pass = CLAVE;
            Class.forName(MANEJADOR_DB);
            this.cnx = DriverManager.getConnection(servidor, user, pass);
        } catch (ClassNotFoundException e) {
            System.out.println("Error en ClassNotFound");
            System.err.println(e.getMessage());
            this.cnx = null;
        }catch (SQLException e) {
            System.out.println("Error en SQL");
            System.err.println(e.getMessage());
            this.cnx = null;
        }catch(Exception e){
            System.err.println(e.getMessage());
            this.cnx = null;
        }finally {
            return this.cnx;
        }
    }
    
    public int executeUpdate(String statement) {
        try {
            Statement stm = cnx.createStatement();
            stm.executeUpdate(statement);
            return stm.getUpdateCount();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public ResultSet executeUpdateWithKeys(String statement) {
        try {
            Statement stm = cnx.createStatement();
            stm.executeUpdate(statement,Statement.RETURN_GENERATED_KEYS);
            return stm.getGeneratedKeys();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ResultSet executeQuery(String statement){
        try {
            Statement stm = cnx.createStatement();
            return stm.executeQuery(statement);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static final String MANEJADOR_DB = "com.mysql.jdbc.Driver";
    private static final String PROTOCOLO = "jdbc:mysql:";
    private static final String SERVIDOR = "localhost";
    private static final String PUERTO = "3306";
    private static final String USUARIO = "root";
    private static final String CLAVE = "root";
    private static final String BASEDATOS = "universidad";
}
