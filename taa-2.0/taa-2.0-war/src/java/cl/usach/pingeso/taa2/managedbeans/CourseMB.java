package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Assignment;
import cl.usach.pingeso.taa2.entityclasses.Course;
import cl.usach.pingeso.taa2.managedbeans.login.SessionValidator;
import cl.usach.pingeso.taa2.sessionbeans.AssignmentFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.AttendanceFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.CourseFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.MembershipFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;  
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "courseMB")
@RequestScoped
public class CourseMB implements Serializable {
    @EJB
    private AttendanceFacadeLocal attendanceFacade;
    @EJB
    private MembershipFacadeLocal membershipFacade;
    @EJB
    private AssignmentFacadeLocal assignmentFacade;
    @EJB
    private CourseFacadeLocal courseFacade;
    private List<Course> courseList;
    private List<Course> courseFiltered;
    private boolean showcolumn;
    private boolean showBtnAdd;
    @Inject 
    private CourseConversationMB mantCourse;
    @Inject 
    private UserConversationMB infoUser;
    @Inject private SessionValidator session;
    @Inject 
    private AttendanceConversationMB mantAttendance;
    
    public CourseMB() {
    }
    
    @PostConstruct
    public void init() {
        if(General.isUserInRole("Administrador")){
            this.courseList = courseFacade.findAll();
        }
        else{
            this.courseList= courseFacade.findByRut(session.userRut());
        }
        this.showcolumn = General.isUserInRole("Profesor");
        this.showBtnAdd = General.isUserInRole("Administrador");
    }
    
    public List<Course> getCourseList() {
        return courseList;
    }
    
    public void setCourseList(List<Course> list) {
        this.courseList = list;
    }

    public List<Course> getCourseFiltered() {
        return courseFiltered;
    }

    public void setCourseFiltered(List<Course> courseFiltered) {
        this.courseFiltered = courseFiltered;
    }
    
    public void viewDetailsCourse(String courseCode) {
        Course courseViewDetails = courseFacade.find(courseCode);
        if (courseViewDetails != null) {
            this.mantCourse.beginConversation();
            this.mantCourse.setIdCourseDetails(courseCode);
            General.goToPage("/faces/viewDetailsCourse.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
        }
        else {
            this.mantCourse.clean();
            General.goToPage("/faces/classList.xhtml");
        }
    }
    
    public void addCourse() {
       General.goToPage("/faces/classRegister.xhtml");   
    }
    
    public void editCourse(String courseCode) {
        Course courseEdit = courseFacade.find(courseCode);
        if (courseEdit != null) {
            this.mantCourse.beginConversation();
            this.mantCourse.setIdCourseDetails(courseCode);
            General.goToPage("/faces/courseEdit.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
        }
        else {
            this.mantCourse.clean();
            General.goToPage("/faces/classList.xhtml");
        }
    }
     
    public void deleteCourse(String courseCode) {
        Course temp = new Course();
        temp.setCourseCode(courseCode);
        boolean resultado;
        try
        {
            if(membershipFacade.findByCourseCode(courseCode)==null && attendanceFacade.findByCourseCode(courseCode)==null)
            {
                Assignment tempAssignment = assignmentFacade.findByCourseCode(courseCode);
                assignmentFacade.remove(tempAssignment);
                courseFacade.remove(temp);
                resultado = true;
            }
            else
            {
                resultado = false;
            }
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha eliminado el curso",
                    "Se ha eliminado correctamente el curso ");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No es posible eliminar el curso mientras tenga alumnos inscritos y/o asistencia asociada.",
                    "No es posible eliminar el curso mientras tenga alumnos inscritos y/o asistencia asociada.");
        }
        General.goToPage("/faces/classList.xhtml?faces-redirect=true");
    }
    
    public void backToCourses() {
       General.goToPage("/faces/classList.xhtml");    
    }
    
    public void studentsCourse(String courseCode) {
        Course course = courseFacade.find(courseCode);
        if (course != null) {
            this.mantCourse.beginConversation();
            this.mantCourse.setIdCourseDetails(courseCode);
            General.goToPage("/faces/studentsCourse.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
        }
        else {
            this.mantCourse.clean();
            General.goToPage("/faces/classList.xhtml");
        }
    }
    
    public void attendanceCourse(String courseCode) {
        Course courseViewDetails = courseFacade.find(courseCode);
        if (courseViewDetails != null) {
            this.mantCourse.beginConversation();
            this.mantCourse.setIdCourseDetails(courseCode);
            General.goToPage("/faces/attendanceCourseOptions.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
        }
        else {
            this.mantCourse.clean();
            General.goToPage("/faces/classList.xhtml");
        }
    }
    
    public void totalAttendancesCourse(String courseCode) {
        Course courseViewDetails = courseFacade.find(courseCode);
        if (courseViewDetails != null) {
            this.mantCourse.beginConversation();
            this.mantCourse.setIdCourseDetails(courseCode);
            General.goToPage("/faces/totalAttendancesCourse.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
        }
        else {
            this.mantCourse.clean();
            General.goToPage("/faces/classList.xhtml");
        }
    }

    public boolean isShowcolumn() {
        return showcolumn;
    }

    public void setShowcolumn(boolean showcolumn) {
        this.showcolumn = showcolumn;
    }
    
    public void attendanceCourseByFace(String courseCode) {
        Course courseViewDetails = courseFacade.find(courseCode);
        if (courseViewDetails != null) {
            this.mantCourse.beginConversation();
            this.mantCourse.setIdCourseDetails(courseCode);
            this.mantAttendance.beginConversation();
            this.mantAttendance.setIdAttendanceDetails(new Date());
            General.goToPage("/faces/attendanceCourseByFace.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
        }
        else {
            this.mantCourse.clean();
            this.mantAttendance.clean();
            General.goToPage("/faces/classList.xhtml");
        }
    }
    
    public void attendanceWorkload(String courseCode){
         Course courseViewDetails = courseFacade.find(courseCode);
        if (courseViewDetails != null) {
            this.mantCourse.beginConversation();
            this.mantCourse.setIdCourseDetails(courseCode);
            General.goToPage("/faces/workloadCourse.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
        }        
        else {
            this.mantCourse.clean();
            General.goToPage("/faces/classList.xhtml");
        }
    }

    public boolean isShowBtnAdd() {
        return showBtnAdd;
    }

    public void setShowBtnAdd(boolean showBtnAdd) {
        this.showBtnAdd = showBtnAdd;
    }
}