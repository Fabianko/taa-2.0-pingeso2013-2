/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "university")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "University.findAll", query = "SELECT u FROM University u"),
    @NamedQuery(name = "University.findByRutUniversity", query = "SELECT u FROM University u WHERE u.rutUniversity = :rutUniversity"),
    @NamedQuery(name = "University.findByUniversityName", query = "SELECT u FROM University u WHERE u.universityName = :universityName"),
    @NamedQuery(name = "University.findByUniversityHeadquarterAdress", query = "SELECT u FROM University u WHERE u.universityHeadquarterAdress = :universityHeadquarterAdress"),
    @NamedQuery(name = "University.findByUniversityHeadquarterPhone", query = "SELECT u FROM University u WHERE u.universityHeadquarterPhone = :universityHeadquarterPhone"),
    @NamedQuery(name = "University.findByUniversityHeadquarterEmail", query = "SELECT u FROM University u WHERE u.universityHeadquarterEmail = :universityHeadquarterEmail"),
    @NamedQuery(name = "University.findByUniversityState", query = "SELECT u FROM University u WHERE u.universityState = :universityState")})
public class University implements Serializable {
    @OneToMany(mappedBy = "rutUniversity")
    private Collection<Coordinator> coordinatorCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RUT_UNIVERSITY")
    private String rutUniversity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UNIVERSITY_NAME")
    private String universityName;
    @Size(max = 100)
    @Column(name = "UNIVERSITY_HEADQUARTER_ADRESS")
    private String universityHeadquarterAdress;
    @Size(max = 30)
    @Column(name = "UNIVERSITY_HEADQUARTER_PHONE")
    private String universityHeadquarterPhone;
    @Size(max = 100)
    @Column(name = "UNIVERSITY_HEADQUARTER_EMAIL")
    private String universityHeadquarterEmail;
    @Size(max = 1)
    @Column(name = "UNIVERSITY_STATE")
    private String universityState;
    @OneToMany(mappedBy = "rutUniversity")
    private Collection<School> schoolCollection;
    @OneToMany(mappedBy = "rutUniversity")
    private Collection<Student> studentCollection;

    public University() {
    }

    public University(String rutUniversity) {
        this.rutUniversity = rutUniversity;
    }

    public University(String rutUniversity, String universityName) {
        this.rutUniversity = rutUniversity;
        this.universityName = universityName;
    }

    public String getRutUniversity() {
        return rutUniversity;
    }

    public void setRutUniversity(String rutUniversity) {
        this.rutUniversity = rutUniversity;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityHeadquarterAdress() {
        return universityHeadquarterAdress;
    }

    public void setUniversityHeadquarterAdress(String universityHeadquarterAdress) {
        this.universityHeadquarterAdress = universityHeadquarterAdress;
    }

    public String getUniversityHeadquarterPhone() {
        return universityHeadquarterPhone;
    }

    public void setUniversityHeadquarterPhone(String universityHeadquarterPhone) {
        this.universityHeadquarterPhone = universityHeadquarterPhone;
    }

    public String getUniversityHeadquarterEmail() {
        return universityHeadquarterEmail;
    }

    public void setUniversityHeadquarterEmail(String universityHeadquarterEmail) {
        this.universityHeadquarterEmail = universityHeadquarterEmail;
    }

    public String getUniversityState() {
        return universityState;
    }

    public void setUniversityState(String universityState) {
        this.universityState = universityState;
    }

    @XmlTransient
    public Collection<School> getSchoolCollection() {
        return schoolCollection;
    }

    public void setSchoolCollection(Collection<School> schoolCollection) {
        this.schoolCollection = schoolCollection;
    }

    @XmlTransient
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rutUniversity != null ? rutUniversity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof University)) {
            return false;
        }
        University other = (University) object;
        if ((this.rutUniversity == null && other.rutUniversity != null) || (this.rutUniversity != null && !this.rutUniversity.equals(other.rutUniversity))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.University[ rutUniversity=" + rutUniversity + " ]";
    }

    @XmlTransient
    public Collection<Coordinator> getCoordinatorCollection() {
        return coordinatorCollection;
    }

    public void setCoordinatorCollection(Collection<Coordinator> coordinatorCollection) {
        this.coordinatorCollection = coordinatorCollection;
    }
    
}
