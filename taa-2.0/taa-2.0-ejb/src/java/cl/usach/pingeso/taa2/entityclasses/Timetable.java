/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Diego
 */
@Entity
@Table(name = "timetable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Timetable.findAll", query = "SELECT t FROM Timetable t"),
    @NamedQuery(name = "Timetable.findByTimetableCode", query = "SELECT t FROM Timetable t WHERE t.timetableCode = :timetableCode"),
    @NamedQuery(name = "Timetable.findByTimetableName", query = "SELECT t FROM Timetable t WHERE t.timetableName = :timetableName"),
    @NamedQuery(name = "Timetable.findByBlocksNumberTotal", query = "SELECT t FROM Timetable t WHERE t.blocksNumberTotal = :blocksNumberTotal"),
    @NamedQuery(name = "Timetable.findByBlockDuration", query = "SELECT t FROM Timetable t WHERE t.blockDuration = :blockDuration"),
    @NamedQuery(name = "Timetable.findByBeginFirstBlock", query = "SELECT t FROM Timetable t WHERE t.beginFirstBlock = :beginFirstBlock"),
    @NamedQuery(name = "Timetable.findByEndLastBlock", query = "SELECT t FROM Timetable t WHERE t.endLastBlock = :endLastBlock"),
    @NamedQuery(name = "Timetable.findByTimetableState", query = "SELECT t FROM Timetable t WHERE t.timetableState = :timetableState")})
public class Timetable implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timetable")
    private Collection<Block> blockCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIMETABLE_CODE")
    private String timetableCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TIMETABLE_NAME")
    private String timetableName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BLOCKS_NUMBER_TOTAL")
    private int blocksNumberTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BLOCK_DURATION")
    private int blockDuration;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BEGIN_FIRST_BLOCK")
    @Temporal(TemporalType.TIME)
    private Date beginFirstBlock;
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_LAST_BLOCK")
    @Temporal(TemporalType.TIME)
    private Date endLastBlock;
    @Size(max = 1)
    @Column(name = "TIMETABLE_STATE")
    private String timetableState;
    @OneToMany(mappedBy = "timetableCode")
    private Collection<School> schoolCollection;

    public Timetable() {
    }

    public Timetable(String timetableCode) {
        this.timetableCode = timetableCode;
    }

    public Timetable(String timetableCode, String timetableName, int blocksNumberTotal, int blockDuration, Date beginFirstBlock, Date endLastBlock) {
        this.timetableCode = timetableCode;
        this.timetableName = timetableName;
        this.blocksNumberTotal = blocksNumberTotal;
        this.blockDuration = blockDuration;
        this.beginFirstBlock = beginFirstBlock;
        this.endLastBlock = endLastBlock;
    }

    public String getTimetableCode() {
        return timetableCode;
    }

    public void setTimetableCode(String timetableCode) {
        this.timetableCode = timetableCode;
    }

    public String getTimetableName() {
        return timetableName;
    }

    public void setTimetableName(String timetableName) {
        this.timetableName = timetableName;
    }

    public int getBlocksNumberTotal() {
        return blocksNumberTotal;
    }

    public void setBlocksNumberTotal(int blocksNumberTotal) {
        this.blocksNumberTotal = blocksNumberTotal;
    }

    public int getBlockDuration() {
        return blockDuration;
    }

    public void setBlockDuration(int blockDuration) {
        this.blockDuration = blockDuration;
    }

    public Date getBeginFirstBlock() {
        return beginFirstBlock;
    }

    public void setBeginFirstBlock(Date beginFirstBlock) {
        this.beginFirstBlock = beginFirstBlock;
    }

    public Date getEndLastBlock() {
        return endLastBlock;
    }

    public void setEndLastBlock(Date endLastBlock) {
        this.endLastBlock = endLastBlock;
    }

    public String getTimetableState() {
        return timetableState;
    }

    public void setTimetableState(String timetableState) {
        this.timetableState = timetableState;
    }

    @XmlTransient
    public Collection<School> getSchoolCollection() {
        return schoolCollection;
    }

    public void setSchoolCollection(Collection<School> schoolCollection) {
        this.schoolCollection = schoolCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timetableCode != null ? timetableCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Timetable)) {
            return false;
        }
        Timetable other = (Timetable) object;
        if ((this.timetableCode == null && other.timetableCode != null) || (this.timetableCode != null && !this.timetableCode.equals(other.timetableCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.Timetable[ timetableCode=" + timetableCode + " ]";
    }

    @XmlTransient
    public Collection<Block> getBlockCollection() {
        return blockCollection;
    }

    public void setBlockCollection(Collection<Block> blockCollection) {
        this.blockCollection = blockCollection;
    }
    
}
