 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;

@Named(value = "programConversationMB")
@ConversationScoped
public class ProgramConversationMB extends AbstractConversation implements Serializable{
    
    private String idProgramDetails;
    
    public void clean() {
        idProgramDetails = null;
    }
    
    public ProgramConversationMB() {
    }

    public String getIdProgramDetails() {
        return idProgramDetails;
    }

    public void setIdProgramDetails(String idProgramDetails) {
        this.idProgramDetails = idProgramDetails;
    }
}