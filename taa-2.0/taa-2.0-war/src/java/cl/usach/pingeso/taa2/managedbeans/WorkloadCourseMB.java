/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
/**
 *
 * @author Nico
 */
@Named(value = "workloadCourseMB")
@RequestScoped
public class WorkloadCourseMB implements Serializable {
    @EJB
    private AssignmentFacadeLocal assignmentFacade;
    @EJB
    private TeacherFacadeLocal teacherFacade;
    @EJB
    private CourseFacadeLocal courseFacade;
    @Inject
    private CourseConversationMB mantCourse;
    private List<String> selectedOptions; 
    private List<String> selectedOptions1; 
    private List<String> selectedOptions2; 
    private List<String> selectedOptions3; 
    private List<String> selectedOptions4; 
    private List<String> selectedOptions5;
    private List<String> selectedOptions6;  
    private String courseCode;
    private String courseName;
    private String Classroom; 
    private String teacher;
    private String workload;
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
        this.Classroom = courseSelect.getMainClassroom();
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
            newCourse.setMainClassroom(Classroom);
            newCourse.setCourseState("1");
            //newCourse.setWorkload(workloadCreate());
            courseFacade.edit(newCourse);
            //saveCourse1(newCourse);
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                        "Se ha agregado el horario al curso",
                        "Se ha agregado el horario al curso ");

           General.goToPage("/faces/classList.xhtml?faces-redirect=true");  
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/courseEdit.xhtml?faces-redirect=true");
        }
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
        
    public String workloadCreate(){
       String workloadX="";
        for (String string : selectedOptions) {
            workloadX+=string+",";
        }        
        for (String string : selectedOptions1) {
            workloadX+=","+string;
        }       
        for (String string : selectedOptions2) {
            workloadX+=","+string;
        }        
        for (String string : selectedOptions3) {
            workloadX+=","+string;
        }        
        for (String string : selectedOptions4) {
            workloadX+=","+string;
        }        
        for (String string : selectedOptions5) {
            workloadX+=","+string;
        }
        for (String string : selectedOptions6) {
            workloadX+=","+string;
        }
        workloadX=workloadX.replace(',', ' ');
        return workloadX;
      }
    
    public void backToCourses() {
       General.goToPage("/faces/classList.xhtml");    
    }

    public AssignmentFacadeLocal getAssignmentFacade() {
        return assignmentFacade;
    }

    public void setAssignmentFacade(AssignmentFacadeLocal assignmentFacade) {
        this.assignmentFacade = assignmentFacade;
    }

    public TeacherFacadeLocal getTeacherFacade() {
        return teacherFacade;
    }

    public void setTeacherFacade(TeacherFacadeLocal teacherFacade) {
        this.teacherFacade = teacherFacade;
    }

    public CourseFacadeLocal getCourseFacade() {
        return courseFacade;
    }

    public void setCourseFacade(CourseFacadeLocal courseFacade) {
        this.courseFacade = courseFacade;
    }

    public CourseConversationMB getMantCourse() {
        return mantCourse;
    }

    public void setMantCourse(CourseConversationMB mantCourse) {
        this.mantCourse = mantCourse;
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

    public String getClassroom() {
        return Classroom;
    }

    public void setClassroom(String Classroom) {
        this.Classroom = Classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public SelectItem[] getTeacherOptions() {
        return teacherOptions;
    }

    public void setTeacherOptions(SelectItem[] teacherOptions) {
        this.teacherOptions = teacherOptions;
    }
    
    public List<String> getSelectedOptions1() {
        return selectedOptions1;
    }

    public void setSelectedOptions1(List<String> selectedOptions1) {
        this.selectedOptions1 = selectedOptions1;
    }

    public List<String> getSelectedOptions2() {
        return selectedOptions2;
    }

    public void setSelectedOptions2(List<String> selectedOptions2) {
        this.selectedOptions2 = selectedOptions2;
    }

    public List<String> getSelectedOptions3() {
        return selectedOptions3;
    }

    public void setSelectedOptions3(List<String> selectedOptions3) {
        this.selectedOptions3 = selectedOptions3;
    }

    public List<String> getSelectedOptions4() {
        return selectedOptions4;
    }

    public void setSelectedOptions4(List<String> selectedOptions4) {
        this.selectedOptions4 = selectedOptions4;
    }

    public List<String> getSelectedOptions5() {
        return selectedOptions5;
    }

    public void setSelectedOptions5(List<String> selectedOptions5) {
        this.selectedOptions5 = selectedOptions5;
    }

    public List<String> getSelectedOptions6() {
        return selectedOptions6;
    }

    public void setSelectedOptions6(List<String> selectedOptions6) {
        this.selectedOptions6 = selectedOptions6;
    }


    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
}
