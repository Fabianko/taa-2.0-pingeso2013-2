/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Photo;
import cl.usach.pingeso.taa2.entityclasses.Student;
import java.util.List;
import javax.ejb.EJB;
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
public class PhotoFacade extends AbstractFacade<Photo> implements PhotoFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;
    @EJB
    private StudentFacadeLocal studentFacade;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhotoFacade() {
        super(Photo.class);
    }

    @Override
    public List<Photo> findByRut(String rutStudent) {
        System.out.println(rutStudent);
        Student tempStudent = studentFacade.find(rutStudent);
        Query q0 = this.em.createNamedQuery("Photo.findByRut");
        q0.setParameter("rut", tempStudent);
        List<Photo> res;
        try {
            res = (List<Photo>)q0.getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
    
}
