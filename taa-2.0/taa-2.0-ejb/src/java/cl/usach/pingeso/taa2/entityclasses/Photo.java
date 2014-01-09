/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nico
 */
@Entity
@Table(name = "photo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Photo.findAll", query = "SELECT p FROM Photo p"),
    @NamedQuery(name = "Photo.findByPhotoId", query = "SELECT p FROM Photo p WHERE p.photoId = :photoId"),
    @NamedQuery(name = "Photo.findByPhotoPath", query = "SELECT p FROM Photo p WHERE p.photoPath = :photoPath"),
    @NamedQuery(name = "Photo.findByRut", query = "SELECT p FROM Photo p WHERE p.rut = :rut"),
    @NamedQuery(name = "Photo.findByPhotoState", query = "SELECT p FROM Photo p WHERE p.photoState = :photoState")})
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PHOTO_ID")
    private Long photoId;
    @Size(max = 500)
    @Column(name = "PHOTO_PATH")
    private String photoPath;
    @Size(max = 1)
    @Column(name = "PHOTO_STATE")
    private String photoState;
    @JoinColumn(name = "RUT", referencedColumnName = "RUT")
    @ManyToOne
    private Student rut;

    public Photo() {
    }

    public Photo(Long photoId) {
        this.photoId = photoId;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoState() {
        return photoState;
    }

    public void setPhotoState(String photoState) {
        this.photoState = photoState;
    }

    public Student getRut() {
        return rut;
    }

    public void setRut(Student rut) {
        this.rut = rut;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (photoId != null ? photoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) object;
        if ((this.photoId == null && other.photoId != null) || (this.photoId != null && !this.photoId.equals(other.photoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Photo[ photoId=" + photoId + " ]";
    }
    
}
