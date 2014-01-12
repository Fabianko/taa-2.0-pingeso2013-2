/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
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
public class ProgramFacade extends AbstractFacade<Program> implements ProgramFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ProgramFacade() {
        super(Program.class);
    }
    
    @Override
    public Program findByProgramName(Object programName) {
        Query q0 = this.em.createNamedQuery("Program.findByProgramName");
        q0.setParameter("programName", programName);
        Program res;
        try {
            res = (Program)q0.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}