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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author barackhussein
 */
@Entity
@Table(name = "roleutilisateur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roleutilisateur.findAll", query = "SELECT r FROM Roleutilisateur r"),
    @NamedQuery(name = "Roleutilisateur.findByIdroleutilisateur", query = "SELECT r FROM Roleutilisateur r WHERE r.idroleutilisateur = :idroleutilisateur"),
    @NamedQuery(name = "Roleutilisateur.findByRole", query = "SELECT r FROM Roleutilisateur r WHERE r.role = :role"),
    @NamedQuery(name = "Roleutilisateur.findByEtat", query = "SELECT r FROM Roleutilisateur r WHERE r.etat = :etat")})
public class Roleutilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idroleutilisateur")
    private Integer idroleutilisateur;
    @Size(max = 254)
    @Column(name = "role")
    private String role;
    @Size(max = 254)
    @Column(name = "etat")
    private String etat;
    @JoinColumn(name = "idutilisateur", referencedColumnName = "idutilisateur")
    @ManyToOne
    private Utilisateur idutilisateur;

    public Roleutilisateur() {
    }

    public Roleutilisateur(Integer idroleutilisateur) {
        this.idroleutilisateur = idroleutilisateur;
    }

    public Integer getIdroleutilisateur() {
        return idroleutilisateur;
    }

    public void setIdroleutilisateur(Integer idroleutilisateur) {
        this.idroleutilisateur = idroleutilisateur;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Utilisateur getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Utilisateur idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idroleutilisateur != null ? idroleutilisateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roleutilisateur)) {
            return false;
        }
        Roleutilisateur other = (Roleutilisateur) object;
        if ((this.idroleutilisateur == null && other.idroleutilisateur != null) || (this.idroleutilisateur != null && !this.idroleutilisateur.equals(other.idroleutilisateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Roleutilisateur[ idroleutilisateur=" + idroleutilisateur + " ]";
    }
    
}
