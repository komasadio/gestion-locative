package com.gestion.locative.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.gestion.locative.domain.enumeration.TypeBien;

/**
 * A Biens.
 */
@Entity
@Table(name = "biens")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Biens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "est_meuble")
    private Boolean estMeuble;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeBien type;

    @Column(name = "surface")
    private Double surface;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adresse;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstMeuble() {
        return estMeuble;
    }

    public Biens estMeuble(Boolean estMeuble) {
        this.estMeuble = estMeuble;
        return this;
    }

    public void setEstMeuble(Boolean estMeuble) {
        this.estMeuble = estMeuble;
    }

    public String getDescription() {
        return description;
    }

    public Biens description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeBien getType() {
        return type;
    }

    public Biens type(TypeBien type) {
        this.type = type;
        return this;
    }

    public void setType(TypeBien type) {
        this.type = type;
    }

    public Double getSurface() {
        return surface;
    }

    public Biens surface(Double surface) {
        this.surface = surface;
        return this;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public Biens adresse(Adresse adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Biens)) {
            return false;
        }
        return id != null && id.equals(((Biens) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Biens{" +
            "id=" + getId() +
            ", estMeuble='" + isEstMeuble() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", surface=" + getSurface() +
            "}";
    }
}
