/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Attendance;
import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.sessionbeans.AttendanceFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
/**
 *
 * @author Nico
 */
@Named(value = "attendanceStudents")
@RequestScoped
public class AttendanceStudents {
    @EJB
    private AttendanceFacadeLocal attendanceFacade;
    private List<Attendance> attendanceList;
    private User selectedUser;
    private List<User> filteredUsers;
    private final static String[] states;
    private boolean value2=false;
    private Integer number;
    
    static {  
        states = new String[3];  
        states[0] = "N";  
        states[1] = "S";
        states[2] = "J";
    }
     
    @PostConstruct
    public void init()
    {
       attendanceList = attendanceFacade.findAll();
       System.out.println("tengo"+attendanceList.size());
    }
    
    public AttendanceStudents(){ 
    }
    
    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }
    
    public String[] getStates() {  
        return states;  
    }
    
    public void setAttendance(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }
    
    public User getSelectedUser() {  
        return selectedUser;  
    }  
  
    public void setSelectedUser(User selectedUser) {  
        this.selectedUser = selectedUser;  
    }
    
    public void onCellEdit(CellEditEvent event) {  
        Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();  
        if(newValue != null && !newValue.equals(oldValue)) {  
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La asistencia ha sido modificada", "Antes: " + oldValue + ", Ahora:" + newValue);  
            FacesContext.getCurrentInstance().addMessage(null, msg);  
        }  
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }
        public void addMessage() {  
        String summary = value2 ? "Presente" : "Ausente";  
  
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));  
    } 

    public AttendanceFacadeLocal getAttendanceFacade() {
        return attendanceFacade;
    }

    public void setAttendanceFacade(AttendanceFacadeLocal attendanceFacade) {
        this.attendanceFacade = attendanceFacade;
    }

    public boolean isValue2() {
        return value2;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public void pasarAsistencia(List<Attendance>a){
        try{
            
                for (Attendance attendance : a) {
                    if(attendance.getPresent().equals(null)){
                        attendance.setPresent("Ausente");
                    }else{
                        attendance.setPresent("Presente");
                    }
                }
                
            
                      
        }catch(Exception e){
                   General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/AttendanceStudents.xhtml?faces-redirect=true");
        }   
    } 
}