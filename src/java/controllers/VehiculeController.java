/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.JsfUtil;
import entities.Attribution;
import entities.Categorievehicule;
import entities.Marque;
import entities.Modeacquisition;
import entities.Modele;
import entities.Reforme;
import entities.Reparation;
import entities.Sourceenergie;
import entities.Sourcefinancement;
import entities.Vehicule;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ColumnResizeEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sessions.AttributionFacadeLocal;
import sessions.CategorievehiculeFacadeLocal;
import sessions.MarqueFacadeLocal;
import sessions.ModeacquisitionFacadeLocal;
import sessions.ModeleFacadeLocal;
import sessions.SourceenergieFacadeLocal;
import sessions.SourcefinancementFacadeLocal;
import sessions.VehiculeFacadeLocal;
import utils.Printer;
import utils.Utilitaires;

/**
 *
 * @author gervais
 */
@ManagedBean
@ViewScoped
public class VehiculeController {

    @EJB
    private VehiculeFacadeLocal vehiculeFacadeLocal;
    private Vehicule vehicule;
    private List<Vehicule> vehicules = new ArrayList<>();
    private Vehicule selectedVehicule;

    @EJB
    private SourcefinancementFacadeLocal sourcefinancementFacade;
    private List<Sourcefinancement> listSourcefinancement = new ArrayList<>();
    private Sourcefinancement sourcefinancement;

    @EJB
    private SourceenergieFacadeLocal sourceenergieFacade;
    private List<Sourceenergie> listSourceenergie = new ArrayList<>();
    private Sourceenergie sourceenergie;

    @EJB
    private ModeleFacadeLocal modeleFacade;
    private List<Modele> listModele = new ArrayList<>();
    private Modele modele;

    @EJB
    private ModeacquisitionFacadeLocal modeacquisitionFacade;
    private List<Modeacquisition> listModeacquisition = new ArrayList<>();
    private Modeacquisition modeacquisition;

    @EJB
    private CategorievehiculeFacadeLocal categorievehiculeFacade;
    private List<Categorievehicule> listCategorievehicule = new ArrayList<>();
    private Categorievehicule categorievehicule;

    @EJB
    private MarqueFacadeLocal marqueFacade;
    private List<Marque> listMarque = new ArrayList<>();
    private Marque marque;

    private List<Reforme> reformes = new ArrayList<>();
    private List<Reparation> reparations = new ArrayList<>();

    @EJB
    private AttributionFacadeLocal attributionFacadeLocal;
    private List<Attribution> attributions = new ArrayList<>();

    private String photo = "";

    private String fichier_photo = null;
    private String msg = "";
    private boolean bouton = true;
    private boolean showPrintForm = true;
    private boolean selectModel = true;
    private Date date;
    UploadedFile file;
    private final String destination = Utilitaires.path + "/resources/images/";

    //variable qui contient la visibilité d'un bouton
    private boolean printable = true;

    private String repertoire = Utilitaires.path + "/" + Utilitaires.repertoireDefautVehicule;
    private String fichier = Utilitaires.nomFichierParDefautListeVehicule;

    /**
     * Creates a new instance of VehiculeController
     */
    public VehiculeController() {
    }

    @PostConstruct
    private void init() {
        vehicule = new Vehicule();
        selectedVehicule = new Vehicule();
        modeacquisition = new Modeacquisition();
        sourceenergie = new Sourceenergie();
        sourcefinancement = new Sourcefinancement();
        marque = new Marque();
        modele = new Modele();
        categorievehicule = new Categorievehicule();
    }

    public void prepareEdit() {
        System.err.println("edit en cours");

        if ("".equals(selectedVehicule.getPhoto())) {
            photo = Utilitaires.carAvatar;
        } else {
            photo = selectedVehicule.getPhoto();
        }
        marque = selectedVehicule.getIdModele().getIdMarque();
        modeacquisition = selectedVehicule.getIdModeacquisition();
        sourceenergie = selectedVehicule.getIdSourceenergie();
        sourcefinancement = selectedVehicule.getIdSourcefinancement();
        modele = selectedVehicule.getIdModele();
        filtreModele();
    }

    public String moneyFormater(Integer money) {
        return Utilitaires.formaterStringMoney(money);
    }

    public void prepareDelete() {
        if (selectedVehicule != null) {
            msg = "Voulez-vous vraiment supprimer le véhicule selectionné ?";
        }
    }

    public boolean activeBouton() {
        bouton = false;
        return bouton;
    }

    public boolean desactiveBouton() {
        bouton = true;
        return bouton;
    }

    public void prepare() {
        vehicule.setNbrereparation(0);
        vehicule.setNbrerevision(0);
        vehicule.setNbresinistre(0);
    }

    public String computeColor(String etat) {
        switch (etat) {
            case "Fonctionnel":
                return "highlightTableRowGreen";
            case "Reforme":
                return "highlightTableRowOrange";
            case "En arret":
                return "highlightTableRowRed";
            default:
                return null;
        }
    }

    public void imprimerCycledevie() {
        System.err.println("impression lancée");
        if (selectedVehicule.getIdVehicule() != null) {
            if (selectedVehicule.getDatepremiereaattribution() != null) {
                attributions = attributionFacadeLocal.get(selectedVehicule.getIdVehicule());
                reparations = (List<Reparation>) selectedVehicule.getReparationCollection();
                reformes = (List<Reforme>) selectedVehicule.getReformeCollection();
                showPrintForm = true;

                System.err.println("taille attrib " + attributions.size());
                Printer.printCycledevie(selectedVehicule, attributions, reparations, reformes);
            } else {                
                System.err.println("cycle de vie null");
                showPrintForm = false;
            }
        } else {
            System.err.println("pas de vehicule selectionné");
            showPrintForm = false;
        }        
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
            OutputStream output = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[128];
            while ((read = in.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }

            in.close();
            output.flush();
            output.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onResize(ColumnResizeEvent event) {

    }

    public void filtreModele() {
        listModele.clear();
        if (marque.getIdMarque() != null) {
            marque = marqueFacade.find(marque.getIdMarque());
            listModele.addAll(marque.getModeleCollection());
            if (!listModele.isEmpty()) {
                selectModel = false;
            } else {
                selectModel = true;
            }
        } else {
            selectModel = true;
        }
    }

    public void filtreModeleEdit() {
        listModele.clear();
        if (selectedVehicule.getIdModele().getIdMarque().getIdMarque() != null) {
            //marque = marqueFacade.find(selectedVehicule.getIdModele().getIdMarque().getIdMarque()); 
            listModele.addAll(selectedVehicule.getIdModele().getIdMarque().getModeleCollection());
            if (!listModele.isEmpty()) {
                selectModel = false;
            } else {
                selectModel = true;
            }
        } else {
            selectModel = true;
        }
    }

    public void saveVehicule() {
        try {
            if (!"".equals(vehicule.getImmatriculation())) {

                List<Vehicule> vehicules1 = vehiculeFacadeLocal.findByImmatriculation(vehicule.getImmatriculation());
                if (vehicules1.isEmpty()) {
                    vehicule.setIdVehicule(vehiculeFacadeLocal.nextId());
                    vehicule.setIdSourcefinancement(sourcefinancement);
                    vehicule.setIdModele(modele);
                    vehicule.setIdSourceenergie(sourceenergie);
                    vehicule.setIdModeacquisition(modeacquisition);
                    vehicule.setIdCategorievehicule(categorievehicule);
                    if ("".equals(photo)) {
                        photo = Utilitaires.carAvatar;
                    }
                    vehicule.setPhoto(photo);
                    vehicule.setDisponible(true);
                    vehicule.setReformed(false);

                    vehiculeFacadeLocal.create(vehicule);
                    vehicule = new Vehicule();
                    msg = "Enregistrement effectué avec succès";
                    JsfUtil.addSuccessMessage(msg);
                } else {
                    msg = "" + vehicule.getImmatriculation().toUpperCase() + " existe déjà";
                    JsfUtil.addErrorMessage(msg);
                }
            } else {
                JsfUtil.addErrorMessage("Le matricule ne peut etre vide !");
            }
        } catch (Exception e) {
            msg = "Enregistrement effectué avec succès";
            JsfUtil.addSuccessMessage(msg);
        } finally {
            this.getVehicules();
            activeBouton();
        }
        System.err.println("soumis");
        getVehicules();
    }

    public void editVehicule() {
        System.out.println("Méthode editVehicule() éxècutée");
        try {
            selectedVehicule.setIdCategorievehicule(categorievehicule);
            selectedVehicule.setIdModeacquisition(modeacquisition);
            selectedVehicule.setIdModele(modele);
            selectedVehicule.setIdSourcefinancement(sourcefinancement);
            selectedVehicule.setIdSourceenergie(sourceenergie);
            selectedVehicule.setPhoto(photo);

            vehiculeFacadeLocal.edit(selectedVehicule);
            msg = "Modification effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = "Echec de l'opération";
        } finally {
            getVehicules();
        }

    }

    public void deleteVehicule() {
        try {
            if (selectedVehicule != null) {
                vehiculeFacadeLocal.remove(selectedVehicule);
                msg = "Suppression effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                JsfUtil.addErrorMessage("veuillez selectionner un véhicule");
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
        } finally {
            getVehicules();
            desactiveBouton();
        }
    }

    //fonction qui imprime la liste des vehicules
    public void imprimerListVehicule() {
        try {
            Printer.printVehiculeList(vehiculeFacadeLocal.findAll());
            System.err.println("impression réussie");
            System.err.println("" + repertoire + "/" + fichier);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public boolean isPrintable() {
        printable = vehiculeFacadeLocal.findAll().isEmpty();
        return printable;
    }

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public Vehicule getSelectedVehicule() {
        return selectedVehicule;
    }

    public void setSelectedVehicule(Vehicule selectedVehicule) {
        this.selectedVehicule = selectedVehicule;
    }

    public List<Vehicule> getVehicules() {
        vehicules = vehiculeFacadeLocal.findAll();
        return vehicules;
    }

    public void setListVehicule(List<Vehicule> listVehicule) {
        this.vehicules = listVehicule;
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

    public List<Sourcefinancement> getListSourcefinancement() {
        listSourcefinancement = sourcefinancementFacade.findAll();
        return listSourcefinancement;
    }

    public void setListSourcefinancement(List<Sourcefinancement> listSourcefinancement) {
        this.listSourcefinancement = listSourcefinancement;
    }

    public Sourcefinancement getSourcefinancement() {
        return sourcefinancement;
    }

    public void setSourcefinancement(Sourcefinancement sourcefinancement) {
        this.sourcefinancement = sourcefinancement;
    }

    public List<Sourceenergie> getListSourceenergie() {
        listSourceenergie = sourceenergieFacade.findAll();
        return listSourceenergie;
    }

    public void setListSourceenergie(List<Sourceenergie> listSourceenergie) {
        this.listSourceenergie = listSourceenergie;
    }

    public Sourceenergie getSourceenergie() {
        return sourceenergie;
    }

    public void setSourceenergie(Sourceenergie sourceenergie) {
        this.sourceenergie = sourceenergie;
    }

    public List<Modele> getListModele() {
        return listModele;
    }

    public void setListModele(List<Modele> listModele) {
        this.listModele = listModele;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public List<Modeacquisition> getListModeacquisition() {
        listModeacquisition = modeacquisitionFacade.findAll();
        return listModeacquisition;
    }

    public void setListModeacquisition(List<Modeacquisition> listModeacquisition) {
        this.listModeacquisition = listModeacquisition;
    }

    public Modeacquisition getModeacquisition() {
        return modeacquisition;
    }

    public void setModeacquisition(Modeacquisition modeacquisition) {
        this.modeacquisition = modeacquisition;
    }

    public List<Categorievehicule> getListCategorievehicule() {
        listCategorievehicule = categorievehiculeFacade.findAll();
        return listCategorievehicule;
    }

    public void setListCategorievehicule(List<Categorievehicule> listCategorievehicule) {
        this.listCategorievehicule = listCategorievehicule;
    }

    public Categorievehicule getCategorievehicule() {
        return categorievehicule;
    }

    public void setCategorievehicule(Categorievehicule categorievehicule) {
        this.categorievehicule = categorievehicule;
    }

    public List<Marque> getListMarque() {
        listMarque = marqueFacade.findAll();
        return listMarque;
    }

    public void setListMarque(List<Marque> listMarque) {
        this.listMarque = listMarque;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFichier_photo() {
        return fichier_photo;
    }

    public void setFichier_photo(String fichier_photo) {
        this.fichier_photo = fichier_photo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSelectModel() {
        return selectModel;
    }

    public void setSelectModel(boolean selectModel) {
        this.selectModel = selectModel;
    }

    public String getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public boolean isShowPrintForm() {
        return showPrintForm;
    }

    public void setShowPrintForm(boolean showPrintForm) {
        this.showPrintForm = showPrintForm;
    }

}
