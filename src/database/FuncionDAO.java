package database;

import model.Funcion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuncionDAO {

    public static ArrayList<Funcion> obtenerTodas() {
        ArrayList<Funcion> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM funciones");
            while (rs.next()) {
                Funcion f = new Funcion();
                f.setIdFuncion(rs.getInt("id_funcion"));
                f.setIdPelicula(rs.getInt("id_pelicula"));
                f.setIdSala(rs.getInt("id_sala"));
                f.setFecha(rs.getString("fecha"));
                f.setHora(rs.getString("hora"));
                f.setPrecio(rs.getDouble("precio"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo funciones: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static Funcion obtenerPorId(int id) {
        Conexion db = new Conexion();
        Funcion f = new Funcion();
        try {
            ResultSet rs = db.query("SELECT * FROM funciones WHERE id_funcion = " + id);
            if (rs.next()) {
                f.setIdFuncion(rs.getInt("id_funcion"));
                f.setIdPelicula(rs.getInt("id_pelicula"));
                f.setIdSala(rs.getInt("id_sala"));
                f.setFecha(rs.getString("fecha"));
                f.setHora(rs.getString("hora"));
                f.setPrecio(rs.getDouble("precio"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        } finally {
            db.closeConnection();
        }
        return f;
    }

    public static ArrayList<Funcion> obtenerPorPelicula(int idPelicula) {
        ArrayList<Funcion> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM funciones WHERE id_pelicula = " + idPelicula + " ORDER BY fecha, hora");
            while (rs.next()) {
                Funcion f = new Funcion();
                f.setIdFuncion(rs.getInt("id_funcion"));
                f.setIdPelicula(rs.getInt("id_pelicula"));
                f.setIdSala(rs.getInt("id_sala"));
                f.setFecha(rs.getString("fecha"));
                f.setHora(rs.getString("hora"));
                f.setPrecio(rs.getDouble("precio"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo funciones por pelicula: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static void insertar(Funcion f) {
        Conexion db = new Conexion();
        try {
            db.insert("INSERT INTO funciones VALUES ("
                    + f.getIdFuncion() + ", "
                    + f.getIdPelicula() + ", "
                    + f.getIdSala() + ", '"
                    + f.getFecha() + "', '"
                    + f.getHora() + "', "
                    + f.getPrecio() + ")");
        } catch (SQLException ex) {
            System.out.println("Error insertando: " + ex);
        } finally {
            db.closeConnection();
        }
    }

    public static void actualizar(Funcion f) {
        Conexion db = new Conexion();
        try {
            db.update("UPDATE funciones SET "
                    + "id_pelicula = " + f.getIdPelicula() + ", "
                    + "id_sala = " + f.getIdSala() + ", "
                    + "fecha = '" + f.getFecha() + "', "
                    + "hora = '" + f.getHora() + "', "
                    + "precio = " + f.getPrecio() + " "
                    + "WHERE id_funcion = " + f.getIdFuncion());
        } catch (SQLException ex) {
            System.out.println("Error actualizando: " + ex);
        } finally {
            db.closeConnection();
        }
    }

    public static void eliminar(int id) {
        Conexion db = new Conexion();
        try {
            db.delete("DELETE FROM funciones WHERE id_funcion = " + id);
        } catch (SQLException ex) {
            System.out.println("Error eliminando: " + ex);
        } finally {
            db.closeConnection();
        }
    }
}
