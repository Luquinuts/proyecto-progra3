/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atlantida.palavecinoexamen1;

/**
 *
 * @author lpalavecino
 */
public class EnvioInternacional extends Envio{

    public EnvioInternacional(int id, double peso, String direccionDestino, Cliente cliente, String estado) {
        super(id, peso, direccionDestino, cliente, estado);
    }

    @Override
    double calcularCosto() {
        return this.getPeso() * 300 + 5000;
    }

    @Override
    double calcularTiempoEntrega() {
        return 10;
    }
    
    
    @Override
    String tipoPedido() {
        return "Envio Internacional";
    }   
}
