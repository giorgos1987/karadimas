package com.karadimas.tyres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Cart;
import com.karadimas.tyres.domain.Customerpayments;
import com.karadimas.tyres.domain.Customers;
import com.karadimas.tyres.domain.enumeration.Typeofcustomer;
import com.karadimas.tyres.repository.CustomersRepository;
import com.karadimas.tyres.service.criteria.CustomersCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CustomersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAR = "AAAAAAAAAA";
    private static final String UPDATED_CAR = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_TYRES = "AAAAAAAAAA";
    private static final String UPDATED_TYRES = "BBBBBBBBBB";

    private static final String DEFAULT_PLATES = "AAAAAAAAAA";
    private static final String UPDATED_PLATES = "BBBBBBBBBB";

    private static final Typeofcustomer DEFAULT_CUSTOMERTYPE = Typeofcustomer.PRIVATE;
    private static final Typeofcustomer UPDATED_CUSTOMERTYPE = Typeofcustomer.BUSINESS;

    private static final Instant DEFAULT_LASTSEEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LASTSEEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIRSTSEEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRSTSEEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PROSELESYSIS = "AAAAAAAAAA";
    private static final String UPDATED_PROSELESYSIS = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANYPHONE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANYPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_CITY_COUNTRY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomersMockMvc;

    private Customers customers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customers createEntity(EntityManager em) {
        Customers customers = new Customers()
            .name(DEFAULT_NAME)
            .car(DEFAULT_CAR)
            .notes(DEFAULT_NOTES)
            .phone(DEFAULT_PHONE)
            .tyres(DEFAULT_TYRES)
            .plates(DEFAULT_PLATES)
            .customertype(DEFAULT_CUSTOMERTYPE)
            .lastseen(DEFAULT_LASTSEEN)
            .firstseen(DEFAULT_FIRSTSEEN)
            .proselesysis(DEFAULT_PROSELESYSIS)
            .mobile(DEFAULT_MOBILE)
            .companyphone(DEFAULT_COMPANYPHONE)
            .email(DEFAULT_EMAIL)
            .addressLine(DEFAULT_ADDRESS_LINE)
            .cityCountry(DEFAULT_CITY_COUNTRY);
        return customers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customers createUpdatedEntity(EntityManager em) {
        Customers customers = new Customers()
            .name(UPDATED_NAME)
            .car(UPDATED_CAR)
            .notes(UPDATED_NOTES)
            .phone(UPDATED_PHONE)
            .tyres(UPDATED_TYRES)
            .plates(UPDATED_PLATES)
            .customertype(UPDATED_CUSTOMERTYPE)
            .lastseen(UPDATED_LASTSEEN)
            .firstseen(UPDATED_FIRSTSEEN)
            .proselesysis(UPDATED_PROSELESYSIS)
            .mobile(UPDATED_MOBILE)
            .companyphone(UPDATED_COMPANYPHONE)
            .email(UPDATED_EMAIL)
            .addressLine(UPDATED_ADDRESS_LINE)
            .cityCountry(UPDATED_CITY_COUNTRY);
        return customers;
    }

    @BeforeEach
    public void initTest() {
        customers = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomers() throws Exception {
        int databaseSizeBeforeCreate = customersRepository.findAll().size();
        // Create the Customers
        restCustomersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customers)))
            .andExpect(status().isCreated());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate + 1);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomers.getCar()).isEqualTo(DEFAULT_CAR);
        assertThat(testCustomers.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCustomers.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomers.getTyres()).isEqualTo(DEFAULT_TYRES);
        assertThat(testCustomers.getPlates()).isEqualTo(DEFAULT_PLATES);
        assertThat(testCustomers.getCustomertype()).isEqualTo(DEFAULT_CUSTOMERTYPE);
        assertThat(testCustomers.getLastseen()).isEqualTo(DEFAULT_LASTSEEN);
        assertThat(testCustomers.getFirstseen()).isEqualTo(DEFAULT_FIRSTSEEN);
        assertThat(testCustomers.getProselesysis()).isEqualTo(DEFAULT_PROSELESYSIS);
        assertThat(testCustomers.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCustomers.getCompanyphone()).isEqualTo(DEFAULT_COMPANYPHONE);
        assertThat(testCustomers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomers.getAddressLine()).isEqualTo(DEFAULT_ADDRESS_LINE);
        assertThat(testCustomers.getCityCountry()).isEqualTo(DEFAULT_CITY_COUNTRY);
    }

    @Test
    @Transactional
    void createCustomersWithExistingId() throws Exception {
        // Create the Customers with an existing ID
        customers.setId(1L);

        int databaseSizeBeforeCreate = customersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customers)))
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].car").value(hasItem(DEFAULT_CAR)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].tyres").value(hasItem(DEFAULT_TYRES)))
            .andExpect(jsonPath("$.[*].plates").value(hasItem(DEFAULT_PLATES)))
            .andExpect(jsonPath("$.[*].customertype").value(hasItem(DEFAULT_CUSTOMERTYPE.toString())))
            .andExpect(jsonPath("$.[*].lastseen").value(hasItem(DEFAULT_LASTSEEN.toString())))
            .andExpect(jsonPath("$.[*].firstseen").value(hasItem(DEFAULT_FIRSTSEEN.toString())))
            .andExpect(jsonPath("$.[*].proselesysis").value(hasItem(DEFAULT_PROSELESYSIS.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].companyphone").value(hasItem(DEFAULT_COMPANYPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].addressLine").value(hasItem(DEFAULT_ADDRESS_LINE)))
            .andExpect(jsonPath("$.[*].cityCountry").value(hasItem(DEFAULT_CITY_COUNTRY)));
    }

    @Test
    @Transactional
    void getCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get the customers
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL_ID, customers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.car").value(DEFAULT_CAR))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.tyres").value(DEFAULT_TYRES))
            .andExpect(jsonPath("$.plates").value(DEFAULT_PLATES))
            .andExpect(jsonPath("$.customertype").value(DEFAULT_CUSTOMERTYPE.toString()))
            .andExpect(jsonPath("$.lastseen").value(DEFAULT_LASTSEEN.toString()))
            .andExpect(jsonPath("$.firstseen").value(DEFAULT_FIRSTSEEN.toString()))
            .andExpect(jsonPath("$.proselesysis").value(DEFAULT_PROSELESYSIS.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.companyphone").value(DEFAULT_COMPANYPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.addressLine").value(DEFAULT_ADDRESS_LINE))
            .andExpect(jsonPath("$.cityCountry").value(DEFAULT_CITY_COUNTRY));
    }

    @Test
    @Transactional
    void getCustomersByIdFiltering() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        Long id = customers.getId();

        defaultCustomersShouldBeFound("id.equals=" + id);
        defaultCustomersShouldNotBeFound("id.notEquals=" + id);

        defaultCustomersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomersShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where name equals to DEFAULT_NAME
        defaultCustomersShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customersList where name equals to UPDATED_NAME
        defaultCustomersShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomersShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customersList where name equals to UPDATED_NAME
        defaultCustomersShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where name is not null
        defaultCustomersShouldBeFound("name.specified=true");

        // Get all the customersList where name is null
        defaultCustomersShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByNameContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where name contains DEFAULT_NAME
        defaultCustomersShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the customersList where name contains UPDATED_NAME
        defaultCustomersShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where name does not contain DEFAULT_NAME
        defaultCustomersShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the customersList where name does not contain UPDATED_NAME
        defaultCustomersShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByCarIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where car equals to DEFAULT_CAR
        defaultCustomersShouldBeFound("car.equals=" + DEFAULT_CAR);

        // Get all the customersList where car equals to UPDATED_CAR
        defaultCustomersShouldNotBeFound("car.equals=" + UPDATED_CAR);
    }

    @Test
    @Transactional
    void getAllCustomersByCarIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where car in DEFAULT_CAR or UPDATED_CAR
        defaultCustomersShouldBeFound("car.in=" + DEFAULT_CAR + "," + UPDATED_CAR);

        // Get all the customersList where car equals to UPDATED_CAR
        defaultCustomersShouldNotBeFound("car.in=" + UPDATED_CAR);
    }

    @Test
    @Transactional
    void getAllCustomersByCarIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where car is not null
        defaultCustomersShouldBeFound("car.specified=true");

        // Get all the customersList where car is null
        defaultCustomersShouldNotBeFound("car.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCarContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where car contains DEFAULT_CAR
        defaultCustomersShouldBeFound("car.contains=" + DEFAULT_CAR);

        // Get all the customersList where car contains UPDATED_CAR
        defaultCustomersShouldNotBeFound("car.contains=" + UPDATED_CAR);
    }

    @Test
    @Transactional
    void getAllCustomersByCarNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where car does not contain DEFAULT_CAR
        defaultCustomersShouldNotBeFound("car.doesNotContain=" + DEFAULT_CAR);

        // Get all the customersList where car does not contain UPDATED_CAR
        defaultCustomersShouldBeFound("car.doesNotContain=" + UPDATED_CAR);
    }

    @Test
    @Transactional
    void getAllCustomersByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where phone equals to DEFAULT_PHONE
        defaultCustomersShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the customersList where phone equals to UPDATED_PHONE
        defaultCustomersShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCustomersShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the customersList where phone equals to UPDATED_PHONE
        defaultCustomersShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where phone is not null
        defaultCustomersShouldBeFound("phone.specified=true");

        // Get all the customersList where phone is null
        defaultCustomersShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByPhoneContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where phone contains DEFAULT_PHONE
        defaultCustomersShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the customersList where phone contains UPDATED_PHONE
        defaultCustomersShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where phone does not contain DEFAULT_PHONE
        defaultCustomersShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the customersList where phone does not contain UPDATED_PHONE
        defaultCustomersShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByTyresIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where tyres equals to DEFAULT_TYRES
        defaultCustomersShouldBeFound("tyres.equals=" + DEFAULT_TYRES);

        // Get all the customersList where tyres equals to UPDATED_TYRES
        defaultCustomersShouldNotBeFound("tyres.equals=" + UPDATED_TYRES);
    }

    @Test
    @Transactional
    void getAllCustomersByTyresIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where tyres in DEFAULT_TYRES or UPDATED_TYRES
        defaultCustomersShouldBeFound("tyres.in=" + DEFAULT_TYRES + "," + UPDATED_TYRES);

        // Get all the customersList where tyres equals to UPDATED_TYRES
        defaultCustomersShouldNotBeFound("tyres.in=" + UPDATED_TYRES);
    }

    @Test
    @Transactional
    void getAllCustomersByTyresIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where tyres is not null
        defaultCustomersShouldBeFound("tyres.specified=true");

        // Get all the customersList where tyres is null
        defaultCustomersShouldNotBeFound("tyres.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByTyresContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where tyres contains DEFAULT_TYRES
        defaultCustomersShouldBeFound("tyres.contains=" + DEFAULT_TYRES);

        // Get all the customersList where tyres contains UPDATED_TYRES
        defaultCustomersShouldNotBeFound("tyres.contains=" + UPDATED_TYRES);
    }

    @Test
    @Transactional
    void getAllCustomersByTyresNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where tyres does not contain DEFAULT_TYRES
        defaultCustomersShouldNotBeFound("tyres.doesNotContain=" + DEFAULT_TYRES);

        // Get all the customersList where tyres does not contain UPDATED_TYRES
        defaultCustomersShouldBeFound("tyres.doesNotContain=" + UPDATED_TYRES);
    }

    @Test
    @Transactional
    void getAllCustomersByPlatesIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where plates equals to DEFAULT_PLATES
        defaultCustomersShouldBeFound("plates.equals=" + DEFAULT_PLATES);

        // Get all the customersList where plates equals to UPDATED_PLATES
        defaultCustomersShouldNotBeFound("plates.equals=" + UPDATED_PLATES);
    }

    @Test
    @Transactional
    void getAllCustomersByPlatesIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where plates in DEFAULT_PLATES or UPDATED_PLATES
        defaultCustomersShouldBeFound("plates.in=" + DEFAULT_PLATES + "," + UPDATED_PLATES);

        // Get all the customersList where plates equals to UPDATED_PLATES
        defaultCustomersShouldNotBeFound("plates.in=" + UPDATED_PLATES);
    }

    @Test
    @Transactional
    void getAllCustomersByPlatesIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where plates is not null
        defaultCustomersShouldBeFound("plates.specified=true");

        // Get all the customersList where plates is null
        defaultCustomersShouldNotBeFound("plates.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByPlatesContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where plates contains DEFAULT_PLATES
        defaultCustomersShouldBeFound("plates.contains=" + DEFAULT_PLATES);

        // Get all the customersList where plates contains UPDATED_PLATES
        defaultCustomersShouldNotBeFound("plates.contains=" + UPDATED_PLATES);
    }

    @Test
    @Transactional
    void getAllCustomersByPlatesNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where plates does not contain DEFAULT_PLATES
        defaultCustomersShouldNotBeFound("plates.doesNotContain=" + DEFAULT_PLATES);

        // Get all the customersList where plates does not contain UPDATED_PLATES
        defaultCustomersShouldBeFound("plates.doesNotContain=" + UPDATED_PLATES);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomertypeIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customertype equals to DEFAULT_CUSTOMERTYPE
        defaultCustomersShouldBeFound("customertype.equals=" + DEFAULT_CUSTOMERTYPE);

        // Get all the customersList where customertype equals to UPDATED_CUSTOMERTYPE
        defaultCustomersShouldNotBeFound("customertype.equals=" + UPDATED_CUSTOMERTYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomertypeIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customertype in DEFAULT_CUSTOMERTYPE or UPDATED_CUSTOMERTYPE
        defaultCustomersShouldBeFound("customertype.in=" + DEFAULT_CUSTOMERTYPE + "," + UPDATED_CUSTOMERTYPE);

        // Get all the customersList where customertype equals to UPDATED_CUSTOMERTYPE
        defaultCustomersShouldNotBeFound("customertype.in=" + UPDATED_CUSTOMERTYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomertypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where customertype is not null
        defaultCustomersShouldBeFound("customertype.specified=true");

        // Get all the customersList where customertype is null
        defaultCustomersShouldNotBeFound("customertype.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByLastseenIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where lastseen equals to DEFAULT_LASTSEEN
        defaultCustomersShouldBeFound("lastseen.equals=" + DEFAULT_LASTSEEN);

        // Get all the customersList where lastseen equals to UPDATED_LASTSEEN
        defaultCustomersShouldNotBeFound("lastseen.equals=" + UPDATED_LASTSEEN);
    }

    @Test
    @Transactional
    void getAllCustomersByLastseenIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where lastseen in DEFAULT_LASTSEEN or UPDATED_LASTSEEN
        defaultCustomersShouldBeFound("lastseen.in=" + DEFAULT_LASTSEEN + "," + UPDATED_LASTSEEN);

        // Get all the customersList where lastseen equals to UPDATED_LASTSEEN
        defaultCustomersShouldNotBeFound("lastseen.in=" + UPDATED_LASTSEEN);
    }

    @Test
    @Transactional
    void getAllCustomersByLastseenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where lastseen is not null
        defaultCustomersShouldBeFound("lastseen.specified=true");

        // Get all the customersList where lastseen is null
        defaultCustomersShouldNotBeFound("lastseen.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByFirstseenIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where firstseen equals to DEFAULT_FIRSTSEEN
        defaultCustomersShouldBeFound("firstseen.equals=" + DEFAULT_FIRSTSEEN);

        // Get all the customersList where firstseen equals to UPDATED_FIRSTSEEN
        defaultCustomersShouldNotBeFound("firstseen.equals=" + UPDATED_FIRSTSEEN);
    }

    @Test
    @Transactional
    void getAllCustomersByFirstseenIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where firstseen in DEFAULT_FIRSTSEEN or UPDATED_FIRSTSEEN
        defaultCustomersShouldBeFound("firstseen.in=" + DEFAULT_FIRSTSEEN + "," + UPDATED_FIRSTSEEN);

        // Get all the customersList where firstseen equals to UPDATED_FIRSTSEEN
        defaultCustomersShouldNotBeFound("firstseen.in=" + UPDATED_FIRSTSEEN);
    }

    @Test
    @Transactional
    void getAllCustomersByFirstseenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where firstseen is not null
        defaultCustomersShouldBeFound("firstseen.specified=true");

        // Get all the customersList where firstseen is null
        defaultCustomersShouldNotBeFound("firstseen.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where mobile equals to DEFAULT_MOBILE
        defaultCustomersShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the customersList where mobile equals to UPDATED_MOBILE
        defaultCustomersShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCustomersByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultCustomersShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the customersList where mobile equals to UPDATED_MOBILE
        defaultCustomersShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCustomersByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where mobile is not null
        defaultCustomersShouldBeFound("mobile.specified=true");

        // Get all the customersList where mobile is null
        defaultCustomersShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByMobileContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where mobile contains DEFAULT_MOBILE
        defaultCustomersShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the customersList where mobile contains UPDATED_MOBILE
        defaultCustomersShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCustomersByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where mobile does not contain DEFAULT_MOBILE
        defaultCustomersShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the customersList where mobile does not contain UPDATED_MOBILE
        defaultCustomersShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyphoneIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where companyphone equals to DEFAULT_COMPANYPHONE
        defaultCustomersShouldBeFound("companyphone.equals=" + DEFAULT_COMPANYPHONE);

        // Get all the customersList where companyphone equals to UPDATED_COMPANYPHONE
        defaultCustomersShouldNotBeFound("companyphone.equals=" + UPDATED_COMPANYPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyphoneIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where companyphone in DEFAULT_COMPANYPHONE or UPDATED_COMPANYPHONE
        defaultCustomersShouldBeFound("companyphone.in=" + DEFAULT_COMPANYPHONE + "," + UPDATED_COMPANYPHONE);

        // Get all the customersList where companyphone equals to UPDATED_COMPANYPHONE
        defaultCustomersShouldNotBeFound("companyphone.in=" + UPDATED_COMPANYPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyphoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where companyphone is not null
        defaultCustomersShouldBeFound("companyphone.specified=true");

        // Get all the customersList where companyphone is null
        defaultCustomersShouldNotBeFound("companyphone.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyphoneContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where companyphone contains DEFAULT_COMPANYPHONE
        defaultCustomersShouldBeFound("companyphone.contains=" + DEFAULT_COMPANYPHONE);

        // Get all the customersList where companyphone contains UPDATED_COMPANYPHONE
        defaultCustomersShouldNotBeFound("companyphone.contains=" + UPDATED_COMPANYPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyphoneNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where companyphone does not contain DEFAULT_COMPANYPHONE
        defaultCustomersShouldNotBeFound("companyphone.doesNotContain=" + DEFAULT_COMPANYPHONE);

        // Get all the customersList where companyphone does not contain UPDATED_COMPANYPHONE
        defaultCustomersShouldBeFound("companyphone.doesNotContain=" + UPDATED_COMPANYPHONE);
    }

    @Test
    @Transactional
    void getAllCustomersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where email equals to DEFAULT_EMAIL
        defaultCustomersShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the customersList where email equals to UPDATED_EMAIL
        defaultCustomersShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCustomersShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the customersList where email equals to UPDATED_EMAIL
        defaultCustomersShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where email is not null
        defaultCustomersShouldBeFound("email.specified=true");

        // Get all the customersList where email is null
        defaultCustomersShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByEmailContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where email contains DEFAULT_EMAIL
        defaultCustomersShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the customersList where email contains UPDATED_EMAIL
        defaultCustomersShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where email does not contain DEFAULT_EMAIL
        defaultCustomersShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the customersList where email does not contain UPDATED_EMAIL
        defaultCustomersShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByAddressLineIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where addressLine equals to DEFAULT_ADDRESS_LINE
        defaultCustomersShouldBeFound("addressLine.equals=" + DEFAULT_ADDRESS_LINE);

        // Get all the customersList where addressLine equals to UPDATED_ADDRESS_LINE
        defaultCustomersShouldNotBeFound("addressLine.equals=" + UPDATED_ADDRESS_LINE);
    }

    @Test
    @Transactional
    void getAllCustomersByAddressLineIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where addressLine in DEFAULT_ADDRESS_LINE or UPDATED_ADDRESS_LINE
        defaultCustomersShouldBeFound("addressLine.in=" + DEFAULT_ADDRESS_LINE + "," + UPDATED_ADDRESS_LINE);

        // Get all the customersList where addressLine equals to UPDATED_ADDRESS_LINE
        defaultCustomersShouldNotBeFound("addressLine.in=" + UPDATED_ADDRESS_LINE);
    }

    @Test
    @Transactional
    void getAllCustomersByAddressLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where addressLine is not null
        defaultCustomersShouldBeFound("addressLine.specified=true");

        // Get all the customersList where addressLine is null
        defaultCustomersShouldNotBeFound("addressLine.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByAddressLineContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where addressLine contains DEFAULT_ADDRESS_LINE
        defaultCustomersShouldBeFound("addressLine.contains=" + DEFAULT_ADDRESS_LINE);

        // Get all the customersList where addressLine contains UPDATED_ADDRESS_LINE
        defaultCustomersShouldNotBeFound("addressLine.contains=" + UPDATED_ADDRESS_LINE);
    }

    @Test
    @Transactional
    void getAllCustomersByAddressLineNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where addressLine does not contain DEFAULT_ADDRESS_LINE
        defaultCustomersShouldNotBeFound("addressLine.doesNotContain=" + DEFAULT_ADDRESS_LINE);

        // Get all the customersList where addressLine does not contain UPDATED_ADDRESS_LINE
        defaultCustomersShouldBeFound("addressLine.doesNotContain=" + UPDATED_ADDRESS_LINE);
    }

    @Test
    @Transactional
    void getAllCustomersByCityCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where cityCountry equals to DEFAULT_CITY_COUNTRY
        defaultCustomersShouldBeFound("cityCountry.equals=" + DEFAULT_CITY_COUNTRY);

        // Get all the customersList where cityCountry equals to UPDATED_CITY_COUNTRY
        defaultCustomersShouldNotBeFound("cityCountry.equals=" + UPDATED_CITY_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustomersByCityCountryIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where cityCountry in DEFAULT_CITY_COUNTRY or UPDATED_CITY_COUNTRY
        defaultCustomersShouldBeFound("cityCountry.in=" + DEFAULT_CITY_COUNTRY + "," + UPDATED_CITY_COUNTRY);

        // Get all the customersList where cityCountry equals to UPDATED_CITY_COUNTRY
        defaultCustomersShouldNotBeFound("cityCountry.in=" + UPDATED_CITY_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustomersByCityCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where cityCountry is not null
        defaultCustomersShouldBeFound("cityCountry.specified=true");

        // Get all the customersList where cityCountry is null
        defaultCustomersShouldNotBeFound("cityCountry.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCityCountryContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where cityCountry contains DEFAULT_CITY_COUNTRY
        defaultCustomersShouldBeFound("cityCountry.contains=" + DEFAULT_CITY_COUNTRY);

        // Get all the customersList where cityCountry contains UPDATED_CITY_COUNTRY
        defaultCustomersShouldNotBeFound("cityCountry.contains=" + UPDATED_CITY_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustomersByCityCountryNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where cityCountry does not contain DEFAULT_CITY_COUNTRY
        defaultCustomersShouldNotBeFound("cityCountry.doesNotContain=" + DEFAULT_CITY_COUNTRY);

        // Get all the customersList where cityCountry does not contain UPDATED_CITY_COUNTRY
        defaultCustomersShouldBeFound("cityCountry.doesNotContain=" + UPDATED_CITY_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustomersByCartIsEqualToSomething() throws Exception {
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            customersRepository.saveAndFlush(customers);
            cart = CartResourceIT.createEntity(em);
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        em.persist(cart);
        em.flush();
        customers.addCart(cart);
        customersRepository.saveAndFlush(customers);
        Long cartId = cart.getId();

        // Get all the customersList where cart equals to cartId
        defaultCustomersShouldBeFound("cartId.equals=" + cartId);

        // Get all the customersList where cart equals to (cartId + 1)
        defaultCustomersShouldNotBeFound("cartId.equals=" + (cartId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerpaymentsIsEqualToSomething() throws Exception {
        Customerpayments customerpayments;
        if (TestUtil.findAll(em, Customerpayments.class).isEmpty()) {
            customersRepository.saveAndFlush(customers);
            customerpayments = CustomerpaymentsResourceIT.createEntity(em);
        } else {
            customerpayments = TestUtil.findAll(em, Customerpayments.class).get(0);
        }
        em.persist(customerpayments);
        em.flush();
        customers.addCustomerpayments(customerpayments);
        customersRepository.saveAndFlush(customers);
        Long customerpaymentsId = customerpayments.getId();

        // Get all the customersList where customerpayments equals to customerpaymentsId
        defaultCustomersShouldBeFound("customerpaymentsId.equals=" + customerpaymentsId);

        // Get all the customersList where customerpayments equals to (customerpaymentsId + 1)
        defaultCustomersShouldNotBeFound("customerpaymentsId.equals=" + (customerpaymentsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomersShouldBeFound(String filter) throws Exception {
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].car").value(hasItem(DEFAULT_CAR)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].tyres").value(hasItem(DEFAULT_TYRES)))
            .andExpect(jsonPath("$.[*].plates").value(hasItem(DEFAULT_PLATES)))
            .andExpect(jsonPath("$.[*].customertype").value(hasItem(DEFAULT_CUSTOMERTYPE.toString())))
            .andExpect(jsonPath("$.[*].lastseen").value(hasItem(DEFAULT_LASTSEEN.toString())))
            .andExpect(jsonPath("$.[*].firstseen").value(hasItem(DEFAULT_FIRSTSEEN.toString())))
            .andExpect(jsonPath("$.[*].proselesysis").value(hasItem(DEFAULT_PROSELESYSIS.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].companyphone").value(hasItem(DEFAULT_COMPANYPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].addressLine").value(hasItem(DEFAULT_ADDRESS_LINE)))
            .andExpect(jsonPath("$.[*].cityCountry").value(hasItem(DEFAULT_CITY_COUNTRY)));

        // Check, that the count call also returns 1
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomersShouldNotBeFound(String filter) throws Exception {
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomers() throws Exception {
        // Get the customers
        restCustomersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Update the customers
        Customers updatedCustomers = customersRepository.findById(customers.getId()).get();
        // Disconnect from session so that the updates on updatedCustomers are not directly saved in db
        em.detach(updatedCustomers);
        updatedCustomers
            .name(UPDATED_NAME)
            .car(UPDATED_CAR)
            .notes(UPDATED_NOTES)
            .phone(UPDATED_PHONE)
            .tyres(UPDATED_TYRES)
            .plates(UPDATED_PLATES)
            .customertype(UPDATED_CUSTOMERTYPE)
            .lastseen(UPDATED_LASTSEEN)
            .firstseen(UPDATED_FIRSTSEEN)
            .proselesysis(UPDATED_PROSELESYSIS)
            .mobile(UPDATED_MOBILE)
            .companyphone(UPDATED_COMPANYPHONE)
            .email(UPDATED_EMAIL)
            .addressLine(UPDATED_ADDRESS_LINE)
            .cityCountry(UPDATED_CITY_COUNTRY);

        restCustomersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomers))
            )
            .andExpect(status().isOk());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomers.getCar()).isEqualTo(UPDATED_CAR);
        assertThat(testCustomers.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomers.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomers.getTyres()).isEqualTo(UPDATED_TYRES);
        assertThat(testCustomers.getPlates()).isEqualTo(UPDATED_PLATES);
        assertThat(testCustomers.getCustomertype()).isEqualTo(UPDATED_CUSTOMERTYPE);
        assertThat(testCustomers.getLastseen()).isEqualTo(UPDATED_LASTSEEN);
        assertThat(testCustomers.getFirstseen()).isEqualTo(UPDATED_FIRSTSEEN);
        assertThat(testCustomers.getProselesysis()).isEqualTo(UPDATED_PROSELESYSIS);
        assertThat(testCustomers.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCustomers.getCompanyphone()).isEqualTo(UPDATED_COMPANYPHONE);
        assertThat(testCustomers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomers.getAddressLine()).isEqualTo(UPDATED_ADDRESS_LINE);
        assertThat(testCustomers.getCityCountry()).isEqualTo(UPDATED_CITY_COUNTRY);
    }

    @Test
    @Transactional
    void putNonExistingCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customers.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customers)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomersWithPatch() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Update the customers using partial update
        Customers partialUpdatedCustomers = new Customers();
        partialUpdatedCustomers.setId(customers.getId());

        partialUpdatedCustomers
            .name(UPDATED_NAME)
            .notes(UPDATED_NOTES)
            .tyres(UPDATED_TYRES)
            .lastseen(UPDATED_LASTSEEN)
            .proselesysis(UPDATED_PROSELESYSIS);

        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomers))
            )
            .andExpect(status().isOk());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomers.getCar()).isEqualTo(DEFAULT_CAR);
        assertThat(testCustomers.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomers.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomers.getTyres()).isEqualTo(UPDATED_TYRES);
        assertThat(testCustomers.getPlates()).isEqualTo(DEFAULT_PLATES);
        assertThat(testCustomers.getCustomertype()).isEqualTo(DEFAULT_CUSTOMERTYPE);
        assertThat(testCustomers.getLastseen()).isEqualTo(UPDATED_LASTSEEN);
        assertThat(testCustomers.getFirstseen()).isEqualTo(DEFAULT_FIRSTSEEN);
        assertThat(testCustomers.getProselesysis()).isEqualTo(UPDATED_PROSELESYSIS);
        assertThat(testCustomers.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCustomers.getCompanyphone()).isEqualTo(DEFAULT_COMPANYPHONE);
        assertThat(testCustomers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomers.getAddressLine()).isEqualTo(DEFAULT_ADDRESS_LINE);
        assertThat(testCustomers.getCityCountry()).isEqualTo(DEFAULT_CITY_COUNTRY);
    }

    @Test
    @Transactional
    void fullUpdateCustomersWithPatch() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Update the customers using partial update
        Customers partialUpdatedCustomers = new Customers();
        partialUpdatedCustomers.setId(customers.getId());

        partialUpdatedCustomers
            .name(UPDATED_NAME)
            .car(UPDATED_CAR)
            .notes(UPDATED_NOTES)
            .phone(UPDATED_PHONE)
            .tyres(UPDATED_TYRES)
            .plates(UPDATED_PLATES)
            .customertype(UPDATED_CUSTOMERTYPE)
            .lastseen(UPDATED_LASTSEEN)
            .firstseen(UPDATED_FIRSTSEEN)
            .proselesysis(UPDATED_PROSELESYSIS)
            .mobile(UPDATED_MOBILE)
            .companyphone(UPDATED_COMPANYPHONE)
            .email(UPDATED_EMAIL)
            .addressLine(UPDATED_ADDRESS_LINE)
            .cityCountry(UPDATED_CITY_COUNTRY);

        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomers))
            )
            .andExpect(status().isOk());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomers.getCar()).isEqualTo(UPDATED_CAR);
        assertThat(testCustomers.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomers.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomers.getTyres()).isEqualTo(UPDATED_TYRES);
        assertThat(testCustomers.getPlates()).isEqualTo(UPDATED_PLATES);
        assertThat(testCustomers.getCustomertype()).isEqualTo(UPDATED_CUSTOMERTYPE);
        assertThat(testCustomers.getLastseen()).isEqualTo(UPDATED_LASTSEEN);
        assertThat(testCustomers.getFirstseen()).isEqualTo(UPDATED_FIRSTSEEN);
        assertThat(testCustomers.getProselesysis()).isEqualTo(UPDATED_PROSELESYSIS);
        assertThat(testCustomers.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCustomers.getCompanyphone()).isEqualTo(UPDATED_COMPANYPHONE);
        assertThat(testCustomers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomers.getAddressLine()).isEqualTo(UPDATED_ADDRESS_LINE);
        assertThat(testCustomers.getCityCountry()).isEqualTo(UPDATED_CITY_COUNTRY);
    }

    @Test
    @Transactional
    void patchNonExistingCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customers))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();
        customers.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(customers))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeDelete = customersRepository.findAll().size();

        // Delete the customers
        restCustomersMockMvc
            .perform(delete(ENTITY_API_URL_ID, customers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
