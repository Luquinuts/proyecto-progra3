package palavecinoexamen2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Producto {

    int id_producto;
    String nombre;
    double precio_base;

    public Producto(int id_producto, String nombre, double precio_base) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.precio_base = precio_base;
    }

    public int getId_producto() {
        return id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio_base() {
        return precio_base;
    }

    public void insertar_producto() {
        Conexion c = new Conexion("localhost", "comercio", "root", "");
        String consulta = "INSERT INTO productos (id_producto, nombre, precio_base) VALUES ("
                + id_producto + ",'" + nombre + "', " + precio_base + ")";
        c.insertar(consulta);
    }

    public static ArrayList<Producto> obtener_productos() {
        ArrayList<Producto> lista = new ArrayList<>();
        Conexion c = new Conexion("localhost", "comercio", "root", "");
        try {
            ResultSet rs = c.consultar("SELECT id_producto, nombre, precio_base FROM productos");
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getDouble("precio_base")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return lista;
    }
}
