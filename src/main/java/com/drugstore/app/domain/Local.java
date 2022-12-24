package com.drugstore.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Local.
 */
@Entity
@Table(name = "local")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Local implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "adresse")
    private String adresse;

    @OneToMany(mappedBy = "local")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "etapeValidationLists", "commisionLists", "user", "documentLists", "utilisateur", "local" },
        allowSetters = true
    )
    private Set<DossierPharmacie> dossierPharmacieLists = new HashSet<>();

    @OneToMany(mappedBy = "local")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etapeValidationLists", "user", "documentLists", "utilisateur", "local" }, allowSetters = true)
    private Set<DossierAutre> dossierAutreLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "localLists" }, allowSetters = true)
    private Zone zone;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Local id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Local longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Local latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Local adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Set<DossierPharmacie> getDossierPharmacieLists() {
        return this.dossierPharmacieLists;
    }

    public void setDossierPharmacieLists(Set<DossierPharmacie> dossierPharmacies) {
        if (this.dossierPharmacieLists != null) {
            this.dossierPharmacieLists.forEach(i -> i.setLocal(null));
        }
        if (dossierPharmacies != null) {
            dossierPharmacies.forEach(i -> i.setLocal(this));
        }
        this.dossierPharmacieLists = dossierPharmacies;
    }

    public Local dossierPharmacieLists(Set<DossierPharmacie> dossierPharmacies) {
        this.setDossierPharmacieLists(dossierPharmacies);
        return this;
    }

    public Local addDossierPharmacieList(DossierPharmacie dossierPharmacie) {
        this.dossierPharmacieLists.add(dossierPharmacie);
        dossierPharmacie.setLocal(this);
        return this;
    }

    public Local removeDossierPharmacieList(DossierPharmacie dossierPharmacie) {
        this.dossierPharmacieLists.remove(dossierPharmacie);
        dossierPharmacie.setLocal(null);
        return this;
    }

    public Set<DossierAutre> getDossierAutreLists() {
        return this.dossierAutreLists;
    }

    public void setDossierAutreLists(Set<DossierAutre> dossierAutres) {
        if (this.dossierAutreLists != null) {
            this.dossierAutreLists.forEach(i -> i.setLocal(null));
        }
        if (dossierAutres != null) {
            dossierAutres.forEach(i -> i.setLocal(this));
        }
        this.dossierAutreLists = dossierAutres;
    }

    public Local dossierAutreLists(Set<DossierAutre> dossierAutres) {
        this.setDossierAutreLists(dossierAutres);
        return this;
    }

    public Local addDossierAutreList(DossierAutre dossierAutre) {
        this.dossierAutreLists.add(dossierAutre);
        dossierAutre.setLocal(this);
        return this;
    }

    public Local removeDossierAutreList(DossierAutre dossierAutre) {
        this.dossierAutreLists.remove(dossierAutre);
        dossierAutre.setLocal(null);
        return this;
    }

    public Zone getZone() {
        return this.zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Local zone(Zone zone) {
        this.setZone(zone);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Local)) {
            return false;
        }
        return id != null && id.equals(((Local) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Local{" +
            "id=" + getId() +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
