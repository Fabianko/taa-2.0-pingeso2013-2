 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;

@Named(value = "teamConversationMB")
@ConversationScoped
public class TeamConversationMB extends AbstractConversation implements Serializable{
    
    private String idTeamDetails;
    
    public void clean() {
        idTeamDetails = null;
    }
    
    public TeamConversationMB() {
    }

    public String getIdTeamDetails() {
        return idTeamDetails;
    }

    public void setIdTeamDetails(String idTeamDetails) {
        this.idTeamDetails = idTeamDetails;
    }
}