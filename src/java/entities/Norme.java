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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author barackhussein
 */
@Entity
@Table(name = "norme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Norme.findAll", query = "SELECT n FROM Norme n"),
    @NamedQuery(name = "Norme.nextId", query = "SELECT MAX(n.idNorme) FROM Norme n"),
    @NamedQuery(name = "Norme.findByIdNorme", query = "SELECT n FROM Norme n WHERE n.idNorme = :idNorme"),
    @NamedQuery(name = "Norme.findByMinMax", query = "SELECT n FROM Norme n WHERE n.minimum = :minimum AND n.maximum = :maximum"),
    @NamedQuery(name = "Norme.findByMinimum", query = "SELECT n FROM Norme n WHERE n.minimum = :minimum"),
    @NamedQuery(name = "Norme.findByMaximunm", query = "SELECT n FROM Norme n WHERE n.maximum = :maximum")})
public class Norme implements Serializable {
    @Column(name = "minimum")
    private Integer minimum;
    @Column(name = "maximum")
    private Integer maximum;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_norme")
    private Integer idNorme;
    @JoinColumn(name = "id_typestructure", referencedColumnName = "id_typestructure")
    @ManyToOne
    private Typestructure idTypestructure;
    @JoinColumn(name = "id_categorievehicule", referencedColumnName = "id_categorievehicule")
    @ManyToOne
    private Categorievehicule idCategorievehicule;

    public Norme() {
    }

    public Norme(Integer idNorme) {
        this.idNorme = idNorme;
    }

    public Integer getIdNorme() {
        return idNorme;
    }

    public void setIdNorme(Integer idNorme) {
        this.idNorme = idNorme;
    }


    public Typestructure getIdTypestructure() {
        return idTypestructure;
    }

    public void setIdTypestructure(Typestructure idTypestructure) {
        this.idTypestructure = idTypestructure;
    }

    public Categorievehicule getIdCategorievehicule() {
        return idCategorievehicule;
    }

    public void setIdCategorievehicule(Categorievehicule idCategorievehicule) {
        this.idCategorievehicule = idCategorievehicule;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNorme != null ? idNorme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Norme)) {
            return false;
        }
        Norme other = (Norme) object;
        if ((this.idNorme == null && other.idNorme != null) || (this.idNorme != null && !this.idNorme.equals(other.idNorme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Norme[ idNorme=" + idNorme + " ]";
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }
    
}
