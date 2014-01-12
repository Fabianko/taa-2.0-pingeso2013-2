package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.School;
import cl.usach.pingeso.taa2.entityclasses.University;
import cl.usach.pingeso.taa2.managedbeans.login.SessionValidator;
import cl.usach.pingeso.taa2.sessionbeans.SchoolFacadeLocal;
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
@Named(value = "schoolMB")
@RequestScoped
public class SchoolMB implements Serializable {
    @EJB
    private UniversityFacadeLocal universityFacade;
    @EJB
    private SchoolFacadeLocal schoolFacade;
    private List<School> schoolList;
    private List<School> schoolFiltered;
    private boolean showBtnSchool;
     private boolean showcolumnSchool;
    @Inject 
    private SchoolConversationMB mantSchool;
    @Inject
    private SessionValidator session;
    private SelectItem[] universityOptions;
    
    public SchoolMB() {
    }
    
    @PostConstruct
    public void init() {
        if(General.isUserInRole("Administrador")){
            this.schoolList = schoolFacade.findAll();
            this.universityOptions = createFilterOptions();
        }
        else{
            this.universityOptions = createFilterOptions();
            this.schoolList = schoolFacade.findAll();
            //this.schoolList= schoolFacade.findByRut(session.userRut());
        }
        this.showcolumnSchool = true;//General.isUserInRole("Profesor");
        this.showBtnSchool = true;//General.isUserInRole("Administrador");
    }
    
    public void viewDetailsSchool(String schoolCode) {
        School schoolViewDetails = schoolFacade.find(schoolCode);
        if (schoolViewDetails != null) {
            this.mantSchool.beginConversation();
            this.mantSchool.setIdSchoolDetails(schoolCode);
            General.goToPage("/faces/viewDetailsSchool.xhtml?cid=".concat(this.mantSchool.getConversation().getId()));
        }
        else {
            this.mantSchool.clean();
            General.goToPage("/faces/schoolList.xhtml");
        }
    }
    
    public void addSchool() {
       General.goToPage("/faces/schoolRegister.xhtml");   
    }
    
    public void editSchool(String schoolCode) {
        School schoolEdit = schoolFacade.find(schoolCode);
        if (schoolEdit != null) {
            this.mantSchool.beginConversation();
            this.mantSchool.setIdSchoolDetails(schoolCode);
            General.goToPage("/faces/schoolEdit.xhtml?cid=".concat(this.mantSchool.getConversation().getId()));
        }
        else {
            this.mantSchool.clean();
            General.goToPage("/faces/schoolList.xhtml");
        }
    }
    
    public void deleteSchool(String schoolCode) {
        School temp = new School();
        temp.setSchoolCode(schoolCode);
        boolean resultado;
        try
        {
            School tempSchool = schoolFacade.find(schoolCode);
            schoolFacade.remove(tempSchool);
            resultado = true;
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Facultad/escuela eliminada satisfactoriamente !!!",
                    "Facultad/escuela eliminada satisfactoriamente !!!");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No es posible eliminar la facultad/escuela mientras tenga carreras registradas.",
                    "No es posible eliminar la facultad/escuela mientras tenga carreras registradas.");
        }
        General.goToPage("/faces/schoolList.xhtml?faces-redirect=true");
    }
    
    public void backToSchools() {
       General.goToPage("/faces/SchoolList.xhtml");    
    }
    
    public boolean isShowcolumn() {
        return showcolumnSchool;
    }

    public void setShowcolumn(boolean showcolumnSchool) {
        this.showcolumnSchool = showcolumnSchool;
    }
    
    public boolean isShowBtnSchool() {
        return showBtnSchool;
    }

    public void setShowBtnSchool(boolean showBtnSchool) {
        this.showBtnSchool = showBtnSchool;
    }

    public List<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }

    public List<School> getSchoolFiltered() {
        return schoolFiltered;
    }

    public void setSchoolFiltered(List<School> schoolFiltered) {
        this.schoolFiltered = schoolFiltered;
    }

    public SchoolConversationMB getMantSchool() {
        return mantSchool;
    }

    public void setMantSchool(SchoolConversationMB mantSchool) {
        this.mantSchool = mantSchool;
    }
    
    private SelectItem[] createFilterOptions()  { 
        List<University> universities = universityFacade.findAll();
        SelectItem[] options = new SelectItem[universities.size() + 1];  
        options[0] = new SelectItem("", "Selecciona");  
        for(int i = 0; i < universities.size(); i++) {  
            options[i + 1] = new SelectItem(universities.get(i).getUniversityName(), universities.get(i).getUniversityName());  
        }  
        return options;  
    }

    public SelectItem[] getUniversityOptions() {
        return universityOptions;
    }
}