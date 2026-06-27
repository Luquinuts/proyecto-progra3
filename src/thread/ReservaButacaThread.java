package thread;

import database.ReservaDAO;
import model.Cliente;
import model.Funcion;
import java.util.List;

public class ReservaButacaThread extends Thread {

    private Cliente cliente;
    private Funcion funcion;
    private List<Integer> idsButacas;
    private boolean exito = false;
    private String mensaje = "";

    public ReservaButacaThread(Cliente cliente, Funcion funcion, List<Integer> idsButacas) {
        this.cliente = cliente;
        this.funcion = funcion;
        this.idsButacas = idsButacas;
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public void run() {
        synchronized (ReservaButacaThread.class) {
            try {
                exito = ReservaDAO.reservarButacas(cliente, funcion, idsButacas);
                if (exito) {
                    mensaje = "Reserva exitosa! Butacas: " + idsButacas;
                } else {
                    mensaje = "Alguna butaca ya fue reservada por otro usuario.";
                }
            } catch (Exception e) {
                mensaje = "Error en la reserva: " + e.getMessage();
                exito = false;
            }
        }
    }
}
