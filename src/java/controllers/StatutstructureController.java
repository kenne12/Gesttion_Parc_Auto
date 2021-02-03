/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Statutstructure;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.StatutstructureFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class StatutstructureController implements Serializable {

    @EJB
    private StatutstructureFacadeLocal statutstructureFacade;
    private List<Statutstructure> listStatutstructure = null;
    private Statutstructure statutstructure = new Statutstructure();
    private String msg = "";
    private boolean bouton = true;

    public StatutstructureController() {
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

    public Statutstructure prepareCreate() {
        statutstructure = new Statutstructure();
        return statutstructure;
    }

    public void prepareEdit() {
        System.out.println("Méthode prepareEdit() éxècutée");
    }

    public void prepareDelete() {
        if (statutstructure != null) {
            msg = "Voulez-vous vraiment supprimer le statut de structure selectionné ?";
        }
    }

    public void affichageStatutstructure() {
        listStatutstructure.clear();
        listStatutstructure.addAll(statutstructureFacade.findAll());
    }

    public void saveStatutstructure() {
        try {
            listStatutstructure = statutstructureFacade.findByNom(statutstructure.getNom());
            if (listStatutstructure.isEmpty()) {
                statutstructure.setIdStatutstructure(statutstructureFacade.nextId());
                statutstructureFacade.create(statutstructure);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + statutstructure.getNom() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageStatutstructure();
            activeBouton();
        }

    }

    public void editStatutstructure() {
        System.out.println("Méthode editStatutstructure() éxècutée");
        try {
            listStatutstructure = statutstructureFacade.findByNom(statutstructure.getNom());
            if (listStatutstructure.isEmpty()) {
                statutstructureFacade.edit(statutstructure);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + statutstructure.getNom() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageStatutstructure();
        }

    }

    public void deleteStatutstructure() {
        try {
            statutstructureFacade.remove(statutstructure);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageStatutstructure();
            desactiveBouton();
        }
    }

    public List<Statutstructure> getListStatutstructure() {
        if (listStatutstructure == null) {
            listStatutstructure = statutstructureFacade.findAll();
        }
        return listStatutstructure;
    }

    public void setListStatutstructure(List<Statutstructure> listStatutstructure) {
        this.listStatutstructure = listStatutstructure;
    }

    public Statutstructure getStatutstructure() {
        return statutstructure;
    }

    public void setStatutstructure(Statutstructure statutstructure) {
        this.statutstructure = statutstructure;
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
