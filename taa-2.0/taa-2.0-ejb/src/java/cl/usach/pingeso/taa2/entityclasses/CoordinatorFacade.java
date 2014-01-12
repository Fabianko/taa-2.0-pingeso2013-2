/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.entityclasses;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Diego
 */
@Stateless
public class CoordinatorFacade extends AbstractFacade<Coordinator> implements CoordinatorFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CoordinatorFacade() {
        super(Coordinator.class);
    }
    
}
