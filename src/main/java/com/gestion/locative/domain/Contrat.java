package com.gestion.locative.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.gestion.locative.domain.enumeration.Periode;

/**
 * A Contrat.
 */
@Entity
@Table(name = "contrat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contrat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_debut")
    private Instant dateDebut;

    @Column(name = "montant_loyer_nu")
    private Double montantLoyerNu;

    @Column(name = "montant_charges")
    private Double montantCharges;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicite_loyer")
    private Periode periodiciteLoyer;

    @Column(name = "montant_depot_garantie")
    private Double montantDepotGarantie;

    @Column(name = "infos_complementaires")
    private String infosComplementaires;

    @OneToOne
    @JoinColumn(unique = true)
    private Biens bien;

    @OneToOne
    @JoinColumn(unique = true)
    private Locataire locataire;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public Contrat dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Double getMontantLoyerNu() {
        return montantLoyerNu;
    }

    public Contrat montantLoyerNu(Double montantLoyerNu) {
        this.montantLoyerNu = montantLoyerNu;
        return this;
    }

    public void setMontantLoyerNu(Double montantLoyerNu) {
        this.montantLoyerNu = montantLoyerNu;
    }

    public Double getMontantCharges() {
        return montantCharges;
    }

    public Contrat montantCharges(Double montantCharges) {
        this.montantCharges = montantCharges;
        return this;
    }

    public void setMontantCharges(Double montantCharges) {
        this.montantCharges = montantCharges;
    }

    public Periode getPeriodiciteLoyer() {
        return periodiciteLoyer;
    }

    public Contrat periodiciteLoyer(Periode periodiciteLoyer) {
        this.periodiciteLoyer = periodiciteLoyer;
        return this;
    }

    public void setPeriodiciteLoyer(Periode periodiciteLoyer) {
        this.periodiciteLoyer = periodiciteLoyer;
    }

    public Double getMontantDepotGarantie() {
        return montantDepotGarantie;
    }

    public Contrat montantDepotGarantie(Double montantDepotGarantie) {
        this.montantDepotGarantie = montantDepotGarantie;
        return this;
    }

    public void setMontantDepotGarantie(Double montantDepotGarantie) {
        this.montantDepotGarantie = montantDepotGarantie;
    }

    public String getInfosComplementaires() {
        return infosComplementaires;
    }

    public Contrat infosComplementaires(String infosComplementaires) {
        this.infosComplementaires = infosComplementaires;
        return this;
    }

    public void setInfosComplementaires(String infosComplementaires) {
        this.infosComplementaires = infosComplementaires;
    }

    public Biens getBien() {
        return bien;
    }

    public Contrat bien(Biens biens) {
        this.bien = biens;
        return this;
    }

    public void setBien(Biens biens) {
        this.bien = biens;
    }

    public Locataire getLocataire() {
        return locataire;
    }

    public Contrat locataire(Locataire locataire) {
        this.locataire = locataire;
        return this;
    }

    public void setLocataire(Locataire locataire) {
        this.locataire = locataire;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contrat)) {
            return false;
        }
        return id != null && id.equals(((Contrat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contrat{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", montantLoyerNu=" + getMontantLoyerNu() +
            ", montantCharges=" + getMontantCharges() +
            ", periodiciteLoyer='" + getPeriodiciteLoyer() + "'" +
            ", montantDepotGarantie=" + getMontantDepotGarantie() +
            ", infosComplementaires='" + getInfosComplementaires() + "'" +
            "}";
    }
}
