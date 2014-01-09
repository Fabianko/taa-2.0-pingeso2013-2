/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Course;
import cl.usach.pingeso.taa2.sessionbeans.CourseFacadeLocal;
import java.util.AbstractList;
import java.util.*;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Nico
 */
@Named(value = "assigmenStudentMenbership")
@Dependent
public class assigmenStudentMenbership {

    /**
     * Creates a new instance of assigmenStudentMenbership
     */
     private CourseFacadeLocal CourseFacade;
    private HashMap Codcourse;
    public assigmenStudentMenbership() {
    }
    public HashMap codeList(){
        Codcourse=new HashMap<String,Course>();
       List courses =CourseFacade.findAll();
        for (Object course : courses) {
            Codcourse.put(course.toString(), course);
        }
 
        
        
        return Codcourse;
        
    }
}
