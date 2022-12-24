package com.drugstore.app.web.rest;

import static com.drugstore.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.drugstore.app.IntegrationTest;
import com.drugstore.app.domain.Historique;
import com.drugstore.app.repository.HistoriqueRepository;
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
 * Integration tests for the {@link HistoriqueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoriqueResourceIT {

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/historiques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoriqueRepository historiqueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoriqueMockMvc;

    private Historique historique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historique createEntity(EntityManager em) {
        Historique historique = new Historique().action(DEFAULT_ACTION).date(DEFAULT_DATE);
        return historique;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historique createUpdatedEntity(EntityManager em) {
        Historique historique = new Historique().action(UPDATED_ACTION).date(UPDATED_DATE);
        return historique;
    }

    @BeforeEach
    public void initTest() {
        historique = createEntity(em);
    }

    @Test
    @Transactional
    void createHistorique() throws Exception {
        int databaseSizeBeforeCreate = historiqueRepository.findAll().size();
        // Create the Historique
        restHistoriqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historique)))
            .andExpect(status().isCreated());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeCreate + 1);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testHistorique.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createHistoriqueWithExistingId() throws Exception {
        // Create the Historique with an existing ID
        historique.setId(1L);

        int databaseSizeBeforeCreate = historiqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historique)))
            .andExpect(status().isBadRequest());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHistoriques() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        // Get all the historiqueList
        restHistoriqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historique.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    void getHistorique() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        // Get the historique
        restHistoriqueMockMvc
            .perform(get(ENTITY_API_URL_ID, historique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historique.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingHistorique() throws Exception {
        // Get the historique
        restHistoriqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHistorique() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();

        // Update the historique
        Historique updatedHistorique = historiqueRepository.findById(historique.getId()).get();
        // Disconnect from session so that the updates on updatedHistorique are not directly saved in db
        em.detach(updatedHistorique);
        updatedHistorique.action(UPDATED_ACTION).date(UPDATED_DATE);

        restHistoriqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHistorique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHistorique))
            )
            .andExpect(status().isOk());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testHistorique.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();
        historique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historique))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();
        historique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historique))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();
        historique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historique)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoriqueWithPatch() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();

        // Update the historique using partial update
        Historique partialUpdatedHistorique = new Historique();
        partialUpdatedHistorique.setId(historique.getId());

        partialUpdatedHistorique.action(UPDATED_ACTION);

        restHistoriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistorique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistorique))
            )
            .andExpect(status().isOk());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testHistorique.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateHistoriqueWithPatch() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();

        // Update the historique using partial update
        Historique partialUpdatedHistorique = new Historique();
        partialUpdatedHistorique.setId(historique.getId());

        partialUpdatedHistorique.action(UPDATED_ACTION).date(UPDATED_DATE);

        restHistoriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistorique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistorique))
            )
            .andExpect(status().isOk());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
        Historique testHistorique = historiqueList.get(historiqueList.size() - 1);
        assertThat(testHistorique.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testHistorique.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();
        historique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historique))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();
        historique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historique))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistorique() throws Exception {
        int databaseSizeBeforeUpdate = historiqueRepository.findAll().size();
        historique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriqueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(historique))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Historique in the database
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistorique() throws Exception {
        // Initialize the database
        historiqueRepository.saveAndFlush(historique);

        int databaseSizeBeforeDelete = historiqueRepository.findAll().size();

        // Delete the historique
        restHistoriqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, historique.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Historique> historiqueList = historiqueRepository.findAll();
        assertThat(historiqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
