/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface ProgramFacadeLocal {

    void create(Program program);

    void edit(Program program);

    void remove(Program program);

    Program find(Object id);

    List<Program> findAll();

    List<Program> findRange(int[] range);

    int count();
    
    Program findByProgramName(Object name);
}
