/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.StudentTeam;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface StudentTeamFacadeLocal {

    void create(StudentTeam studentTeam);

    void edit(StudentTeam studentTeam);

    void remove(StudentTeam studentTeam);

    StudentTeam find(Object id);

    List<StudentTeam> findAll();

    List<StudentTeam> findRange(int[] range);

    int count();
    
}
