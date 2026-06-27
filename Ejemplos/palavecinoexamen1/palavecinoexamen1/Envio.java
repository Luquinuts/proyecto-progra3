/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atlantida.palavecinoexamen1;

/**
 *
 * @author lpalavecino
 */


public abstract class Envio implements Rasteable {
    private int id;
    private double peso;
    private String direccionDestino;
    private Cliente cliente;
    private String estado;
    
    public Envio(int id, double peso, String direccionDestino, Cliente cliente, String estado) {
        this.id = id;
        this.peso = peso;
        this.direccionDestino = direccionDestino;
        this.cliente = cliente;
        this.estado = estado;
    }
    
    abstract double calcularCosto();
    
    abstract double calcularTiempoEntrega();
    
    abstract String tipoPedido();
    
    @Override
    public String toString() {
        return  "Detalle del envio{ " +
                "\n Tipo de envio: " + this.tipoPedido() + 
                "\n Total de costos: " + this.calcularCosto() +
                "\n Estado actual: " + this.estado +
                "\n Metodo de pago: " + this.cliente.getTipoPago() +
                "\n }";
    }


    @Override
    public void actualizarEstado(String dato) {
        this.estado = dato;
    }

    @Override
    public String obtenerEstado() {
        return this.estado;
    }

    public double getPeso() {
        return peso;
    }

    public int getId() {
        return id;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    
}
