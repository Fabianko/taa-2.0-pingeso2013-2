/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.School;
import cl.usach.pingeso.taa2.sessionbeans.SchoolFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "schoolDetailsMB")
@RequestScoped
public class SchoolDetailsMB {

    @EJB
    private SchoolFacadeLocal schoolFacade;
    @Inject 
    private SchoolConversationMB mantSchool;
    private String schoolCode;
    private String schoolName;
    private String schoolAdress;
    private String schoolEmail;
    private String schoolPhone;
    private String university;
    private Collection<Program> programsSchool;
    private String timetable;
    
    public SchoolDetailsMB() {
    }
    
    @PostConstruct
    public void init() {
        if (mantSchool.getIdSchoolDetails()!= null) {
            String school = mantSchool.getIdSchoolDetails();
            loadSchool(school);
        }
        else {
            backToSchools();
        }
    }
    
    private void loadSchool(String schoolCode) {
        School schoolSelect = schoolFacade.find(schoolCode);
        if (schoolSelect == null) {
            backToSchools();
            return;
        }
        this.schoolCode = schoolSelect.getSchoolCode();
        this.schoolName = schoolSelect.getSchoolName();
        this.schoolEmail = schoolSelect.getSchoolEmail();
        this.schoolAdress = schoolSelect.getSchoolAdress();
        this.schoolPhone = schoolSelect.getSchoolPhone();
        this.university = schoolSelect.getRutUniversity().getUniversityName().concat(" (Rut: ").concat(schoolSelect.getRutUniversity().getRutUniversity()).concat(")");
        if(schoolSelect.getTimetableCode()!=null)
            this.timetable = schoolSelect.getTimetableCode().getTimetableName().concat(" (Código: ").concat(schoolSelect.getTimetableCode().getTimetableCode()).concat(")");
        this.programsSchool = (List<Program>)schoolSelect.getProgramCollection();
    }
    
    public void backToSchools() {
        mantSchool.endConversation();
        General.goToPage("/faces/schoolList.xhtml");
    }

    public SchoolConversationMB getMantSchool() {
        return mantSchool;
    }

    public void setMantSchool(SchoolConversationMB mantSchool) {
        this.mantSchool = mantSchool;
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
        if(schoolAdress == null || "".equals(schoolAdress))
            schoolAdress = "No asignado";
        return schoolAdress;
    }

    public void setSchoolAdress(String schoolAdress) {
        this.schoolAdress = schoolAdress;
    }

    public String getSchoolEmail() {
        if(schoolEmail == null || "".equals(schoolEmail))
            schoolEmail = "No asignado";
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getSchoolPhone() {
        if(schoolPhone == null || "".equals(schoolPhone))
            schoolPhone = "No asignado";
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }
    
     public Collection<Program> getProgramsSchool() {
        return programsSchool;
    }
     
    public void setProgramsSchool(Collection<Program> programsSchool) {
        this.programsSchool = programsSchool;
    }
    
    public String getUniversity() {
        return university;
    }
    
    public void setUniversity(String university) {
        this.university = university;
    }

    public String getTimetable() {
        if(timetable == null || "".equals(timetable))
            timetable = "No asignado";
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }
}