/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.School;
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
public class SchoolFacade extends AbstractFacade<School> implements SchoolFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SchoolFacade() {
        super(School.class);
    }

    @Override
    public List<School> findByName(String nameSchool) {
        Query q0 = this.em.createNamedQuery("School.findBySchoolName");
        q0.setParameter("schoolName", nameSchool);
        List<School> res;
        try {
            res = (List<School>)q0.getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}