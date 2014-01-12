/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.School;
import cl.usach.pingeso.taa2.entityclasses.University;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface SchoolFacadeLocal {

    void create(School school);

    void edit(School school);

    void remove(School school);

    School find(Object id);

    List<School> findAll();

    List<School> findRange(int[] range);
    
    List<School> findByName(String nameSchool);

    int count();
    
}
