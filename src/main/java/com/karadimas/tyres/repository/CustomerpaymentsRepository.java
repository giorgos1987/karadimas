package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Customerpayments;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Customerpayments entity.
 */
@Repository
public interface CustomerpaymentsRepository extends JpaRepository<Customerpayments, Long>, JpaSpecificationExecutor<Customerpayments> {
    default Optional<Customerpayments> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Customerpayments> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Customerpayments> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct customerpayments from Customerpayments customerpayments left join fetch customerpayments.customerDetails",
        countQuery = "select count(distinct customerpayments) from Customerpayments customerpayments"
    )
    Page<Customerpayments> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct customerpayments from Customerpayments customerpayments left join fetch customerpayments.customerDetails")
    List<Customerpayments> findAllWithToOneRelationships();

    @Query(
        "select customerpayments from Customerpayments customerpayments left join fetch customerpayments.customerDetails where customerpayments.id =:id"
    )
    Optional<Customerpayments> findOneWithToOneRelationships(@Param("id") Long id);
}
