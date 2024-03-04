package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Cart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cart entity.
 *
 * When extending this class, extend CartRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CartRepository extends CartRepositoryWithBagRelationships, JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    default Optional<Cart> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Cart> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Cart> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct cart from Cart cart left join fetch cart.customers",
        countQuery = "select count(distinct cart) from Cart cart"
    )
    Page<Cart> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct cart from Cart cart left join fetch cart.customers")
    List<Cart> findAllWithToOneRelationships();

    @Query("select cart from Cart cart left join fetch cart.customers where cart.id =:id")
    Optional<Cart> findOneWithToOneRelationships(@Param("id") Long id);
}
