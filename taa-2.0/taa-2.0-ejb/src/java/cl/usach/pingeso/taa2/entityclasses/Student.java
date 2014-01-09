/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findByRut", query = "SELECT s FROM Student s WHERE s.rut = :rut")})
public class Student implements Serializable {
    @Size(max = 30)
    @Column(name = "ROL_TEAM")
    private String rolTeam;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "RUT")
    private String rut;
    @JoinColumn(name = "TEAM_ID", referencedColumnName = "TEAM_ID")
    @ManyToOne
    private Team teamId;
    @JoinColumn(name = "RUT", referencedColumnName = "RUT", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;
    @JoinColumn(name = "PROGRAM_CODE", referencedColumnName = "PROGRAM_CODE")
    @ManyToOne
    private Program programCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Collection<Membership> membershipCollection;

    public Student() {
    }

    public Student(String rut) {
        this.rut = rut;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Program getProgramCode() {
        return programCode;
    }

    public void setProgramCode(Program programCode) {
        this.programCode = programCode;
    }

    @XmlTransient
    public Collection<Membership> getMembershipCollection() {
        return membershipCollection;
    }

    public void setMembershipCollection(Collection<Membership> membershipCollection) {
        this.membershipCollection = membershipCollection;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Student[ rut=" + rut + " ]";
    }

    public String getRolTeam() {
        return rolTeam;
    }

    public void setRolTeam(String rolTeam) {
        this.rolTeam = rolTeam;
    }
    
}
