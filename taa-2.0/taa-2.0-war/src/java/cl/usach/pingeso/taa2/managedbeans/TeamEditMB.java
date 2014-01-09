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
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "teamEditMB")
@RequestScoped
public class TeamEditMB {
    @EJB
    private TeamFacadeLocal teamFacade;
    @Inject
    private TeamConversationMB mantTeam;
    private String teamId;
    private String teamName;
    private String creationDate;
    private Date dateTemp;
    
    @PostConstruct
    public void init() { 
        if (mantTeam.getIdTeamDetails()!= null) {
            String team = mantTeam.getIdTeamDetails();
            loadTeam(team);
        }
        else {
            backToTeams();
        }
    }
    
    private void loadTeam(String team) {
        Team teamSelect = teamFacade.find(Long.parseLong(team));
        if (teamSelect == null) {
            backToTeams();
            return;
        }
        this.teamId = teamSelect.getTeamId().toString();
        this.teamName = teamSelect.getTeamName();
        this.dateTemp = teamSelect.getCreationDate();
    }
    
    public void saveTeam(){
        mantTeam.endConversation();
        try {
            Team newTeam = new Team();
            newTeam.setTeamId(Long.parseLong(teamId));
            newTeam.setTeamName(teamName);
            newTeam.setCreationDate(dateTemp);
            newTeam.setTeamState("1");
            teamFacade.edit(newTeam);
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha modificado el grupo de trabajo",
                    "Se ha modificado el grupo de trabajo \"".concat(teamName).concat("\""));
            General.goToPage("/faces/teamList.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/teamEdit.xhtml?faces-redirect=true");
        }
    }
    
    public void backToTeams() {
        mantTeam.endConversation();
        General.goToPage("/faces/teamList.xhtml");
    }
    
    public TeamEditMB() {
    }
    
    public TeamConversationMB getMantTeam() {
        return mantTeam;
    }

    public void setMantTeam(TeamConversationMB manTeamX) {
        this.mantTeam = manTeamX;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}