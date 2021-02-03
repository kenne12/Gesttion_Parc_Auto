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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author barackhussein
 */
@Entity
@Table(name = "adresse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adresse.findAll", query = "SELECT a FROM Adresse a"),
    @NamedQuery(name = "Adresse.nextId", query = "SELECT MAX(a.idAdresse) FROM Adresse a"),
    @NamedQuery(name = "Adresse.findByIdAdresse", query = "SELECT a FROM Adresse a WHERE a.idAdresse = :idAdresse"),
    @NamedQuery(name = "Adresse.findByTel", query = "SELECT a FROM Adresse a WHERE UPPER(a.tel) = UPPER(:tel)"),
    @NamedQuery(name = "Adresse.findByEmail", query = "SELECT a FROM Adresse a WHERE UPPER(a.email) = UPPER(:email)"),
    @NamedQuery(name = "Adresse.findByBp", query = "SELECT a FROM Adresse a WHERE UPPER(a.bp) = (:bp)"),
    @NamedQuery(name = "Adresse.findByFax", query = "SELECT a FROM Adresse a WHERE UPPER(a.fax) = UPPER(:fax)"),
    @NamedQuery(name = "Adresse.findBySiteweb", query = "SELECT a FROM Adresse a WHERE UPPER(a.siteweb) = (:siteweb)")})
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_adresse")
    private Integer idAdresse;
//    @Size(max = 100)
    @Column(name = "tel")
    private String tel;
    //@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Email invalide")//if the field contains email address consider using this annotation to enforce field validation
//    @Size(max = 20)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "bp")
    private String bp;
    @Size(max = 255)
    @Column(name = "fax")
    private String fax;
    //@Pattern(regexp = "www\\.(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Site web invalide")
    @Size(max = 255)
    @Column(name = "siteweb")
    private String siteweb;
    @OneToMany(mappedBy = "idAdresse")
    private Collection<Garage> garageCollection;
    @OneToMany(mappedBy = "idAdresse")
    private Collection<Personnel> personnelCollection;
    @OneToMany(mappedBy = "idAdresse")
    private Collection<Structure> structureCollection;

    public Adresse() {
    }

    public Adresse(Integer idAdresse) {
        this.idAdresse = idAdresse;
    }

    public Integer getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Integer idAdresse) {
        this.idAdresse = idAdresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    @XmlTransient
    public Collection<Garage> getGarageCollection() {
        return garageCollection;
    }

    public void setGarageCollection(Collection<Garage> garageCollection) {
        this.garageCollection = garageCollection;
    }

    @XmlTransient
    public Collection<Personnel> getPersonnelCollection() {
        return personnelCollection;
    }

    public void setPersonnelCollection(Collection<Personnel> personnelCollection) {
        this.personnelCollection = personnelCollection;
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
        hash += (idAdresse != null ? idAdresse.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adresse)) {
            return false;
        }
        Adresse other = (Adresse) object;
        if ((this.idAdresse == null && other.idAdresse != null) || (this.idAdresse != null && !this.idAdresse.equals(other.idAdresse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Adresse[ idAdresse=" + idAdresse + " ]";
    }

}
