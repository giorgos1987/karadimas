package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.CompanyProfile;
import com.karadimas.tyres.repository.CompanyProfileRepository;
import com.karadimas.tyres.service.criteria.CompanyProfileCriteria;
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
 * Service for executing complex queries for {@link CompanyProfile} entities in the database.
 * The main input is a {@link CompanyProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyProfile} or a {@link Page} of {@link CompanyProfile} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyProfileQueryService extends QueryService<CompanyProfile> {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileQueryService.class);

    private final CompanyProfileRepository companyProfileRepository;

    public CompanyProfileQueryService(CompanyProfileRepository companyProfileRepository) {
        this.companyProfileRepository = companyProfileRepository;
    }

    /**
     * Return a {@link List} of {@link CompanyProfile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyProfile> findByCriteria(CompanyProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<CompanyProfile> specification = createSpecification(criteria);
        return companyProfileRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CompanyProfile} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyProfile> findByCriteria(CompanyProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<CompanyProfile> specification = createSpecification(criteria);
        return companyProfileRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<CompanyProfile> specification = createSpecification(criteria);
        return companyProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompanyProfile> createSpecification(CompanyProfileCriteria criteria) {
        Specification<CompanyProfile> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompanyProfile_.id));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), CompanyProfile_.companyName));
            }
            if (criteria.getAddress1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress1(), CompanyProfile_.address1));
            }
            if (criteria.getAddress2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress2(), CompanyProfile_.address2));
            }
            if (criteria.getSuburb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuburb(), CompanyProfile_.suburb));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), CompanyProfile_.state));
            }
            if (criteria.getPostcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostcode(), CompanyProfile_.postcode));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), CompanyProfile_.phone));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), CompanyProfile_.mobile));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), CompanyProfile_.email));
            }
            if (criteria.getEmailInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailInfo(), CompanyProfile_.emailInfo));
            }
            if (criteria.getLanguage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguage(), CompanyProfile_.language));
            }
        }
        return specification;
    }
}
