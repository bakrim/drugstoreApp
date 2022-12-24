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
 * A DossierPharmacie.
 */
@Entity
@Table(name = "dossier_pharmacie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DossierPharmacie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "date_depot")
    private ZonedDateTime dateDepot;

    @Column(name = "nature")
    private String nature;

    @Column(name = "statut")
    private String statut;

    @Column(name = "date_derniere_modif")
    private ZonedDateTime dateDerniereModif;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "c_in")
    private String cIN;

    @OneToMany(mappedBy = "dossierPharmacie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossierPharmacie", "dossierAutre" }, allowSetters = true)
    private Set<EtapeValidation> etapeValidationLists = new HashSet<>();

    @OneToMany(mappedBy = "dossierPharmacie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "representantLists", "dossierPharmacie" }, allowSetters = true)
    private Set<Commission> commisionLists = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
        name = "rel_dossier_pharmacie__document_list",
        joinColumns = @JoinColumn(name = "dossier_pharmacie_id"),
        inverseJoinColumns = @JoinColumn(name = "document_list_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossierPharmacieLists", "dossierAutreLists" }, allowSetters = true)
    private Set<Document> documentLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossierPharmacieLists", "dossierAutreLists", "sessionLists", "roleLists" }, allowSetters = true)
    private Utilisateur utilisateur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossierPharmacieLists", "dossierAutreLists", "zone" }, allowSetters = true)
    private Local local;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DossierPharmacie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return this.numero;
    }

    public DossierPharmacie numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getDateDepot() {
        return this.dateDepot;
    }

    public DossierPharmacie dateDepot(ZonedDateTime dateDepot) {
        this.setDateDepot(dateDepot);
        return this;
    }

    public void setDateDepot(ZonedDateTime dateDepot) {
        this.dateDepot = dateDepot;
    }

    public String getNature() {
        return this.nature;
    }

    public DossierPharmacie nature(String nature) {
        this.setNature(nature);
        return this;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getStatut() {
        return this.statut;
    }

    public DossierPharmacie statut(String statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public ZonedDateTime getDateDerniereModif() {
        return this.dateDerniereModif;
    }

    public DossierPharmacie dateDerniereModif(ZonedDateTime dateDerniereModif) {
        this.setDateDerniereModif(dateDerniereModif);
        return this;
    }

    public void setDateDerniereModif(ZonedDateTime dateDerniereModif) {
        this.dateDerniereModif = dateDerniereModif;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public DossierPharmacie commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getNom() {
        return this.nom;
    }

    public DossierPharmacie nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public DossierPharmacie prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public DossierPharmacie telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getcIN() {
        return this.cIN;
    }

    public DossierPharmacie cIN(String cIN) {
        this.setcIN(cIN);
        return this;
    }

    public void setcIN(String cIN) {
        this.cIN = cIN;
    }

    public Set<EtapeValidation> getEtapeValidationLists() {
        return this.etapeValidationLists;
    }

    public void setEtapeValidationLists(Set<EtapeValidation> etapeValidations) {
        if (this.etapeValidationLists != null) {
            this.etapeValidationLists.forEach(i -> i.setDossierPharmacie(null));
        }
        if (etapeValidations != null) {
            etapeValidations.forEach(i -> i.setDossierPharmacie(this));
        }
        this.etapeValidationLists = etapeValidations;
    }

    public DossierPharmacie etapeValidationLists(Set<EtapeValidation> etapeValidations) {
        this.setEtapeValidationLists(etapeValidations);
        return this;
    }

    public DossierPharmacie addEtapeValidationList(EtapeValidation etapeValidation) {
        this.etapeValidationLists.add(etapeValidation);
        etapeValidation.setDossierPharmacie(this);
        return this;
    }

    public DossierPharmacie removeEtapeValidationList(EtapeValidation etapeValidation) {
        this.etapeValidationLists.remove(etapeValidation);
        etapeValidation.setDossierPharmacie(null);
        return this;
    }

    public Set<Commission> getCommisionLists() {
        return this.commisionLists;
    }

    public void setCommisionLists(Set<Commission> commissions) {
        if (this.commisionLists != null) {
            this.commisionLists.forEach(i -> i.setDossierPharmacie(null));
        }
        if (commissions != null) {
            commissions.forEach(i -> i.setDossierPharmacie(this));
        }
        this.commisionLists = commissions;
    }

    public DossierPharmacie commisionLists(Set<Commission> commissions) {
        this.setCommisionLists(commissions);
        return this;
    }

    public DossierPharmacie addCommisionList(Commission commission) {
        this.commisionLists.add(commission);
        commission.setDossierPharmacie(this);
        return this;
    }

    public DossierPharmacie removeCommisionList(Commission commission) {
        this.commisionLists.remove(commission);
        commission.setDossierPharmacie(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DossierPharmacie user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Document> getDocumentLists() {
        return this.documentLists;
    }

    public void setDocumentLists(Set<Document> documents) {
        this.documentLists = documents;
    }

    public DossierPharmacie documentLists(Set<Document> documents) {
        this.setDocumentLists(documents);
        return this;
    }

    public DossierPharmacie addDocumentList(Document document) {
        this.documentLists.add(document);
        document.getDossierPharmacieLists().add(this);
        return this;
    }

    public DossierPharmacie removeDocumentList(Document document) {
        this.documentLists.remove(document);
        document.getDossierPharmacieLists().remove(this);
        return this;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public DossierPharmacie utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    public Local getLocal() {
        return this.local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public DossierPharmacie local(Local local) {
        this.setLocal(local);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DossierPharmacie)) {
            return false;
        }
        return id != null && id.equals(((DossierPharmacie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DossierPharmacie{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", dateDepot='" + getDateDepot() + "'" +
            ", nature='" + getNature() + "'" +
            ", statut='" + getStatut() + "'" +
            ", dateDerniereModif='" + getDateDerniereModif() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", cIN='" + getcIN() + "'" +
            "}";
    }
}
