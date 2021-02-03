/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Modeacquisition;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface ModeacquisitionFacadeLocal {

    void create(Modeacquisition modeacquisition);

    void edit(Modeacquisition modeacquisition);

    void remove(Modeacquisition modeacquisition);

    Modeacquisition find(Object id);

    List<Modeacquisition> findAll();

    List<Modeacquisition> findRange(int[] range);

    int count();
    
    int nextId() ;
    
    List<Modeacquisition> findByNom(String nom);
    
}
