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
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ramir
 */
@Entity
@Table(name = "Producto")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findById", query = "SELECT p FROM Producto p WHERE p.id = :id"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByPrecioBase", query = "SELECT p FROM Producto p WHERE p.precioBase = :precioBase")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precioBase")
    private BigDecimal precioBase;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
    private Collection<Oferta> ofertaCollection;

    public Producto() {
    }

    public Producto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public Collection<Oferta> getOfertaCollection() {
        return ofertaCollection;
    }

    public void setOfertaCollection(Collection<Oferta> ofertaCollection) {
        this.ofertaCollection = ofertaCollection;
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
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre() + " $" + getPrecioBase();
    }
    
    public static List<Producto> obtenerTodos(){
        DBConnection db = new DBConnection();
        
        ArrayList<Producto> productos = new ArrayList();
        
        try {
            ResultSet response = db.query("SELECT * FROM Producto");
            
            while(response.next()){
                Producto producto = new Producto();
                producto.setId(response.getInt("id"));
                producto.setNombre(response.getString("nombre"));
                producto.setPrecioBase(response.getBigDecimal("precioBase"));
                
                productos.add(producto);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            db.closeConnection();
        }
        
        return productos;
    }
    
    public static Producto obtenerPorId(int id){
        DBConnection db = new DBConnection();
        
        Producto producto = new Producto();
        
        try {
            ResultSet response = db.query("SELECT * FROM Producto where id = " + id);
            
            while(response.next()){
                producto.setId(response.getInt("id"));
                producto.setNombre(response.getString("nombre"));
                producto.setPrecioBase(response.getBigDecimal("precioBase"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        finally{
            db.closeConnection();
        }
        
        return producto;
    }
    
    public static void insertar(Producto producto){
        DBConnection db = new DBConnection();
        
        try {
            db.insert("INSERT INTO `Producto` (`nombre`, `precioBase`) VALUES ('"
                    + producto.getNombre() +     "', '"
                    + producto.getPrecioBase() +  "')");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        finally {
            db.closeConnection();
        }
    }
    

}
