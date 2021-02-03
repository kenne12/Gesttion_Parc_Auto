/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Attribution;
import entities.Categorievehicule;
import entities.Norme;
import entities.Structure;
import entities.Typestructure;
import entities.Vehicule;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sessions.AttributionFacadeLocal;
import sessions.CategorievehiculeFacadeLocal;
import sessions.NormeFacadeLocal;
import sessions.StructureFacadeLocal;
import sessions.TypestructureFacadeLocal;
import sessions.VehiculeFacadeLocal;
import utils.Printer;
import static utils.Printer.normalFont;
import utils.Utilitaires;

/**
 *
 * @author gervais
 */
@ManagedBean
@ViewScoped
public class AnalyseNormeController {

    /**
     * Creates a new instance of AnalyseNormeController
     */
    @EJB
    private NormeFacadeLocal normeFacadeLocal;
    private Norme norme;
    private List<Norme> normes = new ArrayList<>();

    @EJB
    private AttributionFacadeLocal attributionFacadeLocal;
    private Attribution attribution;
    private List<Attribution> attributions = new ArrayList<>();

    @EJB
    private VehiculeFacadeLocal vehiculeFacadeLocal;
    private Vehicule vehicule;
    private List<Vehicule> vehicules = new ArrayList<>();

    @EJB
    private CategorievehiculeFacadeLocal categorievehiculeFacade;
    private List<Categorievehicule> categorievehicules = new ArrayList<>();
    private Categorievehicule categorievehicule;

    @EJB
    private StructureFacadeLocal StructureFacadeLocal;
    private Structure structure;
    private List<Structure> structures = new ArrayList<>();

    @EJB
    private TypestructureFacadeLocal typestructureFacade;
    private List<Typestructure> typestructures = new ArrayList<>();
    private Typestructure typestructure;

    private boolean imprimer = true;

    private boolean disableTypeStructure = false;
    private boolean disableMode = false;

    private boolean search = false;

    private boolean showPrinterDialog = true;

    private String fichier = Utilitaires.nomFichierParDefautAnalyseNorme;

    private String methode;

    public AnalyseNormeController() {

    }

    @PostConstruct
    private void init() {
        norme = new Norme();
        attribution = new Attribution();
        categorievehicule = new Categorievehicule();
        typestructure = new Typestructure();
        structure = new Structure();
    }

    public void listeTypeStructure() {
        if (typestructure.getIdTypestructure() != null) {
            typestructure = typestructureFacade.find(typestructure.getIdTypestructure());
            normes = (List<Norme>) typestructure.getNormeCollection();
            structures = StructureFacadeLocal.getByTypeStructure(typestructure.getIdTypestructure());
            if (!structures.isEmpty()) {
                imprimer = false;
                if (!normes.isEmpty()) {
                    imprimer = false;
                    showPrinterDialog = true;
                } else {
                    imprimer = true;
                    showPrinterDialog = false;
                    FacesMessage msg = new FacesMessage("La liste des categories de vehicule pour ces types de structures est vide");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                imprimer = true;
                FacesMessage msg = new FacesMessage("La liste des structures est vide");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            imprimer = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("veuillez selectionne un vehicule"));
        }
    }

    public void imprimerNorme() {
        showPrinterDialog = false;
        if (typestructure.getIdTypestructure() != null) {
            //ici on imprime par type de structure
            if (categorievehicule.getIdCategorievehicule() != null) {
                System.err.println("on imprime perso avec categorie");
                //ici on imprime par typ de structure avec categorie de véhicule
                System.err.println("cate " + categorievehicule.getIdCategorievehicule());
                System.err.println("type stru " + typestructure.getIdTypestructure());
                Norme test = normeFacadeLocal.get(typestructure.getIdTypestructure(), categorievehicule.getIdCategorievehicule());

                if (test != null) {
                    structures = StructureFacadeLocal.getByTypeStructure(typestructure.getIdTypestructure());

                    if (!structures.isEmpty()) {

                        Document rapport = new Document();
                        try {
                            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautAnalyseNorme + "/" + Utilitaires.nomFichierParDefautAnalyseNorme));
                            rapport.open();

                            Printer.enteteA4(rapport);

                            Paragraph paragraph = new Paragraph("FICHE D'ANALYSE DES NORMES", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
                            paragraph.setAlignment(Element.ALIGN_CENTER);
                            rapport.add(paragraph);

                            rapport.add(new Paragraph(" ", normalFont));

                            rapport.add(new Paragraph("TYPE DE STRUCTURE : " + test.getIdTypestructure().getNomFr().toUpperCase(), Printer.normalFont));
                            rapport.add(new Paragraph("CATEGORIE DE VEHICULE : " + test.getIdCategorievehicule().getNom().toUpperCase(), Printer.normalFont));
                            rapport.add(new Paragraph("NOMBRE MINIMUM DE VEHICULES : " + test.getMinimum(), Printer.normalFont));
                            rapport.add(new Paragraph("NOMBRE MAXIMUM DE VEHICULES : " + test.getMaximum(), Printer.normalFont));

                            for (Structure s : structures) {
                                rapport.add(new Paragraph(".................................................................................................................................................................................................................................", Printer.smallBlueFont));

                                rapport.add(new Paragraph("******** [ " + s.getNom().toUpperCase() + " ] ", Printer.catFont));
                                rapport.add(new Paragraph("    ", Printer.normalFont));

                                PdfPTable norme1 = new PdfPTable(3);

                                norme1.addCell(Printer.createPdfPCell("REGION", true, Printer.smallNormalFont));
                                norme1.addCell(Printer.createPdfPCell("EFFECTIF", true, Printer.smallNormalFont));
                                norme1.addCell(Printer.createPdfPCell("OBSERVATION ", true, Printer.smallNormalFont));

                                attributions = attributionFacadeLocal.get(s.getIdStructure(), categorievehicule.getIdCategorievehicule(), "Fonctionnel");
                                if (!attributions.isEmpty()) {
                                    if (attributions.size() < test.getMinimum()) {
                                        norme1.addCell(Printer.createPdfPCell("" + s.getIdRegion().getNom().toUpperCase(), true, Printer.smallNormalFont));
                                        norme1.addCell(Printer.createPdfPCell("" + attributions.size(), true, Printer.catFont));
                                        norme1.addCell(Printer.createPdfPCell("Sous - éffectif (-" + (test.getMinimum() - attributions.size()) + " " + test.getIdCategorievehicule().getNom() + "(s))", true, Printer.smallRedFont));
                                    } else {
                                        norme1.addCell(Printer.createPdfPCell("" + s.getIdRegion().getNom().toUpperCase(), true, Printer.smallNormalFont));
                                        norme1.addCell(Printer.createPdfPCell("" + attributions.size(), true, Printer.normalFont));
                                        norme1.addCell(Printer.createPdfPCell("Effectif normal", true, Printer.smallBlueFont));
                                    }
                                } else {
                                    norme1.addCell(Printer.createPdfPCell("" + s.getIdRegion().getNom().toUpperCase(), true, Printer.smallNormalFont));
                                    norme1.addCell(Printer.createPdfPCell("0", true, Printer.normalFont));
                                    norme1.addCell(Printer.createPdfPCell("Sous - éffectif (-" + test.getMinimum() + " " + test.getIdCategorievehicule().getNom() + "(s))", true, Printer.smallRedFont));
                                }
                                rapport.add(norme1);
                                rapport.add(new Paragraph(".................................................................................................................................................................................................................................", Printer.smallBlueFont));
                                showPrinterDialog = true;
                            }
                            rapport.close();
                            System.err.println("impression terminée");
                        } catch (DocumentException ex) {
                            ex.printStackTrace();
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    System.err.println("la norme est nulle");
                }
            } else {
                System.err.println("la cat de véhicule nul");
            }
        } else {
            System.err.println("le type de structure null");
        }
    }

    public boolean isImprimer() {
        return imprimer;
    }

    public void setImprimer(boolean imprimer) {
        this.imprimer = imprimer;
    }

    public boolean isDisableTypeStructure() {
        return disableTypeStructure;
    }

    public void setDisableTypeStructure(boolean disableTypeStructure) {
        this.disableTypeStructure = disableTypeStructure;
    }

    public boolean isDisableMode() {
        return disableMode;
    }

    public void setDisableMode(boolean disableMode) {
        this.disableMode = disableMode;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public boolean isShowPrinterDialog() {
        return showPrinterDialog;
    }

    public void setShowPrinterDialog(boolean showPrinterDialog) {
        this.showPrinterDialog = showPrinterDialog;
    }

    public Norme getNorme() {
        return norme;
    }

    public void setNorme(Norme norme) {
        this.norme = norme;
    }

    public List<Norme> getNormes() {
        return normes;
    }

    public void setNormes(List<Norme> normes) {
        this.normes = normes;
    }

    public Attribution getAttribution() {
        return attribution;
    }

    public void setAttribution(Attribution attribution) {
        this.attribution = attribution;
    }

    public List<Attribution> getAttributions() {
        return attributions;
    }

    public void setAttributions(List<Attribution> attributions) {
        this.attributions = attributions;
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

    public Categorievehicule getCategorievehicule() {
        return categorievehicule;
    }

    public void setCategorievehicule(Categorievehicule categorievehicule) {
        this.categorievehicule = categorievehicule;
    }

    public Typestructure getTypestructure() {
        return typestructure;
    }

    public void setTypestructure(Typestructure typestructure) {
        this.typestructure = typestructure;
    }

    public List<Categorievehicule> getCategorievehicules() {
        //categorievehicules = categorievehiculeFacade.findAll();
        return categorievehicules;
    }

    public void setCategorievehicules(List<Categorievehicule> categorievehicules) {
        this.categorievehicules = categorievehicules;
    }

    public List<Typestructure> getTypestructures() {
        typestructures = typestructureFacade.findAll();
        return typestructures;
    }

    public void setTypestructures(List<Typestructure> typestructures) {
        this.typestructures = typestructures;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

}
