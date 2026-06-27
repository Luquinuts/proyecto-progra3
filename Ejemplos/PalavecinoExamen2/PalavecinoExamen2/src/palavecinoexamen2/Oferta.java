package palavecinoexamen2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Oferta {

    int id_oferta;
    int postulante_dni;
    int producto_id;
    double monto;
    String fecha_creacion;

    public Oferta(int id_oferta, int postulante_dni, int producto_id, double monto, String fecha_creacion) {
        this.id_oferta = id_oferta;
        this.postulante_dni = postulante_dni;
        this.producto_id = producto_id;
        this.monto = monto;
        this.fecha_creacion = fecha_creacion;
    }

    public int getId_oferta() {
        return id_oferta;
    }

    public int getPostulante_dni() {
        return postulante_dni;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public double getMonto() {
        return monto;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void insertar_oferta() {
        Conexion c = new Conexion("localhost", "comercio", "root", "");
        String consulta = "INSERT INTO ofertas (postulante_dni, producto_id, monto) VALUES ("
                + postulante_dni + ", " + producto_id + ", " + monto + ")";
        c.insertar(consulta);
    }

    public static ArrayList<Oferta> obtener_ofertas() {
        ArrayList<Oferta> lista = new ArrayList<>();
        Conexion c = new Conexion("localhost", "comercio", "root", "");
        try {
            ResultSet rs = c.consultar(
                    "SELECT id_oferta, postulante_dni, producto_id, monto, fecha_creacion FROM ofertas ORDER BY fecha_creacion DESC");
            while (rs.next()) {
                lista.add(new Oferta(
                        rs.getInt("id_oferta"),
                        rs.getInt("postulante_dni"),
                        rs.getInt("producto_id"),
                        rs.getDouble("monto"),
                        rs.getString("fecha_creacion")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return lista;
    }

    public static ArrayList<Oferta> obtener_ofertas_por_producto(int productoId) {
        ArrayList<Oferta> lista = new ArrayList<>();
        Conexion c = new Conexion("localhost", "comercio", "root", "");
        try {
            ResultSet rs = c.consultar(
                    "SELECT id_oferta, postulante_dni, producto_id, monto, fecha_creacion FROM ofertas "
                    + "WHERE producto_id = " + productoId + " ORDER BY fecha_creacion DESC");
            while (rs.next()) {
                lista.add(new Oferta(
                        rs.getInt("id_oferta"),
                        rs.getInt("postulante_dni"),
                        rs.getInt("producto_id"),
                        rs.getDouble("monto"),
                        rs.getString("fecha_creacion")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return lista;
    }
}
