/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nico
 */
@Entity
@Table(name = "assignment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assignment.findAll", query = "SELECT a FROM Assignment a"),
    @NamedQuery(name = "Assignment.findByCourseCode", query = "SELECT a FROM Assignment a WHERE a.assignmentPK.courseCode = :courseCode"),
    @NamedQuery(name = "Assignment.findByRut", query = "SELECT a FROM Assignment a WHERE a.assignmentPK.rut = :rut"),
    @NamedQuery(name = "Assignment.findByAssignmentDate", query = "SELECT a FROM Assignment a WHERE a.assignmentDate = :assignmentDate"),
    @NamedQuery(name = "Assignment.findByAssignmentState", query = "SELECT a FROM Assignment a WHERE a.assignmentState = :assignmentState")})
public class Assignment implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AssignmentPK assignmentPK;
    @Basic(optional = false)
    @Column(name = "ASSIGNMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignmentDate;
    @Size(max = 1)
    @Column(name = "ASSIGNMENT_STATE")
    private String assignmentState;
    @JoinColumn(name = "RUT", referencedColumnName = "RUT", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Teacher teacher;
    @JoinColumn(name = "COURSE_CODE", referencedColumnName = "COURSE_CODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Course course;

    public Assignment() {
    }

    public Assignment(AssignmentPK assignmentPK) {
        this.assignmentPK = assignmentPK;
    }

    public Assignment(AssignmentPK assignmentPK, Date assignmentDate) {
        this.assignmentPK = assignmentPK;
        this.assignmentDate = assignmentDate;
    }

    public Assignment(String courseCode, String rut) {
        this.assignmentPK = new AssignmentPK(courseCode, rut);
    }

    public AssignmentPK getAssignmentPK() {
        return assignmentPK;
    }

    public void setAssignmentPK(AssignmentPK assignmentPK) {
        this.assignmentPK = assignmentPK;
    }

    public Date getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(Date assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getAssignmentState() {
        return assignmentState;
    }

    public void setAssignmentState(String assignmentState) {
        this.assignmentState = assignmentState;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assignmentPK != null ? assignmentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assignment)) {
            return false;
        }
        Assignment other = (Assignment) object;
        if ((this.assignmentPK == null && other.assignmentPK != null) || (this.assignmentPK != null && !this.assignmentPK.equals(other.assignmentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Assignment[ assignmentPK=" + assignmentPK + " ]";
    }
    
}
