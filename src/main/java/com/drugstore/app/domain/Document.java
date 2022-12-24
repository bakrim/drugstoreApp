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
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "thumbnail_name")
    private String thumbnailName;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @Column(name = "download_path")
    private String downloadPath;

    @Column(name = "thumbnail_download_path")
    private String thumbnailDownloadPath;

    @Column(name = "upload_date")
    private ZonedDateTime uploadDate;

    @Column(name = "path")
    private String path;

    @Column(name = "document_size")
    private Long documentSize;

    @ManyToMany(mappedBy = "documentLists")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "etapeValidationLists", "commisionLists", "user", "documentLists", "utilisateur", "local" },
        allowSetters = true
    )
    private Set<DossierPharmacie> dossierPharmacieLists = new HashSet<>();

    @ManyToMany(mappedBy = "documentLists")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etapeValidationLists", "user", "documentLists", "utilisateur", "local" }, allowSetters = true)
    private Set<DossierAutre> dossierAutreLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Document id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Document name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailName() {
        return this.thumbnailName;
    }

    public Document thumbnailName(String thumbnailName) {
        this.setThumbnailName(thumbnailName);
        return this;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Document mimeType(String mimeType) {
        this.setMimeType(mimeType);
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getThumbnailPath() {
        return this.thumbnailPath;
    }

    public Document thumbnailPath(String thumbnailPath) {
        this.setThumbnailPath(thumbnailPath);
        return this;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getDownloadPath() {
        return this.downloadPath;
    }

    public Document downloadPath(String downloadPath) {
        this.setDownloadPath(downloadPath);
        return this;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getThumbnailDownloadPath() {
        return this.thumbnailDownloadPath;
    }

    public Document thumbnailDownloadPath(String thumbnailDownloadPath) {
        this.setThumbnailDownloadPath(thumbnailDownloadPath);
        return this;
    }

    public void setThumbnailDownloadPath(String thumbnailDownloadPath) {
        this.thumbnailDownloadPath = thumbnailDownloadPath;
    }

    public ZonedDateTime getUploadDate() {
        return this.uploadDate;
    }

    public Document uploadDate(ZonedDateTime uploadDate) {
        this.setUploadDate(uploadDate);
        return this;
    }

    public void setUploadDate(ZonedDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getPath() {
        return this.path;
    }

    public Document path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getDocumentSize() {
        return this.documentSize;
    }

    public Document documentSize(Long documentSize) {
        this.setDocumentSize(documentSize);
        return this;
    }

    public void setDocumentSize(Long documentSize) {
        this.documentSize = documentSize;
    }

    public Set<DossierPharmacie> getDossierPharmacieLists() {
        return this.dossierPharmacieLists;
    }

    public void setDossierPharmacieLists(Set<DossierPharmacie> dossierPharmacies) {
        if (this.dossierPharmacieLists != null) {
            this.dossierPharmacieLists.forEach(i -> i.removeDocumentList(this));
        }
        if (dossierPharmacies != null) {
            dossierPharmacies.forEach(i -> i.addDocumentList(this));
        }
        this.dossierPharmacieLists = dossierPharmacies;
    }

    public Document dossierPharmacieLists(Set<DossierPharmacie> dossierPharmacies) {
        this.setDossierPharmacieLists(dossierPharmacies);
        return this;
    }

    public Document addDossierPharmacieList(DossierPharmacie dossierPharmacie) {
        this.dossierPharmacieLists.add(dossierPharmacie);
        dossierPharmacie.getDocumentLists().add(this);
        return this;
    }

    public Document removeDossierPharmacieList(DossierPharmacie dossierPharmacie) {
        this.dossierPharmacieLists.remove(dossierPharmacie);
        dossierPharmacie.getDocumentLists().remove(this);
        return this;
    }

    public Set<DossierAutre> getDossierAutreLists() {
        return this.dossierAutreLists;
    }

    public void setDossierAutreLists(Set<DossierAutre> dossierAutres) {
        if (this.dossierAutreLists != null) {
            this.dossierAutreLists.forEach(i -> i.removeDocumentList(this));
        }
        if (dossierAutres != null) {
            dossierAutres.forEach(i -> i.addDocumentList(this));
        }
        this.dossierAutreLists = dossierAutres;
    }

    public Document dossierAutreLists(Set<DossierAutre> dossierAutres) {
        this.setDossierAutreLists(dossierAutres);
        return this;
    }

    public Document addDossierAutreList(DossierAutre dossierAutre) {
        this.dossierAutreLists.add(dossierAutre);
        dossierAutre.getDocumentLists().add(this);
        return this;
    }

    public Document removeDossierAutreList(DossierAutre dossierAutre) {
        this.dossierAutreLists.remove(dossierAutre);
        dossierAutre.getDocumentLists().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", thumbnailName='" + getThumbnailName() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", thumbnailPath='" + getThumbnailPath() + "'" +
            ", downloadPath='" + getDownloadPath() + "'" +
            ", thumbnailDownloadPath='" + getThumbnailDownloadPath() + "'" +
            ", uploadDate='" + getUploadDate() + "'" +
            ", path='" + getPath() + "'" +
            ", documentSize=" + getDocumentSize() +
            "}";
    }
}
