/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Departement;
import entities.Region;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.DepartementFacadeLocal;
import sessions.RegionFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class DepartementController implements Serializable {

    @EJB
    private DepartementFacadeLocal departementFacade;
    private Departement departement = new Departement();
    private List<Departement> listDepartement = null;
    @EJB
    private RegionFacadeLocal regionFacade;
    private Region region = new Region();
    private List<Region> listRegion = new ArrayList<>();

    private String msg = "";
    private boolean bouton=true;

    public DepartementController() {
    }
    
    public boolean activeBouton() {
        bouton = false;
        return bouton;
    }
    
    public boolean desactiveBouton() {
        bouton = true; 
        return bouton;
    }

    
    public void onResize(ColumnResizeEvent event) {
        
    }

    public void filtreDepartement() {
        listDepartement.clear();
        region = regionFacade.find(region.getIdRegion());
        if (region != null) {
            listDepartement.addAll(region.getDepartementCollection());
        } else {
            region = new Region();
            listDepartement.addAll(departementFacade.findAll());
        }
    }

    public Departement prepareCreate() {        
        departement = new Departement();
        region=new Region();
        listRegion.clear();
        listRegion.addAll(regionFacade.findAll());
        return departement;
    }

    public void prepareEdit() {
        listRegion.clear();
        listRegion.addAll(regionFacade.findAll());
        region = new Region();
        if (departement!= null) {
            region.setIdRegion(departement.getIdRegion().getIdRegion());
        }
    }

    public void prepareDelete() { 
        if (departement != null) { 
            msg="Voulez-vous vraiment supprimer le département selectionné ?";
        }
    }

    public void affichageDepartement() {
        listDepartement.clear();
        listDepartement.addAll(departementFacade.findAll());
        listRegion.clear();
        listRegion.addAll(regionFacade.findAll());
    }

    public void saveDepartement() {
        System.out.println("Méthode saveDepartement() éxècutée");
        try {
            listDepartement = departementFacade.findByNom(departement.getNom());
            if (listDepartement.isEmpty()) {
                departement.setIdDepartement(departementFacade.nextId());
                departement.setIdRegion(region);
                departementFacade.create(departement);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "Le departement : " + departement.getNom() + " existe déjà";
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération";
            e.printStackTrace();
        } finally {
            affichageDepartement();
            activeBouton();
        }
    }

    public void editDepartement() {
        System.out.println("Méthode editDepartement() éxècutée");
        try {
            
                //departement.setIdRegion(region);
                departementFacade.edit(departement);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageDepartement();
        }

    }

    public void deleteDepartement() {
        System.out.println("Méthode deleteDepartement() éxècutée");
        try {
            departementFacade.remove(departement);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageDepartement();
            desactiveBouton();
        }
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Departement> getListDepartement() {
        if (listDepartement==null) {
            listDepartement=departementFacade.findAll();
        }
        return listDepartement;
    }

    public void setListDepartement(List<Departement> listDepartement) {
        this.listDepartement = listDepartement;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Region> getListRegion() {
        return listRegion;
    }

    public void setListRegion(List<Region> listRegion) {
        this.listRegion = listRegion;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isBouton() {
        return bouton;
    }

    public void setBouton(boolean bouton) {
        this.bouton = bouton;
    }
    

}
