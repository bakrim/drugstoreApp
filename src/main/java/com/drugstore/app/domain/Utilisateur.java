package com.drugstore.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "etapeValidationLists", "commisionLists", "user", "documentLists", "utilisateur", "local" },
        allowSetters = true
    )
    private Set<DossierPharmacie> dossierPharmacieLists = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etapeValidationLists", "user", "documentLists", "utilisateur", "local" }, allowSetters = true)
    private Set<DossierAutre> dossierAutreLists = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "historiqueLists", "user", "utilisateur" }, allowSetters = true)
    private Set<Session> sessionLists = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_utilisateur__role_list",
        joinColumns = @JoinColumn(name = "utilisateur_id"),
        inverseJoinColumns = @JoinColumn(name = "role_list_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateurLists" }, allowSetters = true)
    private Set<Role> roleLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Utilisateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public Utilisateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public Utilisateur username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Utilisateur password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Utilisateur active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNom() {
        return this.nom;
    }

    public Utilisateur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Utilisateur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Utilisateur telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<DossierPharmacie> getDossierPharmacieLists() {
        return this.dossierPharmacieLists;
    }

    public void setDossierPharmacieLists(Set<DossierPharmacie> dossierPharmacies) {
        if (this.dossierPharmacieLists != null) {
            this.dossierPharmacieLists.forEach(i -> i.setUtilisateur(null));
        }
        if (dossierPharmacies != null) {
            dossierPharmacies.forEach(i -> i.setUtilisateur(this));
        }
        this.dossierPharmacieLists = dossierPharmacies;
    }

    public Utilisateur dossierPharmacieLists(Set<DossierPharmacie> dossierPharmacies) {
        this.setDossierPharmacieLists(dossierPharmacies);
        return this;
    }

    public Utilisateur addDossierPharmacieList(DossierPharmacie dossierPharmacie) {
        this.dossierPharmacieLists.add(dossierPharmacie);
        dossierPharmacie.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeDossierPharmacieList(DossierPharmacie dossierPharmacie) {
        this.dossierPharmacieLists.remove(dossierPharmacie);
        dossierPharmacie.setUtilisateur(null);
        return this;
    }

    public Set<DossierAutre> getDossierAutreLists() {
        return this.dossierAutreLists;
    }

    public void setDossierAutreLists(Set<DossierAutre> dossierAutres) {
        if (this.dossierAutreLists != null) {
            this.dossierAutreLists.forEach(i -> i.setUtilisateur(null));
        }
        if (dossierAutres != null) {
            dossierAutres.forEach(i -> i.setUtilisateur(this));
        }
        this.dossierAutreLists = dossierAutres;
    }

    public Utilisateur dossierAutreLists(Set<DossierAutre> dossierAutres) {
        this.setDossierAutreLists(dossierAutres);
        return this;
    }

    public Utilisateur addDossierAutreList(DossierAutre dossierAutre) {
        this.dossierAutreLists.add(dossierAutre);
        dossierAutre.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeDossierAutreList(DossierAutre dossierAutre) {
        this.dossierAutreLists.remove(dossierAutre);
        dossierAutre.setUtilisateur(null);
        return this;
    }

    public Set<Session> getSessionLists() {
        return this.sessionLists;
    }

    public void setSessionLists(Set<Session> sessions) {
        if (this.sessionLists != null) {
            this.sessionLists.forEach(i -> i.setUtilisateur(null));
        }
        if (sessions != null) {
            sessions.forEach(i -> i.setUtilisateur(this));
        }
        this.sessionLists = sessions;
    }

    public Utilisateur sessionLists(Set<Session> sessions) {
        this.setSessionLists(sessions);
        return this;
    }

    public Utilisateur addSessionList(Session session) {
        this.sessionLists.add(session);
        session.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeSessionList(Session session) {
        this.sessionLists.remove(session);
        session.setUtilisateur(null);
        return this;
    }

    public Set<Role> getRoleLists() {
        return this.roleLists;
    }

    public void setRoleLists(Set<Role> roles) {
        this.roleLists = roles;
    }

    public Utilisateur roleLists(Set<Role> roles) {
        this.setRoleLists(roles);
        return this;
    }

    public Utilisateur addRoleList(Role role) {
        this.roleLists.add(role);
        role.getUtilisateurLists().add(this);
        return this;
    }

    public Utilisateur removeRoleList(Role role) {
        this.roleLists.remove(role);
        role.getUtilisateurLists().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return id != null && id.equals(((Utilisateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", active='" + getActive() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            "}";
    }
}
