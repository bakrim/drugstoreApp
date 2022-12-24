package com.drugstore.app.web.rest;

import static com.drugstore.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.drugstore.app.IntegrationTest;
import com.drugstore.app.domain.Commission;
import com.drugstore.app.repository.CommissionRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CommissionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CommissionResourceIT {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DECISION = "AAAAAAAAAA";
    private static final String UPDATED_DECISION = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/commissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommissionRepository commissionRepository;

    @Mock
    private CommissionRepository commissionRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommissionMockMvc;

    private Commission commission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commission createEntity(EntityManager em) {
        Commission commission = new Commission().date(DEFAULT_DATE).decision(DEFAULT_DECISION).motif(DEFAULT_MOTIF);
        return commission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commission createUpdatedEntity(EntityManager em) {
        Commission commission = new Commission().date(UPDATED_DATE).decision(UPDATED_DECISION).motif(UPDATED_MOTIF);
        return commission;
    }

    @BeforeEach
    public void initTest() {
        commission = createEntity(em);
    }

    @Test
    @Transactional
    void createCommission() throws Exception {
        int databaseSizeBeforeCreate = commissionRepository.findAll().size();
        // Create the Commission
        restCommissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commission)))
            .andExpect(status().isCreated());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeCreate + 1);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCommission.getDecision()).isEqualTo(DEFAULT_DECISION);
        assertThat(testCommission.getMotif()).isEqualTo(DEFAULT_MOTIF);
    }

    @Test
    @Transactional
    void createCommissionWithExistingId() throws Exception {
        // Create the Commission with an existing ID
        commission.setId(1L);

        int databaseSizeBeforeCreate = commissionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commission)))
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommissions() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList
        restCommissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commission.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].decision").value(hasItem(DEFAULT_DECISION)))
            .andExpect(jsonPath("$.[*].motif").value(hasItem(DEFAULT_MOTIF)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommissionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(commissionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCommissionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(commissionRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommissionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(commissionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCommissionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(commissionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCommission() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get the commission
        restCommissionMockMvc
            .perform(get(ENTITY_API_URL_ID, commission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commission.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.decision").value(DEFAULT_DECISION))
            .andExpect(jsonPath("$.motif").value(DEFAULT_MOTIF));
    }

    @Test
    @Transactional
    void getNonExistingCommission() throws Exception {
        // Get the commission
        restCommissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommission() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();

        // Update the commission
        Commission updatedCommission = commissionRepository.findById(commission.getId()).get();
        // Disconnect from session so that the updates on updatedCommission are not directly saved in db
        em.detach(updatedCommission);
        updatedCommission.date(UPDATED_DATE).decision(UPDATED_DECISION).motif(UPDATED_MOTIF);

        restCommissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCommission))
            )
            .andExpect(status().isOk());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCommission.getDecision()).isEqualTo(UPDATED_DECISION);
        assertThat(testCommission.getMotif()).isEqualTo(UPDATED_MOTIF);
    }

    @Test
    @Transactional
    void putNonExistingCommission() throws Exception {
        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();
        commission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commission.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commission))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommission() throws Exception {
        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();
        commission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commission))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommission() throws Exception {
        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();
        commission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commission)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommissionWithPatch() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();

        // Update the commission using partial update
        Commission partialUpdatedCommission = new Commission();
        partialUpdatedCommission.setId(commission.getId());

        partialUpdatedCommission.decision(UPDATED_DECISION).motif(UPDATED_MOTIF);

        restCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommission))
            )
            .andExpect(status().isOk());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCommission.getDecision()).isEqualTo(UPDATED_DECISION);
        assertThat(testCommission.getMotif()).isEqualTo(UPDATED_MOTIF);
    }

    @Test
    @Transactional
    void fullUpdateCommissionWithPatch() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();

        // Update the commission using partial update
        Commission partialUpdatedCommission = new Commission();
        partialUpdatedCommission.setId(commission.getId());

        partialUpdatedCommission.date(UPDATED_DATE).decision(UPDATED_DECISION).motif(UPDATED_MOTIF);

        restCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommission))
            )
            .andExpect(status().isOk());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCommission.getDecision()).isEqualTo(UPDATED_DECISION);
        assertThat(testCommission.getMotif()).isEqualTo(UPDATED_MOTIF);
    }

    @Test
    @Transactional
    void patchNonExistingCommission() throws Exception {
        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();
        commission.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commission))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommission() throws Exception {
        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();
        commission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commission))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommission() throws Exception {
        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();
        commission.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommissionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(commission))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommission() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        int databaseSizeBeforeDelete = commissionRepository.findAll().size();

        // Delete the commission
        restCommissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, commission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
