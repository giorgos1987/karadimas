package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.*; // for static metamodels
import com.karadimas.tyres.domain.Statistics;
import com.karadimas.tyres.repository.StatisticsRepository;
import com.karadimas.tyres.service.criteria.StatisticsCriteria;
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
 * Service for executing complex queries for {@link Statistics} entities in the database.
 * The main input is a {@link StatisticsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Statistics} or a {@link Page} of {@link Statistics} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatisticsQueryService extends QueryService<Statistics> {

    private final Logger log = LoggerFactory.getLogger(StatisticsQueryService.class);

    private final StatisticsRepository statisticsRepository;

    public StatisticsQueryService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    /**
     * Return a {@link List} of {@link Statistics} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Statistics> findByCriteria(StatisticsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Statistics> specification = createSpecification(criteria);
        return statisticsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Statistics} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Statistics> findByCriteria(StatisticsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<Statistics> specification = createSpecification(criteria);
        return statisticsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatisticsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<Statistics> specification = createSpecification(criteria);
        return statisticsRepository.count(specification);
    }

    /**
     * Function to convert {@link StatisticsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Statistics> createSpecification(StatisticsCriteria criteria) {
        Specification<Statistics> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Statistics_.id));
            }
            if (criteria.getTodaysales() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTodaysales(), Statistics_.todaysales));
            }
            if (criteria.getTotalCustomersNumb() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalCustomersNumb(), Statistics_.totalCustomersNumb));
            }
            if (criteria.getCustmerTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustmerTotal(), Statistics_.custmerTotal));
            }
            if (criteria.getSchedTotalNexWeek() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSchedTotalNexWeek(), Statistics_.schedTotalNexWeek));
            }
            if (criteria.getTotalCarts() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalCarts(), Statistics_.totalCarts));
            }
            if (criteria.getTotalPending() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPending(), Statistics_.totalPending));
            }
            if (criteria.getTotalPayments() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPayments(), Statistics_.totalPayments));
            }
            if (criteria.getPendingPayments() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPendingPayments(), Statistics_.pendingPayments));
            }
            if (criteria.getPendingNumberPayments() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPendingNumberPayments(), Statistics_.pendingNumberPayments));
            }
            if (criteria.getTotalCars() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalCars(), Statistics_.totalCars));
            }
            if (criteria.getTotalTrucks() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalTrucks(), Statistics_.totalTrucks));
            }
            if (criteria.getTotalOther1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalOther1(), Statistics_.totalOther1));
            }
            if (criteria.getTotalOther2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalOther2(), Statistics_.totalOther2));
            }
            if (criteria.getLatestpayments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLatestpayments(), Statistics_.latestpayments));
            }
            if (criteria.getLtstsupplierpaym() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLtstsupplierpaym(), Statistics_.ltstsupplierpaym));
            }
            if (criteria.getRecentrlycompltd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRecentrlycompltd(), Statistics_.recentrlycompltd));
            }
        }
        return specification;
    }
}
