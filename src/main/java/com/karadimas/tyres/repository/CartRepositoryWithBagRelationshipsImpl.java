package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Cart;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CartRepositoryWithBagRelationshipsImpl implements CartRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cart> fetchBagRelationships(Optional<Cart> cart) {
        return cart.map(this::fetchJobs);
    }

    @Override
    public Page<Cart> fetchBagRelationships(Page<Cart> carts) {
        return new PageImpl<>(fetchBagRelationships(carts.getContent()), carts.getPageable(), carts.getTotalElements());
    }

    @Override
    public List<Cart> fetchBagRelationships(List<Cart> carts) {
        return Optional.of(carts).map(this::fetchJobs).orElse(Collections.emptyList());
    }

    Cart fetchJobs(Cart result) {
        return entityManager
            .createQuery("select cart from Cart cart left join fetch cart.jobs where cart is :cart", Cart.class)
            .setParameter("cart", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Cart> fetchJobs(List<Cart> carts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, carts.size()).forEach(index -> order.put(carts.get(index).getId(), index));
        List<Cart> result = entityManager
            .createQuery("select distinct cart from Cart cart left join fetch cart.jobs where cart in :carts", Cart.class)
            .setParameter("carts", carts)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
