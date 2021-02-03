/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Fonction;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface FonctionFacadeLocal {

    void create(Fonction fonction);

    void edit(Fonction fonction);

    void remove(Fonction fonction);

    Fonction find(Object id);

    List<Fonction> findAll();

    List<Fonction> findRange(int[] range);

    int count();
    
    int nextId();
    
     List<Fonction> findByNom(String nom);
    
}
