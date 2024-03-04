package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.Customers;
import com.karadimas.tyres.repository.CustomersRepository;
import com.karadimas.tyres.service.criteria.CustomersCriteria;
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
 * Service for executing complex queries for {@link Customers} entities in the database.
 * The main input is a {@link CustomersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Customers} or a {@link Page} of {@link Customers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomersQueryService extends QueryService<Customers> {

    private final Logger log = LoggerFactory.getLogger(CustomersQueryService.class);

    private final CustomersRepository customersRepository;

    public CustomersQueryService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    /**
     * Return a {@link List} of {@link Customers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Customers> findByCriteria(CustomersCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Customers> specification = createSpecification(criteria);
        return customersRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Customers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Customers> findByCriteria(CustomersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<Customers> specification = createSpecification(criteria);
        return customersRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomersCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Customers> specification = createSpecification(criteria);
        return customersRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Customers> createSpecification(CustomersCriteria criteria) {
        Specification<Customers> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Customers_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Customers_.name));
            }
            if (criteria.getCar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCar(), Customers_.car));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Customers_.phone));
            }
            if (criteria.getTyres() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTyres(), Customers_.tyres));
            }
            if (criteria.getPlates() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlates(), Customers_.plates));
            }
            if (criteria.getCustomertype() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomertype(), Customers_.customertype));
            }
            if (criteria.getLastseen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastseen(), Customers_.lastseen));
            }
            if (criteria.getFirstseen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstseen(), Customers_.firstseen));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), Customers_.mobile));
            }
            if (criteria.getCompanyphone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyphone(), Customers_.companyphone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Customers_.email));
            }
            if (criteria.getAddressLine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLine(), Customers_.addressLine));
            }
            if (criteria.getCityCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCityCountry(), Customers_.cityCountry));
            }
            if (criteria.getCartId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCartId(), root -> root.join(Customers_.carts, JoinType.LEFT).get(Cart_.id))
                    );
            }
            if (criteria.getCustomerpaymentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerpaymentsId(),
                            root -> root.join(Customers_.customerpayments, JoinType.LEFT).get(Customerpayments_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
