/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Pays;
import entities.Region;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.ColumnResizeEvent;
import sessions.PaysFacadeLocal;
import sessions.RegionFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class RegionController implements Serializable {

    @EJB
    private RegionFacadeLocal regionFacade;
    private List<Region> listRegion = null;
    private Region region = new Region();
    @EJB
    private PaysFacadeLocal paysFacade;
    private List<Pays> listPays = new ArrayList<>();
    private Pays pays = new Pays();

    private String msg = "";
    private boolean bouton = true;

    public RegionController() {

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

    public void affichageRegion() {
        listRegion.clear();
        listRegion.addAll(regionFacade.findAll());
    }

    public Pays prepareCreate() {
        pays = new Pays();
        region = new Region();
        listPays.clear();
        listPays.addAll(paysFacade.findAll());
        return pays;
    }

    public void prepareEdit() {
        listPays.clear();
        listPays.addAll(paysFacade.findAll());
        pays = new Pays();
        if (region != null) {
            pays.setIdPays(region.getIdPays().getIdPays());
        }
    }

    public void prepareDelete() {
        if (region != null) {
            msg = "Voulez-vous vraiment supprimer la région selectionnée ?";
        }
    }

    public void saveRegion() {
        try {
            listRegion = regionFacade.findByNom(region.getNom());
            if (listRegion.isEmpty()) {
                region.setIdRegion(regionFacade.nextId());
                region.setIdPays(pays);
                regionFacade.create(region);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "La région : " + region.getNom() + " existe déjà";
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération";
            e.printStackTrace();
        } finally {
            affichageRegion();
            activeBouton();
        }
    }

    public void editRegion() {
        try {
            listRegion = regionFacade.findByNom(region.getNom());
            if (listRegion.isEmpty()) {
                region.setIdPays(pays);
                regionFacade.edit(region);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "La région " + region.getNom() + " existe déjà";
                System.out.println(msg);
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageRegion();
        }

    }

    public void deleteRegion() {
        try {
            regionFacade.remove(region);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageRegion();
            desactiveBouton();
        }
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Pays> getListPays() {
        return listPays;
    }

    public void setListPays(List<Pays> listPays) {
        this.listPays = listPays;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public List<Region> getListRegion() {
        if (listRegion == null) {
            listRegion = regionFacade.findAll();
        }
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
