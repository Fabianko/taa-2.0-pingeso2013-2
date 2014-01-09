/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Membership;
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
public class MembershipFacade extends AbstractFacade<Membership> implements MembershipFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MembershipFacade() {
        super(Membership.class);
    }

    @Override
    public Membership findByCourseCode(Object courseCode) {
        Query q0 = this.em.createNamedQuery("Membership.findByCourseCode");
        q0.setParameter("courseCode", courseCode);
        Membership res;
        try {
            res = (Membership)q0.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}
