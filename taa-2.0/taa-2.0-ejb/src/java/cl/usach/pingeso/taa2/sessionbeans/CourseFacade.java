/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Assignment;
import cl.usach.pingeso.taa2.entityclasses.Course;
import java.util.ArrayList;
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
public class CourseFacade extends AbstractFacade<Course> implements CourseFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CourseFacade() {
        super(Course.class);
    }
    
    @Override
    public List<Course> findByRut(Object rut) {
        Query q0 = this.em.createNamedQuery("Assignment.findByRut");
        q0.setParameter("rut", rut);
        List<Assignment> res;
        try {
            res = (List<Assignment>)q0.getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
        List<Course> courseList;
        courseList = new ArrayList(res.size());
        
        for(int i=0;i<res.size();i++)
        {
            Course temp = find(res.get(i).getAssignmentPK().getCourseCode());
            courseList.add(temp);
        }
        return courseList;
    }
    
    @Override
    public Course findByCourseName(Object courseName) {
        Query q0;
        q0 = this.em.createNamedQuery("Course.findByCourseName");
        q0.setParameter("courseName", courseName);
        Course res;
        try {
            res = (Course)q0.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
}
