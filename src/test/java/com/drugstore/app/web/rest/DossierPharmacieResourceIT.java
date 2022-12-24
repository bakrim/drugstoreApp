package com.drugstore.app.web.rest;

import static com.drugstore.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.drugstore.app.IntegrationTest;
import com.drugstore.app.domain.DossierPharmacie;
import com.drugstore.app.repository.DossierPharmacieRepository;
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
 * Integration tests for the {@link DossierPharmacieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DossierPharmacieResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_DEPOT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEPOT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NATURE = "AAAAAAAAAA";
    private static final String UPDATED_NATURE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_DERNIERE_MODIF = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DERNIERE_MODIF = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_C_IN = "AAAAAAAAAA";
    private static final String UPDATED_C_IN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dossier-pharmacies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DossierPharmacieRepository dossierPharmacieRepository;

    @Mock
    private DossierPharmacieRepository dossierPharmacieRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDossierPharmacieMockMvc;

    private DossierPharmacie dossierPharmacie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DossierPharmacie createEntity(EntityManager em) {
        DossierPharmacie dossierPharmacie = new DossierPharmacie()
            .numero(DEFAULT_NUMERO)
            .dateDepot(DEFAULT_DATE_DEPOT)
            .nature(DEFAULT_NATURE)
            .statut(DEFAULT_STATUT)
            .dateDerniereModif(DEFAULT_DATE_DERNIERE_MODIF)
            .commentaire(DEFAULT_COMMENTAIRE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .cIN(DEFAULT_C_IN);
        return dossierPharmacie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DossierPharmacie createUpdatedEntity(EntityManager em) {
        DossierPharmacie dossierPharmacie = new DossierPharmacie()
            .numero(UPDATED_NUMERO)
            .dateDepot(UPDATED_DATE_DEPOT)
            .nature(UPDATED_NATURE)
            .statut(UPDATED_STATUT)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .commentaire(UPDATED_COMMENTAIRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .cIN(UPDATED_C_IN);
        return dossierPharmacie;
    }

    @BeforeEach
    public void initTest() {
        dossierPharmacie = createEntity(em);
    }

    @Test
    @Transactional
    void createDossierPharmacie() throws Exception {
        int databaseSizeBeforeCreate = dossierPharmacieRepository.findAll().size();
        // Create the DossierPharmacie
        restDossierPharmacieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossierPharmacie))
            )
            .andExpect(status().isCreated());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeCreate + 1);
        DossierPharmacie testDossierPharmacie = dossierPharmacieList.get(dossierPharmacieList.size() - 1);
        assertThat(testDossierPharmacie.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDossierPharmacie.getDateDepot()).isEqualTo(DEFAULT_DATE_DEPOT);
        assertThat(testDossierPharmacie.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testDossierPharmacie.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDossierPharmacie.getDateDerniereModif()).isEqualTo(DEFAULT_DATE_DERNIERE_MODIF);
        assertThat(testDossierPharmacie.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testDossierPharmacie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDossierPharmacie.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDossierPharmacie.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDossierPharmacie.getcIN()).isEqualTo(DEFAULT_C_IN);
    }

    @Test
    @Transactional
    void createDossierPharmacieWithExistingId() throws Exception {
        // Create the DossierPharmacie with an existing ID
        dossierPharmacie.setId(1L);

        int databaseSizeBeforeCreate = dossierPharmacieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossierPharmacieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossierPharmacie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDossierPharmacies() throws Exception {
        // Initialize the database
        dossierPharmacieRepository.saveAndFlush(dossierPharmacie);

        // Get all the dossierPharmacieList
        restDossierPharmacieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossierPharmacie.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].dateDepot").value(hasItem(sameInstant(DEFAULT_DATE_DEPOT))))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].dateDerniereModif").value(hasItem(sameInstant(DEFAULT_DATE_DERNIERE_MODIF))))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].cIN").value(hasItem(DEFAULT_C_IN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDossierPharmaciesWithEagerRelationshipsIsEnabled() throws Exception {
        when(dossierPharmacieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDossierPharmacieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dossierPharmacieRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDossierPharmaciesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dossierPharmacieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDossierPharmacieMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dossierPharmacieRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDossierPharmacie() throws Exception {
        // Initialize the database
        dossierPharmacieRepository.saveAndFlush(dossierPharmacie);

        // Get the dossierPharmacie
        restDossierPharmacieMockMvc
            .perform(get(ENTITY_API_URL_ID, dossierPharmacie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dossierPharmacie.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.dateDepot").value(sameInstant(DEFAULT_DATE_DEPOT)))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.dateDerniereModif").value(sameInstant(DEFAULT_DATE_DERNIERE_MODIF)))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.cIN").value(DEFAULT_C_IN));
    }

    @Test
    @Transactional
    void getNonExistingDossierPharmacie() throws Exception {
        // Get the dossierPharmacie
        restDossierPharmacieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDossierPharmacie() throws Exception {
        // Initialize the database
        dossierPharmacieRepository.saveAndFlush(dossierPharmacie);

        int databaseSizeBeforeUpdate = dossierPharmacieRepository.findAll().size();

        // Update the dossierPharmacie
        DossierPharmacie updatedDossierPharmacie = dossierPharmacieRepository.findById(dossierPharmacie.getId()).get();
        // Disconnect from session so that the updates on updatedDossierPharmacie are not directly saved in db
        em.detach(updatedDossierPharmacie);
        updatedDossierPharmacie
            .numero(UPDATED_NUMERO)
            .dateDepot(UPDATED_DATE_DEPOT)
            .nature(UPDATED_NATURE)
            .statut(UPDATED_STATUT)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .commentaire(UPDATED_COMMENTAIRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .cIN(UPDATED_C_IN);

        restDossierPharmacieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDossierPharmacie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDossierPharmacie))
            )
            .andExpect(status().isOk());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeUpdate);
        DossierPharmacie testDossierPharmacie = dossierPharmacieList.get(dossierPharmacieList.size() - 1);
        assertThat(testDossierPharmacie.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDossierPharmacie.getDateDepot()).isEqualTo(UPDATED_DATE_DEPOT);
        assertThat(testDossierPharmacie.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testDossierPharmacie.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDossierPharmacie.getDateDerniereModif()).isEqualTo(UPDATED_DATE_DERNIERE_MODIF);
        assertThat(testDossierPharmacie.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testDossierPharmacie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDossierPharmacie.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDossierPharmacie.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDossierPharmacie.getcIN()).isEqualTo(UPDATED_C_IN);
    }

    @Test
    @Transactional
    void putNonExistingDossierPharmacie() throws Exception {
        int databaseSizeBeforeUpdate = dossierPharmacieRepository.findAll().size();
        dossierPharmacie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierPharmacieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dossierPharmacie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossierPharmacie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDossierPharmacie() throws Exception {
        int databaseSizeBeforeUpdate = dossierPharmacieRepository.findAll().size();
        dossierPharmacie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierPharmacieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossierPharmacie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDossierPharmacie() throws Exception {
        int databaseSizeBeforeUpdate = dossierPharmacieRepository.findAll().size();
        dossierPharmacie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierPharmacieMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossierPharmacie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDossierPharmacieWithPatch() throws Exception {
        // Initialize the database
        dossierPharmacieRepository.saveAndFlush(dossierPharmacie);

        int databaseSizeBeforeUpdate = dossierPharmacieRepository.findAll().size();

        // Update the dossierPharmacie using partial update
        DossierPharmacie partialUpdatedDossierPharmacie = new DossierPharmacie();
        partialUpdatedDossierPharmacie.setId(dossierPharmacie.getId());

        partialUpdatedDossierPharmacie
            .numero(UPDATED_NUMERO)
            .dateDepot(UPDATED_DATE_DEPOT)
            .nature(UPDATED_NATURE)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .commentaire(UPDATED_COMMENTAIRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .cIN(UPDATED_C_IN);

        restDossierPharmacieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossierPharmacie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossierPharmacie))
            )
            .andExpect(status().isOk());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeUpdate);
        DossierPharmacie testDossierPharmacie = dossierPharmacieList.get(dossierPharmacieList.size() - 1);
        assertThat(testDossierPharmacie.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDossierPharmacie.getDateDepot()).isEqualTo(UPDATED_DATE_DEPOT);
        assertThat(testDossierPharmacie.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testDossierPharmacie.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDossierPharmacie.getDateDerniereModif()).isEqualTo(UPDATED_DATE_DERNIERE_MODIF);
        assertThat(testDossierPharmacie.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testDossierPharmacie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDossierPharmacie.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDossierPharmacie.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDossierPharmacie.getcIN()).isEqualTo(UPDATED_C_IN);
    }

    @Test
    @Transactional
    void fullUpdateDossierPharmacieWithPatch() throws Exception {
        // Initialize the database
        dossierPharmacieRepository.saveAndFlush(dossierPharmacie);

        int databaseSizeBeforeUpdate = dossierPharmacieRepository.findAll().size();

        // Update the dossierPharmacie using partial update
        DossierPharmacie partialUpdatedDossierPharmacie = new DossierPharmacie();
        partialUpdatedDossierPharmacie.setId(dossierPharmacie.getId());

        partialUpdatedDossierPharmacie
            .numero(UPDATED_NUMERO)
            .dateDepot(UPDATED_DATE_DEPOT)
            .nature(UPDATED_NATURE)
            .statut(UPDATED_STATUT)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .commentaire(UPDATED_COMMENTAIRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .cIN(UPDATED_C_IN);

        restDossierPharmacieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossierPharmacie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossierPharmacie))
            )
            .andExpect(status().isOk());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeUpdate);
        DossierPharmacie testDossierPharmacie = dossierPharmacieList.get(dossierPharmacieList.size() - 1);
        assertThat(testDossierPharmacie.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDossierPharmacie.getDateDepot()).isEqualTo(UPDATED_DATE_DEPOT);
        assertThat(testDossierPharmacie.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testDossierPharmacie.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDossierPharmacie.getDateDerniereModif()).isEqualTo(UPDATED_DATE_DERNIERE_MODIF);
        assertThat(testDossierPharmacie.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testDossierPharmacie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDossierPharmacie.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDossierPharmacie.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDossierPharmacie.getcIN()).isEqualTo(UPDATED_C_IN);
    }

    @Test
    @Transactional
    void patchNonExistingDossierPharmacie() throws Exception {
        int databaseSizeBeforeUpdate = dossierPharmacieRepository.findAll().size();
        dossierPharmacie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierPharmacieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dossierPharmacie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossierPharmacie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDossierPharmacie() throws Exception {
        int databaseSizeBeforeUpdate = dossierPharmacieRepository.findAll().size();
        dossierPharmacie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierPharmacieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossierPharmacie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDossierPharmacie() throws Exception {
        int databaseSizeBeforeUpdate = dossierPharmacieRepository.findAll().size();
        dossierPharmacie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierPharmacieMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossierPharmacie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DossierPharmacie in the database
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDossierPharmacie() throws Exception {
        // Initialize the database
        dossierPharmacieRepository.saveAndFlush(dossierPharmacie);

        int databaseSizeBeforeDelete = dossierPharmacieRepository.findAll().size();

        // Delete the dossierPharmacie
        restDossierPharmacieMockMvc
            .perform(delete(ENTITY_API_URL_ID, dossierPharmacie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DossierPharmacie> dossierPharmacieList = dossierPharmacieRepository.findAll();
        assertThat(dossierPharmacieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
