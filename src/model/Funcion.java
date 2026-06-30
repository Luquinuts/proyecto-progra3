package model;

public class Funcion {

    private int idFuncion;
    private int idPelicula;
    private int idSala;
    private String fecha;
    private String hora;
    private double precio;
    private int activa = 1;

    public Funcion() {
    }

    public Funcion(int idFuncion, int idPelicula, int idSala, String fecha, String hora, double precio) {
        this.idFuncion = idFuncion;
        this.idPelicula = idPelicula;
        this.idSala = idSala;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.activa = 1;
    }

    public int getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getActiva() {
        return activa;
    }

    public void setActiva(int activa) {
        this.activa = activa;
    }

    public boolean isActiva() {
        return activa == 1;
    }

    @Override
    public String toString() {
        return "Funcion{idFuncion=" + idFuncion
                + ", idPelicula=" + idPelicula
                + ", idSala=" + idSala
                + ", fecha='" + fecha + '\''
                + ", hora='" + hora + '\''
                + ", precio=" + precio + '}';
    }
}
