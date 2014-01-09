/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nico
 */
@Embeddable
public class AssignmentPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "COURSE_CODE")
    private String courseCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RUT")
    private String rut;

    public AssignmentPK() {
    }

    public AssignmentPK(String courseCode, String rut) {
        this.courseCode = courseCode;
        this.rut = rut;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseCode != null ? courseCode.hashCode() : 0);
        hash += (rut != null ? rut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssignmentPK)) {
            return false;
        }
        AssignmentPK other = (AssignmentPK) object;
        if ((this.courseCode == null && other.courseCode != null) || (this.courseCode != null && !this.courseCode.equals(other.courseCode))) {
            return false;
        }
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.AssignmentPK[ courseCode=" + courseCode + ", rut=" + rut + " ]";
    }
    
}
