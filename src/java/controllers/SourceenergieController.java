/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Sourceenergie;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.SourceenergieFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class SourceenergieController implements Serializable {

    @EJB
    private SourceenergieFacadeLocal sourceenergieFacade;
    private List<Sourceenergie> listSourceenergie = null;
    private Sourceenergie sourceenergie = new Sourceenergie();
    private String msg = "";
    private boolean bouton = true;

    public SourceenergieController() {
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

    public Sourceenergie prepareCreate() {
        sourceenergie = new Sourceenergie();
        return sourceenergie;
    }

    public void prepareEdit() {
    }

    public void prepareDelete() {
        if (sourceenergie != null) {
            msg = "Voulez-vous vraiment supprimer la source d'énergie selectionnée ?";
        }
    }

    public void affichageSourceenergie() {
        listSourceenergie.clear();
        listSourceenergie.addAll(sourceenergieFacade.findAll());
    }

    public void saveSourceenergie() {
        try {
            listSourceenergie = sourceenergieFacade.findByNom(sourceenergie.getNom());
            if (listSourceenergie.isEmpty()) {
                sourceenergie.setIdSourceenergie(sourceenergieFacade.nextId());
                sourceenergieFacade.create(sourceenergie);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + sourceenergie.getNom() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageSourceenergie();
            activeBouton();
        }

    }

    public void editSourceenergie() {
        System.out.println("Méthode editSourceenergie() éxècutée");
        try {
            listSourceenergie = sourceenergieFacade.findByNom(sourceenergie.getNom());
            if (listSourceenergie.isEmpty()) {
                sourceenergieFacade.edit(sourceenergie);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + sourceenergie.getNom() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageSourceenergie();
        }

    }

    public void deleteSourceenergie() {
        try {
            sourceenergieFacade.remove(sourceenergie);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageSourceenergie();
            desactiveBouton();
        }
    }

    public List<Sourceenergie> getListSourceenergie() {
        if (listSourceenergie == null) {
            listSourceenergie = sourceenergieFacade.findAll();
        }
        return listSourceenergie;
    }

    public void setListSourceenergie(List<Sourceenergie> listSourceenergie) {
        this.listSourceenergie = listSourceenergie;
    }

    public Sourceenergie getSourceenergie() {
        return sourceenergie;
    }

    public void setSourceenergie(Sourceenergie sourceenergie) {
        this.sourceenergie = sourceenergie;
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
