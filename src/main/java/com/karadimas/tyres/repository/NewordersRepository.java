package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Neworders;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Neworders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NewordersRepository extends JpaRepository<Neworders, Long>, JpaSpecificationExecutor<Neworders> {}
