/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Categorievehicule;
import entities.Norme;
import entities.Typestructure;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.CategorievehiculeFacadeLocal;
import sessions.NormeFacadeLocal;
import sessions.TypestructureFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class NormeController implements Serializable {

    @EJB
    private NormeFacadeLocal normeFacade;
    private List<Norme> listNorme = new ArrayList<>();
    private Norme norme;
    private Norme selectedNorme;

    private List<Norme> normes = new ArrayList<>();

    @EJB
    private CategorievehiculeFacadeLocal categorievehiculeFacade;
    private List<Categorievehicule> listCategorievehicule = new ArrayList<>();
    private Categorievehicule categorievehicule;

    private List<Categorievehicule> categorievehicules = new ArrayList<>();

    @EJB
    private TypestructureFacadeLocal typestructureFacade;
    private List<Typestructure> listTypestructure = new ArrayList<>();
    private Typestructure typestructure;

    private String msg = "";
    private boolean bouton = true;

    public NormeController() {

    }

    @PostConstruct
    private void init() {
        norme = new Norme();
        typestructure = new Typestructure();
        categorievehicule = new Categorievehicule();
        selectedNorme = new Norme();
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

    public void affichageNorme() {
        listNorme.clear();
        listNorme.addAll(normeFacade.findAll());
    }

    public Norme prepareCreate() {
        return norme;
    }

    public void prepareEdit() {
        System.err.println("prepare edit apelé");
        categorievehicule = selectedNorme.getIdCategorievehicule();
        typestructure = selectedNorme.getIdTypestructure();
    }

    public void prepareDelete() {
        System.err.println("prepare delete");
        if (selectedNorme != null) {
            msg = "Voulez-vous vraiment supprimer la norme selectionnée ?";
        }
    }

    public void filterCategorieVehicule() {
        listCategorievehicule = categorievehiculeFacade.findAll();
        if (typestructure.getIdTypestructure() != null) {
            normes = normeFacade.findByTypeStructure(typestructure.getIdTypestructure());
            if (!normes.isEmpty()) {
                for (Norme n : normes) {
                    categorievehicules.add(n.getIdCategorievehicule());
                }
                List<Categorievehicule> cats = categorievehiculeFacade.findAll();
                for (Categorievehicule c : categorievehicules) {
                    if (cats.contains(c)) {
                        listCategorievehicule.remove(c);
                    } else {
                        listCategorievehicule.add(c);
                    }
                }
                System.err.println("" + listCategorievehicule.size());
            } else {
                listCategorievehicule = categorievehiculeFacade.findAll();
            }
        } else {
            listCategorievehicule.clear();
        }
    }

    public void saveNorme() {
        try {
            if (categorievehicule != null) {
                if (typestructure.getIdTypestructure() != null) {

                    Norme test = normeFacade.get(norme.getMinimum(), norme.getMaximum());
                    if (test == null) {
                        norme.setIdNorme(normeFacade.nextId());
                        norme.setIdCategorievehicule(categorievehicule);
                        norme.setIdTypestructure(typestructure);
                        normeFacade.create(norme);
                        norme = new Norme();
                        categorievehicule = new Categorievehicule();
                        typestructure = new Typestructure();
                        msg = "Enregistrement effectué avec succès";
                        JsfUtil.addSuccessMessage(msg);
                    } else {
                        msg = "Ce type de structure dispose dejà cette norme de vehicule définie";
                        JsfUtil.addErrorMessage(msg);
                    }
                } else {
                    JsfUtil.addErrorMessage("veuillez choisir un type de structure");
                }
            } else {
                JsfUtil.addErrorMessage("veuillez choisir une categorie de vehicule");
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageNorme();
            activeBouton();
        }
    }

    public void editNorme() {
        try {
            if (selectedNorme != null) {
                normeFacade.edit(selectedNorme);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                JsfUtil.addErrorMessage("veuillez selectionner une norme");
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageNorme();
        }
    }

    public void deleteNorme() {
        try {
            if (selectedNorme != null) {
                normeFacade.remove(selectedNorme);
                msg = "Suppression effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);

            } else {
                JsfUtil.addErrorMessage("veuillez selectionner une norme");
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";

        }
        affichageNorme();
        desactiveBouton();
    }

    public List<Norme> getListNorme() {
        listNorme = normeFacade.findAll();
        return listNorme;
    }

    public void setListNorme(List<Norme> listNorme) {
        this.listNorme = listNorme;
    }

    public Norme getNorme() {
        return norme;
    }

    public void setNorme(Norme norme) {
        System.err.println("norme initialisée");
        this.norme = norme;
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

    public CategorievehiculeFacadeLocal getCategorievehiculeFacade() {
        return categorievehiculeFacade;
    }

    public void setCategorievehiculeFacade(CategorievehiculeFacadeLocal categorievehiculeFacade) {
        this.categorievehiculeFacade = categorievehiculeFacade;
    }

    public Categorievehicule getCategorievehicule() {
        return categorievehicule;
    }

    public void setCategorievehicule(Categorievehicule categorievehicule) {
        this.categorievehicule = categorievehicule;
    }

    public List<Typestructure> getListTypestructure() {
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

    public List<Categorievehicule> getListCategorievehicule() {
        return listCategorievehicule;
    }

    public void setListCategorievehicule(List<Categorievehicule> listCategorievehicule) {
        this.listCategorievehicule = listCategorievehicule;
    }

    public Norme getSelectedNorme() {
        return selectedNorme;
    }

    public void setSelectedNorme(Norme selectedNorme) {
        this.selectedNorme = selectedNorme;
    }

}
