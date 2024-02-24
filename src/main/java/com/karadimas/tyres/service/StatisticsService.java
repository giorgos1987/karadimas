package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.Statistics;
import com.karadimas.tyres.repository.StatisticsRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Statistics}.
 */
@Service
@Transactional
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(StatisticsService.class);

    private final StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    /**
     * Save a statistics.
     *
     * @param statistics the entity to save.
     * @return the persisted entity.
     */
    public Statistics save(Statistics statistics) {
        log.debug("Request to save Statistics : {}", statistics);
        return statisticsRepository.save(statistics);
    }

    /**
     * Update a statistics.
     *
     * @param statistics the entity to save.
     * @return the persisted entity.
     */
    public Statistics update(Statistics statistics) {
        log.debug("Request to save Statistics : {}", statistics);
        return statisticsRepository.save(statistics);
    }

    /**
     * Partially update a statistics.
     *
     * @param statistics the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Statistics> partialUpdate(Statistics statistics) {
        log.debug("Request to partially update Statistics : {}", statistics);

        return statisticsRepository
            .findById(statistics.getId())
            .map(existingStatistics -> {
                if (statistics.getTodaysales() != null) {
                    existingStatistics.setTodaysales(statistics.getTodaysales());
                }
                if (statistics.getTotalCustomersNumb() != null) {
                    existingStatistics.setTotalCustomersNumb(statistics.getTotalCustomersNumb());
                }
                if (statistics.getCustmerTotal() != null) {
                    existingStatistics.setCustmerTotal(statistics.getCustmerTotal());
                }
                if (statistics.getSchedTotalNexWeek() != null) {
                    existingStatistics.setSchedTotalNexWeek(statistics.getSchedTotalNexWeek());
                }
                if (statistics.getTotalCarts() != null) {
                    existingStatistics.setTotalCarts(statistics.getTotalCarts());
                }
                if (statistics.getTotalPending() != null) {
                    existingStatistics.setTotalPending(statistics.getTotalPending());
                }
                if (statistics.getTotalPayments() != null) {
                    existingStatistics.setTotalPayments(statistics.getTotalPayments());
                }
                if (statistics.getPendingPayments() != null) {
                    existingStatistics.setPendingPayments(statistics.getPendingPayments());
                }
                if (statistics.getPendingNumberPayments() != null) {
                    existingStatistics.setPendingNumberPayments(statistics.getPendingNumberPayments());
                }
                if (statistics.getTotalCars() != null) {
                    existingStatistics.setTotalCars(statistics.getTotalCars());
                }
                if (statistics.getTotalTrucks() != null) {
                    existingStatistics.setTotalTrucks(statistics.getTotalTrucks());
                }
                if (statistics.getTotalOther1() != null) {
                    existingStatistics.setTotalOther1(statistics.getTotalOther1());
                }
                if (statistics.getTotalOther2() != null) {
                    existingStatistics.setTotalOther2(statistics.getTotalOther2());
                }
                if (statistics.getLatestpayments() != null) {
                    existingStatistics.setLatestpayments(statistics.getLatestpayments());
                }
                if (statistics.getLtstsupplierpaym() != null) {
                    existingStatistics.setLtstsupplierpaym(statistics.getLtstsupplierpaym());
                }
                if (statistics.getRecentrlycompltd() != null) {
                    existingStatistics.setRecentrlycompltd(statistics.getRecentrlycompltd());
                }

                return existingStatistics;
            })
            .map(statisticsRepository::save);
    }

    /**
     * Get all the statistics.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Statistics> findAll() {
        log.debug("Request to get all Statistics");
        return statisticsRepository.findAll();
    }

    /**
     * Get one statistics by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Statistics> findOne(Long id) {
        log.debug("Request to get Statistics : {}", id);
        return statisticsRepository.findById(id);
    }

    /**
     * Delete the statistics by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Statistics : {}", id);
        statisticsRepository.deleteById(id);
    }
}
