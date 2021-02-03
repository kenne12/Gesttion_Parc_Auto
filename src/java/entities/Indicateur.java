/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PAT TH
 */
@Entity
@Table(name = "indicateur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indicateur.findAll", query = "SELECT i FROM Indicateur i"),
    @NamedQuery(name = "Indicateur.findByIdindicateur", query = "SELECT i FROM Indicateur i WHERE i.idindicateur = :idindicateur"),
    @NamedQuery(name = "Indicateur.findByNom", query = "SELECT i FROM Indicateur i WHERE i.nom = :nom")})
public class Indicateur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idindicateur")
    private Integer idindicateur;
    @Size(max = 233)
    @Column(name = "nom")
    private String nom;

    public Indicateur() {
    }

    public Indicateur(Integer idindicateur) {
        this.idindicateur = idindicateur;
    }

    public Integer getIdindicateur() {
        return idindicateur;
    }

    public void setIdindicateur(Integer idindicateur) {
        this.idindicateur = idindicateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idindicateur != null ? idindicateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Indicateur)) {
            return false;
        }
        Indicateur other = (Indicateur) object;
        if ((this.idindicateur == null && other.idindicateur != null) || (this.idindicateur != null && !this.idindicateur.equals(other.idindicateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Indicateur[ idindicateur=" + idindicateur + " ]";
    }
    
}
