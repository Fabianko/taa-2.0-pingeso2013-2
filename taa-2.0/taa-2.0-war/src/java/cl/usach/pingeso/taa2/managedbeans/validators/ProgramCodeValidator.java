/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans.validators;

import cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal;
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
@FacesValidator("programCodeValidator")
public class ProgramCodeValidator implements Validator {
    ProgramFacadeLocal programFacade = lookupProgramFacadeLocal();
    
    public ProgramCodeValidator(){
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        if (programFacade.find(value) != null) {
            String mensaje = "Código de carrera ya existe.";
            FacesMessage msg = new FacesMessage(mensaje, mensaje);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
    private ProgramFacadeLocal lookupProgramFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProgramFacadeLocal) c.lookup("java:global/taa-2.0/taa-2.0-ejb/ProgramFacade!cl.usach.pingeso.taa2.sessionbeans.ProgramFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}