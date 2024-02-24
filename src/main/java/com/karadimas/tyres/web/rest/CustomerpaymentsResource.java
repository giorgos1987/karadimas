package com.karadimas.tyres.web.rest;

import com.karadimas.tyres.domain.Customerpayments;
import com.karadimas.tyres.repository.CustomerpaymentsRepository;
import com.karadimas.tyres.service.CustomerpaymentsQueryService;
import com.karadimas.tyres.service.CustomerpaymentsService;
import com.karadimas.tyres.service.criteria.CustomerpaymentsCriteria;
import com.karadimas.tyres.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.karadimas.tyres.domain.Customerpayments}.
 */
@RestController
@RequestMapping("/api")
public class CustomerpaymentsResource {

    private final Logger log = LoggerFactory.getLogger(CustomerpaymentsResource.class);

    private static final String ENTITY_NAME = "customerpayments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerpaymentsService customerpaymentsService;

    private final CustomerpaymentsRepository customerpaymentsRepository;

    private final CustomerpaymentsQueryService customerpaymentsQueryService;

    public CustomerpaymentsResource(
        CustomerpaymentsService customerpaymentsService,
        CustomerpaymentsRepository customerpaymentsRepository,
        CustomerpaymentsQueryService customerpaymentsQueryService
    ) {
        this.customerpaymentsService = customerpaymentsService;
        this.customerpaymentsRepository = customerpaymentsRepository;
        this.customerpaymentsQueryService = customerpaymentsQueryService;
    }

    /**
     * {@code POST  /customerpayments} : Create a new customerpayments.
     *
     * @param customerpayments the customerpayments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerpayments, or with status {@code 400 (Bad Request)} if the customerpayments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customerpayments")
    public ResponseEntity<Customerpayments> createCustomerpayments(@Valid @RequestBody Customerpayments customerpayments)
        throws URISyntaxException {
        log.debug("REST request to save Customerpayments : {}", customerpayments);
        if (customerpayments.getId() != null) {
            throw new BadRequestAlertException("A new customerpayments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Customerpayments result = customerpaymentsService.save(customerpayments);
        return ResponseEntity
            .created(new URI("/api/customerpayments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customerpayments/:id} : Updates an existing customerpayments.
     *
     * @param id the id of the customerpayments to save.
     * @param customerpayments the customerpayments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerpayments,
     * or with status {@code 400 (Bad Request)} if the customerpayments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerpayments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customerpayments/{id}")
    public ResponseEntity<Customerpayments> updateCustomerpayments(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Customerpayments customerpayments
    ) throws URISyntaxException {
        log.debug("REST request to update Customerpayments : {}, {}", id, customerpayments);
        if (customerpayments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerpayments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerpaymentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Customerpayments result = customerpaymentsService.update(customerpayments);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerpayments.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customerpayments/:id} : Partial updates given fields of an existing customerpayments, field will ignore if it is null
     *
     * @param id the id of the customerpayments to save.
     * @param customerpayments the customerpayments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerpayments,
     * or with status {@code 400 (Bad Request)} if the customerpayments is not valid,
     * or with status {@code 404 (Not Found)} if the customerpayments is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerpayments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customerpayments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Customerpayments> partialUpdateCustomerpayments(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Customerpayments customerpayments
    ) throws URISyntaxException {
        log.debug("REST request to partial update Customerpayments partially : {}, {}", id, customerpayments);
        if (customerpayments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerpayments.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerpaymentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Customerpayments> result = customerpaymentsService.partialUpdate(customerpayments);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerpayments.getId().toString())
        );
    }

    /**
     * {@code GET  /customerpayments} : get all the customerpayments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerpayments in body.
     */
    @GetMapping("/customerpayments")
    public ResponseEntity<List<Customerpayments>> getAllCustomerpayments(
        CustomerpaymentsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Customerpayments by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<Customerpayments> page = customerpaymentsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customerpayments/count} : count all the customerpayments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customerpayments/count")
    public ResponseEntity<Long> countCustomerpayments(CustomerpaymentsCriteria criteria) {
        log.debug("REST request to count Customerpayments by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(customerpaymentsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customerpayments/:id} : get the "id" customerpayments.
     *
     * @param id the id of the customerpayments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerpayments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customerpayments/{id}")
    public ResponseEntity<Customerpayments> getCustomerpayments(@PathVariable Long id) {
        log.debug("REST request to get Customerpayments : {}", id);
        Optional<Customerpayments> customerpayments = customerpaymentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerpayments);
    }

    /**
     * {@code DELETE  /customerpayments/:id} : delete the "id" customerpayments.
     *
     * @param id the id of the customerpayments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customerpayments/{id}")
    public ResponseEntity<Void> deleteCustomerpayments(@PathVariable Long id) {
        log.debug("REST request to delete Customerpayments : {}", id);
        customerpaymentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
