package palavecinoexamen2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Postulante {

    String nombre;
    String apellido;
    int dni;
    String correo_electronico;

    public Postulante(String nombre, String apellido, int dni, String correo_electronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.correo_electronico = correo_electronico;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getDni() {
        return dni;
    }

    public void insertar_postulante() {
        Conexion c = new Conexion("localhost", "comercio", "root", "");
        String consulta = "INSERT INTO postulantes (dni, nombre, apellido, correo_electronico) VALUES ("
                + dni + ",'" + nombre + "','" + apellido + "','" + correo_electronico + "')";
        c.insertar(consulta);
    }

    public static ArrayList<Postulante> obtener_postulantes() {
        ArrayList<Postulante> lista = new ArrayList<>();
        Conexion c = new Conexion("localhost", "comercio", "root", "");
        try {
            ResultSet rs = c.consultar("SELECT dni, nombre, apellido, correo_electronico FROM postulantes");
            while (rs.next()) {
                lista.add(new Postulante(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("correo_electronico")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return lista;
    }
}
