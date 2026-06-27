/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package atlantida.palavecinoexamen1;

/**
 *
 * @author lpalavecino
 */

public class PalavecinoExamen1 {

    public static void main(String[] args) {        
        
        /* ENVIO 1 ------------- */
        
        MercadoPago pago = new MercadoPago();
        Cliente cliente1 = new Cliente(1, "Juan", pago);
        
        EnvioExpress envio1 = new EnvioExpress(1, 6000, "Calle 1234", cliente1, "pendiente");
        IO.println("El estado del pedido es: " + envio1.obtenerEstado());
        
        cliente1.PagarEnvio(envio1);
        envio1.setCliente(cliente1);
        
        envio1.actualizarEstado("en viaje");
        IO.println("El estado del pedido fue modificado a " + envio1.obtenerEstado());
        
        IO.println(envio1.toString());
        
        /* ENVIO 2 ------------- */
        
        TransferenciaBancaria pago2 = new TransferenciaBancaria();
        Cliente cliente2 = new Cliente(2, "Esteban", pago2);
        
        EnvioInternacional envio2 = new EnvioInternacional(2, 200, "Calle 4567", cliente2, "pendiente");
        IO.println("El estado del pedido es: " + envio2.obtenerEstado());
        
        cliente2.PagarEnvio(envio2);
        envio2.setCliente(cliente2);
        
        envio2.actualizarEstado("en viaje");
        IO.println("El estado del pedido fue modificado a " + envio2.obtenerEstado());
        
        IO.println(envio2.toString());
        
        
        /* ENVIO 3 ------------- */
        
        TarjetaCredito pago3 = new TarjetaCredito();
        Cliente cliente3 = new Cliente(3, "Franco", pago3);
        
        EnvioNacional envio3 = new EnvioNacional(3, 10000, "Calle 8910", cliente3, "pendiente");
        IO.println("El estado del pedido es: " + envio3.obtenerEstado());
        
        cliente3.PagarEnvio(envio3);
        envio3.setCliente(cliente3);
        
        envio3.actualizarEstado("en viaje");
        IO.println("El estado del pedido fue modificado a " + envio3.obtenerEstado());
        
        IO.println(envio3.toString());
        
    }
}


