/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
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
@Named(value = "programEditMB")
@RequestScoped
public class ProgramEditMB {
    @EJB
    private ProgramFacadeLocal programFacade;
    @Inject
    private ProgramConversationMB mantProgram;
    private String programCode;
    private String programName;
    private String levels;
    
    @PostConstruct
    public void init() { 
        if (mantProgram.getIdProgramDetails()!= null) {
            String program = mantProgram.getIdProgramDetails();
            loadProgram(program);
        }
        else {
            backToPrograms();
        }
    }
    
    private void loadProgram(String program) {
        Program programSelect = programFacade.find(program);
        if (programSelect == null) {
            backToPrograms();
            return;
        }
        this.programCode = programSelect.getProgramCode();
        this.programName = programSelect.getProgramName();
        this.levels = programSelect.getLevels().toString();
    }
    
    public void saveProgram(){
        mantProgram.endConversation();
        try {
            Program newProgram = new Program();
            newProgram.setProgramCode(programCode);
            newProgram.setProgramName(programName);
            newProgram.setLevels(Integer.parseInt(levels));
            newProgram.setProgramState("1");
            programFacade.edit(newProgram);
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han modificado los datos de la carrera",
                    "Se han modificado los datos de la carrera \"".concat(programCode).concat("\""));
            General.goToPage("/faces/programList.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/programEdit.xhtml?faces-redirect=true");
        }
    }
    
    public void backToPrograms() {
        mantProgram.endConversation();
        General.goToPage("/faces/programList.xhtml");
    }
    
    public ProgramEditMB() {
    }
    
    public ProgramConversationMB getMantProgram() {
        return mantProgram;
    }

    public void setMantProgram(ProgramConversationMB manProgramX) {
        this.mantProgram = manProgramX;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }
}