/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Adresse;
import entities.Garage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.AdresseFacadeLocal;
import sessions.GarageFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class GarageController implements Serializable {

    @EJB
    private GarageFacadeLocal garageFacade;
    private List<Garage> listGarage = null;
    private Garage garage = new Garage();

    @EJB
    private AdresseFacadeLocal adresseFacade;
    private Adresse adresse = new Adresse();
    private List<Adresse> listAdresse = new ArrayList<>();

    private String msg = "";
    private boolean bouton = true;

    public GarageController() {
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

    public Garage prepareCreate() {
        garage = new Garage();
        adresse = new Adresse();
        return garage;
    }

    public void prepareEdit() {
        System.out.println("Méthode prepareEdit() éxècutée");
    }

    public void prepareDelete() {
        if (garage != null) {
            msg = "Voulez-vous vraiment supprimer le garage selectionné ?";
        }
    }

    public void affichageGarage() {
        listGarage.clear();
        listGarage.addAll(garageFacade.findAll());
    }

    public void saveGarage() {
        System.out.println("Méthode saveGarage() éxècutée");
        try {
            listGarage = garageFacade.findByNom(garage.getNom());
            if (listGarage.isEmpty()) {
                adresse.setIdAdresse(adresseFacade.nextId());
                adresseFacade.create(adresse);

                garage.setIdGarage(garageFacade.nextId());
                garage.setIdAdresse(adresse);
                garageFacade.create(garage);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + garage.getNom() + " existe déjà";
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageGarage();
            activeBouton();
        }

    }

    public void editGarage() {
        try {

            garage.setIdAdresse(garage.getIdAdresse());
            adresseFacade.edit(garage.getIdAdresse());
            garageFacade.edit(garage);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageGarage();
        }

    }

    public void deleteGarage() {
        try {
            garageFacade.remove(garage);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageGarage();
            desactiveBouton();
        }
    }

    public List<Garage> getListGarage() {
        if (listGarage == null) {
            listGarage = garageFacade.findAll();
        }
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

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Adresse> getListAdresse() {
        return listAdresse;
    }

    public void setListAdresse(List<Adresse> listAdresse) {
        this.listAdresse = listAdresse;
    }

}
