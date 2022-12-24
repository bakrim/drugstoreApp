package com.drugstore.app.web.rest;

import static com.drugstore.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.drugstore.app.IntegrationTest;
import com.drugstore.app.domain.Document;
import com.drugstore.app.repository.DocumentRepository;
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
 * Integration tests for the {@link DocumentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_PATH = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DOWNLOAD_PATH = "AAAAAAAAAA";
    private static final String UPDATED_DOWNLOAD_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_DOWNLOAD_PATH = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_DOWNLOAD_PATH = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPLOAD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPLOAD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_DOCUMENT_SIZE = 1L;
    private static final Long UPDATED_DOCUMENT_SIZE = 2L;

    private static final String ENTITY_API_URL = "/api/documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentMockMvc;

    private Document document;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createEntity(EntityManager em) {
        Document document = new Document()
            .name(DEFAULT_NAME)
            .thumbnailName(DEFAULT_THUMBNAIL_NAME)
            .mimeType(DEFAULT_MIME_TYPE)
            .thumbnailPath(DEFAULT_THUMBNAIL_PATH)
            .downloadPath(DEFAULT_DOWNLOAD_PATH)
            .thumbnailDownloadPath(DEFAULT_THUMBNAIL_DOWNLOAD_PATH)
            .uploadDate(DEFAULT_UPLOAD_DATE)
            .path(DEFAULT_PATH)
            .documentSize(DEFAULT_DOCUMENT_SIZE);
        return document;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Document createUpdatedEntity(EntityManager em) {
        Document document = new Document()
            .name(UPDATED_NAME)
            .thumbnailName(UPDATED_THUMBNAIL_NAME)
            .mimeType(UPDATED_MIME_TYPE)
            .thumbnailPath(UPDATED_THUMBNAIL_PATH)
            .downloadPath(UPDATED_DOWNLOAD_PATH)
            .thumbnailDownloadPath(UPDATED_THUMBNAIL_DOWNLOAD_PATH)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .path(UPDATED_PATH)
            .documentSize(UPDATED_DOCUMENT_SIZE);
        return document;
    }

    @BeforeEach
    public void initTest() {
        document = createEntity(em);
    }

    @Test
    @Transactional
    void createDocument() throws Exception {
        int databaseSizeBeforeCreate = documentRepository.findAll().size();
        // Create the Document
        restDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isCreated());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate + 1);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocument.getThumbnailName()).isEqualTo(DEFAULT_THUMBNAIL_NAME);
        assertThat(testDocument.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testDocument.getThumbnailPath()).isEqualTo(DEFAULT_THUMBNAIL_PATH);
        assertThat(testDocument.getDownloadPath()).isEqualTo(DEFAULT_DOWNLOAD_PATH);
        assertThat(testDocument.getThumbnailDownloadPath()).isEqualTo(DEFAULT_THUMBNAIL_DOWNLOAD_PATH);
        assertThat(testDocument.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
        assertThat(testDocument.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testDocument.getDocumentSize()).isEqualTo(DEFAULT_DOCUMENT_SIZE);
    }

    @Test
    @Transactional
    void createDocumentWithExistingId() throws Exception {
        // Create the Document with an existing ID
        document.setId(1L);

        int databaseSizeBeforeCreate = documentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocuments() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get all the documentList
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(document.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].thumbnailName").value(hasItem(DEFAULT_THUMBNAIL_NAME)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)))
            .andExpect(jsonPath("$.[*].thumbnailPath").value(hasItem(DEFAULT_THUMBNAIL_PATH)))
            .andExpect(jsonPath("$.[*].downloadPath").value(hasItem(DEFAULT_DOWNLOAD_PATH)))
            .andExpect(jsonPath("$.[*].thumbnailDownloadPath").value(hasItem(DEFAULT_THUMBNAIL_DOWNLOAD_PATH)))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(sameInstant(DEFAULT_UPLOAD_DATE))))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].documentSize").value(hasItem(DEFAULT_DOCUMENT_SIZE.intValue())));
    }

    @Test
    @Transactional
    void getDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        // Get the document
        restDocumentMockMvc
            .perform(get(ENTITY_API_URL_ID, document.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(document.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.thumbnailName").value(DEFAULT_THUMBNAIL_NAME))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE))
            .andExpect(jsonPath("$.thumbnailPath").value(DEFAULT_THUMBNAIL_PATH))
            .andExpect(jsonPath("$.downloadPath").value(DEFAULT_DOWNLOAD_PATH))
            .andExpect(jsonPath("$.thumbnailDownloadPath").value(DEFAULT_THUMBNAIL_DOWNLOAD_PATH))
            .andExpect(jsonPath("$.uploadDate").value(sameInstant(DEFAULT_UPLOAD_DATE)))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.documentSize").value(DEFAULT_DOCUMENT_SIZE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDocument() throws Exception {
        // Get the document
        restDocumentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document
        Document updatedDocument = documentRepository.findById(document.getId()).get();
        // Disconnect from session so that the updates on updatedDocument are not directly saved in db
        em.detach(updatedDocument);
        updatedDocument
            .name(UPDATED_NAME)
            .thumbnailName(UPDATED_THUMBNAIL_NAME)
            .mimeType(UPDATED_MIME_TYPE)
            .thumbnailPath(UPDATED_THUMBNAIL_PATH)
            .downloadPath(UPDATED_DOWNLOAD_PATH)
            .thumbnailDownloadPath(UPDATED_THUMBNAIL_DOWNLOAD_PATH)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .path(UPDATED_PATH)
            .documentSize(UPDATED_DOCUMENT_SIZE);

        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocument.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocument.getThumbnailName()).isEqualTo(UPDATED_THUMBNAIL_NAME);
        assertThat(testDocument.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testDocument.getThumbnailPath()).isEqualTo(UPDATED_THUMBNAIL_PATH);
        assertThat(testDocument.getDownloadPath()).isEqualTo(UPDATED_DOWNLOAD_PATH);
        assertThat(testDocument.getThumbnailDownloadPath()).isEqualTo(UPDATED_THUMBNAIL_DOWNLOAD_PATH);
        assertThat(testDocument.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testDocument.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testDocument.getDocumentSize()).isEqualTo(UPDATED_DOCUMENT_SIZE);
    }

    @Test
    @Transactional
    void putNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, document.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument.name(UPDATED_NAME).thumbnailName(UPDATED_THUMBNAIL_NAME).path(UPDATED_PATH);

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocument.getThumbnailName()).isEqualTo(UPDATED_THUMBNAIL_NAME);
        assertThat(testDocument.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testDocument.getThumbnailPath()).isEqualTo(DEFAULT_THUMBNAIL_PATH);
        assertThat(testDocument.getDownloadPath()).isEqualTo(DEFAULT_DOWNLOAD_PATH);
        assertThat(testDocument.getThumbnailDownloadPath()).isEqualTo(DEFAULT_THUMBNAIL_DOWNLOAD_PATH);
        assertThat(testDocument.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
        assertThat(testDocument.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testDocument.getDocumentSize()).isEqualTo(DEFAULT_DOCUMENT_SIZE);
    }

    @Test
    @Transactional
    void fullUpdateDocumentWithPatch() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeUpdate = documentRepository.findAll().size();

        // Update the document using partial update
        Document partialUpdatedDocument = new Document();
        partialUpdatedDocument.setId(document.getId());

        partialUpdatedDocument
            .name(UPDATED_NAME)
            .thumbnailName(UPDATED_THUMBNAIL_NAME)
            .mimeType(UPDATED_MIME_TYPE)
            .thumbnailPath(UPDATED_THUMBNAIL_PATH)
            .downloadPath(UPDATED_DOWNLOAD_PATH)
            .thumbnailDownloadPath(UPDATED_THUMBNAIL_DOWNLOAD_PATH)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .path(UPDATED_PATH)
            .documentSize(UPDATED_DOCUMENT_SIZE);

        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocument.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocument))
            )
            .andExpect(status().isOk());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
        Document testDocument = documentList.get(documentList.size() - 1);
        assertThat(testDocument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocument.getThumbnailName()).isEqualTo(UPDATED_THUMBNAIL_NAME);
        assertThat(testDocument.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testDocument.getThumbnailPath()).isEqualTo(UPDATED_THUMBNAIL_PATH);
        assertThat(testDocument.getDownloadPath()).isEqualTo(UPDATED_DOWNLOAD_PATH);
        assertThat(testDocument.getThumbnailDownloadPath()).isEqualTo(UPDATED_THUMBNAIL_DOWNLOAD_PATH);
        assertThat(testDocument.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testDocument.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testDocument.getDocumentSize()).isEqualTo(UPDATED_DOCUMENT_SIZE);
    }

    @Test
    @Transactional
    void patchNonExistingDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, document.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(document))
            )
            .andExpect(status().isBadRequest());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocument() throws Exception {
        int databaseSizeBeforeUpdate = documentRepository.findAll().size();
        document.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(document)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Document in the database
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocument() throws Exception {
        // Initialize the database
        documentRepository.saveAndFlush(document);

        int databaseSizeBeforeDelete = documentRepository.findAll().size();

        // Delete the document
        restDocumentMockMvc
            .perform(delete(ENTITY_API_URL_ID, document.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Document> documentList = documentRepository.findAll();
        assertThat(documentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
