/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Structure;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author barackhussein
 */
@Stateless
public class StructureFacade extends AbstractFacade<Structure> implements StructureFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StructureFacade() {
        super(Structure.class);
    }
    
     @Override
    public int nextId() {
        try {
            Query query = em.createNamedQuery("Structure.nextId");
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
    public List<Structure> findByNom(String nom) {
        Query query = em.createNamedQuery("Structure.findByNom");
        query.setParameter("nom", nom);
        List listObj = query.getResultList();
        return listObj;
    }

    @Override
    public List<Structure> findByTypeStatutArrondissement(Integer idTypestructure, Integer idStatutstructure, Integer idArrondissement) {
        Query query = em.createNamedQuery("Structure.findByTypeStatutArrondissement");
        query.setParameter("idTypestructure", idTypestructure);
        query.setParameter("idStatutstructure", idStatutstructure);
        query.setParameter("idArrondissement", idArrondissement);
        return query.getResultList();
    }

    @Override
    public List<Structure> findByTypeStructure(Integer idTypestructure) {
        Query query = em.createNamedQuery("Structure.findByTypeStructure");
        query.setParameter("idTypestructure", idTypestructure);
        return query.getResultList();
    }

    @Override
    public List<Structure> findByStatutStructure(Integer idStatutstructure) {
        Query query = em.createNamedQuery("Structure.findByStatutStructure");
        query.setParameter("idStatutstructure", idStatutstructure);
        return query.getResultList();
    }

    @Override
    public List<Structure> findByArrondissement(Integer idArrondissement) {
        Query query = em.createNamedQuery("Structure.findByArrondissement");
        query.setParameter("idArrondissement", idArrondissement);
        return query.getResultList();
    }
    
    @Override
    public List<Structure> findByTypeStatut(Integer idTypestructure, Integer idStatutstructure) {
        Query query = em.createNamedQuery("Structure.findByTypeStatut");
        query.setParameter("idTypestructure", idTypestructure);
        query.setParameter("idStatutstructure", idStatutstructure);
        return query.getResultList();
    }
    
    @Override
    public List<Structure> findByTypeArrondissement(Integer idTypestructure,Integer idArrondissement) {
        Query query = em.createNamedQuery("Structure.findByTypeArrondissement");
        query.setParameter("idTypestructure", idTypestructure);
        query.setParameter("idArrondissement", idArrondissement);
        return query.getResultList();
    }
    
    @Override
    public List<Structure> findByStatutArrondissement(Integer idStatutstructure, Integer idArrondissement) {
        Query query = em.createNamedQuery("Structure.findByStatutArrondissement");
        query.setParameter("idStatutstructure", idStatutstructure);
        query.setParameter("idArrondissement", idArrondissement);
        return query.getResultList();
    }

    @Override
    public List<Structure> findByDistrict(Integer iddistrict) {
        Query query = em.createNamedQuery("Structure.findDistrict");
        query.setParameter("district", iddistrict);
        List listObj = query.getResultList();
        return listObj;
   }

    @Override
    public List<Structure> findByTypeRegion(Integer idregion, Integer idpays) {
        Query query = em.createNamedQuery("Structure.findByTypeRegion");
        query.setParameter("idRegion", idregion);
        query.setParameter("idPays", idpays);
        return query.getResultList();        
    }
    
    @Override
    public List<Structure>getByTypeStructure(int typeStructure){
        List<Structure>structures = null;
        Query query;
        try {
            query = em.createQuery("SELECT s FROM Structure s WHERE s.idTypestructure.idTypestructure=:typeStructure");
            query.setParameter("typeStructure", typeStructure);
            structures = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        return structures;        
    }
    
    
}

