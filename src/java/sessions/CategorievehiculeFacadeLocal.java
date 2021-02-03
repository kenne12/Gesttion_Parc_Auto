/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Categorievehicule;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface CategorievehiculeFacadeLocal {

    void create(Categorievehicule categorievehicule);

    void edit(Categorievehicule categorievehicule);

    void remove(Categorievehicule categorievehicule);

    Categorievehicule find(Object id);

    List<Categorievehicule> findAll();

    List<Categorievehicule> findRange(int[] range);

    int count();
    
    int nextId() ;
    
    List<Categorievehicule> findByNom(String nom);
    
    
}
