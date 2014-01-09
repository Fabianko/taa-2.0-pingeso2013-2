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

/**
 *
 * @author Diego
 */
@Named(value = "teamRegisterMB")
@RequestScoped
public class TeamRegisterMB {
    @EJB 
    private TeamFacadeLocal teamFacade;
    private String teamName;
    
    public TeamRegisterMB() {
    }
    
    @PostConstruct
    public void init() {
    }
    
    public void backToTeams() {
        General.goToPage("/faces/teamList.xhtml");
    }
    
    public void teamRegisterNow()
    {
        try
        {
            Team newTeam = new Team();
            newTeam.setTeamName(teamName);
            newTeam.setTeamState("1");
            newTeam.setCreationDate(new Date());
            if(teamFacade.findByTeamName(teamName) != null)
            {
                General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "Nombre de grupo ya existe.",
                    "Nombre de grupo ya existe.");
                General.goToPage("/faces/teamRegister.xhtml?faces-redirect=true");
            }
            else
            {
                teamFacade.create(newTeam);
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Grupo de trabajo registrado satisfactoriamente!!!",
                    "Grupo de trabajo registrado satisfactoriamente!!!");
                General.goToPage("/faces/teamRegister.xhtml?faces-redirect=true");
            }
        } 
        catch(Exception e)
        {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
            e.getMessage(), 
            e.getMessage());
            General.goToPage("/faces/teamRegister.xhtml?faces-redirect=true");
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}