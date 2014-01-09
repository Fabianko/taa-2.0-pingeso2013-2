
package cl.usach.pingeso.taa2.utils;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Diego
 */
public class General {
    
    public static void goToPage(String page) {
       ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
       try {
           externalContext.redirect(externalContext.getRequestContextPath() + page);
       }
       catch (IOException e) {
           System.out.println(e.getMessage());
       }
    }
    
    public static void goToIndex() {
        goToPage("/faces/index.xhtml");
    }
    
    public static String getUser() {
        HttpServletRequest request = (HttpServletRequest) 
                (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        return request.getRemoteUser();
    }
    
    public static boolean isGetMethod() {
        HttpServletRequest request = (HttpServletRequest) 
                (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        if (request.getMethod().equals("GET")) {
            return true;
        }
        return false;
    }
    
    public static boolean isUserInRole(String nombreRol) {
        HttpServletRequest request = (HttpServletRequest) 
                (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        return request.isUserInRole(nombreRol);
    }
    
    public static void viewMessage(FacesMessage.Severity sev, String titMsg, String msj) {
        FacesMessage msg = new FacesMessage(sev, titMsg, msj);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
     public static void viewErrorMessage(FacesMessage.Severity sev, String titMsg, String msj) {
        FacesMessage msg = new FacesMessage(sev, titMsg, msj);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}