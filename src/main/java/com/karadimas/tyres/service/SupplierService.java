package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.Supplier;
import com.karadimas.tyres.repository.SupplierRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Supplier}.
 */
@Service
@Transactional
public class SupplierService {

    private final Logger log = LoggerFactory.getLogger(SupplierService.class);

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Save a supplier.
     *
     * @param supplier the entity to save.
     * @return the persisted entity.
     */
    public Supplier save(Supplier supplier) {
        log.debug("Request to save Supplier : {}", supplier);
        return supplierRepository.save(supplier);
    }

    /**
     * Update a supplier.
     *
     * @param supplier the entity to save.
     * @return the persisted entity.
     */
    public Supplier update(Supplier supplier) {
        log.debug("Request to save Supplier : {}", supplier);
        return supplierRepository.save(supplier);
    }

    /**
     * Partially update a supplier.
     *
     * @param supplier the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Supplier> partialUpdate(Supplier supplier) {
        log.debug("Request to partially update Supplier : {}", supplier);

        return supplierRepository
            .findById(supplier.getId())
            .map(existingSupplier -> {
                if (supplier.getCompany() != null) {
                    existingSupplier.setCompany(supplier.getCompany());
                }
                if (supplier.getWebPage() != null) {
                    existingSupplier.setWebPage(supplier.getWebPage());
                }
                if (supplier.getNotes() != null) {
                    existingSupplier.setNotes(supplier.getNotes());
                }
                if (supplier.getLastName() != null) {
                    existingSupplier.setLastName(supplier.getLastName());
                }
                if (supplier.getFirstName() != null) {
                    existingSupplier.setFirstName(supplier.getFirstName());
                }
                if (supplier.getEmailAddress() != null) {
                    existingSupplier.setEmailAddress(supplier.getEmailAddress());
                }
                if (supplier.getJobTitle() != null) {
                    existingSupplier.setJobTitle(supplier.getJobTitle());
                }
                if (supplier.getBusinessPhone() != null) {
                    existingSupplier.setBusinessPhone(supplier.getBusinessPhone());
                }
                if (supplier.getHomePhone() != null) {
                    existingSupplier.setHomePhone(supplier.getHomePhone());
                }
                if (supplier.getMobilePhone() != null) {
                    existingSupplier.setMobilePhone(supplier.getMobilePhone());
                }
                if (supplier.getFaxNumber() != null) {
                    existingSupplier.setFaxNumber(supplier.getFaxNumber());
                }
                if (supplier.getAddress() != null) {
                    existingSupplier.setAddress(supplier.getAddress());
                }
                if (supplier.getCity() != null) {
                    existingSupplier.setCity(supplier.getCity());
                }
                if (supplier.getStateProvince() != null) {
                    existingSupplier.setStateProvince(supplier.getStateProvince());
                }
                if (supplier.getZipPostalCode() != null) {
                    existingSupplier.setZipPostalCode(supplier.getZipPostalCode());
                }
                if (supplier.getCountryRegion() != null) {
                    existingSupplier.setCountryRegion(supplier.getCountryRegion());
                }
                if (supplier.getAttachments() != null) {
                    existingSupplier.setAttachments(supplier.getAttachments());
                }
                if (supplier.getAttachmentsContentType() != null) {
                    existingSupplier.setAttachmentsContentType(supplier.getAttachmentsContentType());
                }

                return existingSupplier;
            })
            .map(supplierRepository::save);
    }

    /**
     * Get all the suppliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Supplier> findAll(Pageable pageable) {
        log.debug("Request to get all Suppliers");
        return supplierRepository.findAll(pageable);
    }

    /**
     * Get one supplier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Supplier> findOne(Long id) {
        log.debug("Request to get Supplier : {}", id);
        return supplierRepository.findById(id);
    }

    /**
     * Delete the supplier by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Supplier : {}", id);
        supplierRepository.deleteById(id);
    }
}
