 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;

@Named(value = "courseConversationMB")
@ConversationScoped
public class CourseConversationMB extends AbstractConversation implements Serializable{
    
    private String idCourseDetails;
    
    public void clean() {
        idCourseDetails = null;
    }
    
    public CourseConversationMB() {
    }

    public String getIdCourseDetails() {
        return idCourseDetails;
    }

    public void setIdCourseDetails(String idCourseDetails) {
        this.idCourseDetails = idCourseDetails;
    }
}