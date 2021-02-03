/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.District;
import entities.Region;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.DistrictFacadeLocal;
import sessions.RegionFacadeLocal;

/**
 *
 * @author gervais
 */
@ManagedBean
@SessionScoped
public class DistrictController {

    @EJB
    private DistrictFacadeLocal districtFacadeLocal;
    private District district;
    private List<District>listDistrict = new ArrayList<>();
    private District selectedDistrict;
    /**
     * Creates a new instance of DistrictController
     */
    
    @EJB
    private RegionFacadeLocal regionFacadeLocal;
    private Region region;
    private List<Region>regions = new ArrayList<>();
     
   
    public DistrictController() {
        
    }
    
    @PostConstruct
    private void init(){
        district= new District();
        selectedDistrict = new District();
        region = new Region();
    }
    
    private String msg = "";
    private boolean bouton = true;
    
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
    
    public void prepareEdit(){
        if(selectedDistrict!=null){
            region = selectedDistrict.getIdRegion();
        }
        
    }
    
    public void prepareDelete() {
        if (selectedDistrict != null) {
            msg = "Voulez-vous vraiment supprimer la réparation selectionnée ?";
        }
    }
    
    public void saveDistrict() {
            try {
                if(region.getIdRegion()!=null){
                                    
                    listDistrict = districtFacadeLocal.findByNom(district.getNom());
                    if (listDistrict.isEmpty()) {                        
                        district.setIdDistrict(districtFacadeLocal.nextId());
                        district.setIdRegion(region);
                        districtFacadeLocal.create(district);
                        msg = "Enregistrement effectué avec succès";
                        JsfUtil.addSuccessMessage(msg);
                    } else {
                        msg = "" + district.getNom() + " existe déjà";
                        JsfUtil.addErrorMessage(msg);
                    }
                
                }else{
                    JsfUtil.addErrorMessage("Veuillez selectionner une région");
                }
            } catch (Exception e) {
                msg = "Echec de l'opération!";
            } finally {
                getListDistrict();
                activeBouton();
            }
        }
        
        
    public void editDistrict() {
        try {            
            selectedDistrict.setIdRegion(region);
            districtFacadeLocal.edit(selectedDistrict);            
            //msg = "Modification effectuée avec succès";
            //JsfUtil.addSuccessMessage(msg);
            JsfUtil.addSuccessMessage("Opération réussie");
        } catch (Exception e) {
            msg = "Echec de l'opération";
        } finally {
            getListDistrict();
        }

    }
    
    public void deleteDistrict() {
        try {
            
            districtFacadeLocal.remove(selectedDistrict);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            getDistrict();
            desactiveBouton();
        }
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
    
    

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<District> getListDistrict() {
        listDistrict = districtFacadeLocal.findAll();
        return listDistrict;
    }

    public void setListDistrict(List<District> listDistrict) {
        this.listDistrict = listDistrict;
    }

    public District getSelectedDistrict() {
        return selectedDistrict;
    }

    public void setSelectedDistrict(District selectedDistrict) {
        this.selectedDistrict = selectedDistrict;
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
    
    
    
}
