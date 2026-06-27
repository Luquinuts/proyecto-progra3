/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atlantida.palavecinoexamen1;

/**
 *
 * @author lpalavecino
 */
public class EnvioNacional extends Envio {

    public EnvioNacional(int id, double peso, String direccionDestino, Cliente cliente, String estado) {
        super(id, peso, direccionDestino, cliente, estado);
    }

    @Override
    double calcularCosto() {
        return this.getPeso() * 100;
    }

    @Override
    double calcularTiempoEntrega() {
        return 3;
    }
    
    @Override
    String tipoPedido() {
        return "Envio Nacional";
    }
}
