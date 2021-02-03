/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Garage;
import entities.Sinistre;
import entities.Typesinistre;
import entities.Vehicule;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.GarageFacadeLocal;
import sessions.SinistreFacadeLocal;
import sessions.TypesinistreFacadeLocal;
import sessions.VehiculeFacadeLocal;
import utils.Utilitaires;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@ViewScoped
public class SinistreController implements Serializable {

    @EJB
    private SinistreFacadeLocal sinistreFacade;
    private List<Sinistre> listSinistre = null;
    private Sinistre sinistre = new Sinistre();

    @EJB
    private VehiculeFacadeLocal vehiculeFacade;
    private List<Vehicule> listVehicule = new ArrayList<>();
    private Vehicule vehicule = new Vehicule();

    @EJB
    private GarageFacadeLocal garageFacade;
    private List<Garage> listGarage = new ArrayList<>();
    private Garage garage = new Garage();

    @EJB
    private TypesinistreFacadeLocal typesinistreFacade;
    private List<Typesinistre> listTypesinistre = new ArrayList<>();
    private Typesinistre typesinistre = new Typesinistre();

    private String msg = "";
    private boolean bouton = true;

    public SinistreController() {
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

    public Sinistre prepareCreate() {
        vehicule = new Vehicule();
        garage = new Garage();
        typesinistre = new Typesinistre();
        sinistre = new Sinistre();
        
        listGarage = garageFacade.findAll();
        
        listVehicule = vehiculeFacade.vehiculesEnCircultation();
        
        listTypesinistre = typesinistreFacade.findAll();
        return sinistre;
    }

    public void prepareEdit() {

        System.err.println("prepqre apeler");
        if (sinistre != null) {
            garage.setIdGarage(sinistre.getIdGarage().getIdGarage());
            vehicule.setIdVehicule(sinistre.getIdVehicule().getIdVehicule());
            typesinistre.setIdTypesinistre(sinistre.getIdTypesinistre().getIdTypesinistre());
        }
    }

    public void prepareDelete() {
        if (sinistre != null) {
            msg = "Voulez-vous vraiment supprimer le sinistre selectionné ?";
        }
    }

    //@PostConstruct
    public void affichageSinistre() {
        listSinistre.clear();
        listSinistre.addAll(sinistreFacade.findAll());
    }
    
    public void saveSinistre() {
        try {            
            sinistre.setIdSinistre(sinistreFacade.nextId());
            sinistre.setIdGarage(garage);
            sinistre.setIdVehicule(vehicule);
            sinistre.setIdTypesinistre(typesinistre);
            sinistreFacade.create(sinistre);
            msg = "Enregistrement effectué avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageSinistre();
            activeBouton();
        }

    }

    public void editSinistre() {
        try {            
            sinistre.setIdGarage(garage);
            sinistre.setIdVehicule(vehicule);
            sinistre.setIdTypesinistre(typesinistre);
            sinistreFacade.edit(sinistre);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageSinistre();
        }

    }

    public void deleteSinistre() {
        try {
            sinistreFacade.remove(sinistre);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageSinistre();
            desactiveBouton();
        }
    }
    
    public String moneyFormater(Integer money){
        return Utilitaires.formaterStringMoney(money);
    }

    public List<Sinistre> getListSinistre() {
        listSinistre = sinistreFacade.findAll();
        return listSinistre;
    }

    public void setListSinistre(List<Sinistre> listSinistre) {
        this.listSinistre = listSinistre;
    }

    public Sinistre getSinistre() {
        return sinistre;
    }

    public void setSinistre(Sinistre sinistre) {
        this.sinistre = sinistre;
    }

    public List<Vehicule> getListVehicule() {
        //listVehicule = vehiculeFacade.findByEtat(false);
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

    public List<Garage> getListGarage() {
        listGarage = garageFacade.findAll();
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

    public List<Typesinistre> getListTypesinistre() {
        listTypesinistre = typesinistreFacade.findAll();
        return listTypesinistre;
    }

    public void setListTypesinistre(List<Typesinistre> listTypesinistre) {
        this.listTypesinistre = listTypesinistre;
    }

    public Typesinistre getTypesinistre() {
        return typesinistre;
    }

    public void setTypesinistre(Typesinistre typesinistre) {
        this.typesinistre = typesinistre;
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
