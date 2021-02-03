/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Modeacquisition;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.ModeacquisitionFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class ModeacquisitionController implements Serializable {
    
    @EJB
    private ModeacquisitionFacadeLocal modeacquisitionFacade;
    private List<Modeacquisition> listModeacquisition = null;
    private Modeacquisition modeacquisition = new Modeacquisition();
    private String msg = "";
    private boolean bouton=true;
    
    public ModeacquisitionController() {
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
    
    public Modeacquisition prepareCreate() {
        modeacquisition = new Modeacquisition();
        return modeacquisition;
    }
    
    public void prepareEdit() {
        System.out.println("Méthode prepareEdit() éxècutée");
    }
    
    public void prepareDelete() {
        if (modeacquisition != null) {
            msg = "Voulez-vous vraiment supprimer le mode d'acquiqition selectionné ?";
        }
    }
    
    public void affichageModeacquisition() {
        listModeacquisition.clear();
        listModeacquisition.addAll(modeacquisitionFacade.findAll());
    }
    
    public void saveModeacquisition() {
        System.out.println("Méthode saveModeacquisition() éxècutée");
        try {
            listModeacquisition = modeacquisitionFacade.findByNom(modeacquisition.getNom());
            if (listModeacquisition.isEmpty()) {
                modeacquisition.setIdModeacquisition(modeacquisitionFacade.nextId());
                modeacquisitionFacade.create(modeacquisition);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + modeacquisition.getNom()+ " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageModeacquisition();
            activeBouton();
        }
        
    }
    
    public void editModeacquisition() {
        try {
            listModeacquisition = modeacquisitionFacade.findByNom(modeacquisition.getNom());
            if (listModeacquisition.isEmpty()) {
                modeacquisitionFacade.edit(modeacquisition);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + modeacquisition.getNom()+ " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageModeacquisition();
        }
        
    }
    
    public void deleteModeacquisition() {
        try {
            modeacquisitionFacade.remove(modeacquisition);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageModeacquisition();
            desactiveBouton();
        }
    }
    
    public List<Modeacquisition> getListModeacquisition() {
        if (listModeacquisition==null) {
            listModeacquisition=modeacquisitionFacade.findAll();
        }
        return listModeacquisition;
    }
    
    public void setListModeacquisition(List<Modeacquisition> listModeacquisition) {
        this.listModeacquisition = listModeacquisition;
    }
    
    public Modeacquisition getModeacquisition() {
        return modeacquisition;
    }
    
    public void setModeacquisition(Modeacquisition modeacquisition) {
        this.modeacquisition = modeacquisition;
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
