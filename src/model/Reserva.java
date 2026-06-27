package model;

public class Reserva {

    private int idReserva;
    private int idCliente;
    private String fechaReserva;

    public Reserva() {
    }

    public Reserva(int idReserva, int idCliente, String fechaReserva) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.fechaReserva = fechaReserva;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    @Override
    public String toString() {
        return "Reserva{idReserva=" + idReserva
                + ", idCliente=" + idCliente
                + ", fechaReserva='" + fechaReserva + '\'' + '}';
    }
}
