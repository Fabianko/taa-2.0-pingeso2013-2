/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "coordinator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordinator.findAll", query = "SELECT c FROM Coordinator c"),
    @NamedQuery(name = "Coordinator.findByRut", query = "SELECT c FROM Coordinator c WHERE c.rut = :rut")})
public class Coordinator implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RUT")
    private String rut;
    @JoinColumn(name = "RUT_UNIVERSITY", referencedColumnName = "RUT_UNIVERSITY")
    @ManyToOne
    private University rutUniversity;
    @JoinColumn(name = "RUT", referencedColumnName = "RUT", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public Coordinator() {
    }

    public Coordinator(String rut) {
        this.rut = rut;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public University getRutUniversity() {
        return rutUniversity;
    }

    public void setRutUniversity(University rutUniversity) {
        this.rutUniversity = rutUniversity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rut != null ? rut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coordinator)) {
            return false;
        }
        Coordinator other = (Coordinator) object;
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Coordinator[ rut=" + rut + " ]";
    }
    
}
