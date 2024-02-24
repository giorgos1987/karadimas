package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.JobCategory;
import com.karadimas.tyres.repository.JobCategoryRepository;
import com.karadimas.tyres.service.criteria.JobCategoryCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link JobCategory} entities in the database.
 * The main input is a {@link JobCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobCategory} or a {@link Page} of {@link JobCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobCategoryQueryService extends QueryService<JobCategory> {

    private final Logger log = LoggerFactory.getLogger(JobCategoryQueryService.class);

    private final JobCategoryRepository jobCategoryRepository;

    public JobCategoryQueryService(JobCategoryRepository jobCategoryRepository) {
        this.jobCategoryRepository = jobCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link JobCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobCategory> findByCriteria(JobCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<JobCategory> specification = createSpecification(criteria);
        return jobCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link JobCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobCategory> findByCriteria(JobCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<JobCategory> specification = createSpecification(criteria);
        return jobCategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<JobCategory> specification = createSpecification(criteria);
        return jobCategoryRepository.count(specification);
    }

    /**
     * Function to convert {@link JobCategoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<JobCategory> createSpecification(JobCategoryCriteria criteria) {
        Specification<JobCategory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), JobCategory_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), JobCategory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), JobCategory_.description));
            }
            if (criteria.getJobId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getJobId(), root -> root.join(JobCategory_.jobs, JoinType.LEFT).get(Job_.id))
                    );
            }
        }
        return specification;
    }
}
