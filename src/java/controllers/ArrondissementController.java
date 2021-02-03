/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Arrondissement;
import entities.Departement;
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
import sessions.ArrondissementFacadeLocal;
import sessions.DepartementFacadeLocal;
import sessions.RegionFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class ArrondissementController implements Serializable {

    @EJB
    private ArrondissementFacadeLocal arrondissementFacade;
    private Arrondissement arrondissement = new Arrondissement();
    private List<Arrondissement> listArrondissement = null;

    @EJB
    private DepartementFacadeLocal departementFacade;
    private Departement departement = new Departement();
    private Integer selectDepartement = 0;
    private List<Departement> listDepartement = new ArrayList<>();
    private List<Departement> listDepartement1 = new ArrayList<>();

    @EJB
    private RegionFacadeLocal regionFacade;
    private Region region = new Region();
    private List<Region> listRegion = new ArrayList<>();

    private String msg = "";
    private String msg_confirmation=" ";
    private boolean bouton=true; 

    public ArrondissementController() {
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
        FacesMessage msg1 = new FacesMessage("Column " + event.getColumn().getClientId() + " resized", "W:" + event.getWidth() + ", H:" + event.getHeight());

        FacesContext.getCurrentInstance().addMessage(null, msg1);
    }

    public void filtreDepartement() {
        listDepartement.clear();
        region = regionFacade.find(region.getIdRegion());
        if (region != null) {
            listDepartement.addAll(region.getDepartementCollection());
        } else {
//            region = new Region();
//            listDepartement.addAll(departementFacade.findAll());
        }
    }

    public void filtreArrondissement() {
        listArrondissement.clear();
        departement = departementFacade.find(departement.getIdDepartement());
        if (departement != null) {
            listArrondissement.addAll(departement.getArrondissementCollection());
        } else {
            region = new Region();
            departement = new Departement();
            listArrondissement.addAll(arrondissementFacade.findAll());
        }
    }

    public Arrondissement prepareCreate() {
        departement = new Departement();
        arrondissement = new Arrondissement();
        listDepartement.clear();
        listDepartement.addAll(departementFacade.findAll());
        return arrondissement;
    }

    public void prepareEdit() {
        departement = new Departement();
        if (arrondissement != null) {
            departement.setIdDepartement(arrondissement.getIdDepartement().getIdDepartement());
        }
    }

    public void prepareDelete() {
        if (arrondissement != null) {
            msg = "Voulez-vous vraiment supprimer l'arrondissement selectionné ?";
        }
    }

    public void affichageArrondissement() {
        listArrondissement.clear();
        listArrondissement.addAll(arrondissementFacade.findAll());
        listRegion.clear();
        listRegion.addAll(regionFacade.findAll());
    }

    public void saveArrondissement() {
        System.out.println("Méthode saveArrondissement() éxècutée");
        try {
            listArrondissement = arrondissementFacade.findByNom(arrondissement.getNom());
            if (listArrondissement.isEmpty()) {
                arrondissement.setIdArrondissement(arrondissementFacade.nextId());
                arrondissement.setIdDepartement(departement);
                arrondissementFacade.create(arrondissement);
                msg_confirmation = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg_confirmation);
            } else {
                msg_confirmation = "" + arrondissement.getNom() + " existe déjà";
                JsfUtil.addSuccessMessage(msg_confirmation);
            }
        } catch (Exception e) {
            msg_confirmation = "Echec de l'opération";
            e.printStackTrace();
        } finally {
            affichageArrondissement();
            activeBouton();
        }
    }

    public void editArrondissement() {
        System.out.println("Méthode editArrondissement() éxècutée");
        try {
            listArrondissement = arrondissementFacade.findByNom(arrondissement.getNom());
            if (listArrondissement.isEmpty()) {
                arrondissement.setIdDepartement(departement);
                arrondissementFacade.edit(arrondissement);
                msg_confirmation = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg_confirmation);
            } else {
                msg_confirmation = "" + arrondissement.getNom() + " existe déjà";
                JsfUtil.addSuccessMessage(msg_confirmation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg_confirmation = "Echec de l'opération";
        } finally {
            affichageArrondissement();
            
        }
    }

    public void deleteArrondissement() {
        System.out.println("Méthode deleteArrondissement() éxècutée");
        try {
            arrondissementFacade.remove(arrondissement);
            msg_confirmation = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg_confirmation);
        } catch (Exception e) {
            e.printStackTrace();
            msg_confirmation = "Echec de l'opération!";
        } finally {
            affichageArrondissement();
            desactiveBouton();
        }
    }

    public Arrondissement getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(Arrondissement arrondissement) {
        this.arrondissement = arrondissement;
    }

    public List<Arrondissement> getListArrondissement() {
        if (listArrondissement == null) {
            listArrondissement = arrondissementFacade.findAll();
        }
        return listArrondissement;
    }

    public void setListArrondissement(List<Arrondissement> listArrondissement) {
        this.listArrondissement = listArrondissement;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Departement> getListDepartement() {
        return listDepartement;
    }

    public void setListDepartement(List<Departement> listDepartement) {
        this.listDepartement = listDepartement;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public List<Departement> getListDepartement1() {
        return listDepartement1;
    }

    public void setListDepartement1(List<Departement> listDepartement1) {
        this.listDepartement1 = listDepartement1;
    }

    public Integer getSelectDepartement() {
        return selectDepartement;
    }

    public void setSelectDepartement(Integer selectDepartement) {
        this.selectDepartement = selectDepartement;
    }

    public boolean isBouton() {
        return bouton;
    }

    public void setBouton(boolean bouton) {
        this.bouton = bouton;
    }

    public String getMsg_confirmation() {
        return msg_confirmation;
    }

    public void setMsg_confirmation(String msg_confirmation) {
        this.msg_confirmation = msg_confirmation;
    }
    

}
