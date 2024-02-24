package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.Cart;
import com.karadimas.tyres.repository.CartRepository;
import com.karadimas.tyres.service.criteria.CartCriteria;
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
 * Service for executing complex queries for {@link Cart} entities in the database.
 * The main input is a {@link CartCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Cart} or a {@link Page} of {@link Cart} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CartQueryService extends QueryService<Cart> {

    private final Logger log = LoggerFactory.getLogger(CartQueryService.class);

    private final CartRepository cartRepository;

    public CartQueryService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Return a {@link List} of {@link Cart} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Cart> findByCriteria(CartCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Cart> specification = createSpecification(criteria);
        return cartRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Cart} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Cart> findByCriteria(CartCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<Cart> specification = createSpecification(criteria);
        return cartRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CartCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Cart> specification = createSpecification(criteria);
        return cartRepository.count(specification);
    }

    /**
     * Function to convert {@link CartCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cart> createSpecification(CartCriteria criteria) {
        Specification<Cart> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cart_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Cart_.name));
            }
            if (criteria.getPlate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlate(), Cart_.plate));
            }
            if (criteria.getPlaceddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlaceddate(), Cart_.placeddate));
            }
            if (criteria.getScheduleddate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScheduleddate(), Cart_.scheduleddate));
            }
            if (criteria.getDeliverydate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliverydate(), Cart_.deliverydate));
            }
            if (criteria.getBrand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrand(), Cart_.brand));
            }
            if (criteria.getModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModel(), Cart_.model));
            }
            if (criteria.getNumbertyres() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumbertyres(), Cart_.numbertyres));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Cart_.status));
            }
            if (criteria.getMechanic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMechanic(), Cart_.mechanic));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Cart_.phone));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Cart_.address));
            }
            if (criteria.getWorkorder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWorkorder(), Cart_.workorder));
            }
            if (criteria.getKilometers() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKilometers(), Cart_.kilometers));
            }
            if (criteria.getPaymentMethod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentMethod(), Cart_.paymentMethod));
            }
            if (criteria.getPaymentReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentReference(), Cart_.paymentReference));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), Cart_.totalPrice));
            }
            if (criteria.getJobId() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getJobId(), root -> root.join(Cart_.jobs, JoinType.LEFT).get(Job_.id)));
            }
            if (criteria.getCustomerDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerDetailsId(),
                            root -> root.join(Cart_.customerDetails, JoinType.LEFT).get(CustomerDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
