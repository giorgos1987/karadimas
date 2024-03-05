package com.karadimas.tyres.repository;

import com.karadimas.tyres.domain.Cart;
import com.karadimas.tyres.domain.Customers;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CustomersRepositoryWithBagRelationships {
    Optional<Customers> fetchBagRelationships(Optional<Customers> customers);

    List<Customers> fetchBagRelationships(List<Customers> customers);

    Page<Customers> fetchBagRelationships(Page<Customers> customers);
}
