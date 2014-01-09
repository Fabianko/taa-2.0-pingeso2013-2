/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Attendance;
import cl.usach.pingeso.taa2.entityclasses.AttendancePK;
import cl.usach.pingeso.taa2.entityclasses.Course;
import cl.usach.pingeso.taa2.entityclasses.Membership;
import cl.usach.pingeso.taa2.entityclasses.MembershipPK;
import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.Student;
import cl.usach.pingeso.taa2.sessionbeans.AttendanceFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.CourseFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.MembershipFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import cl.usach.pingeso.taa2.utils.StudentDataModel;
import com.sun.faces.component.visit.FullVisitContext;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "courseStudentsMB")
@RequestScoped
public class CourseStudentsMB {
    @EJB
    private AttendanceFacadeLocal attendanceFacade;
    @EJB
    private CourseFacadeLocal courseFacade;
    @EJB
    private MembershipFacadeLocal membershipFacade;
    @EJB
    private StudentFacadeLocal studentFacade;
    @EJB
    private ProgramFacadeLocal programFacade;
    private List<Student> courseFiltered;
    @Inject 
    private CourseConversationMB mantCourse;
    private SelectItem[] programOptions;
    private Date date1 = new Date();
    private Integer stateAttendance = 0;
    private List<Attendance> attendances;
    private Student[] selectedStudent;
    private StudentDataModel studentsModel;
    
    @PostConstruct
    public void init() {
        if (mantCourse.getIdCourseDetails()!= null) {
            String course = mantCourse.getIdCourseDetails();
            loadStudentsCourse(course);
        }
        else {
            backToCourses();
        }
        this.programOptions = createFilterOptions();
    }
    
    private void loadStudentsCourse(String course) {
        List<Student> students = studentFacade.findByCourse(course);
        if (students == null) {
            backToCourses();
            return;
        }
        this.studentsModel = new StudentDataModel(students);
    }
    
    private SelectItem[] createFilterOptions()  { 
        List<Program> programs = programFacade.findAll();
        SelectItem[] options = new SelectItem[programs.size() + 1];  
        options[0] = new SelectItem("", "Selecciona");  
        for(int i = 0; i < programs.size(); i++) {  
            options[i + 1] = new SelectItem(programs.get(i).getProgramName(), programs.get(i).getProgramName());  
        }  
        return options;  
    }
    
    public void backToCourses() {
        mantCourse.endConversation();
        General.goToPage("/faces/classList.xhtml");
    }
      
    public CourseStudentsMB() {
    }
    
    public CourseConversationMB getMantCourse() {
        return mantCourse;
    }

    public void setMantCourse(CourseConversationMB manCourseX) {
        this.mantCourse = manCourseX;
    }
    
    public List<Student> getCourseFiltered() {
        return courseFiltered;
    }

    public void setCourseFiltered(List<Student> courseFiltered) {
        this.courseFiltered = courseFiltered;
    }

    public SelectItem[] getProgramOptions() {
        return programOptions;
    }

    public void setProgramOptions(SelectItem[] programOptions) {
        this.programOptions = programOptions;
    }
    
    public void deleteCourseStudent(String rutStudent) {
        String course = mantCourse.getIdCourseDetails();
        boolean resultado;
        try
        {
            System.out.println(course);
            MembershipPK temp = new MembershipPK();
            temp.setRut(rutStudent);
            temp.setCourseCode(course);
            Membership tempMembership = membershipFacade.find(temp);
            membershipFacade.remove(tempMembership);
            resultado = true;
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Estudiante eliminado satisfactoriamente de este curso !!!",
                    "Estudiante eliminado satisfactoriamente de este curso !!!");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No es posible eliminar al estudiante del curso mientras haya información asociada a este.",
                    "No es posible eliminar al estudiante del curso mientras haya información asociada a este.");
        }
        General.goToPage("/faces/studentsCourse.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
    }
    
    public void addCourseStudent(String courseCode) {
        Course courseDetails = courseFacade.find(courseCode);
        if (courseDetails != null) {
            this.mantCourse.beginConversation();
            this.mantCourse.setIdCourseDetails(courseCode);
            General.goToPage("/faces/studentCourseRegister.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
        }
        else {
            this.mantCourse.clean();
            General.goToPage("/faces/classList.xhtml");
        }  
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Integer getStateAttendance() {
        return stateAttendance;
    }
    
    public void setStateAttendance(Integer attendance) {
        this.stateAttendance = attendance;
    }
    
    public void changeState(String rutStudent) {
        System.out.println(this.stateAttendance);
    }
    
    public void registerAttendanceNow() {
        boolean back = true;
        try
        {
            if(selectedStudent.length == 0)
            {
                back = false;
                General.viewMessage(FacesMessage.SEVERITY_ERROR,
                "Para guardar la asistencia debe haber al menos un estudiante presente.",
                "Para guardar la asistencia debe haber al menos un estudiante presente.");
            }
            else
            {
                Attendance newAttendance = new Attendance();
                AttendancePK newAttendancePK = new AttendancePK();
                for(int i = 0; i<selectedStudent.length;i++)
                {
                    newAttendancePK.setRut(selectedStudent[i].getRut());
                    newAttendancePK.setCourseCode(mantCourse.getIdCourseDetails());
                    newAttendancePK.setAttendanceDate(date1);
                    newAttendance.setAttendanceState("1");
                    newAttendance.setPresent("1");
                    newAttendance.setCourse(courseFacade.find(mantCourse.getIdCourseDetails()));
                    newAttendance.setStudent(selectedStudent[i]);
                    newAttendance.setAttendancePK(newAttendancePK);
                    attendanceFacade.create(newAttendance);
                }
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Asistencia guardada satisfactoriamente !!!",
                    "Asistencia guardada satisfactoriamente !!!");
            }
        }
        catch(Exception e)
        {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                "No es posible guardar la asistencia en estos momentos. Inténtalo más tarde.",
                "No es posible guardar la asistencia en estos momentos. Inténtalo más tarde.");
        }
        if(back)
            General.goToPage("/faces/classList.xhtml?faces-redirect=true");
        else
            General.goToPage("/faces/attendanceCourseOptions.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
    }
    
    public UIComponent findComponent(final String id){
        FacesContext context = FacesContext.getCurrentInstance(); 
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];
        root.visitTree(new FullVisitContext(context), new VisitCallback() {     
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if(component.getId().equals(id)){
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;              
            }
        });
        return found[0];
    }
    
    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Student[] getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student[] selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public StudentDataModel getStudentsModel() {
        return studentsModel;
    }

    public void setStudentsModel(StudentDataModel studentsModel) {
        this.studentsModel = studentsModel;
    }
}