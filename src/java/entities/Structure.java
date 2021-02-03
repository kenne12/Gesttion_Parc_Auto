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
@Table(name = "structure")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Structure.findAll", query = "SELECT s FROM Structure s"),
    @NamedQuery(name = "Structure.nextId", query = "SELECT MAX(s.idStructure) FROM Structure s"),
    @NamedQuery(name = "Structure.findByIdStructure", query = "SELECT s FROM Structure s WHERE s.idStructure = :idStructure"),
    @NamedQuery(name = "Structure.findByNom", query = "SELECT s FROM Structure s WHERE UPPER(s.nom )= UPPER(:nom)"),
    @NamedQuery(name = "Structure.findByLocalite", query = "SELECT s FROM Structure s WHERE s.localite = :localite"),
    @NamedQuery(name = "Structure.findByActive", query = "SELECT s FROM Structure s WHERE s.active = :active"),
    @NamedQuery(name = "Structure.findByGpsNord", query = "SELECT s FROM Structure s WHERE s.gpsNord = :gpsNord"),
    @NamedQuery(name = "Structure.findByGpsSud", query = "SELECT s FROM Structure s WHERE s.gpsSud = :gpsSud")})
public class Structure implements Serializable {
    @OneToMany(mappedBy = "idStructure")
    private Collection<Personnel> personnelCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_structure")
    private Integer idStructure;
    @Size(max = 255)
    @Column(name = "nom")
    private String nom;
    @Size(max = 70)
    @Column(name = "localite")
    private String localite;
    @Column(name = "active")
    private Boolean active;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "gps_nord")
    private Double gpsNord;
    @Column(name = "gps_sud")
    private Double gpsSud;
    @JoinColumn(name = "id_typestructure", referencedColumnName = "id_typestructure")
    @ManyToOne
    private Typestructure idTypestructure;
    @JoinColumn(name = "id_statutstructure", referencedColumnName = "id_statutstructure")
    @ManyToOne
    private Statutstructure idStatutstructure;
    @JoinColumn(name = "id_arrondissement", referencedColumnName = "id_arrondissement")
    @ManyToOne
    private Arrondissement idArrondissement;
    @JoinColumn(name = "id_adresse", referencedColumnName = "id_adresse")
    @ManyToOne
    private Adresse idAdresse;
    @OneToMany(mappedBy = "idStructure", cascade = CascadeType.ALL)
    private Collection<Demande> demandeCollection;
        @JoinColumn(name = "id_district", referencedColumnName = "id_district")
    @ManyToOne
    private District idDistrict;
    @JoinColumn(name = "id_region", referencedColumnName = "id_region")
    @ManyToOne
    private Region idRegion;

    public Structure() {
    }

    public Structure(Integer idStructure) {
        this.idStructure = idStructure;
    }

    public Integer getIdStructure() {
        return idStructure;
    }

    public void setIdStructure(Integer idStructure) {
        this.idStructure = idStructure;
    }
    
        public District getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(District idDistrict) {
        this.idDistrict = idDistrict;
    }

    public Region getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Region idRegion) {
        this.idRegion = idRegion;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getGpsNord() {
        return gpsNord;
    }

    public void setGpsNord(Double gpsNord) {
        this.gpsNord = gpsNord;
    }

    public Double getGpsSud() {
        return gpsSud;
    }

    public void setGpsSud(Double gpsSud) {
        this.gpsSud = gpsSud;
    }

    public Typestructure getIdTypestructure() {
        return idTypestructure;
    }

    public void setIdTypestructure(Typestructure idTypestructure) {
        this.idTypestructure = idTypestructure;
    }

    public Statutstructure getIdStatutstructure() {
        return idStatutstructure;
    }

    public void setIdStatutstructure(Statutstructure idStatutstructure) {
        this.idStatutstructure = idStatutstructure;
    }

    public Arrondissement getIdArrondissement() {
        return idArrondissement;
    }

    public void setIdArrondissement(Arrondissement idArrondissement) {
        this.idArrondissement = idArrondissement;
    }

    public Adresse getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Adresse idAdresse) {
        this.idAdresse = idAdresse;
    }

    @XmlTransient
    public Collection<Demande> getDemandeCollection() {
        return demandeCollection;
    }

    public void setDemandeCollection(Collection<Demande> demandeCollection) {
        this.demandeCollection = demandeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStructure != null ? idStructure.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Structure)) {
            return false;
        }
        Structure other = (Structure) object;
        if ((this.idStructure == null && other.idStructure != null) || (this.idStructure != null && !this.idStructure.equals(other.idStructure))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Structure[ idStructure=" + idStructure + " ]";
    }

    @XmlTransient
    public Collection<Personnel> getPersonnelCollection() {
        return personnelCollection;
    }

    public void setPersonnelCollection(Collection<Personnel> personnelCollection) {
        this.personnelCollection = personnelCollection;
    }

}
