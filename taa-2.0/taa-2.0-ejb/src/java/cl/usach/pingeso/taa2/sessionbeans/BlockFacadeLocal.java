/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Block;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diego
 */
@Local
public interface BlockFacadeLocal {

    void create(Block block);

    void edit(Block block);

    void remove(Block block);

    Block find(Object id);

    List<Block> findAll();

    List<Block> findRange(int[] range);

    int count();
    
}
