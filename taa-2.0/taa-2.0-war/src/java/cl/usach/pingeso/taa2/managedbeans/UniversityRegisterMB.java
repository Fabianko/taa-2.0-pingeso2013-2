/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.University;
import cl.usach.pingeso.taa2.sessionbeans.UniversityFacadeLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import cl.usach.pingeso.taa2.utils.General;
import javax.annotation.PostConstruct;

/**
 *
 * @author Diego
 */
@Named(value = "universityRegisterMB")
@RequestScoped
public class UniversityRegisterMB {
    @EJB
    private UniversityFacadeLocal universityFacade;
    private String rutUniversity;
    private String nameUniversity;
    private String adress;
    private String phone;
    private String emailUniversity;
    private String state="1";
    
    public UniversityRegisterMB() {
    }
    
    @PostConstruct
    public void init() {
    }
    
    public void universityRegisterNow() {
        try {
            University newUniversity;
            newUniversity = new University();
            newUniversity.setRutUniversity(rutUniversity);
            newUniversity.setUniversityName(nameUniversity);
            newUniversity.setUniversityHeadquarterAdress(adress);
            newUniversity.setUniversityHeadquarterEmail(emailUniversity);
            newUniversity.setUniversityHeadquarterPhone(phone);
            newUniversity.setUniversityState(state);
            universityFacade.create(newUniversity);
            
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    nameUniversity.concat(" registrada satisfactoriamente !!!"),
                    nameUniversity.concat(" registrada satisfactoriamente !!!"));
            General.goToPage("/faces/universityRegister.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/universityRegister.xhtml?faces-redirect=true");
        }
    }
    
    public void backToUniversities() {
        General.goToPage("/faces/universityList.xhtml");
    }

    public String getRutUniversity() {
        return rutUniversity;
    }

    public void setRutUniversity(String rutUniversity) {
        this.rutUniversity = rutUniversity;
    }

    public String getNameUniversity() {
        return nameUniversity;
    }

    public void setNameUniversity(String nameUniversity) {
        this.nameUniversity = nameUniversity;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailUniversity() {
        return emailUniversity;
    }

    public void setEmailUniversity(String emailUniversity) {
        this.emailUniversity = emailUniversity;
    }
}