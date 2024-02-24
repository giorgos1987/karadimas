package com.karadimas.tyres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Job;
import com.karadimas.tyres.domain.JobCategory;
import com.karadimas.tyres.repository.JobCategoryRepository;
import com.karadimas.tyres.service.criteria.JobCategoryCriteria;
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
 * Integration tests for the {@link JobCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobCategoryMockMvc;

    private JobCategory jobCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobCategory createEntity(EntityManager em) {
        JobCategory jobCategory = new JobCategory().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return jobCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobCategory createUpdatedEntity(EntityManager em) {
        JobCategory jobCategory = new JobCategory().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return jobCategory;
    }

    @BeforeEach
    public void initTest() {
        jobCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createJobCategory() throws Exception {
        int databaseSizeBeforeCreate = jobCategoryRepository.findAll().size();
        // Create the JobCategory
        restJobCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCategory)))
            .andExpect(status().isCreated());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        JobCategory testJobCategory = jobCategoryList.get(jobCategoryList.size() - 1);
        assertThat(testJobCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createJobCategoryWithExistingId() throws Exception {
        // Create the JobCategory with an existing ID
        jobCategory.setId(1L);

        int databaseSizeBeforeCreate = jobCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCategory)))
            .andExpect(status().isBadRequest());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobCategoryRepository.findAll().size();
        // set the field null
        jobCategory.setName(null);

        // Create the JobCategory, which fails.

        restJobCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCategory)))
            .andExpect(status().isBadRequest());

        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllJobCategories() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList
        restJobCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getJobCategory() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get the jobCategory
        restJobCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, jobCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getJobCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        Long id = jobCategory.getId();

        defaultJobCategoryShouldBeFound("id.equals=" + id);
        defaultJobCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultJobCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJobCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultJobCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJobCategoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllJobCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where name equals to DEFAULT_NAME
        defaultJobCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the jobCategoryList where name equals to UPDATED_NAME
        defaultJobCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJobCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultJobCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the jobCategoryList where name equals to UPDATED_NAME
        defaultJobCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJobCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where name is not null
        defaultJobCategoryShouldBeFound("name.specified=true");

        // Get all the jobCategoryList where name is null
        defaultJobCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllJobCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where name contains DEFAULT_NAME
        defaultJobCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the jobCategoryList where name contains UPDATED_NAME
        defaultJobCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJobCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where name does not contain DEFAULT_NAME
        defaultJobCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the jobCategoryList where name does not contain UPDATED_NAME
        defaultJobCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJobCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultJobCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the jobCategoryList where description equals to UPDATED_DESCRIPTION
        defaultJobCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllJobCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultJobCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the jobCategoryList where description equals to UPDATED_DESCRIPTION
        defaultJobCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllJobCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where description is not null
        defaultJobCategoryShouldBeFound("description.specified=true");

        // Get all the jobCategoryList where description is null
        defaultJobCategoryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllJobCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where description contains DEFAULT_DESCRIPTION
        defaultJobCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the jobCategoryList where description contains UPDATED_DESCRIPTION
        defaultJobCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllJobCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        // Get all the jobCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultJobCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the jobCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultJobCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllJobCategoriesByJobIsEqualToSomething() throws Exception {
        Job job;
        if (TestUtil.findAll(em, Job.class).isEmpty()) {
            jobCategoryRepository.saveAndFlush(jobCategory);
            job = JobResourceIT.createEntity(em);
        } else {
            job = TestUtil.findAll(em, Job.class).get(0);
        }
        em.persist(job);
        em.flush();
        jobCategory.addJob(job);
        jobCategoryRepository.saveAndFlush(jobCategory);
        Long jobId = job.getId();

        // Get all the jobCategoryList where job equals to jobId
        defaultJobCategoryShouldBeFound("jobId.equals=" + jobId);

        // Get all the jobCategoryList where job equals to (jobId + 1)
        defaultJobCategoryShouldNotBeFound("jobId.equals=" + (jobId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJobCategoryShouldBeFound(String filter) throws Exception {
        restJobCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restJobCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJobCategoryShouldNotBeFound(String filter) throws Exception {
        restJobCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingJobCategory() throws Exception {
        // Get the jobCategory
        restJobCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJobCategory() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();

        // Update the jobCategory
        JobCategory updatedJobCategory = jobCategoryRepository.findById(jobCategory.getId()).get();
        // Disconnect from session so that the updates on updatedJobCategory are not directly saved in db
        em.detach(updatedJobCategory);
        updatedJobCategory.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restJobCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJobCategory))
            )
            .andExpect(status().isOk());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeUpdate);
        JobCategory testJobCategory = jobCategoryList.get(jobCategoryList.size() - 1);
        assertThat(testJobCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingJobCategory() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();
        jobCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobCategory() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();
        jobCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobCategory() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();
        jobCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobCategoryWithPatch() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();

        // Update the jobCategory using partial update
        JobCategory partialUpdatedJobCategory = new JobCategory();
        partialUpdatedJobCategory.setId(jobCategory.getId());

        partialUpdatedJobCategory.description(UPDATED_DESCRIPTION);

        restJobCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobCategory))
            )
            .andExpect(status().isOk());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeUpdate);
        JobCategory testJobCategory = jobCategoryList.get(jobCategoryList.size() - 1);
        assertThat(testJobCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateJobCategoryWithPatch() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();

        // Update the jobCategory using partial update
        JobCategory partialUpdatedJobCategory = new JobCategory();
        partialUpdatedJobCategory.setId(jobCategory.getId());

        partialUpdatedJobCategory.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restJobCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobCategory))
            )
            .andExpect(status().isOk());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeUpdate);
        JobCategory testJobCategory = jobCategoryList.get(jobCategoryList.size() - 1);
        assertThat(testJobCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingJobCategory() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();
        jobCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobCategory() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();
        jobCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobCategory() throws Exception {
        int databaseSizeBeforeUpdate = jobCategoryRepository.findAll().size();
        jobCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobCategory in the database
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobCategory() throws Exception {
        // Initialize the database
        jobCategoryRepository.saveAndFlush(jobCategory);

        int databaseSizeBeforeDelete = jobCategoryRepository.findAll().size();

        // Delete the jobCategory
        restJobCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobCategory> jobCategoryList = jobCategoryRepository.findAll();
        assertThat(jobCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
