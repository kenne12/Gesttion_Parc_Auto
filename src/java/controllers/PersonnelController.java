/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Adresse;
import entities.Attribution;
import entities.Fonction;
import entities.Personnel;
import entities.Service;
import entities.Structure;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.ColumnResizeEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sessions.AdresseFacadeLocal;
import sessions.AttributionFacadeLocal;
import sessions.FonctionFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.ServiceFacadeLocal;
import sessions.StructureFacadeLocal;
import utils.Printer;
import utils.Utilitaires;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class PersonnelController implements Serializable {

    @EJB
    private PersonnelFacadeLocal personnelFacade;
    private Personnel personnel = new Personnel();
    private List<Personnel> listPersonnel = null;
    private List<Personnel> listPersonnel1 = new ArrayList<>();

    @EJB
    private StructureFacadeLocal structureFacade;
    private Structure structure = new Structure();
    private List<Structure> listStructure = new ArrayList<>();

    @EJB
    private AdresseFacadeLocal adresseFacade;
    private Adresse adresse = new Adresse();
    private List<Adresse> listAdresse = new ArrayList<>();

    @EJB
    private FonctionFacadeLocal fonctionFacade;
    private Fonction fonction = new Fonction();
    private List<Fonction> listFonction = new ArrayList<>();

    @EJB
    private ServiceFacadeLocal serviceFacade;
    private Service service = new Service();
    private List<Service> listService = new ArrayList();

    @EJB
    private AttributionFacadeLocal attributionFacadeLocal;
    private Attribution attribution = new Attribution();
    private List<Attribution> attributions = new ArrayList<>();

    private String msg = "";
    private boolean bouton = true;
    private String photo = "";
    UploadedFile file;

    private boolean showFiche;
    private final String destination = Utilitaires.path + "/resources/images/";

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public PersonnelController() {
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

    public void prepareFiche() {

        if (personnel.getIdPersonnel() != null) {
            attributions = attributionFacadeLocal.getByPersonnel(personnel.getIdPersonnel(), false, true);
            if (attributions.isEmpty()) {
                showFiche = false;
            } else {
                showFiche = true;
            }
        } else {
            attributions.clear();
            showFiche = true;
        }
        System.err.println("attr taille "+attributions.size());
    }

    public void imprimerFiche() throws IOException {
        if (personnel.getIdPersonnel() != null) {
            Printer.printFicheDetenteur(personnel, attributions);
        }
    }

    public Personnel prepareCreate() {
        Integer id = new Integer(2);

        fonction = new Fonction();
        service = new Service();
        personnel = new Personnel();
        adresse = new Adresse();
        structure = new Structure();
        photo = "";
        listFonction.clear();
        listFonction.addAll(fonctionFacade.findAll());
        listService.clear();
        listService.addAll(serviceFacade.findAll());
        listStructure.clear();
        listStructure.addAll(structureFacade.findAll());
        return personnel;
    }

    public void prepareEdit() {
        fonction = new Fonction();
        service = new Service();
        structure = new Structure();
        listStructure.clear();
        listStructure.addAll(structureFacade.findAll());
        listFonction.clear();
        listFonction.addAll(fonctionFacade.findAll());
        listService.clear();
        listService.addAll(serviceFacade.findAll());
        if (personnel != null) {
            personnel.setSexe(personnel.getSexe());
            service.setIdService(personnel.getIdService().getIdService());
            fonction.setIdFonction(personnel.getIdFonction().getIdFonction());
            structure.setIdStructure(personnel.getIdStructure().getIdStructure());
            if ("".equals(personnel.getPhoto())) {
                photo = Utilitaires.userAvatar;
            } else {
                photo = personnel.getPhoto();
            }

        }
    }

    public void prepareDelete() {
        if (personnel != null) {
            msg = "Voulez-vous vraiment supprimer le personnel selectionné ?";
        }
    }

    public void affichagePersonnel() {

        listPersonnel.clear();
        listPersonnel.addAll(personnelFacade.findAll());
    }

    public void handleFileUpload(FileUploadEvent event) {
        this.file = event.getFile();
        System.out.println("Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            photo = event.getFile().getFileName();
        } catch (IOException e) {

        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[128];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void savePersonnel() {
        try {
            listPersonnel = personnelFacade.findByMatricule(personnel.getMatricule());
            listPersonnel1 = personnelFacade.findByCni(personnel.getCni());
            if (listPersonnel.isEmpty()) {
                if (listPersonnel1.isEmpty()) {
                    adresse.setIdAdresse(adresseFacade.nextId());
                    adresseFacade.create(adresse);
                    personnel.setIdPersonnel(personnelFacade.nextId());
                    personnel.setIdService(service);
                    personnel.setIdFonction(fonction);
                    personnel.setIdStructure(structure);
                    personnel.setIdAdresse(adresse);
                    if ("".equals(photo)) {
                        photo = Utilitaires.userAvatar;
                    }
                    personnel.setPhoto(photo);
                    personnelFacade.create(personnel);
                    personnel = new Personnel();
                    msg = "Enregistrement effectué avec succès";
                    JsfUtil.addSuccessMessage(msg);
                } else {
                    msg = "" + personnel.getCni() + " existe déjà";
                    JsfUtil.addErrorMessage(msg);
                }
            } else {
                msg = " " + personnel.getMatricule() + "  existent déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
        } finally {
            affichagePersonnel();
            activeBouton();
        }

    }

    public void editPersonnel() {
        try {

            personnel.setIdAdresse(personnel.getIdAdresse());
            adresseFacade.edit(personnel.getIdAdresse());
            personnel.setIdFonction(fonction);
            personnel.setIdStructure(structure);
            personnel.setIdService(service);
            personnel.setPhoto(photo);
            personnelFacade.edit(personnel);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);

        } catch (Exception e) {
            msg = "Echec de l'opération";
            JsfUtil.addErrorMessage(msg);
        } finally {
            affichagePersonnel();
        }

    }

    public void deletePersonnel() {
        try {
            personnelFacade.remove(personnel);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = "Echec de l'opération!";
        } finally {
            affichagePersonnel();
            desactiveBouton();
        }
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public List<Personnel> getListPersonnel() {
        if (listPersonnel == null) {
            listPersonnel = personnelFacade.findAll();
        }
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

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public List<Fonction> getListFonction() {
        return listFonction;
    }

    public void setListFonction(List<Fonction> listFonction) {
        this.listFonction = listFonction;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Service> getListService() {
        return listService;
    }

    public void setListService(List<Service> listService) {
        this.listService = listService;
    }

    public List<Personnel> getListPersonnel1() {
        return listPersonnel1;
    }

    public void setListPersonnel1(List<Personnel> listPersonnel1) {
        this.listPersonnel1 = listPersonnel1;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<Structure> getListStructure() {
        return listStructure;
    }

    public void setListStructure(List<Structure> listStructure) {
        this.listStructure = listStructure;
    }

    public boolean isBouton() {
        return bouton;
    }

    public void setBouton(boolean bouton) {
        this.bouton = bouton;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Adresse> getListAdresse() {
        return listAdresse;
    }

    public void setListAdresse(List<Adresse> listAdresse) {
        this.listAdresse = listAdresse;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Attribution> getAttributions() {
        return attributions;
    }

    public void setAttributions(List<Attribution> attributions) {
        this.attributions = attributions;
    }

    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    public boolean isShowFiche() {
        return showFiche;
    }

    public void setShowFiche(boolean showFiche) {
        this.showFiche = showFiche;
    }

}
