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
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "programDetailsMB")
@RequestScoped
public class ProgramDetailsMB {
    @EJB
    private ProgramFacadeLocal programFacade;
    @Inject 
    private ProgramConversationMB mantProgram;
    private String code;
    private String name;
    private Short levels;
    
    public ProgramDetailsMB() {
    }
    
    @PostConstruct
    public void init() {
        if (mantProgram.getIdProgramDetails()!= null) {
            String codeProgram = mantProgram.getIdProgramDetails();
            loadProgram(codeProgram);
        }
        else {
            backToPrograms();
        }
    }
    
    private void loadProgram(String programCode) {
        Program programSelect = programFacade.find(programCode);
        if (programSelect == null) {
            backToPrograms();
            return;
        }
        this.code = programSelect.getProgramCode();
        this.name = programSelect.getProgramName();
        this.levels = programSelect.getLevels();
    }
    
    public void backToPrograms() {
        mantProgram.endConversation();
        General.goToPage("/faces/programList.xhtml");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getLevels() {
        return levels;
    }

    public void setLevels(Short levels) {
        this.levels = levels;
    }
}