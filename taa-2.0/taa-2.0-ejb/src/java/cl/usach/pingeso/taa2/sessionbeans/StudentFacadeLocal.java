/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Student;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nico
 */
@Local
public interface StudentFacadeLocal {

    void create(Student student);

    void edit(Student student);

    void remove(Student student);

    Student find(Object id);

    List<Student> findAll();

    List<Student> findRange(int[] range);
    
    List<Student> findByCourse(Object courseCode);
    
    List<Student> findByCourseComplement(Object courseCode);

    int count();
    
    List<Student> findByCourseDate(Object courseCode, Date dateNow);
    
}
