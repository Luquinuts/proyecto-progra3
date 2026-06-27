/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atlantida.palavecinoexamen1;

/**
 *
 * @author lpalavecino
 */
public class Cliente{
   private int id;
   private String nombre;
   private MetodoPago metodopago;
   
   public Cliente(){
       
   }
   
   public Cliente(int id, String nombre, MetodoPago metodopago) {
        this.id = id;
        this.nombre = nombre;
        this.metodopago = metodopago;
    }
   
   public boolean PagarEnvio(Envio envio){
       return this.metodopago.procesarPago(envio.calcularCosto()); 
   }

    public String getTipoPago() {
        return metodopago.tipoPago();
    }
   
}