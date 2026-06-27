/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ramir
 */
public class DBConnection {
    private Connection conn = null;
    private String server = "localhost";
    private String database = "Subastas";
    private String user = "root";
    private String pass = "root";
    private String url;
    
    public DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            url = "jdbc:mysql://" + server + ":3306/" + database;
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex){
            System.out.println(ex);
        }
    }
    
    public Connection getConnection(){
        return conn;
    }
    
    public void closeConnection(){
        try{
            conn.close();
        } catch (SQLException ex){
            System.out.println(ex);
        }
    }
    
    public synchronized ResultSet query(String query) throws SQLException{
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        return rs;
    }
    
    public synchronized int insert(String sql) throws SQLException {
        try(Statement stm = conn.createStatement()){
            return stm.executeUpdate(sql);
        }
    }
    
    public synchronized int actualizar(String sql) throws SQLException{
        try(Statement stm = conn.createStatement()){
            return stm.executeUpdate(sql);
        }
    }
}
