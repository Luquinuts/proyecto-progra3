/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ramir
 */
@Entity
@Table(name = "Oferta")
@NamedQueries({
    @NamedQuery(name = "Oferta.findAll", query = "SELECT o FROM Oferta o"),
    @NamedQuery(name = "Oferta.findById", query = "SELECT o FROM Oferta o WHERE o.id = :id"),
    @NamedQuery(name = "Oferta.findByFechaCreacion", query = "SELECT o FROM Oferta o WHERE o.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Oferta.findByMonto", query = "SELECT o FROM Oferta o WHERE o.monto = :monto")})
public class Oferta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fechaCreacion")
    private String fechaCreacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private BigDecimal monto;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "dniPostulante", referencedColumnName = "dni")
    @ManyToOne(optional = false)
    private Postulante dniPostulante;
    @JoinColumn(name = "idProducto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public Oferta() {
    }

    public Oferta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

    public Postulante getDniPostulante() {
        return dniPostulante;
    }

    public void setDniPostulante(Postulante dniPostulante) {
        this.dniPostulante = dniPostulante;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oferta)) {
            return false;
        }
        Oferta other = (Oferta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Oferta[ id=" + id + " ]";
    }
    
    public synchronized void registrarOfertaSincronizada(Oferta oferta){
        DBConnection db = new DBConnection();
        
        try {
            db.insert("INSERT INTO `Oferta` (`dniPostulante`, `idProducto`, `fechaCreacion`, `monto`, estado) VALUES ('"
                    + oferta.getDniPostulante().getDni() +     "', '"
                    + oferta.getIdProducto().getId() +     "', '"
                    + oferta.getFechaCreacion() +     "', '"
                    + oferta.getMonto() +     "', '"
                    + oferta.getEstado() +  "')");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        finally {
            db.closeConnection();
        }   
    }
    
    public static List<Oferta> obtenerTodos(){
        DBConnection db = new DBConnection();
        
        ArrayList<Oferta> ofertas = new ArrayList();
        
        try {
            ResultSet response = db.query("SELECT * FROM Oferta");
            
            while(response.next()){
                Oferta oferta = new Oferta();
                oferta.setId(response.getInt("id"));
                oferta.setDniPostulante(
                        Postulante.obtenerPorDni(response.getInt("dniPostulante"))
                );
                oferta.setIdProducto(
                        Producto.obtenerPorId(response.getInt("idProducto"))
                );
                oferta.setFechaCreacion(response.getString("fechaCreacion"));
                oferta.setMonto(response.getBigDecimal("monto"));
                oferta.setEstado(response.getString("estado"));
                
                ofertas.add(oferta);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            db.closeConnection();
        }
        
        return ofertas;
    }
    
    public synchronized String validaMontoSincronizado(BigDecimal monto, int idProducto){
        DBConnection db = new DBConnection();
        String out = "error";
        
        try {
            ResultSet response = db.query("SELECT MAX(monto) monto FROM Oferta where idProducto = " + idProducto);
            BigDecimal montoMax = new BigDecimal(0);
            while(response.next()){
                montoMax = response.getBigDecimal("monto");
            }
            
            montoMax = montoMax == null ? BigDecimal.ZERO : montoMax;
            
            out = monto.doubleValue() > montoMax.doubleValue() ? "EXITO" : "RECHAZADO";
            
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            db.closeConnection();
        }
        
        return out;
    }
    
}
