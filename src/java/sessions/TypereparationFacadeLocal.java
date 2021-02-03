/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Typereparation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface TypereparationFacadeLocal {

    void create(Typereparation typereparation);

    void edit(Typereparation typereparation);

    void remove(Typereparation typereparation);

    Typereparation find(Object id);

    List<Typereparation> findAll();

    List<Typereparation> findRange(int[] range);

    int count();
    
    int nextId() ;
    
    List<Typereparation> findByNom(String nom);
    
}
