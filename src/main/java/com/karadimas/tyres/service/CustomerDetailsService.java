package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.CustomerDetails;
import com.karadimas.tyres.repository.CustomerDetailsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomerDetails}.
 */
@Service
@Transactional
public class CustomerDetailsService {

    private final Logger log = LoggerFactory.getLogger(CustomerDetailsService.class);

    private final CustomerDetailsRepository customerDetailsRepository;

    public CustomerDetailsService(CustomerDetailsRepository customerDetailsRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
    }

    /**
     * Save a customerDetails.
     *
     * @param customerDetails the entity to save.
     * @return the persisted entity.
     */
    public CustomerDetails save(CustomerDetails customerDetails) {
        log.debug("Request to save CustomerDetails : {}", customerDetails);
        return customerDetailsRepository.save(customerDetails);
    }

    /**
     * Update a customerDetails.
     *
     * @param customerDetails the entity to save.
     * @return the persisted entity.
     */
    public CustomerDetails update(CustomerDetails customerDetails) {
        log.debug("Request to save CustomerDetails : {}", customerDetails);
        return customerDetailsRepository.save(customerDetails);
    }

    /**
     * Partially update a customerDetails.
     *
     * @param customerDetails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomerDetails> partialUpdate(CustomerDetails customerDetails) {
        log.debug("Request to partially update CustomerDetails : {}", customerDetails);

        return customerDetailsRepository
            .findById(customerDetails.getId())
            .map(existingCustomerDetails -> {
                if (customerDetails.getName() != null) {
                    existingCustomerDetails.setName(customerDetails.getName());
                }
                if (customerDetails.getPlates() != null) {
                    existingCustomerDetails.setPlates(customerDetails.getPlates());
                }
                if (customerDetails.getCustomertype() != null) {
                    existingCustomerDetails.setCustomertype(customerDetails.getCustomertype());
                }
                if (customerDetails.getLastseen() != null) {
                    existingCustomerDetails.setLastseen(customerDetails.getLastseen());
                }
                if (customerDetails.getFirstseen() != null) {
                    existingCustomerDetails.setFirstseen(customerDetails.getFirstseen());
                }
                if (customerDetails.getProselesysis() != null) {
                    existingCustomerDetails.setProselesysis(customerDetails.getProselesysis());
                }
                if (customerDetails.getMobile() != null) {
                    existingCustomerDetails.setMobile(customerDetails.getMobile());
                }
                if (customerDetails.getPhone() != null) {
                    existingCustomerDetails.setPhone(customerDetails.getPhone());
                }
                if (customerDetails.getCompanyphone() != null) {
                    existingCustomerDetails.setCompanyphone(customerDetails.getCompanyphone());
                }
                if (customerDetails.getEmail() != null) {
                    existingCustomerDetails.setEmail(customerDetails.getEmail());
                }
                if (customerDetails.getNotes() != null) {
                    existingCustomerDetails.setNotes(customerDetails.getNotes());
                }
                if (customerDetails.getAddressLine1() != null) {
                    existingCustomerDetails.setAddressLine1(customerDetails.getAddressLine1());
                }
                if (customerDetails.getAddressLine2() != null) {
                    existingCustomerDetails.setAddressLine2(customerDetails.getAddressLine2());
                }
                if (customerDetails.getCity() != null) {
                    existingCustomerDetails.setCity(customerDetails.getCity());
                }
                if (customerDetails.getCountry() != null) {
                    existingCustomerDetails.setCountry(customerDetails.getCountry());
                }

                return existingCustomerDetails;
            })
            .map(customerDetailsRepository::save);
    }

    /**
     * Get all the customerDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerDetails> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerDetails");
        return customerDetailsRepository.findAll(pageable);
    }

    /**
     * Get one customerDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomerDetails> findOne(Long id) {
        log.debug("Request to get CustomerDetails : {}", id);
        return customerDetailsRepository.findById(id);
    }

    /**
     * Delete the customerDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerDetails : {}", id);
        customerDetailsRepository.deleteById(id);
    }
}
