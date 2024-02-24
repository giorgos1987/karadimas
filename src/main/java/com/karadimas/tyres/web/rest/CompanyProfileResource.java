package com.karadimas.tyres.web.rest;

import com.karadimas.tyres.domain.CompanyProfile;
import com.karadimas.tyres.repository.CompanyProfileRepository;
import com.karadimas.tyres.service.CompanyProfileQueryService;
import com.karadimas.tyres.service.CompanyProfileService;
import com.karadimas.tyres.service.criteria.CompanyProfileCriteria;
import com.karadimas.tyres.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.karadimas.tyres.domain.CompanyProfile}.
 */
@RestController
@RequestMapping("/api")
public class CompanyProfileResource {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileResource.class);

    private static final String ENTITY_NAME = "companyProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyProfileService companyProfileService;

    private final CompanyProfileRepository companyProfileRepository;

    private final CompanyProfileQueryService companyProfileQueryService;

    public CompanyProfileResource(
        CompanyProfileService companyProfileService,
        CompanyProfileRepository companyProfileRepository,
        CompanyProfileQueryService companyProfileQueryService
    ) {
        this.companyProfileService = companyProfileService;
        this.companyProfileRepository = companyProfileRepository;
        this.companyProfileQueryService = companyProfileQueryService;
    }

    /**
     * {@code POST  /company-profiles} : Create a new companyProfile.
     *
     * @param companyProfile the companyProfile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyProfile, or with status {@code 400 (Bad Request)} if the companyProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-profiles")
    public ResponseEntity<CompanyProfile> createCompanyProfile(@RequestBody CompanyProfile companyProfile) throws URISyntaxException {
        log.debug("REST request to save CompanyProfile : {}", companyProfile);
        if (companyProfile.getId() != null) {
            throw new BadRequestAlertException("A new companyProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyProfile result = companyProfileService.save(companyProfile);
        return ResponseEntity
            .created(new URI("/api/company-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-profiles/:id} : Updates an existing companyProfile.
     *
     * @param id the id of the companyProfile to save.
     * @param companyProfile the companyProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyProfile,
     * or with status {@code 400 (Bad Request)} if the companyProfile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-profiles/{id}")
    public ResponseEntity<CompanyProfile> updateCompanyProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyProfile companyProfile
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyProfile : {}, {}", id, companyProfile);
        if (companyProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyProfile result = companyProfileService.update(companyProfile);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyProfile.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-profiles/:id} : Partial updates given fields of an existing companyProfile, field will ignore if it is null
     *
     * @param id the id of the companyProfile to save.
     * @param companyProfile the companyProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyProfile,
     * or with status {@code 400 (Bad Request)} if the companyProfile is not valid,
     * or with status {@code 404 (Not Found)} if the companyProfile is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-profiles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyProfile> partialUpdateCompanyProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyProfile companyProfile
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyProfile partially : {}, {}", id, companyProfile);
        if (companyProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyProfile.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyProfile> result = companyProfileService.partialUpdate(companyProfile);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyProfile.getId().toString())
        );
    }

    /**
     * {@code GET  /company-profiles} : get all the companyProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyProfiles in body.
     */
    @GetMapping("/company-profiles")
    public ResponseEntity<List<CompanyProfile>> getAllCompanyProfiles(CompanyProfileCriteria criteria) {
        log.debug("REST request to get CompanyProfiles by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        List<CompanyProfile> entityList = companyProfileQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /company-profiles/count} : count all the companyProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/company-profiles/count")
    public ResponseEntity<Long> countCompanyProfiles(CompanyProfileCriteria criteria) {
        log.debug("REST request to count CompanyProfiles by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(companyProfileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /company-profiles/:id} : get the "id" companyProfile.
     *
     * @param id the id of the companyProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyProfile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-profiles/{id}")
    public ResponseEntity<CompanyProfile> getCompanyProfile(@PathVariable Long id) {
        log.debug("REST request to get CompanyProfile : {}", id);
        Optional<CompanyProfile> companyProfile = companyProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyProfile);
    }

    /**
     * {@code DELETE  /company-profiles/:id} : delete the "id" companyProfile.
     *
     * @param id the id of the companyProfile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-profiles/{id}")
    public ResponseEntity<Void> deleteCompanyProfile(@PathVariable Long id) {
        log.debug("REST request to delete CompanyProfile : {}", id);
        companyProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
