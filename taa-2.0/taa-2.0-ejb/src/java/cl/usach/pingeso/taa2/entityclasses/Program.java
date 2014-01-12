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
import javax.persistence.ManyToMany;
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
@Table(name = "program")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Program.findAll", query = "SELECT p FROM Program p"),
    @NamedQuery(name = "Program.findByProgramCode", query = "SELECT p FROM Program p WHERE p.programCode = :programCode"),
    @NamedQuery(name = "Program.findByProgramName", query = "SELECT p FROM Program p WHERE p.programName = :programName"),
    @NamedQuery(name = "Program.findByLevels", query = "SELECT p FROM Program p WHERE p.levels = :levels"),
    @NamedQuery(name = "Program.findByProgramState", query = "SELECT p FROM Program p WHERE p.programState = :programState")})

public class Program implements Serializable {
    @ManyToMany(mappedBy = "programCollection")
    private Collection<Course> courseCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PROGRAM_CODE")
    private String programCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PROGRAM_NAME")
    private String programName;
    @Column(name = "LEVELS")
    private Integer levels;
    @Size(max = 1)
    @Column(name = "PROGRAM_STATE")
    private String programState;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programCode")
    private Collection<Student> studentCollection;
    @JoinColumn(name = "SCHOOL_CODE", referencedColumnName = "SCHOOL_CODE")
    @ManyToOne
    private School schoolCode;

    public Program() {
    }

    public Program(String programCode) {
        this.programCode = programCode;
    }

    public Program(String programCode, String programName) {
        this.programCode = programCode;
        this.programName = programName;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getProgramState() {
        return programState;
    }

    public void setProgramState(String programState) {
        this.programState = programState;
    }

    @XmlTransient
    public Collection<Student> getStudentCollection() {
        return studentCollection;
    }

    public void setStudentCollection(Collection<Student> studentCollection) {
        this.studentCollection = studentCollection;
    }

    public School getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(School schoolCode) {
        this.schoolCode = schoolCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programCode != null ? programCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Program)) {
            return false;
        }
        Program other = (Program) object;
        if ((this.programCode == null && other.programCode != null) || (this.programCode != null && !this.programCode.equals(other.programCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Program[ programCode=" + programCode + " ]";
    }

    @XmlTransient
    public Collection<Course> getCourseCollection() {
        return courseCollection;
    }

    public void setCourseCollection(Collection<Course> courseCollection) {
        this.courseCollection = courseCollection;
    }
    
}
