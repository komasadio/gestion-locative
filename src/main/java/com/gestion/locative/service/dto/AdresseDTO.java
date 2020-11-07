package com.gestion.locative.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.gestion.locative.domain.Adresse} entity.
 */
public class AdresseDTO implements Serializable {
    
    private Long id;

    private String libelle;

    private String ville;

    private String commune;

    private String departement;


    private Long paysId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Long getPaysId() {
        return paysId;
    }

    public void setPaysId(Long paysId) {
        this.paysId = paysId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdresseDTO)) {
            return false;
        }

        return id != null && id.equals(((AdresseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdresseDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", ville='" + getVille() + "'" +
            ", commune='" + getCommune() + "'" +
            ", departement='" + getDepartement() + "'" +
            ", paysId=" + getPaysId() +
            "}";
    }
}
