/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Attendance;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface AttendanceFacadeLocal {

    void create(Attendance attendance);

    void edit(Attendance attendance);

    void remove(Attendance attendance);

    Attendance find(Object id);

    List<Attendance> findAll();

    List<Attendance> findRange(int[] range);

    int count();
    
    void photoValidation(String photoName, int[] id, double[] distance);
    
    void train(String rutStudent);
    
    boolean predict(String photoName, String rutStudent);
    
    Attendance findByCourseCode(Object courseCode);
    
    List<Date> findAllDatesByCourse(String course);
    
    int removeAll(Date attendanceDate, String courseCode);
    
}
