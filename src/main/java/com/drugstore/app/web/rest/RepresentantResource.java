package com.drugstore.app.web.rest;

import com.drugstore.app.domain.Representant;
import com.drugstore.app.repository.RepresentantRepository;
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
 * REST controller for managing {@link com.drugstore.app.domain.Representant}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RepresentantResource {

    private final Logger log = LoggerFactory.getLogger(RepresentantResource.class);

    private static final String ENTITY_NAME = "representant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepresentantRepository representantRepository;

    public RepresentantResource(RepresentantRepository representantRepository) {
        this.representantRepository = representantRepository;
    }

    /**
     * {@code POST  /representants} : Create a new representant.
     *
     * @param representant the representant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new representant, or with status {@code 400 (Bad Request)} if the representant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/representants")
    public ResponseEntity<Representant> createRepresentant(@RequestBody Representant representant) throws URISyntaxException {
        log.debug("REST request to save Representant : {}", representant);
        if (representant.getId() != null) {
            throw new BadRequestAlertException("A new representant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Representant result = representantRepository.save(representant);
        return ResponseEntity
            .created(new URI("/api/representants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /representants/:id} : Updates an existing representant.
     *
     * @param id the id of the representant to save.
     * @param representant the representant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representant,
     * or with status {@code 400 (Bad Request)} if the representant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the representant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/representants/{id}")
    public ResponseEntity<Representant> updateRepresentant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Representant representant
    ) throws URISyntaxException {
        log.debug("REST request to update Representant : {}, {}", id, representant);
        if (representant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Representant result = representantRepository.save(representant);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, representant.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /representants/:id} : Partial updates given fields of an existing representant, field will ignore if it is null
     *
     * @param id the id of the representant to save.
     * @param representant the representant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representant,
     * or with status {@code 400 (Bad Request)} if the representant is not valid,
     * or with status {@code 404 (Not Found)} if the representant is not found,
     * or with status {@code 500 (Internal Server Error)} if the representant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/representants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Representant> partialUpdateRepresentant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Representant representant
    ) throws URISyntaxException {
        log.debug("REST request to partial update Representant partially : {}, {}", id, representant);
        if (representant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Representant> result = representantRepository
            .findById(representant.getId())
            .map(existingRepresentant -> {
                if (representant.getEntite() != null) {
                    existingRepresentant.setEntite(representant.getEntite());
                }
                if (representant.getNom() != null) {
                    existingRepresentant.setNom(representant.getNom());
                }
                if (representant.getPrenom() != null) {
                    existingRepresentant.setPrenom(representant.getPrenom());
                }
                if (representant.getTelephone() != null) {
                    existingRepresentant.setTelephone(representant.getTelephone());
                }
                if (representant.getcIN() != null) {
                    existingRepresentant.setcIN(representant.getcIN());
                }

                return existingRepresentant;
            })
            .map(representantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, representant.getId().toString())
        );
    }

    /**
     * {@code GET  /representants} : get all the representants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of representants in body.
     */
    @GetMapping("/representants")
    public List<Representant> getAllRepresentants() {
        log.debug("REST request to get all Representants");
        return representantRepository.findAll();
    }

    /**
     * {@code GET  /representants/:id} : get the "id" representant.
     *
     * @param id the id of the representant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the representant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/representants/{id}")
    public ResponseEntity<Representant> getRepresentant(@PathVariable Long id) {
        log.debug("REST request to get Representant : {}", id);
        Optional<Representant> representant = representantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(representant);
    }

    /**
     * {@code DELETE  /representants/:id} : delete the "id" representant.
     *
     * @param id the id of the representant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/representants/{id}")
    public ResponseEntity<Void> deleteRepresentant(@PathVariable Long id) {
        log.debug("REST request to delete Representant : {}", id);
        representantRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
