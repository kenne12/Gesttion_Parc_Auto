package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.html.ParaGraph;
import entities.Attribution;
import entities.Personnel;
import entities.Reforme;
import entities.Region;
import entities.Reparation;
import entities.Structure;
import entities.Vehicule;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.joda.time.DateTime;
import org.joda.time.Years;
import sessions.AttributionFacadeLocal;
import sessions.StructureFacadeLocal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gervais
 */
public class Printer {

    public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    public static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.RED);
    public static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLACK);
    public static Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLUE);
    public static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    public static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);

    public static Font smallBlueFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLUE);
    public static Font smallNormalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
    public static Font smallRedFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.RED);

    @EJB
    private static AttributionFacadeLocal attributionFacadeLocal;
    private static List<Attribution> attributions = new ArrayList<>();
    private static Attribution attribution = new Attribution();

    public static void enteteA4(Document document) {

        try {
            document.add(new Paragraph("    REPUBLIQUE DU CAMEROUN                                                                         REPUBLIC OF CAMEROUN", normalFont));
            document.add(new Paragraph("                   Paix - Travail - Patrie                                                                                                                       Peace - Work - Fatherland", smallNormalFont));

            document.add(new Paragraph("                           ----------                                                                                                                                                 ----------", smallNormalFont));

            document.add(new Paragraph("MINISTERE DE LA SANTE PUBLIQUE                                                            MINISTRY OF PUBLIC HEALTH", normalFont));

            document.add(new Paragraph("                           ----------                                                                                                                                                 ----------", smallNormalFont));
            document.add(new Paragraph("          SECRETARIAT GENERAL                                                                               GENRERAL SECRETARY", normalFont));

            document.add(new Paragraph("                           ----------                                                                                                                                                 ----------", smallNormalFont));

            document.add(new Paragraph("    DIRECTION DES RESSOURCES                                                                      DEPARTMENT OF FINANCIAL", normalFont));

            document.add(new Paragraph("   FINANCIERES ET DU PATRIMOINE                                                                            RESSOURCES AND PATRIMONY", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));

            document.add(new Paragraph("                           ----------                                                                                                                                                 ----------", smallNormalFont));

            document.add(new Paragraph("  ", normalFont));

            Image image = Image.getInstance(Utilitaires.path + "/" + "resources/images/logo1.png");
            image.setAbsolutePosition(270f, 700f);
            document.add(image);

        } catch (DocumentException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void entetePaysage(Document document) {

        try {
            document.add(new Paragraph("    REPUBLIQUE DU CAMEROUN                                                                                                                                                                   REPUBLIC OF CAMEROUN", normalFont));
            document.add(new Paragraph("                   Paix - Travail - Patrie                                                                                                                                                                                                                                   Peace - Work - Fatherland", smallNormalFont));

            document.add(new Paragraph("                           ----------                                                                                                                                                                                                                                                               ----------", smallNormalFont));

            document.add(new Paragraph("MINISTERE DE LA SANTE PUBLIQUE                                                                                                                                                      MINISTRY OF PUBLIC HEALTH", normalFont));

            document.add(new Paragraph("                           ----------                                                                                                                                                                                                                                                               ----------", smallNormalFont));
            document.add(new Paragraph("          SECRETARIAT GENERAL                                                                                                                                                                         GENRERAL SECRETARY", normalFont));

            document.add(new Paragraph("                           ----------                                                                                                                                                                                                                                                               ----------", smallNormalFont));

            document.add(new Paragraph("    DIRECTION DES RESSOURCES                                                                                                                                                               DEPARTMENT OF FINANCIAL", normalFont));

            document.add(new Paragraph("   FINANCIERES ET DU PATRIMOINE                                                                                                                                                                              RESSOURCES AND PATRIMONY", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));

            document.add(new Paragraph("                          ----------                                                                                                                                                                                                                                                               ----------", smallNormalFont));

            document.add(new Paragraph("  ", normalFont));

            Image image = Image.getInstance(Utilitaires.path + "/" + "resources/images/logo1.png");
            image.setAbsolutePosition(400f, 450f);
            document.add(image);

        } catch (DocumentException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @EJB
    private StructureFacadeLocal structureFacadeLocal;
    List<Structure> structures = new ArrayList<>();

    public static Font setUpFont(final float size, final int style, final BaseColor color) {
        Font font = new Font();
        font.setStyle(style);
        font.setSize(size);
        font.setColor(color);
        return font;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, boolean etatHori, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
        cell.setColspan(colspan);
        if (etatHori) {
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }

        cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, boolean etatHori) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell));
        cell.setColspan(colspan);
        if (etatHori) {
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }

        cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, boolean etatHori, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
        if (etatHori) {
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }

        cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, boolean etatHori) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell));
        if (etatHori) {
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        }

        cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        return cell;
    }

    public static void printVehiculeList(List<Vehicule> vehicules) {

        Document rapport = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautVehicule + "/" + Utilitaires.nomFichierParDefautListeVehicule));
            rapport.open();
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(96);
            rapport.setMargins(10, 10, 10, 10);

            entetePaysage(rapport);

            Paragraph paragraph = new Paragraph("LISTE DES VEHICULE DU PARC AUTOMOBILE", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            rapport.add(paragraph);

            rapport.add(new Paragraph(" ", normalFont));

            table.addCell(createPdfPCell("MARQUE", true, new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL)));

            table.addCell(createPdfPCell("IMMATRICULATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL)));
            table.addCell(createPdfPCell("N° CHASSIS", true, new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL)));

            table.addCell(createPdfPCell("CATEGORIE", true, new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL)));
            table.addCell(createPdfPCell("PRIX (Fcfa)", true, new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL)));
            table.addCell(createPdfPCell("DUREE DE VIE (AN)", true, new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.NORMAL)));

            for (Vehicule v : vehicules) {

                table.addCell(createPdfPCell("" + v.getIdModele().getNom() + " " + v.getIdModele().getIdMarque().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));

                table.addCell(createPdfPCell("" + v.getImmatriculation(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getNumchassis(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));

                table.addCell(createPdfPCell("" + v.getIdCategorievehicule().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                table.addCell(createPdfPCell("" + Utilitaires.formaterStringMoney(v.getCoutachatttc()), true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getDureevie(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));

            }
            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void printAttributedVehicule(List<Attribution> attributions) {
        Document rapport = new Document();
        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautAttribution + "/" + Utilitaires.nomFichierParDefautAttribution));
            rapport.open();
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(95);
            rapport.setMargins(5, 5, 5, 5);

            enteteA4(rapport);

            Paragraph paragraph = new Paragraph("LISTE DES VEHICULE DU PARC AUTOMOBILE ATTRIBUES", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            rapport.add(paragraph);

            rapport.add(new Paragraph(" ", normalFont));

            table.addCell(createPdfPCell("PERSONNEL", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("MARQUE", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("IMMATRICULATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("DATE D'ATTRIBUTION", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

            for (Attribution v : attributions) {
                table.addCell(createPdfPCell("" + v.getIdPersonnel().getNomPrenom().toUpperCase(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getIdVehicule().getIdModele().getNom() + " " + v.getIdVehicule().getIdModele().getIdMarque().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getIdVehicule().getImmatriculation(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getDateattribution().getDate() + "/" + (v.getDateattribution().getMonth() + 1) + "/" + (v.getDateattribution().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            }

            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void printAttributedVehicule(List<Attribution> attributions, Region region) {
        Document rapport = new Document();
        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautAttribution + "/" + Utilitaires.nomFichierParDefautAttribution));
            rapport.open();
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            rapport.setMargins(5, 5, 5, 5);

            enteteA4(rapport);

            Paragraph paragraph = new Paragraph("LISTE DES VEHICULE DU PARC AUTOMOBILE ATTRIBUES (" + region.getNom().toUpperCase() + ")", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            rapport.add(paragraph);

            rapport.add(new Paragraph(" ", normalFont));

            table.addCell(createPdfPCell("PERSONNEL", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("MARQUE", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("IMMATRICULATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("DATE D'ATTRIBUTION", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

            for (Attribution v : attributions) {
                table.addCell(createPdfPCell("" + v.getIdPersonnel().getNomPrenom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
                table.addCell(createPdfPCell("" + v.getIdVehicule().getIdModele().getNom() + " " + v.getIdVehicule().getIdModele().getIdMarque().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getIdVehicule().getImmatriculation(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getDateattribution().getDate() + "/" + (v.getDateattribution().getMonth() + 1) + "/" + (v.getDateattribution().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            }

            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void printRestituedVehicule(List<Attribution> restitutions) {
        Document rapport = new Document();
        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautRestitution + "/" + Utilitaires.nomFichierParDefautRestitution));
            rapport.open();
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(98);
            rapport.setMargins(5, 5, 5, 5);

            enteteA4(rapport);

            Paragraph paragraph = new Paragraph("LISTE DE VEHICULES RESTITUES DU PARC AUTOMOBILE", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            rapport.add(paragraph);

            rapport.add(new Paragraph(" ", normalFont));

            table.addCell(createPdfPCell("PERSONNEL", true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
            table.addCell(createPdfPCell("MARQUE", true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
            table.addCell(createPdfPCell("IMMATRICULATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
            table.addCell(createPdfPCell("DATE ATTRIBUTION", true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
            table.addCell(createPdfPCell("DATE RESTITUTION", true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));

            for (Attribution v : restitutions) {
                table.addCell(createPdfPCell("" + v.getIdPersonnel().getNomPrenom().toUpperCase(), true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getIdVehicule().getIdModele().getNom() + " " + v.getIdVehicule().getIdModele().getIdMarque().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getIdVehicule().getImmatriculation(), true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));

                table.addCell(createPdfPCell("" + v.getDateattribution().getDate() + "/" + (v.getDateattribution().getMonth() + 1) + "/" + (v.getDateattribution().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getDaterestitution().getDate() + "/" + (v.getDaterestitution().getMonth() + 1) + "/" + (v.getDaterestitution().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
            }
            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void printDetentionSheet(List<Attribution> restitutions, List<Region> regions) {
        Document rapport = new Document();
        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautFicheDetention + "/" + Utilitaires.nomFichierParDefautFicheDetention));
            rapport.open();
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(98);
            rapport.setMargins(5, 5, 5, 5);

            enteteA4(rapport);

            Paragraph paragraph = new Paragraph("FICHE DE DETENTION DES VEHICULES PAR REGION", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            rapport.add(paragraph);

            rapport.add(new Paragraph(" ", normalFont));

            table.addCell(createPdfPCell("PERSONNEL", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("MARQUE", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("IMMATRICULE", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("DATE D'ATTRIBUTION", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("STRUCTURE", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

            for (Region r : regions) {
                table.addCell(createPdfPCell("" + r.getNom().toUpperCase(), 5, true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLUE)));

                for (Attribution v : restitutions) {
                    if (Objects.equals(v.getIdPersonnel().getIdStructure().getIdRegion().getIdRegion(), r.getIdRegion())) {
                        table.addCell(createPdfPCell("" + v.getIdPersonnel().getNomPrenom().toUpperCase(), true, new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL)));
                        table.addCell(createPdfPCell("" + v.getIdVehicule().getIdModele().getNom() + " " + v.getIdVehicule().getIdModele().getIdMarque().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                        table.addCell(createPdfPCell("" + v.getIdVehicule().getImmatriculation(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                        table.addCell(createPdfPCell("" + v.getDateattribution().getDate() + "/" + (v.getDateattribution().getMonth() + 1) + "/" + (v.getDateattribution().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                        table.addCell(createPdfPCell("" + v.getIdPersonnel().getIdStructure().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                    }
                }
            }
            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void printReformedVehicules(List<Reforme> reformes) {
        Document rapport = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautHistoriqueReforme + "/" + Utilitaires.nomFichierParDefautHistoriqueReforme));
            rapport.open();
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(98);
            rapport.setMargins(5, 5, 5, 5);

            entetePaysage(rapport);

            Paragraph paragraph = new Paragraph("LISTE DES VEHICULES PROPOSES A LA REFORME ", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            rapport.add(paragraph);

            rapport.add(new Paragraph(" ", normalFont));

            table.addCell(createPdfPCell("VEHICULE", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

            table.addCell(createPdfPCell("DATE DE MISE EN REFORME", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("DATE DE VENTE", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("MONTANT DE VENTE", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

            table.addCell(createPdfPCell("ANNEE MISE EN CIRCULATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("STRUCTURE D'UTILISATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

            for (Reforme v : reformes) {
                table.addCell(createPdfPCell("" + v.getIdVehicule().getImmatriculation() + " (" + v.getIdVehicule().getIdModele().getNom() + " " + v.getIdVehicule().getIdModele().getIdMarque().getNom() + ")", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getDatemisereforme().getDate() + "/" + (v.getDatemisereforme().getMonth() + 1) + "/" + (v.getDatemisereforme().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + (v.getDatevente().getMonth() + 1) + "/" + (v.getDatevente().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + Utilitaires.formaterStringMoney(v.getMontantvente().toString())+" Fcfa", true, new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL)));

                attributions = (List<Attribution>) v.getIdVehicule().getAttributionCollection();
                if(attributions.size()==1){
                    attribution = attributions.get(0);
                }else{
                    attribution = attributions.get(attributions.size() - 1);
                }
                
                table.addCell(createPdfPCell("" + v.getIdVehicule().getDatepremiereaattribution().getDate() + "/" + v.getIdVehicule().getDatepremiereaattribution().getMonth() + 1 + "/" + v.getIdVehicule().getDatepremiereaattribution().getYear() + 1900, true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + attribution.getIdPersonnel().getIdStructure().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            }

            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void printCycledevie(Vehicule vehicule, List<Attribution> attributions, List<Reparation> reparations, List<Reforme> reformes) {
        Document rapport = new Document();

        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautCycledevie + "/" + Utilitaires.nomFichierParDefautCycledevie));
            rapport.open();
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            rapport.setMargins(5, 5, 5, 5);

            PdfPTable tableReparation = new PdfPTable(4);
            tableReparation.setWidthPercentage(100);
            PdfPTable tableReforme = new PdfPTable(4);

            enteteA4(rapport);

            Paragraph paragraph = new Paragraph("CYCLE DE VIE (ATTRIBUATIONS, REPARATIONS ET REFORME) DU VEHICULE : " + vehicule.getImmatriculation().toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            rapport.add(paragraph);
            rapport.add(new Paragraph("", normalFont));

            rapport.add(new Paragraph("Modèle : " + vehicule.getIdModele().getNom().toUpperCase(), normalFont));
            rapport.add(new Paragraph("Marque : " + vehicule.getIdModele().getIdMarque().getNom().toUpperCase(), normalFont));
            rapport.add(new Paragraph("Catégorie : " + vehicule.getIdCategorievehicule().getNom(), normalFont));
            rapport.add(new Paragraph(" ", normalFont));

            Paragraph para = new Paragraph("HISTORIQUE DES ATTRIBUTIONS", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            para.setAlignment(Element.ALIGN_CENTER);
            rapport.add(para);
            rapport.add(new Paragraph(" ", normalFont));

            table.addCell(createPdfPCell("Personnel", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("Date d'attribution", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("Région", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            table.addCell(createPdfPCell("Structure", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

            if (!attributions.isEmpty()) {
                for (Attribution v : attributions) {
                    table.addCell(createPdfPCell("" + v.getIdPersonnel().getNomPrenom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL)));
                    table.addCell(createPdfPCell("" + v.getDateattribution().getDate() + "/" + (v.getDateattribution().getMonth() + 1) + "/" + (v.getDateattribution().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL)));
                    table.addCell(createPdfPCell("" + v.getIdPersonnel().getIdStructure().getIdRegion().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL)));
                    table.addCell(createPdfPCell("" + v.getIdPersonnel().getIdStructure().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL)));
                }
            } else {
                table.addCell(createPdfPCell("Historique des attributions vide", 4, true, normalFont));
            }

            rapport.add(table);

            Paragraph par = new Paragraph("HISTORIQUE DES REPARATIONS", new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            par.setAlignment(Element.ALIGN_CENTER);
            rapport.add(par);
            rapport.add(new Paragraph(" ", normalFont));

            tableReparation.addCell(createPdfPCell("TYPE DE REPARATION ", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            tableReparation.addCell(createPdfPCell("GARAGE", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            tableReparation.addCell(createPdfPCell("DATE REPARATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
            tableReparation.addCell(createPdfPCell("COUT DE REPARATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

            if (!reparations.isEmpty()) {

                for (Reparation r : reparations) {
                    tableReparation.addCell(createPdfPCell("" + r.getIdTypereparation().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                    tableReparation.addCell(createPdfPCell("" + r.getIdGarage().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                    tableReparation.addCell(createPdfPCell("" + r.getDatereparation().getDate() + "/" + (r.getDatereparation().getMonth() + 1) + "/" + (r.getDatereparation().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                    tableReparation.addCell(createPdfPCell("" + Utilitaires.formaterStringMoney(r.getCoutreparation()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                }

            } else {
                tableReparation.addCell(createPdfPCell("Historique des reparations vide", 4, true, normalFont));
            }

            rapport.add(tableReparation);

            if (!reformes.isEmpty()) {
                rapport.add(new Paragraph("Date de mise en reforme : " + reformes.get(0).getDatemisereforme().getDate() + "/" + (reformes.get(0).getDatemisereforme().getMonth() + 1) + "/" + (reformes.get(0).getDatemisereforme().getYear() + 1900), normalFont));
                rapport.add(new Paragraph("Date de vente : " + reformes.get(0).getDatevente().getDate() + "/" + (reformes.get(0).getDatevente().getMonth() + 1) + "/" + (reformes.get(0).getDatevente().getYear() + 1900), normalFont));
                rapport.add(new Paragraph("Montant de vente : " + Utilitaires.formaterStringMoney(reformes.get(0).getMontantvente().toString()), normalFont));
                if (reformes.get(0).getVendu()) {
                    rapport.add(new Paragraph("Vendu : OUI", normalFont));
                } else {
                    rapport.add(new Paragraph("Vendu : NON", normalFont));
                }
            }

            rapport.add(tableReforme);
            rapport.close();
            System.err.println("imprimé avec succès");
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void printCycledevie2(List<Vehicule> vehicules) {
        Document rapport = new Document(PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautCycledevie + "/" + Utilitaires.nomFichierParDefautCycledevie));
            rapport.open();
            entetePaysage(rapport);
            rapport.setMargins(10, 10, 10, 10);
            for (Vehicule v : vehicules) {
                rapport.add(new Paragraph("                            .....................................................................................................................................................................................................................", normalFont));
                rapport.add(new Paragraph("                          CYCLE DE VIE (ATTRIBUATIONS, REPARATIONS ET REFORME) DU VEHICULE : " + v.getImmatriculation().toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                rapport.add(new Paragraph("                          Modele : " + v.getIdModele().getNom().toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                rapport.add(new Paragraph("                          Marque : " + v.getIdModele().getIdMarque().getNom().toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                rapport.add(new Paragraph(" ", normalFont));

                if (v.getDatepremiereaattribution() != null) {
                    List<Attribution> attributions = (List<Attribution>) v.getAttributionCollection();
                    if (!attributions.isEmpty()) {
                        PdfPTable table = new PdfPTable(4);

                        table.addCell(createPdfPCell("Historique des attributions", 4, true, new Font(Font.FontFamily.TIMES_ROMAN, 1, Font.BOLD)));

                        table.addCell(createPdfPCell("PERSONNEL", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                        table.addCell(createPdfPCell("DATE D'ATTRIBUTION", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                        table.addCell(createPdfPCell("REGION", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                        table.addCell(createPdfPCell("STRUCTURE", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));

                        for (Attribution a : attributions) {
                            table.addCell(createPdfPCell("" + a.getIdPersonnel().getNomPrenom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
                            table.addCell(createPdfPCell("" + a.getDateattribution().getDate() + "/" + (a.getDateattribution().getMonth() + 1) + "/" + (a.getDateattribution().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                            table.addCell(createPdfPCell("" + a.getIdPersonnel().getIdStructure().getIdRegion().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                            table.addCell(createPdfPCell("" + a.getIdPersonnel().getIdStructure().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                        }

                        rapport.add(table);

                        List<Reparation> reparations = (List<Reparation>) v.getReparationCollection();

                        if (!reparations.isEmpty()) {
                            PdfPTable tableReparation = new PdfPTable(4);
                            tableReparation.addCell(createPdfPCell("Historique des reparations", 4, true, new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD)));

                            tableReparation.addCell(createPdfPCell("TYPE DE REPARATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                            tableReparation.addCell(createPdfPCell("GARAGE", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                            tableReparation.addCell(createPdfPCell("DATE DE REPARATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
                            tableReparation.addCell(createPdfPCell("COUT DE REPARATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));

                            for (Reparation r : reparations) {
                                tableReparation.addCell(createPdfPCell("" + r.getIdTypereparation().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                                tableReparation.addCell(createPdfPCell("" + r.getIdGarage().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                                tableReparation.addCell(createPdfPCell("" + r.getDatereparation().getDate() + "/" + (r.getDatereparation().getMonth() + 1) + "/" + (r.getDatereparation().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                                tableReparation.addCell(createPdfPCell("" + r.getCoutreparation(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                            }

                            rapport.add(tableReparation);
                        }

                        List<Reforme> reformes = (List<Reforme>) v.getReformeCollection();

                        if (!reformes.isEmpty()) {
                            rapport.add(new Paragraph("                          Date de mise en reforme : " + reformes.get(0).getDatemisereforme().getDay() + " - " + reformes.get(0).getDatemisereforme().getMonth() + " - " + reformes.get(0).getDatemisereforme().getYear(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                            rapport.add(new Paragraph("                          Date de vente : " + reformes.get(0).getDatevente().getDate() + "" + (reformes.get(0).getDatevente().getMonth() + 1) + "/" + (reformes.get(0).getDatevente().getYear() + 1900), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                            rapport.add(new Paragraph("                          Montant de vente : " + reformes.get(0).getMontantvente(), normalFont));
                            if (reformes.get(0).getVendu()) {
                                rapport.add(new Paragraph("                          Vendu : OUI", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                            } else {
                                rapport.add(new Paragraph("                      Vendu : NON", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
                            }
                        }
                    }
                } else {
                    rapport.add(new Paragraph("                         Cycle de vie nul (Ce vehicule n'est pas encore mis en circulation)", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.RED)));
                }
                rapport.add(new Paragraph("                             ....................................................................................................................................................................................................................", normalFont));
                rapport.add(new Paragraph("   ", catFont));
            }

            rapport.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void printReformeProgrammee(List<Vehicule> vehicules, Date date) {
        Document rapport = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautHistoriqueReforme + "/vehiculeAreformerLe.pfd"));
            rapport.open();
            PdfPTable table = new PdfPTable(8);

            table.setWidthPercentage(100);
            rapport.setMargins(5, 5, 5, 5);

            entetePaysage(rapport);

            Paragraph paragraph = new Paragraph("LISTE DES VEHICULE A REFORMER A PARTIR DU : " + date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 1900), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            rapport.add(paragraph);

            rapport.add(new Paragraph(" ", normalFont));

            table.addCell(createPdfPCell("MARQUE", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));

            table.addCell(createPdfPCell("IMMATRICULATION", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            table.addCell(createPdfPCell("N° CHASSIS", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));

            table.addCell(createPdfPCell("PRIX (Fcfa)", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            table.addCell(createPdfPCell("DUREE DE VIE (AN)", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            table.addCell(createPdfPCell("DATE MISE EN SERVICE", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            table.addCell(createPdfPCell("DATE REFORME", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
            table.addCell(createPdfPCell("DUREE RESTANTE (AN)", true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));

            for (Vehicule v : vehicules) {

                table.addCell(createPdfPCell("" + v.getIdModele().getNom() + " " + v.getIdModele().getIdMarque().getNom(), true, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));

                table.addCell(createPdfPCell("" + v.getImmatriculation(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getNumchassis(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

                table.addCell(createPdfPCell("" + Utilitaires.formaterStringMoney(v.getCoutachatttc()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getDureevie(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell("" + v.getDatepremiereaattribution().getDate() + "/" + (v.getDatepremiereaattribution().getMonth() + 1) + "/" + (v.getDatepremiereaattribution().getYear() + 1900), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

                DateTime dat = new DateTime("" + (v.getDatepremiereaattribution().getYear() + 1900) + "-" + (v.getDatepremiereaattribution().getMonth() + 1) + "-" + v.getDatepremiereaattribution().getDate() + "");
                DateTime dat1 = dat.plusYears(v.getDureevie());

                table.addCell(createPdfPCell("" + dat1.getDayOfMonth() + "/" + (dat1.getMonthOfYear()) + "/" + (dat1.getYear()), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

                Years year = Years.yearsBetween(dat, dat1);

                table.addCell(createPdfPCell("" + year.getYears(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

            }
            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void printFicheDetenteur(Personnel personnel, List<Attribution> attributions) throws IOException {

        Document rapport = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/" + Utilitaires.repertoireDefautFicheDetention + "/FicheDetenteur.pfd"));
            rapport.open();
                        
            rapport.setMargins(5, 5, 5, 5);

            entetePaysage(rapport);

            Paragraph paragraph = new Paragraph("FICHE DE DETENTION DES VEHICULES DE M / Mme : " + personnel.getNomPrenom().toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL | Font.UNDERLINE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            rapport.add(paragraph);
            rapport.add(new  Paragraph("   ",normalFont));

            for (Attribution a : attributions) {
                
                PdfPTable table = new PdfPTable(1);
                   
                table.addCell(createPdfPCell(" IMMATRICULATION : " +a.getIdVehicule().getImmatriculation(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                table.addCell(createPdfPCell(" IMMATRICULATION : " +a.getIdVehicule().getImmatriculation(), true, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
                Image image = Image.getInstance(Utilitaires.path + "/" + "resources/images/"+a.getIdVehicule().getPhoto());
                table.addCell(image);
                
                rapport.add(table);
                rapport.add(new Paragraph("..................",catFont));
                System.err.println("imprimé avec succes");
            }
            
            rapport.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }
}
