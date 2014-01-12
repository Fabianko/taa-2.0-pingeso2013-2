/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.School;
import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.SchoolFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

/**
 *
 * @author Nico
 */
@Named(value = "programRegisterMB")
@RequestScoped
public class ProgramRegisterMB {
    @EJB
    private SchoolFacadeLocal schoolFacade;
    @EJB 
    private ProgramFacadeLocal programFacade;
    private String code;
    private String name;
    private String levels;
    private String schoolUniversity;
    private SelectItem[] schoolUniversityOptions;
    
    public ProgramRegisterMB() {
    }
    
    @PostConstruct
    public void init() {
        this.schoolUniversityOptions = createFilterOptions();
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
            newProgram.setLevels(Integer.parseInt(levels));
            newProgram.setProgramState("1");
            newProgram.setSchoolCode(schoolFacade.find(schoolUniversity));
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
    
    private SelectItem[] createFilterOptions()  { 
        List<School> schools = schoolFacade.findAll();
        SelectItem[] options = new SelectItem[schools.size() + 1];  
        options[0] = new SelectItem("", "Selecciona");  
        for(int i = 0; i < schools.size(); i++) {  
            options[i + 1] = new SelectItem(schools.get(i).getSchoolCode(), schools.get(i).getSchoolName().concat(" / ").concat(schools.get(i).getRutUniversity().getUniversityName()));  
        }  
        return options;  
    }

    public String getSchoolUniversity() {
        return schoolUniversity;
    }

    public void setSchoolUniversity(String schoolUniversity) {
        this.schoolUniversity = schoolUniversity;
    }

    public SelectItem[] getSchoolUniversityOptions() {
        return schoolUniversityOptions;
    }

    public void setSchoolUniversityOptions(SelectItem[] schoolUniversityOptions) {
        this.schoolUniversityOptions = schoolUniversityOptions;
    }
}