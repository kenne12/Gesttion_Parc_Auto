/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Vehicule;
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
public class VehiculeFacade extends AbstractFacade<Vehicule> implements VehiculeFacadeLocal {

    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculeFacade() {
        super(Vehicule.class);
    }

    @Override
    public int nextId() {
        try {
            Query query = em.createNamedQuery("Vehicule.nextId");
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
    public List<Vehicule> findByImmatriculation(String immatriculation) {
        Query query = em.createNamedQuery("Vehicule.findByImmatriculation");
        query.setParameter("immatriculation", immatriculation);
        List listObj = query.getResultList();
        return listObj;
    }

    @Override
    public List<Vehicule> getVehicule(boolean disponible, boolean reformed) {
        List<Vehicule> vehicules = null;
        try {
            Query query = em.createQuery("SELECT v FROM Vehicule v WHERE v.disponible =:disponible AND v.reformed=:reformed");
            query.setParameter("disponible", disponible);
            query.setParameter("reformed", reformed);
            vehicules = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
        }
        return vehicules;
    }

    @Override
    public List<Vehicule> getVehicule(boolean reformed) {

        List<Vehicule> vehicules = null;
        try {
            Query query = em.createQuery("SELECT v FROM Vehicule v WHERE v.datepremiereaattribution IS NOT NULL AND v.reformed=:reformed");
            query.setParameter("reformed", reformed);

            vehicules = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
        }
        return vehicules;
    }

    @Override
    public List<Vehicule> findByEtat(boolean reformed) {

        List<Vehicule> vehicules = null;
        try {
            Query query = em.createQuery("SELECT v FROM Vehicule v WHERE  v.etat=:reformed");
            query.setParameter("reformed", reformed);
            vehicules = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
        }
        return vehicules;
    }

    @Override
    public List<Vehicule> vehiculesEnCircultation() {

        List<Vehicule> vehicules = null;
        try {
            Query query = em.createQuery("SELECT v FROM Vehicule v WHERE  v.datepremiereaattribution IS NOT NULL");
            vehicules = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
        }
        return vehicules;
    }

    @Override
    public List<Vehicule> findByCategorie(int categorie) {
        List<Vehicule> vehicules = null;
        try {
            Query query = em.createQuery("SELECT v FROM Vehicule v WHERE  v.idCategorievehicule.idCategorievehicule=:categorie");            
            query.setParameter("categorie", categorie);
            vehicules = query.getResultList();
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
        }
        return vehicules;
    }
}
