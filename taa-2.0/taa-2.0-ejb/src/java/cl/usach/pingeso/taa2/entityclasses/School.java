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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "school")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "School.findAll", query = "SELECT s FROM School s"),
    @NamedQuery(name = "School.findBySchoolCode", query = "SELECT s FROM School s WHERE s.schoolCode = :schoolCode"),
    @NamedQuery(name = "School.findBySchoolName", query = "SELECT s FROM School s WHERE s.schoolName = :schoolName"),
    @NamedQuery(name = "School.findBySchoolAdress", query = "SELECT s FROM School s WHERE s.schoolAdress = :schoolAdress"),
    @NamedQuery(name = "School.findBySchoolPhone", query = "SELECT s FROM School s WHERE s.schoolPhone = :schoolPhone"),
    @NamedQuery(name = "School.findBySchoolEmail", query = "SELECT s FROM School s WHERE s.schoolEmail = :schoolEmail"),
    @NamedQuery(name = "School.findBySchoolState", query = "SELECT s FROM School s WHERE s.schoolState = :schoolState")})

public class School implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SCHOOL_CODE")
    private String schoolCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SCHOOL_NAME")
    private String schoolName;
    @Size(max = 100)
    @Column(name = "SCHOOL_ADRESS")
    private String schoolAdress;
    @Size(max = 30)
    @Column(name = "SCHOOL_PHONE")
    private String schoolPhone;
    @Size(max = 100)
    @Column(name = "SCHOOL_EMAIL")
    private String schoolEmail;
    @Size(max = 1)
    @Column(name = "SCHOOL_STATE")
    private String schoolState;
    @JoinColumn(name = "RUT_UNIVERSITY", referencedColumnName = "RUT_UNIVERSITY")
    @ManyToOne
    private University rutUniversity;
    @JoinColumn(name = "TIMETABLE_CODE", referencedColumnName = "TIMETABLE_CODE")
    @ManyToOne
    private Timetable timetableCode;
    @OneToMany(mappedBy = "schoolCode")
    private Collection<Program> programCollection;

    public School() {
    }

    public School(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public School(String schoolCode, String schoolName) {
        this.schoolCode = schoolCode;
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAdress() {
        return schoolAdress;
    }

    public void setSchoolAdress(String schoolAdress) {
        this.schoolAdress = schoolAdress;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getSchoolState() {
        return schoolState;
    }

    public void setSchoolState(String schoolState) {
        this.schoolState = schoolState;
    }

    public University getRutUniversity() {
        return rutUniversity;
    }

    public void setRutUniversity(University rutUniversity) {
        this.rutUniversity = rutUniversity;
    }

    public Timetable getTimetableCode() {
        return timetableCode;
    }

    public void setTimetableCode(Timetable timetableCode) {
        this.timetableCode = timetableCode;
    }

    @XmlTransient
    public Collection<Program> getProgramCollection() {
        return programCollection;
    }

    public void setProgramCollection(Collection<Program> programCollection) {
        this.programCollection = programCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolCode != null ? schoolCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof School)) {
            return false;
        }
        School other = (School) object;
        if ((this.schoolCode == null && other.schoolCode != null) || (this.schoolCode != null && !this.schoolCode.equals(other.schoolCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.School[ schoolCode=" + schoolCode + " ]";
    }
    
}
