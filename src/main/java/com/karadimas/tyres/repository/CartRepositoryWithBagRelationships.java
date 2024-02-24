package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Cart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CartRepositoryWithBagRelationships {
    Optional<Cart> fetchBagRelationships(Optional<Cart> cart);

    List<Cart> fetchBagRelationships(List<Cart> carts);

    Page<Cart> fetchBagRelationships(Page<Cart> carts);
}
