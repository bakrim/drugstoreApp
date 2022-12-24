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
 * A Session.
 */
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "adresse_ip")
    private String adresseIp;

    @Column(name = "date_connect")
    private ZonedDateTime dateConnect;

    @Column(name = "date_deconnect")
    private ZonedDateTime dateDeconnect;

    @OneToMany(mappedBy = "session")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "session" }, allowSetters = true)
    private Set<Historique> historiqueLists = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossierPharmacieLists", "dossierAutreLists", "sessionLists", "roleLists" }, allowSetters = true)
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Session id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresseIp() {
        return this.adresseIp;
    }

    public Session adresseIp(String adresseIp) {
        this.setAdresseIp(adresseIp);
        return this;
    }

    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }

    public ZonedDateTime getDateConnect() {
        return this.dateConnect;
    }

    public Session dateConnect(ZonedDateTime dateConnect) {
        this.setDateConnect(dateConnect);
        return this;
    }

    public void setDateConnect(ZonedDateTime dateConnect) {
        this.dateConnect = dateConnect;
    }

    public ZonedDateTime getDateDeconnect() {
        return this.dateDeconnect;
    }

    public Session dateDeconnect(ZonedDateTime dateDeconnect) {
        this.setDateDeconnect(dateDeconnect);
        return this;
    }

    public void setDateDeconnect(ZonedDateTime dateDeconnect) {
        this.dateDeconnect = dateDeconnect;
    }

    public Set<Historique> getHistoriqueLists() {
        return this.historiqueLists;
    }

    public void setHistoriqueLists(Set<Historique> historiques) {
        if (this.historiqueLists != null) {
            this.historiqueLists.forEach(i -> i.setSession(null));
        }
        if (historiques != null) {
            historiques.forEach(i -> i.setSession(this));
        }
        this.historiqueLists = historiques;
    }

    public Session historiqueLists(Set<Historique> historiques) {
        this.setHistoriqueLists(historiques);
        return this;
    }

    public Session addHistoriqueList(Historique historique) {
        this.historiqueLists.add(historique);
        historique.setSession(this);
        return this;
    }

    public Session removeHistoriqueList(Historique historique) {
        this.historiqueLists.remove(historique);
        historique.setSession(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session user(User user) {
        this.setUser(user);
        return this;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Session utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return id != null && id.equals(((Session) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", adresseIp='" + getAdresseIp() + "'" +
            ", dateConnect='" + getDateConnect() + "'" +
            ", dateDeconnect='" + getDateDeconnect() + "'" +
            "}";
    }
}
