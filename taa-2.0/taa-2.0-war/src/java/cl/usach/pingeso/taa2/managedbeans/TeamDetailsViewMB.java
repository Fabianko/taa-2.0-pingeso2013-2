/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Team;
import cl.usach.pingeso.taa2.sessionbeans.TeamFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "teamDetailsViewMB")
@RequestScoped
public class TeamDetailsViewMB {
    @EJB
    private TeamFacadeLocal teamFacade;
    @Inject 
    private TeamConversationMB mantTeam;
    private Date teamCreationDate;
    private String teamName;
    
    public TeamDetailsViewMB() {
    }
    
    @PostConstruct
    public void init() {
        if (mantTeam.getIdTeamDetails()!= null) {
            String teamId = mantTeam.getIdTeamDetails();
            loadTeam(teamId);
        }
        else {
            backToTeams();
        }
    }
    
    private void loadTeam(String teamId) {
        Team teamSelect = teamFacade.find(Long.parseLong(teamId));
        if (teamSelect == null) {
            backToTeams();
            return;
        }
        this.teamName = teamSelect.getTeamName();
        this.teamCreationDate = teamSelect.getCreationDate();
    }
    
    public void backToTeams() {
        mantTeam.endConversation();
        General.goToPage("/faces/teamList.xhtml");
    }

    public Date getTeamCreationDate() {
        return teamCreationDate;
    }

    public void setTeamCreationDate(Date teamCreationDate) {
        this.teamCreationDate = teamCreationDate;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}