/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans.validators;

import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.TeacherFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UniversityFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@FacesValidator("RutValidatorUniversity")
public class RutValidatorUniversity implements Validator {
    UniversityFacadeLocal universityFacade = lookupUniversityFacadeLocal();
    UserFacadeLocal userFacade = lookupUserFacadeLocal();
    TeacherFacadeLocal teacherFacade = lookupTeacherFacadeLocal();
    StudentFacadeLocal studentFacade = lookupStudentFacadeLocal();
    
    
    private static final String RUT_PATTERN = "^[0-9]+$";
    private Pattern pattern;
    private Matcher matcher;
    
    public RutValidatorUniversity(){
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
                new FacesMessage("Rut debe tener máximo 10 dígitos.",
                "Rut debe tener máximo 10 dígitos.");
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
        
        if (userFacade.find(value) != null) {
            String mensaje;
            if(studentFacade.find(value) != null)
                mensaje = "Rut ingresado ya existe como estudiante.";
            else if(teacherFacade.find(value) != null)
                mensaje = "Rut ingresado ya existe como profesor.";
            else
                mensaje = "Rut ingresado ya existe";
            FacesMessage msg = new FacesMessage(mensaje, mensaje);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        else if(universityFacade.find(value) != null) {
            FacesMessage msg = new FacesMessage("Rut ingresado ya existe como universidad." , "Rut ingresado ya existe como universidad.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
    private UserFacadeLocal lookupUserFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (UserFacadeLocal) c.lookup("java:global/taa-2.0/taa-2.0-ejb/UserFacade!cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private StudentFacadeLocal lookupStudentFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (StudentFacadeLocal) c.lookup("java:global/taa-2.0/taa-2.0-ejb/StudentFacade!cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private TeacherFacadeLocal lookupTeacherFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TeacherFacadeLocal) c.lookup("java:global/taa-2.0/taa-2.0-ejb/TeacherFacade!cl.usach.pingeso.taa2.sessionbeans.TeacherFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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

    private UniversityFacadeLocal lookupUniversityFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (UniversityFacadeLocal) c.lookup("java:global/taa-2.0/taa-2.0-ejb/UniversityFacade!cl.usach.pingeso.taa2.sessionbeans.UniversityFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}