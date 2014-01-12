/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Assignment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface AssignmentFacadeLocal {

    void create(Assignment assignment);

    void edit(Assignment assignment);

    void remove(Assignment assignment);

    Assignment find(Object id);

    List<Assignment> findAll();

    List<Assignment> findRange(int[] range);

    int count();
    
    List<Assignment> findByRut(Object rut);
    
    Assignment findByCourseCode(Object courseCode);
}
