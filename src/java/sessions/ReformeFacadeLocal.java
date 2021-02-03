/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Reforme;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface ReformeFacadeLocal {

    void create(Reforme reforme);

    void edit(Reforme reforme);

    void remove(Reforme reforme);

    Reforme find(Object id);

    List<Reforme> findAll();

    List<Reforme> findRange(int[] range);

    int count();
    
    int nextId();
    
}
