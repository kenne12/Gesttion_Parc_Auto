/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Attribution;
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
public class AttributionFacade extends AbstractFacade<Attribution> implements AttributionFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttributionFacade() {
        super(Attribution.class);
    }
    
    @Override
    public int nextId() {
        try {
            Query query = em.createNamedQuery("Attribution.nextId");
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
    public List<Attribution> findAll(){
        Query query = em.createNamedQuery("Attribution.findAll");
        return query.getResultList();
        
    }
    @Override
    public List<Attribution> findAllRestitution(){
        Query query = em.createNamedQuery("Attribution.findAllRestitution");
        return query.getResultList();
        
    }
    
    @Override
    public List<Attribution>get(int region,boolean disponible,boolean restitue){
        List<Attribution>attributions = null;        
        Query query;        
        try {
            query = em.createQuery("SELECT a FROM Attribution a WHERE a.idPersonnel.idStructure.idRegion.idRegion =:region AND a.idVehicule.disponible=:disponible AND a.attr =:restitution");
            query.setParameter("region", region);
            query.setParameter("disponible", disponible);
            query.setParameter("restitution", restitue); 
           attributions = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        return attributions;
       
    }
    
    @Override
    public List<Attribution>getByStructure(int structure){
        List<Attribution>attributions = null;        
        Query query;        
        try {
            query = em.createQuery("SELECT a FROM Attribution a WHERE a.idPersonnel.idStructure.idStructure =:structure");
            query.setParameter("structure", structure);            
            attributions = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        return attributions;       
    }
    
    @Override
    public List<Attribution>get(boolean restitue,boolean reformed){
        List<Attribution>attributions = null;        
        Query query;        
        try {
            query = em.createQuery("SELECT a FROM Attribution a WHERE a.attr=:restitue AND a.idVehicule.reformed=:reformed");
            query.setParameter("restitue", restitue);
            query.setParameter("reformed", reformed);
            attributions = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }
        return attributions; 
    }
    
    @Override
    public List<Attribution>get(int structure,int categorieVehicule,String etat){
        List<Attribution>attributions = null;        
        Query query;          
        try {
            query = em.createQuery("SELECT a FROM Attribution a WHERE a.idPersonnel.idStructure.idStructure=:structure AND a.idVehicule.idCategorievehicule.idCategorievehicule=:categorieVehicule AND a.idVehicule.etat=:etat");
            query.setParameter("structure", structure);
            query.setParameter("categorieVehicule", categorieVehicule);
            query.setParameter("etat", etat); 
            attributions = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }        
        return attributions;        
    }
    
    @Override
    public List<Attribution>get(int vehicule){       
        List<Attribution>attributions = null;        
        Query query;          
        try {
            query = em.createQuery("SELECT a FROM Attribution a WHERE a.idVehicule.idVehicule=:vehicule");
            query.setParameter("vehicule", vehicule);            
            attributions = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }        
        return attributions;        
    }
    
    @Override
    public Attribution get(int vehicule,boolean attribue){
        Attribution attribution = null;        
        Query query;          
        try {
            query = em.createQuery("SELECT a FROM Attribution a WHERE a.idVehicule.idVehicule=:vehicule AND a.attr=:attribue");
            query.setParameter("vehicule", vehicule);            
            query.setParameter("attribue", attribue);   
            attribution = (Attribution) query.getSingleResult();
        } catch (Exception e) {
            e.getMessage();
        } 
       
        return attribution;     
    }
    
    @Override
    public List<Attribution>getByPersonnel(int personnel,boolean reformed,boolean  restitue){
        
        List<Attribution> attributions = null;        
        Query query;          
        try {
            query = em.createQuery("SELECT a FROM Attribution a WHERE a.idPersonnel.idPersonnel=:personnel AND a.idVehicule.reformed=:reformed AND a.attr=:restitue");
            query.setParameter("reformed", reformed);            
            query.setParameter("personnel", personnel);
            query.setParameter("restitue", restitue);   
            attributions = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
        }        
        return attributions;        
    }

}

