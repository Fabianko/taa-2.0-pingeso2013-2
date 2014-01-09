/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.usach.pingeso.taa2.managedbeans.login;

import cl.usach.pingeso.taa2.managedbeans.login.SessionValidator;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import cl.usach.pingeso.taa2.utils.General;
import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.managedbeans.UserConversationMB;
import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import javax.ejb.EJB;
/**
 *
 * @author Diego
 */
@Named(value = "autentification")
@RequestScoped
public class Session {
    @EJB
    private UserFacadeLocal userFacade;
    private User u;
    @Inject private SessionValidator session;
    @Inject private UserConversationMB infoUser;
    private String rut;
    private String password;
    private String myURL;
    
    /**
     * Creates a new instance of Autentificacion
     */
    public Session() {
    }
    
    @PostConstruct
    public void init() {
        myURL = (String)FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        if (myURL == null) {
            myURL = session.getMyURL();
            if (myURL == null) {
                myURL = "/faces/index.xhtml";
            }
        }
        else {
            String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            myURL = myURL.substring(contextPath.length());
            session.setMyURL(myURL);
        }
        
        //Si el usuario ya tiene sesión iniciada, se redirecciona a la página solicitada. 
        if (session.loginStatus()) {
            General.goToPage(myURL);
        }
    }
    
    public void login() {
        if(session.login(rut, password)) {
            u=userFacade.find(rut);
            session.setUsername(u.getFirstName()+" "+u.getPrimaryLastName());
            
            this.infoUser.beginConversation();
            this.infoUser.setIdUserDetails(rut);
            General.goToPage(myURL);
        }
    }
    
    public String getRut() {
        return rut;
    }
    
    public void setRut(String rut) {
        this.rut = rut;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }   
}