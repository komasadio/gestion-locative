package com.gestion.locative.service.dto;

import java.time.Instant;
import java.io.Serializable;
import com.gestion.locative.domain.enumeration.Periode;

/**
 * A DTO for the {@link com.gestion.locative.domain.Contrat} entity.
 */
public class ContratDTO implements Serializable {
    
    private Long id;

    private Instant dateDebut;

    private Double montantLoyerNu;

    private Double montantCharges;

    private Periode periodiciteLoyer;

    private Double montantDepotGarantie;

    private String infosComplementaires;


    private Long bienId;

    private Long locataireId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Double getMontantLoyerNu() {
        return montantLoyerNu;
    }

    public void setMontantLoyerNu(Double montantLoyerNu) {
        this.montantLoyerNu = montantLoyerNu;
    }

    public Double getMontantCharges() {
        return montantCharges;
    }

    public void setMontantCharges(Double montantCharges) {
        this.montantCharges = montantCharges;
    }

    public Periode getPeriodiciteLoyer() {
        return periodiciteLoyer;
    }

    public void setPeriodiciteLoyer(Periode periodiciteLoyer) {
        this.periodiciteLoyer = periodiciteLoyer;
    }

    public Double getMontantDepotGarantie() {
        return montantDepotGarantie;
    }

    public void setMontantDepotGarantie(Double montantDepotGarantie) {
        this.montantDepotGarantie = montantDepotGarantie;
    }

    public String getInfosComplementaires() {
        return infosComplementaires;
    }

    public void setInfosComplementaires(String infosComplementaires) {
        this.infosComplementaires = infosComplementaires;
    }

    public Long getBienId() {
        return bienId;
    }

    public void setBienId(Long biensId) {
        this.bienId = biensId;
    }

    public Long getLocataireId() {
        return locataireId;
    }

    public void setLocataireId(Long locataireId) {
        this.locataireId = locataireId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContratDTO)) {
            return false;
        }

        return id != null && id.equals(((ContratDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContratDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", montantLoyerNu=" + getMontantLoyerNu() +
            ", montantCharges=" + getMontantCharges() +
            ", periodiciteLoyer='" + getPeriodiciteLoyer() + "'" +
            ", montantDepotGarantie=" + getMontantDepotGarantie() +
            ", infosComplementaires='" + getInfosComplementaires() + "'" +
            ", bienId=" + getBienId() +
            ", locataireId=" + getLocataireId() +
            "}";
    }
}
