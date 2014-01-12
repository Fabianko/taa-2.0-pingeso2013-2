/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface CoordinatorFacadeLocal {

    void create(Coordinator coordinator);

    void edit(Coordinator coordinator);

    void remove(Coordinator coordinator);

    Coordinator find(Object id);

    List<Coordinator> findAll();

    List<Coordinator> findRange(int[] range);

    int count();
    
}
