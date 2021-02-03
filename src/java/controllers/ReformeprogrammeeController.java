/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Vehicule;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.joda.time.DateTime;
import org.joda.time.Years;
import sessions.VehiculeFacadeLocal;
import utils.Printer;
import utils.Utilitaires;

/**
 *
 * @author gervais
 */
@ManagedBean
@SessionScoped
public class ReformeprogrammeeController {

    /**
     * Creates a new instance of ReformeprogrammeeController
     */
    @EJB
    private VehiculeFacadeLocal vehiculeFacadeLocal;
    private Vehicule vehicule;
    private Vehicule selectedVehicule;
    private List<Vehicule>vehicules = new ArrayList<>();
    
    private Date date;
    
    private String fichier;
    private boolean printable = true;
    
    
    
    public ReformeprogrammeeController() {
        
    }
    
    @PostConstruct
    private void init(){
        vehicule = new Vehicule();
        date = new Date();
        selectedVehicule = new Vehicule();
    }
    
    public void filtre(){
        vehicules.clear();        
        List<Vehicule>vehicules1 = vehiculeFacadeLocal.getVehicule(false);
        
        if(!vehicules1.isEmpty()){
            
            for(Vehicule v : vehicules1){
                DateTime dateAtt = new DateTime(""+(v.getDatepremiereaattribution().getYear()+1900)+"-"+(v.getDatepremiereaattribution().getMonth()+1)+"-"+v.getDatepremiereaattribution().getDate()+"");               
                DateTime dateAct = new DateTime(""+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+"");                                    
                Years year = Years.yearsBetween(dateAtt, dateAct);
                if(year.getYears()>=v.getDureevie()){
                    vehicules.add(v);
                }               
                System.out.println(year.getYears() + " an entre les deux dates"); 
           }            
        }
        if(vehicules.isEmpty()){
            this.setPrintable(true);
        }else{
            this.setPrintable(false);
        }        
    }
    
    public void imprimer(){
        try{
           Printer.printReformeProgrammee(vehicules, date);
        }catch(Exception e){
            
        }
        
    }

    public Vehicule getSelectedVehicule() {
        return selectedVehicule;
    }

    public void setSelectedVehicule(Vehicule selectedVehicule) {
        this.selectedVehicule = selectedVehicule;
    }

    public boolean isPrintable() {
        return printable;
    }

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }
    

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }   

    public String getFichier() {
        fichier = Utilitaires.repertoireDefautHistoriqueReforme +"/vehiculeAreformerLe("+date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1)+").pfd"; 
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }
    
    
    
    
    
}
