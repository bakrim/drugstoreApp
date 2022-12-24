package com.drugstore.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.drugstore.app.IntegrationTest;
import com.drugstore.app.domain.Representant;
import com.drugstore.app.repository.RepresentantRepository;
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
 * Integration tests for the {@link RepresentantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RepresentantResourceIT {

    private static final String DEFAULT_ENTITE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_C_IN = "AAAAAAAAAA";
    private static final String UPDATED_C_IN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/representants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RepresentantRepository representantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRepresentantMockMvc;

    private Representant representant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Representant createEntity(EntityManager em) {
        Representant representant = new Representant()
            .entite(DEFAULT_ENTITE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .cIN(DEFAULT_C_IN);
        return representant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Representant createUpdatedEntity(EntityManager em) {
        Representant representant = new Representant()
            .entite(UPDATED_ENTITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .cIN(UPDATED_C_IN);
        return representant;
    }

    @BeforeEach
    public void initTest() {
        representant = createEntity(em);
    }

    @Test
    @Transactional
    void createRepresentant() throws Exception {
        int databaseSizeBeforeCreate = representantRepository.findAll().size();
        // Create the Representant
        restRepresentantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(representant)))
            .andExpect(status().isCreated());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeCreate + 1);
        Representant testRepresentant = representantList.get(representantList.size() - 1);
        assertThat(testRepresentant.getEntite()).isEqualTo(DEFAULT_ENTITE);
        assertThat(testRepresentant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRepresentant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testRepresentant.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testRepresentant.getcIN()).isEqualTo(DEFAULT_C_IN);
    }

    @Test
    @Transactional
    void createRepresentantWithExistingId() throws Exception {
        // Create the Representant with an existing ID
        representant.setId(1L);

        int databaseSizeBeforeCreate = representantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepresentantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(representant)))
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRepresentants() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        // Get all the representantList
        restRepresentantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(representant.getId().intValue())))
            .andExpect(jsonPath("$.[*].entite").value(hasItem(DEFAULT_ENTITE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].cIN").value(hasItem(DEFAULT_C_IN)));
    }

    @Test
    @Transactional
    void getRepresentant() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        // Get the representant
        restRepresentantMockMvc
            .perform(get(ENTITY_API_URL_ID, representant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(representant.getId().intValue()))
            .andExpect(jsonPath("$.entite").value(DEFAULT_ENTITE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.cIN").value(DEFAULT_C_IN));
    }

    @Test
    @Transactional
    void getNonExistingRepresentant() throws Exception {
        // Get the representant
        restRepresentantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRepresentant() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        int databaseSizeBeforeUpdate = representantRepository.findAll().size();

        // Update the representant
        Representant updatedRepresentant = representantRepository.findById(representant.getId()).get();
        // Disconnect from session so that the updates on updatedRepresentant are not directly saved in db
        em.detach(updatedRepresentant);
        updatedRepresentant.entite(UPDATED_ENTITE).nom(UPDATED_NOM).prenom(UPDATED_PRENOM).telephone(UPDATED_TELEPHONE).cIN(UPDATED_C_IN);

        restRepresentantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRepresentant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRepresentant))
            )
            .andExpect(status().isOk());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
        Representant testRepresentant = representantList.get(representantList.size() - 1);
        assertThat(testRepresentant.getEntite()).isEqualTo(UPDATED_ENTITE);
        assertThat(testRepresentant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRepresentant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRepresentant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testRepresentant.getcIN()).isEqualTo(UPDATED_C_IN);
    }

    @Test
    @Transactional
    void putNonExistingRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, representant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(representant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRepresentantWithPatch() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        int databaseSizeBeforeUpdate = representantRepository.findAll().size();

        // Update the representant using partial update
        Representant partialUpdatedRepresentant = new Representant();
        partialUpdatedRepresentant.setId(representant.getId());

        partialUpdatedRepresentant.telephone(UPDATED_TELEPHONE).cIN(UPDATED_C_IN);

        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepresentant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepresentant))
            )
            .andExpect(status().isOk());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
        Representant testRepresentant = representantList.get(representantList.size() - 1);
        assertThat(testRepresentant.getEntite()).isEqualTo(DEFAULT_ENTITE);
        assertThat(testRepresentant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRepresentant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testRepresentant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testRepresentant.getcIN()).isEqualTo(UPDATED_C_IN);
    }

    @Test
    @Transactional
    void fullUpdateRepresentantWithPatch() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        int databaseSizeBeforeUpdate = representantRepository.findAll().size();

        // Update the representant using partial update
        Representant partialUpdatedRepresentant = new Representant();
        partialUpdatedRepresentant.setId(representant.getId());

        partialUpdatedRepresentant
            .entite(UPDATED_ENTITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .cIN(UPDATED_C_IN);

        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepresentant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepresentant))
            )
            .andExpect(status().isOk());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
        Representant testRepresentant = representantList.get(representantList.size() - 1);
        assertThat(testRepresentant.getEntite()).isEqualTo(UPDATED_ENTITE);
        assertThat(testRepresentant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRepresentant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRepresentant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testRepresentant.getcIN()).isEqualTo(UPDATED_C_IN);
    }

    @Test
    @Transactional
    void patchNonExistingRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, representant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representant))
            )
            .andExpect(status().isBadRequest());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRepresentant() throws Exception {
        int databaseSizeBeforeUpdate = representantRepository.findAll().size();
        representant.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(representant))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Representant in the database
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRepresentant() throws Exception {
        // Initialize the database
        representantRepository.saveAndFlush(representant);

        int databaseSizeBeforeDelete = representantRepository.findAll().size();

        // Delete the representant
        restRepresentantMockMvc
            .perform(delete(ENTITY_API_URL_ID, representant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Representant> representantList = representantRepository.findAll();
        assertThat(representantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
