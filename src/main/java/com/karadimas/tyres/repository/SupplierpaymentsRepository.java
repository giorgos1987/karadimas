package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Supplierpayments;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Supplierpayments entity.
 */
@Repository
public interface SupplierpaymentsRepository extends JpaRepository<Supplierpayments, Long>, JpaSpecificationExecutor<Supplierpayments> {
    default Optional<Supplierpayments> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Supplierpayments> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Supplierpayments> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct supplierpayments from Supplierpayments supplierpayments left join fetch supplierpayments.supplier",
        countQuery = "select count(distinct supplierpayments) from Supplierpayments supplierpayments"
    )
    Page<Supplierpayments> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct supplierpayments from Supplierpayments supplierpayments left join fetch supplierpayments.supplier")
    List<Supplierpayments> findAllWithToOneRelationships();

    @Query(
        "select supplierpayments from Supplierpayments supplierpayments left join fetch supplierpayments.supplier where supplierpayments.id =:id"
    )
    Optional<Supplierpayments> findOneWithToOneRelationships(@Param("id") Long id);
}
