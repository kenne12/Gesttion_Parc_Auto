/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Attribution;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface AttributionFacadeLocal {

    void create(Attribution attribution);

    void edit(Attribution attribution);

    void remove(Attribution attribution);

    Attribution find(Object id);

    List<Attribution> findAll();
    List<Attribution> findAllRestitution();

    List<Attribution> findRange(int[] range);

    int count();
    
    int nextId();
    
    List<Attribution>get(int region,boolean disponible,boolean restitue);
    
    List<Attribution>get(boolean restitue,boolean reformed); 
    
    List<Attribution>getByStructure(int structure); 
    
    List<Attribution>get(int structure,int categorieVehicule,String etat); 
    List<Attribution>get(int vehicule); 
    
    Attribution get(int vehicule,boolean attribue); 
    
    List<Attribution>getByPersonnel(int personnel,boolean reformed,boolean  restitue);
    
   
  
    
  
}
