/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans.login;

import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import javax.inject.Named;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import cl.usach.pingeso.taa2.utils.General;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;

/**
 *
 * @author Diego
 */
@Named(value = "sessionValidator")
@SessionScoped
public class SessionValidator implements Serializable {
    
    private String myURL;

    private String username;
    private String password;
    private Date date;
    private User u;
    @EJB
    private UserFacadeLocal userFacade;
    
    public boolean loginStatus() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        return request.getRemoteUser() != null;
    }
    
    public String userRut() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        return request.getRemoteUser();
    }
    
    public boolean oldLoginStatus(String rut) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        if (request.getRemoteUser() != null) {
            if (request.getRemoteUser().equals(rut)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean login(String rut, String password) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        u=userFacade.find(rut);
        username=u.getFirstName() +" "+u.getPrimaryLastName();
        try {
            if (!loginStatus()) {
                request.login(rut, password);
            }            
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Bienvenido Señor "+u.getFirstName() +" "+u.getPrimaryLastName()+".",
                    "Bienvenido Señor "+u.getFirstName() +" "+u.getPrimaryLastName()+".");
            return true;
        }
        catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Acceso denegado. Los datos ingresados no son válidos.", ""));
        }
        return false;
    }
    
    public void logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        this.myURL = null;
        General.goToPage("/faces/login.xhtml");
    }
    
    public void indexLoggued() {
        General.goToPage("/faces/index.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public UserFacadeLocal getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacadeLocal userFacade) {
        this.userFacade = userFacade;
    }
    
    public SessionValidator() {
    }

    public String getMyURL() {
        return myURL;
    }
    
    public void setMyURL(String myURL) {
        this.myURL = myURL;
    }
}