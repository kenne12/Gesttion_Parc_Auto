/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Categorievehicule;
import entities.Demande;
import entities.Modele;
import entities.Structure;
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
import sessions.CategorievehiculeFacadeLocal;
import sessions.DemandeFacadeLocal;
import sessions.ModeleFacadeLocal;
import sessions.StructureFacadeLocal;
import sessions.VehiculeFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class DemandeController implements Serializable {

    @EJB
    private DemandeFacadeLocal demandeFacade;
    private List<Demande> listDemande = null;
    private Demande demande = new Demande();

    @EJB
    private CategorievehiculeFacadeLocal categorievehiculeFacade;
    private List<Categorievehicule> listCategorievehicule = new ArrayList<>();
    private Categorievehicule categorievehicule = new Categorievehicule();

    @EJB
    private ModeleFacadeLocal modeleFacade;
    private List<Modele> listModele = new ArrayList<>();
    private List<Modele> modeleTest = new ArrayList<>();
    private Modele modele = new Modele();

    @EJB
    private StructureFacadeLocal structureFacade;
    private List<Structure> listStructure = new ArrayList<>();
    private Structure structure = new Structure();
    
    @EJB
    private VehiculeFacadeLocal vehiculeFacadeLocal;
    private Vehicule vehicule;
    private List<Vehicule> vehicules = new ArrayList<>();

    private String msg = "";
    private boolean bouton = true;

    public DemandeController() {
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

    public Demande prepareCreate() {
        demande = new Demande();
        vehicule = new Vehicule();
        modele = new Modele();
        structure = new Structure();
        listCategorievehicule.clear();
        listCategorievehicule.addAll(categorievehiculeFacade.findAll());
        
        listStructure.clear();
        listStructure.addAll(structureFacade.findAll());
        return demande;
    }

    public void prepareEdit() {
        listCategorievehicule = categorievehiculeFacade.findAll();

        listModele.clear();
        listStructure = structureFacade.findAll();
        if (demande != null) {
            categorievehicule.setIdCategorievehicule(demande.getIdCategorievehicule().getIdCategorievehicule());
            modele = demande.getIdModele();
            structure.setIdStructure(demande.getIdStructure().getIdStructure());
        }
    }

    public void filtreModele() {
        listModele.clear();
        modeleTest.clear();
        if (categorievehicule.getIdCategorievehicule() != null) {
           List<Vehicule> vehicul = vehiculeFacadeLocal.findByCategorie(categorievehicule.getIdCategorievehicule());
            if(!vehicul.isEmpty()){
                for(Vehicule v : vehicul){
                    modeleTest.add(v.getIdModele());
                }                
                for(Modele m : modeleTest){
                    if(!listModele.contains(m)){
                        listModele.add(m);
                    }
                }
                System.err.println("la taille : "+vehicul.size());
            }
        } else {
            listModele.clear();
        }
    }

    public void prepareDelete() {
        if (modele != null) {
            msg = "Voulez-vous vraiment supprimer la demande selectionnée ?";
        }
    }

    public void saveDemande() {
        try {
            demande.setIdDemande(demandeFacade.nextId());
            demande.setIdCategorievehicule(categorievehicule);
            demande.setIdModele(modele);
            demande.setIdStructure(structure);
            demandeFacade.create(demande);
            msg = "Enregistrement effectué avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageDemande();
            activeBouton();
        }

    }

    public void editDemande() {
        try {
            demande.setIdCategorievehicule(categorievehicule);
            demande.setIdModele(modele);
            demande.setIdStructure(structure);
            demandeFacade.edit(demande);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageDemande();
        }

    }

    public void deleteDemande() {
        try {
            demandeFacade.remove(demande);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageDemande();
            desactiveBouton();
        }
    }

    public void affichageDemande() {
        listDemande.clear();
        listDemande.addAll(demandeFacade.findAll());
    }

    public List<Demande> getListDemande() {
        listDemande = demandeFacade.findAll();
        return listDemande;
    }

    public void setListDemande(List<Demande> listDemande) {
        this.listDemande = listDemande;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public List<Categorievehicule> getListCategorievehicule() {
        return listCategorievehicule;
    }

    public void setListCategorievehicule(List<Categorievehicule> listCategorievehicule) {
        this.listCategorievehicule = listCategorievehicule;
    }

    public List<Modele> getListModele() {
        return listModele;
    }

    public void setListModele(List<Modele> listModele) {
        this.listModele = listModele;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
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

    public Categorievehicule getCategorievehicule() {
        return categorievehicule;
    }

    public void setCategorievehicule(Categorievehicule categorievehicule) {
        this.categorievehicule = categorievehicule;
    }

}
