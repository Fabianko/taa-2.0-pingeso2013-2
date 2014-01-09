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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "membership")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Membership.findAll", query = "SELECT m FROM Membership m"),
    @NamedQuery(name = "Membership.findByCourseCode", query = "SELECT m FROM Membership m WHERE m.membershipPK.courseCode = :courseCode"),
    @NamedQuery(name = "Membership.findByMembershipDate", query = "SELECT m FROM Membership m WHERE m.membershipDate = :membershipDate"),
    @NamedQuery(name = "Membership.findByMembershipState", query = "SELECT m FROM Membership m WHERE m.membershipState = :membershipState"),
    @NamedQuery(name = "Membership.findByRut", query = "SELECT m FROM Membership m WHERE m.membershipPK.rut = :rut"),
    @NamedQuery(name = "Membership.findStudentByCourseCode", query = "SELECT m FROM Membership m WHERE m.membershipPK.courseCode = :courseCode AND m.membershipPK.rut = :rut")})

public class Membership implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MembershipPK membershipPK;
    @Column(name = "MEMBERSHIP_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date membershipDate;
    @Size(max = 255)
    @Column(name = "MEMBERSHIP_STATE")
    private String membershipState;
    @JoinColumn(name = "RUT", referencedColumnName = "RUT", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;
    @JoinColumn(name = "COURSE_CODE", referencedColumnName = "COURSE_CODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Course course;

    public Membership() {
    }

    public Membership(MembershipPK membershipPK) {
        this.membershipPK = membershipPK;
    }

    public Membership(String courseCode, String rut) {
        this.membershipPK = new MembershipPK(courseCode, rut);
    }

    public MembershipPK getMembershipPK() {
        return membershipPK;
    }

    public void setMembershipPK(MembershipPK membershipPK) {
        this.membershipPK = membershipPK;
    }

    public Date getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(Date membershipDate) {
        this.membershipDate = membershipDate;
    }

    public String getMembershipState() {
        return membershipState;
    }

    public void setMembershipState(String membershipState) {
        this.membershipState = membershipState;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
        hash += (membershipPK != null ? membershipPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Membership)) {
            return false;
        }
        Membership other = (Membership) object;
        if ((this.membershipPK == null && other.membershipPK != null) || (this.membershipPK != null && !this.membershipPK.equals(other.membershipPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Membership[ membershipPK=" + membershipPK + " ]";
    }
    
}
