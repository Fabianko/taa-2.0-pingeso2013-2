package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Team;
import cl.usach.pingeso.taa2.sessionbeans.TeamFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;  
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "teamMB")
@RequestScoped
public class TeamMB implements Serializable {
    @EJB
    private TeamFacadeLocal teamFacade;
    private List<Team> list;
    private List<Team> filteredTeams;
    @Inject 
    private TeamConversationMB mantTeam;
    
    public TeamMB() {
    }
    
    @PostConstruct
    public void init() {        
        this.list = teamFacade.findAll();
    }
    
    public List<Team> getList() {
        return list;
    }
    
    public void setList(List<Team> newList) {
        this.list = newList;
    }

    public List<Team> getFilteredTeams() {
        return filteredTeams;
    }

    public void setFilteredTeams(List<Team> filteredTeams) {
        this.filteredTeams = filteredTeams;
    }
    
    public void viewDetailsTeam(String teamId) {
        Team teamViewDetails = teamFacade.find(Long.parseLong(teamId));
        if (teamViewDetails != null) {
            this.mantTeam.beginConversation();
            this.mantTeam.setIdTeamDetails(teamId);
            General.goToPage("/faces/viewDetailsTeam.xhtml?cid=".concat(this.mantTeam.getConversation().getId()));
        }
        else {
            this.mantTeam.clean();
            General.goToPage("/faces/teamList.xhtml");
        }
    }
    
    public void add() {
       General.goToPage("/faces/teamRegister.xhtml");   
    }
    
    public void edit(String teamId) {
        Team teamEdit = teamFacade.find(Long.parseLong(teamId));
        if (teamEdit != null) {
            this.mantTeam.beginConversation();
            this.mantTeam.setIdTeamDetails(teamId);
            General.goToPage("/faces/teamEdit.xhtml?cid=".concat(this.mantTeam.getConversation().getId()));
        }
        else {
            this.mantTeam.clean();
            General.goToPage("/faces/teamList.xhtml");
        }
    }
    
    public void delete(String teamId) {
        Team temp = new Team();
        temp.setTeamId(Long.parseLong(teamId));
        boolean resultado;
        try
        {
            teamFacade.remove(temp);
            resultado = true;
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Grupo de trabajo eliminado satisfactoriamente !!!",
                    "Grupo de trabajo eliminado satisfactoriamente !!!");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No se puede eliminar el grupo mientras tenga alumnos asociados.",
                    "No se puede eliminar el grupo mientras tenga alumnos asociados.");
        }
        General.goToPage("/faces/teamList.xhtml?faces-redirect=true");
    }
    
    public void backToTeams() {
       General.goToPage("/faces/teamList.xhtml");
       
    }
}