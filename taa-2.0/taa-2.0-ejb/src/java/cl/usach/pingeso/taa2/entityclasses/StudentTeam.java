/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "student_team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentTeam.findAll", query = "SELECT s FROM StudentTeam s"),
    @NamedQuery(name = "StudentTeam.findByTeamId", query = "SELECT s FROM StudentTeam s WHERE s.studentTeamPK.teamId = :teamId"),
    @NamedQuery(name = "StudentTeam.findByRut", query = "SELECT s FROM StudentTeam s WHERE s.studentTeamPK.rut = :rut"),
    @NamedQuery(name = "StudentTeam.findByRolStudent", query = "SELECT s FROM StudentTeam s WHERE s.rolStudent = :rolStudent")})
public class StudentTeam implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StudentTeamPK studentTeamPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ROL_STUDENT")
    private String rolStudent;
    @JoinColumn(name = "RUT", referencedColumnName = "RUT", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "TEAM_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Team team;

    public StudentTeam() {
    }

    public StudentTeam(StudentTeamPK studentTeamPK) {
        this.studentTeamPK = studentTeamPK;
    }

    public StudentTeam(StudentTeamPK studentTeamPK, String rolStudent) {
        this.studentTeamPK = studentTeamPK;
        this.rolStudent = rolStudent;
    }

    public StudentTeam(long teamId, String rut) {
        this.studentTeamPK = new StudentTeamPK(teamId, rut);
    }

    public StudentTeamPK getStudentTeamPK() {
        return studentTeamPK;
    }

    public void setStudentTeamPK(StudentTeamPK studentTeamPK) {
        this.studentTeamPK = studentTeamPK;
    }

    public String getRolStudent() {
        return rolStudent;
    }

    public void setRolStudent(String rolStudent) {
        this.rolStudent = rolStudent;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentTeamPK != null ? studentTeamPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentTeam)) {
            return false;
        }
        StudentTeam other = (StudentTeam) object;
        if ((this.studentTeamPK == null && other.studentTeamPK != null) || (this.studentTeamPK != null && !this.studentTeamPK.equals(other.studentTeamPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.StudentTeam[ studentTeamPK=" + studentTeamPK + " ]";
    }
    
}
