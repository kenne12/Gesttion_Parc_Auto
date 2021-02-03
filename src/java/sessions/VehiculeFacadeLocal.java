/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Vehicule;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface VehiculeFacadeLocal {

    void create(Vehicule vehicule);

    void edit(Vehicule vehicule);

    void remove(Vehicule vehicule);

    Vehicule find(Object id);

    List<Vehicule> findAll();

    List<Vehicule> findRange(int[] range);

    int count();

    int nextId();

    List<Vehicule> findByImmatriculation(String immatriculation);

    List<Vehicule> getVehicule(boolean disponible, boolean reformed);

    List<Vehicule> getVehicule(boolean reformed);

    List<Vehicule> findByEtat(boolean reformed);
    List<Vehicule>vehiculesEnCircultation();
    List<Vehicule>findByCategorie(int categorie);
}
