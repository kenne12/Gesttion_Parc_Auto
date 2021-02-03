/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.District;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author batrapi
 */
@Stateless
public class DistrictFacade extends AbstractFacade<District> implements DistrictFacadeLocal{
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DistrictFacade() {
        super(District.class);
    }


    @Override
    public int nextId() {
        try {
            Query query = em.createNamedQuery("Adresse.nextId");
            List listObj = query.getResultList();
            if (!listObj.isEmpty()) {
                return ((Integer) listObj.get(0)) + 1;
            } else {
                return 1;
            }
        } catch (Exception e) {
            return 1;
        }

    }

    @Override
    public List<District> findByNom(String nom) {
        Query query = em.createNamedQuery("District.findByNom");
        query.setParameter("nom", nom);
        List listObj = query.getResultList();
        return listObj;
    }
    
    @Override
    public List<District> findByRegion(int region){
        List<District>districts = null;
        Query query;
        try {
            query = em.createQuery("SELECT d FROM District d WHERE d.idRegion.idRegion=:region");
            query.setParameter("region", region);
            districts = query.getResultList();
        } catch (Exception e) {
            
        }
        
        return districts;
   } 
   
}
