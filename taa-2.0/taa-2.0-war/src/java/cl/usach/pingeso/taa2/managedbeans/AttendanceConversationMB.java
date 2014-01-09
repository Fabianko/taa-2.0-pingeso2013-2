 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.ConversationScoped;

@Named(value = "attendanceConversationMB")
@ConversationScoped
public class AttendanceConversationMB extends AbstractConversation implements Serializable{
    
    private Date idAttendanceDetails;
    
    public void clean() {
        idAttendanceDetails = null;
    }
    
    public AttendanceConversationMB() {
    }

    public Date getIdAttendanceDetails() {
        return idAttendanceDetails;
    }

    public void setIdAttendanceDetails(Date idAttendanceDetails) {
        this.idAttendanceDetails = idAttendanceDetails;
    }
}