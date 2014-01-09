/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans.validators;

import cl.usach.pingeso.taa2.sessionbeans.TeamFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Diego
 */
@FacesValidator("teamNameValidator")
public class TeamNameValidator implements Validator {
    TeamFacadeLocal teamFacade = lookupTeamFacadeLocal();
    
    public TeamNameValidator(){
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        System.out.println(value);
        if (teamFacade.findByTeamName(value) != null) {
            String mensaje = "Nombre de grupo ya existe.";
            FacesMessage msg = new FacesMessage(mensaje, mensaje);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
    private TeamFacadeLocal lookupTeamFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TeamFacadeLocal) c.lookup("java:global/taa-2.0/taa-2.0-ejb/TeamFacade!cl.usach.pingeso.taa2.sessionbeans.TeamFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}