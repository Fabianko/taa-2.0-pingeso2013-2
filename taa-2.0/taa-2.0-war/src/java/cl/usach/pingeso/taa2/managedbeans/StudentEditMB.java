/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.Student;
import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
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
@Named(value = "studentEditMB")
@RequestScoped
public class StudentEditMB {
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private ProgramFacadeLocal programFacade;
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
    private Integer present;
    private SelectItem[] programOptions;
    
    @PostConstruct
    public void init() { 
        this.programOptions = createFilterOptions();
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
        this.program = studentSelect.getProgramCode().getProgramName();
    }
    
    public void saveStudent(){
        mantStudent.endConversation();
        try {
            Student temp = new Student();
            User tempUser = new User();
            temp.setRut(rut);
            tempUser.setRut(rut);
            tempUser.setFirstName(firstName);
            tempUser.setMiddleName(middleName);
            tempUser.setPrimaryLastName(primaryLastName);
            tempUser.setSecondLastName(secondLastName);
            tempUser.setPassword(null);
            tempUser.setRol("Estudiante");
            tempUser.setUserState("1");
            tempUser.setEmail(mail);
            temp.setUser(tempUser);
            Program tempProgram = programFacade.find(program);
            temp.setProgramCode(tempProgram);
            studentFacade.edit(temp);
            userFacade.edit(tempUser);
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han modificado los datos del estudiante",
                    "Se han modificado los datos del estudiante \"".concat(rut).concat("\""));
            General.goToPage("/faces/studentList.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/studentEdit.xhtml?faces-redirect=true");
        }
    }
    
    public void backToStudents() {
        mantStudent.endConversation();
        General.goToPage("/faces/studentList.xhtml");
    }
    
    public StudentEditMB() {
    }
    
    public StudentConversation getMantStudent() {
        return mantStudent;
    }

    public void setMantStudent(StudentConversation manStudentX) {
        this.mantStudent = manStudentX;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPrimaryLastName() {
        return primaryLastName;
    }

    public void setPrimaryLastName(String primaryLastName) {
        this.primaryLastName = primaryLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
    
    private SelectItem[] createFilterOptions()  { 
        List<Program> programs = programFacade.findAll();
        SelectItem[] options = new SelectItem[programs.size() + 1];  
        options[0] = new SelectItem("", "Selecciona");  
        for(int i = 0; i < programs.size(); i++) {  
            options[i + 1] = new SelectItem(programs.get(i).getProgramCode(), programs.get(i).getProgramName());  
        }
        return options;  
    }
    
    public SelectItem[] getProgramOptions() {
        return programOptions;
    }

    public void setProgramOptions(SelectItem[] programOptions) {
        this.programOptions = programOptions;
    }

    public Integer getPresent() {
        return present;
    }

    public void setPresent(Integer present) {
        this.present = present;
    }
}