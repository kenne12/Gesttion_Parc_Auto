/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Reforme;
import entities.Vehicule;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.primefaces.event.ColumnResizeEvent;
import sessions.ReformeFacadeLocal;
import sessions.VehiculeFacadeLocal;
import utils.Utilitaires;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class ReformeController implements Serializable {

    @EJB
    private ReformeFacadeLocal reformeFacade;
    private List<Reforme> listReforme = null;
    private Reforme reforme;

    @EJB
    private VehiculeFacadeLocal vehiculeFacade;
    private List<Vehicule> listVehicule = new ArrayList<>();
    private Vehicule vehicule;
    
    private List<Vehicule> vehiculesAReformer = new ArrayList<>();
    private Vehicule vehiculeAReformer;

    private String msg = "";
    private boolean bouton = true;
    
    private boolean imprimer = true;
    private String repertoire = Utilitaires.path+"/"+Utilitaires.repertoireDefautHistoriqueReforme+"/";
    private String fichier;
    
    private Date date;
    
    
    @PostConstruct
    private void init(){
        vehicule = new Vehicule();
        vehiculeAReformer = new Vehicule();
        reforme = new Reforme();
        date = new Date();
    }
    
    

    public ReformeController() {
        
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

    public void affichageReforme() {
        listReforme.clear();
        listReforme.addAll(reformeFacade.findAll());
    }

    public Reforme prepareCreate() {
        reforme = new Reforme();
        vehicule = new Vehicule();
        listReforme.clear();
        listReforme.addAll(reformeFacade.findAll());
        return reforme;
    }

    public void prepareEdit() {
        listReforme.clear();
        listReforme.addAll(reformeFacade.findAll());
        reforme = new Reforme();
        if (reforme != null) {
            vehicule.setIdVehicule(reforme.getIdVehicule().getIdVehicule());
        }
    }

    public void prepareDelete() {
        if (reforme != null) {
            msg = "Voulez-vous vraiment supprimer la reforme selectionnée ?";
        }
    }

    public void saveReforme() {
        try {
            
            if(vehiculeAReformer!=null){                
                reforme.setIdReforme(reformeFacade.nextId());

                vehiculeAReformer.setEtat("Reforme");
                vehiculeAReformer.setDisponible(false);
                vehiculeAReformer.setReformed(true);
                vehiculeFacade.edit(vehiculeAReformer);

                reforme.setIdVehicule(vehiculeAReformer);
                reformeFacade.create(reforme);

                vehiculeAReformer = new Vehicule();
                findVehiculeAReformer(); 
                getListReforme();

                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            }else{
                JsfUtil.addErrorMessage("veuillez selectionner un vehicule");
            }
        } catch (Exception e) {
            msg = "Echec de l'opération";
            e.printStackTrace();
        } finally {
            affichageReforme();
            activeBouton();
        }
    }

    public void editReforme() {
        try {
            reforme.setIdVehicule(vehicule);
            reformeFacade.edit(reforme);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageReforme();
        }

    }

    public void deleteReforme() {
        try {
            reformeFacade.remove(reforme);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageReforme();
            desactiveBouton();
        }
    }
    
    public void findVehiculeAReformer(){
        vehiculesAReformer.clear(); 
        List<Vehicule> results = vehiculeFacade.getVehicule(false);
        
        if(!results.isEmpty()){
            for(Vehicule v : results){
                Date date = new Date();                
                DateTime dateAtt = new DateTime(""+(v.getDatepremiereaattribution().getYear()+1900)+"-"+(v.getDatepremiereaattribution().getMonth()+1)+"-"+v.getDatepremiereaattribution().getDate()+"");
               
                DateTime dateAct = new DateTime(""+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+""); 

                Years year = Years.yearsBetween(dateAtt, dateAct);
                
                if(year.getYears()>=v.getDureevie()){
                    vehiculesAReformer.add(v);
                }               
                System.out.println(year.getYears() + " an entre les deux dates"); 
            }
        }                
    } 
    
    public void filtre(){
        vehiculesAReformer.clear();
        List<Vehicule>vehicules1 = vehiculeFacade.getVehicule(false);
        
        if(!vehicules1.isEmpty()){            
            for(Vehicule v : vehicules1){
                DateTime dateAtt = new DateTime(""+(v.getDatepremiereaattribution().getYear()+1900)+"-"+(v.getDatepremiereaattribution().getMonth()+1)+"-"+v.getDatepremiereaattribution().getDate()+"");               
                DateTime dateAct = new DateTime(""+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+"");                                    
                Years year = Years.yearsBetween(dateAtt, dateAct);
                if(year.getYears()>=v.getDureevie()){
                    vehiculesAReformer.add(v);
                }               
                System.out.println(year.getYears() + "an entre les deux dates"); 
           }            
        }                
    }
    
    public String moneyFormater(Integer money){
        return Utilitaires.formaterStringMoney(money);
    } 
   
    /*public void filtre(){
        vehiculesAReformer.clear();        
        List<Vehicule>vehicules1 = vehiculeFacade.getVehicule(false);        
        if(!vehicules1.isEmpty()){
            
            for(Vehicule v : vehicules1){
                DateTime dateAtt = new DateTime(""+(v.getDatepremiereaattribution().getYear()+1900)+"-"+(v.getDatepremiereaattribution().getMonth()+1)+"-"+v.getDatepremiereaattribution().getDate()+"");               
                DateTime dateAct = new DateTime(""+(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate()+"");                                    
                Years year = Years.yearsBetween(dateAtt, dateAct);
                if(year.getYears()>=v.getDureevie()){
                    vehiculesAReformer.add(v);
                }               
                System.out.println(year.getYears() + " an entre les deux dates"); 
           }            
        }
        if(vehiculesAReformer.isEmpty()){
            //this.setPrintable(true);
        }else{
            //this.setPrintable(false);
        }        
    } */
  
    

    public List<Reforme> getListReforme() {
        listReforme = reformeFacade.findAll();
        return listReforme;
    }

    public void setListReforme(List<Reforme> listReforme) {
        this.listReforme = listReforme;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
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

    public Reforme getReforme() {
        return reforme;
    }

    public void setReforme(Reforme reforme) {
        this.reforme = reforme;
    }

    public List<Vehicule> getListVehicule() {
        listVehicule = vehiculeFacade.getVehicule(true, false);
        return listVehicule;
    }

    public void setListVehicule(List<Vehicule> listVehicule) {
        this.listVehicule = listVehicule;
    }

    public boolean isImprimer() {
        imprimer = reformeFacade.findAll().isEmpty();
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }

    public String getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

    public String getFichier() {
        return Utilitaires.nomFichierParDefautHistoriqueReforme;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public List<Vehicule> getVehiculesAReformer() {
        return vehiculesAReformer;
    }

    public void setVehiculesAReformer(List<Vehicule> vehiculesAReformer) {
        this.vehiculesAReformer = vehiculesAReformer;
    }

    public Vehicule getVehiculeAReformer() {
        return vehiculeAReformer;
    }

    public void setVehiculeAReformer(Vehicule vehiculeAReformer) {
        this.vehiculeAReformer = vehiculeAReformer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
