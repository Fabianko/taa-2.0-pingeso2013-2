/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Timetable;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface TimetableFacadeLocal {

    void create(Timetable timetable);

    void edit(Timetable timetable);

    void remove(Timetable timetable);

    Timetable find(Object id);

    List<Timetable> findAll();

    List<Timetable> findRange(int[] range);

    int count();
    
}
