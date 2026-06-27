package palavecinoexamen2;

import java.util.ArrayList;

public class Oferente_Prosessor_Thread extends Thread {

    Postulante postulante;
    Producto producto;
    double nuevo_monto;

    public Oferente_Prosessor_Thread(Postulante postulante, Producto producto, double nuevo_monto) {
        this.postulante = postulante;
        this.producto = producto;
        this.nuevo_monto = nuevo_monto;
    }

    @Override
    public void run() {
        synchronized (Oferente_Prosessor_Thread.class) {
            double ultimoMonto = 0;
            ArrayList<Oferta> ofertas = Oferta.obtener_ofertas_por_producto(producto.getId_producto());
            if (!ofertas.isEmpty()) {
                ultimoMonto = ofertas.get(0).getMonto();
            }

            if (valida_monto_sincronizado(ultimoMonto)) {
                Oferta oferta = new Oferta(0, postulante.getDni(), producto.getId_producto(),
                        nuevo_monto, null);
                oferta.insertar_oferta();
                System.out.println("Oferta registrada: $" + nuevo_monto
                        + " por " + postulante.getNombre() + " en " + producto.getNombre());
            } else {
                System.out.println("Oferta rechazada: $" + nuevo_monto
                        + " es menor o igual al monto actual de $" + ultimoMonto);
            }
        }
    }

    public boolean valida_monto_sincronizado(double ultimoMonto) {
        return nuevo_monto > ultimoMonto;
    }
}
