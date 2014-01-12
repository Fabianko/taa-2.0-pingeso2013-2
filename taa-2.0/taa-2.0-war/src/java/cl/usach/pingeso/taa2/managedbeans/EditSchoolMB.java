/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.School;
import cl.usach.pingeso.taa2.entityclasses.Timetable;
import cl.usach.pingeso.taa2.entityclasses.University;
import cl.usach.pingeso.taa2.sessionbeans.SchoolFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.TimetableFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UniversityFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "editSchoolMB")
@RequestScoped
public class EditSchoolMB {
    @EJB
    private UniversityFacadeLocal universityFacade;
    @EJB
    private TimetableFacadeLocal timetableFacade;
    @EJB
    private SchoolFacadeLocal schoolFacade;
    @Inject
    private SchoolConversationMB mantSchool;
    private String schoolCode;
    private String schoolName;
    private String schoolPhone;
    private String schoolAdress;
    private String schoolEmail;
    private String timetableCode;
    private String universityCode;
    private SelectItem[] universityOptions;
    private SelectItem[] timetableOptions;
    private Collection<Program> programsSchool;
    
    @PostConstruct
    public void init() { 
        this.universityOptions = createFilterOptions();
        this.timetableOptions = createFilterTimetableOptions();
        if (mantSchool.getIdSchoolDetails()!= null) {
            String school = mantSchool.getIdSchoolDetails();
            loadSchool(school);
        }
        else {
            backToSchools();
        }
    }
    
    private void loadSchool(String school) {
        School schoolSelect = schoolFacade.find(school);
        if (schoolSelect == null) {
            backToSchools();
            return;
        }
        this.schoolCode = schoolSelect.getSchoolCode();
        this.schoolName = schoolSelect.getSchoolName();
        this.schoolAdress = schoolSelect.getSchoolAdress();
        this.schoolEmail = schoolSelect.getSchoolEmail();
        this.schoolPhone = schoolSelect.getSchoolPhone();
        if(schoolSelect.getTimetableCode()!=null)
            this.timetableCode = schoolSelect.getTimetableCode().getTimetableCode();
        this.universityCode = schoolSelect.getRutUniversity().getRutUniversity();
    }
    
    public void saveSchool(){
        List<School> tempSchool;
        tempSchool = schoolFacade.findByName(schoolName);
        tempSchool.remove(schoolFacade.find(schoolCode));
        boolean valid = true;
        for(int i=0; i < tempSchool.size();i++)
        {
            if(tempSchool.get(i).getRutUniversity().getUniversityName().toString().compareTo(universityFacade.find(universityCode).getUniversityName())==0 )
                valid = false;
        }
        if(!valid)
        {   
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    schoolName.concat(" ya existe en la universidad especificada."),
                    schoolName.concat(" ya existe en la universidad especificada."));
            General.goToPage("/faces/schoolList.xhtml?faces-redirect=true");
        }
        else
        {
            mantSchool.endConversation();
            try {
                School newSchool = new School();
                newSchool.setSchoolCode(schoolCode);
                newSchool.setSchoolName(schoolName);
                newSchool.setSchoolAdress(schoolAdress);
                newSchool.setSchoolEmail(schoolEmail);
                newSchool.setSchoolPhone(schoolPhone);
                newSchool.setSchoolState("1");
                if(timetableCode!=null)
                {
                    Timetable timetableTemp = timetableFacade.find(timetableCode);
                    newSchool.setTimetableCode(timetableTemp);
                }
                else
                    newSchool.setTimetableCode(null);
                newSchool.setRutUniversity(universityFacade.find(universityCode));
                newSchool.setProgramCollection(programsSchool);
                schoolFacade.edit(newSchool);
                
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                    schoolName.concat(" modificada satisfactoriamente !!!"),
                    schoolName.concat(" modificada satisfactoriamente !!!"));
                General.goToPage("/faces/schoolList.xhtml?faces-redirect=true");
            }
            catch (Exception e) {
                General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                        e.getMessage(), 
                        e.getMessage());
                General.goToPage("/faces/schoolEdit.xhtml?faces-redirect=true");
            }
        }
    }
    
    public void backToSchools() {
        mantSchool.endConversation();
        General.goToPage("/faces/schoolList.xhtml");
    }
    
    public EditSchoolMB() {
    }

    public SchoolConversationMB getMantSchool() {
        return mantSchool;
    }

    public void setMantSchool(SchoolConversationMB mantSchool) {
        this.mantSchool = mantSchool;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public String getSchoolAdress() {
        return schoolAdress;
    }

    public void setSchoolAdress(String schoolAdress) {
        this.schoolAdress = schoolAdress;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getTimetableCode() {
        return timetableCode;
    }

    public void setTimetableCode(String timetableCode) {
        this.timetableCode = timetableCode;
    }

    public String getUniversityCode() {
        return universityCode;
    }

    public void setUniversityCode(String universityCode) {
        this.universityCode = universityCode;
    }
    
    private SelectItem[] createFilterOptions()  { 
        List<University> universities = universityFacade.findAll();
        SelectItem[] options = new SelectItem[universities.size() + 1];  
        options[0] = new SelectItem("", "Selecciona");  
        for(int i = 0; i < universities.size(); i++) {  
            options[i + 1] = new SelectItem(universities.get(i).getRutUniversity(), universities.get(i).getUniversityName());  
        }
        return options;  
    }
    
    private SelectItem[] createFilterTimetableOptions()  { 
        List<Timetable> timetables = timetableFacade.findAll();
        SelectItem[] options = new SelectItem[timetables.size() + 1];  
        options[0] = new SelectItem("", "Selecciona");  
        for(int i = 0; i < timetables.size(); i++) {  
            options[i + 1] = new SelectItem(timetables.get(i).getTimetableCode(), timetables.get(i).getTimetableName());  
        }
        return options;  
    }

    public SelectItem[] getUniversityOptions() {
        return universityOptions;
    }

    public void setUniversityOptions(SelectItem[] universityOptions) {
        this.universityOptions = universityOptions;
    }

    public SelectItem[] getTimetableOptions() {
        return timetableOptions;
    }

    public void setTimetableOptions(SelectItem[] timetableOptions) {
        this.timetableOptions = timetableOptions;
    }
}