/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Norme;
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
public class NormeFacade extends AbstractFacade<Norme> implements NormeFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NormeFacade() {
        super(Norme.class);
    }
    
        @Override
    public int nextId() {
        try {
            Query query = em.createNamedQuery("Norme.nextId");
            List listObj = query.getResultList();
            if (!listObj.isEmpty()) {
                return ((Integer) listObj.get(0)) + 1;
            } else {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

    }
    
    @Override
    public List<Norme> findByMinimum(int minimum) {
        Query query = em.createNamedQuery("Norme.findByMinimum");
        query.setParameter("minimum", minimum);
        List listObj = query.getResultList();
        return listObj;
    }
    
    @Override
    public List<Norme> findByMaximum(int maximum) {
        Query query = em.createNamedQuery("Norme.findByMaximum");
        query.setParameter("maximum", maximum);
        List listObj = query.getResultList();
        return listObj;
    }
    
    @Override
    public List<Norme> findByMinMax(int minimum,int maximum) {
        Query query = em.createNamedQuery("Norme.findByMinMax");
        query.setParameter("minimum", minimum).setParameter("maximum", maximum);
        List listObj = query.getResultList();
        return listObj;
    }
    
    @Override
    public  List<Norme>findByTypeStructure(int typeStructure,int categorieVehicule){
        List<Norme> normes= null;
        Query query;
        try {
            query = em.createQuery("SELECT n From Norme n WHERE n.idTypestructure.idTypestructure = :typeStructure AND n.idCategorievehicule.idCategorievehicule = :categorievehicule");
            query.setParameter("typeStructure", typeStructure);
            query.setParameter("categorievehicule", categorieVehicule);            
            normes = query.getResultList();             
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
        }        
        return normes;       
    }
    
    @Override
    public  Norme get(int typeStructure,int categorieVehicule){
        Norme norme= null;
        Query query;
        try {
            query = em.createQuery("SELECT n From Norme n WHERE n.idTypestructure.idTypestructure = :typeStructure AND n.idCategorievehicule.idCategorievehicule = :categorievehicule");
            query.setParameter("typeStructure", typeStructure);
            query.setParameter("categorievehicule", categorieVehicule);            
            norme = (Norme) query.getSingleResult();             
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
        }        
        return norme;       
    }
    
    
    @Override
    public  List<Norme>findByTypeStructure(int typeStructure){
        List<Norme> normes= null;
        Query query;
        try {
            query = em.createQuery("SELECT n From Norme n WHERE n.idTypestructure.idTypestructure = :typeStructure");
            query.setParameter("typeStructure", typeStructure);                        
            normes = query.getResultList();             
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
        }        
        return normes;       
    }

}

