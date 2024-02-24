package com.karadimas.tyres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Cart;
import com.karadimas.tyres.domain.CustomerDetails;
import com.karadimas.tyres.domain.Customerpayments;
import com.karadimas.tyres.domain.enumeration.Typeofcustomer;
import com.karadimas.tyres.repository.CustomerDetailsRepository;
import com.karadimas.tyres.service.criteria.CustomerDetailsCriteria;
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
 * Integration tests for the {@link CustomerDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerDetailsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANYPHONE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANYPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customer-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerDetailsMockMvc;

    private CustomerDetails customerDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerDetails createEntity(EntityManager em) {
        CustomerDetails customerDetails = new CustomerDetails()
            .name(DEFAULT_NAME)
            .plates(DEFAULT_PLATES)
            .customertype(DEFAULT_CUSTOMERTYPE)
            .lastseen(DEFAULT_LASTSEEN)
            .firstseen(DEFAULT_FIRSTSEEN)
            .proselesysis(DEFAULT_PROSELESYSIS)
            .mobile(DEFAULT_MOBILE)
            .phone(DEFAULT_PHONE)
            .companyphone(DEFAULT_COMPANYPHONE)
            .email(DEFAULT_EMAIL)
            .notes(DEFAULT_NOTES)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY);
        return customerDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerDetails createUpdatedEntity(EntityManager em) {
        CustomerDetails customerDetails = new CustomerDetails()
            .name(UPDATED_NAME)
            .plates(UPDATED_PLATES)
            .customertype(UPDATED_CUSTOMERTYPE)
            .lastseen(UPDATED_LASTSEEN)
            .firstseen(UPDATED_FIRSTSEEN)
            .proselesysis(UPDATED_PROSELESYSIS)
            .mobile(UPDATED_MOBILE)
            .phone(UPDATED_PHONE)
            .companyphone(UPDATED_COMPANYPHONE)
            .email(UPDATED_EMAIL)
            .notes(UPDATED_NOTES)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);
        return customerDetails;
    }

    @BeforeEach
    public void initTest() {
        customerDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomerDetails() throws Exception {
        int databaseSizeBeforeCreate = customerDetailsRepository.findAll().size();
        // Create the CustomerDetails
        restCustomerDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDetails))
            )
            .andExpect(status().isCreated());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerDetails testCustomerDetails = customerDetailsList.get(customerDetailsList.size() - 1);
        assertThat(testCustomerDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerDetails.getPlates()).isEqualTo(DEFAULT_PLATES);
        assertThat(testCustomerDetails.getCustomertype()).isEqualTo(DEFAULT_CUSTOMERTYPE);
        assertThat(testCustomerDetails.getLastseen()).isEqualTo(DEFAULT_LASTSEEN);
        assertThat(testCustomerDetails.getFirstseen()).isEqualTo(DEFAULT_FIRSTSEEN);
        assertThat(testCustomerDetails.getProselesysis()).isEqualTo(DEFAULT_PROSELESYSIS);
        assertThat(testCustomerDetails.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCustomerDetails.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomerDetails.getCompanyphone()).isEqualTo(DEFAULT_COMPANYPHONE);
        assertThat(testCustomerDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerDetails.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCustomerDetails.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testCustomerDetails.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testCustomerDetails.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomerDetails.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void createCustomerDetailsWithExistingId() throws Exception {
        // Create the CustomerDetails with an existing ID
        customerDetails.setId(1L);

        int databaseSizeBeforeCreate = customerDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList
        restCustomerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].plates").value(hasItem(DEFAULT_PLATES)))
            .andExpect(jsonPath("$.[*].customertype").value(hasItem(DEFAULT_CUSTOMERTYPE.toString())))
            .andExpect(jsonPath("$.[*].lastseen").value(hasItem(DEFAULT_LASTSEEN.toString())))
            .andExpect(jsonPath("$.[*].firstseen").value(hasItem(DEFAULT_FIRSTSEEN.toString())))
            .andExpect(jsonPath("$.[*].proselesysis").value(hasItem(DEFAULT_PROSELESYSIS.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].companyphone").value(hasItem(DEFAULT_COMPANYPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }

    @Test
    @Transactional
    void getCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get the customerDetails
        restCustomerDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, customerDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.plates").value(DEFAULT_PLATES))
            .andExpect(jsonPath("$.customertype").value(DEFAULT_CUSTOMERTYPE.toString()))
            .andExpect(jsonPath("$.lastseen").value(DEFAULT_LASTSEEN.toString()))
            .andExpect(jsonPath("$.firstseen").value(DEFAULT_FIRSTSEEN.toString()))
            .andExpect(jsonPath("$.proselesysis").value(DEFAULT_PROSELESYSIS.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.companyphone").value(DEFAULT_COMPANYPHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }

    @Test
    @Transactional
    void getCustomerDetailsByIdFiltering() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        Long id = customerDetails.getId();

        defaultCustomerDetailsShouldBeFound("id.equals=" + id);
        defaultCustomerDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where name equals to DEFAULT_NAME
        defaultCustomerDetailsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerDetailsList where name equals to UPDATED_NAME
        defaultCustomerDetailsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerDetailsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerDetailsList where name equals to UPDATED_NAME
        defaultCustomerDetailsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where name is not null
        defaultCustomerDetailsShouldBeFound("name.specified=true");

        // Get all the customerDetailsList where name is null
        defaultCustomerDetailsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByNameContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where name contains DEFAULT_NAME
        defaultCustomerDetailsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the customerDetailsList where name contains UPDATED_NAME
        defaultCustomerDetailsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where name does not contain DEFAULT_NAME
        defaultCustomerDetailsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the customerDetailsList where name does not contain UPDATED_NAME
        defaultCustomerDetailsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPlatesIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where plates equals to DEFAULT_PLATES
        defaultCustomerDetailsShouldBeFound("plates.equals=" + DEFAULT_PLATES);

        // Get all the customerDetailsList where plates equals to UPDATED_PLATES
        defaultCustomerDetailsShouldNotBeFound("plates.equals=" + UPDATED_PLATES);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPlatesIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where plates in DEFAULT_PLATES or UPDATED_PLATES
        defaultCustomerDetailsShouldBeFound("plates.in=" + DEFAULT_PLATES + "," + UPDATED_PLATES);

        // Get all the customerDetailsList where plates equals to UPDATED_PLATES
        defaultCustomerDetailsShouldNotBeFound("plates.in=" + UPDATED_PLATES);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPlatesIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where plates is not null
        defaultCustomerDetailsShouldBeFound("plates.specified=true");

        // Get all the customerDetailsList where plates is null
        defaultCustomerDetailsShouldNotBeFound("plates.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPlatesContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where plates contains DEFAULT_PLATES
        defaultCustomerDetailsShouldBeFound("plates.contains=" + DEFAULT_PLATES);

        // Get all the customerDetailsList where plates contains UPDATED_PLATES
        defaultCustomerDetailsShouldNotBeFound("plates.contains=" + UPDATED_PLATES);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPlatesNotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where plates does not contain DEFAULT_PLATES
        defaultCustomerDetailsShouldNotBeFound("plates.doesNotContain=" + DEFAULT_PLATES);

        // Get all the customerDetailsList where plates does not contain UPDATED_PLATES
        defaultCustomerDetailsShouldBeFound("plates.doesNotContain=" + UPDATED_PLATES);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCustomertypeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where customertype equals to DEFAULT_CUSTOMERTYPE
        defaultCustomerDetailsShouldBeFound("customertype.equals=" + DEFAULT_CUSTOMERTYPE);

        // Get all the customerDetailsList where customertype equals to UPDATED_CUSTOMERTYPE
        defaultCustomerDetailsShouldNotBeFound("customertype.equals=" + UPDATED_CUSTOMERTYPE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCustomertypeIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where customertype in DEFAULT_CUSTOMERTYPE or UPDATED_CUSTOMERTYPE
        defaultCustomerDetailsShouldBeFound("customertype.in=" + DEFAULT_CUSTOMERTYPE + "," + UPDATED_CUSTOMERTYPE);

        // Get all the customerDetailsList where customertype equals to UPDATED_CUSTOMERTYPE
        defaultCustomerDetailsShouldNotBeFound("customertype.in=" + UPDATED_CUSTOMERTYPE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCustomertypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where customertype is not null
        defaultCustomerDetailsShouldBeFound("customertype.specified=true");

        // Get all the customerDetailsList where customertype is null
        defaultCustomerDetailsShouldNotBeFound("customertype.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByLastseenIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where lastseen equals to DEFAULT_LASTSEEN
        defaultCustomerDetailsShouldBeFound("lastseen.equals=" + DEFAULT_LASTSEEN);

        // Get all the customerDetailsList where lastseen equals to UPDATED_LASTSEEN
        defaultCustomerDetailsShouldNotBeFound("lastseen.equals=" + UPDATED_LASTSEEN);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByLastseenIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where lastseen in DEFAULT_LASTSEEN or UPDATED_LASTSEEN
        defaultCustomerDetailsShouldBeFound("lastseen.in=" + DEFAULT_LASTSEEN + "," + UPDATED_LASTSEEN);

        // Get all the customerDetailsList where lastseen equals to UPDATED_LASTSEEN
        defaultCustomerDetailsShouldNotBeFound("lastseen.in=" + UPDATED_LASTSEEN);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByLastseenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where lastseen is not null
        defaultCustomerDetailsShouldBeFound("lastseen.specified=true");

        // Get all the customerDetailsList where lastseen is null
        defaultCustomerDetailsShouldNotBeFound("lastseen.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByFirstseenIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where firstseen equals to DEFAULT_FIRSTSEEN
        defaultCustomerDetailsShouldBeFound("firstseen.equals=" + DEFAULT_FIRSTSEEN);

        // Get all the customerDetailsList where firstseen equals to UPDATED_FIRSTSEEN
        defaultCustomerDetailsShouldNotBeFound("firstseen.equals=" + UPDATED_FIRSTSEEN);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByFirstseenIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where firstseen in DEFAULT_FIRSTSEEN or UPDATED_FIRSTSEEN
        defaultCustomerDetailsShouldBeFound("firstseen.in=" + DEFAULT_FIRSTSEEN + "," + UPDATED_FIRSTSEEN);

        // Get all the customerDetailsList where firstseen equals to UPDATED_FIRSTSEEN
        defaultCustomerDetailsShouldNotBeFound("firstseen.in=" + UPDATED_FIRSTSEEN);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByFirstseenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where firstseen is not null
        defaultCustomerDetailsShouldBeFound("firstseen.specified=true");

        // Get all the customerDetailsList where firstseen is null
        defaultCustomerDetailsShouldNotBeFound("firstseen.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where mobile equals to DEFAULT_MOBILE
        defaultCustomerDetailsShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the customerDetailsList where mobile equals to UPDATED_MOBILE
        defaultCustomerDetailsShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultCustomerDetailsShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the customerDetailsList where mobile equals to UPDATED_MOBILE
        defaultCustomerDetailsShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where mobile is not null
        defaultCustomerDetailsShouldBeFound("mobile.specified=true");

        // Get all the customerDetailsList where mobile is null
        defaultCustomerDetailsShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByMobileContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where mobile contains DEFAULT_MOBILE
        defaultCustomerDetailsShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the customerDetailsList where mobile contains UPDATED_MOBILE
        defaultCustomerDetailsShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where mobile does not contain DEFAULT_MOBILE
        defaultCustomerDetailsShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the customerDetailsList where mobile does not contain UPDATED_MOBILE
        defaultCustomerDetailsShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where phone equals to DEFAULT_PHONE
        defaultCustomerDetailsShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the customerDetailsList where phone equals to UPDATED_PHONE
        defaultCustomerDetailsShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCustomerDetailsShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the customerDetailsList where phone equals to UPDATED_PHONE
        defaultCustomerDetailsShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where phone is not null
        defaultCustomerDetailsShouldBeFound("phone.specified=true");

        // Get all the customerDetailsList where phone is null
        defaultCustomerDetailsShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where phone contains DEFAULT_PHONE
        defaultCustomerDetailsShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the customerDetailsList where phone contains UPDATED_PHONE
        defaultCustomerDetailsShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where phone does not contain DEFAULT_PHONE
        defaultCustomerDetailsShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the customerDetailsList where phone does not contain UPDATED_PHONE
        defaultCustomerDetailsShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCompanyphoneIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where companyphone equals to DEFAULT_COMPANYPHONE
        defaultCustomerDetailsShouldBeFound("companyphone.equals=" + DEFAULT_COMPANYPHONE);

        // Get all the customerDetailsList where companyphone equals to UPDATED_COMPANYPHONE
        defaultCustomerDetailsShouldNotBeFound("companyphone.equals=" + UPDATED_COMPANYPHONE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCompanyphoneIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where companyphone in DEFAULT_COMPANYPHONE or UPDATED_COMPANYPHONE
        defaultCustomerDetailsShouldBeFound("companyphone.in=" + DEFAULT_COMPANYPHONE + "," + UPDATED_COMPANYPHONE);

        // Get all the customerDetailsList where companyphone equals to UPDATED_COMPANYPHONE
        defaultCustomerDetailsShouldNotBeFound("companyphone.in=" + UPDATED_COMPANYPHONE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCompanyphoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where companyphone is not null
        defaultCustomerDetailsShouldBeFound("companyphone.specified=true");

        // Get all the customerDetailsList where companyphone is null
        defaultCustomerDetailsShouldNotBeFound("companyphone.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCompanyphoneContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where companyphone contains DEFAULT_COMPANYPHONE
        defaultCustomerDetailsShouldBeFound("companyphone.contains=" + DEFAULT_COMPANYPHONE);

        // Get all the customerDetailsList where companyphone contains UPDATED_COMPANYPHONE
        defaultCustomerDetailsShouldNotBeFound("companyphone.contains=" + UPDATED_COMPANYPHONE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCompanyphoneNotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where companyphone does not contain DEFAULT_COMPANYPHONE
        defaultCustomerDetailsShouldNotBeFound("companyphone.doesNotContain=" + DEFAULT_COMPANYPHONE);

        // Get all the customerDetailsList where companyphone does not contain UPDATED_COMPANYPHONE
        defaultCustomerDetailsShouldBeFound("companyphone.doesNotContain=" + UPDATED_COMPANYPHONE);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where email equals to DEFAULT_EMAIL
        defaultCustomerDetailsShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the customerDetailsList where email equals to UPDATED_EMAIL
        defaultCustomerDetailsShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCustomerDetailsShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the customerDetailsList where email equals to UPDATED_EMAIL
        defaultCustomerDetailsShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where email is not null
        defaultCustomerDetailsShouldBeFound("email.specified=true");

        // Get all the customerDetailsList where email is null
        defaultCustomerDetailsShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByEmailContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where email contains DEFAULT_EMAIL
        defaultCustomerDetailsShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the customerDetailsList where email contains UPDATED_EMAIL
        defaultCustomerDetailsShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where email does not contain DEFAULT_EMAIL
        defaultCustomerDetailsShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the customerDetailsList where email does not contain UPDATED_EMAIL
        defaultCustomerDetailsShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine1IsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine1 equals to DEFAULT_ADDRESS_LINE_1
        defaultCustomerDetailsShouldBeFound("addressLine1.equals=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the customerDetailsList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultCustomerDetailsShouldNotBeFound("addressLine1.equals=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine1IsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine1 in DEFAULT_ADDRESS_LINE_1 or UPDATED_ADDRESS_LINE_1
        defaultCustomerDetailsShouldBeFound("addressLine1.in=" + DEFAULT_ADDRESS_LINE_1 + "," + UPDATED_ADDRESS_LINE_1);

        // Get all the customerDetailsList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultCustomerDetailsShouldNotBeFound("addressLine1.in=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine1IsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine1 is not null
        defaultCustomerDetailsShouldBeFound("addressLine1.specified=true");

        // Get all the customerDetailsList where addressLine1 is null
        defaultCustomerDetailsShouldNotBeFound("addressLine1.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine1ContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine1 contains DEFAULT_ADDRESS_LINE_1
        defaultCustomerDetailsShouldBeFound("addressLine1.contains=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the customerDetailsList where addressLine1 contains UPDATED_ADDRESS_LINE_1
        defaultCustomerDetailsShouldNotBeFound("addressLine1.contains=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine1NotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine1 does not contain DEFAULT_ADDRESS_LINE_1
        defaultCustomerDetailsShouldNotBeFound("addressLine1.doesNotContain=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the customerDetailsList where addressLine1 does not contain UPDATED_ADDRESS_LINE_1
        defaultCustomerDetailsShouldBeFound("addressLine1.doesNotContain=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine2IsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine2 equals to DEFAULT_ADDRESS_LINE_2
        defaultCustomerDetailsShouldBeFound("addressLine2.equals=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the customerDetailsList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultCustomerDetailsShouldNotBeFound("addressLine2.equals=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine2IsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine2 in DEFAULT_ADDRESS_LINE_2 or UPDATED_ADDRESS_LINE_2
        defaultCustomerDetailsShouldBeFound("addressLine2.in=" + DEFAULT_ADDRESS_LINE_2 + "," + UPDATED_ADDRESS_LINE_2);

        // Get all the customerDetailsList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultCustomerDetailsShouldNotBeFound("addressLine2.in=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine2IsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine2 is not null
        defaultCustomerDetailsShouldBeFound("addressLine2.specified=true");

        // Get all the customerDetailsList where addressLine2 is null
        defaultCustomerDetailsShouldNotBeFound("addressLine2.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine2ContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine2 contains DEFAULT_ADDRESS_LINE_2
        defaultCustomerDetailsShouldBeFound("addressLine2.contains=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the customerDetailsList where addressLine2 contains UPDATED_ADDRESS_LINE_2
        defaultCustomerDetailsShouldNotBeFound("addressLine2.contains=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByAddressLine2NotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where addressLine2 does not contain DEFAULT_ADDRESS_LINE_2
        defaultCustomerDetailsShouldNotBeFound("addressLine2.doesNotContain=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the customerDetailsList where addressLine2 does not contain UPDATED_ADDRESS_LINE_2
        defaultCustomerDetailsShouldBeFound("addressLine2.doesNotContain=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where city equals to DEFAULT_CITY
        defaultCustomerDetailsShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the customerDetailsList where city equals to UPDATED_CITY
        defaultCustomerDetailsShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where city in DEFAULT_CITY or UPDATED_CITY
        defaultCustomerDetailsShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the customerDetailsList where city equals to UPDATED_CITY
        defaultCustomerDetailsShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where city is not null
        defaultCustomerDetailsShouldBeFound("city.specified=true");

        // Get all the customerDetailsList where city is null
        defaultCustomerDetailsShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCityContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where city contains DEFAULT_CITY
        defaultCustomerDetailsShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the customerDetailsList where city contains UPDATED_CITY
        defaultCustomerDetailsShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where city does not contain DEFAULT_CITY
        defaultCustomerDetailsShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the customerDetailsList where city does not contain UPDATED_CITY
        defaultCustomerDetailsShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where country equals to DEFAULT_COUNTRY
        defaultCustomerDetailsShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the customerDetailsList where country equals to UPDATED_COUNTRY
        defaultCustomerDetailsShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultCustomerDetailsShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the customerDetailsList where country equals to UPDATED_COUNTRY
        defaultCustomerDetailsShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where country is not null
        defaultCustomerDetailsShouldBeFound("country.specified=true");

        // Get all the customerDetailsList where country is null
        defaultCustomerDetailsShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCountryContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where country contains DEFAULT_COUNTRY
        defaultCustomerDetailsShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the customerDetailsList where country contains UPDATED_COUNTRY
        defaultCustomerDetailsShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        // Get all the customerDetailsList where country does not contain DEFAULT_COUNTRY
        defaultCustomerDetailsShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the customerDetailsList where country does not contain UPDATED_COUNTRY
        defaultCustomerDetailsShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCartIsEqualToSomething() throws Exception {
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            customerDetailsRepository.saveAndFlush(customerDetails);
            cart = CartResourceIT.createEntity(em);
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        em.persist(cart);
        em.flush();
        customerDetails.addCart(cart);
        customerDetailsRepository.saveAndFlush(customerDetails);
        Long cartId = cart.getId();

        // Get all the customerDetailsList where cart equals to cartId
        defaultCustomerDetailsShouldBeFound("cartId.equals=" + cartId);

        // Get all the customerDetailsList where cart equals to (cartId + 1)
        defaultCustomerDetailsShouldNotBeFound("cartId.equals=" + (cartId + 1));
    }

    @Test
    @Transactional
    void getAllCustomerDetailsByCustomerpaymentsIsEqualToSomething() throws Exception {
        Customerpayments customerpayments;
        if (TestUtil.findAll(em, Customerpayments.class).isEmpty()) {
            customerDetailsRepository.saveAndFlush(customerDetails);
            customerpayments = CustomerpaymentsResourceIT.createEntity(em);
        } else {
            customerpayments = TestUtil.findAll(em, Customerpayments.class).get(0);
        }
        em.persist(customerpayments);
        em.flush();
        customerDetails.addCustomerpayments(customerpayments);
        customerDetailsRepository.saveAndFlush(customerDetails);
        Long customerpaymentsId = customerpayments.getId();

        // Get all the customerDetailsList where customerpayments equals to customerpaymentsId
        defaultCustomerDetailsShouldBeFound("customerpaymentsId.equals=" + customerpaymentsId);

        // Get all the customerDetailsList where customerpayments equals to (customerpaymentsId + 1)
        defaultCustomerDetailsShouldNotBeFound("customerpaymentsId.equals=" + (customerpaymentsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerDetailsShouldBeFound(String filter) throws Exception {
        restCustomerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].plates").value(hasItem(DEFAULT_PLATES)))
            .andExpect(jsonPath("$.[*].customertype").value(hasItem(DEFAULT_CUSTOMERTYPE.toString())))
            .andExpect(jsonPath("$.[*].lastseen").value(hasItem(DEFAULT_LASTSEEN.toString())))
            .andExpect(jsonPath("$.[*].firstseen").value(hasItem(DEFAULT_FIRSTSEEN.toString())))
            .andExpect(jsonPath("$.[*].proselesysis").value(hasItem(DEFAULT_PROSELESYSIS.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].companyphone").value(hasItem(DEFAULT_COMPANYPHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));

        // Check, that the count call also returns 1
        restCustomerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerDetailsShouldNotBeFound(String filter) throws Exception {
        restCustomerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomerDetails() throws Exception {
        // Get the customerDetails
        restCustomerDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();

        // Update the customerDetails
        CustomerDetails updatedCustomerDetails = customerDetailsRepository.findById(customerDetails.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerDetails are not directly saved in db
        em.detach(updatedCustomerDetails);
        updatedCustomerDetails
            .name(UPDATED_NAME)
            .plates(UPDATED_PLATES)
            .customertype(UPDATED_CUSTOMERTYPE)
            .lastseen(UPDATED_LASTSEEN)
            .firstseen(UPDATED_FIRSTSEEN)
            .proselesysis(UPDATED_PROSELESYSIS)
            .mobile(UPDATED_MOBILE)
            .phone(UPDATED_PHONE)
            .companyphone(UPDATED_COMPANYPHONE)
            .email(UPDATED_EMAIL)
            .notes(UPDATED_NOTES)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);

        restCustomerDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomerDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomerDetails))
            )
            .andExpect(status().isOk());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
        CustomerDetails testCustomerDetails = customerDetailsList.get(customerDetailsList.size() - 1);
        assertThat(testCustomerDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerDetails.getPlates()).isEqualTo(UPDATED_PLATES);
        assertThat(testCustomerDetails.getCustomertype()).isEqualTo(UPDATED_CUSTOMERTYPE);
        assertThat(testCustomerDetails.getLastseen()).isEqualTo(UPDATED_LASTSEEN);
        assertThat(testCustomerDetails.getFirstseen()).isEqualTo(UPDATED_FIRSTSEEN);
        assertThat(testCustomerDetails.getProselesysis()).isEqualTo(UPDATED_PROSELESYSIS);
        assertThat(testCustomerDetails.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCustomerDetails.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomerDetails.getCompanyphone()).isEqualTo(UPDATED_COMPANYPHONE);
        assertThat(testCustomerDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerDetails.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomerDetails.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testCustomerDetails.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testCustomerDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomerDetails.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void putNonExistingCustomerDetails() throws Exception {
        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();
        customerDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerDetails() throws Exception {
        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();
        customerDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerDetails() throws Exception {
        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();
        customerDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerDetailsWithPatch() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();

        // Update the customerDetails using partial update
        CustomerDetails partialUpdatedCustomerDetails = new CustomerDetails();
        partialUpdatedCustomerDetails.setId(customerDetails.getId());

        partialUpdatedCustomerDetails
            .plates(UPDATED_PLATES)
            .customertype(UPDATED_CUSTOMERTYPE)
            .lastseen(UPDATED_LASTSEEN)
            .proselesysis(UPDATED_PROSELESYSIS)
            .mobile(UPDATED_MOBILE)
            .companyphone(UPDATED_COMPANYPHONE)
            .notes(UPDATED_NOTES)
            .city(UPDATED_CITY);

        restCustomerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerDetails))
            )
            .andExpect(status().isOk());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
        CustomerDetails testCustomerDetails = customerDetailsList.get(customerDetailsList.size() - 1);
        assertThat(testCustomerDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerDetails.getPlates()).isEqualTo(UPDATED_PLATES);
        assertThat(testCustomerDetails.getCustomertype()).isEqualTo(UPDATED_CUSTOMERTYPE);
        assertThat(testCustomerDetails.getLastseen()).isEqualTo(UPDATED_LASTSEEN);
        assertThat(testCustomerDetails.getFirstseen()).isEqualTo(DEFAULT_FIRSTSEEN);
        assertThat(testCustomerDetails.getProselesysis()).isEqualTo(UPDATED_PROSELESYSIS);
        assertThat(testCustomerDetails.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCustomerDetails.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomerDetails.getCompanyphone()).isEqualTo(UPDATED_COMPANYPHONE);
        assertThat(testCustomerDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerDetails.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomerDetails.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testCustomerDetails.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testCustomerDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomerDetails.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void fullUpdateCustomerDetailsWithPatch() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();

        // Update the customerDetails using partial update
        CustomerDetails partialUpdatedCustomerDetails = new CustomerDetails();
        partialUpdatedCustomerDetails.setId(customerDetails.getId());

        partialUpdatedCustomerDetails
            .name(UPDATED_NAME)
            .plates(UPDATED_PLATES)
            .customertype(UPDATED_CUSTOMERTYPE)
            .lastseen(UPDATED_LASTSEEN)
            .firstseen(UPDATED_FIRSTSEEN)
            .proselesysis(UPDATED_PROSELESYSIS)
            .mobile(UPDATED_MOBILE)
            .phone(UPDATED_PHONE)
            .companyphone(UPDATED_COMPANYPHONE)
            .email(UPDATED_EMAIL)
            .notes(UPDATED_NOTES)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);

        restCustomerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerDetails))
            )
            .andExpect(status().isOk());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
        CustomerDetails testCustomerDetails = customerDetailsList.get(customerDetailsList.size() - 1);
        assertThat(testCustomerDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerDetails.getPlates()).isEqualTo(UPDATED_PLATES);
        assertThat(testCustomerDetails.getCustomertype()).isEqualTo(UPDATED_CUSTOMERTYPE);
        assertThat(testCustomerDetails.getLastseen()).isEqualTo(UPDATED_LASTSEEN);
        assertThat(testCustomerDetails.getFirstseen()).isEqualTo(UPDATED_FIRSTSEEN);
        assertThat(testCustomerDetails.getProselesysis()).isEqualTo(UPDATED_PROSELESYSIS);
        assertThat(testCustomerDetails.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCustomerDetails.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomerDetails.getCompanyphone()).isEqualTo(UPDATED_COMPANYPHONE);
        assertThat(testCustomerDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerDetails.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomerDetails.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testCustomerDetails.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testCustomerDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomerDetails.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void patchNonExistingCustomerDetails() throws Exception {
        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();
        customerDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerDetails() throws Exception {
        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();
        customerDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerDetails() throws Exception {
        int databaseSizeBeforeUpdate = customerDetailsRepository.findAll().size();
        customerDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerDetails in the database
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerDetails() throws Exception {
        // Initialize the database
        customerDetailsRepository.saveAndFlush(customerDetails);

        int databaseSizeBeforeDelete = customerDetailsRepository.findAll().size();

        // Delete the customerDetails
        restCustomerDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerDetails> customerDetailsList = customerDetailsRepository.findAll();
        assertThat(customerDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
