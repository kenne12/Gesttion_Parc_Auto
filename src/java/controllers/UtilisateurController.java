/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.util.SessionMBean;
import entities.Utilisateur;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sessions.UtilisateurFacadeLocal;

@ManagedBean
@SessionScoped
public class UtilisateurController implements Serializable {

    @EJB
    private UtilisateurFacadeLocal utilisateurFacade;
    private Utilisateur utilisateur = new Utilisateur();

    private String msg;

    public UtilisateurController() {
    }

    //validate login
    public String validateUsernamePassword() {
        boolean valid = utilisateurFacade.findByLoginMdp(utilisateur.getLogin(), utilisateur.getMdp());
        System.out.println("Username :" + utilisateur.getLogin() + "   Password :" + utilisateur.getMdp());
        System.out.println("The checking value comming from your database is " + valid);
        if (valid) {
            HttpSession session = SessionMBean.getSession();
            session.setAttribute("login", utilisateur.getLogin());
            return "index";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Login et mot de passe incorrets",
                            "Please enter correct username and Password"));
            return "login";
        }
    }

    public void checkConnection() {
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("login")) {
                String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/faces/index.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forward() {
        try {
            if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("login")) {
                String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/faces/login.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionMBean.getSession();
        session.invalidate();
        String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/faces/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(UtilisateurController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/login.xhtml";
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
