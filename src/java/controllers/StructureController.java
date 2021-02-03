/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Adresse;
import entities.Arrondissement;
import entities.District;
import entities.Region;
import entities.Statutstructure;
import entities.Structure;
import entities.Typestructure;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.AdresseFacadeLocal;
import sessions.ArrondissementFacadeLocal;
import sessions.DistrictFacadeLocal;
import sessions.RegionFacadeLocal;
import sessions.StatutstructureFacadeLocal;
import sessions.StructureFacadeLocal;
import sessions.TypestructureFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@ViewScoped
public class StructureController implements Serializable {

    @EJB
    private StructureFacadeLocal structureFacade;
    private List<Structure> listStructure = new  ArrayList<>();
    private Structure structure;
    private Structure selectedStructure;

    @EJB
    private ArrondissementFacadeLocal arrondissementFacade;
    private Arrondissement arrondissement;
    private List<Arrondissement> listArrondissement = new ArrayList<>();

    @EJB
    private TypestructureFacadeLocal typestructureFacade;
    private List<Typestructure> listTypestructure = new ArrayList<>();
    private Typestructure typestructure;

    @EJB
    private StatutstructureFacadeLocal statutstructureFacade;
    private List<Statutstructure> listStatutstructure = new ArrayList<>();
    private Statutstructure statutstructure = new Statutstructure();

    @EJB
    private AdresseFacadeLocal adresseFacade;
    private Adresse adresse;
    private List<Adresse> listAdresse = new ArrayList<>();
    
    @EJB
    private DistrictFacadeLocal districtFacade;
    private District district;
    private List<District> listDistrict = new ArrayList<>();
    
    @EJB
    private RegionFacadeLocal regionFacade;
    private Region region;
    private List<Region> listRegion = new ArrayList<>();

    private String msg = "";
    private boolean bouton = true;

    public StructureController() {
        
    }
    
    @PostConstruct
    private void init(){
        adresse = new Adresse();
        structure = new Structure();
        district = new District();
        region = new Region();
        arrondissement = new Arrondissement();
        statutstructure = new Statutstructure();  
        typestructure = new Typestructure();
        selectedStructure = new Structure();
    }

    public boolean activeBouton() {
        bouton = false;
        return bouton;
    }

    public boolean desactiveBouton() {
        bouton = true;
        return bouton;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<District> getListDistrict() {
        return listDistrict;
    }

    public void setListDistrict(List<District> listDistrict) {
        this.listDistrict = listDistrict;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Region> getListRegion() {
        listRegion = regionFacade.findAll();
        return listRegion;
    }

    public void setListRegion(List<Region> listRegion) {
        this.listRegion = listRegion;
    }
    
    

    public void onResize(ColumnResizeEvent event) {
        
    }
    
    
    
    public void filtreDistrict(){
        System.err.println("filtre apelé");
        listDistrict.clear();
        if(region.getIdRegion()!=null){            
            System.err.println("c'est bon");
            listDistrict = districtFacade.findByRegion(region.getIdRegion());
            System.err.println(""+listDistrict.size());
        }else{
            listDistrict.clear();
        }       
        
        
    }

    public List<Structure> filtreStructure() {

        if (((typestructure.getIdTypestructure() != null) && (statutstructure.getIdStatutstructure() == null) && (arrondissement.getIdArrondissement() == null)) || ((typestructure.getIdTypestructure() != 0) && (statutstructure.getIdStatutstructure() == 0) && (arrondissement.getIdArrondissement() == 0))) {
            System.out.println("Faire le choix typestructure ========> " + typestructure.getIdTypestructure());
            System.out.println("Faire le choix statutstructure ========> " + statutstructure.getIdStatutstructure());
            System.out.println("Faire le choix arrondissement ========> " + arrondissement.getIdArrondissement());
            System.out.println("Vous avez tout choisi 222");
            listStructure.clear();
            listStructure.addAll(structureFacade.findByTypeStructure(typestructure.getIdTypestructure()));
            return listStructure;
        } else if (((typestructure.getIdTypestructure() == null) && (statutstructure.getIdStatutstructure() != null) && (arrondissement.getIdArrondissement() == null)) || ((typestructure.getIdTypestructure() == 0) && (statutstructure.getIdStatutstructure() != 0) && (arrondissement.getIdArrondissement() == 0))) {
            System.out.println("Faire le choix typestructure ========> " + typestructure.getIdTypestructure());
            System.out.println("Faire le choix statutstructure ========> " + statutstructure.getIdStatutstructure());
            System.out.println("Faire le choix arrondissement ========> " + arrondissement.getIdArrondissement());
            System.out.println("Vous avez tout choisi 333");
            listStructure.clear();
            //listStructure.addAll(structureFacade.findByStatutStructure(statutstructure.getIdStatutstructure()));
            //listStructure.addAll(structureFacade.findByArrondissement(arrondissement.getIdArrondissement()));
            return listStructure;
        } else if (((typestructure.getIdTypestructure() == null) && (statutstructure.getIdStatutstructure() == null) && (arrondissement.getIdArrondissement() != null)) || ((typestructure.getIdTypestructure() == 0) && (statutstructure.getIdStatutstructure() == 0) && (arrondissement.getIdArrondissement() != 0))) {
            System.out.println("Faire le choix typestructure ========> " + typestructure.getIdTypestructure());
            System.out.println("Faire le choix statutstructure ========> " + statutstructure.getIdStatutstructure());
            System.out.println("Faire le choix arrondissement ========> " + arrondissement.getIdArrondissement());
            System.out.println("Vous avez tout choisi 333");
            listStructure.clear();
            listStructure.addAll(structureFacade.findByArrondissement(arrondissement.getIdArrondissement()));
            return listStructure;
        } else if (((typestructure.getIdTypestructure() == null) && (statutstructure.getIdStatutstructure() == null) && (arrondissement.getIdArrondissement() == null)) || ((typestructure.getIdTypestructure() == 0) && (statutstructure.getIdStatutstructure() == 0) && (arrondissement.getIdArrondissement() == 0))) {
            System.out.println("Faire le choix typestructure ========> " + typestructure.getIdTypestructure());
            System.out.println("Faire le choix statutstructure ========> " + statutstructure.getIdStatutstructure());
            System.out.println("Faire le choix arrondissement ========> " + arrondissement.getIdArrondissement());
            System.out.println("Vous avez tout choisi 111");
            listStructure.clear();
            return listStructure;
        } else if (typestructure.getIdTypestructure() != null && statutstructure.getIdStatutstructure() != null && arrondissement.getIdArrondissement() == null) {
            listStructure.addAll(structureFacade.findByTypeStatut(typestructure.getIdTypestructure(), statutstructure.getIdStatutstructure()));
            return listStructure;
        } else if (typestructure.getIdTypestructure() != null && statutstructure.getIdStatutstructure() == null && arrondissement.getIdArrondissement() != null) {
            listStructure.addAll(structureFacade.findByTypeArrondissement(typestructure.getIdTypestructure(), arrondissement.getIdArrondissement()));
            return listStructure;
        } else if (typestructure.getIdTypestructure() == null && statutstructure.getIdStatutstructure() != null && arrondissement.getIdArrondissement() != null) {
            //listStructure.addAll(structureFacade.findByStatutArrondissement(statutstructure.getIdStatutstructure(), arrondissement.getIdArrondissement()));
            return listStructure;
        } else if (typestructure.getIdTypestructure() != null && statutstructure.getIdStatutstructure() != null && arrondissement.getIdArrondissement() != null) {
            System.out.println("Faire le choix typestructure ========> " + typestructure.getIdTypestructure());
            System.out.println("Faire le choix statutstructure ========> " + statutstructure.getIdStatutstructure());
            System.out.println("Faire le choix arrondissement ========> " + arrondissement.getIdArrondissement());
            System.out.println("Vous avez tout choisi 444");
            listStructure.clear();
            listStructure.addAll(structureFacade.findByTypeStatutArrondissement(typestructure.getIdTypestructure(), statutstructure.getIdStatutstructure(), arrondissement.getIdArrondissement()));
            return listStructure;
        } else {
            return null;
        }

    }

//    public void filtreStructureParType() {
//        listStructure.clear();
//        typestructure = typestructureFacade.find(typestructure.getIdTypestructure());
//        if (typestructure != null) {
//            listStructure.addAll(typestructure.getStructureCollection());
//        } else {
//            typestructure = new Typestructure();
//            listStructure.addAll(structureFacade.findAll());
//        }
//    }
//    public void filtreStructureParStatut() {
//        listStructure.clear();
//        statutstructure = statutstructureFacade.find(statutstructure.getIdStatutstructure());
//        if (statutstructure != null) {
//            listStructure.addAll(statutstructure.getStructureCollection());
//        } else {
//            statutstructure = new Statutstructure();
//            listStructure.addAll(structureFacade.findAll());
//        }
//    }
//    public void filtreStructureParArrondissement() {
//        listStructure.clear();
//        arrondissement = arrondissementFacade.find(arrondissement.getIdArrondissement());
//        if (arrondissement != null) {
//            listStructure.addAll(arrondissement.getStructureCollection());
//        } else {
//            arrondissement = new Arrondissement();
//            listStructure.addAll(structureFacade.findAll());
//        }
//    } 
    public Structure prepareCreate() {
        
        return structure;
    }
    
    public void prepare(){
        
    }

    public void prepareEdit() {
        region = selectedStructure.getIdRegion();
        statutstructure= selectedStructure.getIdStatutstructure();
        typestructure = selectedStructure.getIdTypestructure();
        district = selectedStructure.getIdDistrict();
        arrondissement = selectedStructure.getIdArrondissement();
    }

    public void prepareDelete() {
        if (structure != null) {
            msg = "Voulez-vous vraiment supprimer la structure selectionnée ?";
        }
    }

    public void affichageStructure() {
        listStructure.clear();
        listStructure.addAll(structureFacade.findAll());
        listTypestructure.clear();
        listTypestructure.addAll(typestructureFacade.findAll());
        listStatutstructure.clear();
        listStatutstructure.addAll(statutstructureFacade.findAll());
        listArrondissement.clear();
        listArrondissement.addAll(arrondissementFacade.findAll());
        listDistrict.clear();
        listDistrict.addAll(districtFacade.findAll());
        listRegion.clear();
        listRegion.addAll(regionFacade.findAll());
    }

    public void saveStructure() {
        try {
           List<Structure> listes = structureFacade.findByNom(structure.getNom());
            if (listes.isEmpty()) {                
                adresse.setIdAdresse(adresseFacade.nextId());
                adresseFacade.create(adresse);
                
                structure.setIdStructure(structureFacade.nextId());
                structure.setIdTypestructure(typestructure);
                structure.setIdStatutstructure(statutstructure);
                structure.setIdArrondissement(arrondissement);
                structure.setIdAdresse(adresse);
                structure.setIdDistrict(district);
                structure.setIdRegion(region);
                
                structureFacade.create(structure);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + structure.getNom().toUpperCase() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            JsfUtil.addErrorMessage(msg);
            e.printStackTrace();
        } finally {
            affichageStructure();
            activeBouton();
        }

    }

    public void editStructure() {
        try {           
            selectedStructure.setIdRegion(region);
            selectedStructure.setIdArrondissement(arrondissement);
            selectedStructure.setIdDistrict(district);
            selectedStructure.setIdStatutstructure(statutstructure);
            selectedStructure.setIdTypestructure(typestructure);            
            structureFacade.edit(selectedStructure);
            
            msg = "Modification effectuée avec succès";
            
            JsfUtil.addSuccessMessage(msg);
            
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
            JsfUtil.addErrorMessage(msg);
        } finally {
            affichageStructure();
        }

    }

    public void deleteStructure() {
        try {
            structureFacade.remove(structure);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
            JsfUtil.addErrorMessage(msg);
        } finally {
            affichageStructure();
            desactiveBouton();
        }
    }

    public List<Structure> getListStructure() {        
            listStructure = structureFacade.findAll();        
            return listStructure;
    }

    public void setListStructure(List<Structure> listStructure) {
        this.listStructure = listStructure;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
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

    public Arrondissement getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(Arrondissement arrondissement) {
        this.arrondissement = arrondissement;
    }

    public List<Arrondissement> getListArrondissement() {
        listArrondissement = arrondissementFacade.findAll();
        return listArrondissement;
    }

    public void setListArrondissement(List<Arrondissement> listArrondissement) {
        this.listArrondissement = listArrondissement;
    }

    public List<Typestructure> getListTypestructure() {
        listTypestructure = typestructureFacade.findAll();
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

    public List<Statutstructure> getListStatutstructure() {
        listStatutstructure = statutstructureFacade.findAll();
        return listStatutstructure;
    }

    public void setListStatutstructure(List<Statutstructure> listStatutstructure) {
        this.listStatutstructure = listStatutstructure;
    }

    public Statutstructure getStatutstructure() {
        return statutstructure;
    }

    public void setStatutstructure(Statutstructure statutstructure) {
        this.statutstructure = statutstructure;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Adresse> getListAdresse() {
        listAdresse = adresseFacade.findAll();
        return listAdresse;
    }

    public void setListAdresse(List<Adresse> listAdresse) {
        this.listAdresse = listAdresse;
    }

    public Structure getSelectedStructure() {
        return selectedStructure;
    }

    public void setSelectedStructure(Structure selectedStructure) {
        this.selectedStructure = selectedStructure;
    }
    
    

}
