/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Typesinistre;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface TypesinistreFacadeLocal {

    void create(Typesinistre typesinistre);

    void edit(Typesinistre typesinistre);

    void remove(Typesinistre typesinistre);

    Typesinistre find(Object id);

    List<Typesinistre> findAll();

    List<Typesinistre> findRange(int[] range);

    int count();
    
    int nextId() ;
    
    List<Typesinistre> findByNom(String nom);
    
}
