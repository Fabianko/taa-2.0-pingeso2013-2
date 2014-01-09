package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Membership;
import cl.usach.pingeso.taa2.entityclasses.MembershipPK;
import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.Student;
import cl.usach.pingeso.taa2.sessionbeans.MembershipFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.CourseFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;  
import java.util.Date;
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
@Named(value = "studentsSelectMB")
@RequestScoped
public class StudentsSelectMB implements Serializable {
    @EJB
    private MembershipFacadeLocal membershipFacade;
    @EJB
    private ProgramFacadeLocal programFacade;
    @EJB
    private CourseFacadeLocal courseFacade;
    @EJB
    private StudentFacadeLocal studentFacade;
    private List<Student> lista;
    private List<Student> filteredStudents;
    @Inject 
    private StudentConversation mantStudent;
    @Inject 
    private CourseConversationMB mantCourse;
    private SelectItem[] programOptions;
    
    public StudentsSelectMB() {
    }
    
    @PostConstruct
    public void init() {
        System.out.println(mantCourse.getIdCourseDetails());
        this.lista = studentFacade.findByCourseComplement(mantCourse.getIdCourseDetails());
        this.programOptions = createFilterOptions();
    }
    
    public List<Student> getLista() {
        return lista;
    }
    
    public void setLista(List<Student> lista) {
        this.lista = lista;
    }

    public List<Student> getFilteredStudents() {
        return filteredStudents;
    }

    public void setFilteredStudents(List<Student> filteredStudents) {
        this.filteredStudents = filteredStudents;
    }
    
    public void viewDetails(String rutStudent) {
        Student userViewDetails = studentFacade.find(rutStudent);
        if (userViewDetails != null) {
            this.mantStudent.beginConversation();
            this.mantStudent.setIdUserDetails(rutStudent);
            General.goToPage("/faces/viewDetailsStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
        }
        else {
            this.mantStudent.clean();
            General.goToPage("/faces/studentList.xhtml");
        }
    }
    
    public void addStudentToCourse(String rutStudent, String courseCode) {
       MembershipPK newMembershipPK = new MembershipPK();
       newMembershipPK.setCourseCode(courseCode);
       newMembershipPK.setRut(rutStudent);
       if(membershipFacade.find(newMembershipPK) == null)
       {
           Membership newMembership = new Membership();
           newMembership.setMembershipPK(newMembershipPK);
           newMembership.setCourse(courseFacade.find(courseCode));
           newMembership.setStudent(studentFacade.find(rutStudent));
           newMembership.setMembershipState("1");
           newMembership.setMembershipDate(new Date());
           membershipFacade.create(newMembership);
       }
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Estudiante ".concat(rutStudent).concat(" agregado satisfactoriamente al curso ").concat(courseCode),
                "Estudiante ".concat(rutStudent).concat(" agregado satisfactoriamente al curso ").concat(courseCode));
            General.goToPage("/faces/studentCourseRegister.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
    }
    
    public void backToStudentsCourse() {
       General.goToPage("/faces/studentsCourse.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
       
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
    
    public SelectItem[] getProgramOptions() {  
        return programOptions;  
    }
}