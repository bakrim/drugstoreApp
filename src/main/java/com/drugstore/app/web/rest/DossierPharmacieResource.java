package com.drugstore.app.web.rest;

import com.drugstore.app.domain.DossierPharmacie;
import com.drugstore.app.repository.DossierPharmacieRepository;
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
 * REST controller for managing {@link com.drugstore.app.domain.DossierPharmacie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DossierPharmacieResource {

    private final Logger log = LoggerFactory.getLogger(DossierPharmacieResource.class);

    private static final String ENTITY_NAME = "dossierPharmacie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DossierPharmacieRepository dossierPharmacieRepository;

    public DossierPharmacieResource(DossierPharmacieRepository dossierPharmacieRepository) {
        this.dossierPharmacieRepository = dossierPharmacieRepository;
    }

    /**
     * {@code POST  /dossier-pharmacies} : Create a new dossierPharmacie.
     *
     * @param dossierPharmacie the dossierPharmacie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dossierPharmacie, or with status {@code 400 (Bad Request)} if the dossierPharmacie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dossier-pharmacies")
    public ResponseEntity<DossierPharmacie> createDossierPharmacie(@RequestBody DossierPharmacie dossierPharmacie)
        throws URISyntaxException {
        log.debug("REST request to save DossierPharmacie : {}", dossierPharmacie);
        if (dossierPharmacie.getId() != null) {
            throw new BadRequestAlertException("A new dossierPharmacie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DossierPharmacie result = dossierPharmacieRepository.save(dossierPharmacie);
        return ResponseEntity
            .created(new URI("/api/dossier-pharmacies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dossier-pharmacies/:id} : Updates an existing dossierPharmacie.
     *
     * @param id the id of the dossierPharmacie to save.
     * @param dossierPharmacie the dossierPharmacie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dossierPharmacie,
     * or with status {@code 400 (Bad Request)} if the dossierPharmacie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dossierPharmacie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dossier-pharmacies/{id}")
    public ResponseEntity<DossierPharmacie> updateDossierPharmacie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DossierPharmacie dossierPharmacie
    ) throws URISyntaxException {
        log.debug("REST request to update DossierPharmacie : {}, {}", id, dossierPharmacie);
        if (dossierPharmacie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dossierPharmacie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dossierPharmacieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DossierPharmacie result = dossierPharmacieRepository.save(dossierPharmacie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dossierPharmacie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dossier-pharmacies/:id} : Partial updates given fields of an existing dossierPharmacie, field will ignore if it is null
     *
     * @param id the id of the dossierPharmacie to save.
     * @param dossierPharmacie the dossierPharmacie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dossierPharmacie,
     * or with status {@code 400 (Bad Request)} if the dossierPharmacie is not valid,
     * or with status {@code 404 (Not Found)} if the dossierPharmacie is not found,
     * or with status {@code 500 (Internal Server Error)} if the dossierPharmacie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dossier-pharmacies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DossierPharmacie> partialUpdateDossierPharmacie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DossierPharmacie dossierPharmacie
    ) throws URISyntaxException {
        log.debug("REST request to partial update DossierPharmacie partially : {}, {}", id, dossierPharmacie);
        if (dossierPharmacie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dossierPharmacie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dossierPharmacieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DossierPharmacie> result = dossierPharmacieRepository
            .findById(dossierPharmacie.getId())
            .map(existingDossierPharmacie -> {
                if (dossierPharmacie.getNumero() != null) {
                    existingDossierPharmacie.setNumero(dossierPharmacie.getNumero());
                }
                if (dossierPharmacie.getDateDepot() != null) {
                    existingDossierPharmacie.setDateDepot(dossierPharmacie.getDateDepot());
                }
                if (dossierPharmacie.getNature() != null) {
                    existingDossierPharmacie.setNature(dossierPharmacie.getNature());
                }
                if (dossierPharmacie.getStatut() != null) {
                    existingDossierPharmacie.setStatut(dossierPharmacie.getStatut());
                }
                if (dossierPharmacie.getDateDerniereModif() != null) {
                    existingDossierPharmacie.setDateDerniereModif(dossierPharmacie.getDateDerniereModif());
                }
                if (dossierPharmacie.getCommentaire() != null) {
                    existingDossierPharmacie.setCommentaire(dossierPharmacie.getCommentaire());
                }
                if (dossierPharmacie.getNom() != null) {
                    existingDossierPharmacie.setNom(dossierPharmacie.getNom());
                }
                if (dossierPharmacie.getPrenom() != null) {
                    existingDossierPharmacie.setPrenom(dossierPharmacie.getPrenom());
                }
                if (dossierPharmacie.getTelephone() != null) {
                    existingDossierPharmacie.setTelephone(dossierPharmacie.getTelephone());
                }
                if (dossierPharmacie.getcIN() != null) {
                    existingDossierPharmacie.setcIN(dossierPharmacie.getcIN());
                }

                return existingDossierPharmacie;
            })
            .map(dossierPharmacieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dossierPharmacie.getId().toString())
        );
    }

    /**
     * {@code GET  /dossier-pharmacies} : get all the dossierPharmacies.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dossierPharmacies in body.
     */
    @GetMapping("/dossier-pharmacies")
    public List<DossierPharmacie> getAllDossierPharmacies(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DossierPharmacies");
        if (eagerload) {
            return dossierPharmacieRepository.findAllWithEagerRelationships();
        } else {
            return dossierPharmacieRepository.findAll();
        }
    }

    /**
     * {@code GET  /dossier-pharmacies/:id} : get the "id" dossierPharmacie.
     *
     * @param id the id of the dossierPharmacie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dossierPharmacie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dossier-pharmacies/{id}")
    public ResponseEntity<DossierPharmacie> getDossierPharmacie(@PathVariable Long id) {
        log.debug("REST request to get DossierPharmacie : {}", id);
        Optional<DossierPharmacie> dossierPharmacie = dossierPharmacieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(dossierPharmacie);
    }

    /**
     * {@code DELETE  /dossier-pharmacies/:id} : delete the "id" dossierPharmacie.
     *
     * @param id the id of the dossierPharmacie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dossier-pharmacies/{id}")
    public ResponseEntity<Void> deleteDossierPharmacie(@PathVariable Long id) {
        log.debug("REST request to delete DossierPharmacie : {}", id);
        dossierPharmacieRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
