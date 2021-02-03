/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Structure;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface StructureFacadeLocal {

    void create(Structure structure);

    void edit(Structure structure);

    void remove(Structure structure);

    Structure find(Object id);

    List<Structure> findAll();

    List<Structure> findRange(int[] range);

    int count();
    
    int nextId() ;
    
    List<Structure> findByNom(String nom);
    
    List<Structure> findByTypeStatutArrondissement(Integer idTypestructure, Integer idStatutstructure, Integer idArrondissement);
    
    List<Structure> findByTypeStructure(Integer idTypestructure);
    
    List<Structure> findByStatutStructure(Integer idStatutstructure);
    
    List<Structure> findByArrondissement(Integer idArrondissement);
    
    List<Structure> findByTypeStatut(Integer idTypestructure, Integer idStatutstructure);
    
    List<Structure> findByTypeArrondissement(Integer idTypestructure,Integer idArrondissement); 
    
    List<Structure> findByStatutArrondissement(Integer idStatutstructure, Integer idArrondissement);
    List<Structure> findByDistrict(Integer iddistrict);
    List<Structure> findByTypeRegion(Integer idregion,Integer idpays);
    
    List<Structure>getByTypeStructure(int typeStructure); 
   
    
    
}
