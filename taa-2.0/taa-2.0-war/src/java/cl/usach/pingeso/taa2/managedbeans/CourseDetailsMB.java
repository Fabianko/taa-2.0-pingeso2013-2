/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Assignment;
import cl.usach.pingeso.taa2.entityclasses.Course;
import cl.usach.pingeso.taa2.entityclasses.Teacher;
import cl.usach.pingeso.taa2.sessionbeans.AssignmentFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.CourseFacadeLocal;
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
@Named(value = "courseDetailsMB")
@RequestScoped
public class CourseDetailsMB {
    @EJB
    private AssignmentFacadeLocal assignmentFacade;
    @EJB
    private TeacherFacadeLocal teacherFacade;
    @EJB
    private CourseFacadeLocal courseFacade;
    @Inject 
    private CourseConversationMB mantCourse;
    private String code;
    private String name;
    private String room;
    private String teacher;
    private String workload;
    
    public CourseDetailsMB() {
    }
    
    @PostConstruct
    public void init() {
        if (mantCourse.getIdCourseDetails()!= null) {
            String codeCourse = mantCourse.getIdCourseDetails();
            loadCourse(codeCourse);
        }
        else {
            backToCourses();
        }
    }
    
    private void loadCourse(String courseCode) {
        Course courseSelect = courseFacade.find(courseCode);
        Assignment assignmentSelect = assignmentFacade.findByCourseCode(courseCode);
        Teacher teacherSelect = teacherFacade.find(assignmentSelect.getTeacher().getRut());
        if (courseSelect == null || teacherSelect == null) {
            backToCourses();
            return;
        }
        this.code = courseSelect.getCourseCode();
        this.name = courseSelect.getCourseName();
        this.room = courseSelect.getMainClassroom();
        this.teacher = teacherSelect.getUser().getFirstName() + " " + teacherSelect.getUser().getPrimaryLastName() + " (" + teacherSelect.getRut() + ")";
        //this.workload= courseSelect.getWorkload();
    }
    
    public void backToCourses() {
        mantCourse.endConversation();
        General.goToPage("/faces/classList.xhtml");
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

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
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
}