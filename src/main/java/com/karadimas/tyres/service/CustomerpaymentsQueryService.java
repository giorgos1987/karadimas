package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.Customerpayments;
import com.karadimas.tyres.repository.CustomerpaymentsRepository;
import com.karadimas.tyres.service.criteria.CustomerpaymentsCriteria;
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
 * Service for executing complex queries for {@link Customerpayments} entities in the database.
 * The main input is a {@link CustomerpaymentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Customerpayments} or a {@link Page} of {@link Customerpayments} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerpaymentsQueryService extends QueryService<Customerpayments> {

    private final Logger log = LoggerFactory.getLogger(CustomerpaymentsQueryService.class);

    private final CustomerpaymentsRepository customerpaymentsRepository;

    public CustomerpaymentsQueryService(CustomerpaymentsRepository customerpaymentsRepository) {
        this.customerpaymentsRepository = customerpaymentsRepository;
    }

    /**
     * Return a {@link List} of {@link Customerpayments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Customerpayments> findByCriteria(CustomerpaymentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Customerpayments> specification = createSpecification(criteria);
        return customerpaymentsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Customerpayments} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Customerpayments> findByCriteria(CustomerpaymentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<Customerpayments> specification = createSpecification(criteria);
        return customerpaymentsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerpaymentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Customerpayments> specification = createSpecification(criteria);
        return customerpaymentsRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerpaymentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Customerpayments> createSpecification(CustomerpaymentsCriteria criteria) {
        Specification<Customerpayments> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Customerpayments_.id));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalAmount(), Customerpayments_.totalAmount));
            }
            if (criteria.getRemainder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemainder(), Customerpayments_.remainder));
            }
            if (criteria.getDownPayment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDownPayment(), Customerpayments_.downPayment));
            }
            if (criteria.getIspaid() != null) {
                specification = specification.and(buildSpecification(criteria.getIspaid(), Customerpayments_.ispaid));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), Customerpayments_.invoiceDate));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), Customerpayments_.dueDate));
            }
            if (criteria.getLastupdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastupdated(), Customerpayments_.lastupdated));
            }
            if (criteria.getCustomersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomersId(),
                            root -> root.join(Customerpayments_.customers, JoinType.LEFT).get(Customers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
