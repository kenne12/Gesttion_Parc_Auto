/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Restitution;
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
public class RestitutionFacade extends AbstractFacade<Restitution> implements RestitutionFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RestitutionFacade() {
        super(Restitution.class);
    }
    
    @Override
    public int nextId() {
         try {
             System.out.println("Inside nextId");
            Query query = em.createNamedQuery("Restitution.nextId");
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
    
    
}
