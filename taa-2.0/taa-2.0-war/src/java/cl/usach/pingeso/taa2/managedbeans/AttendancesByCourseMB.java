/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.sessionbeans.AttendanceFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "attendancesByCourseMB")
@RequestScoped
public class AttendancesByCourseMB {
    @EJB
    private AttendanceFacadeLocal attendanceFacade;
    @Inject 
    private CourseConversationMB mantCourse;
    private List<Date> listAttendances;
    private List<Date> attendancesFiltered;
    private Date dateAttendance;
    private Integer presents;
    private Integer noPresents;
    
    @PostConstruct
    public void init() {
        if (mantCourse.getIdCourseDetails()!= null) {
            String course = mantCourse.getIdCourseDetails();
            loadDatesCourse(course);
        }
        else {
            backToCourses();
        }
    }
    
    private void loadDatesCourse(String course) {
        listAttendances = attendanceFacade.findAllDatesByCourse(course);
    }
    
    public void backToCourses() {
        mantCourse.endConversation();
        General.goToPage("/faces/classList.xhtml");
    }
    
    public AttendancesByCourseMB() {
    }
    
    public void editAttendance(Date dateAttendance) {
        String course = mantCourse.getIdCourseDetails();
        General.goToPage("/faces/editAttendanceCourse.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
    }
    
    public void deleteAttendance(Date attendanceDate) {
        String course = mantCourse.getIdCourseDetails();
        boolean resultado;
        try
        {
            System.out.println(attendanceDate);
            attendanceFacade.removeAll(attendanceDate, course);
            resultado = true;
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Toma de asistencia eliminada satisfactoriamente !!!",
                    "Toma de asistencia eliminada satisfactoriamente !!!");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No es posible eliminar la toma de asistencia.",
                    "No es posible eliminar la toma de asistencia.");
        }
        General.goToPage("/faces/totalAttendancesCourse.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
    }

    public List<Date> getListAttendances() {
        return listAttendances;
    }

    public void setListAttendances(List<Date> listAttendances) {
        this.listAttendances = listAttendances;
    }

    public List<Date> getAttendancesFiltered() {
        return attendancesFiltered;
    }

    public void setAttendancesFiltered(List<Date> attendancesFiltered) {
        this.attendancesFiltered = attendancesFiltered;
    }

    public Date getDateAttendance() {
        return dateAttendance;
    }

    public void setDateAttendance(Date dateAttendance) {
        this.dateAttendance = dateAttendance;
    }

    public Integer getPresents() {
        return presents;
    }

    public void setPresents(Integer presents) {
        this.presents = presents;
    }

    public Integer getNoPresents() {
        return noPresents;
    }

    public void setNoPresents(Integer noPresents) {
        this.noPresents = noPresents;
    }
}