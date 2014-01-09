/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans.validators;

import cl.usach.pingeso.taa2.sessionbeans.CourseFacadeLocal;
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
@FacesValidator("courseNameValidator")
public class CourseNameValidator implements Validator {
    CourseFacadeLocal courseFacade = lookupCourseFacadeLocal();
    
    public CourseNameValidator(){
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        System.out.println(value);
        if (courseFacade.findByCourseName(value) != null) {
            String mensaje = "Nombre de curso ya existe.";
            FacesMessage msg = new FacesMessage(mensaje, mensaje);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
    private CourseFacadeLocal lookupCourseFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (CourseFacadeLocal) c.lookup("java:global/taa-2.0/taa-2.0-ejb/CourseFacade!cl.usach.pingeso.taa2.sessionbeans.CourseFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}