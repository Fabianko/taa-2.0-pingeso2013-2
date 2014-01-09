/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Teacher;
import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.sessionbeans.TeacherFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "teacherEditMB")
@RequestScoped
public class TeacherEditMB {
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private TeacherFacadeLocal teacherFacade;
    @Inject 
    private TeacherConversationMB mantTeacher;
    private String rut;
    private String firstName;
    private String middleName;
    private String primaryLastName;
    private String secondLastName;
    private String mail;
    private boolean checkPass;
    private String pass;
    
    @PostConstruct
    public void init() { 
        if (mantTeacher.getIdUserDetails()!= null) {
            String rutTeacher = mantTeacher.getIdUserDetails();
            loadTeacher(rutTeacher);
        }
        else {
            backToTeachers();
        }
    }
    
    private void loadTeacher(String rutTeacher) {
        Teacher teacherSelect = teacherFacade.find(rutTeacher);
        if (teacherSelect == null) {
            backToTeachers();
            return;
        }
        this.rut = teacherSelect.getRut();
        this.firstName = teacherSelect.getUser().getFirstName();
        this.middleName = teacherSelect.getUser().getMiddleName();
        this.primaryLastName = teacherSelect.getUser().getPrimaryLastName();
        this.secondLastName = teacherSelect.getUser().getSecondLastName();
        this.mail = teacherSelect.getUser().getEmail();
        this.pass = teacherSelect.getUser().getPassword();
    }
    
    public void saveTeacher(){
        mantTeacher.endConversation();
        try {
            Teacher temp = new Teacher();
            User tempUser = new User();
            temp.setRut(rut);
            tempUser.setRut(rut);
            tempUser.setFirstName(firstName);
            tempUser.setMiddleName(middleName);
            tempUser.setPrimaryLastName(primaryLastName);
            tempUser.setSecondLastName(secondLastName);
            tempUser.setRol("Profesor");
            tempUser.setUserState("1");
            tempUser.setEmail(mail);
            if(checkPass)
            {
                tempUser.setPassword(convertToMD5(rut));
            }
            else
            {
                tempUser.setPassword(pass);
            }
            temp.setUser(tempUser);
            teacherFacade.edit(temp);
            userFacade.edit(tempUser);
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se han modificado los datos del profesor",
                    "Se han modificado los datos del profesor \"".concat(rut).concat("\""));
            General.goToPage("/faces/teacherList.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/teacherEdit.xhtml?faces-redirect=true");
        }
    }
    
    public void backToTeachers() {
        mantTeacher.endConversation();
        General.goToPage("/faces/teacherList.xhtml");
    }
    
    public TeacherEditMB() {
    }
    
    public TeacherConversationMB getMantTeacher() {
        return mantTeacher;
    }

    public void setMantTeacher(TeacherConversationMB manTeacherX) {
        this.mantTeacher = manTeacherX;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPrimaryLastName() {
        return primaryLastName;
    }

    public void setPrimaryLastName(String primaryLastName) {
        this.primaryLastName = primaryLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public boolean getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(boolean checkPass) {
        this.checkPass = checkPass;
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

    public String getPass() {
        return pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
}