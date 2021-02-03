package controllers;

import classes.GeneratedReport;
import controllers.util.JsfUtil;
import entities.Pays;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.ColumnResizeEvent;
import sessions.PaysFacadeLocal;

/**
 *
 * @author barackhussein
 */
@ManagedBean
@SessionScoped
public class PaysController implements Serializable {

    @EJB
    private PaysFacadeLocal paysFacade;
    private List<Pays> listPays = null;
    private Pays pays = new Pays();
    private String msg = "";
    private boolean bouton=true;

    public PaysController() {
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
        FacesMessage msg1 = new FacesMessage("Column " + event.getColumn().getClientId() + " resized", "W:" + event.getWidth() + ", H:" + event.getHeight());
        FacesContext.getCurrentInstance().addMessage(null, msg1);
    }

    public void affichagePays() {
        listPays.clear();
        listPays.addAll(paysFacade.findAll());
    }

    public Pays prepareCreate() {
        pays = new Pays();
        return pays;
    }

    public void prepareEdit() {
        
    }

    public void prepareDelete() {
        if (pays!=null) {
            msg="Voulez-vous vraiment supprimer le pays selectionné ?";
        }
    }

    public void savePays() {
        try {
            listPays = paysFacade.findByNom(pays.getNom());
            if (listPays.isEmpty()) {
                pays.setIdPays(paysFacade.nextId());
                paysFacade.create(pays);
                msg = "Enregistrement effectué avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + pays.getNom() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération!";
        } finally {
            affichagePays();
            activeBouton();
        }

    }

    public void editPays() {
        try {
            listPays = paysFacade.findByNom(pays.getNom());
            if (listPays.isEmpty()) {
                paysFacade.edit(pays);
                msg = "Modification effectuée avec succès";
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = "" + pays.getNom() + " existe déjà";
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            msg = "Echec de l'opération";
        } finally {
            affichagePays();
        }

    }

    public void deletePays() {
        try {
            paysFacade.remove(pays);
            msg = "Suppression effectuée avec succès";
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = "Echec de l'opération!";

        }
        affichagePays();
        desactiveBouton();
    }

    public List<Pays> getListPays() {
        if (listPays == null) {
            listPays = paysFacade.findAll();
        }
        return listPays;
    }

    public void setListPays(List<Pays> listPays) {
        this.listPays = listPays;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
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
    
     public void generatePDF(ActionEvent actionEvent) {
        try {
            //GeneratedReport.PDF(paysFacade.findAll(), "Pays", "ListDesPays");
            GeneratedReport.PDFGenerator("VehiculesAttribues", "ListDesVehicules");
        } catch (JRException | IOException ex) {
            Logger.getLogger(PaysController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         public void generateDOCX(ActionEvent actionEvent) {
        try {
            GeneratedReport.DOCX(paysFacade.findAll(), "PaysPDF", "ListDesPays");
        } catch (JRException | IOException ex) {
            Logger.getLogger(PaysController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
      public void generateXLSX(ActionEvent actionEvent) {
        try {
            GeneratedReport.XLSX(paysFacade.findAll(), "PaysPDF", "ListDesPays");
        } catch (JRException | IOException ex) {
            Logger.getLogger(PaysController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         
      public void generateODT(ActionEvent actionEvent) {
        try {
            GeneratedReport.ODT(paysFacade.findAll(), "PaysPDF", "ListDesPays");
        } catch (JRException | IOException ex) {
            Logger.getLogger(PaysController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
