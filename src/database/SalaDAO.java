package database;

import model.ReporteSala;
import model.Sala;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaDAO {

    public static ArrayList<Sala> obtenerTodas() {
        ArrayList<Sala> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM salas");
            while (rs.next()) {
                Sala s = new Sala();
                s.setIdSala(rs.getInt("id_sala"));
                s.setNombre(rs.getString("nombre"));
                s.setCapacidad(rs.getInt("capacidad"));
                lista.add(s);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo salas: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static Sala obtenerPorId(int id) {
        Conexion db = new Conexion();
        Sala s = new Sala();
        try {
            ResultSet rs = db.query("SELECT * FROM salas WHERE id_sala = " + id);
            if (rs.next()) {
                s.setIdSala(rs.getInt("id_sala"));
                s.setNombre(rs.getString("nombre"));
                s.setCapacidad(rs.getInt("capacidad"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        } finally {
            db.closeConnection();
        }
        return s;
    }

    /**
     * Reporte de recaudacion por sala, ordenado por mayor recaudacion.
     */
    public static ArrayList<ReporteSala> obtenerReporte() {
        ArrayList<ReporteSala> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            String sql = "SELECT s.id_sala, s.nombre, "
                    + "COUNT(DISTINCT f.id_funcion) AS cantidad_funciones, "
                    + "COUNT(dr.id_detalle) AS entradas_vendidas, "
                    + "COALESCE(SUM(f.precio), 0) AS recaudacion_total "
                    + "FROM salas s "
                    + "LEFT JOIN funciones f ON f.id_sala = s.id_sala "
                    + "LEFT JOIN detalle_reserva dr ON dr.id_funcion = f.id_funcion "
                    + "GROUP BY s.id_sala, s.nombre "
                    + "ORDER BY recaudacion_total DESC";
            ResultSet rs = db.query(sql);
            while (rs.next()) {
                ReporteSala r = new ReporteSala();
                r.setIdSala(rs.getInt("id_sala"));
                r.setNombre(rs.getString("nombre"));
                r.setCantidadFunciones(rs.getInt("cantidad_funciones"));
                r.setEntradasVendidas(rs.getInt("entradas_vendidas"));
                r.setRecaudacionTotal(rs.getDouble("recaudacion_total"));
                lista.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo reporte: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static void insertar(Sala s) {
        Conexion db = new Conexion();
        try {
            db.insert("INSERT INTO salas VALUES ("
                    + s.getIdSala() + ", '"
                    + s.getNombre() + "', "
                    + s.getCapacidad() + ")");
        } catch (SQLException ex) {
            System.out.println("Error insertando: " + ex);
        } finally {
            db.closeConnection();
        }
    }

    public static void actualizar(Sala s) {
        Conexion db = new Conexion();
        try {
            db.update("UPDATE salas SET "
                    + "nombre = '" + s.getNombre() + "', "
                    + "capacidad = " + s.getCapacidad() + " "
                    + "WHERE id_sala = " + s.getIdSala());
        } catch (SQLException ex) {
            System.out.println("Error actualizando: " + ex);
        } finally {
            db.closeConnection();
        }
    }

    public static void eliminar(int id) {
        Conexion db = new Conexion();
        try {
            db.delete("DELETE FROM salas WHERE id_sala = " + id);
        } catch (SQLException ex) {
            System.out.println("Error eliminando: " + ex);
        } finally {
            db.closeConnection();
        }
    }
}
