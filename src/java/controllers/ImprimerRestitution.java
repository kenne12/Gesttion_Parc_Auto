/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Attribution;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.AttributionFacadeLocal;
import utils.Printer;

/**
 *
 * @author gervais
 */
@ManagedBean
@ViewScoped
public class ImprimerRestitution {

    /**
     * Creates a new instance of ImprimerRestitution
     */
    
    @EJB
    private AttributionFacadeLocal attributionFacadeLocal;
    private Attribution attribution;
    private List<Attribution>attributions = new ArrayList<>();
    
    private boolean imprimer = true;
    
    public ImprimerRestitution() {
        
    }
    
    @PostConstruct
    private void init(){
        attribution = new Attribution();
    }
    
    public void imprimerRestitution() {
        try {
           Printer.printRestituedVehicule(attributionFacadeLocal.get(false, false));
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
     
     
    public void onResize(ColumnResizeEvent event) {
        
    }

    public boolean isImprimer() {
        imprimer = attributionFacadeLocal.get(false, false).isEmpty();
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }

    
    
    
    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    

    public List<Attribution> getAttributions() {
        attributions = attributionFacadeLocal.get(false, false);
        return attributions;
    }

    public void setAttributions(List<Attribution> attributions) {
        this.attributions = attributions;
    }
  
}
