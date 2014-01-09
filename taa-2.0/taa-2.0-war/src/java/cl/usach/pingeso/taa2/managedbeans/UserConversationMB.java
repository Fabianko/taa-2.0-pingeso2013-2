 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;

@Named(value = "userConversationMB")
@ConversationScoped
public class UserConversationMB extends AbstractConversation implements Serializable{
    
    private String idUserDetails;
    
    public void clean() {
        idUserDetails = null;
    }
    
    public UserConversationMB() {
    }

    public String getIdUserDetails() {
        return idUserDetails;
    }

    public void setIdUserDetails(String idUserDetails) {
        this.idUserDetails = idUserDetails;
    }
}