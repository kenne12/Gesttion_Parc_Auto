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
import javax.faces.bean.RequestScoped;
import org.primefaces.event.ColumnResizeEvent;
import sessions.AttributionFacadeLocal;
import utils.Printer;

/**
 *
 * @author gervais
 */
@ManagedBean
@RequestScoped
public class ImprimerAttributionController {

    /**
     * Creates a new instance of ImprimerAttributionController
     */
    
    @EJB
    private AttributionFacadeLocal attributionFacadeLocal;
    private Attribution attribution;
    private List<Attribution>attributions = new ArrayList<>();
    
    private boolean imprimer = true;
    
    public ImprimerAttributionController() {
        
    }
    
    @PostConstruct
    private void init(){
        attribution = new Attribution();
    }
    
    public void imprimerLIstAttribution(){
        try {
            Printer.printAttributedVehicule(getAttributions());
        } catch (Exception e) {
            e.getCause();
            e.getMessage();
        }
    }
     
    public void onResize(ColumnResizeEvent event) {
        
    }

    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    public List<Attribution> getAttributions() {
        attributions = attributionFacadeLocal.get(true, false);
        return attributions;
    }

    public void setAttributions(List<Attribution> attributions) {
        this.attributions = attributions;
    }

    public boolean isImprimer() {
        imprimer = attributionFacadeLocal.get(true, false).isEmpty();
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }
    
    
    
}
