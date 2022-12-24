package com.drugstore.app.web.rest;

import static com.drugstore.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.drugstore.app.IntegrationTest;
import com.drugstore.app.domain.DossierAutre;
import com.drugstore.app.repository.DossierAutreRepository;
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
 * Integration tests for the {@link DossierAutreResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DossierAutreResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_ENVOI = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_ENVOI = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_DEPOT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEPOT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_ENVOI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ENVOI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TYPE_DEMENDEUR = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_DEMENDEUR = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

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

    private static final String DEFAULT_RAISON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_I_CE = "AAAAAAAAAA";
    private static final String UPDATED_I_CE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_RC = "AAAAAAAAAA";
    private static final String UPDATED_NUM_RC = "BBBBBBBBBB";

    private static final String DEFAULT_I_F = "AAAAAAAAAA";
    private static final String UPDATED_I_F = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_AFFILIATION = "AAAAAAAAAA";
    private static final String UPDATED_NUM_AFFILIATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dossier-autres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DossierAutreRepository dossierAutreRepository;

    @Mock
    private DossierAutreRepository dossierAutreRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDossierAutreMockMvc;

    private DossierAutre dossierAutre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DossierAutre createEntity(EntityManager em) {
        DossierAutre dossierAutre = new DossierAutre()
            .numero(DEFAULT_NUMERO)
            .numeroEnvoi(DEFAULT_NUMERO_ENVOI)
            .dateDepot(DEFAULT_DATE_DEPOT)
            .dateEnvoi(DEFAULT_DATE_ENVOI)
            .typeDemendeur(DEFAULT_TYPE_DEMENDEUR)
            .profession(DEFAULT_PROFESSION)
            .nature(DEFAULT_NATURE)
            .statut(DEFAULT_STATUT)
            .dateDerniereModif(DEFAULT_DATE_DERNIERE_MODIF)
            .commentaire(DEFAULT_COMMENTAIRE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .cIN(DEFAULT_C_IN)
            .raisonSocial(DEFAULT_RAISON_SOCIAL)
            .iCE(DEFAULT_I_CE)
            .numRC(DEFAULT_NUM_RC)
            .iF(DEFAULT_I_F)
            .numAffiliation(DEFAULT_NUM_AFFILIATION);
        return dossierAutre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DossierAutre createUpdatedEntity(EntityManager em) {
        DossierAutre dossierAutre = new DossierAutre()
            .numero(UPDATED_NUMERO)
            .numeroEnvoi(UPDATED_NUMERO_ENVOI)
            .dateDepot(UPDATED_DATE_DEPOT)
            .dateEnvoi(UPDATED_DATE_ENVOI)
            .typeDemendeur(UPDATED_TYPE_DEMENDEUR)
            .profession(UPDATED_PROFESSION)
            .nature(UPDATED_NATURE)
            .statut(UPDATED_STATUT)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .commentaire(UPDATED_COMMENTAIRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .cIN(UPDATED_C_IN)
            .raisonSocial(UPDATED_RAISON_SOCIAL)
            .iCE(UPDATED_I_CE)
            .numRC(UPDATED_NUM_RC)
            .iF(UPDATED_I_F)
            .numAffiliation(UPDATED_NUM_AFFILIATION);
        return dossierAutre;
    }

    @BeforeEach
    public void initTest() {
        dossierAutre = createEntity(em);
    }

    @Test
    @Transactional
    void createDossierAutre() throws Exception {
        int databaseSizeBeforeCreate = dossierAutreRepository.findAll().size();
        // Create the DossierAutre
        restDossierAutreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossierAutre)))
            .andExpect(status().isCreated());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeCreate + 1);
        DossierAutre testDossierAutre = dossierAutreList.get(dossierAutreList.size() - 1);
        assertThat(testDossierAutre.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDossierAutre.getNumeroEnvoi()).isEqualTo(DEFAULT_NUMERO_ENVOI);
        assertThat(testDossierAutre.getDateDepot()).isEqualTo(DEFAULT_DATE_DEPOT);
        assertThat(testDossierAutre.getDateEnvoi()).isEqualTo(DEFAULT_DATE_ENVOI);
        assertThat(testDossierAutre.getTypeDemendeur()).isEqualTo(DEFAULT_TYPE_DEMENDEUR);
        assertThat(testDossierAutre.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testDossierAutre.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testDossierAutre.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testDossierAutre.getDateDerniereModif()).isEqualTo(DEFAULT_DATE_DERNIERE_MODIF);
        assertThat(testDossierAutre.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testDossierAutre.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDossierAutre.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDossierAutre.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDossierAutre.getcIN()).isEqualTo(DEFAULT_C_IN);
        assertThat(testDossierAutre.getRaisonSocial()).isEqualTo(DEFAULT_RAISON_SOCIAL);
        assertThat(testDossierAutre.getiCE()).isEqualTo(DEFAULT_I_CE);
        assertThat(testDossierAutre.getNumRC()).isEqualTo(DEFAULT_NUM_RC);
        assertThat(testDossierAutre.getiF()).isEqualTo(DEFAULT_I_F);
        assertThat(testDossierAutre.getNumAffiliation()).isEqualTo(DEFAULT_NUM_AFFILIATION);
    }

    @Test
    @Transactional
    void createDossierAutreWithExistingId() throws Exception {
        // Create the DossierAutre with an existing ID
        dossierAutre.setId(1L);

        int databaseSizeBeforeCreate = dossierAutreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDossierAutreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossierAutre)))
            .andExpect(status().isBadRequest());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDossierAutres() throws Exception {
        // Initialize the database
        dossierAutreRepository.saveAndFlush(dossierAutre);

        // Get all the dossierAutreList
        restDossierAutreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dossierAutre.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].numeroEnvoi").value(hasItem(DEFAULT_NUMERO_ENVOI)))
            .andExpect(jsonPath("$.[*].dateDepot").value(hasItem(sameInstant(DEFAULT_DATE_DEPOT))))
            .andExpect(jsonPath("$.[*].dateEnvoi").value(hasItem(sameInstant(DEFAULT_DATE_ENVOI))))
            .andExpect(jsonPath("$.[*].typeDemendeur").value(hasItem(DEFAULT_TYPE_DEMENDEUR)))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].dateDerniereModif").value(hasItem(sameInstant(DEFAULT_DATE_DERNIERE_MODIF))))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].cIN").value(hasItem(DEFAULT_C_IN)))
            .andExpect(jsonPath("$.[*].raisonSocial").value(hasItem(DEFAULT_RAISON_SOCIAL)))
            .andExpect(jsonPath("$.[*].iCE").value(hasItem(DEFAULT_I_CE)))
            .andExpect(jsonPath("$.[*].numRC").value(hasItem(DEFAULT_NUM_RC)))
            .andExpect(jsonPath("$.[*].iF").value(hasItem(DEFAULT_I_F)))
            .andExpect(jsonPath("$.[*].numAffiliation").value(hasItem(DEFAULT_NUM_AFFILIATION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDossierAutresWithEagerRelationshipsIsEnabled() throws Exception {
        when(dossierAutreRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDossierAutreMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dossierAutreRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDossierAutresWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dossierAutreRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDossierAutreMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dossierAutreRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDossierAutre() throws Exception {
        // Initialize the database
        dossierAutreRepository.saveAndFlush(dossierAutre);

        // Get the dossierAutre
        restDossierAutreMockMvc
            .perform(get(ENTITY_API_URL_ID, dossierAutre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dossierAutre.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.numeroEnvoi").value(DEFAULT_NUMERO_ENVOI))
            .andExpect(jsonPath("$.dateDepot").value(sameInstant(DEFAULT_DATE_DEPOT)))
            .andExpect(jsonPath("$.dateEnvoi").value(sameInstant(DEFAULT_DATE_ENVOI)))
            .andExpect(jsonPath("$.typeDemendeur").value(DEFAULT_TYPE_DEMENDEUR))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.dateDerniereModif").value(sameInstant(DEFAULT_DATE_DERNIERE_MODIF)))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.cIN").value(DEFAULT_C_IN))
            .andExpect(jsonPath("$.raisonSocial").value(DEFAULT_RAISON_SOCIAL))
            .andExpect(jsonPath("$.iCE").value(DEFAULT_I_CE))
            .andExpect(jsonPath("$.numRC").value(DEFAULT_NUM_RC))
            .andExpect(jsonPath("$.iF").value(DEFAULT_I_F))
            .andExpect(jsonPath("$.numAffiliation").value(DEFAULT_NUM_AFFILIATION));
    }

    @Test
    @Transactional
    void getNonExistingDossierAutre() throws Exception {
        // Get the dossierAutre
        restDossierAutreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDossierAutre() throws Exception {
        // Initialize the database
        dossierAutreRepository.saveAndFlush(dossierAutre);

        int databaseSizeBeforeUpdate = dossierAutreRepository.findAll().size();

        // Update the dossierAutre
        DossierAutre updatedDossierAutre = dossierAutreRepository.findById(dossierAutre.getId()).get();
        // Disconnect from session so that the updates on updatedDossierAutre are not directly saved in db
        em.detach(updatedDossierAutre);
        updatedDossierAutre
            .numero(UPDATED_NUMERO)
            .numeroEnvoi(UPDATED_NUMERO_ENVOI)
            .dateDepot(UPDATED_DATE_DEPOT)
            .dateEnvoi(UPDATED_DATE_ENVOI)
            .typeDemendeur(UPDATED_TYPE_DEMENDEUR)
            .profession(UPDATED_PROFESSION)
            .nature(UPDATED_NATURE)
            .statut(UPDATED_STATUT)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .commentaire(UPDATED_COMMENTAIRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .cIN(UPDATED_C_IN)
            .raisonSocial(UPDATED_RAISON_SOCIAL)
            .iCE(UPDATED_I_CE)
            .numRC(UPDATED_NUM_RC)
            .iF(UPDATED_I_F)
            .numAffiliation(UPDATED_NUM_AFFILIATION);

        restDossierAutreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDossierAutre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDossierAutre))
            )
            .andExpect(status().isOk());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeUpdate);
        DossierAutre testDossierAutre = dossierAutreList.get(dossierAutreList.size() - 1);
        assertThat(testDossierAutre.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDossierAutre.getNumeroEnvoi()).isEqualTo(UPDATED_NUMERO_ENVOI);
        assertThat(testDossierAutre.getDateDepot()).isEqualTo(UPDATED_DATE_DEPOT);
        assertThat(testDossierAutre.getDateEnvoi()).isEqualTo(UPDATED_DATE_ENVOI);
        assertThat(testDossierAutre.getTypeDemendeur()).isEqualTo(UPDATED_TYPE_DEMENDEUR);
        assertThat(testDossierAutre.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testDossierAutre.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testDossierAutre.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDossierAutre.getDateDerniereModif()).isEqualTo(UPDATED_DATE_DERNIERE_MODIF);
        assertThat(testDossierAutre.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testDossierAutre.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDossierAutre.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDossierAutre.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDossierAutre.getcIN()).isEqualTo(UPDATED_C_IN);
        assertThat(testDossierAutre.getRaisonSocial()).isEqualTo(UPDATED_RAISON_SOCIAL);
        assertThat(testDossierAutre.getiCE()).isEqualTo(UPDATED_I_CE);
        assertThat(testDossierAutre.getNumRC()).isEqualTo(UPDATED_NUM_RC);
        assertThat(testDossierAutre.getiF()).isEqualTo(UPDATED_I_F);
        assertThat(testDossierAutre.getNumAffiliation()).isEqualTo(UPDATED_NUM_AFFILIATION);
    }

    @Test
    @Transactional
    void putNonExistingDossierAutre() throws Exception {
        int databaseSizeBeforeUpdate = dossierAutreRepository.findAll().size();
        dossierAutre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierAutreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dossierAutre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossierAutre))
            )
            .andExpect(status().isBadRequest());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDossierAutre() throws Exception {
        int databaseSizeBeforeUpdate = dossierAutreRepository.findAll().size();
        dossierAutre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierAutreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dossierAutre))
            )
            .andExpect(status().isBadRequest());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDossierAutre() throws Exception {
        int databaseSizeBeforeUpdate = dossierAutreRepository.findAll().size();
        dossierAutre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierAutreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dossierAutre)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDossierAutreWithPatch() throws Exception {
        // Initialize the database
        dossierAutreRepository.saveAndFlush(dossierAutre);

        int databaseSizeBeforeUpdate = dossierAutreRepository.findAll().size();

        // Update the dossierAutre using partial update
        DossierAutre partialUpdatedDossierAutre = new DossierAutre();
        partialUpdatedDossierAutre.setId(dossierAutre.getId());

        partialUpdatedDossierAutre
            .dateDepot(UPDATED_DATE_DEPOT)
            .typeDemendeur(UPDATED_TYPE_DEMENDEUR)
            .statut(UPDATED_STATUT)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .prenom(UPDATED_PRENOM)
            .cIN(UPDATED_C_IN)
            .iCE(UPDATED_I_CE)
            .numRC(UPDATED_NUM_RC)
            .iF(UPDATED_I_F);

        restDossierAutreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossierAutre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossierAutre))
            )
            .andExpect(status().isOk());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeUpdate);
        DossierAutre testDossierAutre = dossierAutreList.get(dossierAutreList.size() - 1);
        assertThat(testDossierAutre.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDossierAutre.getNumeroEnvoi()).isEqualTo(DEFAULT_NUMERO_ENVOI);
        assertThat(testDossierAutre.getDateDepot()).isEqualTo(UPDATED_DATE_DEPOT);
        assertThat(testDossierAutre.getDateEnvoi()).isEqualTo(DEFAULT_DATE_ENVOI);
        assertThat(testDossierAutre.getTypeDemendeur()).isEqualTo(UPDATED_TYPE_DEMENDEUR);
        assertThat(testDossierAutre.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testDossierAutre.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testDossierAutre.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDossierAutre.getDateDerniereModif()).isEqualTo(UPDATED_DATE_DERNIERE_MODIF);
        assertThat(testDossierAutre.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testDossierAutre.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDossierAutre.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDossierAutre.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDossierAutre.getcIN()).isEqualTo(UPDATED_C_IN);
        assertThat(testDossierAutre.getRaisonSocial()).isEqualTo(DEFAULT_RAISON_SOCIAL);
        assertThat(testDossierAutre.getiCE()).isEqualTo(UPDATED_I_CE);
        assertThat(testDossierAutre.getNumRC()).isEqualTo(UPDATED_NUM_RC);
        assertThat(testDossierAutre.getiF()).isEqualTo(UPDATED_I_F);
        assertThat(testDossierAutre.getNumAffiliation()).isEqualTo(DEFAULT_NUM_AFFILIATION);
    }

    @Test
    @Transactional
    void fullUpdateDossierAutreWithPatch() throws Exception {
        // Initialize the database
        dossierAutreRepository.saveAndFlush(dossierAutre);

        int databaseSizeBeforeUpdate = dossierAutreRepository.findAll().size();

        // Update the dossierAutre using partial update
        DossierAutre partialUpdatedDossierAutre = new DossierAutre();
        partialUpdatedDossierAutre.setId(dossierAutre.getId());

        partialUpdatedDossierAutre
            .numero(UPDATED_NUMERO)
            .numeroEnvoi(UPDATED_NUMERO_ENVOI)
            .dateDepot(UPDATED_DATE_DEPOT)
            .dateEnvoi(UPDATED_DATE_ENVOI)
            .typeDemendeur(UPDATED_TYPE_DEMENDEUR)
            .profession(UPDATED_PROFESSION)
            .nature(UPDATED_NATURE)
            .statut(UPDATED_STATUT)
            .dateDerniereModif(UPDATED_DATE_DERNIERE_MODIF)
            .commentaire(UPDATED_COMMENTAIRE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .cIN(UPDATED_C_IN)
            .raisonSocial(UPDATED_RAISON_SOCIAL)
            .iCE(UPDATED_I_CE)
            .numRC(UPDATED_NUM_RC)
            .iF(UPDATED_I_F)
            .numAffiliation(UPDATED_NUM_AFFILIATION);

        restDossierAutreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDossierAutre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDossierAutre))
            )
            .andExpect(status().isOk());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeUpdate);
        DossierAutre testDossierAutre = dossierAutreList.get(dossierAutreList.size() - 1);
        assertThat(testDossierAutre.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDossierAutre.getNumeroEnvoi()).isEqualTo(UPDATED_NUMERO_ENVOI);
        assertThat(testDossierAutre.getDateDepot()).isEqualTo(UPDATED_DATE_DEPOT);
        assertThat(testDossierAutre.getDateEnvoi()).isEqualTo(UPDATED_DATE_ENVOI);
        assertThat(testDossierAutre.getTypeDemendeur()).isEqualTo(UPDATED_TYPE_DEMENDEUR);
        assertThat(testDossierAutre.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testDossierAutre.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testDossierAutre.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testDossierAutre.getDateDerniereModif()).isEqualTo(UPDATED_DATE_DERNIERE_MODIF);
        assertThat(testDossierAutre.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testDossierAutre.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDossierAutre.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDossierAutre.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDossierAutre.getcIN()).isEqualTo(UPDATED_C_IN);
        assertThat(testDossierAutre.getRaisonSocial()).isEqualTo(UPDATED_RAISON_SOCIAL);
        assertThat(testDossierAutre.getiCE()).isEqualTo(UPDATED_I_CE);
        assertThat(testDossierAutre.getNumRC()).isEqualTo(UPDATED_NUM_RC);
        assertThat(testDossierAutre.getiF()).isEqualTo(UPDATED_I_F);
        assertThat(testDossierAutre.getNumAffiliation()).isEqualTo(UPDATED_NUM_AFFILIATION);
    }

    @Test
    @Transactional
    void patchNonExistingDossierAutre() throws Exception {
        int databaseSizeBeforeUpdate = dossierAutreRepository.findAll().size();
        dossierAutre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDossierAutreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dossierAutre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossierAutre))
            )
            .andExpect(status().isBadRequest());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDossierAutre() throws Exception {
        int databaseSizeBeforeUpdate = dossierAutreRepository.findAll().size();
        dossierAutre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierAutreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dossierAutre))
            )
            .andExpect(status().isBadRequest());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDossierAutre() throws Exception {
        int databaseSizeBeforeUpdate = dossierAutreRepository.findAll().size();
        dossierAutre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDossierAutreMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dossierAutre))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DossierAutre in the database
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDossierAutre() throws Exception {
        // Initialize the database
        dossierAutreRepository.saveAndFlush(dossierAutre);

        int databaseSizeBeforeDelete = dossierAutreRepository.findAll().size();

        // Delete the dossierAutre
        restDossierAutreMockMvc
            .perform(delete(ENTITY_API_URL_ID, dossierAutre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DossierAutre> dossierAutreList = dossierAutreRepository.findAll();
        assertThat(dossierAutreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
