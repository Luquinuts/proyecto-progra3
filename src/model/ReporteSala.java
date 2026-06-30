package model;

public class ReporteSala {

    private int idSala;
    private String nombre;
    private int cantidadFunciones;
    private int entradasVendidas;
    private double recaudacionTotal;

    public ReporteSala() {
    }

    public ReporteSala(int idSala, String nombre, int cantidadFunciones, int entradasVendidas, double recaudacionTotal) {
        this.idSala = idSala;
        this.nombre = nombre;
        this.cantidadFunciones = cantidadFunciones;
        this.entradasVendidas = entradasVendidas;
        this.recaudacionTotal = recaudacionTotal;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadFunciones() {
        return cantidadFunciones;
    }

    public void setCantidadFunciones(int cantidadFunciones) {
        this.cantidadFunciones = cantidadFunciones;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    public double getRecaudacionTotal() {
        return recaudacionTotal;
    }

    public void setRecaudacionTotal(double recaudacionTotal) {
        this.recaudacionTotal = recaudacionTotal;
    }
}
