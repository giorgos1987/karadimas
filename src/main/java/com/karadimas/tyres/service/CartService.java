package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.Cart;
import com.karadimas.tyres.repository.CartRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cart}.
 */
@Service
@Transactional
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Save a cart.
     *
     * @param cart the entity to save.
     * @return the persisted entity.
     */
    public Cart save(Cart cart) {
        log.debug("Request to save Cart : {}", cart);
        return cartRepository.save(cart);
    }

    /**
     * Update a cart.
     *
     * @param cart the entity to save.
     * @return the persisted entity.
     */
    public Cart update(Cart cart) {
        log.debug("Request to save Cart : {}", cart);
        return cartRepository.save(cart);
    }

    /**
     * Partially update a cart.
     *
     * @param cart the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Cart> partialUpdate(Cart cart) {
        log.debug("Request to partially update Cart : {}", cart);

        return cartRepository
            .findById(cart.getId())
            .map(existingCart -> {
                if (cart.getName() != null) {
                    existingCart.setName(cart.getName());
                }
                if (cart.getPlate() != null) {
                    existingCart.setPlate(cart.getPlate());
                }
                if (cart.getPlaceddate() != null) {
                    existingCart.setPlaceddate(cart.getPlaceddate());
                }
                if (cart.getScheduleddate() != null) {
                    existingCart.setScheduleddate(cart.getScheduleddate());
                }
                if (cart.getDeliverydate() != null) {
                    existingCart.setDeliverydate(cart.getDeliverydate());
                }
                if (cart.getBrand() != null) {
                    existingCart.setBrand(cart.getBrand());
                }
                if (cart.getModel() != null) {
                    existingCart.setModel(cart.getModel());
                }
                if (cart.getNumbertyres() != null) {
                    existingCart.setNumbertyres(cart.getNumbertyres());
                }
                if (cart.getStatus() != null) {
                    existingCart.setStatus(cart.getStatus());
                }
                if (cart.getNotes() != null) {
                    existingCart.setNotes(cart.getNotes());
                }
                if (cart.getMechanic() != null) {
                    existingCart.setMechanic(cart.getMechanic());
                }
                if (cart.getPhone() != null) {
                    existingCart.setPhone(cart.getPhone());
                }
                if (cart.getAddress() != null) {
                    existingCart.setAddress(cart.getAddress());
                }
                if (cart.getWorkorder() != null) {
                    existingCart.setWorkorder(cart.getWorkorder());
                }
                if (cart.getKilometers() != null) {
                    existingCart.setKilometers(cart.getKilometers());
                }
                if (cart.getPaymentMethod() != null) {
                    existingCart.setPaymentMethod(cart.getPaymentMethod());
                }
                if (cart.getPaymentReference() != null) {
                    existingCart.setPaymentReference(cart.getPaymentReference());
                }
                if (cart.getTotalPrice() != null) {
                    existingCart.setTotalPrice(cart.getTotalPrice());
                }

                return existingCart;
            })
            .map(cartRepository::save);
    }

    /**
     * Get all the carts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Cart> findAll(Pageable pageable) {
        log.debug("Request to get all Carts");
        return cartRepository.findAll(pageable);
    }

    /**
     * Get all the carts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Cart> findAllWithEagerRelationships(Pageable pageable) {
        return cartRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one cart by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cart> findOne(Long id) {
        log.debug("Request to get Cart : {}", id);
        return cartRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the cart by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cart : {}", id);
        cartRepository.deleteById(id);
    }
}
