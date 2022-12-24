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
 * A DossierAutre.
 */
@Entity
@Table(name = "dossier_autre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DossierAutre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "numero_envoi")
    private String numeroEnvoi;

    @Column(name = "date_depot")
    private ZonedDateTime dateDepot;

    @Column(name = "date_envoi")
    private ZonedDateTime dateEnvoi;

    @Column(name = "type_demendeur")
    private String typeDemendeur;

    @Column(name = "profession")
    private String profession;

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

    @Column(name = "raison_social")
    private String raisonSocial;

    @Column(name = "i_ce")
    private String iCE;

    @Column(name = "num_rc")
    private String numRC;

    @Column(name = "i_f")
    private String iF;

    @Column(name = "num_affiliation")
    private String numAffiliation;

    @OneToMany(mappedBy = "dossierAutre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossierPharmacie", "dossierAutre" }, allowSetters = true)
    private Set<EtapeValidation> etapeValidationLists = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
        name = "rel_dossier_autre__document_list",
        joinColumns = @JoinColumn(name = "dossier_autre_id"),
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

    public DossierAutre id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return this.numero;
    }

    public DossierAutre numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroEnvoi() {
        return this.numeroEnvoi;
    }

    public DossierAutre numeroEnvoi(String numeroEnvoi) {
        this.setNumeroEnvoi(numeroEnvoi);
        return this;
    }

    public void setNumeroEnvoi(String numeroEnvoi) {
        this.numeroEnvoi = numeroEnvoi;
    }

    public ZonedDateTime getDateDepot() {
        return this.dateDepot;
    }

    public DossierAutre dateDepot(ZonedDateTime dateDepot) {
        this.setDateDepot(dateDepot);
        return this;
    }

    public void setDateDepot(ZonedDateTime dateDepot) {
        this.dateDepot = dateDepot;
    }

    public ZonedDateTime getDateEnvoi() {
        return this.dateEnvoi;
    }

    public DossierAutre dateEnvoi(ZonedDateTime dateEnvoi) {
        this.setDateEnvoi(dateEnvoi);
        return this;
    }

    public void setDateEnvoi(ZonedDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getTypeDemendeur() {
        return this.typeDemendeur;
    }

    public DossierAutre typeDemendeur(String typeDemendeur) {
        this.setTypeDemendeur(typeDemendeur);
        return this;
    }

    public void setTypeDemendeur(String typeDemendeur) {
        this.typeDemendeur = typeDemendeur;
    }

    public String getProfession() {
        return this.profession;
    }

    public DossierAutre profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNature() {
        return this.nature;
    }

    public DossierAutre nature(String nature) {
        this.setNature(nature);
        return this;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getStatut() {
        return this.statut;
    }

    public DossierAutre statut(String statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public ZonedDateTime getDateDerniereModif() {
        return this.dateDerniereModif;
    }

    public DossierAutre dateDerniereModif(ZonedDateTime dateDerniereModif) {
        this.setDateDerniereModif(dateDerniereModif);
        return this;
    }

    public void setDateDerniereModif(ZonedDateTime dateDerniereModif) {
        this.dateDerniereModif = dateDerniereModif;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public DossierAutre commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getNom() {
        return this.nom;
    }

    public DossierAutre nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public DossierAutre prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public DossierAutre telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getcIN() {
        return this.cIN;
    }

    public DossierAutre cIN(String cIN) {
        this.setcIN(cIN);
        return this;
    }

    public void setcIN(String cIN) {
        this.cIN = cIN;
    }

    public String getRaisonSocial() {
        return this.raisonSocial;
    }

    public DossierAutre raisonSocial(String raisonSocial) {
        this.setRaisonSocial(raisonSocial);
        return this;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public String getiCE() {
        return this.iCE;
    }

    public DossierAutre iCE(String iCE) {
        this.setiCE(iCE);
        return this;
    }

    public void setiCE(String iCE) {
        this.iCE = iCE;
    }

    public String getNumRC() {
        return this.numRC;
    }

    public DossierAutre numRC(String numRC) {
        this.setNumRC(numRC);
        return this;
    }

    public void setNumRC(String numRC) {
        this.numRC = numRC;
    }

    public String getiF() {
        return this.iF;
    }

    public DossierAutre iF(String iF) {
        this.setiF(iF);
        return this;
    }

    public void setiF(String iF) {
        this.iF = iF;
    }

    public String getNumAffiliation() {
        return this.numAffiliation;
    }

    public DossierAutre numAffiliation(String numAffiliation) {
        this.setNumAffiliation(numAffiliation);
        return this;
    }

    public void setNumAffiliation(String numAffiliation) {
        this.numAffiliation = numAffiliation;
    }

    public Set<EtapeValidation> getEtapeValidationLists() {
        return this.etapeValidationLists;
    }

    public void setEtapeValidationLists(Set<EtapeValidation> etapeValidations) {
        if (this.etapeValidationLists != null) {
            this.etapeValidationLists.forEach(i -> i.setDossierAutre(null));
        }
        if (etapeValidations != null) {
            etapeValidations.forEach(i -> i.setDossierAutre(this));
        }
        this.etapeValidationLists = etapeValidations;
    }

    public DossierAutre etapeValidationLists(Set<EtapeValidation> etapeValidations) {
        this.setEtapeValidationLists(etapeValidations);
        return this;
    }

    public DossierAutre addEtapeValidationList(EtapeValidation etapeValidation) {
        this.etapeValidationLists.add(etapeValidation);
        etapeValidation.setDossierAutre(this);
        return this;
    }

    public DossierAutre removeEtapeValidationList(EtapeValidation etapeValidation) {
        this.etapeValidationLists.remove(etapeValidation);
        etapeValidation.setDossierAutre(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DossierAutre user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Document> getDocumentLists() {
        return this.documentLists;
    }

    public void setDocumentLists(Set<Document> documents) {
        this.documentLists = documents;
    }

    public DossierAutre documentLists(Set<Document> documents) {
        this.setDocumentLists(documents);
        return this;
    }

    public DossierAutre addDocumentList(Document document) {
        this.documentLists.add(document);
        document.getDossierAutreLists().add(this);
        return this;
    }

    public DossierAutre removeDocumentList(Document document) {
        this.documentLists.remove(document);
        document.getDossierAutreLists().remove(this);
        return this;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public DossierAutre utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    public Local getLocal() {
        return this.local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public DossierAutre local(Local local) {
        this.setLocal(local);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DossierAutre)) {
            return false;
        }
        return id != null && id.equals(((DossierAutre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DossierAutre{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", numeroEnvoi='" + getNumeroEnvoi() + "'" +
            ", dateDepot='" + getDateDepot() + "'" +
            ", dateEnvoi='" + getDateEnvoi() + "'" +
            ", typeDemendeur='" + getTypeDemendeur() + "'" +
            ", profession='" + getProfession() + "'" +
            ", nature='" + getNature() + "'" +
            ", statut='" + getStatut() + "'" +
            ", dateDerniereModif='" + getDateDerniereModif() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", cIN='" + getcIN() + "'" +
            ", raisonSocial='" + getRaisonSocial() + "'" +
            ", iCE='" + getiCE() + "'" +
            ", numRC='" + getNumRC() + "'" +
            ", iF='" + getiF() + "'" +
            ", numAffiliation='" + getNumAffiliation() + "'" +
            "}";
    }
}
