package database;

import model.Pelicula;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PeliculaDAO {

    public static ArrayList<Pelicula> obtenerTodas() {
        ArrayList<Pelicula> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM peliculas");
            while (rs.next()) {
                Pelicula p = new Pelicula();
                p.setIdPelicula(rs.getInt("id_pelicula"));
                p.setTitulo(rs.getString("titulo"));
                p.setGenero(rs.getString("genero"));
                p.setDuracion(rs.getInt("duracion"));
                p.setClasificacion(rs.getString("clasificacion"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo peliculas: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static Pelicula obtenerPorId(int id) {
        Conexion db = new Conexion();
        Pelicula p = new Pelicula();
        try {
            ResultSet rs = db.query("SELECT * FROM peliculas WHERE id_pelicula = " + id);
            if (rs.next()) {
                p.setIdPelicula(rs.getInt("id_pelicula"));
                p.setTitulo(rs.getString("titulo"));
                p.setGenero(rs.getString("genero"));
                p.setDuracion(rs.getInt("duracion"));
                p.setClasificacion(rs.getString("clasificacion"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        } finally {
            db.closeConnection();
        }
        return p;
    }

    public static void insertar(Pelicula p) {
        Conexion db = new Conexion();
        try {
            db.insert("INSERT INTO peliculas VALUES ("
                    + p.getIdPelicula() + ", '"
                    + p.getTitulo() + "', '"
                    + p.getGenero() + "', "
                    + p.getDuracion() + ", '"
                    + p.getClasificacion() + "')");
        } catch (SQLException ex) {
            System.out.println("Error insertando: " + ex);
        } finally {
            db.closeConnection();
        }
    }

    public static void eliminar(int id) {
        Conexion db = new Conexion();
        try {
            db.delete("DELETE FROM peliculas WHERE id_pelicula = " + id);
        } catch (SQLException ex) {
            System.out.println("Error eliminando: " + ex);
        } finally {
            db.closeConnection();
        }
    }
}
