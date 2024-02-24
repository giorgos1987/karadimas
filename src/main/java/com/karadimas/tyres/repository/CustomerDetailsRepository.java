package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.CustomerDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomerDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long>, JpaSpecificationExecutor<CustomerDetails> {}
