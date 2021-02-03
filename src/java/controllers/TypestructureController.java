/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Typestructure;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.TypestructureFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class TypestructureController implements Serializable {

    @EJB
    private TypestructureFacadeLocal typestructureFacade;
    private List<Typestructure> listTypestructure = null;
    private List<Typestructure> listTypestructure1 = new ArrayList<>();
    private Typestructure typestructure = new Typestructure();
    private String msg = "";
    private boolean bouton=true;

    public TypestructureController() {
        
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

    public Typestructure prepareCreate() {
        typestructure = new Typestructure();
        return typestructure;
    }

    public void prepareEdit() {
        
    }

    public void prepareDelete() {
        if (typestructure!=null) {
            msg="Voulez-vous vraiment supprimer le type de structure selectionné ?";
        }
    }

    public void affichageTypestructure() {
        listTypestructure.clear();
        listTypestructure.addAll(typestructureFacade.findAll());
    }

    public void saveTypestructure() {
        try {
            listTypestructure = typestructureFacade.findByCodeFr(typestructure.getCodeFr());
            listTypestructure1 = typestructureFacade.findByNomFr(typestructure.getNomFr());
            if (listTypestructure.isEmpty()) {
                if (listTypestructure1.isEmpty()) {
                    typestructure.setIdTypestructure(typestructureFacade.nextId());
                    typestructureFacade.create(typestructure);
                    msg = "Enregistrement effectué avec succès";
                    JsfUtil.addSuccessMessage(msg);
                } else {
                    msg = "" + typestructure.getNomFr()+ " existe déjà";
                    JsfUtil.addErrorMessage(msg);
                }
            } else {
                msg = "" + typestructure.getCodeFr()+ " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
             affichageTypestructure();
             activeBouton();
        }

    }

    public void editTypestructure() {
        System.out.println("Méthode saveTypestructure() éxècutée");
        try {                  
            
            typestructureFacade.edit(typestructure);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);                           
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
             affichageTypestructure();
        }

    }

    public void deleteTypestructure() {
        try {
            typestructureFacade.remove(typestructure);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
             affichageTypestructure();
             desactiveBouton();
        }
    }

    public List<Typestructure> getListTypestructure() {
        if (listTypestructure==null) {
            listTypestructure=typestructureFacade.findAll();
        }
        return listTypestructure;
    }

    public void setListTypestructure(List<Typestructure> listTypestructure) {
        this.listTypestructure = listTypestructure;
    }

    public Typestructure getTypestructure() {
        return typestructure;
    }

    public void setTypestructure(Typestructure typestructure) {
        this.typestructure = typestructure;
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

    
    public List<Typestructure> getListTypestructure1() {
        return listTypestructure1;
    }

    public void setListTypestructure1(List<Typestructure> listTypestructure1) {
        this.listTypestructure1 = listTypestructure1;
    }

}
