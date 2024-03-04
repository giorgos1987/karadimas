package com.karadimas.tyres.web.rest;

import com.karadimas.tyres.domain.Neworders;
import com.karadimas.tyres.repository.NewordersRepository;
import com.karadimas.tyres.service.NewordersQueryService;
import com.karadimas.tyres.service.NewordersService;
import com.karadimas.tyres.service.criteria.NewordersCriteria;
import com.karadimas.tyres.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.karadimas.tyres.domain.Neworders}.
 */
@RestController
@RequestMapping("/api")
public class NewordersResource {

    private final Logger log = LoggerFactory.getLogger(NewordersResource.class);

    private static final String ENTITY_NAME = "neworders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NewordersService newordersService;

    private final NewordersRepository newordersRepository;

    private final NewordersQueryService newordersQueryService;

    public NewordersResource(
        NewordersService newordersService,
        NewordersRepository newordersRepository,
        NewordersQueryService newordersQueryService
    ) {
        this.newordersService = newordersService;
        this.newordersRepository = newordersRepository;
        this.newordersQueryService = newordersQueryService;
    }

    /**
     * {@code POST  /neworders} : Create a new neworders.
     *
     * @param neworders the neworders to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new neworders, or with status {@code 400 (Bad Request)} if the neworders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/neworders")
    public ResponseEntity<Neworders> createNeworders(@RequestBody Neworders neworders) throws URISyntaxException {
        log.debug("REST request to save Neworders : {}", neworders);
        if (neworders.getId() != null) {
            throw new BadRequestAlertException("A new neworders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Neworders result = newordersService.save(neworders);
        return ResponseEntity
            .created(new URI("/api/neworders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /neworders/:id} : Updates an existing neworders.
     *
     * @param id the id of the neworders to save.
     * @param neworders the neworders to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated neworders,
     * or with status {@code 400 (Bad Request)} if the neworders is not valid,
     * or with status {@code 500 (Internal Server Error)} if the neworders couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/neworders/{id}")
    public ResponseEntity<Neworders> updateNeworders(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Neworders neworders
    ) throws URISyntaxException {
        log.debug("REST request to update Neworders : {}, {}", id, neworders);
        if (neworders.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, neworders.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newordersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Neworders result = newordersService.update(neworders);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, neworders.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /neworders/:id} : Partial updates given fields of an existing neworders, field will ignore if it is null
     *
     * @param id the id of the neworders to save.
     * @param neworders the neworders to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated neworders,
     * or with status {@code 400 (Bad Request)} if the neworders is not valid,
     * or with status {@code 404 (Not Found)} if the neworders is not found,
     * or with status {@code 500 (Internal Server Error)} if the neworders couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/neworders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Neworders> partialUpdateNeworders(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Neworders neworders
    ) throws URISyntaxException {
        log.debug("REST request to partial update Neworders partially : {}, {}", id, neworders);
        if (neworders.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, neworders.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newordersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Neworders> result = newordersService.partialUpdate(neworders);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, neworders.getId().toString())
        );
    }

    /**
     * {@code GET  /neworders} : get all the neworders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of neworders in body.
     */
    @GetMapping("/neworders")
    public ResponseEntity<List<Neworders>> getAllNeworders(
        NewordersCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Neworders by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<Neworders> page = newordersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /neworders/count} : count all the neworders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/neworders/count")
    public ResponseEntity<Long> countNeworders(NewordersCriteria criteria) {
        log.debug("REST request to count Neworders by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(newordersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /neworders/:id} : get the "id" neworders.
     *
     * @param id the id of the neworders to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the neworders, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/neworders/{id}")
    public ResponseEntity<Neworders> getNeworders(@PathVariable Long id) {
        log.debug("REST request to get Neworders : {}", id);
        Optional<Neworders> neworders = newordersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(neworders);
    }

    /**
     * {@code DELETE  /neworders/:id} : delete the "id" neworders.
     *
     * @param id the id of the neworders to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/neworders/{id}")
    public ResponseEntity<Void> deleteNeworders(@PathVariable Long id) {
        log.debug("REST request to delete Neworders : {}", id);
        newordersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
