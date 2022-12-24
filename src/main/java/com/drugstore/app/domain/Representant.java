package com.drugstore.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Representant.
 */
@Entity
@Table(name = "representant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Representant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "entite")
    private String entite;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "c_in")
    private String cIN;

    @ManyToMany(mappedBy = "representantLists")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "representantLists", "dossierPharmacie" }, allowSetters = true)
    private Set<Commission> commissionLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Representant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntite() {
        return this.entite;
    }

    public Representant entite(String entite) {
        this.setEntite(entite);
        return this;
    }

    public void setEntite(String entite) {
        this.entite = entite;
    }

    public String getNom() {
        return this.nom;
    }

    public Representant nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Representant prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Representant telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getcIN() {
        return this.cIN;
    }

    public Representant cIN(String cIN) {
        this.setcIN(cIN);
        return this;
    }

    public void setcIN(String cIN) {
        this.cIN = cIN;
    }

    public Set<Commission> getCommissionLists() {
        return this.commissionLists;
    }

    public void setCommissionLists(Set<Commission> commissions) {
        if (this.commissionLists != null) {
            this.commissionLists.forEach(i -> i.removeRepresentantList(this));
        }
        if (commissions != null) {
            commissions.forEach(i -> i.addRepresentantList(this));
        }
        this.commissionLists = commissions;
    }

    public Representant commissionLists(Set<Commission> commissions) {
        this.setCommissionLists(commissions);
        return this;
    }

    public Representant addCommissionList(Commission commission) {
        this.commissionLists.add(commission);
        commission.getRepresentantLists().add(this);
        return this;
    }

    public Representant removeCommissionList(Commission commission) {
        this.commissionLists.remove(commission);
        commission.getRepresentantLists().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Representant)) {
            return false;
        }
        return id != null && id.equals(((Representant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Representant{" +
            "id=" + getId() +
            ", entite='" + getEntite() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", cIN='" + getcIN() + "'" +
            "}";
    }
}
