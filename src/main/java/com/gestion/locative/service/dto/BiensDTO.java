package com.gestion.locative.service.dto;

import java.io.Serializable;
import com.gestion.locative.domain.enumeration.TypeBien;

/**
 * A DTO for the {@link com.gestion.locative.domain.Biens} entity.
 */
public class BiensDTO implements Serializable {
    
    private Long id;

    private Boolean estMeuble;

    private String description;

    private TypeBien type;

    private Double surface;


    private Long adresseId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstMeuble() {
        return estMeuble;
    }

    public void setEstMeuble(Boolean estMeuble) {
        this.estMeuble = estMeuble;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeBien getType() {
        return type;
    }

    public void setType(TypeBien type) {
        this.type = type;
    }

    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public Long getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(Long adresseId) {
        this.adresseId = adresseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BiensDTO)) {
            return false;
        }

        return id != null && id.equals(((BiensDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BiensDTO{" +
            "id=" + getId() +
            ", estMeuble='" + isEstMeuble() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", surface=" + getSurface() +
            ", adresseId=" + getAdresseId() +
            "}";
    }
}
