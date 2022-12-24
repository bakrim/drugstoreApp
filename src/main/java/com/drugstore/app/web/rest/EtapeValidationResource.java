package com.drugstore.app.web.rest;

import com.drugstore.app.domain.EtapeValidation;
import com.drugstore.app.repository.EtapeValidationRepository;
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
 * REST controller for managing {@link com.drugstore.app.domain.EtapeValidation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EtapeValidationResource {

    private final Logger log = LoggerFactory.getLogger(EtapeValidationResource.class);

    private static final String ENTITY_NAME = "etapeValidation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtapeValidationRepository etapeValidationRepository;

    public EtapeValidationResource(EtapeValidationRepository etapeValidationRepository) {
        this.etapeValidationRepository = etapeValidationRepository;
    }

    /**
     * {@code POST  /etape-validations} : Create a new etapeValidation.
     *
     * @param etapeValidation the etapeValidation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etapeValidation, or with status {@code 400 (Bad Request)} if the etapeValidation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etape-validations")
    public ResponseEntity<EtapeValidation> createEtapeValidation(@RequestBody EtapeValidation etapeValidation) throws URISyntaxException {
        log.debug("REST request to save EtapeValidation : {}", etapeValidation);
        if (etapeValidation.getId() != null) {
            throw new BadRequestAlertException("A new etapeValidation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtapeValidation result = etapeValidationRepository.save(etapeValidation);
        return ResponseEntity
            .created(new URI("/api/etape-validations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etape-validations/:id} : Updates an existing etapeValidation.
     *
     * @param id the id of the etapeValidation to save.
     * @param etapeValidation the etapeValidation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapeValidation,
     * or with status {@code 400 (Bad Request)} if the etapeValidation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etapeValidation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etape-validations/{id}")
    public ResponseEntity<EtapeValidation> updateEtapeValidation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtapeValidation etapeValidation
    ) throws URISyntaxException {
        log.debug("REST request to update EtapeValidation : {}, {}", id, etapeValidation);
        if (etapeValidation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etapeValidation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etapeValidationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtapeValidation result = etapeValidationRepository.save(etapeValidation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapeValidation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etape-validations/:id} : Partial updates given fields of an existing etapeValidation, field will ignore if it is null
     *
     * @param id the id of the etapeValidation to save.
     * @param etapeValidation the etapeValidation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapeValidation,
     * or with status {@code 400 (Bad Request)} if the etapeValidation is not valid,
     * or with status {@code 404 (Not Found)} if the etapeValidation is not found,
     * or with status {@code 500 (Internal Server Error)} if the etapeValidation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etape-validations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EtapeValidation> partialUpdateEtapeValidation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EtapeValidation etapeValidation
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtapeValidation partially : {}, {}", id, etapeValidation);
        if (etapeValidation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etapeValidation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etapeValidationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtapeValidation> result = etapeValidationRepository
            .findById(etapeValidation.getId())
            .map(existingEtapeValidation -> {
                if (etapeValidation.getLibelle() != null) {
                    existingEtapeValidation.setLibelle(etapeValidation.getLibelle());
                }
                if (etapeValidation.getDescription() != null) {
                    existingEtapeValidation.setDescription(etapeValidation.getDescription());
                }
                if (etapeValidation.getDate() != null) {
                    existingEtapeValidation.setDate(etapeValidation.getDate());
                }
                if (etapeValidation.getDecision() != null) {
                    existingEtapeValidation.setDecision(etapeValidation.getDecision());
                }

                return existingEtapeValidation;
            })
            .map(etapeValidationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapeValidation.getId().toString())
        );
    }

    /**
     * {@code GET  /etape-validations} : get all the etapeValidations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etapeValidations in body.
     */
    @GetMapping("/etape-validations")
    public List<EtapeValidation> getAllEtapeValidations() {
        log.debug("REST request to get all EtapeValidations");
        return etapeValidationRepository.findAll();
    }

    /**
     * {@code GET  /etape-validations/:id} : get the "id" etapeValidation.
     *
     * @param id the id of the etapeValidation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etapeValidation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etape-validations/{id}")
    public ResponseEntity<EtapeValidation> getEtapeValidation(@PathVariable Long id) {
        log.debug("REST request to get EtapeValidation : {}", id);
        Optional<EtapeValidation> etapeValidation = etapeValidationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(etapeValidation);
    }

    /**
     * {@code DELETE  /etape-validations/:id} : delete the "id" etapeValidation.
     *
     * @param id the id of the etapeValidation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etape-validations/{id}")
    public ResponseEntity<Void> deleteEtapeValidation(@PathVariable Long id) {
        log.debug("REST request to delete EtapeValidation : {}", id);
        etapeValidationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
