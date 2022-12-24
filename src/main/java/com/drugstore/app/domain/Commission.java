package com.drugstore.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Commission.
 */
@Entity
@Table(name = "commission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Commission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "decision")
    private String decision;

    @Column(name = "motif")
    private String motif;

    @ManyToMany
    @JoinTable(
        name = "rel_commission__representant_list",
        joinColumns = @JoinColumn(name = "commission_id"),
        inverseJoinColumns = @JoinColumn(name = "representant_list_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commissionLists" }, allowSetters = true)
    private Set<Representant> representantLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "etapeValidationLists", "commisionLists", "user", "documentLists", "utilisateur", "local" },
        allowSetters = true
    )
    private DossierPharmacie dossierPharmacie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Commission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Commission date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getDecision() {
        return this.decision;
    }

    public Commission decision(String decision) {
        this.setDecision(decision);
        return this;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getMotif() {
        return this.motif;
    }

    public Commission motif(String motif) {
        this.setMotif(motif);
        return this;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Set<Representant> getRepresentantLists() {
        return this.representantLists;
    }

    public void setRepresentantLists(Set<Representant> representants) {
        this.representantLists = representants;
    }

    public Commission representantLists(Set<Representant> representants) {
        this.setRepresentantLists(representants);
        return this;
    }

    public Commission addRepresentantList(Representant representant) {
        this.representantLists.add(representant);
        representant.getCommissionLists().add(this);
        return this;
    }

    public Commission removeRepresentantList(Representant representant) {
        this.representantLists.remove(representant);
        representant.getCommissionLists().remove(this);
        return this;
    }

    public DossierPharmacie getDossierPharmacie() {
        return this.dossierPharmacie;
    }

    public void setDossierPharmacie(DossierPharmacie dossierPharmacie) {
        this.dossierPharmacie = dossierPharmacie;
    }

    public Commission dossierPharmacie(DossierPharmacie dossierPharmacie) {
        this.setDossierPharmacie(dossierPharmacie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commission)) {
            return false;
        }
        return id != null && id.equals(((Commission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commission{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", decision='" + getDecision() + "'" +
            ", motif='" + getMotif() + "'" +
            "}";
    }
}
