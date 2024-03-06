package com.karadimas.tyres.web.rest;

import com.karadimas.tyres.domain.Customerpayments;
import com.karadimas.tyres.domain.Customers;
import com.karadimas.tyres.repository.CustomersRepository;
import com.karadimas.tyres.service.CustomerpaymentsQueryService;
import com.karadimas.tyres.service.CustomerpaymentsService;
import com.karadimas.tyres.service.CustomersQueryService;
import com.karadimas.tyres.service.CustomersService;
import com.karadimas.tyres.service.criteria.CustomerpaymentsCriteria;
import com.karadimas.tyres.service.criteria.CustomersCriteria;
import com.karadimas.tyres.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.karadimas.tyres.domain.Customers}.
 */
@RestController
@RequestMapping("/api")
public class CustomersResource {

    private final Logger log = LoggerFactory.getLogger(CustomersResource.class);

    private static final String ENTITY_NAME = "customers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomersService customersService;

    private final CustomersRepository customersRepository;

    private final CustomersQueryService customersQueryService;

    private final CustomerpaymentsService customerpaymentsService;

    private final CustomerpaymentsQueryService customerpaymentsQueryService;

    public CustomersResource(
        CustomersService customersService,
        CustomersRepository customersRepository,
        CustomersQueryService customersQueryService,
        CustomerpaymentsService customerpaymentsService,
        CustomerpaymentsQueryService customerpaymentsQueryService
    ) {
        this.customersService = customersService;
        this.customersRepository = customersRepository;
        this.customersQueryService = customersQueryService;
        this.customerpaymentsService = customerpaymentsService;
        this.customerpaymentsQueryService = customerpaymentsQueryService;
    }

    /**
     * {@code POST  /customers} : Create a new customers.
     *
     * @param customers the customers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customers, or with status {@code 400 (Bad Request)} if the customers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers")
    public ResponseEntity<Customers> createCustomers(@RequestBody Customers customers) throws URISyntaxException {
        log.debug("REST request to save Customers : {}", customers);
        if (customers.getId() != null) {
            throw new BadRequestAlertException("A new customers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Customers result = customersService.save(customers);
        return ResponseEntity
            .created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customers/:id} : Updates an existing customers.
     *
     * @param id the id of the customers to save.
     * @param customers the customers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customers,
     * or with status {@code 400 (Bad Request)} if the customers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customers> updateCustomers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Customers customers
    ) throws URISyntaxException {
        log.debug("REST request to update Customers : {}, {}", id, customers);
        if (customers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Customers result = customersService.update(customers);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customers.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customers/:id} : Partial updates given fields of an existing customers, field will ignore if it is null
     *
     * @param id the id of the customers to save.
     * @param customers the customers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customers,
     * or with status {@code 400 (Bad Request)} if the customers is not valid,
     * or with status {@code 404 (Not Found)} if the customers is not found,
     * or with status {@code 500 (Internal Server Error)} if the customers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Customers> partialUpdateCustomers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Customers customers
    ) throws URISyntaxException {
        log.debug("REST request to partial update Customers partially : {}, {}", id, customers);
        if (customers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customers.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Customers> result = customersService.partialUpdate(customers);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customers.getId().toString())
        );
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("/customers")
    public ResponseEntity<List<Customers>> getAllCustomers(
        CustomersCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Customers by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));

        //Page<Customers> allCustomers = customersService.findAllWithEagerRelationships(pageable);
        Page<Customers> filteredpageCustomers = customersQueryService.findByCriteria(criteria, pageable);

        // with criteria
        // Page<Customers> filteredpageCustomers = customersQueryService.findByCriteria(criteria, pageable);

        //        List<Customers> customersList = filteredpageCustomers.getContent();
        //        List<Customerpayments> customerpaymentsList = new ArrayList<>();
        //
        //        CustomerpaymentsCriteria cpCrit = new CustomerpaymentsCriteria();
        //        LongFilter lf = new LongFilter();
        //        lf.setIn(filteredpageCustomers.get()
        //                                .map(Customers::getId).collect(Collectors.toList()));
        //        cpCrit.setCustomersId(lf);
        //
        //
        //
        //
        //
        //        customerpaymentsQueryService.findByCriteria(cpCrit).forEach(customerpayments -> {
        //            customersList.forEach(customers -> {
        //                if(customerpayments.getCustomers().getId()==customers.getId())
        //                    customerpaymentsList.add(customerpayments);
        //            });
        //
        //        });
        //Page<Customers> pageCustomers = customersService.findAllWithEagerRelationships(pageable);

        //        filteredpageCustomers.forEach(customer -> {
        //            customer.setCustomerpayments(pageCustomers.stream().filter(pC -> pC.getId()==customer.getId()).findFirst().get().getCustomerpayments());
        //        });

        //
        //                filteredpageCustomers
        //                    .getContent()
        //                    .stream()
        //                    .forEach(customer -> {
        //
        //                            CustomerpaymentsCriteria cpCrit = new CustomerpaymentsCriteria();
        //                            LongFilter lf = new LongFilter();
        //                            lf.setEquals(customer.getId());
        //                            cpCrit.setCustomersId(lf);
        //                            //Customerpayments mvS = customerpaymentsQueryService.findByCriteria(cpCrit);
        //                            customer.setCustomerpayments(customerpaymentsQueryService.findByCriteria(cpCrit).stream().collect(Collectors.toSet()));
        //
        //                    });

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            filteredpageCustomers
        );
        return ResponseEntity.ok().headers(headers).body(filteredpageCustomers.getContent());
    }

    /**
     * {@code GET  /customers/count} : count all the customers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customers/count")
    public ResponseEntity<Long> countCustomers(CustomersCriteria criteria) {
        log.debug("REST request to count Customers by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(customersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customers/:id} : get the "id" customers.
     *
     * @param id the id of the customers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customers> getCustomers(@PathVariable Long id) {
        log.debug("REST request to get Customers : {}", id);
        Optional<Customers> customers = customersService.findOneWithToOneRelationshipsPayments(id);
        // findOne(id); prior to fetch payments

        return ResponseUtil.wrapOrNotFound(customers);
    }

    /**
     * {@code DELETE  /customers/:id} : delete the "id" customers.
     *
     * @param id the id of the customers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomers(@PathVariable Long id) {
        log.debug("REST request to delete Customers : {}", id);
        customersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
