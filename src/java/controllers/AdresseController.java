/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Adresse;
import entities.Garage;
import entities.Personnel;
import entities.Structure;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.ColumnResizeEvent;
import sessions.AdresseFacadeLocal;
import sessions.GarageFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.StructureFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class AdresseController implements Serializable {

    @EJB
    private AdresseFacadeLocal adresseFacade;
    private Adresse adresse = new Adresse();
    private List<Adresse> listAdresse = null;

    @EJB
    private GarageFacadeLocal garageFacade;
    private List<Garage> listGarage = new ArrayList<>();
    private Garage garage = new Garage();

    @EJB
    private PersonnelFacadeLocal personnelFacade;
    private Personnel personnel = new Personnel();
    private List<Personnel> listPersonnel = new ArrayList<>();

    @EJB
    private StructureFacadeLocal structureFacade;
    private List<Structure> listStructure = new ArrayList<>();
    private Structure structure = new Structure();

    private String msg = "";
    private boolean bouton = true;
    private boolean selection = true;
    private boolean selectionStructure = true;
    private boolean selectionPersonnel = true;
    private boolean selectionGarage = true;
    private String entiteSelectionne = null;

    /**
     * Creates a new instance of adresseController
     */
    public AdresseController() {
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

    public void affichageAdresse() {
        listAdresse.clear();
        listAdresse.addAll(adresseFacade.findAll());
    }

    public Adresse prepareCreate() {
        adresse = new Adresse();
        garage = new Garage();
        personnel = new Personnel();
        structure = new Structure();
        listPersonnel.clear();
        listPersonnel.addAll(personnelFacade.findAll());
        listStructure.clear();
        listStructure.addAll(structureFacade.findAll());
        listGarage.clear();
        listGarage.addAll(garageFacade.findAll());
        return adresse;
    }

    public void prepareEdit() {
        //garage = new Garage();
        personnel = new Personnel();
       // structure = new Structure();
        listPersonnel.clear();
        listPersonnel.addAll(personnelFacade.findAll());
        listStructure.clear();
        listStructure.addAll(structureFacade.findAll());
        listGarage.clear();
        listGarage.addAll(garageFacade.findAll());
//        if (adresse != null) {
//            //garage.setIdGarage(adresse.getIdGarage().getIdGarage());
//            personnel.setIdPersonnel(adresse.getIdPersonnel().getIdPersonnel());
//            //structure.setIdStructure(adresse.getIdStructure().getIdStructure());
//        }

    }

    public void prepareDelete() {
        if (adresse != null) {
            msg = "Voulez-vous vraiment supprimer l'adresse selectionnée ?";
        }
    }

    public boolean activeEntiteSelectionne() {
        System.out.println("entite selectionnée " + entiteSelectionne);
        switch (entiteSelectionne) {
            case "Personnel":
                selectionPersonnel = false;
                selectionGarage = true;
                selectionStructure = true;
                garage.setIdGarage(-1);
                structure.setIdStructure(-1);
                return selectionPersonnel;
            case "Structure":
                selectionStructure = false;
                selectionPersonnel = true;
                selectionGarage = true;
                structure.setIdStructure(-1);
                return selectionStructure;
            case "Garage":
                selectionGarage = false;
                selectionStructure = true;
                selectionPersonnel = true;
                garage.setIdGarage(-1);
                return selectionGarage;
            default:
                selectionGarage = true;
                selectionStructure = true;
                selectionPersonnel = true;
                return true;
        }
    }

    public void saveAdresse() {
        try {
            listAdresse = adresseFacade.findByTel(adresse.getTel());
            if (listAdresse.isEmpty()) {
                listAdresse = adresseFacade.findByBp(adresse.getBp());
                if (listAdresse.isEmpty()) {
                    listAdresse = adresseFacade.findByEmail(adresse.getEmail());
                    if (listAdresse.isEmpty()) {
                        listAdresse = adresseFacade.findBySiteweb(adresse.getSiteweb());
                        if (listAdresse.isEmpty()) {
                            adresse.setIdAdresse(adresseFacade.nextId());
                            //adresse.setIdGarage(garage);
                            //adresse.setIdPersonnel(personnel);
                            //adresse.setIdStructure(structure);
                            adresseFacade.create(adresse);
                            msg = "Enregistrement effectué avec succès";

                            JsfUtil.addSuccessMessage(msg);
                        }
                    }
                }
            } else {
                msg = "" + adresse.getTel() + " " + adresse.getBp() + " " + adresse.getEmail() + " " + adresse.getSiteweb() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageAdresse();
            activeBouton();
        }

    }

    public void editAdresse() {
        try {
            listAdresse = adresseFacade.findByTel(adresse.getTel());
            if (listAdresse.isEmpty()) {
                listAdresse = adresseFacade.findByBp(adresse.getBp());
                if (listAdresse.isEmpty()) {
                    listAdresse = adresseFacade.findByEmail(adresse.getEmail());
                    if (listAdresse.isEmpty()) {
                        listAdresse = adresseFacade.findBySiteweb(adresse.getSiteweb());
                        if (listAdresse.isEmpty()) {                            
                           //adresse.setIdGarage(garage);
                            //adresse.setIdPersonnel(personnel);
                            //adresse.setIdStructure(structure);
                            adresseFacade.edit(adresse);
                            msg = "Enregistrement effectué avec succès";
                            JsfUtil.addSuccessMessage(msg);
                        }
                    }
                }
            } else {
                msg = "" + adresse.getTel() + " " + adresse.getBp() + " " + adresse.getEmail() + " " + adresse.getSiteweb() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageAdresse();
        }

    }

    public void deleteAdresse() {
        try {
            adresseFacade.remove(adresse);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";

        }
        affichageAdresse();
        desactiveBouton();
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Adresse> getListAdresse() {
        listAdresse = new ArrayList<>();
        listAdresse = adresseFacade.findAll();
        return listAdresse;
    }

    public void setListAdresse(List<Adresse> listAdresse) {
        this.listAdresse = listAdresse;
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

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public List<Personnel> getListPersonnel() {
        return listPersonnel;
    }

    public void setListPersonnel(List<Personnel> listPersonnel) {
        this.listPersonnel = listPersonnel;
    }

    public List<Structure> getListStructure() {
        return listStructure;
    }

    public void setListStructure(List<Structure> listStructure) {
        this.listStructure = listStructure;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
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

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    public boolean isSelectionStructure() {
        return selectionStructure;
    }

    public void setSelectionStructure(boolean selectionStructure) {
        this.selectionStructure = selectionStructure;
    }

    public boolean isSelectionPersonnel() {
        return selectionPersonnel;
    }

    public void setSelectionPersonnel(boolean selectionPersonnel) {
        this.selectionPersonnel = selectionPersonnel;
    }

    public boolean isSelectionGarage() {
        return selectionGarage;
    }

    public void setSelectionGarage(boolean selectionGarage) {
        this.selectionGarage = selectionGarage;
    }

    public String getEntiteSelectionne() {
        return entiteSelectionne;
    }

    public void setEntiteSelectionne(String entiteSelectionne) {
        this.entiteSelectionne = entiteSelectionne;
    }

}
