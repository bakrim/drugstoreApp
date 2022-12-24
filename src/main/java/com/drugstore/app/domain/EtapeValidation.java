package com.drugstore.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EtapeValidation.
 */
@Entity
@Table(name = "etape_validation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EtapeValidation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "decision")
    private String decision;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "etapeValidationLists", "commisionLists", "user", "documentLists", "utilisateur", "local" },
        allowSetters = true
    )
    private DossierPharmacie dossierPharmacie;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etapeValidationLists", "user", "documentLists", "utilisateur", "local" }, allowSetters = true)
    private DossierAutre dossierAutre;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EtapeValidation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public EtapeValidation libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public EtapeValidation description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public EtapeValidation date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getDecision() {
        return this.decision;
    }

    public EtapeValidation decision(String decision) {
        this.setDecision(decision);
        return this;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public DossierPharmacie getDossierPharmacie() {
        return this.dossierPharmacie;
    }

    public void setDossierPharmacie(DossierPharmacie dossierPharmacie) {
        this.dossierPharmacie = dossierPharmacie;
    }

    public EtapeValidation dossierPharmacie(DossierPharmacie dossierPharmacie) {
        this.setDossierPharmacie(dossierPharmacie);
        return this;
    }

    public DossierAutre getDossierAutre() {
        return this.dossierAutre;
    }

    public void setDossierAutre(DossierAutre dossierAutre) {
        this.dossierAutre = dossierAutre;
    }

    public EtapeValidation dossierAutre(DossierAutre dossierAutre) {
        this.setDossierAutre(dossierAutre);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtapeValidation)) {
            return false;
        }
        return id != null && id.equals(((EtapeValidation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtapeValidation{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", decision='" + getDecision() + "'" +
            "}";
    }
}
