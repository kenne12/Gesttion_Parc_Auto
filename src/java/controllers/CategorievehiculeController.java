/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Categorievehicule;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.CategorievehiculeFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class CategorievehiculeController implements Serializable {

    @EJB
    private CategorievehiculeFacadeLocal categorievehiculeFacade;
    private List<Categorievehicule> listCategorievehicule = null;
    private Categorievehicule categorievehicule = new Categorievehicule();
    private String msg = "";
    private boolean bouton = true;

    public CategorievehiculeController() {
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

    public Categorievehicule prepareCreate() {
        categorievehicule = new Categorievehicule();
        return categorievehicule;
    }

    public void prepareEdit() {
    }

    public void prepareDelete() {
        if (categorievehicule != null) {
            msg = "Voulez-vous vraiment supprimer la catégorie selectionnée ?";
        }
    }

    public void affichageCategorievehicule() {
        listCategorievehicule.clear();
        listCategorievehicule.addAll(categorievehiculeFacade.findAll());
    }

    public void saveCategorievehicule() {
        try {
            listCategorievehicule = categorievehiculeFacade.findByNom(categorievehicule.getNom());
            if (listCategorievehicule.isEmpty()) {
                categorievehicule.setIdCategorievehicule(categorievehiculeFacade.nextId());
                categorievehiculeFacade.create(categorievehicule);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + categorievehicule.getNom() + " existe déjà";
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageCategorievehicule();
            activeBouton();
        }

    }

    public void editCategorievehicule() {
        try {
            listCategorievehicule = categorievehiculeFacade.findByNom(categorievehicule.getNom());
            if (listCategorievehicule.isEmpty()) {
                categorievehiculeFacade.edit(categorievehicule);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
                getListCategorievehicule();
            } else {
                msg = "" + categorievehicule.getNom() + " existe déjà";
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            getListCategorievehicule();
            affichageCategorievehicule();
        }

    }

    public void deleteCategorievehicule() {
        try {
            categorievehiculeFacade.remove(categorievehicule);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageCategorievehicule();
            desactiveBouton();
        }
    }

    public List<Categorievehicule> getListCategorievehicule() {

        listCategorievehicule = categorievehiculeFacade.findAll();
        return listCategorievehicule;
    }

    public void setListCategorievehicule(List<Categorievehicule> listCategorievehicule) {
        this.listCategorievehicule = listCategorievehicule;
    }

    public Categorievehicule getCategorievehicule() {
        return categorievehicule;
    }

    public void setCategorievehicule(Categorievehicule categorievehicule) {
        this.categorievehicule = categorievehicule;
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
