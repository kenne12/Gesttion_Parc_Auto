/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.ColumnResizeEvent;
import sessions.ServiceFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class ServiceController implements Serializable {

    @EJB
    private ServiceFacadeLocal serviceFacade;
    private Service service = new Service();
    private List<Service> listService = null;
    private String msg = "";
    private boolean bouton=true;

    public ServiceController() {
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

    public Service prepareCreate() {
        service = new Service();
        return service;
    }

    public void prepareEdit() {
    }

    public void prepareDelete() {
        if (service != null) {
            msg = "Voulez-vous vraiment supprimer le service selectionnée ?";
        }
    }

    public void affichageService() {
        listService.clear();
        listService.addAll(serviceFacade.findAll());
    }

    public void saveService() {
        try {
            listService = serviceFacade.findByNom(service.getNom());
            if (listService.isEmpty()) {
                service.setIdService(serviceFacade.nextId());
                serviceFacade.create(service);
                msg = "Enregistrement effectué avec succès";
            } else {
                msg = "Le service : " + service.getNom().toUpperCase() + " existe déjà";
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
            e.printStackTrace();
        } finally {
            affichageService();
            activeBouton();
        }

    }

    public void editService() {
        try {
            listService = serviceFacade.findByNom(service.getNom());
            if (listService.isEmpty()) {                
                serviceFacade.edit(service);
                msg = "Modification effectuée avec succès";

            } else {
                msg = "Le service " + service.getNom().toUpperCase() + " existe déjà";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            affichageService();
        }

    }

    public void deleteService() {
        try {
            serviceFacade.remove(service);
            msg = "Suppression effectuée avec succès";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            affichageService();
            desactiveBouton();
        }
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Service> getListService() {
        if (listService==null) {
            listService=serviceFacade.findAll();
        }
        return listService;
    }

    public void setListService(List<Service> listService) {
        this.listService = listService;
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


}
