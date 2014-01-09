/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Nico
 */
@Embeddable
public class AttendancePK implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "ATTENDANCE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date attendanceDate;

    public AttendancePK() {
    }

    public AttendancePK(String courseCode, String rut, Date attendanceDate) {
        this.courseCode = courseCode;
        this.rut = rut;
        this.attendanceDate = attendanceDate;
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

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseCode != null ? courseCode.hashCode() : 0);
        hash += (rut != null ? rut.hashCode() : 0);
        hash += (attendanceDate != null ? attendanceDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttendancePK)) {
            return false;
        }
        AttendancePK other = (AttendancePK) object;
        if ((this.courseCode == null && other.courseCode != null) || (this.courseCode != null && !this.courseCode.equals(other.courseCode))) {
            return false;
        }
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        if ((this.attendanceDate == null && other.attendanceDate != null) || (this.attendanceDate != null && !this.attendanceDate.equals(other.attendanceDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.AttendancePK[ courseCode=" + courseCode + ", rut=" + rut + ", attendanceDate=" + attendanceDate + " ]";
    }
    
}
