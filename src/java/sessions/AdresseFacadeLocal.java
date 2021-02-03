/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Adresse;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface AdresseFacadeLocal {

    void create(Adresse adresse);

    void edit(Adresse adresse);

    void remove(Adresse adresse);

    Adresse find(Object id);

    List<Adresse> findAll();

    List<Adresse> findRange(int[] range);

    int count();
    
    int nextId();
    
    List<Adresse> findByTel(String tel);
    
    List<Adresse> findByEmail(String email);
    
    List<Adresse> findByBp(String bp);
    
    List<Adresse> findBySiteweb(String siteweb);
    
}
