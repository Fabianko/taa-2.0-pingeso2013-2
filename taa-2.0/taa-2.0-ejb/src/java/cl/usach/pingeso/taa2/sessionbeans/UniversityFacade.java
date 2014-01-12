/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.University;
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
public class UniversityFacade extends AbstractFacade<University> implements UniversityFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UniversityFacade() {
        super(University.class);
    }
    
    @Override
    public University findByUniversityName(String name) {
        Query q0 = this.em.createNamedQuery("University.findByUniversityName");
        q0.setParameter("universityName", name);
        University res;
        try {
            res = (University)q0.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}
