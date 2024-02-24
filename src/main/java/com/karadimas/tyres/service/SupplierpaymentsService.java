package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.Supplierpayments;
import com.karadimas.tyres.repository.SupplierpaymentsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Supplierpayments}.
 */
@Service
@Transactional
public class SupplierpaymentsService {

    private final Logger log = LoggerFactory.getLogger(SupplierpaymentsService.class);

    private final SupplierpaymentsRepository supplierpaymentsRepository;

    public SupplierpaymentsService(SupplierpaymentsRepository supplierpaymentsRepository) {
        this.supplierpaymentsRepository = supplierpaymentsRepository;
    }

    /**
     * Save a supplierpayments.
     *
     * @param supplierpayments the entity to save.
     * @return the persisted entity.
     */
    public Supplierpayments save(Supplierpayments supplierpayments) {
        log.debug("Request to save Supplierpayments : {}", supplierpayments);
        return supplierpaymentsRepository.save(supplierpayments);
    }

    /**
     * Update a supplierpayments.
     *
     * @param supplierpayments the entity to save.
     * @return the persisted entity.
     */
    public Supplierpayments update(Supplierpayments supplierpayments) {
        log.debug("Request to save Supplierpayments : {}", supplierpayments);
        return supplierpaymentsRepository.save(supplierpayments);
    }

    /**
     * Partially update a supplierpayments.
     *
     * @param supplierpayments the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Supplierpayments> partialUpdate(Supplierpayments supplierpayments) {
        log.debug("Request to partially update Supplierpayments : {}", supplierpayments);

        return supplierpaymentsRepository
            .findById(supplierpayments.getId())
            .map(existingSupplierpayments -> {
                if (supplierpayments.getInvoiceDate() != null) {
                    existingSupplierpayments.setInvoiceDate(supplierpayments.getInvoiceDate());
                }
                if (supplierpayments.getDueDate() != null) {
                    existingSupplierpayments.setDueDate(supplierpayments.getDueDate());
                }
                if (supplierpayments.getIspaid() != null) {
                    existingSupplierpayments.setIspaid(supplierpayments.getIspaid());
                }
                if (supplierpayments.getAmountDue() != null) {
                    existingSupplierpayments.setAmountDue(supplierpayments.getAmountDue());
                }
                if (supplierpayments.getNotes() != null) {
                    existingSupplierpayments.setNotes(supplierpayments.getNotes());
                }
                if (supplierpayments.getAttachments() != null) {
                    existingSupplierpayments.setAttachments(supplierpayments.getAttachments());
                }
                if (supplierpayments.getAttachmentsContentType() != null) {
                    existingSupplierpayments.setAttachmentsContentType(supplierpayments.getAttachmentsContentType());
                }
                if (supplierpayments.getTax() != null) {
                    existingSupplierpayments.setTax(supplierpayments.getTax());
                }
                if (supplierpayments.getShipping() != null) {
                    existingSupplierpayments.setShipping(supplierpayments.getShipping());
                }
                if (supplierpayments.getLastupdated() != null) {
                    existingSupplierpayments.setLastupdated(supplierpayments.getLastupdated());
                }

                return existingSupplierpayments;
            })
            .map(supplierpaymentsRepository::save);
    }

    /**
     * Get all the supplierpayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Supplierpayments> findAll(Pageable pageable) {
        log.debug("Request to get all Supplierpayments");
        return supplierpaymentsRepository.findAll(pageable);
    }

    /**
     * Get all the supplierpayments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Supplierpayments> findAllWithEagerRelationships(Pageable pageable) {
        return supplierpaymentsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one supplierpayments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Supplierpayments> findOne(Long id) {
        log.debug("Request to get Supplierpayments : {}", id);
        return supplierpaymentsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the supplierpayments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Supplierpayments : {}", id);
        supplierpaymentsRepository.deleteById(id);
    }
}
