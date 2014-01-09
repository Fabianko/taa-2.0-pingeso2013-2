/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a"),
    @NamedQuery(name = "Attendance.findByCourseCode", query = "SELECT a FROM Attendance a WHERE a.attendancePK.courseCode = :courseCode"),
    @NamedQuery(name = "Attendance.findAllDatesByCourseCode", query = "SELECT DISTINCT a.attendancePK.attendanceDate FROM Attendance a WHERE a.attendancePK.courseCode = :courseCode"),
    @NamedQuery(name = "Attendance.findByRut", query = "SELECT a FROM Attendance a WHERE a.attendancePK.rut = :rut"),
     @NamedQuery(name = "Attendance.findByCourseCodeDate", query = "SELECT a FROM Attendance a WHERE a.attendancePK.attendanceDate = :attendanceDate AND a.attendancePK.courseCode = :courseCode"),
    @NamedQuery(name = "Attendance.findByAttendanceDate", query = "SELECT a FROM Attendance a WHERE a.attendancePK.attendanceDate = :attendanceDate"),
    @NamedQuery(name = "Attendance.findByPresent", query = "SELECT a FROM Attendance a WHERE a.present = :present"),
    @NamedQuery(name = "Attendance.findByAttendanceState", query = "SELECT a FROM Attendance a WHERE a.attendanceState = :attendanceState"),
    @NamedQuery(name = "Attendance.removeAll", query = "DELETE FROM Attendance a WHERE a.attendancePK.attendanceDate = :attendanceDate AND a.attendancePK.courseCode = :courseCode")})
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AttendancePK attendancePK;
    @Size(max = 1)
    @Column(name = "PRESENT")
    private String present;
    @Size(max = 1)
    @Column(name = "ATTENDANCE_STATE")
    private String attendanceState;
    @JoinColumn(name = "COURSE_CODE", referencedColumnName = "COURSE_CODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Course course;
    @JoinColumn(name = "RUT", referencedColumnName = "RUT", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;

    public Attendance() {
    }

    public Attendance(AttendancePK attendancePK) {
        this.attendancePK = attendancePK;
    }

    public Attendance(String courseCode, String rut, Date attendanceDate) {
        this.attendancePK = new AttendancePK(courseCode, rut, attendanceDate);
    }

    public AttendancePK getAttendancePK() {
        return attendancePK;
    }

    public void setAttendancePK(AttendancePK attendancePK) {
        this.attendancePK = attendancePK;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getAttendanceState() {
        return attendanceState;
    }

    public void setAttendanceState(String attendanceState) {
        this.attendanceState = attendanceState;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendancePK != null ? attendancePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        if ((this.attendancePK == null && other.attendancePK != null) || (this.attendancePK != null && !this.attendancePK.equals(other.attendancePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Attendance[ attendancePK=" + attendancePK + " ]";
    }
    
}
