/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

public abstract class AbstractConversation {
    
    @Inject Conversation conversation;
    
    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }
    
    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
    
    public Conversation getConversation() {
        return conversation;
    }
}