package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.CompanyProfile;
import com.karadimas.tyres.repository.CompanyProfileRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompanyProfile}.
 */
@Service
@Transactional
public class CompanyProfileService {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileService.class);

    private final CompanyProfileRepository companyProfileRepository;

    public CompanyProfileService(CompanyProfileRepository companyProfileRepository) {
        this.companyProfileRepository = companyProfileRepository;
    }

    /**
     * Save a companyProfile.
     *
     * @param companyProfile the entity to save.
     * @return the persisted entity.
     */
    public CompanyProfile save(CompanyProfile companyProfile) {
        log.debug("Request to save CompanyProfile : {}", companyProfile);
        return companyProfileRepository.save(companyProfile);
    }

    /**
     * Update a companyProfile.
     *
     * @param companyProfile the entity to save.
     * @return the persisted entity.
     */
    public CompanyProfile update(CompanyProfile companyProfile) {
        log.debug("Request to save CompanyProfile : {}", companyProfile);
        return companyProfileRepository.save(companyProfile);
    }

    /**
     * Partially update a companyProfile.
     *
     * @param companyProfile the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompanyProfile> partialUpdate(CompanyProfile companyProfile) {
        log.debug("Request to partially update CompanyProfile : {}", companyProfile);

        return companyProfileRepository
            .findById(companyProfile.getId())
            .map(existingCompanyProfile -> {
                if (companyProfile.getCompanyName() != null) {
                    existingCompanyProfile.setCompanyName(companyProfile.getCompanyName());
                }
                if (companyProfile.getAddress1() != null) {
                    existingCompanyProfile.setAddress1(companyProfile.getAddress1());
                }
                if (companyProfile.getAddress2() != null) {
                    existingCompanyProfile.setAddress2(companyProfile.getAddress2());
                }
                if (companyProfile.getSuburb() != null) {
                    existingCompanyProfile.setSuburb(companyProfile.getSuburb());
                }
                if (companyProfile.getState() != null) {
                    existingCompanyProfile.setState(companyProfile.getState());
                }
                if (companyProfile.getPostcode() != null) {
                    existingCompanyProfile.setPostcode(companyProfile.getPostcode());
                }
                if (companyProfile.getPhone() != null) {
                    existingCompanyProfile.setPhone(companyProfile.getPhone());
                }
                if (companyProfile.getMobile() != null) {
                    existingCompanyProfile.setMobile(companyProfile.getMobile());
                }
                if (companyProfile.getEmail() != null) {
                    existingCompanyProfile.setEmail(companyProfile.getEmail());
                }
                if (companyProfile.getEmailInfo() != null) {
                    existingCompanyProfile.setEmailInfo(companyProfile.getEmailInfo());
                }
                if (companyProfile.getLogo() != null) {
                    existingCompanyProfile.setLogo(companyProfile.getLogo());
                }
                if (companyProfile.getLogoContentType() != null) {
                    existingCompanyProfile.setLogoContentType(companyProfile.getLogoContentType());
                }
                if (companyProfile.getLanguage() != null) {
                    existingCompanyProfile.setLanguage(companyProfile.getLanguage());
                }

                return existingCompanyProfile;
            })
            .map(companyProfileRepository::save);
    }

    /**
     * Get all the companyProfiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyProfile> findAll() {
        log.debug("Request to get all CompanyProfiles");
        return companyProfileRepository.findAll();
    }

    /**
     * Get one companyProfile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyProfile> findOne(Long id) {
        log.debug("Request to get CompanyProfile : {}", id);
        return companyProfileRepository.findById(id);
    }

    /**
     * Delete the companyProfile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyProfile : {}", id);
        companyProfileRepository.deleteById(id);
    }
}
