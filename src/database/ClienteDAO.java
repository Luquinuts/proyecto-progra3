package database;

import model.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO {

    public static ArrayList<Cliente> obtenerTodos() {
        ArrayList<Cliente> lista = new ArrayList<>();
        Conexion db = new Conexion();
        try {
            ResultSet rs = db.query("SELECT * FROM clientes");
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
                lista.add(c);
            }
        } catch (SQLException ex) {
            System.out.println("Error obteniendo clientes: " + ex);
        } finally {
            db.closeConnection();
        }
        return lista;
    }

    public static Cliente obtenerPorId(int id) {
        Conexion db = new Conexion();
        Cliente c = new Cliente();
        try {
            ResultSet rs = db.query("SELECT * FROM clientes WHERE id_cliente = " + id);
            if (rs.next()) {
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        } finally {
            db.closeConnection();
        }
        return c;
    }

    public static void insertar(Cliente c) {
        Conexion db = new Conexion();
        try {
            db.insert("INSERT INTO clientes VALUES ("
                    + c.getIdCliente() + ", '"
                    + c.getNombre() + "', '"
                    + c.getApellido() + "', '"
                    + c.getEmail() + "', '"
                    + c.getTelefono() + "')");
        } catch (SQLException ex) {
            System.out.println("Error insertando: " + ex);
        } finally {
            db.closeConnection();
        }
    }

    public static void actualizar(Cliente c) {
        Conexion db = new Conexion();
        try {
            db.update("UPDATE clientes SET "
                    + "nombre = '" + c.getNombre() + "', "
                    + "apellido = '" + c.getApellido() + "', "
                    + "email = '" + c.getEmail() + "', "
                    + "telefono = '" + c.getTelefono() + "' "
                    + "WHERE id_cliente = " + c.getIdCliente());
        } catch (SQLException ex) {
            System.out.println("Error actualizando: " + ex);
        } finally {
            db.closeConnection();
        }
    }

    public static void eliminar(int id) {
        Conexion db = new Conexion();
        try {
            db.delete("DELETE FROM clientes WHERE id_cliente = " + id);
        } catch (SQLException ex) {
            System.out.println("Error eliminando: " + ex);
        } finally {
            db.closeConnection();
        }
    }
}
