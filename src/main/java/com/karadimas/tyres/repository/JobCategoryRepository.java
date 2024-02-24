package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.JobCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JobCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long>, JpaSpecificationExecutor<JobCategory> {}
