/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Sourcefinancement;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.SourcefinancementFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class SourcefinancementController implements Serializable {
    
    @EJB
    private SourcefinancementFacadeLocal sourcefinancementFacade;
    private List<Sourcefinancement> listSourcefinancement = null;
    private Sourcefinancement sourcefinancement = new Sourcefinancement();
    private String msg = "";
    private boolean bouton=true;
    
    public SourcefinancementController() {
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
    
    public Sourcefinancement prepareCreate() {
        sourcefinancement = new Sourcefinancement();
        return sourcefinancement;
    }
    
    public void prepareEdit() {
        System.out.println("Méthode prepareEdit() éxècutée");
    }
    
    public void prepareDelete() {
        if (sourcefinancement != null) {
            msg = "Voulez-vous vraiment supprimer la source de financement selectionnée ?";
        }
    }
    
    public void affichageSourcefinancement() {
        listSourcefinancement.clear();
        listSourcefinancement.addAll(sourcefinancementFacade.findAll());
    }
    
    public void saveSourcefinancement() {
        try {
            listSourcefinancement = sourcefinancementFacade.findByNom(sourcefinancement.getNom());
            if (listSourcefinancement.isEmpty()) {
                sourcefinancement.setIdSourcefinancement(sourcefinancementFacade.nextId());
                sourcefinancementFacade.create(sourcefinancement);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + sourcefinancement.getNom()+ " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageSourcefinancement();
            activeBouton();
        }
        
    }
    
    public void editSourcefinancement() {
        System.out.println("Méthode editSourcefinancement() éxècutée");
        try {
            listSourcefinancement = sourcefinancementFacade.findByNom(sourcefinancement.getNom());
            if (listSourcefinancement.isEmpty()) {
                sourcefinancementFacade.edit(sourcefinancement);
                sourcefinancement.setNom(sourcefinancement.getNom().toUpperCase());
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg=""+sourcefinancement.getNom()+" existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageSourcefinancement();
        }
        
    }
    
    public void deleteSourcefinancement() {
        try {
            sourcefinancementFacade.remove(sourcefinancement);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageSourcefinancement();
            desactiveBouton();
        }
    }
    
    public List<Sourcefinancement> getListSourcefinancement() {
        if (listSourcefinancement==null) {
            listSourcefinancement=sourcefinancementFacade.findAll();
        }
        return listSourcefinancement;
    }
    
    public void setListSourcefinancement(List<Sourcefinancement> listSourcefinancement) {
        this.listSourcefinancement = listSourcefinancement;
    }
    
    public Sourcefinancement getSourcefinancement() {
        return sourcefinancement;
    }
    
    public void setSourcefinancement(Sourcefinancement sourcefinancement) {
        this.sourcefinancement = sourcefinancement;
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
