/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atlantida.palavecinoexamen1;

/**
 *
 * @author lpalavecino
 */
public class MercadoPago implements MetodoPago{

    public MercadoPago() {
    }
    
    @Override
    public boolean procesarPago(double dato) {
        IO.println("usted pagara: " + dato);
        IO.println("procesando pago");
        IO.println("pago procesado");
        return true;
    }

    @Override
    public String tipoPago() {
        return "Mercado Pago";
    }
}
