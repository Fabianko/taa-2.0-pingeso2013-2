/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nico
 */
@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();
    
}
