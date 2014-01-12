package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.University;
import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UniversityFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;  
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "programMB")
@RequestScoped
public class ProgramMB implements Serializable {
    @EJB
    private UniversityFacadeLocal universityFacade;
    @EJB
    private ProgramFacadeLocal programFacade;
    private List<Program> list;
    private List<Program> filteredPrograms;
    @Inject 
    private ProgramConversationMB mantProgram;
    private boolean showcolumn;
    private boolean showBtnAdd;
    private String schoolUniversity;
    private SelectItem[] schoolUniversityOptions;

    public ProgramMB() {
    }
    
    @PostConstruct
    public void init() {        
        this.schoolUniversityOptions = createFilterOptions();
        if(General.isUserInRole("Administrador")){
            this.list = programFacade.findAll();
        }
        else{
            this.list = programFacade.findAll();
            //this.list= programFacade.findByUniversity(session.userRut());
        }
        this.showcolumn = true;//General.isUserInRole("Profesor");
        this.showBtnAdd = true;//General.isUserInRole("Administrador");
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
        Program temp = programFacade.find(programCode);
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
                    "Carrera ha sido eliminada satisfactoriamente !!!",
                    "Carrera ha sido eliminada satisfactoriamente !!!");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No se puede eliminar la carrera mientras tenga cursos y/o alumnos asignados.",
                    "No se puede eliminar la carrera mientras tenga cursos y/o alumnos asignados.");
        }
        General.goToPage("/faces/programList.xhtml?faces-redirect=true");
    }
    
    public void backToPrograms() {
       General.goToPage("/faces/programList.xhtml");
       
    }

    public ProgramConversationMB getMantProgram() {
        return mantProgram;
    }

    public void setMantProgram(ProgramConversationMB mantProgram) {
        this.mantProgram = mantProgram;
    }

    public boolean isShowcolumn() {
        return showcolumn;
    }

    public void setShowcolumn(boolean showcolumn) {
        this.showcolumn = showcolumn;
    }

    public boolean isShowBtnAdd() {
        return showBtnAdd;
    }

    public void setShowBtnAdd(boolean showBtnAdd) {
        this.showBtnAdd = showBtnAdd;
    }
    
    private SelectItem[] createFilterOptions()  { 
        List<University> schools = universityFacade.findAll();
        SelectItem[] options = new SelectItem[schools.size() + 1];  
        options[0] = new SelectItem("", "Selecciona");  
        for(int i = 0; i < schools.size(); i++) {  
            options[i + 1] = new SelectItem(schools.get(i).getUniversityName(), schools.get(i).getUniversityName());  
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