/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Diego
 */
@Embeddable
public class BlockPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TIMETABLE_CODE")
    private String timetableCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BLOCK_NUMBER")
    private int blockNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "BLOCK_DAY")
    private String blockDay;

    public BlockPK() {
    }

    public BlockPK(String timetableCode, int blockNumber, String blockDay) {
        this.timetableCode = timetableCode;
        this.blockNumber = blockNumber;
        this.blockDay = blockDay;
    }

    public String getTimetableCode() {
        return timetableCode;
    }

    public void setTimetableCode(String timetableCode) {
        this.timetableCode = timetableCode;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getBlockDay() {
        return blockDay;
    }

    public void setBlockDay(String blockDay) {
        this.blockDay = blockDay;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timetableCode != null ? timetableCode.hashCode() : 0);
        hash += (int) blockNumber;
        hash += (blockDay != null ? blockDay.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlockPK)) {
            return false;
        }
        BlockPK other = (BlockPK) object;
        if ((this.timetableCode == null && other.timetableCode != null) || (this.timetableCode != null && !this.timetableCode.equals(other.timetableCode))) {
            return false;
        }
        if (this.blockNumber != other.blockNumber) {
            return false;
        }
        if ((this.blockDay == null && other.blockDay != null) || (this.blockDay != null && !this.blockDay.equals(other.blockDay))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.BlockPK[ timetableCode=" + timetableCode + ", blockNumber=" + blockNumber + ", blockDay=" + blockDay + " ]";
    }
    
}
