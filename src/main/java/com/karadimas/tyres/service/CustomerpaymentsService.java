package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.Customerpayments;
import com.karadimas.tyres.repository.CustomerpaymentsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Customerpayments}.
 */
@Service
@Transactional
public class CustomerpaymentsService {

    private final Logger log = LoggerFactory.getLogger(CustomerpaymentsService.class);

    private final CustomerpaymentsRepository customerpaymentsRepository;

    public CustomerpaymentsService(CustomerpaymentsRepository customerpaymentsRepository) {
        this.customerpaymentsRepository = customerpaymentsRepository;
    }

    /**
     * Save a customerpayments.
     *
     * @param customerpayments the entity to save.
     * @return the persisted entity.
     */
    public Customerpayments save(Customerpayments customerpayments) {
        log.debug("Request to save Customerpayments : {}", customerpayments);
        return customerpaymentsRepository.save(customerpayments);
    }

    /**
     * Update a customerpayments.
     *
     * @param customerpayments the entity to save.
     * @return the persisted entity.
     */
    public Customerpayments update(Customerpayments customerpayments) {
        log.debug("Request to save Customerpayments : {}", customerpayments);
        return customerpaymentsRepository.save(customerpayments);
    }

    /**
     * Partially update a customerpayments.
     *
     * @param customerpayments the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Customerpayments> partialUpdate(Customerpayments customerpayments) {
        log.debug("Request to partially update Customerpayments : {}", customerpayments);

        return customerpaymentsRepository
            .findById(customerpayments.getId())
            .map(existingCustomerpayments -> {
                if (customerpayments.getIspaid() != null) {
                    existingCustomerpayments.setIspaid(customerpayments.getIspaid());
                }
                if (customerpayments.getInvoiceDate() != null) {
                    existingCustomerpayments.setInvoiceDate(customerpayments.getInvoiceDate());
                }
                if (customerpayments.getDueDate() != null) {
                    existingCustomerpayments.setDueDate(customerpayments.getDueDate());
                }
                if (customerpayments.getTax() != null) {
                    existingCustomerpayments.setTax(customerpayments.getTax());
                }
                if (customerpayments.getShipping() != null) {
                    existingCustomerpayments.setShipping(customerpayments.getShipping());
                }
                if (customerpayments.getAmountDue() != null) {
                    existingCustomerpayments.setAmountDue(customerpayments.getAmountDue());
                }
                if (customerpayments.getNotes() != null) {
                    existingCustomerpayments.setNotes(customerpayments.getNotes());
                }
                if (customerpayments.getAttachments() != null) {
                    existingCustomerpayments.setAttachments(customerpayments.getAttachments());
                }
                if (customerpayments.getAttachmentsContentType() != null) {
                    existingCustomerpayments.setAttachmentsContentType(customerpayments.getAttachmentsContentType());
                }
                if (customerpayments.getLastupdated() != null) {
                    existingCustomerpayments.setLastupdated(customerpayments.getLastupdated());
                }

                return existingCustomerpayments;
            })
            .map(customerpaymentsRepository::save);
    }

    /**
     * Get all the customerpayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Customerpayments> findAll(Pageable pageable) {
        log.debug("Request to get all Customerpayments");
        return customerpaymentsRepository.findAll(pageable);
    }

    /**
     * Get all the customerpayments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Customerpayments> findAllWithEagerRelationships(Pageable pageable) {
        return customerpaymentsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one customerpayments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Customerpayments> findOne(Long id) {
        log.debug("Request to get Customerpayments : {}", id);
        return customerpaymentsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the customerpayments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Customerpayments : {}", id);
        customerpaymentsRepository.deleteById(id);
    }
}
