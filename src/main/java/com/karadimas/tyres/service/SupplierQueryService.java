package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.Supplier;
import com.karadimas.tyres.repository.SupplierRepository;
import com.karadimas.tyres.service.criteria.SupplierCriteria;
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
 * Service for executing complex queries for {@link Supplier} entities in the database.
 * The main input is a {@link SupplierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Supplier} or a {@link Page} of {@link Supplier} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierQueryService extends QueryService<Supplier> {

    private final Logger log = LoggerFactory.getLogger(SupplierQueryService.class);

    private final SupplierRepository supplierRepository;

    public SupplierQueryService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Return a {@link List} of {@link Supplier} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Supplier> findByCriteria(SupplierCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Supplier> specification = createSpecification(criteria);
        return supplierRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Supplier} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Supplier> findByCriteria(SupplierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<Supplier> specification = createSpecification(criteria);
        return supplierRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Supplier> specification = createSpecification(criteria);
        return supplierRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Supplier> createSpecification(SupplierCriteria criteria) {
        Specification<Supplier> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Supplier_.id));
            }
            if (criteria.getCompany() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompany(), Supplier_.company));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Supplier_.lastName));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Supplier_.firstName));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), Supplier_.emailAddress));
            }
            if (criteria.getJobTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobTitle(), Supplier_.jobTitle));
            }
            if (criteria.getBusinessPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBusinessPhone(), Supplier_.businessPhone));
            }
            if (criteria.getHomePhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHomePhone(), Supplier_.homePhone));
            }
            if (criteria.getMobilePhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobilePhone(), Supplier_.mobilePhone));
            }
            if (criteria.getFaxNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFaxNumber(), Supplier_.faxNumber));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Supplier_.city));
            }
            if (criteria.getStateProvince() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateProvince(), Supplier_.stateProvince));
            }
            if (criteria.getZipPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZipPostalCode(), Supplier_.zipPostalCode));
            }
            if (criteria.getCountryRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryRegion(), Supplier_.countryRegion));
            }
            if (criteria.getSupplierpaymentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupplierpaymentsId(),
                            root -> root.join(Supplier_.supplierpayments, JoinType.LEFT).get(Supplierpayments_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
