/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Roleutilisateur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author barackhussein
 */
@Stateless
public class RoleutilisateurFacade extends AbstractFacade<Roleutilisateur> implements RoleutilisateurFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleutilisateurFacade() {
        super(Roleutilisateur.class);
    }
    
}
