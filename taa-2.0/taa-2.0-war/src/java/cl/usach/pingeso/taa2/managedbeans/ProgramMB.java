package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
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
@Named(value = "programMB")
@RequestScoped
public class ProgramMB implements Serializable {
    @EJB
    private ProgramFacadeLocal programFacade;
    private List<Program> list;
    private List<Program> filteredPrograms;
    @Inject 
    private ProgramConversationMB mantProgram;
    
    public ProgramMB() {
    }
    
    @PostConstruct
    public void init() {        
        this.list = programFacade.findAll();
    }
    
    public List<Program> getList() {
        return list;
    }
    
    public void setList(List<Program> newList) {
        this.list = newList;
    }

    public List<Program> getFilteredPrograms() {
        return filteredPrograms;
    }

    public void setFilteredPrograms(List<Program> filteredPrograms) {
        this.filteredPrograms = filteredPrograms;
    }
    
    public void viewDetailsProgram(String programCode) {
        Program programViewDetails = programFacade.find(programCode);
        if (programViewDetails != null) {
            this.mantProgram.beginConversation();
            this.mantProgram.setIdProgramDetails(programCode);
            General.goToPage("/faces/viewDetailsProgram.xhtml?cid=".concat(this.mantProgram.getConversation().getId()));
        }
        else {
            this.mantProgram.clean();
            General.goToPage("/faces/programList.xhtml");
        }
    }
    
    public void add() {
       General.goToPage("/faces/programRegister.xhtml");   
    }
    
    public void edit(String programCode) {
        Program programEdit = programFacade.find(programCode);
        if (programEdit != null) {
            this.mantProgram.beginConversation();
            this.mantProgram.setIdProgramDetails(programCode);
            General.goToPage("/faces/editProgram.xhtml?cid=".concat(this.mantProgram.getConversation().getId()));
        }
        else {
            this.mantProgram.clean();
            General.goToPage("/faces/programList.xhtml");
        }
    }
    
    public void delete(String programCode) {
        Program temp = new Program();
        temp.setProgramCode(programCode);
        boolean resultado;
        try
        {
            programFacade.remove(temp);
            resultado = true;
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Carrera eliminada satisfactoriamente !!!",
                    "Carrera eliminada satisfactoriamente !!!");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No se puede eliminar la carrera mientras tenga alumnos inscritos.",
                    "No se puede eliminar la carrera mientras tenga alumnos inscritos.");
        }
        General.goToPage("/faces/programList.xhtml?faces-redirect=true");
    }
    
    public void backToPrograms() {
       General.goToPage("/faces/programList.xhtml");
       
    }
}