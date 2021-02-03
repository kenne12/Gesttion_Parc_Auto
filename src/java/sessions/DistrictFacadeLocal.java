/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.District;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author batrapi
 */
@Local
public interface DistrictFacadeLocal {
    void create(District adresse);

    void edit(District adresse);

    void remove(District adresse);

    District find(Object id);

    List<District> findAll();

    int count();
    
    int nextId();
    
    List<District> findByNom(String nom);
    List<District> findByRegion(int region);

}

