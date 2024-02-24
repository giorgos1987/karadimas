package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.JobCategory;
import com.karadimas.tyres.repository.JobCategoryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobCategory}.
 */
@Service
@Transactional
public class JobCategoryService {

    private final Logger log = LoggerFactory.getLogger(JobCategoryService.class);

    private final JobCategoryRepository jobCategoryRepository;

    public JobCategoryService(JobCategoryRepository jobCategoryRepository) {
        this.jobCategoryRepository = jobCategoryRepository;
    }

    /**
     * Save a jobCategory.
     *
     * @param jobCategory the entity to save.
     * @return the persisted entity.
     */
    public JobCategory save(JobCategory jobCategory) {
        log.debug("Request to save JobCategory : {}", jobCategory);
        return jobCategoryRepository.save(jobCategory);
    }

    /**
     * Update a jobCategory.
     *
     * @param jobCategory the entity to save.
     * @return the persisted entity.
     */
    public JobCategory update(JobCategory jobCategory) {
        log.debug("Request to save JobCategory : {}", jobCategory);
        return jobCategoryRepository.save(jobCategory);
    }

    /**
     * Partially update a jobCategory.
     *
     * @param jobCategory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobCategory> partialUpdate(JobCategory jobCategory) {
        log.debug("Request to partially update JobCategory : {}", jobCategory);

        return jobCategoryRepository
            .findById(jobCategory.getId())
            .map(existingJobCategory -> {
                if (jobCategory.getName() != null) {
                    existingJobCategory.setName(jobCategory.getName());
                }
                if (jobCategory.getDescription() != null) {
                    existingJobCategory.setDescription(jobCategory.getDescription());
                }

                return existingJobCategory;
            })
            .map(jobCategoryRepository::save);
    }

    /**
     * Get all the jobCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobCategory> findAll(Pageable pageable) {
        log.debug("Request to get all JobCategories");
        return jobCategoryRepository.findAll(pageable);
    }

    /**
     * Get one jobCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobCategory> findOne(Long id) {
        log.debug("Request to get JobCategory : {}", id);
        return jobCategoryRepository.findById(id);
    }

    /**
     * Delete the jobCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobCategory : {}", id);
        jobCategoryRepository.deleteById(id);
    }
}
