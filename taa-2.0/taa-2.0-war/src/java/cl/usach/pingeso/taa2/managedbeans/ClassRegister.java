/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Assignment;
import cl.usach.pingeso.taa2.entityclasses.AssignmentPK;
import cl.usach.pingeso.taa2.entityclasses.Course;
import cl.usach.pingeso.taa2.entityclasses.Teacher;
import cl.usach.pingeso.taa2.sessionbeans.AssignmentFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.CourseFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.TeacherFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

/**
 *
 * @author Nico
 */
@Named(value = "classRegister")
@RequestScoped
public class ClassRegister {
    
    @EJB
    private TeacherFacadeLocal teacherFacade;
    @EJB 
    private CourseFacadeLocal courseFacade;
    @EJB 
    private AssignmentFacadeLocal assignmentFacade;
    private String code;
    private String name;
    private String room;
    private String teacher;
    private SelectItem[] teacherOptions;
    
    public ClassRegister() {
    }
    
    @PostConstruct
    public void init() {
        this.teacherOptions = createFilterOptions();
    }
    
    public void backToCourses() {
        General.goToPage("/faces/classList.xhtml");
    }
    
    private SelectItem[] createFilterOptions()  { 
        List<Teacher> teachers = teacherFacade.findAll();
        SelectItem[] options = new SelectItem[teachers.size() + 1];  
        options[0] = new SelectItem("", "Selecciona");  
        for(int i = 0; i < teachers.size(); i++) {  
            options[i + 1] = new SelectItem(teachers.get(i).getRut(), teachers.get(i).getUser().getFirstName() + " " + teachers.get(i).getUser().getPrimaryLastName() + " (" + teachers.get(i).getRut() + ")");  
        }  
        return options;  
    }
    
    public void courseRegisterNow()
    {
        try
        {
            Course newCourse = new Course();
            newCourse.setCourseCode(code);
            newCourse.setCourseName(name);
            newCourse.setMainClassroom(null);
            newCourse.setCourseState("1");
            if(courseFacade.find(code) != null)
            {
                General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "Código de curso ya existe.",
                    "Código de curso ya existe.");
                General.goToPage("/faces/classRegister.xhtml?faces-redirect=true");
            }
            else
            {
                if(courseFacade.findByCourseName(name) != null)
                {
                    General.viewMessage(FacesMessage.SEVERITY_ERROR,
                        "Nombre de curso ya existe.",
                        "Nombre de curso ya existe.");
                    General.goToPage("/faces/classRegister.xhtml?faces-redirect=true");
                }
                else
                {
                    courseFacade.create(newCourse);
                    courseRegisterNow1(newCourse);
                    General.viewMessage(FacesMessage.SEVERITY_INFO,
                        "Curso registrado satisfactoriamente!!!",
                        "Curso registrado satisfactoriamente!!!");
                    General.goToPage("/faces/classRegister.xhtml?faces-redirect=true");
                }
            }
        } 
        catch(Exception e)
        {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
            e.getMessage(), 
            e.getMessage());
            General.goToPage("/faces/classRegister.xhtml?faces-redirect=true");
        }
    }
    
    public void courseRegisterNow1(Course c)
    {
        Teacher findTeacher = teacherFacade.find(teacher);
        Course findCourse = courseFacade.find(c.getCourseCode());
        AssignmentPK tempPK = new AssignmentPK();
        tempPK.setCourseCode(findCourse.getCourseCode());
        tempPK.setRut(findTeacher.getRut());
        Assignment temp = new Assignment();
        temp.setAssignmentPK(tempPK);
        temp.setAssignmentState("1");
        temp.setTeacher(findTeacher);
        temp.setCourse(findCourse);
        assignmentFacade.create(temp);
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }
    
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    
    public SelectItem[] getTeacherOptions() {
        return teacherOptions;
    }
    
    public void setTeacherOptions(SelectItem[] teacherOptions) {
        this.teacherOptions = teacherOptions;
    }
}