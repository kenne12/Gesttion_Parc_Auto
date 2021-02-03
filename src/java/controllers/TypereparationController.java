/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Typereparation;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.TypereparationFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class TypereparationController implements Serializable {
    
    @EJB
    private TypereparationFacadeLocal typereparationFacade;
    private List<Typereparation> listTypereparation = null;
    private Typereparation typereparation = new Typereparation();
    private String msg = "";
    private boolean bouton=true;
    
    public TypereparationController() {
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
    
    public Typereparation prepareCreate() {
        typereparation = new Typereparation();
        return typereparation;
    }
    
    public void prepareEdit() {
        System.out.println("Méthode prepareEdit() éxècutée");
    }
    
    public void prepareDelete() {
        if (typereparation != null) {
            msg = "Voulez-vous vraiment supprimer le type de réparation selectionné ?";
        }
    }
    
    public void affichageTypereparation() {
        listTypereparation.clear();
        listTypereparation.addAll(typereparationFacade.findAll());
    }
    
    public void saveTypereparation() {
        try {
            listTypereparation = typereparationFacade.findByNom(typereparation.getNom());
            if (listTypereparation.isEmpty()) {
                typereparation.setIdTypereparation(typereparationFacade.nextId());
                typereparationFacade.create(typereparation);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + typereparation.getNom()+ " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
             affichageTypereparation();
             activeBouton();
        }
        
    }
    
    public void editTypereparation() {
        System.out.println("Méthode editTypereparation() éxècutée");
        try {
            listTypereparation = typereparationFacade.findByNom(typereparation.getNom());
            if (listTypereparation.isEmpty()) {
                typereparationFacade.edit(typereparation);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg="L"+typereparation.getNom()+" exixte déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
             affichageTypereparation();
        }
        
    }
    
    public void deleteTypereparation() {
        try {
            typereparationFacade.remove(typereparation);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
             affichageTypereparation();
             desactiveBouton();
        }
    }
    
    public List<Typereparation> getListTypereparation() {
        if (listTypereparation==null) {
            listTypereparation=typereparationFacade.findAll();
        }
        return listTypereparation;
    }
    
    public void setListTypereparation(List<Typereparation> listTypereparation) {
        this.listTypereparation = listTypereparation;
    }
    
    public Typereparation getTypereparation() {
        return typereparation;
    }
    
    public void setTypereparation(Typereparation typereparation) {
        this.typereparation = typereparation;
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
