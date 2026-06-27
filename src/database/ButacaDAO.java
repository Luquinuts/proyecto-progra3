package database;

import model.Butaca;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ButacaDAO {

    public static ArrayList<Butaca> obtenerTodas() {
        ArrayList<Butaca> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM butacas");
            while (rs.next()) {
                Butaca b = new Butaca();
                b.setIdButaca(rs.getInt("id_butaca"));
                b.setIdSala(rs.getInt("id_sala"));
                b.setFila(rs.getString("fila"));
                b.setNumero(rs.getInt("numero"));
                lista.add(b);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo butacas: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static Butaca obtenerPorId(int id) {
        Conexion db = new Conexion();
        Butaca b = new Butaca();
        try {
            ResultSet rs = db.query("SELECT * FROM butacas WHERE id_butaca = " + id);
            if (rs.next()) {
                b.setIdButaca(rs.getInt("id_butaca"));
                b.setIdSala(rs.getInt("id_sala"));
                b.setFila(rs.getString("fila"));
                b.setNumero(rs.getInt("numero"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        } finally {
            db.closeConnection();
        }
        return b;
    }

    public static ArrayList<Butaca> obtenerPorSala(int idSala) {
        ArrayList<Butaca> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM butacas WHERE id_sala = " + idSala + " ORDER BY id_butaca");
            while (rs.next()) {
                Butaca b = new Butaca();
                b.setIdButaca(rs.getInt("id_butaca"));
                b.setIdSala(rs.getInt("id_sala"));
                b.setFila(rs.getString("fila"));
                b.setNumero(rs.getInt("numero"));
                lista.add(b);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo butacas por sala: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static ArrayList<Butaca> obtenerDisponiblesPorFuncion(int idFuncion) {
        ArrayList<Butaca> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            String sql = "SELECT b.* FROM butacas b "
                    + "WHERE b.id_sala = (SELECT id_sala FROM funciones WHERE id_funcion = " + idFuncion + ") "
                    + "AND b.id_butaca NOT IN ("
                    + "SELECT dr.id_butaca FROM detalle_reserva dr "
                    + "JOIN reservas r ON dr.id_reserva = r.id_reserva "
                    + "WHERE dr.id_funcion = " + idFuncion + ") "
                    + "ORDER BY b.id_butaca";
            ResultSet rs = db.query(sql);
            while (rs.next()) {
                Butaca b = new Butaca();
                b.setIdButaca(rs.getInt("id_butaca"));
                b.setIdSala(rs.getInt("id_sala"));
                b.setFila(rs.getString("fila"));
                b.setNumero(rs.getInt("numero"));
                lista.add(b);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo butacas disponibles: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static void insertar(Butaca b) {
        Conexion db = new Conexion();
        try {
            db.insert("INSERT INTO butacas VALUES ("
                    + b.getIdButaca() + ", "
                    + b.getIdSala() + ", '"
                    + b.getFila() + "', "
                    + b.getNumero() + ")");
        } catch (SQLException ex) {
            System.out.println("Error insertando: " + ex);
        } finally {
            db.closeConnection();
        }
    }

    public static void eliminar(int id) {
        Conexion db = new Conexion();
        try {
            db.delete("DELETE FROM butacas WHERE id_butaca = " + id);
        } catch (SQLException ex) {
            System.out.println("Error eliminando: " + ex);
        } finally {
            db.closeConnection();
        }
    }
}
