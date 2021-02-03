/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author barackhussein
 */
@Entity
@Table(name = "garage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Garage.findAll", query = "SELECT g FROM Garage g"),
    @NamedQuery(name = "Garage.nextId", query = "SELECT MAX(g.idGarage) FROM Garage g"),
    @NamedQuery(name = "Garage.findByIdGarage", query = "SELECT g FROM Garage g WHERE g.idGarage = :idGarage"),
    @NamedQuery(name = "Garage.findByNom", query = "SELECT g FROM Garage g WHERE UPPER(g.nom) = UPPER(:nom)")})
public class Garage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_garage")
    private Integer idGarage;
    @Size(max = 255)
    @Column(name = "nom")
    private String nom;
    @OneToMany(mappedBy = "idGarage",cascade = CascadeType.ALL)
    private Collection<Reparation> reparationCollection;
    @JoinColumn(name = "id_adresse", referencedColumnName = "id_adresse")
    @ManyToOne
    private Adresse idAdresse;
    @OneToMany(mappedBy = "idGarage",cascade = CascadeType.ALL)
    private Collection<Sinistre> sinistreCollection;

    public Garage() {
    }

    public Garage(Integer idGarage) {
        this.idGarage = idGarage;
    }

    public Integer getIdGarage() {
        return idGarage;
    }

    public void setIdGarage(Integer idGarage) {
        this.idGarage = idGarage;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlTransient
    public Collection<Reparation> getReparationCollection() {
        return reparationCollection;
    }

    public void setReparationCollection(Collection<Reparation> reparationCollection) {
        this.reparationCollection = reparationCollection;
    }

    public Adresse getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Adresse idAdresse) {
        this.idAdresse = idAdresse;
    }

    @XmlTransient
    public Collection<Sinistre> getSinistreCollection() {
        return sinistreCollection;
    }

    public void setSinistreCollection(Collection<Sinistre> sinistreCollection) {
        this.sinistreCollection = sinistreCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGarage != null ? idGarage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Garage)) {
            return false;
        }
        Garage other = (Garage) object;
        if ((this.idGarage == null && other.idGarage != null) || (this.idGarage != null && !this.idGarage.equals(other.idGarage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Garage[ idGarage=" + idGarage + " ]";
    }
    
}
