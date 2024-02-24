package com.karadimas.tyres.web.rest;

import com.karadimas.tyres.domain.JobCategory;
import com.karadimas.tyres.repository.JobCategoryRepository;
import com.karadimas.tyres.service.JobCategoryQueryService;
import com.karadimas.tyres.service.JobCategoryService;
import com.karadimas.tyres.service.criteria.JobCategoryCriteria;
import com.karadimas.tyres.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.karadimas.tyres.domain.JobCategory}.
 */
@RestController
@RequestMapping("/api")
public class JobCategoryResource {

    private final Logger log = LoggerFactory.getLogger(JobCategoryResource.class);

    private static final String ENTITY_NAME = "jobCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobCategoryService jobCategoryService;

    private final JobCategoryRepository jobCategoryRepository;

    private final JobCategoryQueryService jobCategoryQueryService;

    public JobCategoryResource(
        JobCategoryService jobCategoryService,
        JobCategoryRepository jobCategoryRepository,
        JobCategoryQueryService jobCategoryQueryService
    ) {
        this.jobCategoryService = jobCategoryService;
        this.jobCategoryRepository = jobCategoryRepository;
        this.jobCategoryQueryService = jobCategoryQueryService;
    }

    /**
     * {@code POST  /job-categories} : Create a new jobCategory.
     *
     * @param jobCategory the jobCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobCategory, or with status {@code 400 (Bad Request)} if the jobCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-categories")
    public ResponseEntity<JobCategory> createJobCategory(@Valid @RequestBody JobCategory jobCategory) throws URISyntaxException {
        log.debug("REST request to save JobCategory : {}", jobCategory);
        if (jobCategory.getId() != null) {
            throw new BadRequestAlertException("A new jobCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobCategory result = jobCategoryService.save(jobCategory);
        return ResponseEntity
            .created(new URI("/api/job-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-categories/:id} : Updates an existing jobCategory.
     *
     * @param id the id of the jobCategory to save.
     * @param jobCategory the jobCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobCategory,
     * or with status {@code 400 (Bad Request)} if the jobCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-categories/{id}")
    public ResponseEntity<JobCategory> updateJobCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody JobCategory jobCategory
    ) throws URISyntaxException {
        log.debug("REST request to update JobCategory : {}, {}", id, jobCategory);
        if (jobCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobCategory result = jobCategoryService.update(jobCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-categories/:id} : Partial updates given fields of an existing jobCategory, field will ignore if it is null
     *
     * @param id the id of the jobCategory to save.
     * @param jobCategory the jobCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobCategory,
     * or with status {@code 400 (Bad Request)} if the jobCategory is not valid,
     * or with status {@code 404 (Not Found)} if the jobCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobCategory> partialUpdateJobCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody JobCategory jobCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobCategory partially : {}, {}", id, jobCategory);
        if (jobCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobCategory> result = jobCategoryService.partialUpdate(jobCategory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /job-categories} : get all the jobCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobCategories in body.
     */
    @GetMapping("/job-categories")
    public ResponseEntity<List<JobCategory>> getAllJobCategories(
        JobCategoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get JobCategories by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<JobCategory> page = jobCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-categories/count} : count all the jobCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/job-categories/count")
    public ResponseEntity<Long> countJobCategories(JobCategoryCriteria criteria) {
        log.debug("REST request to count JobCategories by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(jobCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /job-categories/:id} : get the "id" jobCategory.
     *
     * @param id the id of the jobCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-categories/{id}")
    public ResponseEntity<JobCategory> getJobCategory(@PathVariable Long id) {
        log.debug("REST request to get JobCategory : {}", id);
        Optional<JobCategory> jobCategory = jobCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobCategory);
    }

    /**
     * {@code DELETE  /job-categories/:id} : delete the "id" jobCategory.
     *
     * @param id the id of the jobCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-categories/{id}")
    public ResponseEntity<Void> deleteJobCategory(@PathVariable Long id) {
        log.debug("REST request to delete JobCategory : {}", id);
        jobCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
