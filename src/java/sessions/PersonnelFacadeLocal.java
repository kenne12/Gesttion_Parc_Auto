/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Personnel;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author barackhussein
 */
@Local
public interface PersonnelFacadeLocal {

    void create(Personnel personnel);

    void edit(Personnel personnel);

    void remove(Personnel personnel);

    Personnel find(Object id);

    List<Personnel> findAll();

    List<Personnel> findRange(int[] range);

    int count();
            
    int nextId();
    
     List<Personnel> findByNom(String nom);
     
     List<Personnel> findByCni(String cni);
     
     List<Personnel> findByMatricule(String matricule);
     
     List<Personnel> findByMatriculeCni(String matricule, String cni);
     
     List<Personnel> findByAllChamp(String matricule, String cni, Date dateNaissance,String lieuNaissance,String nationalite,Date dateRecrutement,String photo,String sexe);
     int AttributionVehicule();
     List<Personnel> findByNotIdPersonnel(Integer id);
}
