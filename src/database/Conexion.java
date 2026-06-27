package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private Connection conn = null;
    private String server = "localhost";
    private String database = "cine";
    private String user = "root";
    private String pass = "";
    private String url;

    public Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            url = "jdbc:mysql://" + server + ":3306/" + database;
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado a " + url);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error de conexion: " + ex);
        }
    }

    public Connection getConnection() { return conn; }

    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ResultSet query(String sql) throws SQLException {
        Statement stm = conn.createStatement();
        return stm.executeQuery(sql);
    }

    public int insert(String sql) throws SQLException {
        try (Statement stm = conn.createStatement()) {
            return stm.executeUpdate(sql);
        }
    }

    public int update(String sql) throws SQLException {
        try (Statement stm = conn.createStatement()) {
            return stm.executeUpdate(sql);
        }
    }

    public int delete(String sql) throws SQLException {
        try (Statement stm = conn.createStatement()) {
            return stm.executeUpdate(sql);
        }
    }
}
