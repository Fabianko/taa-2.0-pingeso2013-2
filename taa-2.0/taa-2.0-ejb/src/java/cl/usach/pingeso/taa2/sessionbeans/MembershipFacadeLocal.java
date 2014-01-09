/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Membership;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface MembershipFacadeLocal {

    void create(Membership membership);

    void edit(Membership membership);

    void remove(Membership membership);

    Membership find(Object id);

    List<Membership> findAll();

    List<Membership> findRange(int[] range);
    
    int count();
    
     Membership findByCourseCode(Object courseCode);
    
}
