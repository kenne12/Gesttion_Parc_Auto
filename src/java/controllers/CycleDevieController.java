/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Attribution;
import entities.Reforme;
import entities.Reparation;
import entities.Vehicule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sessions.AttributionFacadeLocal;
import sessions.VehiculeFacadeLocal;
import utils.Printer;

/**
 *
 * @author gervais
 */
@ManagedBean
@ViewScoped
public class CycleDevieController {

    /**
     * Creates a new instance of CycleDevieController
    */
    @EJB
    private AttributionFacadeLocal attributionFacadeLocal;
    private Attribution attribution;
    private List<Attribution> attributions = new ArrayList<>();

    @EJB
    private VehiculeFacadeLocal vehiculeFacadeLocal;
    private Vehicule vehicule;
    private List<Vehicule> vehicules = new ArrayList<>();

    private Reparation reparation;
    private List<Reparation> reparations = new ArrayList<>();

    private Reforme reforme;
    private List<Reforme> reformes = new ArrayList<>();

    private String methode;

    private boolean imprimer = true;
    private  boolean  showPrintForm = false;

    @PostConstruct
    private void init() {
        attribution = new Attribution();
        vehicule = new Vehicule();
        reparation = new Reparation();
    }

    public CycleDevieController() {

    }

    public void filterCycleDeVie() {
        attributions.clear();
        if (vehicule.getIdVehicule() != null) {
            vehicule = vehiculeFacadeLocal.find(vehicule.getIdVehicule());
            attributions = (List<Attribution>) vehicule.getAttributionCollection();
            if (!attributions.isEmpty()) {
                imprimer = false;
            }
        } else {
            System.err.println("vehicule null");
            imprimer = true;
        }
    }

    public void filterByManiere() {

    }

    public void imprimerCycledevie() {
        System.err.println("impression commencé");
        showPrintForm = false;

        if (vehicule.getIdVehicule() != null) {
            reparations = (List<Reparation>) vehicule.getReparationCollection();
            reformes = (List<Reforme>) vehicule.getReformeCollection();
            attributions = (List<Attribution>) vehicule.getAttributionCollection();
            Printer.printCycledevie(vehicule, attributions, reparations, reformes);
            System.err.println("impression terminée");
            showPrintForm = true;
        } else {
            System.err.println("le vehicule est null");
            attributions.clear();
        }

        System.err.println("vehicule " + vehicule.getImmatriculation());
    }

    public boolean isImprimer() {
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }

    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    public List<Attribution> getAttributions() {

        return attributions;
    }

    public void setAttributions(List<Attribution> attributions) {
        this.attributions = attributions;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public List<Vehicule> getVehicules() {
        vehicules = vehiculeFacadeLocal.vehiculesEnCircultation();
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Reparation getReparation() {
        return reparation;
    }

    public void setReparation(Reparation reparation) {
        this.reparation = reparation;
    }

    public List<Reparation> getReparations() {
        return reparations;
    }

    public void setReparations(List<Reparation> reparations) {
        this.reparations = reparations;
    }

    public Reforme getReforme() {
        return reforme;
    }

    public void setReforme(Reforme reforme) {
        this.reforme = reforme;
    }

    public List<Reforme> getReformes() {
        return reformes;
    }

    public void setReformes(List<Reforme> reformes) {
        this.reformes = reformes;
    }

    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    public boolean isShowPrintForm() {
        return showPrintForm;
    }

    public void setShowPrintForm(boolean showPrintForm) {
        this.showPrintForm = showPrintForm;
    }

    

}
