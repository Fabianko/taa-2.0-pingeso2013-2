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

/**
 *
 * @author Nico
 */
@Named(value = "programRegisterMB")
@RequestScoped
public class ProgramRegisterMB {
    @EJB 
    private ProgramFacadeLocal programFacade;
    private String code;
    private String name;
    private String levels;
    
    public ProgramRegisterMB() {
    }
    
    @PostConstruct
    public void init() {
    }
    
    public void backToPrograms() {
        General.goToPage("/faces/programList.xhtml");
    }
    
    public void programRegisterNow()
    {
        try
        {
            Program newProgram = new Program();
            newProgram.setProgramCode(code);
            newProgram.setProgramName(name);
            newProgram.setLevels(Short.parseShort(levels));
            newProgram.setProgramState("1");
            if(programFacade.find(code) != null)
            {
                General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "Código de carrera ya existe.",
                    "Código de carrera ya existe.");
                General.goToPage("/faces/programRegister.xhtml?faces-redirect=true");
            }
            else
            {
                if(programFacade.findByProgramName(name) != null)
                {
                    General.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "Nombre de carrera ya existe.",
                        "Nombre de carrera ya existe.");
                    General.goToPage("/faces/programRegister.xhtml?faces-redirect=true");
                }
                else
                {
                    programFacade.create(newProgram);
                    General.viewMessage(FacesMessage.SEVERITY_INFO,
                        "Carrera registrada satisfactoriamente!!!",
                        "Carrera registrada satisfactoriamente!!!");
                    General.goToPage("/faces/programRegister.xhtml?faces-redirect=true");
                }
            }
        } 
        catch(Exception e)
        {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
            e.getMessage(), 
            e.getMessage());
            General.goToPage("/faces/programRegister.xhtml?faces-redirect=true");
        }
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

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }
}