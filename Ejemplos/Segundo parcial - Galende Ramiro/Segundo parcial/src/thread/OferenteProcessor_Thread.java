/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thread;

import database.Oferta;
import database.Postulante;
import database.Producto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ramir
 */
public class OferenteProcessor_Thread extends Thread {

    Oferta dataAccess;
    Producto productoAOfertar;
    Postulante postulante;

    public OferenteProcessor_Thread(Oferta dataAccess, Producto productoAOfertar, Postulante postulante) {
        this.dataAccess = dataAccess;
        this.productoAOfertar = productoAOfertar;
        this.postulante = postulante;
    }
    
    
    
    @Override
    public void run() {
        
        try {
            Thread.sleep((long) (Math.random() * 3000));
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        
        BigDecimal montoAOfertar = new BigDecimal(( 1 + Math.random()) * productoAOfertar.getPrecioBase().doubleValue() );
        
        System.out.println(postulante + " ofertando " + montoAOfertar);
        
        Oferta oferta = new Oferta();
        
        oferta.setDniPostulante(postulante);
        oferta.setIdProducto(productoAOfertar);
        oferta.setMonto(montoAOfertar);
        oferta.setFechaCreacion(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        oferta.setEstado(dataAccess.validaMontoSincronizado(montoAOfertar, productoAOfertar.getId()));
        dataAccess.registrarOfertaSincronizada(oferta);
        
    }
    
    
    
}
