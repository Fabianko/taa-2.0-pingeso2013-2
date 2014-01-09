/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Teacher;
import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.sessionbeans.TeacherFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import cl.usach.pingeso.taa2.utils.General;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.annotation.PostConstruct;

/**
 *
 * @author Diego
 */
@Named(value = "teacherRegisterMB")
@RequestScoped
public class TeacherRegisterMB {
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private TeacherFacadeLocal teacherFacade;
    private String rut;
    private String name1;
    private String name2;
    private String lastNameP;
    private String lastNameM;
    private String email;
    private String rol="Profesor";
    private String state="1";
    private String pass;
    
    public TeacherRegisterMB() {
    }
    
    @PostConstruct
    public void init() {
    }
    
    public void teacherRegisterNow() {
        try {
            User newUser = new User();
            newUser.setRut(rut);
            newUser.setFirstName(name1);
            newUser.setMiddleName(name2);
            newUser.setPrimaryLastName(lastNameP);
            newUser.setSecondLastName(lastNameM);
            newUser.setEmail(email);
            newUser.setPassword(convertToMD5(rut));
            newUser.setUserState(state);
            newUser.setRol(rol);
            userFacade.create(newUser);
            teacherRegisterNow1(newUser);
            
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Profesor registrado satisfactoriamente!!!",
                    "Profesor registrado satisfactoriamente!!!");
            General.goToPage("/faces/teacherRegister.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/teacherRegister.xhtml?faces-redirect=true");
        }
    }
    
    public void teacherRegisterNow1(User u){
            
            Teacher newTeacher=new Teacher();
            newTeacher.setRut(u.getRut());
            newTeacher.setUser(u);
            teacherFacade.create(newTeacher);
    }
    
    public TeacherFacadeLocal getStudentFacade() {
        return teacherFacade;
    }

    public void setTeacherFacade(TeacherFacadeLocal teacherFacade) {
        this.teacherFacade = teacherFacade;
    }

    public UserFacadeLocal getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacadeLocal userFacade) {
        this.userFacade = userFacade;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }
    
    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getLastNameP() {
        return lastNameP;
    }

    public void setLastNameP(String lastNameP) {
        this.lastNameP = lastNameP;
    }

    public String getLastNameM() {
        return lastNameM;
    }

    public void setLastNameM(String lastNameM) {
        this.lastNameM = lastNameM;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void backToTeachers() {
        General.goToPage("/faces/teacherList.xhtml");
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    private String convertToMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes("UTF-8"));

            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            pass = bigInt.toString(16);
        } catch (Exception e) {
            System.out.println("No se pudo convertir a MD5 la pass");
        }
        return pass;
    }
}