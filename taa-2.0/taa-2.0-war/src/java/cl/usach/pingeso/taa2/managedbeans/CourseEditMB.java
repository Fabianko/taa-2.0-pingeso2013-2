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
@Named(value = "courseEditMB")
@RequestScoped
public class CourseEditMB {
    @EJB
    private AssignmentFacadeLocal assignmentFacade;
    @EJB
    private TeacherFacadeLocal teacherFacade;
    @EJB
    private CourseFacadeLocal courseFacade;
    @Inject
    private CourseConversationMB mantCourse;
    private String courseCode;
    private String courseName;
    private String teacher;
    private SelectItem[] teacherOptions;
    
    @PostConstruct
    public void init() { 
        if (mantCourse.getIdCourseDetails()!= null) {
            String course = mantCourse.getIdCourseDetails();
            loadCourse(course);
        }
        else {
            //MOSTRAR ERROR
            backToCourses();
        }
        this.teacherOptions = createFilterOptions();
    }
    
    private void loadCourse(String course) {
        Course courseSelect = courseFacade.find(course);
        if (courseSelect == null) {
            backToCourses();
            return;
        }
        this.courseCode = courseSelect.getCourseCode();
        this.courseName = courseSelect.getCourseName();
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
    
    public void saveCourse(){
        mantCourse.endConversation();
        try {
            Course newCourse = new Course();
            newCourse.setCourseCode(courseCode);
            newCourse.setCourseName(courseName);
            newCourse.setCourseState("1");
            courseFacade.edit(newCourse);
            saveCourse1(newCourse);
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han modificado los datos del curso",
                    "Se han modificado los datos del curso \"".concat(courseCode).concat("\""));
            General.goToPage("/faces/classList.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/studentEdit.xhtml?faces-redirect=true");
        }
    }
    
    public void backToCourses() {
        mantCourse.endConversation();
        General.goToPage("/faces/classList.xhtml");
    }
    
    public void saveCourse1(Course c)
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
        assignmentFacade.remove(assignmentFacade.findByCourseCode(courseCode));
        assignmentFacade.create(temp);
    }
    
    public CourseEditMB() {
    }
    
    public CourseConversationMB getMantCourse() {
        return mantCourse;
    }

    public void setMantCourse(CourseConversationMB manCourseX) {
        this.mantCourse = manCourseX;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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