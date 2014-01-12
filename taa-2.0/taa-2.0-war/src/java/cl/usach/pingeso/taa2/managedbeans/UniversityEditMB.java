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
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "universityEditMB")
@RequestScoped
public class UniversityEditMB {
    @EJB
    private UniversityFacadeLocal universityFacade;
    @Inject
    private UniversityConversationMB mantUniversity;
    private String universityRut;
    private String universityName;
    private String universityPhone;
    private String universityAdress;
    private String universityEmail;
    private Collection<School> schoolsUniversity;
    
    @PostConstruct
    public void init() { 
        if (mantUniversity.getIdUniversityDetails()!= null) {
            String university = mantUniversity.getIdUniversityDetails();
            loadUniversity(university);
        }
        else {
            backToUniversities();
        }
    }
    
    private void loadUniversity(String university) {
        University universitySelect = universityFacade.find(university);
        if (universitySelect == null) {
            backToUniversities();
            return;
        }
        this.universityRut = universitySelect.getRutUniversity();
        this.universityName = universitySelect.getUniversityName();
        this.universityAdress = universitySelect.getUniversityHeadquarterAdress();
        this.universityEmail = universitySelect.getUniversityHeadquarterEmail();
        this.universityPhone = universitySelect.getUniversityHeadquarterPhone();
        this.schoolsUniversity = (List<School>)universitySelect.getSchoolCollection();
    }
    
    public void saveUniversity(){
        mantUniversity.endConversation();
        try {
            University newUniversity = new University();
            newUniversity.setRutUniversity(universityRut);
            newUniversity.setUniversityName(universityName);
            newUniversity.setUniversityHeadquarterAdress(universityAdress);
            newUniversity.setUniversityHeadquarterEmail(universityEmail);
            newUniversity.setUniversityHeadquarterPhone(universityPhone);
            newUniversity.setUniversityState("1");
            newUniversity.setSchoolCollection(schoolsUniversity);
            universityFacade.edit(newUniversity);
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    universityName.concat(" modificada satisfactoriamente !!!"),
                    universityName.concat(" modificada satisfactoriamente !!!"));
            General.goToPage("/faces/universityList.xhtml?faces-redirect=true");
        }
        catch (Exception e) {
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/universityEdit.xhtml?faces-redirect=true");
        }
    }
    
    public void backToUniversities() {
        mantUniversity.endConversation();
        General.goToPage("/faces/universityList.xhtml");
    }
    
    public UniversityEditMB() {
    }

    public UniversityConversationMB getMantUniversity() {
        return mantUniversity;
    }

    public void setMantUniversity(UniversityConversationMB mantUniversity) {
        this.mantUniversity = mantUniversity;
    }

    public String getUniversityRut() {
        return universityRut;
    }

    public void setUniversityRut(String universityRut) {
        this.universityRut = universityRut;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityPhone() {
        return universityPhone;
    }

    public void setUniversityPhone(String universityPhone) {
        this.universityPhone = universityPhone;
    }

    public String getUniversityAdress() {
        return universityAdress;
    }

    public void setUniversityAdress(String universityAdress) {
        this.universityAdress = universityAdress;
    }

    public String getUniversityEmail() {
        return universityEmail;
    }

    public void setUniversityEmail(String universityEmail) {
        this.universityEmail = universityEmail;
    }

    public Collection<School> getSchoolsUniversity() {
        return schoolsUniversity;
    }

    public void setSchoolsUniversity(Collection<School> schoolsUniversity) {
        this.schoolsUniversity = schoolsUniversity;
    }
}