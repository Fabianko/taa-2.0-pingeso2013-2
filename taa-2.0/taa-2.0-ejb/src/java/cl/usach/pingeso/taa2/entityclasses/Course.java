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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c"),
    @NamedQuery(name = "Course.findByCourseCode", query = "SELECT c FROM Course c WHERE c.courseCode = :courseCode"),
    @NamedQuery(name = "Course.findByCourseName", query = "SELECT c FROM Course c WHERE c.courseName = :courseName"),
    @NamedQuery(name = "Course.findByMainClassroom", query = "SELECT c FROM Course c WHERE c.mainClassroom = :mainClassroom"),
    @NamedQuery(name = "Course.findByAttendanceRequired", query = "SELECT c FROM Course c WHERE c.attendanceRequired = :attendanceRequired"),
    @NamedQuery(name = "Course.findByCourseState", query = "SELECT c FROM Course c WHERE c.courseState = :courseState")})
public class Course implements Serializable {
    @JoinTable(name = "workload", joinColumns = {
        @JoinColumn(name = "COURSE_CODE", referencedColumnName = "COURSE_CODE")}, inverseJoinColumns = {
        @JoinColumn(name = "TIMETABLE_CODE", referencedColumnName = "TIMETABLE_CODE"),
        @JoinColumn(name = "BLOCK_NUMBER", referencedColumnName = "BLOCK_NUMBER"),
        @JoinColumn(name = "BLOCK_DAY", referencedColumnName = "BLOCK_DAY")})
    @ManyToMany
    private Collection<Block> blockCollection;
    @JoinTable(name = "program_course", joinColumns = {
        @JoinColumn(name = "COURSE_CODE", referencedColumnName = "COURSE_CODE")}, inverseJoinColumns = {
        @JoinColumn(name = "PROGRAM_CODE", referencedColumnName = "PROGRAM_CODE")})
    @ManyToMany
    private Collection<Program> programCollection;
    @OneToMany(mappedBy = "courseCode")
    private Collection<CourseHistory> courseHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Collection<Membership> membershipCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Collection<Attendance> attendanceCollection;
    @OneToMany(mappedBy = "courseCode")
    private Collection<Team> teamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Collection<Assignment> assignmentCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "COURSE_CODE")
    private String courseCode;
    @Size(max = 50)
    @Column(name = "COURSE_NAME")
    private String courseName;
    @Size(max = 10)
    @Column(name = "MAIN_CLASSROOM")
    private String mainClassroom;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ATTENDANCE_REQUIRED")
    private Float attendanceRequired;
    @Size(max = 1)
    @Column(name = "COURSE_STATE")
    private String courseState;

    public Course() {
    }

    public Course(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getMainClassroom() {
        return mainClassroom;
    }

    public void setMainClassroom(String mainClassroom) {
        this.mainClassroom = mainClassroom;
    }

    public Float getAttendanceRequired() {
        return attendanceRequired;
    }

    public void setAttendanceRequired(Float attendanceRequired) {
        this.attendanceRequired = attendanceRequired;
    }

    public String getCourseState() {
        return courseState;
    }

    public void setCourseState(String courseState) {
        this.courseState = courseState;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseCode != null ? courseCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.courseCode == null && other.courseCode != null) || (this.courseCode != null && !this.courseCode.equals(other.courseCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Course[ courseCode=" + courseCode + " ]";
    }

    @XmlTransient
    public Collection<Block> getBlockCollection() {
        return blockCollection;
    }

    public void setBlockCollection(Collection<Block> blockCollection) {
        this.blockCollection = blockCollection;
    }

    @XmlTransient
    public Collection<Program> getProgramCollection() {
        return programCollection;
    }

    public void setProgramCollection(Collection<Program> programCollection) {
        this.programCollection = programCollection;
    }

    @XmlTransient
    public Collection<CourseHistory> getCourseHistoryCollection() {
        return courseHistoryCollection;
    }

    public void setCourseHistoryCollection(Collection<CourseHistory> courseHistoryCollection) {
        this.courseHistoryCollection = courseHistoryCollection;
    }

    @XmlTransient
    public Collection<Membership> getMembershipCollection() {
        return membershipCollection;
    }

    public void setMembershipCollection(Collection<Membership> membershipCollection) {
        this.membershipCollection = membershipCollection;
    }

    @XmlTransient
    public Collection<Attendance> getAttendanceCollection() {
        return attendanceCollection;
    }

    public void setAttendanceCollection(Collection<Attendance> attendanceCollection) {
        this.attendanceCollection = attendanceCollection;
    }

    @XmlTransient
    public Collection<Team> getTeamCollection() {
        return teamCollection;
    }

    public void setTeamCollection(Collection<Team> teamCollection) {
        this.teamCollection = teamCollection;
    }

    @XmlTransient
    public Collection<Assignment> getAssignmentCollection() {
        return assignmentCollection;
    }

    public void setAssignmentCollection(Collection<Assignment> assignmentCollection) {
        this.assignmentCollection = assignmentCollection;
    }
    
}
