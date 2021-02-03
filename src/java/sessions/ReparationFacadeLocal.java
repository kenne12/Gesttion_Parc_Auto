/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Reparation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface ReparationFacadeLocal {

    void create(Reparation reparation);

    void edit(Reparation reparation);

    void remove(Reparation reparation);

    Reparation find(Object id);

    List<Reparation> findAll();

    List<Reparation> findRange(int[] range);

    int count();
    
    int nextId();
    
}
