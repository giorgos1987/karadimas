package com.karadimas.tyres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.CompanyProfile;
import com.karadimas.tyres.repository.CompanyProfileRepository;
import com.karadimas.tyres.service.criteria.CompanyProfileCriteria;
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
 * Integration tests for the {@link CompanyProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyProfileResourceIT {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SUBURB = "AAAAAAAAAA";
    private static final String UPDATED_SUBURB = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_INFO = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_INFO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/company-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyProfileMockMvc;

    private CompanyProfile companyProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyProfile createEntity(EntityManager em) {
        CompanyProfile companyProfile = new CompanyProfile()
            .companyName(DEFAULT_COMPANY_NAME)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .suburb(DEFAULT_SUBURB)
            .state(DEFAULT_STATE)
            .postcode(DEFAULT_POSTCODE)
            .phone(DEFAULT_PHONE)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .emailInfo(DEFAULT_EMAIL_INFO)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .language(DEFAULT_LANGUAGE);
        return companyProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyProfile createUpdatedEntity(EntityManager em) {
        CompanyProfile companyProfile = new CompanyProfile()
            .companyName(UPDATED_COMPANY_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .suburb(UPDATED_SUBURB)
            .state(UPDATED_STATE)
            .postcode(UPDATED_POSTCODE)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .emailInfo(UPDATED_EMAIL_INFO)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .language(UPDATED_LANGUAGE);
        return companyProfile;
    }

    @BeforeEach
    public void initTest() {
        companyProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyProfile() throws Exception {
        int databaseSizeBeforeCreate = companyProfileRepository.findAll().size();
        // Create the CompanyProfile
        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfile))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompanyProfile.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testCompanyProfile.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testCompanyProfile.getSuburb()).isEqualTo(DEFAULT_SUBURB);
        assertThat(testCompanyProfile.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCompanyProfile.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testCompanyProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCompanyProfile.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testCompanyProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompanyProfile.getEmailInfo()).isEqualTo(DEFAULT_EMAIL_INFO);
        assertThat(testCompanyProfile.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCompanyProfile.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testCompanyProfile.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void createCompanyProfileWithExistingId() throws Exception {
        // Create the CompanyProfile with an existing ID
        companyProfile.setId(1L);

        int databaseSizeBeforeCreate = companyProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyProfileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanyProfiles() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].emailInfo").value(hasItem(DEFAULT_EMAIL_INFO)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));
    }

    @Test
    @Transactional
    void getCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get the companyProfile
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, companyProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyProfile.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.suburb").value(DEFAULT_SUBURB))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.emailInfo").value(DEFAULT_EMAIL_INFO))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE));
    }

    @Test
    @Transactional
    void getCompanyProfilesByIdFiltering() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        Long id = companyProfile.getId();

        defaultCompanyProfileShouldBeFound("id.equals=" + id);
        defaultCompanyProfileShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyProfileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where companyName equals to DEFAULT_COMPANY_NAME
        defaultCompanyProfileShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the companyProfileList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyProfileShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultCompanyProfileShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the companyProfileList where companyName equals to UPDATED_COMPANY_NAME
        defaultCompanyProfileShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where companyName is not null
        defaultCompanyProfileShouldBeFound("companyName.specified=true");

        // Get all the companyProfileList where companyName is null
        defaultCompanyProfileShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where companyName contains DEFAULT_COMPANY_NAME
        defaultCompanyProfileShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the companyProfileList where companyName contains UPDATED_COMPANY_NAME
        defaultCompanyProfileShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultCompanyProfileShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the companyProfileList where companyName does not contain UPDATED_COMPANY_NAME
        defaultCompanyProfileShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress1IsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address1 equals to DEFAULT_ADDRESS_1
        defaultCompanyProfileShouldBeFound("address1.equals=" + DEFAULT_ADDRESS_1);

        // Get all the companyProfileList where address1 equals to UPDATED_ADDRESS_1
        defaultCompanyProfileShouldNotBeFound("address1.equals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress1IsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address1 in DEFAULT_ADDRESS_1 or UPDATED_ADDRESS_1
        defaultCompanyProfileShouldBeFound("address1.in=" + DEFAULT_ADDRESS_1 + "," + UPDATED_ADDRESS_1);

        // Get all the companyProfileList where address1 equals to UPDATED_ADDRESS_1
        defaultCompanyProfileShouldNotBeFound("address1.in=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress1IsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address1 is not null
        defaultCompanyProfileShouldBeFound("address1.specified=true");

        // Get all the companyProfileList where address1 is null
        defaultCompanyProfileShouldNotBeFound("address1.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress1ContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address1 contains DEFAULT_ADDRESS_1
        defaultCompanyProfileShouldBeFound("address1.contains=" + DEFAULT_ADDRESS_1);

        // Get all the companyProfileList where address1 contains UPDATED_ADDRESS_1
        defaultCompanyProfileShouldNotBeFound("address1.contains=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress1NotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address1 does not contain DEFAULT_ADDRESS_1
        defaultCompanyProfileShouldNotBeFound("address1.doesNotContain=" + DEFAULT_ADDRESS_1);

        // Get all the companyProfileList where address1 does not contain UPDATED_ADDRESS_1
        defaultCompanyProfileShouldBeFound("address1.doesNotContain=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress2IsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address2 equals to DEFAULT_ADDRESS_2
        defaultCompanyProfileShouldBeFound("address2.equals=" + DEFAULT_ADDRESS_2);

        // Get all the companyProfileList where address2 equals to UPDATED_ADDRESS_2
        defaultCompanyProfileShouldNotBeFound("address2.equals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress2IsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address2 in DEFAULT_ADDRESS_2 or UPDATED_ADDRESS_2
        defaultCompanyProfileShouldBeFound("address2.in=" + DEFAULT_ADDRESS_2 + "," + UPDATED_ADDRESS_2);

        // Get all the companyProfileList where address2 equals to UPDATED_ADDRESS_2
        defaultCompanyProfileShouldNotBeFound("address2.in=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress2IsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address2 is not null
        defaultCompanyProfileShouldBeFound("address2.specified=true");

        // Get all the companyProfileList where address2 is null
        defaultCompanyProfileShouldNotBeFound("address2.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress2ContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address2 contains DEFAULT_ADDRESS_2
        defaultCompanyProfileShouldBeFound("address2.contains=" + DEFAULT_ADDRESS_2);

        // Get all the companyProfileList where address2 contains UPDATED_ADDRESS_2
        defaultCompanyProfileShouldNotBeFound("address2.contains=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByAddress2NotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where address2 does not contain DEFAULT_ADDRESS_2
        defaultCompanyProfileShouldNotBeFound("address2.doesNotContain=" + DEFAULT_ADDRESS_2);

        // Get all the companyProfileList where address2 does not contain UPDATED_ADDRESS_2
        defaultCompanyProfileShouldBeFound("address2.doesNotContain=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesBySuburbIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where suburb equals to DEFAULT_SUBURB
        defaultCompanyProfileShouldBeFound("suburb.equals=" + DEFAULT_SUBURB);

        // Get all the companyProfileList where suburb equals to UPDATED_SUBURB
        defaultCompanyProfileShouldNotBeFound("suburb.equals=" + UPDATED_SUBURB);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesBySuburbIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where suburb in DEFAULT_SUBURB or UPDATED_SUBURB
        defaultCompanyProfileShouldBeFound("suburb.in=" + DEFAULT_SUBURB + "," + UPDATED_SUBURB);

        // Get all the companyProfileList where suburb equals to UPDATED_SUBURB
        defaultCompanyProfileShouldNotBeFound("suburb.in=" + UPDATED_SUBURB);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesBySuburbIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where suburb is not null
        defaultCompanyProfileShouldBeFound("suburb.specified=true");

        // Get all the companyProfileList where suburb is null
        defaultCompanyProfileShouldNotBeFound("suburb.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesBySuburbContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where suburb contains DEFAULT_SUBURB
        defaultCompanyProfileShouldBeFound("suburb.contains=" + DEFAULT_SUBURB);

        // Get all the companyProfileList where suburb contains UPDATED_SUBURB
        defaultCompanyProfileShouldNotBeFound("suburb.contains=" + UPDATED_SUBURB);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesBySuburbNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where suburb does not contain DEFAULT_SUBURB
        defaultCompanyProfileShouldNotBeFound("suburb.doesNotContain=" + DEFAULT_SUBURB);

        // Get all the companyProfileList where suburb does not contain UPDATED_SUBURB
        defaultCompanyProfileShouldBeFound("suburb.doesNotContain=" + UPDATED_SUBURB);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where state equals to DEFAULT_STATE
        defaultCompanyProfileShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the companyProfileList where state equals to UPDATED_STATE
        defaultCompanyProfileShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where state in DEFAULT_STATE or UPDATED_STATE
        defaultCompanyProfileShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the companyProfileList where state equals to UPDATED_STATE
        defaultCompanyProfileShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where state is not null
        defaultCompanyProfileShouldBeFound("state.specified=true");

        // Get all the companyProfileList where state is null
        defaultCompanyProfileShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByStateContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where state contains DEFAULT_STATE
        defaultCompanyProfileShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the companyProfileList where state contains UPDATED_STATE
        defaultCompanyProfileShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where state does not contain DEFAULT_STATE
        defaultCompanyProfileShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the companyProfileList where state does not contain UPDATED_STATE
        defaultCompanyProfileShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPostcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where postcode equals to DEFAULT_POSTCODE
        defaultCompanyProfileShouldBeFound("postcode.equals=" + DEFAULT_POSTCODE);

        // Get all the companyProfileList where postcode equals to UPDATED_POSTCODE
        defaultCompanyProfileShouldNotBeFound("postcode.equals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPostcodeIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where postcode in DEFAULT_POSTCODE or UPDATED_POSTCODE
        defaultCompanyProfileShouldBeFound("postcode.in=" + DEFAULT_POSTCODE + "," + UPDATED_POSTCODE);

        // Get all the companyProfileList where postcode equals to UPDATED_POSTCODE
        defaultCompanyProfileShouldNotBeFound("postcode.in=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPostcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where postcode is not null
        defaultCompanyProfileShouldBeFound("postcode.specified=true");

        // Get all the companyProfileList where postcode is null
        defaultCompanyProfileShouldNotBeFound("postcode.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPostcodeContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where postcode contains DEFAULT_POSTCODE
        defaultCompanyProfileShouldBeFound("postcode.contains=" + DEFAULT_POSTCODE);

        // Get all the companyProfileList where postcode contains UPDATED_POSTCODE
        defaultCompanyProfileShouldNotBeFound("postcode.contains=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPostcodeNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where postcode does not contain DEFAULT_POSTCODE
        defaultCompanyProfileShouldNotBeFound("postcode.doesNotContain=" + DEFAULT_POSTCODE);

        // Get all the companyProfileList where postcode does not contain UPDATED_POSTCODE
        defaultCompanyProfileShouldBeFound("postcode.doesNotContain=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where phone equals to DEFAULT_PHONE
        defaultCompanyProfileShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the companyProfileList where phone equals to UPDATED_PHONE
        defaultCompanyProfileShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCompanyProfileShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the companyProfileList where phone equals to UPDATED_PHONE
        defaultCompanyProfileShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where phone is not null
        defaultCompanyProfileShouldBeFound("phone.specified=true");

        // Get all the companyProfileList where phone is null
        defaultCompanyProfileShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where phone contains DEFAULT_PHONE
        defaultCompanyProfileShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the companyProfileList where phone contains UPDATED_PHONE
        defaultCompanyProfileShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where phone does not contain DEFAULT_PHONE
        defaultCompanyProfileShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the companyProfileList where phone does not contain UPDATED_PHONE
        defaultCompanyProfileShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where mobile equals to DEFAULT_MOBILE
        defaultCompanyProfileShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the companyProfileList where mobile equals to UPDATED_MOBILE
        defaultCompanyProfileShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultCompanyProfileShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the companyProfileList where mobile equals to UPDATED_MOBILE
        defaultCompanyProfileShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where mobile is not null
        defaultCompanyProfileShouldBeFound("mobile.specified=true");

        // Get all the companyProfileList where mobile is null
        defaultCompanyProfileShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMobileContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where mobile contains DEFAULT_MOBILE
        defaultCompanyProfileShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the companyProfileList where mobile contains UPDATED_MOBILE
        defaultCompanyProfileShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where mobile does not contain DEFAULT_MOBILE
        defaultCompanyProfileShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the companyProfileList where mobile does not contain UPDATED_MOBILE
        defaultCompanyProfileShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email equals to DEFAULT_EMAIL
        defaultCompanyProfileShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyProfileList where email equals to UPDATED_EMAIL
        defaultCompanyProfileShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyProfileShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyProfileList where email equals to UPDATED_EMAIL
        defaultCompanyProfileShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email is not null
        defaultCompanyProfileShouldBeFound("email.specified=true");

        // Get all the companyProfileList where email is null
        defaultCompanyProfileShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email contains DEFAULT_EMAIL
        defaultCompanyProfileShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the companyProfileList where email contains UPDATED_EMAIL
        defaultCompanyProfileShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where email does not contain DEFAULT_EMAIL
        defaultCompanyProfileShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the companyProfileList where email does not contain UPDATED_EMAIL
        defaultCompanyProfileShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where emailInfo equals to DEFAULT_EMAIL_INFO
        defaultCompanyProfileShouldBeFound("emailInfo.equals=" + DEFAULT_EMAIL_INFO);

        // Get all the companyProfileList where emailInfo equals to UPDATED_EMAIL_INFO
        defaultCompanyProfileShouldNotBeFound("emailInfo.equals=" + UPDATED_EMAIL_INFO);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailInfoIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where emailInfo in DEFAULT_EMAIL_INFO or UPDATED_EMAIL_INFO
        defaultCompanyProfileShouldBeFound("emailInfo.in=" + DEFAULT_EMAIL_INFO + "," + UPDATED_EMAIL_INFO);

        // Get all the companyProfileList where emailInfo equals to UPDATED_EMAIL_INFO
        defaultCompanyProfileShouldNotBeFound("emailInfo.in=" + UPDATED_EMAIL_INFO);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where emailInfo is not null
        defaultCompanyProfileShouldBeFound("emailInfo.specified=true");

        // Get all the companyProfileList where emailInfo is null
        defaultCompanyProfileShouldNotBeFound("emailInfo.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailInfoContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where emailInfo contains DEFAULT_EMAIL_INFO
        defaultCompanyProfileShouldBeFound("emailInfo.contains=" + DEFAULT_EMAIL_INFO);

        // Get all the companyProfileList where emailInfo contains UPDATED_EMAIL_INFO
        defaultCompanyProfileShouldNotBeFound("emailInfo.contains=" + UPDATED_EMAIL_INFO);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByEmailInfoNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where emailInfo does not contain DEFAULT_EMAIL_INFO
        defaultCompanyProfileShouldNotBeFound("emailInfo.doesNotContain=" + DEFAULT_EMAIL_INFO);

        // Get all the companyProfileList where emailInfo does not contain UPDATED_EMAIL_INFO
        defaultCompanyProfileShouldBeFound("emailInfo.doesNotContain=" + UPDATED_EMAIL_INFO);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where language equals to DEFAULT_LANGUAGE
        defaultCompanyProfileShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the companyProfileList where language equals to UPDATED_LANGUAGE
        defaultCompanyProfileShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultCompanyProfileShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the companyProfileList where language equals to UPDATED_LANGUAGE
        defaultCompanyProfileShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where language is not null
        defaultCompanyProfileShouldBeFound("language.specified=true");

        // Get all the companyProfileList where language is null
        defaultCompanyProfileShouldNotBeFound("language.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLanguageContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where language contains DEFAULT_LANGUAGE
        defaultCompanyProfileShouldBeFound("language.contains=" + DEFAULT_LANGUAGE);

        // Get all the companyProfileList where language contains UPDATED_LANGUAGE
        defaultCompanyProfileShouldNotBeFound("language.contains=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllCompanyProfilesByLanguageNotContainsSomething() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList where language does not contain DEFAULT_LANGUAGE
        defaultCompanyProfileShouldNotBeFound("language.doesNotContain=" + DEFAULT_LANGUAGE);

        // Get all the companyProfileList where language does not contain UPDATED_LANGUAGE
        defaultCompanyProfileShouldBeFound("language.doesNotContain=" + UPDATED_LANGUAGE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyProfileShouldBeFound(String filter) throws Exception {
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].emailInfo").value(hasItem(DEFAULT_EMAIL_INFO)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)));

        // Check, that the count call also returns 1
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyProfileShouldNotBeFound(String filter) throws Exception {
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompanyProfile() throws Exception {
        // Get the companyProfile
        restCompanyProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();

        // Update the companyProfile
        CompanyProfile updatedCompanyProfile = companyProfileRepository.findById(companyProfile.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyProfile are not directly saved in db
        em.detach(updatedCompanyProfile);
        updatedCompanyProfile
            .companyName(UPDATED_COMPANY_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .suburb(UPDATED_SUBURB)
            .state(UPDATED_STATE)
            .postcode(UPDATED_POSTCODE)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .emailInfo(UPDATED_EMAIL_INFO)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .language(UPDATED_LANGUAGE);

        restCompanyProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompanyProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompanyProfile))
            )
            .andExpect(status().isOk());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompanyProfile.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCompanyProfile.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCompanyProfile.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testCompanyProfile.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCompanyProfile.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testCompanyProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCompanyProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCompanyProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanyProfile.getEmailInfo()).isEqualTo(UPDATED_EMAIL_INFO);
        assertThat(testCompanyProfile.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCompanyProfile.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testCompanyProfile.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void putNonExistingCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyProfile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyProfileWithPatch() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();

        // Update the companyProfile using partial update
        CompanyProfile partialUpdatedCompanyProfile = new CompanyProfile();
        partialUpdatedCompanyProfile.setId(companyProfile.getId());

        partialUpdatedCompanyProfile
            .companyName(UPDATED_COMPANY_NAME)
            .address1(UPDATED_ADDRESS_1)
            .suburb(UPDATED_SUBURB)
            .state(UPDATED_STATE)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .emailInfo(UPDATED_EMAIL_INFO)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);

        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyProfile))
            )
            .andExpect(status().isOk());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompanyProfile.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCompanyProfile.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testCompanyProfile.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testCompanyProfile.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCompanyProfile.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testCompanyProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCompanyProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCompanyProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompanyProfile.getEmailInfo()).isEqualTo(UPDATED_EMAIL_INFO);
        assertThat(testCompanyProfile.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCompanyProfile.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testCompanyProfile.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    void fullUpdateCompanyProfileWithPatch() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();

        // Update the companyProfile using partial update
        CompanyProfile partialUpdatedCompanyProfile = new CompanyProfile();
        partialUpdatedCompanyProfile.setId(companyProfile.getId());

        partialUpdatedCompanyProfile
            .companyName(UPDATED_COMPANY_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .suburb(UPDATED_SUBURB)
            .state(UPDATED_STATE)
            .postcode(UPDATED_POSTCODE)
            .phone(UPDATED_PHONE)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .emailInfo(UPDATED_EMAIL_INFO)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .language(UPDATED_LANGUAGE);

        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyProfile))
            )
            .andExpect(status().isOk());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompanyProfile.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testCompanyProfile.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testCompanyProfile.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testCompanyProfile.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCompanyProfile.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testCompanyProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCompanyProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testCompanyProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanyProfile.getEmailInfo()).isEqualTo(UPDATED_EMAIL_INFO);
        assertThat(testCompanyProfile.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCompanyProfile.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testCompanyProfile.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();
        companyProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        int databaseSizeBeforeDelete = companyProfileRepository.findAll().size();

        // Delete the companyProfile
        restCompanyProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
