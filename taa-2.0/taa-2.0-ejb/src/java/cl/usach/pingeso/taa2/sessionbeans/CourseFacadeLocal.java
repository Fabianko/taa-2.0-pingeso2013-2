/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Course;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nico
 */
@Local
public interface CourseFacadeLocal {

    void create(Course course);

    void edit(Course course);

    void remove(Course course);

    Course find(Object id);

    List<Course> findAll();

    List<Course> findRange(int[] range);

    int count();
    
    List<Course> findByRut(Object rut);
    
    Course findByCourseName(Object name);
    
}
