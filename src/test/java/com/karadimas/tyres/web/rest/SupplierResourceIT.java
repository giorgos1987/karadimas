package com.karadimas.tyres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Supplier;
import com.karadimas.tyres.domain.Supplierpayments;
import com.karadimas.tyres.repository.SupplierRepository;
import com.karadimas.tyres.service.criteria.SupplierCriteria;
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
 * Integration tests for the {@link SupplierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SupplierResourceIT {

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_PAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FAX_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_REGION = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_REGION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENTS_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/suppliers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierMockMvc;

    private Supplier supplier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createEntity(EntityManager em) {
        Supplier supplier = new Supplier()
            .company(DEFAULT_COMPANY)
            .webPage(DEFAULT_WEB_PAGE)
            .notes(DEFAULT_NOTES)
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .jobTitle(DEFAULT_JOB_TITLE)
            .businessPhone(DEFAULT_BUSINESS_PHONE)
            .homePhone(DEFAULT_HOME_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .zipPostalCode(DEFAULT_ZIP_POSTAL_CODE)
            .countryRegion(DEFAULT_COUNTRY_REGION)
            .attachments(DEFAULT_ATTACHMENTS)
            .attachmentsContentType(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        return supplier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createUpdatedEntity(EntityManager em) {
        Supplier supplier = new Supplier()
            .company(UPDATED_COMPANY)
            .webPage(UPDATED_WEB_PAGE)
            .notes(UPDATED_NOTES)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .jobTitle(UPDATED_JOB_TITLE)
            .businessPhone(UPDATED_BUSINESS_PHONE)
            .homePhone(UPDATED_HOME_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .faxNumber(UPDATED_FAX_NUMBER)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .zipPostalCode(UPDATED_ZIP_POSTAL_CODE)
            .countryRegion(UPDATED_COUNTRY_REGION)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        return supplier;
    }

    @BeforeEach
    public void initTest() {
        supplier = createEntity(em);
    }

    @Test
    @Transactional
    void createSupplier() throws Exception {
        int databaseSizeBeforeCreate = supplierRepository.findAll().size();
        // Create the Supplier
        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplier)))
            .andExpect(status().isCreated());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeCreate + 1);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testSupplier.getWebPage()).isEqualTo(DEFAULT_WEB_PAGE);
        assertThat(testSupplier.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testSupplier.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSupplier.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSupplier.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testSupplier.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testSupplier.getBusinessPhone()).isEqualTo(DEFAULT_BUSINESS_PHONE);
        assertThat(testSupplier.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testSupplier.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testSupplier.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testSupplier.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSupplier.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSupplier.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testSupplier.getZipPostalCode()).isEqualTo(DEFAULT_ZIP_POSTAL_CODE);
        assertThat(testSupplier.getCountryRegion()).isEqualTo(DEFAULT_COUNTRY_REGION);
        assertThat(testSupplier.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testSupplier.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createSupplierWithExistingId() throws Exception {
        // Create the Supplier with an existing ID
        supplier.setId(1L);

        int databaseSizeBeforeCreate = supplierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplier)))
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSuppliers() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].webPage").value(hasItem(DEFAULT_WEB_PAGE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].businessPhone").value(hasItem(DEFAULT_BUSINESS_PHONE)))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE)))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].zipPostalCode").value(hasItem(DEFAULT_ZIP_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].countryRegion").value(hasItem(DEFAULT_COUNTRY_REGION)))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))));
    }

    @Test
    @Transactional
    void getSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get the supplier
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL_ID, supplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplier.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.webPage").value(DEFAULT_WEB_PAGE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE))
            .andExpect(jsonPath("$.businessPhone").value(DEFAULT_BUSINESS_PHONE))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE))
            .andExpect(jsonPath("$.zipPostalCode").value(DEFAULT_ZIP_POSTAL_CODE))
            .andExpect(jsonPath("$.countryRegion").value(DEFAULT_COUNTRY_REGION))
            .andExpect(jsonPath("$.attachmentsContentType").value(DEFAULT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachments").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS)));
    }

    @Test
    @Transactional
    void getSuppliersByIdFiltering() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        Long id = supplier.getId();

        defaultSupplierShouldBeFound("id.equals=" + id);
        defaultSupplierShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSuppliersByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where company equals to DEFAULT_COMPANY
        defaultSupplierShouldBeFound("company.equals=" + DEFAULT_COMPANY);

        // Get all the supplierList where company equals to UPDATED_COMPANY
        defaultSupplierShouldNotBeFound("company.equals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllSuppliersByCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where company in DEFAULT_COMPANY or UPDATED_COMPANY
        defaultSupplierShouldBeFound("company.in=" + DEFAULT_COMPANY + "," + UPDATED_COMPANY);

        // Get all the supplierList where company equals to UPDATED_COMPANY
        defaultSupplierShouldNotBeFound("company.in=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllSuppliersByCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where company is not null
        defaultSupplierShouldBeFound("company.specified=true");

        // Get all the supplierList where company is null
        defaultSupplierShouldNotBeFound("company.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByCompanyContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where company contains DEFAULT_COMPANY
        defaultSupplierShouldBeFound("company.contains=" + DEFAULT_COMPANY);

        // Get all the supplierList where company contains UPDATED_COMPANY
        defaultSupplierShouldNotBeFound("company.contains=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllSuppliersByCompanyNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where company does not contain DEFAULT_COMPANY
        defaultSupplierShouldNotBeFound("company.doesNotContain=" + DEFAULT_COMPANY);

        // Get all the supplierList where company does not contain UPDATED_COMPANY
        defaultSupplierShouldBeFound("company.doesNotContain=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastName equals to DEFAULT_LAST_NAME
        defaultSupplierShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the supplierList where lastName equals to UPDATED_LAST_NAME
        defaultSupplierShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultSupplierShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the supplierList where lastName equals to UPDATED_LAST_NAME
        defaultSupplierShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastName is not null
        defaultSupplierShouldBeFound("lastName.specified=true");

        // Get all the supplierList where lastName is null
        defaultSupplierShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastName contains DEFAULT_LAST_NAME
        defaultSupplierShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the supplierList where lastName contains UPDATED_LAST_NAME
        defaultSupplierShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastName does not contain DEFAULT_LAST_NAME
        defaultSupplierShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the supplierList where lastName does not contain UPDATED_LAST_NAME
        defaultSupplierShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where firstName equals to DEFAULT_FIRST_NAME
        defaultSupplierShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the supplierList where firstName equals to UPDATED_FIRST_NAME
        defaultSupplierShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultSupplierShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the supplierList where firstName equals to UPDATED_FIRST_NAME
        defaultSupplierShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where firstName is not null
        defaultSupplierShouldBeFound("firstName.specified=true");

        // Get all the supplierList where firstName is null
        defaultSupplierShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where firstName contains DEFAULT_FIRST_NAME
        defaultSupplierShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the supplierList where firstName contains UPDATED_FIRST_NAME
        defaultSupplierShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where firstName does not contain DEFAULT_FIRST_NAME
        defaultSupplierShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the supplierList where firstName does not contain UPDATED_FIRST_NAME
        defaultSupplierShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultSupplierShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the supplierList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultSupplierShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultSupplierShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the supplierList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultSupplierShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where emailAddress is not null
        defaultSupplierShouldBeFound("emailAddress.specified=true");

        // Get all the supplierList where emailAddress is null
        defaultSupplierShouldNotBeFound("emailAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultSupplierShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the supplierList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultSupplierShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultSupplierShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the supplierList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultSupplierShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllSuppliersByJobTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where jobTitle equals to DEFAULT_JOB_TITLE
        defaultSupplierShouldBeFound("jobTitle.equals=" + DEFAULT_JOB_TITLE);

        // Get all the supplierList where jobTitle equals to UPDATED_JOB_TITLE
        defaultSupplierShouldNotBeFound("jobTitle.equals=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllSuppliersByJobTitleIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where jobTitle in DEFAULT_JOB_TITLE or UPDATED_JOB_TITLE
        defaultSupplierShouldBeFound("jobTitle.in=" + DEFAULT_JOB_TITLE + "," + UPDATED_JOB_TITLE);

        // Get all the supplierList where jobTitle equals to UPDATED_JOB_TITLE
        defaultSupplierShouldNotBeFound("jobTitle.in=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllSuppliersByJobTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where jobTitle is not null
        defaultSupplierShouldBeFound("jobTitle.specified=true");

        // Get all the supplierList where jobTitle is null
        defaultSupplierShouldNotBeFound("jobTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByJobTitleContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where jobTitle contains DEFAULT_JOB_TITLE
        defaultSupplierShouldBeFound("jobTitle.contains=" + DEFAULT_JOB_TITLE);

        // Get all the supplierList where jobTitle contains UPDATED_JOB_TITLE
        defaultSupplierShouldNotBeFound("jobTitle.contains=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllSuppliersByJobTitleNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where jobTitle does not contain DEFAULT_JOB_TITLE
        defaultSupplierShouldNotBeFound("jobTitle.doesNotContain=" + DEFAULT_JOB_TITLE);

        // Get all the supplierList where jobTitle does not contain UPDATED_JOB_TITLE
        defaultSupplierShouldBeFound("jobTitle.doesNotContain=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllSuppliersByBusinessPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where businessPhone equals to DEFAULT_BUSINESS_PHONE
        defaultSupplierShouldBeFound("businessPhone.equals=" + DEFAULT_BUSINESS_PHONE);

        // Get all the supplierList where businessPhone equals to UPDATED_BUSINESS_PHONE
        defaultSupplierShouldNotBeFound("businessPhone.equals=" + UPDATED_BUSINESS_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByBusinessPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where businessPhone in DEFAULT_BUSINESS_PHONE or UPDATED_BUSINESS_PHONE
        defaultSupplierShouldBeFound("businessPhone.in=" + DEFAULT_BUSINESS_PHONE + "," + UPDATED_BUSINESS_PHONE);

        // Get all the supplierList where businessPhone equals to UPDATED_BUSINESS_PHONE
        defaultSupplierShouldNotBeFound("businessPhone.in=" + UPDATED_BUSINESS_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByBusinessPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where businessPhone is not null
        defaultSupplierShouldBeFound("businessPhone.specified=true");

        // Get all the supplierList where businessPhone is null
        defaultSupplierShouldNotBeFound("businessPhone.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByBusinessPhoneContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where businessPhone contains DEFAULT_BUSINESS_PHONE
        defaultSupplierShouldBeFound("businessPhone.contains=" + DEFAULT_BUSINESS_PHONE);

        // Get all the supplierList where businessPhone contains UPDATED_BUSINESS_PHONE
        defaultSupplierShouldNotBeFound("businessPhone.contains=" + UPDATED_BUSINESS_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByBusinessPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where businessPhone does not contain DEFAULT_BUSINESS_PHONE
        defaultSupplierShouldNotBeFound("businessPhone.doesNotContain=" + DEFAULT_BUSINESS_PHONE);

        // Get all the supplierList where businessPhone does not contain UPDATED_BUSINESS_PHONE
        defaultSupplierShouldBeFound("businessPhone.doesNotContain=" + UPDATED_BUSINESS_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByHomePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where homePhone equals to DEFAULT_HOME_PHONE
        defaultSupplierShouldBeFound("homePhone.equals=" + DEFAULT_HOME_PHONE);

        // Get all the supplierList where homePhone equals to UPDATED_HOME_PHONE
        defaultSupplierShouldNotBeFound("homePhone.equals=" + UPDATED_HOME_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByHomePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where homePhone in DEFAULT_HOME_PHONE or UPDATED_HOME_PHONE
        defaultSupplierShouldBeFound("homePhone.in=" + DEFAULT_HOME_PHONE + "," + UPDATED_HOME_PHONE);

        // Get all the supplierList where homePhone equals to UPDATED_HOME_PHONE
        defaultSupplierShouldNotBeFound("homePhone.in=" + UPDATED_HOME_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByHomePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where homePhone is not null
        defaultSupplierShouldBeFound("homePhone.specified=true");

        // Get all the supplierList where homePhone is null
        defaultSupplierShouldNotBeFound("homePhone.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByHomePhoneContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where homePhone contains DEFAULT_HOME_PHONE
        defaultSupplierShouldBeFound("homePhone.contains=" + DEFAULT_HOME_PHONE);

        // Get all the supplierList where homePhone contains UPDATED_HOME_PHONE
        defaultSupplierShouldNotBeFound("homePhone.contains=" + UPDATED_HOME_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByHomePhoneNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where homePhone does not contain DEFAULT_HOME_PHONE
        defaultSupplierShouldNotBeFound("homePhone.doesNotContain=" + DEFAULT_HOME_PHONE);

        // Get all the supplierList where homePhone does not contain UPDATED_HOME_PHONE
        defaultSupplierShouldBeFound("homePhone.doesNotContain=" + UPDATED_HOME_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByMobilePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where mobilePhone equals to DEFAULT_MOBILE_PHONE
        defaultSupplierShouldBeFound("mobilePhone.equals=" + DEFAULT_MOBILE_PHONE);

        // Get all the supplierList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultSupplierShouldNotBeFound("mobilePhone.equals=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByMobilePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where mobilePhone in DEFAULT_MOBILE_PHONE or UPDATED_MOBILE_PHONE
        defaultSupplierShouldBeFound("mobilePhone.in=" + DEFAULT_MOBILE_PHONE + "," + UPDATED_MOBILE_PHONE);

        // Get all the supplierList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultSupplierShouldNotBeFound("mobilePhone.in=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByMobilePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where mobilePhone is not null
        defaultSupplierShouldBeFound("mobilePhone.specified=true");

        // Get all the supplierList where mobilePhone is null
        defaultSupplierShouldNotBeFound("mobilePhone.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByMobilePhoneContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where mobilePhone contains DEFAULT_MOBILE_PHONE
        defaultSupplierShouldBeFound("mobilePhone.contains=" + DEFAULT_MOBILE_PHONE);

        // Get all the supplierList where mobilePhone contains UPDATED_MOBILE_PHONE
        defaultSupplierShouldNotBeFound("mobilePhone.contains=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByMobilePhoneNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where mobilePhone does not contain DEFAULT_MOBILE_PHONE
        defaultSupplierShouldNotBeFound("mobilePhone.doesNotContain=" + DEFAULT_MOBILE_PHONE);

        // Get all the supplierList where mobilePhone does not contain UPDATED_MOBILE_PHONE
        defaultSupplierShouldBeFound("mobilePhone.doesNotContain=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    void getAllSuppliersByFaxNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where faxNumber equals to DEFAULT_FAX_NUMBER
        defaultSupplierShouldBeFound("faxNumber.equals=" + DEFAULT_FAX_NUMBER);

        // Get all the supplierList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultSupplierShouldNotBeFound("faxNumber.equals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersByFaxNumberIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where faxNumber in DEFAULT_FAX_NUMBER or UPDATED_FAX_NUMBER
        defaultSupplierShouldBeFound("faxNumber.in=" + DEFAULT_FAX_NUMBER + "," + UPDATED_FAX_NUMBER);

        // Get all the supplierList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultSupplierShouldNotBeFound("faxNumber.in=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersByFaxNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where faxNumber is not null
        defaultSupplierShouldBeFound("faxNumber.specified=true");

        // Get all the supplierList where faxNumber is null
        defaultSupplierShouldNotBeFound("faxNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByFaxNumberContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where faxNumber contains DEFAULT_FAX_NUMBER
        defaultSupplierShouldBeFound("faxNumber.contains=" + DEFAULT_FAX_NUMBER);

        // Get all the supplierList where faxNumber contains UPDATED_FAX_NUMBER
        defaultSupplierShouldNotBeFound("faxNumber.contains=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersByFaxNumberNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where faxNumber does not contain DEFAULT_FAX_NUMBER
        defaultSupplierShouldNotBeFound("faxNumber.doesNotContain=" + DEFAULT_FAX_NUMBER);

        // Get all the supplierList where faxNumber does not contain UPDATED_FAX_NUMBER
        defaultSupplierShouldBeFound("faxNumber.doesNotContain=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllSuppliersByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where city equals to DEFAULT_CITY
        defaultSupplierShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the supplierList where city equals to UPDATED_CITY
        defaultSupplierShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllSuppliersByCityIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where city in DEFAULT_CITY or UPDATED_CITY
        defaultSupplierShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the supplierList where city equals to UPDATED_CITY
        defaultSupplierShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllSuppliersByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where city is not null
        defaultSupplierShouldBeFound("city.specified=true");

        // Get all the supplierList where city is null
        defaultSupplierShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByCityContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where city contains DEFAULT_CITY
        defaultSupplierShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the supplierList where city contains UPDATED_CITY
        defaultSupplierShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllSuppliersByCityNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where city does not contain DEFAULT_CITY
        defaultSupplierShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the supplierList where city does not contain UPDATED_CITY
        defaultSupplierShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllSuppliersByStateProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where stateProvince equals to DEFAULT_STATE_PROVINCE
        defaultSupplierShouldBeFound("stateProvince.equals=" + DEFAULT_STATE_PROVINCE);

        // Get all the supplierList where stateProvince equals to UPDATED_STATE_PROVINCE
        defaultSupplierShouldNotBeFound("stateProvince.equals=" + UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    void getAllSuppliersByStateProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where stateProvince in DEFAULT_STATE_PROVINCE or UPDATED_STATE_PROVINCE
        defaultSupplierShouldBeFound("stateProvince.in=" + DEFAULT_STATE_PROVINCE + "," + UPDATED_STATE_PROVINCE);

        // Get all the supplierList where stateProvince equals to UPDATED_STATE_PROVINCE
        defaultSupplierShouldNotBeFound("stateProvince.in=" + UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    void getAllSuppliersByStateProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where stateProvince is not null
        defaultSupplierShouldBeFound("stateProvince.specified=true");

        // Get all the supplierList where stateProvince is null
        defaultSupplierShouldNotBeFound("stateProvince.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByStateProvinceContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where stateProvince contains DEFAULT_STATE_PROVINCE
        defaultSupplierShouldBeFound("stateProvince.contains=" + DEFAULT_STATE_PROVINCE);

        // Get all the supplierList where stateProvince contains UPDATED_STATE_PROVINCE
        defaultSupplierShouldNotBeFound("stateProvince.contains=" + UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    void getAllSuppliersByStateProvinceNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where stateProvince does not contain DEFAULT_STATE_PROVINCE
        defaultSupplierShouldNotBeFound("stateProvince.doesNotContain=" + DEFAULT_STATE_PROVINCE);

        // Get all the supplierList where stateProvince does not contain UPDATED_STATE_PROVINCE
        defaultSupplierShouldBeFound("stateProvince.doesNotContain=" + UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    void getAllSuppliersByZipPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where zipPostalCode equals to DEFAULT_ZIP_POSTAL_CODE
        defaultSupplierShouldBeFound("zipPostalCode.equals=" + DEFAULT_ZIP_POSTAL_CODE);

        // Get all the supplierList where zipPostalCode equals to UPDATED_ZIP_POSTAL_CODE
        defaultSupplierShouldNotBeFound("zipPostalCode.equals=" + UPDATED_ZIP_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersByZipPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where zipPostalCode in DEFAULT_ZIP_POSTAL_CODE or UPDATED_ZIP_POSTAL_CODE
        defaultSupplierShouldBeFound("zipPostalCode.in=" + DEFAULT_ZIP_POSTAL_CODE + "," + UPDATED_ZIP_POSTAL_CODE);

        // Get all the supplierList where zipPostalCode equals to UPDATED_ZIP_POSTAL_CODE
        defaultSupplierShouldNotBeFound("zipPostalCode.in=" + UPDATED_ZIP_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersByZipPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where zipPostalCode is not null
        defaultSupplierShouldBeFound("zipPostalCode.specified=true");

        // Get all the supplierList where zipPostalCode is null
        defaultSupplierShouldNotBeFound("zipPostalCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByZipPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where zipPostalCode contains DEFAULT_ZIP_POSTAL_CODE
        defaultSupplierShouldBeFound("zipPostalCode.contains=" + DEFAULT_ZIP_POSTAL_CODE);

        // Get all the supplierList where zipPostalCode contains UPDATED_ZIP_POSTAL_CODE
        defaultSupplierShouldNotBeFound("zipPostalCode.contains=" + UPDATED_ZIP_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersByZipPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where zipPostalCode does not contain DEFAULT_ZIP_POSTAL_CODE
        defaultSupplierShouldNotBeFound("zipPostalCode.doesNotContain=" + DEFAULT_ZIP_POSTAL_CODE);

        // Get all the supplierList where zipPostalCode does not contain UPDATED_ZIP_POSTAL_CODE
        defaultSupplierShouldBeFound("zipPostalCode.doesNotContain=" + UPDATED_ZIP_POSTAL_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersByCountryRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where countryRegion equals to DEFAULT_COUNTRY_REGION
        defaultSupplierShouldBeFound("countryRegion.equals=" + DEFAULT_COUNTRY_REGION);

        // Get all the supplierList where countryRegion equals to UPDATED_COUNTRY_REGION
        defaultSupplierShouldNotBeFound("countryRegion.equals=" + UPDATED_COUNTRY_REGION);
    }

    @Test
    @Transactional
    void getAllSuppliersByCountryRegionIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where countryRegion in DEFAULT_COUNTRY_REGION or UPDATED_COUNTRY_REGION
        defaultSupplierShouldBeFound("countryRegion.in=" + DEFAULT_COUNTRY_REGION + "," + UPDATED_COUNTRY_REGION);

        // Get all the supplierList where countryRegion equals to UPDATED_COUNTRY_REGION
        defaultSupplierShouldNotBeFound("countryRegion.in=" + UPDATED_COUNTRY_REGION);
    }

    @Test
    @Transactional
    void getAllSuppliersByCountryRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where countryRegion is not null
        defaultSupplierShouldBeFound("countryRegion.specified=true");

        // Get all the supplierList where countryRegion is null
        defaultSupplierShouldNotBeFound("countryRegion.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByCountryRegionContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where countryRegion contains DEFAULT_COUNTRY_REGION
        defaultSupplierShouldBeFound("countryRegion.contains=" + DEFAULT_COUNTRY_REGION);

        // Get all the supplierList where countryRegion contains UPDATED_COUNTRY_REGION
        defaultSupplierShouldNotBeFound("countryRegion.contains=" + UPDATED_COUNTRY_REGION);
    }

    @Test
    @Transactional
    void getAllSuppliersByCountryRegionNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where countryRegion does not contain DEFAULT_COUNTRY_REGION
        defaultSupplierShouldNotBeFound("countryRegion.doesNotContain=" + DEFAULT_COUNTRY_REGION);

        // Get all the supplierList where countryRegion does not contain UPDATED_COUNTRY_REGION
        defaultSupplierShouldBeFound("countryRegion.doesNotContain=" + UPDATED_COUNTRY_REGION);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierpaymentsIsEqualToSomething() throws Exception {
        Supplierpayments supplierpayments;
        if (TestUtil.findAll(em, Supplierpayments.class).isEmpty()) {
            supplierRepository.saveAndFlush(supplier);
            supplierpayments = SupplierpaymentsResourceIT.createEntity(em);
        } else {
            supplierpayments = TestUtil.findAll(em, Supplierpayments.class).get(0);
        }
        em.persist(supplierpayments);
        em.flush();
        supplier.addSupplierpayments(supplierpayments);
        supplierRepository.saveAndFlush(supplier);
        Long supplierpaymentsId = supplierpayments.getId();

        // Get all the supplierList where supplierpayments equals to supplierpaymentsId
        defaultSupplierShouldBeFound("supplierpaymentsId.equals=" + supplierpaymentsId);

        // Get all the supplierList where supplierpayments equals to (supplierpaymentsId + 1)
        defaultSupplierShouldNotBeFound("supplierpaymentsId.equals=" + (supplierpaymentsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierShouldBeFound(String filter) throws Exception {
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].webPage").value(hasItem(DEFAULT_WEB_PAGE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].businessPhone").value(hasItem(DEFAULT_BUSINESS_PHONE)))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE)))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].zipPostalCode").value(hasItem(DEFAULT_ZIP_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].countryRegion").value(hasItem(DEFAULT_COUNTRY_REGION)))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))));

        // Check, that the count call also returns 1
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierShouldNotBeFound(String filter) throws Exception {
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSupplier() throws Exception {
        // Get the supplier
        restSupplierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the supplier
        Supplier updatedSupplier = supplierRepository.findById(supplier.getId()).get();
        // Disconnect from session so that the updates on updatedSupplier are not directly saved in db
        em.detach(updatedSupplier);
        updatedSupplier
            .company(UPDATED_COMPANY)
            .webPage(UPDATED_WEB_PAGE)
            .notes(UPDATED_NOTES)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .jobTitle(UPDATED_JOB_TITLE)
            .businessPhone(UPDATED_BUSINESS_PHONE)
            .homePhone(UPDATED_HOME_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .faxNumber(UPDATED_FAX_NUMBER)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .zipPostalCode(UPDATED_ZIP_POSTAL_CODE)
            .countryRegion(UPDATED_COUNTRY_REGION)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE);

        restSupplierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSupplier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSupplier))
            )
            .andExpect(status().isOk());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testSupplier.getWebPage()).isEqualTo(UPDATED_WEB_PAGE);
        assertThat(testSupplier.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testSupplier.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSupplier.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSupplier.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testSupplier.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testSupplier.getBusinessPhone()).isEqualTo(UPDATED_BUSINESS_PHONE);
        assertThat(testSupplier.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testSupplier.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testSupplier.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testSupplier.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSupplier.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSupplier.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testSupplier.getZipPostalCode()).isEqualTo(UPDATED_ZIP_POSTAL_CODE);
        assertThat(testSupplier.getCountryRegion()).isEqualTo(UPDATED_COUNTRY_REGION);
        assertThat(testSupplier.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testSupplier.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplierWithPatch() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the supplier using partial update
        Supplier partialUpdatedSupplier = new Supplier();
        partialUpdatedSupplier.setId(supplier.getId());

        partialUpdatedSupplier
            .webPage(UPDATED_WEB_PAGE)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .jobTitle(UPDATED_JOB_TITLE)
            .businessPhone(UPDATED_BUSINESS_PHONE)
            .homePhone(UPDATED_HOME_PHONE)
            .faxNumber(UPDATED_FAX_NUMBER)
            .zipPostalCode(UPDATED_ZIP_POSTAL_CODE)
            .countryRegion(UPDATED_COUNTRY_REGION);

        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplier))
            )
            .andExpect(status().isOk());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testSupplier.getWebPage()).isEqualTo(UPDATED_WEB_PAGE);
        assertThat(testSupplier.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testSupplier.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSupplier.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSupplier.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testSupplier.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testSupplier.getBusinessPhone()).isEqualTo(UPDATED_BUSINESS_PHONE);
        assertThat(testSupplier.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testSupplier.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testSupplier.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testSupplier.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSupplier.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSupplier.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testSupplier.getZipPostalCode()).isEqualTo(UPDATED_ZIP_POSTAL_CODE);
        assertThat(testSupplier.getCountryRegion()).isEqualTo(UPDATED_COUNTRY_REGION);
        assertThat(testSupplier.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testSupplier.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateSupplierWithPatch() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the supplier using partial update
        Supplier partialUpdatedSupplier = new Supplier();
        partialUpdatedSupplier.setId(supplier.getId());

        partialUpdatedSupplier
            .company(UPDATED_COMPANY)
            .webPage(UPDATED_WEB_PAGE)
            .notes(UPDATED_NOTES)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .jobTitle(UPDATED_JOB_TITLE)
            .businessPhone(UPDATED_BUSINESS_PHONE)
            .homePhone(UPDATED_HOME_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .faxNumber(UPDATED_FAX_NUMBER)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .zipPostalCode(UPDATED_ZIP_POSTAL_CODE)
            .countryRegion(UPDATED_COUNTRY_REGION)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE);

        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplier))
            )
            .andExpect(status().isOk());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testSupplier.getWebPage()).isEqualTo(UPDATED_WEB_PAGE);
        assertThat(testSupplier.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testSupplier.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSupplier.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSupplier.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testSupplier.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testSupplier.getBusinessPhone()).isEqualTo(UPDATED_BUSINESS_PHONE);
        assertThat(testSupplier.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testSupplier.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testSupplier.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testSupplier.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSupplier.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSupplier.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testSupplier.getZipPostalCode()).isEqualTo(UPDATED_ZIP_POSTAL_CODE);
        assertThat(testSupplier.getCountryRegion()).isEqualTo(UPDATED_COUNTRY_REGION);
        assertThat(testSupplier.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testSupplier.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplier))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(supplier)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeDelete = supplierRepository.findAll().size();

        // Delete the supplier
        restSupplierMockMvc
            .perform(delete(ENTITY_API_URL_ID, supplier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
