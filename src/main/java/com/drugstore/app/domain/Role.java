package com.drugstore.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @ManyToMany(mappedBy = "roleLists")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossierPharmacieLists", "dossierAutreLists", "sessionLists", "roleLists" }, allowSetters = true)
    private Set<Utilisateur> utilisateurLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Role id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Role libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Utilisateur> getUtilisateurLists() {
        return this.utilisateurLists;
    }

    public void setUtilisateurLists(Set<Utilisateur> utilisateurs) {
        if (this.utilisateurLists != null) {
            this.utilisateurLists.forEach(i -> i.removeRoleList(this));
        }
        if (utilisateurs != null) {
            utilisateurs.forEach(i -> i.addRoleList(this));
        }
        this.utilisateurLists = utilisateurs;
    }

    public Role utilisateurLists(Set<Utilisateur> utilisateurs) {
        this.setUtilisateurLists(utilisateurs);
        return this;
    }

    public Role addUtilisateurList(Utilisateur utilisateur) {
        this.utilisateurLists.add(utilisateur);
        utilisateur.getRoleLists().add(this);
        return this;
    }

    public Role removeUtilisateurList(Utilisateur utilisateur) {
        this.utilisateurLists.remove(utilisateur);
        utilisateur.getRoleLists().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
