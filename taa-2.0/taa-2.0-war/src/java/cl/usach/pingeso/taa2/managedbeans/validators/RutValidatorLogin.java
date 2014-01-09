/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@FacesValidator("RutValidatorLogin")
public class RutValidatorLogin implements Validator {
    
    private static final String RUT_PATTERN = "^[0-9]+$";
    private Pattern pattern;
    private Matcher matcher;
    
    public RutValidatorLogin(){
        pattern = Pattern.compile(RUT_PATTERN);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
        
        if (!matcher.matches()) {
            FacesMessage msg =
                    new FacesMessage("Rut ingresado no es válido.",
                    "Rut ingresado no es válido.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        else if(value.toString().length() < 8)
        {
            FacesMessage msg =
                new FacesMessage("Rut debe tener al menos 8 dígitos.",
                "Rut debe tener al menos 8 dígitos.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        else if(value.toString().length() > 9)
        {
            FacesMessage msg =
                new FacesMessage("Rut debe tener máximo 9 dígitos.",
                "Rut debe tener máximo 9 dígitos.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);        
        }
        else if(!digitoVerificadorValido(value.toString()))
        {
            FacesMessage msg =
                new FacesMessage("Dígito verificador del rut no es válido.",
                "Dígito verificador del rut no es válido.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg); 
        }
    }
    
    private boolean digitoVerificadorValido(String rut)
    {
            String rutInvertido = "";
            String digitoV = rut.substring(rut.length()-1, rut.length());
            for(int i=rut.length()-2;i>=0;i--)
            {
                rutInvertido = rutInvertido.concat(rut.substring(i, i+1));
            }
            String invertido = rutInvertido;
            int multiplicar = 2;
            int suma = 0;
            
            for (int i = 0; i < invertido.length(); i++)
            {
                int temp;
                if(multiplicar > 7)
                    multiplicar = 2;
                if(i == (invertido.length()-1))
                {
                    temp = Integer.parseInt(invertido.substring(i));
                    suma = multiplicar * temp + suma;
                }
                else
                {
                    temp = Integer.parseInt(invertido.substring(i,i+1));
                    suma = multiplicar * temp + suma;
                }
                multiplicar += 1;
            }
            
            int valor = 11 - (suma % 11);
            String verificador;
            if(valor == 11)
                verificador = "0";
            else if(valor == 10)
                verificador = "0";
            else
                verificador = String.valueOf(valor);
            
            if(verificador.equals(digitoV))
            {
                return true;
            }
            return false;
    }
}