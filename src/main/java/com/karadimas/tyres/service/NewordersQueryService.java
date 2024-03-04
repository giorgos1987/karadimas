package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.Neworders;
import com.karadimas.tyres.repository.NewordersRepository;
import com.karadimas.tyres.service.criteria.NewordersCriteria;
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
 * Service for executing complex queries for {@link Neworders} entities in the database.
 * The main input is a {@link NewordersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Neworders} or a {@link Page} of {@link Neworders} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NewordersQueryService extends QueryService<Neworders> {

    private final Logger log = LoggerFactory.getLogger(NewordersQueryService.class);

    private final NewordersRepository newordersRepository;

    public NewordersQueryService(NewordersRepository newordersRepository) {
        this.newordersRepository = newordersRepository;
    }

    /**
     * Return a {@link List} of {@link Neworders} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Neworders> findByCriteria(NewordersCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Neworders> specification = createSpecification(criteria);
        return newordersRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Neworders} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Neworders> findByCriteria(NewordersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<Neworders> specification = createSpecification(criteria);
        return newordersRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NewordersCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Neworders> specification = createSpecification(criteria);
        return newordersRepository.count(specification);
    }

    /**
     * Function to convert {@link NewordersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Neworders> createSpecification(NewordersCriteria criteria) {
        Specification<Neworders> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Neworders_.id));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), Neworders_.orderDate));
            }
            if (criteria.getElastika1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika1(), Neworders_.elastika1));
            }
            if (criteria.getElastika2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika2(), Neworders_.elastika2));
            }
            if (criteria.getElastika3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika3(), Neworders_.elastika3));
            }
            if (criteria.getElastika4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika4(), Neworders_.elastika4));
            }
            if (criteria.getElastika5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika5(), Neworders_.elastika5));
            }
            if (criteria.getElastika6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika6(), Neworders_.elastika6));
            }
            if (criteria.getElastika7() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika7(), Neworders_.elastika7));
            }
            if (criteria.getElastika8() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika8(), Neworders_.elastika8));
            }
            if (criteria.getElastika9() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika9(), Neworders_.elastika9));
            }
            if (criteria.getElastika10() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika10(), Neworders_.elastika10));
            }
            if (criteria.getElastika11() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika11(), Neworders_.elastika11));
            }
            if (criteria.getElastika12() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika12(), Neworders_.elastika12));
            }
            if (criteria.getElastika13() != null) {
                specification = specification.and(buildStringSpecification(criteria.getElastika13(), Neworders_.elastika13));
            }
        }
        return specification;
    }
}
