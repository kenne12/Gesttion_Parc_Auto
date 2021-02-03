/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Attribution;
import entities.Personnel;
import entities.Restitution;
import entities.Vehicule;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.AttributionFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.RestitutionFacadeLocal;
import sessions.VehiculeFacadeLocal;
import utils.Utilitaires;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@ViewScoped
public class RestitutionController implements Serializable {

    @EJB
    private RestitutionFacadeLocal restitutionFacade;

    private Restitution restitution = new Restitution();

    @EJB
    private AttributionFacadeLocal attributionFacade;
    private List<Attribution> listAttribution = new ArrayList<>();
    private List<Attribution> listRestitution = new ArrayList<>();
    private Attribution attribution = new Attribution();
    private Personnel nouveauPersonnel = new Personnel();
    private Attribution nouvelleAttribution = new Attribution();
    private Attribution selectedAttribution = new Attribution();
    @EJB
    private VehiculeFacadeLocal vehiculeFacade;
    private List<Vehicule> listVehicule = new ArrayList<>();
    private List<Vehicule> listVehiculeSelection = new ArrayList<>();
    private Vehicule vehicule = new Vehicule();

    @EJB
    private PersonnelFacadeLocal personnelFacade;
    private Personnel personnel = new Personnel();
    private List<Personnel> listPersonnel = new ArrayList<>();

    private String msg = "";
    private boolean bouton = true;
    private boolean canclel = true;

    protected String repertoitre = Utilitaires.path + "/" + utils.Utilitaires.repertoireDefautRestitution;
    protected String fichier = Utilitaires.nomFichierParDefautRestitution;
    
    private boolean selectedNewPersonnel = true;

    public RestitutionController() {

    }

    public boolean activeBouton() {
        bouton = false;
        return bouton;
    }

    public boolean desactiveBouton() {
        bouton = true;
        return bouton;
    }

    private boolean printable;

    public void onResize(ColumnResizeEvent event) {

    }

    public Attribution prepareCreate() {
        vehicule = new Vehicule();
        personnel = new Personnel();
        nouveauPersonnel = new Personnel();
        nouvelleAttribution = new Attribution();
        nouvelleAttribution.setDatesyst(new Date());
        nouvelleAttribution.setDateattribution(new Date());
        attribution = new Attribution();
        selectedNewPersonnel = true;
        listVehicule.clear();
        listVehicule.addAll(vehiculeFacade.findAll());
        return nouvelleAttribution;
    }
    
    public void removePersonnel(){       
        if(attribution.getIdAttribution()!=null){
            listPersonnel = personnelFacade.findAll(); 
            if(listPersonnel.contains(attribution.getIdPersonnel())){
                listPersonnel.remove(attribution.getIdPersonnel()); 
               
            }                        
        }        
    }

    public void prepareEdit() {
        listPersonnel.clear();
        listPersonnel.addAll(personnelFacade.findAll());
        listVehicule.clear();
        listVehicule.addAll(vehiculeFacade.findAll());
        if (attribution != null) {
            personnel.setIdPersonnel(attribution.getIdPersonnel().getIdPersonnel());
            vehicule.setIdVehicule(attribution.getIdVehicule().getIdVehicule());
        }
    }

    public void prepareDelete() {
        System.err.println("leprepare edit appelé");
        if (selectedAttribution.getIdAttribution() != null) {
            vehicule = selectedAttribution.getIdVehicule();
            Attribution test = attributionFacade.get(vehicule.getIdVehicule(), true);
            if (test != null) {
                nouveauPersonnel = test.getIdPersonnel();
            } else {
                System.err.println("resultat non trouvé");
            }
        } else {
            System.err.println("attribution nulle");
        }
    }

    //@PostConstruct
    public void affichageRestitution() {
        listRestitution.clear();
        listRestitution.addAll(attributionFacade.findAllRestitution());
    }

    public void saveRestitution() {
        try {

            if (attribution.getIdAttribution() != null) {
                if (nouveauPersonnel.getIdPersonnel() != null) {

                    vehicule = attribution.getIdVehicule();

                    attribution.setAttr(false);

                    attribution.setDaterestitution(new Date());
                    attributionFacade.edit(attribution);

                    nouvelleAttribution.setIdAttribution(attributionFacade.nextId());
                    nouvelleAttribution.setIdPersonnel(nouveauPersonnel);
                    nouvelleAttribution.setIdVehicule(vehicule);
                    nouvelleAttribution.setDaterestitution(null);
                    nouvelleAttribution.setDateattribution(attribution.getDaterestitution());
                    nouvelleAttribution.setDatesyst(new Date());
                    nouvelleAttribution.setAttr(true);
                    attributionFacade.create(nouvelleAttribution);

                    attribution = new Attribution();
                    nouvelleAttribution = new Attribution();
                    nouveauPersonnel = new Personnel();

                    JsfUtil.addSuccessMessage("Operation effectuée avec succès");
                } else {
                    JsfUtil.addErrorMessage("Veuillez selectionner le nouveau personnel");
                }
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
        } finally {
            affichageRestitution();
            activeBouton();
        }
    }

    public void editAttribution() {
        try {
            attributionFacade.edit(attribution);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = "Echec de l'opération";
        } finally {
            affichageRestitution();
        }

    }

    public void deleteRestitution() {
        try {
            if (selectedAttribution.getIdAttribution() != null) {
                if (nouveauPersonnel.getIdPersonnel() != null) {
                    Attribution annuler;
                    annuler = attributionFacade.get(selectedAttribution.getIdVehicule().getIdVehicule(), true);
                    annuler.setDaterestitution(selectedAttribution.getDateattribution());
                    annuler.setAttr(false);
                    attributionFacade.edit(annuler);

                    selectedAttribution.setAttr(true);
                    selectedAttribution.setDaterestitution(null);
                    attributionFacade.edit(attribution);

                    attribution = new Attribution();
                    nouveauPersonnel = new Personnel();
                } else {
                    attribution.setAttr(true);
                    attribution.setDaterestitution(null);
                    attributionFacade.edit(attribution);
                }
            }
            JsfUtil.addSuccessMessage("Operation réussie");
        } catch (Exception e) {
            msg = "Echec de l'opération!";
        } finally {
            affichageRestitution();
            desactiveBouton();
        }
    }

    public void vehiculeAttribuer() {
        listVehiculeSelection = new ArrayList<>();
        listVehiculeSelection = vehiculeFacade.getVehicule(true, false);
        System.out.println("oui");
    }

    public void listattribution() {
        listAttribution.clear();
        listAttribution.addAll(attributionFacade.findAll());
    }

    public boolean isCanclel() {
        canclel = selectedAttribution.getIdAttribution() == null;
        return canclel;
    }

    public void setCanclel(boolean canclel) {
        this.canclel = canclel;
    }

    public List<Attribution> getListRestitution() {
        listRestitution = attributionFacade.get(false, false);
        return listRestitution;
    }

    public void setListRestitution(List<Attribution> listRestitution) {
        this.listRestitution = listRestitution;
    }

    public Personnel getNouveauPersonnel() {
        return nouveauPersonnel;
    }

    public void setNouveauPersonnel(Personnel nouveauPersonnel) {
        this.nouveauPersonnel = nouveauPersonnel;
    }

    public Attribution getNouvelleAttribution() {
        return nouvelleAttribution;
    }

    public void setNouvelleAttribution(Attribution nouvelleAttribution) {
        this.nouvelleAttribution = nouvelleAttribution;
    }

    public Restitution getRestitution() {
        return restitution;
    }

    public void setRestitution(Restitution restitution) {
        this.restitution = restitution;
    }

    public List<Attribution> getListAttribution() {
        listAttribution.clear();
        listAttribution.addAll(attributionFacade.findAll());
        return listAttribution;
    }

    public void setListAttribution(List<Attribution> listAttribution) {
        this.listAttribution = listAttribution;
    }

    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(Attribution attribution) {
       selectedNewPersonnel = false; 
       this.attribution = attribution;
    }

    public List<Vehicule> getListVehicule() {
        return listVehicule;
    }

    public void setListVehicule(List<Vehicule> listVehicule) {
        this.listVehicule = listVehicule;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public List<Personnel> getListPersonnel() {
        return listPersonnel;
    }

    public void setListPersonnel(List<Personnel> listPersonnel) {
        this.listPersonnel = listPersonnel;
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

    public List<Vehicule> getListVehiculeSelection() {
        return listVehiculeSelection;
    }

    public void setListVehiculeSelection(List<Vehicule> listVehiculeSelection) {
        this.listVehiculeSelection = listVehiculeSelection;
    }

    public boolean isPrintable() {
        printable = attributionFacade.findAll().isEmpty();
        return printable;
    }

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public String getRepertoitre() {
        return repertoitre;
    }

    public void setRepertoitre(String repertoitre) {
        this.repertoitre = repertoitre;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public Attribution getSelectedAttribution() {
        return selectedAttribution;
    }

    public void setSelectedAttribution(Attribution selectedAttribution) {
        this.selectedAttribution = selectedAttribution;
    }

    public boolean isSelectedNewPersonnel() {
        return selectedNewPersonnel;
    }

    public void setSelectedNewPersonnel(boolean selectedNewPersonnel) {
        this.selectedNewPersonnel = selectedNewPersonnel;
    }
    
    

}
