/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Norme;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface NormeFacadeLocal {

    void create(Norme norme);

    void edit(Norme norme);

    void remove(Norme norme);

    Norme find(Object id);

    List<Norme> findAll();

    List<Norme> findRange(int[] range);

    int count();
    
    int nextId();

    List<Norme> findByMinimum(int minimum);
    
    List<Norme> findByMaximum(int maximum);
    
    List<Norme> findByMinMax(int minimum,int maximum);
    
    List<Norme>findByTypeStructure(int typeStructure, int categorieVehicule); 
    Norme get(int typeStructure,int categorieVehicule); 
    List<Norme>findByTypeStructure(int typeStructure); 
    
 
    
    
}
