package database;

import model.Funcion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuncionDAO {

    /**
     * Retorna TODAS las funciones (activas e inactivas) — para admin ABM.
     */
    public static ArrayList<Funcion> obtenerTodas() {
        ArrayList<Funcion> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM funciones ORDER BY fecha, hora");
            while (rs.next()) {
                lista.add(mapearFuncion(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo funciones: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    /**
     * Retorna solo funciones activas — para el flujo de reserva.
     */
    public static ArrayList<Funcion> obtenerTodasActivas() {
        ArrayList<Funcion> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM funciones WHERE activa = 1 ORDER BY fecha, hora");
            while (rs.next()) {
                lista.add(mapearFuncion(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo funciones activas: " + ex);
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
                f = mapearFuncion(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        } finally {
            db.closeConnection();
        }
        return f;
    }

    /**
     * Retorna funciones activas para una pelicula — usado en el flujo de reserva.
     */
    public static ArrayList<Funcion> obtenerPorPelicula(int idPelicula) {
        ArrayList<Funcion> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM funciones WHERE id_pelicula = " + idPelicula
                    + " AND activa = 1 ORDER BY fecha, hora");
            while (rs.next()) {
                lista.add(mapearFuncion(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo funciones por pelicula: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    /**
     * Verifica si existe una funcion activa con misma sala, fecha y hora.
     * @param idExcluir opcional, para excluir una funcion al editar (pasar 0 si no aplica)
     */
    public static boolean existeFuncionActiva(int idSala, String fecha, String hora, int idExcluir) {
        Conexion db = new Conexion();
        try {
            String sql = "SELECT COUNT(*) FROM funciones "
                    + "WHERE activa = 1 AND id_sala = " + idSala
                    + " AND fecha = '" + fecha + "'"
                    + " AND hora = '" + hora + "'";
            if (idExcluir > 0) {
                sql += " AND id_funcion != " + idExcluir;
            }
            ResultSet rs = db.query(sql);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error verificando duplicado: " + ex);
        } finally {
            db.closeConnection();
        }
        return false;
    }

    /**
     * Verifica si una funcion tiene reservas asociadas.
     */
    public static boolean tieneReservas(int idFuncion) {
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT COUNT(*) FROM detalle_reserva WHERE id_funcion = " + idFuncion);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error verificando reservas: " + ex);
        } finally {
            db.closeConnection();
        }
        return false;
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
                    + f.getPrecio() + ", "
                    + f.getActiva() + ")");
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
                    + "precio = " + f.getPrecio() + ", "
                    + "activa = " + f.getActiva() + " "
                    + "WHERE id_funcion = " + f.getIdFuncion());
        } catch (SQLException ex) {
            System.out.println("Error actualizando: " + ex);
        } finally {
            db.closeConnection();
        }
    }

    /**
     * Baja logica: desactiva una funcion (activa = 0).
     */
    public static void desactivar(int idFuncion) {
        Conexion db = new Conexion();
        try {
            db.update("UPDATE funciones SET activa = 0 WHERE id_funcion = " + idFuncion);
        } catch (SQLException ex) {
            System.out.println("Error desactivando funcion: " + ex);
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

    // --- Helper ---

    private static Funcion mapearFuncion(ResultSet rs) throws SQLException {
        Funcion f = new Funcion();
        f.setIdFuncion(rs.getInt("id_funcion"));
        f.setIdPelicula(rs.getInt("id_pelicula"));
        f.setIdSala(rs.getInt("id_sala"));
        f.setFecha(rs.getString("fecha"));
        f.setHora(rs.getString("hora"));
        f.setPrecio(rs.getDouble("precio"));
        f.setActiva(rs.getInt("activa"));
        return f;
    }
}
