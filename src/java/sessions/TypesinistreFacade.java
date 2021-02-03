/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Typesinistre;
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
public class TypesinistreFacade extends AbstractFacade<Typesinistre> implements TypesinistreFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypesinistreFacade() {
        super(Typesinistre.class);
    }
    
    @Override
    public int nextId() {
        try {
            Query query = em.createNamedQuery("Typesinistre.nextId");
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
    public List<Typesinistre> findByNom(String nom) {
        Query query = em.createNamedQuery("Typesinistre.findByNom");
        query.setParameter("nom", nom);
        List listObj = query.getResultList();
        return listObj;
    }
}
