/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.School;
import cl.usach.pingeso.taa2.entityclasses.Timetable;
import cl.usach.pingeso.taa2.entityclasses.University;
import cl.usach.pingeso.taa2.sessionbeans.SchoolFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.TimetableFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UniversityFacadeLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import cl.usach.pingeso.taa2.utils.General;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

/**
 *
 * @author Diego
 */
@Named(value = "schoolRegisterMB")
@RequestScoped
public class SchoolRegisterMB {
    @EJB
    private TimetableFacadeLocal timetableFacade;
    @EJB
    private UniversityFacadeLocal universityFacade;
    @EJB
    private SchoolFacadeLocal schoolFacade;
    private String schoolCode;
    private String schoolName;
    private String schoolAdress;
    private String schoolPhone;
    private String schoolEmail;
    private String schoolState="1";
    private String universityRut;
    private String timetableCode;
    private SelectItem[] universityOptions;
    private SelectItem[] timetableOptions;
    
    public SchoolRegisterMB() {
    }
    
    @PostConstruct
    public void init() {
        this.universityOptions = createFilterOptions();
        this.timetableOptions = createFilterTimetableOptions();
    }
    
    public void schoolRegisterNow() {
        try {
            List<School> tempSchool;
            tempSchool = schoolFacade.findByName(schoolName);
            boolean valid = true;
            for(int i=0; i < tempSchool.size();i++)
            {
                if(tempSchool.get(i).getRutUniversity().getRutUniversity().toString().compareTo(universityRut)==0)
                    valid = false;
            }
            if(!valid)
            {
                General.viewMessage(FacesMessage.SEVERITY_ERROR,
                        schoolName.concat(" ya existe en la universidad especificada."),
                        schoolName.concat(" ya existe en la universidad especificada."));
                General.goToPage("/faces/schoolRegister.xhtml?faces-redirect=true");
            }
            else
            {
                School newSchool;
                newSchool = new School();
                String[] schoolNameTokens = schoolName.split(" ");
                String[] dateNowTokens = new Date().toString().split(":");
                schoolCode = universityRut;
                for(int i=0;i<schoolNameTokens.length;i++)
                    schoolCode = schoolCode.concat(schoolNameTokens[i].trim());
                for(int i=0;i<dateNowTokens.length;i++)
                {
                    String[] dateNowSpaceTokens = dateNowTokens[i].split(" ");
                    for(int j=0;i<dateNowSpaceTokens.length;i++)
                        schoolCode = schoolCode.concat(dateNowSpaceTokens[i].trim());
                }
                newSchool.setSchoolCode(schoolCode);
                newSchool.setSchoolName(schoolName);
                newSchool.setSchoolAdress(schoolAdress);
                newSchool.setSchoolPhone(schoolPhone);
                newSchool.setSchoolEmail(schoolEmail);
                newSchool.setSchoolState(schoolState);
                if(timetableCode != null)
                    newSchool.setTimetableCode(timetableFacade.find(timetableCode));
                newSchool.setRutUniversity(universityFacade.find(universityRut));
                schoolFacade.create(newSchool);

                General.viewMessage(FacesMessage.SEVERITY_INFO,
                        schoolName.concat(" registrada satisfactoriamente !!!"),
                        schoolName.concat(" registrada satisfactoriamente !!!"));
                General.goToPage("/faces/schoolRegister.xhtml?faces-redirect=true");
            }
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/schoolRegister.xhtml?faces-redirect=true");
        }
    }
    
    public void backToSchools() {
        General.goToPage("/faces/schoolList.xhtml");
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAdress() {
        return schoolAdress;
    }

    public void setSchoolAdress(String schoolAdress) {
        this.schoolAdress = schoolAdress;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getSchoolState() {
        return schoolState;
    }

    public void setSchoolState(String schoolState) {
        this.schoolState = schoolState;
    }

    public String getUniversityRut() {
        return universityRut;
    }

    public void setUniversityRut(String universityRut) {
        this.universityRut = universityRut;
    }

    public String getTimetableCode() {
        return timetableCode;
    }

    public void setTimetableCode(String timetableCode) {
        this.timetableCode = timetableCode;
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

    public SelectItem[] getTimetableOptions() {
        return timetableOptions;
    }
}