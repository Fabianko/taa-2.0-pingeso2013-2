/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Team;
import cl.usach.pingeso.taa2.entityclasses.Student;
import cl.usach.pingeso.taa2.sessionbeans.TeamFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
@Named(value = "studentTeamMB")
@RequestScoped
public class StudentTeamMB {
    @EJB
    private StudentFacadeLocal studentFacade;
    @EJB
    private TeamFacadeLocal teamFacade;
    @Inject 
    private StudentConversation mantStudent;
    private SelectItem[] teamsOptions;
    private String rut;
    private String fullName;
    private String teamCurrentStudent;
    private String rolCurrentStudent;
  
    public StudentTeamMB() {
    }
    
    @PostConstruct
    public void init() {
        this.teamsOptions = createFilterOptions();
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
        this.fullName = studentSelect.getUser().getFirstName().concat(" ").concat(studentSelect.getUser().getPrimaryLastName()).concat(" ").concat(studentSelect.getUser().getSecondLastName());
        //this.rolCurrentStudent = studentSelect.getRolTeam();
    }
    
    public void backToStudents() {
       General.goToPage("/faces/studentList.xhtml");
       
    }
    
    private SelectItem[] createFilterOptions()  { 
        List<Team> teams = teamFacade.findAll();
        SelectItem[] options = new SelectItem[teams.size() + 1];  
        options[0] = new SelectItem("", "Selecciona");  
        for(int i = 0; i < teams.size(); i++) {
            options[i + 1] = new SelectItem(teams.get(i).getTeamId(), teams.get(i).getTeamName());  
        }  
        return options;  
    }
    
    public SelectItem[] getTeamsOptions() {  
        return teamsOptions;  
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTeamCurrentStudent() {
        return teamCurrentStudent;
    }

    public void setTeamCurrentStudent(String teamCurrentStudent) {
        this.teamCurrentStudent = teamCurrentStudent;
    }

    public String getRolCurrentStudent() {
        return rolCurrentStudent;
    }

    public void setRolCurrentStudent(String rolCurrentStudent) {
        this.rolCurrentStudent = rolCurrentStudent;
    }
    
    public void teamStudentRegisterNow()
    {
        Student temp = studentFacade.find(rut);
        //temp.setTeamId(teamFacade.find(Long.parseLong(teamCurrentStudent)));
        //temp.setRolTeam(rolCurrentStudent);
        studentFacade.edit(temp);
        General.viewMessage(FacesMessage.SEVERITY_INFO,
            "Asignación de grupo y rol guardada satisfactoriamente !!!",
            "Asignación de grupo y rol guardada satisfactoriamente !!!");
    }
}
