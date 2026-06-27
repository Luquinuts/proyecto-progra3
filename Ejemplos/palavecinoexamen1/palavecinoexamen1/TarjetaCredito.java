/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atlantida.palavecinoexamen1;

/**
 *
 * @author lpalavecino
 */
public class TarjetaCredito implements MetodoPago{

    public TarjetaCredito() {
    }

    @Override
    public boolean procesarPago(double dato) {
        IO.println("procesando pago");
        IO.println("pago procesado");
        return true;    
    }
    
    @Override
    public String tipoPago() {
        return "Tarjeta de Credito";
    }
}
