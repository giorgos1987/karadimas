package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.CompanyProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompanyProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long>, JpaSpecificationExecutor<CompanyProfile> {}
