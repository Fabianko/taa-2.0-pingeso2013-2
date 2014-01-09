/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Teacher;
import cl.usach.pingeso.taa2.sessionbeans.TeacherFacadeLocal;
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
@Named(value = "teacherDetailsMB")
@RequestScoped
public class TeacherDetailsMB {

    @EJB
    private TeacherFacadeLocal teacherFacade;
    @Inject 
    private TeacherConversationMB mantTeacher;
    
    private String rut;
    private String firstName;
    private String middleName;
    private String primaryLastName;
    private String secondLastName;
    private String mail;
    
    public TeacherDetailsMB() {
    }
    
    @PostConstruct
    public void init() {
        if (mantTeacher.getIdUserDetails()!= null) {
            String rutTeacher = mantTeacher.getIdUserDetails();
            loadTeacher(rutTeacher);
        }
        else {
            backToTeachers();
        }
    }
    
    private void loadTeacher(String rutTeacher) {
        Teacher teacherSelect = teacherFacade.find(rutTeacher);
        if (teacherSelect == null) {
            backToTeachers();
            return;
        }
        this.rut = teacherSelect.getRut();
        this.firstName = teacherSelect.getUser().getFirstName();
        this.middleName = teacherSelect.getUser().getMiddleName();
        this.primaryLastName = teacherSelect.getUser().getPrimaryLastName();
        this.secondLastName = teacherSelect.getUser().getSecondLastName();
        this.mail = teacherSelect.getUser().getEmail();
    }
    
    public void backToTeachers() {
        mantTeacher.endConversation();
        General.goToPage("/faces/teacherList.xhtml");
    }

    public String getRut() {
        return rut;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getMiddleName() {
        if(middleName == null || "".equals(middleName))
            middleName = "No asignado";
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPrimaryLastName() {
        return primaryLastName;
    }

    public void setPrimaryLastName(String primaryLastName) {
        this.primaryLastName = primaryLastName;
    }

    public String getSecondLastName() {
        if(secondLastName == null || "".equals(secondLastName))
            secondLastName = "No asignado";
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}