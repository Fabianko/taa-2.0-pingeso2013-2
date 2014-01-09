/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Student;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
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
@Named(value = "studentDetailsMB")
@RequestScoped
public class StudentDetailsMB {

    @EJB
    private StudentFacadeLocal studentFacade;
    @Inject 
    private StudentConversation mantStudent;
    
    private String rut;
    private String firstName;
    private String middleName;
    private String primaryLastName;
    private String secondLastName;
    private String mail;
    private String program;
    
    public StudentDetailsMB() {
    }
    
    @PostConstruct
    public void init() {
        if (mantStudent.getIdUserDetails()!= null) {
            String rutStudent = mantStudent.getIdUserDetails();
            loadStudent(rutStudent);
        }
        else {
            backToStudents();
        }
    }
    
    private void loadStudent(String rutStudent) {
        Student studentSelect = studentFacade.find(rutStudent);
        if (studentSelect == null) {
            backToStudents();
            return;
        }
        this.rut = studentSelect.getRut();
        this.firstName = studentSelect.getUser().getFirstName();
        this.middleName = studentSelect.getUser().getMiddleName();
        this.primaryLastName = studentSelect.getUser().getPrimaryLastName();
        this.secondLastName = studentSelect.getUser().getSecondLastName();
        this.mail = studentSelect.getUser().getEmail();
        this.program = studentSelect.getProgramCode().getProgramName() + " (Código: " + studentSelect.getProgramCode().getProgramCode() + ")";
    }
    
    public void backToStudents() {
        mantStudent.endConversation();
        General.goToPage("/faces/studentList.xhtml");
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

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}