/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nico
 */
@Entity
@Table(name = "course_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseHistory.findAll", query = "SELECT c FROM CourseHistory c"),
    @NamedQuery(name = "CourseHistory.findByHistoryId", query = "SELECT c FROM CourseHistory c WHERE c.historyId = :historyId"),
    @NamedQuery(name = "CourseHistory.findByDate", query = "SELECT c FROM CourseHistory c WHERE c.date = :date"),
    @NamedQuery(name = "CourseHistory.findByStudentsTotal", query = "SELECT c FROM CourseHistory c WHERE c.studentsTotal = :studentsTotal"),
    @NamedQuery(name = "CourseHistory.findByPresentsTotal", query = "SELECT c FROM CourseHistory c WHERE c.presentsTotal = :presentsTotal"),
    @NamedQuery(name = "CourseHistory.findByHistoryState", query = "SELECT c FROM CourseHistory c WHERE c.historyState = :historyState")})
public class CourseHistory implements Serializable {
    @JoinColumns({
        @JoinColumn(name = "TIMETABLE_CODE", referencedColumnName = "TIMETABLE_CODE"),
        @JoinColumn(name = "BLOCK_NUMBER", referencedColumnName = "BLOCK_NUMBER"),
        @JoinColumn(name = "BLOCK_DAY", referencedColumnName = "BLOCK_DAY")})
    @ManyToOne
    private Block block;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    private Long historyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "STUDENTS_TOTAL")
    private Integer studentsTotal;
    @Column(name = "PRESENTS_TOTAL")
    private Integer presentsTotal;
    @Size(max = 1)
    @Column(name = "HISTORY_STATE")
    private String historyState;
    @JoinColumn(name = "COURSE_CODE", referencedColumnName = "COURSE_CODE")
    @ManyToOne
    private Course courseCode;

    public CourseHistory() {
    }

    public CourseHistory(Long historyId) {
        this.historyId = historyId;
    }

    public CourseHistory(Long historyId, Date date) {
        this.historyId = historyId;
        this.date = date;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStudentsTotal() {
        return studentsTotal;
    }

    public void setStudentsTotal(Integer studentsTotal) {
        this.studentsTotal = studentsTotal;
    }

    public Integer getPresentsTotal() {
        return presentsTotal;
    }

    public void setPresentsTotal(Integer presentsTotal) {
        this.presentsTotal = presentsTotal;
    }

    public String getHistoryState() {
        return historyState;
    }

    public void setHistoryState(String historyState) {
        this.historyState = historyState;
    }

    public Course getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(Course courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historyId != null ? historyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseHistory)) {
            return false;
        }
        CourseHistory other = (CourseHistory) object;
        if ((this.historyId == null && other.historyId != null) || (this.historyId != null && !this.historyId.equals(other.historyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.CourseHistory[ historyId=" + historyId + " ]";
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
    
}
