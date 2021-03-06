/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Adresse;
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
public class AdresseFacade extends AbstractFacade<Adresse> implements AdresseFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdresseFacade() {
        super(Adresse.class);
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
    public List<Adresse> findByTel(String tel) {
        Query query = em.createNamedQuery("Adresse.findByTel");
        query.setParameter("tel", tel);
        List listObj = query.getResultList();
        return listObj;
    }
    
    @Override
    public List<Adresse> findByEmail(String email) {
        Query query = em.createNamedQuery("Adresse.findByEmail");
        query.setParameter("email", email);
        List listObj = query.getResultList();
        return listObj;
    }
    
    @Override
    public List<Adresse> findByBp(String bp) {
        Query query = em.createNamedQuery("Adresse.findByBp");
        query.setParameter("bp", bp);
        List listObj = query.getResultList();
        return listObj;
    }
    
    @Override
    public List<Adresse> findBySiteweb(String siteweb) {
        Query query = em.createNamedQuery("Adresse.findBySiteweb");
        query.setParameter("siteweb", siteweb);
        List listObj = query.getResultList();
        return listObj;
    }
    
}
