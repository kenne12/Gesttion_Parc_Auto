/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Fonction;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.FonctionFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class FonctionController implements Serializable {

    @EJB
    private FonctionFacadeLocal fonctionFacade;
    private Fonction fonction = new Fonction();
    private List<Fonction> listFonction = null;
    private String msg = "";
    private boolean bouton = true;

    public FonctionController() {
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

    public Fonction prepareCreate() {
        fonction = new Fonction();
        return fonction;
    }

    public void prepareEdit() {
    }

    public void prepareDelete() {
        if (fonction != null) {
            msg = "Voulez-vous vraiment supprimer la fonction selectionnée ?";
        }
    }

    public void affichageFonction() {
        listFonction.clear();
        listFonction.addAll(fonctionFacade.findAll());
    }

    public void saveFonction() {
        try {
            listFonction = fonctionFacade.findByNom(fonction.getNom());
            if (listFonction.isEmpty()) {
                fonction.setIdFonction(fonctionFacade.nextId());
                fonctionFacade.create(fonction);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + fonction.getNom() + " existe déjà";
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageFonction();
            activeBouton();
        }

    }

    public void editFonction() {
        try {
            listFonction = fonctionFacade.findByNom(fonction.getNom());
            if (listFonction.isEmpty()) {
                fonctionFacade.edit(fonction);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = " " + fonction.getNom() + " existe déjà";
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageFonction();
        }

    }

    public void deleteFonction() {
        try {
            fonctionFacade.remove(fonction);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageFonction();
            desactiveBouton();
        }
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public List<Fonction> getListFonction() {
        if (listFonction == null) {
            listFonction = fonctionFacade.findAll();
        }
        return listFonction;
    }

    public void setListFonction(List<Fonction> listFonction) {
        this.listFonction = listFonction;
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
