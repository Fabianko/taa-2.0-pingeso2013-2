package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Teacher;
import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.sessionbeans.TeacherFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;  
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "teacherMB")
@RequestScoped
public class TeacherMB implements Serializable {
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private TeacherFacadeLocal teacherFacade;
    private List<Teacher> list;
    private List<Teacher> filteredTeachers;
    @Inject 
    private TeacherConversationMB mantTeacher;
    
    public TeacherMB() {
    }
    
    @PostConstruct
    public void init() {        
        //this.list = teacherFacade.findAll();
    }
    
    public List<Teacher> getList() {
        return list;
    }
    
    public void setList(List<Teacher> newList) {
        this.list = newList;
    }

    public List<Teacher> getFilteredTeachers() {
        return filteredTeachers;
    }

    public void setFilteredTeachers(List<Teacher> filteredTeachers) {
        this.filteredTeachers = filteredTeachers;
    }
    
    public void viewDetailsTeacher(String rutTeacher) {
        Teacher userViewDetails = teacherFacade.find(rutTeacher);
        if (userViewDetails != null) {
            this.mantTeacher.beginConversation();
            this.mantTeacher.setIdUserDetails(rutTeacher);
            General.goToPage("/faces/viewDetailsTeacher.xhtml?cid=".concat(this.mantTeacher.getConversation().getId()));
        }
        else {
            this.mantTeacher.clean();
            General.goToPage("/faces/teacherList.xhtml");
        }
    }
    
    public void add() {
       General.goToPage("/faces/teacherRegister.xhtml");   
    }
    
    public void edit(String rutTeacher) {
        Teacher teacherEdit = teacherFacade.find(rutTeacher);
        if (teacherEdit != null) {
            this.mantTeacher.beginConversation();
            this.mantTeacher.setIdUserDetails(rutTeacher);
            General.goToPage("/faces/editTeacher.xhtml?cid=".concat(this.mantTeacher.getConversation().getId()));
        }
        else {
            this.mantTeacher.clean();
            General.goToPage("/faces/teacherList.xhtml");
        }
    }
     
    public void delete(String rutTeacher) {
        Teacher temp = new Teacher();
        User tempUser = userFacade.find(rutTeacher);
        temp.setRut(rutTeacher);
        boolean resultado;
        try
        {
            teacherFacade.remove(temp);
            userFacade.remove(tempUser);
            resultado = true;
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Profesor eliminado satisfactoriamente !!!",
                    "Profesor eliminado satisfactoriamente !!!");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No se puede eliminar al profesor mientras tenga cursos asignados.",
                    "No se puede eliminar al profesor mientras tenga cursos asignados.");
        }
        General.goToPage("/faces/teacherList.xhtml?faces-redirect=true");
    }
    
    public void backToTeachers() {
       General.goToPage("/faces/teacherList.xhtml"); 
    }
}