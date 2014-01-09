/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Photo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nico
 */
@Local
public interface PhotoFacadeLocal {

    void create(Photo photo);

    void edit(Photo photo);

    void remove(Photo photo);

    Photo find(Object id);

    List<Photo> findAll();

    List<Photo> findRange(int[] range);

    int count();
    
    List<Photo> findByRut(String rutStudent);
}
