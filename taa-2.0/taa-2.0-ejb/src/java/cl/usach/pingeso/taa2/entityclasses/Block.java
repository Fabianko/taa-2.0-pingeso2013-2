/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "block")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Block.findAll", query = "SELECT b FROM Block b"),
    @NamedQuery(name = "Block.findByTimetableCode", query = "SELECT b FROM Block b WHERE b.blockPK.timetableCode = :timetableCode"),
    @NamedQuery(name = "Block.findByBlockNumber", query = "SELECT b FROM Block b WHERE b.blockPK.blockNumber = :blockNumber"),
    @NamedQuery(name = "Block.findByBlockDay", query = "SELECT b FROM Block b WHERE b.blockPK.blockDay = :blockDay")})
public class Block implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BlockPK blockPK;
    @ManyToMany(mappedBy = "blockCollection")
    private Collection<Course> courseCollection;
    @OneToMany(mappedBy = "block")
    private Collection<CourseHistory> courseHistoryCollection;
    @OneToMany(mappedBy = "block")
    private Collection<Attendance> attendanceCollection;
    @JoinColumn(name = "TIMETABLE_CODE", referencedColumnName = "TIMETABLE_CODE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Timetable timetable;

    public Block() {
    }

    public Block(BlockPK blockPK) {
        this.blockPK = blockPK;
    }

    public Block(String timetableCode, int blockNumber, String blockDay) {
        this.blockPK = new BlockPK(timetableCode, blockNumber, blockDay);
    }

    public BlockPK getBlockPK() {
        return blockPK;
    }

    public void setBlockPK(BlockPK blockPK) {
        this.blockPK = blockPK;
    }

    @XmlTransient
    public Collection<Course> getCourseCollection() {
        return courseCollection;
    }

    public void setCourseCollection(Collection<Course> courseCollection) {
        this.courseCollection = courseCollection;
    }

    @XmlTransient
    public Collection<CourseHistory> getCourseHistoryCollection() {
        return courseHistoryCollection;
    }

    public void setCourseHistoryCollection(Collection<CourseHistory> courseHistoryCollection) {
        this.courseHistoryCollection = courseHistoryCollection;
    }

    @XmlTransient
    public Collection<Attendance> getAttendanceCollection() {
        return attendanceCollection;
    }

    public void setAttendanceCollection(Collection<Attendance> attendanceCollection) {
        this.attendanceCollection = attendanceCollection;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blockPK != null ? blockPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Block)) {
            return false;
        }
        Block other = (Block) object;
        if ((this.blockPK == null && other.blockPK != null) || (this.blockPK != null && !this.blockPK.equals(other.blockPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Block[ blockPK=" + blockPK + " ]";
    }
    
}
