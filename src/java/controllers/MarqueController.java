/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import controllers.util.JsfUtil;
import entities.Marque;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.MarqueFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class MarqueController implements Serializable {
    
    @EJB
    private MarqueFacadeLocal marqueFacade;
    private List<Marque> listMarque = null;
    private Marque marque = new Marque();
    private String msg = "";
    private boolean bouton=true;
    
    public MarqueController() {
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
    
    public Marque prepareCreate() {
        marque = new Marque();
        return marque;
    }
    
    public void prepareEdit() {
    }
    
    public void prepareDelete() {
        if (marque != null) {
            msg = "Voulez-vous vraiment supprimer la marque selectionnée ?";
        }
    }
    
    public void affichageMarque() {
        listMarque.clear();
        listMarque.addAll(marqueFacade.findAll());
    }
    
    public void saveMarque() {
        try {
            listMarque = marqueFacade.findByNom(marque.getNom());
            if (listMarque.isEmpty()) {
                marque.setIdMarque(marqueFacade.nextId());
                marqueFacade.create(marque);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + marque.getNom() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
           affichageMarque();
           activeBouton();
        }
        
    }
    
    public void editMarque() {
        System.out.println("Méthode editMarque() éxècutée");
        try {
            listMarque = marqueFacade.findByNom(marque.getNom());
            if (listMarque.isEmpty()) {
                marqueFacade.edit(marque);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg=""+marque.getNom()+" existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageMarque();
        }
        
    }
    
    public void deleteMarque() {
        try {
            marqueFacade.remove(marque);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageMarque();
            desactiveBouton();
        }
    }
    
    public List<Marque> getListMarque() {
        if (listMarque==null) {
            listMarque=marqueFacade.findAll();
        }
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
