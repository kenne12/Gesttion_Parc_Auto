/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Marque;
import entities.Modele;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.ColumnResizeEvent;
import sessions.MarqueFacadeLocal;
import sessions.ModeleFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class ModeleController implements Serializable {

    @EJB
    private ModeleFacadeLocal modeleFacade;
    private List<Modele> listModele = null;
    private Modele modele = new Modele();

    @EJB
    private MarqueFacadeLocal marqueFacade;
    private List<Marque> listMarque = new ArrayList<>();
    private Marque marque = new Marque();

    private String msg = "";
    private boolean bouton=true;

    public ModeleController() {
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

    public Modele prepareCreate() {
        marque = new Marque();
        modele = new Modele();
        listMarque.clear();
        listMarque.addAll(marqueFacade.findAll());
        return modele;
    }

    public void prepareEdit() {
        listMarque.clear();
        listMarque.addAll(marqueFacade.findAll()); 
        marque = new Marque();
        if (modele != null) {
            marque.setIdMarque(modele.getIdMarque().getIdMarque());
        }
    }

    public void prepareDelete() {
        if (modele != null) {
            msg = "Voulez-vous vraiment supprimer le modèle selectionné ?";
        }
    }

    public void affichageModele() {
        listModele.clear();
        listModele.addAll(modeleFacade.findAll());
    }

    public void saveModele() {
        try {
            listModele = modeleFacade.findByNom(modele.getNom());
            if (listModele.isEmpty()) {
                modele.setIdModele(modeleFacade.nextId());
                modele.setIdMarque(marque);
                modeleFacade.create(modele);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + modele.getNom()+ " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageModele();
            activeBouton();
        }

    }

    public void editModele() {
        try {
            if(modele.getIdModele()!=null){

                modeleFacade.edit(modele);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            }else{
               JsfUtil.addErrorMessage("veuillez selectionner un modèle");
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageModele();
        }

    }

    public void deleteModele() {
        try {
            modeleFacade.remove(modele);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageModele();
            desactiveBouton();
        }
    }

    public List<Modele> getListModele() {
        if (listModele==null) {
            listModele=modeleFacade.findAll();
        }
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

    public List<Marque> getListMarque() {
        return listMarque;
    }

    public void setListMarque(List<Marque> listMarque) {
        this.listMarque = listMarque;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

}
