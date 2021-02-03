/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author gervais
 */
public class Utilitaires {
    private static final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public static final String path = servletContext.getRealPath("");
    public static final String repertoireDefautVehicule = "Rapports/ListeVehicule";    
    public static final String nomFichierParDefautListeVehicule = "Liste_des_vehicules.pdf";
    
    public static  final String repertoireDefautRestitution = "Rapports/Restitution";
    public static final String nomFichierParDefautRestitution = "Liste_des_restitutions.pdf";
    
    public static  final String repertoireDefautAttribution = "Rapports/Attribution";
    public static final String nomFichierParDefautAttribution = "Liste_des_attribution.pdf"; 
   
    public static  final String repertoireDefautHistoriqueReforme = "Rapports/HistoriqueReforme";    
    public static final String nomFichierParDefautHistoriqueReforme = "Historique_reforme.pdf";
    
    public static  final String repertoireDefautFicheDetention = "Rapports/FicheDetention";    
    public static final String nomFichierParDefautFicheDetention = "FicheDetention.pdf";
    
    public static  final String repertoireDefautCycledevie = "Rapports/Cycledevie";    
    public static final String nomFichierParDefautCycledevie = "Cycledevie.pdf";
    
    public static  final String repertoireDefautAnalyseNorme = "Rapports/AnalyseNorme";    
    public static final String nomFichierParDefautAnalyseNorme = "AnalyseNorme.pdf";
    
    public static final String userAvatar = "avatar1.png"; 
    public static final String carAvatar = "carAvatar.jpeg";
   
    
    public static String formaterStringMoney(Long valeur) {
        String chaine = Long.toString(valeur);
        if (chaine == null) {
            return null;
        }
        int taille = chaine.length(), j = taille;
        String result = "";
        int i = 0;
        while (i < taille) {
            result += chaine.charAt(i);
            i++;
            j--;
            if (j > 0 && j % 3 == 0) {
                result += ' ';
            }
        }

        return result;
    }

    public static String formaterStringMoney(Integer valeur) {
        String chaine = Integer.toString(valeur);
        if (chaine == null) {
            return null;
        }
        int taille = chaine.length(), j = taille;
        String result = "";
        int i = 0;
        while (i < taille) {
            result += chaine.charAt(i);
            i++;
            j--;
            if (j > 0 && j % 3 == 0) {
                result += ' ';
            }
        }

        return result;
    }

    public static String formaterStringMoney(String valeur) {
        String chaine = valeur;
        if (chaine == null) {
            return null;
        }
        int taille = chaine.length(), j = taille;
        String result = "";
        int i = 0;
        while (i < taille) {
            result += chaine.charAt(i);
            i++;
            j--;
            if (j > 0 && j % 3 == 0) {
                result += ' ';
            }
        }

        return result;
    }

    public static String formaterStringMoney(Double val) {
        String pEntiere = partieEntiere(val);
        String pDec = partieDecimale(val);
        String chaine = pEntiere;
        int taille = chaine.length(), j = taille;
        String result = "";
        int i = 0;
        while (i < taille) {
            result += chaine.charAt(i);
            i++;
            j--;
            if (j > 0 && j % 3 == 0) {
                result += ' ';
            }
        }
        if (pDec != null) {
            result = result + "." + pDec;
        }
        return result;
    }
    
    
    
    private static String partieDecimale(Double nombre) {
        return partieDecimale(nombre.toString());
    }

    private static String partieDecimale(String nombre) {
        String result = "";
        int taille = nombre.length();
        boolean copie = false;
        for (int i = 0; i < taille; i++) {
            if (copie) {
                result += nombre.charAt(i);
            } else if (nombre.charAt(i) == '.') {
                copie = true;
            }
        }
        if (result.equals("0")) {
            return null;
        }
        return result;
    }

    private static String partieEntiere(Double nombre) {
        Integer tmp = nombre.intValue();
        return tmp.toString();
    }


}
