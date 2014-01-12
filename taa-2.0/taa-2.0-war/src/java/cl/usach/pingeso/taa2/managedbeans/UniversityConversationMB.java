 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;

@Named(value = "universityConversationMB")
@ConversationScoped
public class UniversityConversationMB extends AbstractConversation implements Serializable{
    
    private String idUniversityDetails;
    
    public void clean() {
        idUniversityDetails = null;
    }
    
    public UniversityConversationMB() {
    }

    public String getIdUniversityDetails() {
        return idUniversityDetails;
    }

    public void setIdUniversityDetails(String idUniversityDetails) {
        this.idUniversityDetails = idUniversityDetails;
    }
}