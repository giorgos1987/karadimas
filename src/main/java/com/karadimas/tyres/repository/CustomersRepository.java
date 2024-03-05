package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Cart;
import com.karadimas.tyres.domain.Customers;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Customers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomersRepository
    extends CustomersRepositoryWithBagRelationships, JpaRepository<Customers, Long>, JpaSpecificationExecutor<Customers> {
    default Optional<Customers> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Customers> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Customers> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct customers from Customers customers left join fetch customers.customerpayments",
        countQuery = "select count(distinct customers) from Customers customers"
    )
    Page<Customers> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct customers from Customers customers left join fetch customers.customerpayments")
    List<Customers> findAllWithToOneRelationships();

    @Query("select customers from Customers customers left join fetch customers.customerpayments where customers.id =:id")
    Optional<Customers> findOneWithToOneRelationships(@Param("id") Long id);
}
