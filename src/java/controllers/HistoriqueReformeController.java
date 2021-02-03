/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Reforme;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sessions.ReformeFacadeLocal;
import utils.Printer;

/**
 *
 * @author gervais
 */
@ManagedBean
@SessionScoped
public class HistoriqueReformeController {

    /**
     * Creates a new instance of HistoriqueReformeController
     */
    
    @EJB
    private ReformeFacadeLocal reformeFacadeLocal;
    private Reforme reforme;
    private List<Reforme>reformes = new ArrayList<>();
    
    private boolean imprimer = true;
    
    public HistoriqueReformeController() {
        
    }
    
    @PostConstruct
    private void init(){
        reforme = new Reforme();
    }
    
    public void imprimerReforme(){
        try {
            Printer.printReformedVehicules(reformeFacadeLocal.findAll());
        } catch (Exception e) {
            e.getMessage();
        }
    }
    
    

    public Reforme getReforme() {
        return reforme;
    }

    public void setReforme(Reforme reforme) {
        this.reforme = reforme;
    }

    public List<Reforme> getReformes() {
        reformes = reformeFacadeLocal.findAll();
        return reformes;
    }

    public void setReformes(List<Reforme> reformes) {
        this.reformes = reformes;
    }

    public boolean isImprimer() {
        imprimer = reformeFacadeLocal.findAll().isEmpty();
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }
    
    
    
    
}
