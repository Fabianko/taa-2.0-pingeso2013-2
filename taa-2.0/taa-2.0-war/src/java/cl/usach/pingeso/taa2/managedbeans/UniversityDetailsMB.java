/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.School;
import cl.usach.pingeso.taa2.entityclasses.University;
import cl.usach.pingeso.taa2.sessionbeans.UniversityFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "universityDetailsMB")
@RequestScoped
public class UniversityDetailsMB {
    @EJB
    private UniversityFacadeLocal universityFacade;
    @Inject
    private UniversityConversationMB mantUniversity;
    private String rutUniversity;
    private String nameUniversity;
    private String adressUniversity;
    private String emailUniversity;
    private String phoneUniversity;
    private Collection<School> schoolsUniversity;
    
    public UniversityDetailsMB() {
    }
    
    @PostConstruct
    public void init() {
        if (mantUniversity.getIdUniversityDetails()!= null) {
            String universityRut = mantUniversity.getIdUniversityDetails();
            loadUniversity(universityRut);
        }
        else {
            backToUniversities();
        }
    }
    
    private void loadUniversity(String rutUniversity) {
        University universitySelect = universityFacade.find(rutUniversity);
        if (universitySelect == null) {
            backToUniversities();
            return;
        }
        this.rutUniversity = universitySelect.getRutUniversity();
        this.nameUniversity = universitySelect.getUniversityName();
        this.emailUniversity = universitySelect.getUniversityHeadquarterEmail();
        this.adressUniversity = universitySelect.getUniversityHeadquarterAdress();
        this.phoneUniversity = universitySelect.getUniversityHeadquarterPhone();
        this.schoolsUniversity = (List<School>)universitySelect.getSchoolCollection();
    }

    public Collection<School> getSchoolsUniversity() {
        return schoolsUniversity;
    }

    public void setSchoolsUniversity(Collection<School> schoolsUniversity) {
        this.schoolsUniversity = schoolsUniversity;
    }
    
    public void backToUniversities() {
        mantUniversity.endConversation();
        General.goToPage("/faces/universityList.xhtml");
    }

    public String getRutUniversity() {
        return rutUniversity;
    }

    public void setRutUniversity(String rutUniversity) {
        this.rutUniversity = rutUniversity;
    }

    public UniversityConversationMB getMantUniversity() {
        return mantUniversity;
    }

    public void setMantUniversity(UniversityConversationMB mantUniversity) {
        this.mantUniversity = mantUniversity;
    }

    public String getNameUniversity() {
        return nameUniversity;
    }

    public void setNameUniversity(String nameUniversity) {
        this.nameUniversity = nameUniversity;
    }
    
    public String getEmailUniversity() {
        if(emailUniversity == null || "".equals(emailUniversity))
            emailUniversity = "No asignado";
        return emailUniversity;
    }
    
    public void setEmailUniversity(String emailUniversity) {
        this.emailUniversity = emailUniversity;
    }

    public String getPhoneUniversity() {
        if(phoneUniversity == null || "".equals(phoneUniversity))
            phoneUniversity = "No asignado";
        return phoneUniversity;
    }

    public void setPhoneUniversity(String phoneUniversity) {
        this.phoneUniversity = phoneUniversity;
    }

    public String getAdressUniversity() {
        if(adressUniversity == null || "".equals(adressUniversity))
            adressUniversity = "No asignada";
        return adressUniversity;
    }

    public void setAdressUniversity(String adressUniversity) {
        this.adressUniversity = adressUniversity;
    }
}