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
public class StudentTeamPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEAM_ID")
    private long teamId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RUT")
    private String rut;

    public StudentTeamPK() {
    }

    public StudentTeamPK(long teamId, String rut) {
        this.teamId = teamId;
        this.rut = rut;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) teamId;
        hash += (rut != null ? rut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentTeamPK)) {
            return false;
        }
        StudentTeamPK other = (StudentTeamPK) object;
        if (this.teamId != other.teamId) {
            return false;
        }
        if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.usach.pingeso.taa2.entityclasses.StudentTeamPK[ teamId=" + teamId + ", rut=" + rut + " ]";
    }
    
}
