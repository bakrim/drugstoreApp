package com.drugstore.app.web.rest;

import static com.drugstore.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.drugstore.app.IntegrationTest;
import com.drugstore.app.domain.Session;
import com.drugstore.app.repository.SessionRepository;
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
 * Integration tests for the {@link SessionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SessionResourceIT {

    private static final String DEFAULT_ADRESSE_IP = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_IP = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_CONNECT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CONNECT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_DECONNECT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DECONNECT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/sessions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSessionMockMvc;

    private Session session;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Session createEntity(EntityManager em) {
        Session session = new Session()
            .adresseIp(DEFAULT_ADRESSE_IP)
            .dateConnect(DEFAULT_DATE_CONNECT)
            .dateDeconnect(DEFAULT_DATE_DECONNECT);
        return session;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Session createUpdatedEntity(EntityManager em) {
        Session session = new Session()
            .adresseIp(UPDATED_ADRESSE_IP)
            .dateConnect(UPDATED_DATE_CONNECT)
            .dateDeconnect(UPDATED_DATE_DECONNECT);
        return session;
    }

    @BeforeEach
    public void initTest() {
        session = createEntity(em);
    }

    @Test
    @Transactional
    void createSession() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();
        // Create the Session
        restSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isCreated());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate + 1);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getAdresseIp()).isEqualTo(DEFAULT_ADRESSE_IP);
        assertThat(testSession.getDateConnect()).isEqualTo(DEFAULT_DATE_CONNECT);
        assertThat(testSession.getDateDeconnect()).isEqualTo(DEFAULT_DATE_DECONNECT);
    }

    @Test
    @Transactional
    void createSessionWithExistingId() throws Exception {
        // Create the Session with an existing ID
        session.setId(1L);

        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSessions() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get all the sessionList
        restSessionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresseIp").value(hasItem(DEFAULT_ADRESSE_IP)))
            .andExpect(jsonPath("$.[*].dateConnect").value(hasItem(sameInstant(DEFAULT_DATE_CONNECT))))
            .andExpect(jsonPath("$.[*].dateDeconnect").value(hasItem(sameInstant(DEFAULT_DATE_DECONNECT))));
    }

    @Test
    @Transactional
    void getSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get the session
        restSessionMockMvc
            .perform(get(ENTITY_API_URL_ID, session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(session.getId().intValue()))
            .andExpect(jsonPath("$.adresseIp").value(DEFAULT_ADRESSE_IP))
            .andExpect(jsonPath("$.dateConnect").value(sameInstant(DEFAULT_DATE_CONNECT)))
            .andExpect(jsonPath("$.dateDeconnect").value(sameInstant(DEFAULT_DATE_DECONNECT)));
    }

    @Test
    @Transactional
    void getNonExistingSession() throws Exception {
        // Get the session
        restSessionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session
        Session updatedSession = sessionRepository.findById(session.getId()).get();
        // Disconnect from session so that the updates on updatedSession are not directly saved in db
        em.detach(updatedSession);
        updatedSession.adresseIp(UPDATED_ADRESSE_IP).dateConnect(UPDATED_DATE_CONNECT).dateDeconnect(UPDATED_DATE_DECONNECT);

        restSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSession.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSession))
            )
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getAdresseIp()).isEqualTo(UPDATED_ADRESSE_IP);
        assertThat(testSession.getDateConnect()).isEqualTo(UPDATED_DATE_CONNECT);
        assertThat(testSession.getDateDeconnect()).isEqualTo(UPDATED_DATE_DECONNECT);
    }

    @Test
    @Transactional
    void putNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, session.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(session))
            )
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(session))
            )
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSessionWithPatch() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session using partial update
        Session partialUpdatedSession = new Session();
        partialUpdatedSession.setId(session.getId());

        partialUpdatedSession.dateConnect(UPDATED_DATE_CONNECT).dateDeconnect(UPDATED_DATE_DECONNECT);

        restSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSession))
            )
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getAdresseIp()).isEqualTo(DEFAULT_ADRESSE_IP);
        assertThat(testSession.getDateConnect()).isEqualTo(UPDATED_DATE_CONNECT);
        assertThat(testSession.getDateDeconnect()).isEqualTo(UPDATED_DATE_DECONNECT);
    }

    @Test
    @Transactional
    void fullUpdateSessionWithPatch() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session using partial update
        Session partialUpdatedSession = new Session();
        partialUpdatedSession.setId(session.getId());

        partialUpdatedSession.adresseIp(UPDATED_ADRESSE_IP).dateConnect(UPDATED_DATE_CONNECT).dateDeconnect(UPDATED_DATE_DECONNECT);

        restSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSession.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSession))
            )
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getAdresseIp()).isEqualTo(UPDATED_ADRESSE_IP);
        assertThat(testSession.getDateConnect()).isEqualTo(UPDATED_DATE_CONNECT);
        assertThat(testSession.getDateDeconnect()).isEqualTo(UPDATED_DATE_DECONNECT);
    }

    @Test
    @Transactional
    void patchNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, session.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(session))
            )
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(session))
            )
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();
        session.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeDelete = sessionRepository.findAll().size();

        // Delete the session
        restSessionMockMvc
            .perform(delete(ENTITY_API_URL_ID, session.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
