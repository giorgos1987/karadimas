package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Statistics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Statistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long>, JpaSpecificationExecutor<Statistics> {}
