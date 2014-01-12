/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Assignment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Diego
 */
@Stateless
public class AssignmentFacade extends AbstractFacade<Assignment> implements AssignmentFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AssignmentFacade() {
        super(Assignment.class);
    }
    
    @Override
    public List<Assignment> findByRut(Object rut) {
        Query q = this.em.createNamedQuery("Assignment.findByRut");
        q.setParameter("rut", rut);
        List<Assignment> res;
        try {
            res = (List<Assignment>)q.getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
    
    @Override
    public Assignment findByCourseCode(Object courseCode) {
        Query q = this.em.createNamedQuery("Assignment.findByCourseCode");
        q.setParameter("courseCode", courseCode);
        Assignment res;
        try {
            res = (Assignment)q.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}
