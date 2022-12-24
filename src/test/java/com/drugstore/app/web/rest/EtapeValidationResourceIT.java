package com.drugstore.app.web.rest;

import static com.drugstore.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.drugstore.app.IntegrationTest;
import com.drugstore.app.domain.EtapeValidation;
import com.drugstore.app.repository.EtapeValidationRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EtapeValidationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtapeValidationResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DECISION = "AAAAAAAAAA";
    private static final String UPDATED_DECISION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/etape-validations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EtapeValidationRepository etapeValidationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtapeValidationMockMvc;

    private EtapeValidation etapeValidation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapeValidation createEntity(EntityManager em) {
        EtapeValidation etapeValidation = new EtapeValidation()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .decision(DEFAULT_DECISION);
        return etapeValidation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapeValidation createUpdatedEntity(EntityManager em) {
        EtapeValidation etapeValidation = new EtapeValidation()
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .decision(UPDATED_DECISION);
        return etapeValidation;
    }

    @BeforeEach
    public void initTest() {
        etapeValidation = createEntity(em);
    }

    @Test
    @Transactional
    void createEtapeValidation() throws Exception {
        int databaseSizeBeforeCreate = etapeValidationRepository.findAll().size();
        // Create the EtapeValidation
        restEtapeValidationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etapeValidation))
            )
            .andExpect(status().isCreated());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeCreate + 1);
        EtapeValidation testEtapeValidation = etapeValidationList.get(etapeValidationList.size() - 1);
        assertThat(testEtapeValidation.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEtapeValidation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEtapeValidation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEtapeValidation.getDecision()).isEqualTo(DEFAULT_DECISION);
    }

    @Test
    @Transactional
    void createEtapeValidationWithExistingId() throws Exception {
        // Create the EtapeValidation with an existing ID
        etapeValidation.setId(1L);

        int databaseSizeBeforeCreate = etapeValidationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapeValidationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etapeValidation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtapeValidations() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        // Get all the etapeValidationList
        restEtapeValidationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapeValidation.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].decision").value(hasItem(DEFAULT_DECISION)));
    }

    @Test
    @Transactional
    void getEtapeValidation() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        // Get the etapeValidation
        restEtapeValidationMockMvc
            .perform(get(ENTITY_API_URL_ID, etapeValidation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etapeValidation.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.decision").value(DEFAULT_DECISION));
    }

    @Test
    @Transactional
    void getNonExistingEtapeValidation() throws Exception {
        // Get the etapeValidation
        restEtapeValidationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEtapeValidation() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();

        // Update the etapeValidation
        EtapeValidation updatedEtapeValidation = etapeValidationRepository.findById(etapeValidation.getId()).get();
        // Disconnect from session so that the updates on updatedEtapeValidation are not directly saved in db
        em.detach(updatedEtapeValidation);
        updatedEtapeValidation.libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION).date(UPDATED_DATE).decision(UPDATED_DECISION);

        restEtapeValidationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtapeValidation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEtapeValidation))
            )
            .andExpect(status().isOk());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
        EtapeValidation testEtapeValidation = etapeValidationList.get(etapeValidationList.size() - 1);
        assertThat(testEtapeValidation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEtapeValidation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEtapeValidation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEtapeValidation.getDecision()).isEqualTo(UPDATED_DECISION);
    }

    @Test
    @Transactional
    void putNonExistingEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etapeValidation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(etapeValidation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtapeValidationWithPatch() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();

        // Update the etapeValidation using partial update
        EtapeValidation partialUpdatedEtapeValidation = new EtapeValidation();
        partialUpdatedEtapeValidation.setId(etapeValidation.getId());

        partialUpdatedEtapeValidation.libelle(UPDATED_LIBELLE).description(UPDATED_DESCRIPTION);

        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtapeValidation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtapeValidation))
            )
            .andExpect(status().isOk());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
        EtapeValidation testEtapeValidation = etapeValidationList.get(etapeValidationList.size() - 1);
        assertThat(testEtapeValidation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEtapeValidation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEtapeValidation.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEtapeValidation.getDecision()).isEqualTo(DEFAULT_DECISION);
    }

    @Test
    @Transactional
    void fullUpdateEtapeValidationWithPatch() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();

        // Update the etapeValidation using partial update
        EtapeValidation partialUpdatedEtapeValidation = new EtapeValidation();
        partialUpdatedEtapeValidation.setId(etapeValidation.getId());

        partialUpdatedEtapeValidation
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .decision(UPDATED_DECISION);

        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtapeValidation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEtapeValidation))
            )
            .andExpect(status().isOk());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
        EtapeValidation testEtapeValidation = etapeValidationList.get(etapeValidationList.size() - 1);
        assertThat(testEtapeValidation.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEtapeValidation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEtapeValidation.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEtapeValidation.getDecision()).isEqualTo(UPDATED_DECISION);
    }

    @Test
    @Transactional
    void patchNonExistingEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etapeValidation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidation))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtapeValidation() throws Exception {
        int databaseSizeBeforeUpdate = etapeValidationRepository.findAll().size();
        etapeValidation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapeValidationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(etapeValidation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtapeValidation in the database
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtapeValidation() throws Exception {
        // Initialize the database
        etapeValidationRepository.saveAndFlush(etapeValidation);

        int databaseSizeBeforeDelete = etapeValidationRepository.findAll().size();

        // Delete the etapeValidation
        restEtapeValidationMockMvc
            .perform(delete(ENTITY_API_URL_ID, etapeValidation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtapeValidation> etapeValidationList = etapeValidationRepository.findAll();
        assertThat(etapeValidationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
