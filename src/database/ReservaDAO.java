package database;

import model.Butaca;
import model.Cliente;
import model.Funcion;
import model.Reserva;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public static ArrayList<Reserva> obtenerTodas() {
        ArrayList<Reserva> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM reservas");
            while (rs.next()) {
                Reserva r = new Reserva();
                r.setIdReserva(rs.getInt("id_reserva"));
                r.setIdCliente(rs.getInt("id_cliente"));
                r.setFechaReserva(rs.getString("fecha_reserva"));
                lista.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo reservas: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static Reserva obtenerPorId(int id) {
        Conexion db = new Conexion();
        Reserva r = new Reserva();
        try {
            ResultSet rs = db.query("SELECT * FROM reservas WHERE id_reserva = " + id);
            if (rs.next()) {
                r.setIdReserva(rs.getInt("id_reserva"));
                r.setIdCliente(rs.getInt("id_cliente"));
                r.setFechaReserva(rs.getString("fecha_reserva"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        } finally {
            db.closeConnection();
        }
        return r;
    }

    /**
     * METODO PRINCIPAL: Verifica disponibilidad, inserta en reservas y
     * detalle_reserva dentro del mismo flujo. Retorna true si se reservaron
     * todas las butacas solicitadas, false si alguna ya estaba ocupada.
     */
    public static boolean reservarButacas(Cliente cliente, Funcion funcion, List<Integer> idsButacas) {
        Conexion db = new Conexion();
        try {
            if (db.getConnection() == null) {
                System.out.println("Error: No hay conexion a la base de datos");
                return false;
            }

            // 1. Verificar que todas las butacas esten disponibles
            for (int idButaca : idsButacas) {
                String checkSql = "SELECT COUNT(*) FROM detalle_reserva "
                        + "WHERE id_funcion = " + funcion.getIdFuncion() + " "
                        + "AND id_butaca = " + idButaca;
                ResultSet rs = db.query(checkSql);
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Butaca " + idButaca + " ya esta reservada");
                    return false;
                }
            }

            // 2. Obtener el proximo id_reserva
            int idReserva = 1;
            ResultSet rsMax = db.query("SELECT COALESCE(MAX(id_reserva), 0) + 1 FROM reservas");
            if (rsMax.next()) {
                idReserva = rsMax.getInt(1);
            }

            // 3. Insertar en reservas
            String fechaActual = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
            db.insert("INSERT INTO reservas VALUES ("
                    + idReserva + ", "
                    + cliente.getIdCliente() + ", '"
                    + fechaActual + "')");

            // 4. Obtener el proximo id_detalle
            rsMax = db.query("SELECT COALESCE(MAX(id_detalle), 0) + 1 FROM detalle_reserva");
            int idDetalle = 1;
            if (rsMax.next()) {
                idDetalle = rsMax.getInt(1);
            }

            // 5. Insertar en detalle_reserva para cada butaca
            for (int idButaca : idsButacas) {
                db.insert("INSERT INTO detalle_reserva VALUES ("
                        + idDetalle + ", "
                        + idReserva + ", "
                        + funcion.getIdFuncion() + ", "
                        + idButaca + ")");
                idDetalle++;
            }

            return true;

        } catch (SQLException ex) {
            System.out.println("Error en reservarButacas: " + ex);
            return false;
        } finally {
            db.closeConnection();
        }
    }

    /**
     * Retorna una lista con los IDs de butacas reservadas para una funcion.
     */
    public static ArrayList<Integer> obtenerDetallePorFuncion(int idFuncion) {
        ArrayList<Integer> butacasReservadas = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            String sql = "SELECT dr.id_butaca FROM detalle_reserva dr "
                    + "JOIN butacas b ON dr.id_butaca = b.id_butaca "
                    + "WHERE dr.id_funcion = " + idFuncion;
            ResultSet rs = db.query(sql);
            while (rs.next()) {
                butacasReservadas.add(rs.getInt("id_butaca"));
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo detalle de reserva: " + ex);
        } finally {
            db.closeConnection();
        }
        return butacasReservadas;
    }

    /**
     * Retorna la ultima reserva insertada.
     */
    public static Reserva obtenerUltimaReserva() {
        Conexion db = new Conexion();
        Reserva r = new Reserva();
        try {
            ResultSet rs = db.query("SELECT * FROM reservas ORDER BY id_reserva DESC LIMIT 1");
            if (rs.next()) {
                r.setIdReserva(rs.getInt("id_reserva"));
                r.setIdCliente(rs.getInt("id_cliente"));
                r.setFechaReserva(rs.getString("fecha_reserva"));
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo ultima reserva: " + ex);
        } finally {
            db.closeConnection();
        }
        return r;
    }
}
