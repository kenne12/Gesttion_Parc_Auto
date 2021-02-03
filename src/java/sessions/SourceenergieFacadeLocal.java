/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Sourceenergie;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface SourceenergieFacadeLocal {

    void create(Sourceenergie sourceenergie);

    void edit(Sourceenergie sourceenergie);

    void remove(Sourceenergie sourceenergie);

    Sourceenergie find(Object id);

    List<Sourceenergie> findAll();

    List<Sourceenergie> findRange(int[] range);

    int count();
    
    int nextId() ;
    
    List<Sourceenergie> findByNom(String nom);
    
}
