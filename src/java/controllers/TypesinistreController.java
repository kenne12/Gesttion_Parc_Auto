/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Typesinistre;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.TypesinistreFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class TypesinistreController {

    @EJB
    private TypesinistreFacadeLocal typesinistreFacade;
    private List<Typesinistre> listTypesinistre = null;
    private Typesinistre typesinistre = new Typesinistre();
    private String msg = "";
    private boolean bouton=true;

    public TypesinistreController() {
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

    public Typesinistre prepareCreate() {
        typesinistre = new Typesinistre();
        return typesinistre;
    }

    public void prepareEdit() {
    }

    public void prepareDelete() {
        if (typesinistre != null) {
            msg = "Voulez-vous vraiment supprimer le type de sinsistre selectionné ?";
        }
    }

    public void affichageTypesinistre() {
        listTypesinistre.clear();
        listTypesinistre.addAll(typesinistreFacade.findAll());
    }

    public void saveTypesinistre() {
        try {
            listTypesinistre = typesinistreFacade.findByNom(typesinistre.getNom());
            if (typesinistreFacade.findByNom(typesinistre.getNom()).isEmpty()) {
                typesinistre.setIdTypesinistre(typesinistreFacade.nextId());
                typesinistreFacade.create(typesinistre);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + typesinistre.getNom()+ " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
             affichageTypesinistre();
             activeBouton();
        }

    }

    public void editTypesinistre() {
        try {
            listTypesinistre = typesinistreFacade.findByNom(typesinistre.getNom());
            if (listTypesinistre.isEmpty()) {
                typesinistreFacade.edit(typesinistre);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + typesinistre.getNom()+ " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
             affichageTypesinistre();
        }

    }

    public void deleteTypesinistre() {
        System.out.println("Méthode deleteTypesinistre() éxècutée");
        try {
            typesinistreFacade.remove(typesinistre);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
             affichageTypesinistre();
             desactiveBouton();
        }
    }

    public List<Typesinistre> getListTypesinistre() {
        if (listTypesinistre==null) {
            listTypesinistre=typesinistreFacade.findAll();
        }
        return listTypesinistre;
    }

    public void setListTypesinistre(List<Typesinistre> listTypesinistre) {
        this.listTypesinistre = listTypesinistre;
    }

    public Typesinistre getTypesinistre() {
        return typesinistre;
    }

    public void setTypesinistre(Typesinistre typesinistre) {
        this.typesinistre = typesinistre;
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
