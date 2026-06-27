/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atlantida.palavecinoexamen1;

/**
 *
 * @author lpalavecino
 */
public class EnvioExpress extends Envio{

    public EnvioExpress(int id, double peso, String direccionDestino, Cliente cliente, String estado) {
        super(id, peso, direccionDestino, cliente, estado);
    }
    
    
    @Override
    double calcularCosto() {
        return this.getPeso() * 200 + 2000;
    }
    
    @Override
    double calcularTiempoEntrega() {
        return 1;
    }

    @Override
    String tipoPedido() {
        return "Envio Express";
    }
}
