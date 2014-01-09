/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.CourseHistory;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nico
 */
@Local
public interface CourseHistoryFacadeLocal {

    void create(CourseHistory courseHistory);

    void edit(CourseHistory courseHistory);

    void remove(CourseHistory courseHistory);

    CourseHistory find(Object id);

    List<CourseHistory> findAll();

    List<CourseHistory> findRange(int[] range);

    int count();
    
}
