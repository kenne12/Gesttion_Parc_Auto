/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "district")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "District.findAll", query = "SELECT d FROM District d"),
    @NamedQuery(name = "District.nextId", query = "SELECT MAX(d.idDistrict) FROM District d"),
    @NamedQuery(name = "District.findByIdDistrict", query = "SELECT d FROM District d WHERE d.idDistrict = :idDistrict"),
    @NamedQuery(name = "District.findByNom", query = "SELECT d FROM District d WHERE d.nom = :nom")})
public class District implements Serializable {
    @JoinColumn(name = "id_region", referencedColumnName = "id_region")
    @ManyToOne
    private Region idRegion;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_district")
    private Integer idDistrict;
    @Size(max = 255)
    @Column(name = "nom")
    private String nom;
    @OneToMany(mappedBy = "idDistrict")
    private Collection<Structure> structureCollection;

    public District() {
        
    }

    public District(Integer idDistrict) {
        this.idDistrict = idDistrict;
    }

    public Integer getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(Integer idDistrict) {
        this.idDistrict = idDistrict;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlTransient
    public Collection<Structure> getStructureCollection() {
        return structureCollection;
    }

    public void setStructureCollection(Collection<Structure> structureCollection) {
        this.structureCollection = structureCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistrict != null ? idDistrict.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof District)) {
            return false;
        }
        District other = (District) object;
        if ((this.idDistrict == null && other.idDistrict != null) || (this.idDistrict != null && !this.idDistrict.equals(other.idDistrict))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.District[ idDistrict=" + idDistrict + " ]";
    }

    public Region getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Region idRegion) {
        this.idRegion = idRegion;
    }
    
}
