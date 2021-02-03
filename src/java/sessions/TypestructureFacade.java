/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Typestructure;
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
public class TypestructureFacade extends AbstractFacade<Typestructure> implements TypestructureFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypestructureFacade() {
        super(Typestructure.class);
    }
    
    @Override
    public int nextId() {
        try {
            Query query = em.createNamedQuery("Typestructure.nextId");
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
    public List<Typestructure> findByNomFr(String nomFr) {
        Query query = em.createNamedQuery("Typestructure.findByNomFr");
        query.setParameter("nomFr", nomFr);
        List listObj = query.getResultList();
        return listObj;
    }
    
    
    @Override
    public List<Typestructure> findByCodeFr(String codeFr) {
        Query query = em.createNamedQuery("Typestructure.findByCodeFr");
        query.setParameter("codeFr", codeFr);
        List listObj = query.getResultList();
        return listObj;
    }
}