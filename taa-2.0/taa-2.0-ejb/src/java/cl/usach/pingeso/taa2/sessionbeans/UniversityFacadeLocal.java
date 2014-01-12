/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.University;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface UniversityFacadeLocal {

    void create(University university);

    void edit(University university);

    void remove(University university);

    University find(Object id);

    List<University> findAll();

    List<University> findRange(int[] range);
    
    University findByUniversityName(String name);

    int count();
    
}
