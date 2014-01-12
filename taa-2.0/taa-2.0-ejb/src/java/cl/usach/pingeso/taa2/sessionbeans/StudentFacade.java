/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Attendance;
import cl.usach.pingeso.taa2.entityclasses.Membership;
import cl.usach.pingeso.taa2.entityclasses.MembershipPK;
import cl.usach.pingeso.taa2.entityclasses.Student;
import java.util.ArrayList;
import java.util.Date;
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
public class StudentFacade extends AbstractFacade<Student> implements StudentFacadeLocal {
    @EJB
    private MembershipFacadeLocal membershipFacade;
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }
    
    @Override
    public List<Student> findByCourse(Object courseCode) {
        Query q0 = this.em.createNamedQuery("Membership.findByCourseCode");
        q0.setParameter("courseCode", courseCode);
        List<Membership> res;
        try {
            res = (List<Membership>)q0.getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
        List<Student> studentList = new ArrayList(res.size());
        
        for(int i=0;i<res.size();i++)
        {
            Student temp = find(res.get(i).getMembershipPK().getRut());
            studentList.add(temp);
        }
        return studentList;
    }
    
    @Override
    public List<Student> findByCourseComplement(Object courseCode) {
        List<Student> res = findAll();
        MembershipPK res4 = new MembershipPK();
        List<Student> res3 = new ArrayList();
        for(int i=0;i<res.size();i++)
        {
           res4.setCourseCode((String)courseCode);
           res4.setRut(res.get(i).getRut());
           if(membershipFacade.find(res4) == null)
           {
                res3.add(res.get(i));
           }
        }
        return res3;
    }
    
    @Override
    public List<Student> findByCourseDate(Object courseCode, Date dateNow) {
        Query q0 = this.em.createNamedQuery("Attendance.findByCourseCodeDate");
        q0.setParameter("courseCode", courseCode);
        q0.setParameter("attendanceDate", dateNow);
        List<Attendance> res;
        try {
            res = (List<Attendance>)q0.getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
        
        List<Student> studentList = findByCourse(courseCode);
        
        for(int i=0;i<res.size();i++)
        {
            Student temp = find(res.get(i).getStudent().getRut());
            studentList.remove(temp);
        }
        return studentList;
    }
}
