/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Utilisateur;
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
public class UtilisateurFacade extends AbstractFacade<Utilisateur> implements UtilisateurFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }
    
    @Override
    public int nextId() {
        try {
            Query query = em.createNamedQuery("Utilisateur.nextId");
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
    public boolean findByLoginMdp(String login, String mdp) {
        try {
            Query query = em.createNamedQuery("Utilisateur.findByLoginMdp");
            query.setParameter("login", login).setParameter("mdp", mdp);
            List listObj = query.getResultList();
            if (!listObj.isEmpty()) {
            return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
