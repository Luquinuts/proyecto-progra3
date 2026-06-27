/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "Postulante")
@NamedQueries({
    @NamedQuery(name = "Postulante.findAll", query = "SELECT p FROM Postulante p"),
    @NamedQuery(name = "Postulante.findByDni", query = "SELECT p FROM Postulante p WHERE p.dni = :dni"),
    @NamedQuery(name = "Postulante.findByNombre", query = "SELECT p FROM Postulante p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Postulante.findByApellido", query = "SELECT p FROM Postulante p WHERE p.apellido = :apellido"),
    @NamedQuery(name = "Postulante.findByCorreoElectronico", query = "SELECT p FROM Postulante p WHERE p.correoElectronico = :correoElectronico")})
public class Postulante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dni")
    private Integer dni;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "correoElectronico")
    private String correoElectronico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dniPostulante")
    private Collection<Oferta> ofertaCollection;

    public Postulante() {
    }

    public Postulante(Integer dni) {
        this.dni = dni;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
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
        hash += (dni != null ? dni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Postulante)) {
            return false;
        }
        Postulante other = (Postulante) object;
        if ((this.dni == null && other.dni != null) || (this.dni != null && !this.dni.equals(other.dni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido() + " " + getDni();
    }
    
    public static List<Postulante> obtenerTodos(){
        DBConnection db = new DBConnection();
        
        ArrayList<Postulante> postulantes = new ArrayList();
        
        try {
            ResultSet response = db.query("SELECT * FROM Postulante");
            
            while(response.next()){
                Postulante postulante = new Postulante();
                postulante.setDni(response.getInt("dni"));
                postulante.setNombre(response.getString("nombre"));
                postulante.setApellido(response.getString("apellido"));
                postulante.setCorreoElectronico(response.getString("correoElectronico"));
                
                postulantes.add(postulante);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            db.closeConnection();
        }
        
        return postulantes;
    }
    
    public static Postulante obtenerPorDni(int dni){
        DBConnection db = new DBConnection();
        
        Postulante postulante = new Postulante();
        
        try {
            ResultSet response = db.query("SELECT * FROM Postulante where dni = " + dni);
            
            while(response.next()){
                postulante.setDni(response.getInt("dni"));
                postulante.setNombre(response.getString("nombre"));
                postulante.setApellido(response.getString("apellido"));
                postulante.setCorreoElectronico(response.getString("correoElectronico"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        finally{
            db.closeConnection();
        }
        
        return postulante;
    }
}
