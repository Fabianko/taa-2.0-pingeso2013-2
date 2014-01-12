 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;

@Named(value = "schoolConversationMB")
@ConversationScoped
public class SchoolConversationMB extends AbstractConversation implements Serializable{
    
    private String idSchoolDetails;
    
    public void clean() {
        idSchoolDetails = null;
    }
    
    public SchoolConversationMB() {
    }

    public String getIdSchoolDetails() {
        return idSchoolDetails;
    }

    public void setIdSchoolDetails(String idSchoolDetails) {
        this.idSchoolDetails = idSchoolDetails;
    }
}