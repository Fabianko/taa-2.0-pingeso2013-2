package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.Student;
import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;  
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
@Named(value = "student")
@RequestScoped
public class StudentMB implements Serializable {
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private ProgramFacadeLocal programFacade;
    @EJB
    private StudentFacadeLocal studentFacade;
    private List<Student> lista;
    private List<Student> filteredStudents;
    @Inject 
    private StudentConversation mantStudent;
    private SelectItem[] programOptions;
    
    public StudentMB() {
    }
    
    @PostConstruct
    public void init() {        
        this.lista = studentFacade.findAll();
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
    
    public void photos(String rutStudent) {
        Student userViewDetails = studentFacade.find(rutStudent);
        if (userViewDetails != null) {
            this.mantStudent.beginConversation();
            this.mantStudent.setIdUserDetails(rutStudent);
            General.goToPage("/faces/photosStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
        }
        else {
            this.mantStudent.clean();
            General.goToPage("/faces/studentList.xhtml");
        }
    }
    
    public void add() {
       General.goToPage("/faces/studentRegister.xhtml");   
    }
    
     public void edit(String rutStudent) {
        Student usuarioEdit = studentFacade.find(rutStudent);
        if (usuarioEdit != null) {
            this.mantStudent.beginConversation();
            this.mantStudent.setIdUserDetails(rutStudent);
            General.goToPage("/faces/studentEdit.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
        }
        else {
            this.mantStudent.clean();
            General.goToPage("/faces/studentList.xhtml");
        }
    }
     
    public void teamStudent(String rutStudent) {
        Student usuarioEdit = studentFacade.find(rutStudent);
        if (usuarioEdit != null) {
            this.mantStudent.beginConversation();
            this.mantStudent.setIdUserDetails(rutStudent);
            General.goToPage("/faces/studentTeamStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
        }
        else {
            this.mantStudent.clean();
            General.goToPage("/faces/studentList.xhtml");
        }
    }
     
    public void delete(String rutStudent) {
        Student temp = new Student();
        User tempUser = userFacade.find(rutStudent);
        temp.setRut(rutStudent);
        boolean resultado;
        try
        {
            studentFacade.remove(temp);
            userFacade.remove(tempUser);
            resultado = true;
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Estudiante eliminado satisfactoriamente !!!",
                    "Estudiante eliminado satisfactoriamente !!!");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No se puede eliminar al estudiante mientras tenga cursos inscritos.",
                    "No se puede eliminar al estudiante mientras tenga cursos inscritos.");
        }
        General.goToPage("/faces/studentList.xhtml?faces-redirect=true");
    }
    
    public void backToStudents() {
       General.goToPage("/faces/studentList.xhtml");
       
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