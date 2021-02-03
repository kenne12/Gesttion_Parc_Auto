/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Garage;
import entities.Reparation;
import entities.Typereparation;
import entities.Vehicule;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.ColumnResizeEvent;
import sessions.GarageFacadeLocal;
import sessions.ReparationFacadeLocal;
import sessions.TypereparationFacadeLocal;
import sessions.VehiculeFacadeLocal;
import utils.Utilitaires;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class ReparationController implements Serializable {

    @EJB
    private ReparationFacadeLocal reparationFacade;
    private List<Reparation> listReparation = null;
    private Reparation reparation = new Reparation();

    @EJB
    private GarageFacadeLocal garageFacade;
    private List<Garage> listGarage = new ArrayList<>();
    private Garage garage = new Garage();

    @EJB
    private VehiculeFacadeLocal vehiculeFacade;
    private List<Vehicule> listVehicule = new ArrayList<>();
    private Vehicule vehicule = new Vehicule();

    @EJB
    private TypereparationFacadeLocal typereparationFacade;
    private List<Typereparation> listTypereparation = new ArrayList<>();
    private Typereparation typereparation = new Typereparation();

    private String msg = "";
    private boolean bouton = true;

    public ReparationController() {
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
    
    public String moneyFormater(Integer money){
        return Utilitaires.formaterStringMoney(money);
    }

    public Reparation prepareCreate() {
        reparation = new Reparation();
        vehicule = new Vehicule();
        garage = new Garage();
        
        listGarage = garageFacade.findAll();
        listVehicule = vehiculeFacade.vehiculesEnCircultation();
       
        listTypereparation = typereparationFacade.findAll();
        return reparation;
    }

    public void prepareEdit() {
        typereparation = reparation.getIdTypereparation();
        garage = reparation.getIdGarage();
        vehicule = reparation.getIdVehicule();
        listGarage = garageFacade.findAll();
        listVehicule = vehiculeFacade.findByEtat(false);
        listTypereparation = typereparationFacade.findAll();        
    }

    public void prepareDelete() {
        if (reparation != null) {
            msg = "Voulez-vous vraiment supprimer la réparation selectionnée ?";
        }
    }

    public void affichageReparation() {
        listReparation.clear();
        listReparation.addAll(reparationFacade.findAll());
    }

    public void saveReparation() {
        try {
            reparation.setIdReparation(reparationFacade.nextId());
            reparation.setIdGarage(garage);
            reparation.setIdTypereparation(typereparation);
            reparation.setIdVehicule(vehicule);
            reparationFacade.create(reparation);
            msg = "Enregistrement effectué avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageReparation();
            activeBouton();
        }

    }

    public void editReparation() {
        try {
            reparation.setIdGarage(garage);
            reparation.setIdTypereparation(typereparation);
            reparation.setIdVehicule(vehicule);
            reparationFacade.edit(reparation);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageReparation();
        }

    }

    public void deleteGarage() {
        try {
            reparationFacade.remove(reparation);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageReparation();
            desactiveBouton();
        }
    }

    public List<Reparation> getListReparation() {
        listReparation=reparationFacade.findAll();
        return listReparation;
    }

    public void setListReparation(List<Reparation> listReparation) {
        this.listReparation = listReparation;
    }

    public List<Garage> getListGarage() {
        return listGarage;
    }

    public void setListGarage(List<Garage> listGarage) {
        this.listGarage = listGarage;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public List<Vehicule> getListVehicule() {
        return listVehicule;
    }

    public void setListVehicule(List<Vehicule> listVehicule) {
        this.listVehicule = listVehicule;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public List<Typereparation> getListTypereparation() {
        
        return listTypereparation;
    }

    public void setListTypereparation(List<Typereparation> listTypereparation) {
        this.listTypereparation = listTypereparation;
    }

    public Typereparation getTypereparation() {
        return typereparation;
    }

    public void setTypereparation(Typereparation typereparation) {
        this.typereparation = typereparation;
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

    public Reparation getReparation() {
        return reparation;
    }

    public void setReparation(Reparation reparation) {
        this.reparation = reparation;
    }

}
