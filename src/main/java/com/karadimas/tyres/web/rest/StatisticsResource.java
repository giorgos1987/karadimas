package com.karadimas.tyres.web.rest;

import com.karadimas.tyres.domain.Statistics;
import com.karadimas.tyres.repository.StatisticsRepository;
import com.karadimas.tyres.service.StatisticsQueryService;
import com.karadimas.tyres.service.StatisticsService;
import com.karadimas.tyres.service.criteria.StatisticsCriteria;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.karadimas.tyres.domain.Statistics}.
 */
@RestController
@RequestMapping("/api")
public class StatisticsResource {

    private final Logger log = LoggerFactory.getLogger(StatisticsResource.class);

    private static final String ENTITY_NAME = "statistics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatisticsService statisticsService;

    private final StatisticsRepository statisticsRepository;

    private final StatisticsQueryService statisticsQueryService;

    public StatisticsResource(
        StatisticsService statisticsService,
        StatisticsRepository statisticsRepository,
        StatisticsQueryService statisticsQueryService
    ) {
        this.statisticsService = statisticsService;
        this.statisticsRepository = statisticsRepository;
        this.statisticsQueryService = statisticsQueryService;
    }

    /**
     * {@code POST  /statistics} : Create a new statistics.
     *
     * @param statistics the statistics to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statistics, or with status {@code 400 (Bad Request)} if the statistics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statistics")
    public ResponseEntity<Statistics> createStatistics(@Valid @RequestBody Statistics statistics) throws URISyntaxException {
        log.debug("REST request to save Statistics : {}", statistics);
        if (statistics.getId() != null) {
            throw new BadRequestAlertException("A new statistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Statistics result = statisticsService.save(statistics);
        return ResponseEntity
            .created(new URI("/api/statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statistics/:id} : Updates an existing statistics.
     *
     * @param id the id of the statistics to save.
     * @param statistics the statistics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statistics,
     * or with status {@code 400 (Bad Request)} if the statistics is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statistics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statistics/{id}")
    public ResponseEntity<Statistics> updateStatistics(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Statistics statistics
    ) throws URISyntaxException {
        log.debug("REST request to update Statistics : {}, {}", id, statistics);
        if (statistics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statistics.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Statistics result = statisticsService.update(statistics);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statistics.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /statistics/:id} : Partial updates given fields of an existing statistics, field will ignore if it is null
     *
     * @param id the id of the statistics to save.
     * @param statistics the statistics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statistics,
     * or with status {@code 400 (Bad Request)} if the statistics is not valid,
     * or with status {@code 404 (Not Found)} if the statistics is not found,
     * or with status {@code 500 (Internal Server Error)} if the statistics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/statistics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Statistics> partialUpdateStatistics(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Statistics statistics
    ) throws URISyntaxException {
        log.debug("REST request to partial update Statistics partially : {}, {}", id, statistics);
        if (statistics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statistics.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Statistics> result = statisticsService.partialUpdate(statistics);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statistics.getId().toString())
        );
    }

    /**
     * {@code GET  /statistics} : get all the statistics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statistics in body.
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Statistics>> getAllStatistics(StatisticsCriteria criteria) {
        log.debug("REST request to get Statistics by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        List<Statistics> entityList = statisticsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /statistics/count} : count all the statistics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/statistics/count")
    public ResponseEntity<Long> countStatistics(StatisticsCriteria criteria) {
        log.debug("REST request to count Statistics by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(statisticsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /statistics/:id} : get the "id" statistics.
     *
     * @param id the id of the statistics to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statistics, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statistics/{id}")
    public ResponseEntity<Statistics> getStatistics(@PathVariable Long id) {
        log.debug("REST request to get Statistics : {}", id);
        Optional<Statistics> statistics = statisticsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statistics);
    }

    /**
     * {@code DELETE  /statistics/:id} : delete the "id" statistics.
     *
     * @param id the id of the statistics to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statistics/{id}")
    public ResponseEntity<Void> deleteStatistics(@PathVariable Long id) {
        log.debug("REST request to delete Statistics : {}", id);
        statisticsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
