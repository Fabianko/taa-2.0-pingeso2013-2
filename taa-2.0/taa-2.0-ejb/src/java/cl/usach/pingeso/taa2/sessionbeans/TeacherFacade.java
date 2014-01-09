/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Teacher;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nico
 */
@Stateless
public class TeacherFacade extends AbstractFacade<Teacher> implements TeacherFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeacherFacade() {
        super(Teacher.class);
    }
    
}
