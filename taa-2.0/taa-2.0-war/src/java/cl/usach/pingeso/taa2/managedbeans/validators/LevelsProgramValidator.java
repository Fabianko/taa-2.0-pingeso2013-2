/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Diego
 */
@FacesValidator("levelsProgramValidator")
public class LevelsProgramValidator implements Validator {
    
    public LevelsProgramValidator(){
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        Short valor = 0;
        try
        {
            valor = Short.parseShort((String)value);
        }
        catch(Exception e)
        {
            System.out.println(valor);
            String mensaje = "Duración (Semestres) debe ser un número entero positivo menor que 15.";
            FacesMessage msg = new FacesMessage(mensaje, mensaje);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if(valor < 1 || valor > 14)
        {
            String mensaje = "Duración (Semestres) debe ser un número entero positivo menor que 15.";
            FacesMessage msg = new FacesMessage(mensaje, mensaje);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}