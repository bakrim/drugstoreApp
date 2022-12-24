package com.drugstore.app.web.rest;

import com.drugstore.app.domain.DossierAutre;
import com.drugstore.app.repository.DossierAutreRepository;
import com.drugstore.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.drugstore.app.domain.DossierAutre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DossierAutreResource {

    private final Logger log = LoggerFactory.getLogger(DossierAutreResource.class);

    private static final String ENTITY_NAME = "dossierAutre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DossierAutreRepository dossierAutreRepository;

    public DossierAutreResource(DossierAutreRepository dossierAutreRepository) {
        this.dossierAutreRepository = dossierAutreRepository;
    }

    /**
     * {@code POST  /dossier-autres} : Create a new dossierAutre.
     *
     * @param dossierAutre the dossierAutre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dossierAutre, or with status {@code 400 (Bad Request)} if the dossierAutre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dossier-autres")
    public ResponseEntity<DossierAutre> createDossierAutre(@RequestBody DossierAutre dossierAutre) throws URISyntaxException {
        log.debug("REST request to save DossierAutre : {}", dossierAutre);
        if (dossierAutre.getId() != null) {
            throw new BadRequestAlertException("A new dossierAutre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DossierAutre result = dossierAutreRepository.save(dossierAutre);
        return ResponseEntity
            .created(new URI("/api/dossier-autres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dossier-autres/:id} : Updates an existing dossierAutre.
     *
     * @param id the id of the dossierAutre to save.
     * @param dossierAutre the dossierAutre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dossierAutre,
     * or with status {@code 400 (Bad Request)} if the dossierAutre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dossierAutre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dossier-autres/{id}")
    public ResponseEntity<DossierAutre> updateDossierAutre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DossierAutre dossierAutre
    ) throws URISyntaxException {
        log.debug("REST request to update DossierAutre : {}, {}", id, dossierAutre);
        if (dossierAutre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dossierAutre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dossierAutreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DossierAutre result = dossierAutreRepository.save(dossierAutre);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dossierAutre.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dossier-autres/:id} : Partial updates given fields of an existing dossierAutre, field will ignore if it is null
     *
     * @param id the id of the dossierAutre to save.
     * @param dossierAutre the dossierAutre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dossierAutre,
     * or with status {@code 400 (Bad Request)} if the dossierAutre is not valid,
     * or with status {@code 404 (Not Found)} if the dossierAutre is not found,
     * or with status {@code 500 (Internal Server Error)} if the dossierAutre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dossier-autres/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DossierAutre> partialUpdateDossierAutre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DossierAutre dossierAutre
    ) throws URISyntaxException {
        log.debug("REST request to partial update DossierAutre partially : {}, {}", id, dossierAutre);
        if (dossierAutre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dossierAutre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dossierAutreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DossierAutre> result = dossierAutreRepository
            .findById(dossierAutre.getId())
            .map(existingDossierAutre -> {
                if (dossierAutre.getNumero() != null) {
                    existingDossierAutre.setNumero(dossierAutre.getNumero());
                }
                if (dossierAutre.getNumeroEnvoi() != null) {
                    existingDossierAutre.setNumeroEnvoi(dossierAutre.getNumeroEnvoi());
                }
                if (dossierAutre.getDateDepot() != null) {
                    existingDossierAutre.setDateDepot(dossierAutre.getDateDepot());
                }
                if (dossierAutre.getDateEnvoi() != null) {
                    existingDossierAutre.setDateEnvoi(dossierAutre.getDateEnvoi());
                }
                if (dossierAutre.getTypeDemendeur() != null) {
                    existingDossierAutre.setTypeDemendeur(dossierAutre.getTypeDemendeur());
                }
                if (dossierAutre.getProfession() != null) {
                    existingDossierAutre.setProfession(dossierAutre.getProfession());
                }
                if (dossierAutre.getNature() != null) {
                    existingDossierAutre.setNature(dossierAutre.getNature());
                }
                if (dossierAutre.getStatut() != null) {
                    existingDossierAutre.setStatut(dossierAutre.getStatut());
                }
                if (dossierAutre.getDateDerniereModif() != null) {
                    existingDossierAutre.setDateDerniereModif(dossierAutre.getDateDerniereModif());
                }
                if (dossierAutre.getCommentaire() != null) {
                    existingDossierAutre.setCommentaire(dossierAutre.getCommentaire());
                }
                if (dossierAutre.getNom() != null) {
                    existingDossierAutre.setNom(dossierAutre.getNom());
                }
                if (dossierAutre.getPrenom() != null) {
                    existingDossierAutre.setPrenom(dossierAutre.getPrenom());
                }
                if (dossierAutre.getTelephone() != null) {
                    existingDossierAutre.setTelephone(dossierAutre.getTelephone());
                }
                if (dossierAutre.getcIN() != null) {
                    existingDossierAutre.setcIN(dossierAutre.getcIN());
                }
                if (dossierAutre.getRaisonSocial() != null) {
                    existingDossierAutre.setRaisonSocial(dossierAutre.getRaisonSocial());
                }
                if (dossierAutre.getiCE() != null) {
                    existingDossierAutre.setiCE(dossierAutre.getiCE());
                }
                if (dossierAutre.getNumRC() != null) {
                    existingDossierAutre.setNumRC(dossierAutre.getNumRC());
                }
                if (dossierAutre.getiF() != null) {
                    existingDossierAutre.setiF(dossierAutre.getiF());
                }
                if (dossierAutre.getNumAffiliation() != null) {
                    existingDossierAutre.setNumAffiliation(dossierAutre.getNumAffiliation());
                }

                return existingDossierAutre;
            })
            .map(dossierAutreRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dossierAutre.getId().toString())
        );
    }

    /**
     * {@code GET  /dossier-autres} : get all the dossierAutres.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dossierAutres in body.
     */
    @GetMapping("/dossier-autres")
    public List<DossierAutre> getAllDossierAutres(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DossierAutres");
        if (eagerload) {
            return dossierAutreRepository.findAllWithEagerRelationships();
        } else {
            return dossierAutreRepository.findAll();
        }
    }

    /**
     * {@code GET  /dossier-autres/:id} : get the "id" dossierAutre.
     *
     * @param id the id of the dossierAutre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dossierAutre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dossier-autres/{id}")
    public ResponseEntity<DossierAutre> getDossierAutre(@PathVariable Long id) {
        log.debug("REST request to get DossierAutre : {}", id);
        Optional<DossierAutre> dossierAutre = dossierAutreRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(dossierAutre);
    }

    /**
     * {@code DELETE  /dossier-autres/:id} : delete the "id" dossierAutre.
     *
     * @param id the id of the dossierAutre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dossier-autres/{id}")
    public ResponseEntity<Void> deleteDossierAutre(@PathVariable Long id) {
        log.debug("REST request to delete DossierAutre : {}", id);
        dossierAutreRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
