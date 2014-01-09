/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.managedbeans.login.Session;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Nico
 */
@Named(value = "assigmenTeacherCourse")
@RequestScoped
public class AssigmenTeacherCourse {

    /**
     * Creates a new instance of AssigmenTeacherCourse
     */
    String rut;

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
        System.out.println(rut);
    }

    
    public AssigmenTeacherCourse() {
        
    }
}
