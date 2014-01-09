/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Attendance;
import cl.usach.pingeso.taa2.entityclasses.AttendancePK;
import cl.usach.pingeso.taa2.entityclasses.Membership;
import cl.usach.pingeso.taa2.entityclasses.MembershipPK;
import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.Student;
import static cl.usach.pingeso.taa2.managedbeans.StudentAutenticationMB.detect;
import cl.usach.pingeso.taa2.sessionbeans.AttendanceFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.CourseFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.MembershipFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import cl.usach.pingeso.taa2.utils.StudentDataModel;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.CV_AA;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import com.googlecode.javacv.cpp.opencv_objdetect;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import org.primefaces.event.CaptureEvent;

/**
 *
 * @author Diego
 */
@Named(value = "courseStudentsByFaceMB")
@RequestScoped
public class CourseStudentsByFaceMB {
    @EJB
    private CourseFacadeLocal courseFacade;
    @EJB
    private AttendanceFacadeLocal attendanceFacade;
    @EJB
    private MembershipFacadeLocal membershipFacade;
    @EJB
    private StudentFacadeLocal studentFacade;
    @EJB
    private ProgramFacadeLocal programFacade;
    private List<Student> courseList;
    private List<Student> courseFiltered;
    @Inject 
    private CourseConversationMB mantCourse;
    @Inject 
    private StudentConversation mantStudent;
    @Inject 
    private AttendanceConversationMB mantAttendance;
    private SelectItem[] programOptions;
    private Date date1 = new Date();
    private Integer stateAttendance = 0;
    String currentPhoto;
    String nomTemp;
    private Student[] selectedStudent;
    private StudentDataModel studentsModel;
    private String nomNoExt;
    
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
        if(mantAttendance.getIdAttendanceDetails() != null)
        {
            date1 = mantAttendance.getIdAttendanceDetails();
        }
    }
    
    private void loadStudentsCourse(String course) {
        List<Student> students;
        if (mantAttendance.getIdAttendanceDetails() != null) {
            //students = studentFacade.findByCourseDate(course, date1);
            students = studentFacade.findByCourse(course);
        }
        else {
            this.mantAttendance.beginConversation();
            this.mantAttendance.setIdAttendanceDetails(date1);
            //students = studentFacade.findByCourseDate(course, date1);
            students = studentFacade.findByCourse(course);
        }
        
        if (students == null) {
            backToCourses();
            return;
        }
        this.courseList = students;
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
        mantAttendance.endConversation();
        General.goToPage("/faces/classList.xhtml");
    }
      
    public CourseStudentsByFaceMB() {
    }
    
    public CourseConversationMB getMantCourse() {
        return mantCourse;
    }

    public void setMantCourse(CourseConversationMB manCourseX) {
        this.mantCourse = manCourseX;
    }

    public List<Student> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Student> courseList) {
        this.courseList = courseList;
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
    
    public void addCourseStudent() {
        General.goToPage("/faces/studentCourseRegister.xhtml");  
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
     
    public void oncapture(CaptureEvent captureEvent)
    {
        final byte[] datos = captureEvent.getData();
        Random r = new Random(System.currentTimeMillis());
        int val = r.nextInt(10000)+1;
        this.nomNoExt = String.valueOf(val);
        this.currentPhoto = nomNoExt+".png";
        final String filePhoto = "C:\\photos_taa\\temp\\"+currentPhoto;
        FileImageOutputStream outputStream;
        
        try {
            outputStream = new FileImageOutputStream(new File(filePhoto));
            outputStream.write(datos, 0, datos.length);
            outputStream.close();
            String evaluation = detectFace();
            System.out.println(this.mantStudent.getIdUserDetails());
            if(evaluation.compareTo("ok")==0)
            {
                if(attendanceFacade.predict(filePhoto, this.mantStudent.getIdUserDetails()))
                {
                    File myPhoto = new File("C:\\photos_taa\\temp\\"+currentPhoto);
                    myPhoto.delete();
                    try
                    {
                        AttendancePK newAttendance = new AttendancePK();
                        newAttendance.setAttendanceDate(date1);
                        newAttendance.setRut(this.mantStudent.getIdUserDetails());
                        newAttendance.setCourseCode(this.mantCourse.getIdCourseDetails());
                        Attendance newAttendance2 = new Attendance();
                        newAttendance2.setAttendancePK(newAttendance);
                        newAttendance2.setPresent("1");
                        newAttendance2.setAttendanceState("1");
                        newAttendance2.setCourse(courseFacade.find(this.mantCourse.getIdCourseDetails()));
                        newAttendance2.setStudent(studentFacade.find(this.mantStudent.getIdUserDetails()));
                        attendanceFacade.create(newAttendance2);
                        General.viewMessage(FacesMessage.SEVERITY_INFO,
                            "Reconocimiento exitoso. Estás presente rut " + this.mantStudent.getIdUserDetails() +" !!!",
                            "Reconocimiento exitoso. Estás presente rut " + this.mantStudent.getIdUserDetails() +" !!!");
                    }
                    catch(Exception e)
                    {
                        General.viewMessage(FacesMessage.SEVERITY_INFO,
                            "Rut " + this.mantStudent.getIdUserDetails() +" ya se autentificó y estás presente. !!!",
                            "Rut " + this.mantStudent.getIdUserDetails() +" ya se autentificó y estás presente. !!!");
                    }
                    General.goToPage("/faces/attendanceCourseByFace.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
                }
                else
                {
                    File myPhoto = new File("C:\\photos_taa\\temp\\"+currentPhoto);
                    myPhoto.delete();
                    General.viewMessage(FacesMessage.SEVERITY_INFO,
                        "Estudiante no reconocido. Inténtalo nuevamente",
                        "Estudiante no reconocido. Inténtalo nuevamente");
                    General.goToPage("/faces/attendanceCourseByFace.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
                }
            }
            else if(evaluation.compareTo("noFaces")==0)
            {
                File myPhoto = new File("C:\\photos_taa\\temp\\"+currentPhoto);
                myPhoto.delete();
                System.out.println("No se detectaron rostros en la fotografía.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "No se detectaron rostros en la fotografía.",
                "No se detectaron rostros en la fotografía.");
                General.goToPage("/faces/attendanceCourseByFace.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
            }
            else if(evaluation.compareTo("manyFaces")==0)
            {
                File myPhoto = new File("C:\\photos_taa\\temp\\"+currentPhoto);
                myPhoto.delete();
                System.out.println("Se detectó más de un rostro en la fotografía.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Se detectó más de un rostro en la fotografía.",
                "Se detectó más de un rostro en la fotografía.");
                General.goToPage("/faces/attendanceCourseByFace.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
            }
            else if(evaluation.compareTo("smallFace")==0)
            {
                File myPhoto = new File("C:\\photos_taa\\temp\\"+currentPhoto);
                myPhoto.delete();
                System.out.println("Rostro demasiado lejos.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Rostro demasiado lejos. Acercate a la cámara e inténtalo nuevamente.",
                "Rostro demasiado lejos. Acercate a la cámara e inténtalo nuevamente.");
                General.goToPage("/faces/attendanceCourseByFace.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
            }
            else if(evaluation.compareTo("bigFace")==0)
            {
                File myPhoto = new File("C:\\photos_taa\\temp\\"+currentPhoto);
                myPhoto.delete();
                System.out.println("Rostro demasiado cerca.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Rostro demasiado cerca. Alejate de la cámara e inténtalo nuevamente.",
                "Rostro demasiado cerca. Alejate de la cámara e inténtalo nuevamente.");
                General.goToPage("/faces/attendanceCourseByFace.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
            }
            else
            {
                File myPhoto = new File("C:\\photos_taa\\temp\\"+currentPhoto);
                myPhoto.delete();
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Error de causa desconocida. Contacta al administrador del sistema.",
                "Error de causa desconocida. Contacta al administrador del sistema.");
                System.out.println("Error de causa desconocida.");
                General.goToPage("/faces/attendanceCourseByFace.xhtml?cid=".concat(this.mantCourse.getConversation().getId()));
            }
        }
        catch (IOException e)
        {
            File myPhoto = new File("C:\\photos_taa\\temp\\"+currentPhoto);
            myPhoto.delete();
            throw new FacesException("Error al guardar fotografía", e);
        }
    }
    
    private String detectFace()
    {
        opencv_core.IplImage img = cvLoadImage("C:\\photos_taa\\temp\\"+currentPhoto);
        String result = detect(img);
        System.out.println(result);
        return result;
    }
    
    public void attendanceByFace(String rutStudent) {
        Student studentViewDetails = studentFacade.find(rutStudent);
        if (studentViewDetails != null) {
            this.mantStudent.beginConversation();
            this.mantStudent.setIdUserDetails(rutStudent);
            this.nomTemp = rutStudent;
        }
        else {
            this.mantStudent.clean();
            this.nomTemp = null;
        }
    }
    
    public static String detect(opencv_core.IplImage src)
    {
         opencv_objdetect.CvHaarClassifierCascade cascade = new opencv_objdetect.CvHaarClassifierCascade(cvLoad("C:\\opencv\\data\\haarcascades\\haarcascade_frontalface_alt.xml"));
        opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();
	opencv_core.CvSeq sign = cvHaarDetectObjects(src, cascade, storage, 1.5, 3, CV_HAAR_DO_CANNY_PRUNING);
	cvClearMemStorage(storage);
        int total_Faces = sign.total();		
        if(total_Faces < 1)
            return "ok";
        else if(total_Faces > 1)
            return "manyFaces";
        else
        {
            opencv_core.CvRect r = new opencv_core.CvRect(cvGetSeqElem(sign, 0));
            cvRectangle (src, cvPoint(r.x(), r.y()), cvPoint(r.width() + r.x(), r.height() + r.y()), opencv_core.CvScalar.RED, 2, CV_AA, 0);
            if(r.width() < 100)
                return "smallFace";
            else if(r.width() > 130)
                return "bigFace";
            else
                return "ok";
        }
    }
    
    public String getCurrentPhoto() {
        return currentPhoto;
    }
    
    public void setCurrentPhoto(String photo) {
        this.currentPhoto = photo;
    }
    
    public boolean isViewPhoto() {
        return currentPhoto != null;
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