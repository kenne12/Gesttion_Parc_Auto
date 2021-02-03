/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Restitution;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface RestitutionFacadeLocal {

    void create(Restitution restitution);

    void edit(Restitution restitution);

    void remove(Restitution restitution);

    Restitution find(Object id);

    List<Restitution> findAll();

    List<Restitution> findRange(int[] range);

    int count();
    
    int nextId();
    
}
