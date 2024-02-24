package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.CustomerDetails;
import com.karadimas.tyres.repository.CustomerDetailsRepository;
import com.karadimas.tyres.service.criteria.CustomerDetailsCriteria;
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
 * Service for executing complex queries for {@link CustomerDetails} entities in the database.
 * The main input is a {@link CustomerDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerDetails} or a {@link Page} of {@link CustomerDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerDetailsQueryService extends QueryService<CustomerDetails> {

    private final Logger log = LoggerFactory.getLogger(CustomerDetailsQueryService.class);

    private final CustomerDetailsRepository customerDetailsRepository;

    public CustomerDetailsQueryService(CustomerDetailsRepository customerDetailsRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link CustomerDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerDetails> findByCriteria(CustomerDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<CustomerDetails> specification = createSpecification(criteria);
        return customerDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CustomerDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerDetails> findByCriteria(CustomerDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<CustomerDetails> specification = createSpecification(criteria);
        return customerDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<CustomerDetails> specification = createSpecification(criteria);
        return customerDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerDetails> createSpecification(CustomerDetailsCriteria criteria) {
        Specification<CustomerDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerDetails_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustomerDetails_.name));
            }
            if (criteria.getPlates() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlates(), CustomerDetails_.plates));
            }
            if (criteria.getCustomertype() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomertype(), CustomerDetails_.customertype));
            }
            if (criteria.getLastseen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastseen(), CustomerDetails_.lastseen));
            }
            if (criteria.getFirstseen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstseen(), CustomerDetails_.firstseen));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), CustomerDetails_.mobile));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), CustomerDetails_.phone));
            }
            if (criteria.getCompanyphone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyphone(), CustomerDetails_.companyphone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), CustomerDetails_.email));
            }
            if (criteria.getAddressLine1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLine1(), CustomerDetails_.addressLine1));
            }
            if (criteria.getAddressLine2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLine2(), CustomerDetails_.addressLine2));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), CustomerDetails_.city));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), CustomerDetails_.country));
            }
            if (criteria.getCartId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCartId(), root -> root.join(CustomerDetails_.carts, JoinType.LEFT).get(Cart_.id))
                    );
            }
            if (criteria.getCustomerpaymentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerpaymentsId(),
                            root -> root.join(CustomerDetails_.customerpayments, JoinType.LEFT).get(Customerpayments_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
