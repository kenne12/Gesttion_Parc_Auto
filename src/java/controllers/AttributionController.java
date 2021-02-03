/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Attribution;
import entities.Personnel;
import entities.Region;
import entities.Structure;
import entities.Vehicule;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.AttributionFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.RegionFacadeLocal;
import sessions.VehiculeFacadeLocal;
import utils.Printer;
import utils.Utilitaires;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class AttributionController extends AbstractController<Attribution> implements Serializable {

    @EJB
    private AttributionFacadeLocal attributionFacade;
    private List<Attribution> listAttribution = new ArrayList<>();
    private Attribution attribution;
    private List<Attribution> attributiontries = new ArrayList<>();
    private List<Attribution> attributionStructures = new ArrayList<>();

    @EJB
    private VehiculeFacadeLocal vehiculeFacade;
    private List<Vehicule> listVehicule = new ArrayList<>();
    private List<Vehicule> listVehiculeNonSelection = new ArrayList<>();
    private Vehicule vehicule = new Vehicule();
    private List<Personnel> listPersonnelNonSelection = null;

    private Vehicule vehiculeAmodifier;

    private Attribution attri;

    @EJB
    private PersonnelFacadeLocal personnelFacade;
    private Personnel personnel = new Personnel();
    private Personnel personnelSelected = new Personnel();
    private List<Personnel> listPersonnel = new ArrayList<>();

    private Structure structure;

    @EJB
    private RegionFacadeLocal regionFacadeLocal;
    private Region region;
    private List<Region> regions = new ArrayList<>();

    private String msg = "";
    private boolean bouton = true;

    protected String repertoitre = Utilitaires.path + "/" + utils.Utilitaires.repertoireDefautAttribution;
    protected String fichier = Utilitaires.nomFichierParDefautAttribution;

    private boolean imprimer = true;
    private boolean imprimerTrie = true;
    private boolean imprimerStructure = true;

    @PostConstruct
    private void init() {
        vehicule = new Vehicule();
        personnel = new Personnel();
        attribution = new Attribution();
        attri = new Attribution();
        region = new Region();
        structure = new Structure();
        vehiculeAmodifier = new Vehicule();
        System.err.println("le postconstruct appélé");
    }

    public AttributionController() {
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

    public Attribution prepareCreate() {
        System.err.println("le prepare create apele");
        return attribution;
    }

    public void prepare() {
        System.err.println("initial");
    }

    public void prepareEdit() {
        listPersonnel.clear();
        listPersonnel.addAll(personnelFacade.findAll());
        listVehiculeNonSelection = vehiculeFacade.getVehicule(true, false);
        if (attribution != null) {
            personnel.setIdPersonnel(attribution.getIdPersonnel().getIdPersonnel());
            vehicule.setIdVehicule(attribution.getIdVehicule().getIdVehicule());
            vehiculeAmodifier = attribution.getIdVehicule();
            listVehiculeNonSelection.add(attribution.getIdVehicule());
        }
    }

    public void prepareDetail() {
        if (attribution != null) {
            personnel = attribution.getIdPersonnel();
            vehicule = attribution.getIdVehicule();
        }
    }

    public void prepareDelete() {
        if (personnel != null) {
            msg = "Voulez-vous vraiment supprimer l'attribution selectionnée ?";
        }
    }

    public void affichageAttribution() {
        listAttribution = attributionFacade.findAll();
    }

    public void saveAttribution() {
        try {
            if (vehicule.getIdVehicule() != null) {

                if (personnel.getIdPersonnel() != null) {

                    if (vehicule.getDatepremiereaattribution() == null) {
                        vehicule.setDatepremiereaattribution(new Date());
                        vehiculeFacade.edit(vehicule);
                    }

                    vehicule.setDisponible(false);
                    vehiculeFacade.edit(vehicule);

                    attri.setIdAttribution(attributionFacade.nextId());

                    attri.setIdVehicule(vehicule);
                    attri.setIdPersonnel(personnel);

                    attri.setDaterestitution(null);
                    attri.setDatesyst(new Date());
                    attri.setAttr(true);

                    attributionFacade.create(attri);

                    listeVehiculeNonAttribuer();
                    getListAttribution();
                    personnel = new Personnel();
                    vehicule = new Vehicule();
                    attri = new Attribution();

                    msg = "Enregistrement effectué avec succès";
                    JsfUtil.addSuccessMessage(msg);
                } else {
                    JsfUtil.addErrorMessage("veuillez selectionnez le personnel");
                }
            } else {
                JsfUtil.addErrorMessage("Veuillez selectionner un vehicule");
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageAttribution();
            activeBouton();
        }

    }

    public void editAttribution() {
        try {
            attribution.setIdPersonnel(personnel);
            attribution.setIdVehicule(vehicule);

            if (vehicule.getDatepremiereaattribution() == null) {
                vehicule.setDatepremiereaattribution(new Date());
            }

            if (vehiculeAmodifier != null) {
                vehiculeAmodifier.setDisponible(false);
                vehiculeFacade.edit(vehiculeAmodifier);
            }

            vehicule.setDisponible(false);
            vehiculeFacade.edit(vehicule);

            attributionFacade.edit(attribution);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = "Echec de l'opération";
        } finally {
            affichageAttribution();
            listeVehiculeNonAttribuer();
        }
    }

    public void deleteAttribution() {
        try {
            attributionFacade.remove(attribution);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = "Echec de l'opération!";
        } finally {
            affichageAttribution();
            desactiveBouton();
        }
    }

    public void filterAttribution() {
        if (region.getIdRegion() != null) {
            attributiontries = attributionFacade.get(region.getIdRegion(), false, true);
            if (!attributiontries.isEmpty()) {
                imprimerTrie = false;
            } else {
                imprimerTrie = true;
            }
        } else {
            attributiontries.clear();
            imprimerTrie = true;
            imprimer = true;
        }
    }

    public void filterAttributionStructure() {
        if (structure.getIdStructure() != null) {
            attributionStructures = attributionFacade.getByStructure(structure.getIdStructure());
            if (!attributionStructures.isEmpty()) {
                imprimerStructure = false;
            } else {
                imprimerStructure = true;
            }
        } else {
            attributionStructures.clear();
            imprimerStructure = true;
        }
    }

    public void imprimerTri() {
        try {
            region = regionFacadeLocal.find(region.getIdRegion());
            Printer.printAttributedVehicule(attributiontries, region);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imprimerFicheDetention() {
        try {
            Printer.printDetentionSheet(listAttribution, getRegions());
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void listPersonnelNonAttribues() {

    }

    public List<Personnel> getListPersonnelNonSelection() {
        return listPersonnelNonSelection;
    }

    public void setListPersonnelNonSelection(List<Personnel> listPersonnelNonSelection) {
        this.listPersonnelNonSelection = listPersonnelNonSelection;
    }

    public void listeVehiculeNonAttribuer() {
        listVehiculeNonSelection = vehiculeFacade.getVehicule(true, false);
    }

    public void listepersonnel() {
        if (getSelected() != null) {
            System.out.println("The selected Personnel: " + getSelected().getIdPersonnel().getIdPersonnel());
        }
        listPersonnel.clear();
        listPersonnel.addAll(personnelFacade.findAll());
    }

    public void vehiculeSelection() {

    }

    public void personnelSelection() {

    }

    public boolean isImprimerTrie() {
        return imprimerTrie;
    }

    public void setImprimerTrie(boolean imprimerTrie) {
        this.imprimerTrie = imprimerTrie;
    }

    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    public List<Attribution> getListAttribution() {
        listAttribution = attributionFacade.get(true, false);
        return listAttribution;
    }

    public void setListAttribution(List<Attribution> listAttribution) {
        this.listAttribution = listAttribution;
    }

    public List<Vehicule> getListVehicule() {
        listVehicule = vehiculeFacade.findAll();
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

        listPersonnel = personnelFacade.findAll();
        return listPersonnel;
    }

    public void setListPersonnel(List<Personnel> listPersonnel) {
        this.listPersonnel = listPersonnel;
    }

    public boolean isBouton() {
        return bouton;
    }

    public void setBouton(boolean bouton) {
        this.bouton = bouton;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Vehicule> getListVehiculeNonSelection() {
        listVehiculeNonSelection = vehiculeFacade.getVehicule(true, false);
        return listVehiculeNonSelection;
    }

    public void setListVehiculeNonSelection(List<Vehicule> listVehiculeNonSelection) {
        this.listVehiculeNonSelection = listVehiculeNonSelection;
    }

    public Personnel getPersonnelSelected() {

        return personnelSelected;
    }

    public void setPersonnelSelected(Personnel personnelSelected) {
        this.personnelSelected = personnelSelected;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Region> getRegions() {
        regions = regionFacadeLocal.findAll();
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<Attribution> getAttributiontries() {
        return attributiontries;
    }

    public void setAttributiontries(List<Attribution> attributiontries) {
        this.attributiontries = attributiontries;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<Attribution> getAttributionStructures() {
        return attributionStructures;
    }

    public void setAttributionStructures(List<Attribution> attributionStructures) {
        this.attributionStructures = attributionStructures;
    }

    public boolean isImprimerStructure() {
        return imprimerStructure;
    }

    public void setImprimerStructure(boolean imprimerStructure) {
        this.imprimerStructure = imprimerStructure;
    }

    public boolean isImprimer() {
        imprimer = listAttribution.isEmpty();
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }

    public Attribution getAttri() {
        return attri;
    }

    public void setAttri(Attribution attri) {
        this.attri = attri;
    }

}
