package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.Supplierpayments;
import com.karadimas.tyres.repository.SupplierpaymentsRepository;
import com.karadimas.tyres.service.criteria.SupplierpaymentsCriteria;
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
 * Service for executing complex queries for {@link Supplierpayments} entities in the database.
 * The main input is a {@link SupplierpaymentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Supplierpayments} or a {@link Page} of {@link Supplierpayments} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierpaymentsQueryService extends QueryService<Supplierpayments> {

    private final Logger log = LoggerFactory.getLogger(SupplierpaymentsQueryService.class);

    private final SupplierpaymentsRepository supplierpaymentsRepository;

    public SupplierpaymentsQueryService(SupplierpaymentsRepository supplierpaymentsRepository) {
        this.supplierpaymentsRepository = supplierpaymentsRepository;
    }

    /**
     * Return a {@link List} of {@link Supplierpayments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Supplierpayments> findByCriteria(SupplierpaymentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Supplierpayments> specification = createSpecification(criteria);
        return supplierpaymentsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Supplierpayments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Supplierpayments> findByCriteria(SupplierpaymentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<Supplierpayments> specification = createSpecification(criteria);
        return supplierpaymentsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierpaymentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Supplierpayments> specification = createSpecification(criteria);
        return supplierpaymentsRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierpaymentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Supplierpayments> createSpecification(SupplierpaymentsCriteria criteria) {
        Specification<Supplierpayments> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Supplierpayments_.id));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), Supplierpayments_.invoiceDate));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), Supplierpayments_.dueDate));
            }
            if (criteria.getIspaid() != null) {
                specification = specification.and(buildSpecification(criteria.getIspaid(), Supplierpayments_.ispaid));
            }
            if (criteria.getAmountDue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountDue(), Supplierpayments_.amountDue));
            }
            if (criteria.getTax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTax(), Supplierpayments_.tax));
            }
            if (criteria.getShipping() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShipping(), Supplierpayments_.shipping));
            }
            if (criteria.getLastupdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastupdated(), Supplierpayments_.lastupdated));
            }
            if (criteria.getSupplierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupplierId(),
                            root -> root.join(Supplierpayments_.supplier, JoinType.LEFT).get(Supplier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
