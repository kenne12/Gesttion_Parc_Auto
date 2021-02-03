/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessions;

import entities.Personnel;
import java.util.Date;
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
public class PersonnelFacade extends AbstractFacade<Personnel> implements PersonnelFacadeLocal {
    @PersistenceContext(unitName = "TestProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonnelFacade() {
        super(Personnel.class);
    }
    
    @Override
    public int nextId() {
        try {
            Query query = em.createNamedQuery("Personnel.nextId");
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
    public List<Personnel> findByNotIdPersonnel(Integer id) {
        Query query = em.createNamedQuery("Personnel.findByNotIdPersonnel");
        query.setParameter("id", id);
        List listObj = query.getResultList();
        return listObj;
    }
    
    @Override
    public List<Personnel> findByNom(String nom) {
        Query query = em.createNamedQuery("Personnel.findByNomPrenom");
        query.setParameter("nom", nom);
        List listObj = query.getResultList();
        return listObj;
    }

    @Override
    public List<Personnel> findByCni(String cni) {
        Query query = em.createNamedQuery("Personnel.findByCni");
        query.setParameter("cni", cni);
        return query.getResultList();
    }

    @Override
    public List<Personnel> findByMatricule(String matricule) {
        Query query = em.createNamedQuery("Personnel.findByMatricule");
        query.setParameter("matricule", matricule);
        return query.getResultList();
    }
    
    @Override
    public List<Personnel> findByMatriculeCni(String matricule, String cni) {
        Query query = em.createNamedQuery("Personnel.findByMatriculeCni");
        query.setParameter("matricule", matricule).setParameter("cni", cni);
        return query.getResultList();
    }
    
    @Override
    public List<Personnel> findByAllChamp(String matricule, String cni, Date dateNaissance,String lieuNaissance,String nationalite,Date dateRecrutement,String photo,String sexe) {
        Query query = em.createNamedQuery("Personnel.findByAllChamp");
        query.setParameter("matricule", matricule).setParameter("cni", cni).setParameter("dateNaissance", dateNaissance).setParameter("lieuNaissance", lieuNaissance).setParameter("nationalite", nationalite).setParameter("dateRecrutement", dateRecrutement).setParameter("photo", photo).setParameter("sexe", sexe);
        return query.getResultList();
    }
    
    /**
     *
     * @return
     */
    @Override
    public int AttributionVehicule() {
        Query query =  em.createQuery("select p.nomPrenom from Personnel p , Structure s where s = p.idStructure and s.idStructure = 2");
        
        return query.executeUpdate();
    }
}
