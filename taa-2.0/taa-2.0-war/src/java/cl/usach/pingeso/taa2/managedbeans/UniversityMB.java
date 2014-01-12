package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.University;
import cl.usach.pingeso.taa2.managedbeans.login.SessionValidator;
import cl.usach.pingeso.taa2.sessionbeans.UniversityFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;  
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

/**
 *
 * @author Diego
 */
@Named(value = "universityMB")
@RequestScoped
public class UniversityMB implements Serializable {
    @EJB
    private UniversityFacadeLocal universityFacade;
    private List<University> universityList;
    private List<University> universityFiltered;
    private boolean showBtnUniversity;
     private boolean showcolumnUniversity;
    @Inject 
    private UniversityConversationMB mantUniversity;
    @Inject
    private SessionValidator session;
    
    public UniversityMB() {
    }
    
    @PostConstruct
    public void init() {
        if(General.isUserInRole("Administrador")){
            this.universityList = universityFacade.findAll();
        }
        else{
            this.universityList = universityFacade.findAll();
            //this.universityList= universityFacade.findByRut(session.userRut());
        }
        this.showcolumnUniversity = true;//General.isUserInRole("Profesor");
        this.showBtnUniversity = true;//General.isUserInRole("Administrador");
    }
    
    public List<University> getUniversityList() {
        return universityList;
    }
    
    public void setUniversityList(List<University> list) {
        this.universityList = list;
    }

    public List<University> getUniversityFiltered() {
        return universityFiltered;
    }

    public void setUniversityFiltered(List<University> universityFiltered) {
        this.universityFiltered = universityFiltered;
    }
    
    public void viewDetailsUniversity(String universityRut) {
        University universityViewDetails = universityFacade.find(universityRut);
        if (universityViewDetails != null) {
            this.mantUniversity.beginConversation();
            this.mantUniversity.setIdUniversityDetails(universityRut);
            General.goToPage("/faces/viewDetailsUniversity.xhtml?cid=".concat(this.mantUniversity.getConversation().getId()));
        }
        else {
            this.mantUniversity.clean();
            General.goToPage("/faces/universityList.xhtml");
        }
    }
    
    public void addUniversity() {
       General.goToPage("/faces/universityRegister.xhtml");   
    }
    
    public void editUniversity(String universityRut) {
        University universityEdit = universityFacade.find(universityRut);
        if (universityEdit != null) {
            this.mantUniversity.beginConversation();
            this.mantUniversity.setIdUniversityDetails(universityRut);
            General.goToPage("/faces/universityEdit.xhtml?cid=".concat(this.mantUniversity.getConversation().getId()));
        }
        else {
            this.mantUniversity.clean();
            General.goToPage("/faces/universityList.xhtml");
        }
    }
    
    public void deleteUniversity(String universityRut) {
        University temp = new University();
        temp.setRutUniversity(universityRut);
        boolean resultado;
        String name ="";
        try
        {
            University tempUniversity = universityFacade.find(universityRut);
            name = tempUniversity.getUniversityName();
            universityFacade.remove(tempUniversity);
            resultado = true;
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    name.concat( " eliminada satisfactoriamente !!!"),
                    name.concat( " eliminada satisfactoriamente !!!"));
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No es posible eliminar la universidad mientras tenga facultades o escuelas registradas.",
                    "No es posible eliminar la universidad mientras tenga facultades o escuelas registradas.");
        }
        General.goToPage("/faces/universityList.xhtml?faces-redirect=true");
    }
    
    public void backToUniversities() {
       General.goToPage("/faces/UniversityList.xhtml");    
    }
    
    public boolean isShowcolumn() {
        return showcolumnUniversity;
    }

    public void setShowcolumn(boolean showcolumnUniversity) {
        this.showcolumnUniversity = showcolumnUniversity;
    }
    
    public boolean isShowBtnUniversity() {
        return showBtnUniversity;
    }

    public void setShowBtnUniversity(boolean showBtnUniversity) {
        this.showBtnUniversity = showBtnUniversity;
    }
}