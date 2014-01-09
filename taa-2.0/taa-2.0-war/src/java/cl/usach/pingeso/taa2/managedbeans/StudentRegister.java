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
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import cl.usach.pingeso.taa2.utils.General;
import java.io.File;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

/**
 *
 * @author Diego
 */
@Named(value = "studentRegister")
@RequestScoped
public class StudentRegister {
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private StudentFacadeLocal studentFacade;
    @EJB
    private ProgramFacadeLocal programFacade;
    private SelectItem[] programOptions;
    private String rut;
    private String name1;
    private String name2;
    private String lastNameP;
    private String lastNameM;
    private String email;
    private String rol="Estudiante";
    private String state="1";
    private String program;
    private String pathFace;
    
    public StudentRegister() {
    }
    
    @PostConstruct
    public void init() {
        this.programOptions = createFilterOptions();
    }
    
    public void studentRegisterNow() {
        try {
            User newUser = new User();
            newUser.setRut(rut);
            newUser.setFirstName(name1);
            newUser.setMiddleName(name2);
            newUser.setPrimaryLastName(lastNameP);
            newUser.setSecondLastName(lastNameM);
            newUser.setEmail(email);
            newUser.setPassword(null);
            newUser.setUserState(state);
            newUser.setRol(rol);
            File folder = new File("C:\\photos_taa\\real\\"+rut);
            folder.mkdirs();
            userFacade.create(newUser);
            studentRegisterNow1(newUser);
            
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Alumno registrado satisfactoriamente!!!",
                    "Alumno registrado satisfactoriamente!!!");
            General.goToPage("/faces/studentRegister.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/studentRegister.xhtml?faces-redirect=true");
        }
    }
    
    public void studentRegisterNow1(User u){
            
            Student newStudent=new Student();
            newStudent.setRut(u.getRut());
            newStudent.setUser(u);
            Program newProgram = programFacade.find(program);
            newStudent.setProgramCode(newProgram);
            studentFacade.create(newStudent);
    }
    
    public StudentFacadeLocal getStudentFacade() {
        return studentFacade;
    }

    public void setStudentFacade(StudentFacadeLocal studentFacade) {
        this.studentFacade = studentFacade;
    }

    public UserFacadeLocal getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacadeLocal userFacade) {
        this.userFacade = userFacade;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }
    
    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getLastNameP() {
        return lastNameP;
    }

    public void setLastNameP(String lastNameP) {
        this.lastNameP = lastNameP;
    }

    public String getLastNameM() {
        return lastNameM;
    }

    public void setLastNameM(String lastNameM) {
        this.lastNameM = lastNameM;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void backToStudents() {
        General.goToPage("/faces/studentList.xhtml");
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

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getPathFace() {
        return pathFace;
    }

    public void setPathFace(String pathFace) {
        this.pathFace = pathFace;
    }
}