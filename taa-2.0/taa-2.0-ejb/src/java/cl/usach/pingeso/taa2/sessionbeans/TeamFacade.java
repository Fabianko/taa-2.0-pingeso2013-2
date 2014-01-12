/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Team;
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
public class TeamFacade extends AbstractFacade<Team> implements TeamFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeamFacade() {
        super(Team.class);
    }
    
    @Override
    public Team findByTeamName(Object name) {
        Query q0 = this.em.createNamedQuery("Team.findByTeamName");
        q0.setParameter("teamName", name);
        Team res;
        try {
            res = (Team)q0.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}
