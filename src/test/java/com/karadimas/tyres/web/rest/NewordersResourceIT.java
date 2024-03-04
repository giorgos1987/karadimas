package com.karadimas.tyres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Neworders;
import com.karadimas.tyres.repository.NewordersRepository;
import com.karadimas.tyres.service.criteria.NewordersCriteria;
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

/**
 * Integration tests for the {@link NewordersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NewordersResourceIT {

    private static final Instant DEFAULT_ORDER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ELASTIKA_1 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_2 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_3 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_4 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_5 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_5 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_6 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_6 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_7 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_7 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_8 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_8 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_9 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_9 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_10 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_10 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_11 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_11 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_12 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_12 = "BBBBBBBBBB";

    private static final String DEFAULT_ELASTIKA_13 = "AAAAAAAAAA";
    private static final String UPDATED_ELASTIKA_13 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/neworders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NewordersRepository newordersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewordersMockMvc;

    private Neworders neworders;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Neworders createEntity(EntityManager em) {
        Neworders neworders = new Neworders()
            .orderDate(DEFAULT_ORDER_DATE)
            .elastika1(DEFAULT_ELASTIKA_1)
            .elastika2(DEFAULT_ELASTIKA_2)
            .elastika3(DEFAULT_ELASTIKA_3)
            .elastika4(DEFAULT_ELASTIKA_4)
            .elastika5(DEFAULT_ELASTIKA_5)
            .elastika6(DEFAULT_ELASTIKA_6)
            .elastika7(DEFAULT_ELASTIKA_7)
            .elastika8(DEFAULT_ELASTIKA_8)
            .elastika9(DEFAULT_ELASTIKA_9)
            .elastika10(DEFAULT_ELASTIKA_10)
            .elastika11(DEFAULT_ELASTIKA_11)
            .elastika12(DEFAULT_ELASTIKA_12)
            .elastika13(DEFAULT_ELASTIKA_13);
        return neworders;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Neworders createUpdatedEntity(EntityManager em) {
        Neworders neworders = new Neworders()
            .orderDate(UPDATED_ORDER_DATE)
            .elastika1(UPDATED_ELASTIKA_1)
            .elastika2(UPDATED_ELASTIKA_2)
            .elastika3(UPDATED_ELASTIKA_3)
            .elastika4(UPDATED_ELASTIKA_4)
            .elastika5(UPDATED_ELASTIKA_5)
            .elastika6(UPDATED_ELASTIKA_6)
            .elastika7(UPDATED_ELASTIKA_7)
            .elastika8(UPDATED_ELASTIKA_8)
            .elastika9(UPDATED_ELASTIKA_9)
            .elastika10(UPDATED_ELASTIKA_10)
            .elastika11(UPDATED_ELASTIKA_11)
            .elastika12(UPDATED_ELASTIKA_12)
            .elastika13(UPDATED_ELASTIKA_13);
        return neworders;
    }

    @BeforeEach
    public void initTest() {
        neworders = createEntity(em);
    }

    @Test
    @Transactional
    void createNeworders() throws Exception {
        int databaseSizeBeforeCreate = newordersRepository.findAll().size();
        // Create the Neworders
        restNewordersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(neworders)))
            .andExpect(status().isCreated());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeCreate + 1);
        Neworders testNeworders = newordersList.get(newordersList.size() - 1);
        assertThat(testNeworders.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testNeworders.getElastika1()).isEqualTo(DEFAULT_ELASTIKA_1);
        assertThat(testNeworders.getElastika2()).isEqualTo(DEFAULT_ELASTIKA_2);
        assertThat(testNeworders.getElastika3()).isEqualTo(DEFAULT_ELASTIKA_3);
        assertThat(testNeworders.getElastika4()).isEqualTo(DEFAULT_ELASTIKA_4);
        assertThat(testNeworders.getElastika5()).isEqualTo(DEFAULT_ELASTIKA_5);
        assertThat(testNeworders.getElastika6()).isEqualTo(DEFAULT_ELASTIKA_6);
        assertThat(testNeworders.getElastika7()).isEqualTo(DEFAULT_ELASTIKA_7);
        assertThat(testNeworders.getElastika8()).isEqualTo(DEFAULT_ELASTIKA_8);
        assertThat(testNeworders.getElastika9()).isEqualTo(DEFAULT_ELASTIKA_9);
        assertThat(testNeworders.getElastika10()).isEqualTo(DEFAULT_ELASTIKA_10);
        assertThat(testNeworders.getElastika11()).isEqualTo(DEFAULT_ELASTIKA_11);
        assertThat(testNeworders.getElastika12()).isEqualTo(DEFAULT_ELASTIKA_12);
        assertThat(testNeworders.getElastika13()).isEqualTo(DEFAULT_ELASTIKA_13);
    }

    @Test
    @Transactional
    void createNewordersWithExistingId() throws Exception {
        // Create the Neworders with an existing ID
        neworders.setId(1L);

        int databaseSizeBeforeCreate = newordersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewordersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(neworders)))
            .andExpect(status().isBadRequest());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNeworders() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList
        restNewordersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(neworders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].elastika1").value(hasItem(DEFAULT_ELASTIKA_1)))
            .andExpect(jsonPath("$.[*].elastika2").value(hasItem(DEFAULT_ELASTIKA_2)))
            .andExpect(jsonPath("$.[*].elastika3").value(hasItem(DEFAULT_ELASTIKA_3)))
            .andExpect(jsonPath("$.[*].elastika4").value(hasItem(DEFAULT_ELASTIKA_4)))
            .andExpect(jsonPath("$.[*].elastika5").value(hasItem(DEFAULT_ELASTIKA_5)))
            .andExpect(jsonPath("$.[*].elastika6").value(hasItem(DEFAULT_ELASTIKA_6)))
            .andExpect(jsonPath("$.[*].elastika7").value(hasItem(DEFAULT_ELASTIKA_7)))
            .andExpect(jsonPath("$.[*].elastika8").value(hasItem(DEFAULT_ELASTIKA_8)))
            .andExpect(jsonPath("$.[*].elastika9").value(hasItem(DEFAULT_ELASTIKA_9)))
            .andExpect(jsonPath("$.[*].elastika10").value(hasItem(DEFAULT_ELASTIKA_10)))
            .andExpect(jsonPath("$.[*].elastika11").value(hasItem(DEFAULT_ELASTIKA_11)))
            .andExpect(jsonPath("$.[*].elastika12").value(hasItem(DEFAULT_ELASTIKA_12)))
            .andExpect(jsonPath("$.[*].elastika13").value(hasItem(DEFAULT_ELASTIKA_13)));
    }

    @Test
    @Transactional
    void getNeworders() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get the neworders
        restNewordersMockMvc
            .perform(get(ENTITY_API_URL_ID, neworders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(neworders.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.elastika1").value(DEFAULT_ELASTIKA_1))
            .andExpect(jsonPath("$.elastika2").value(DEFAULT_ELASTIKA_2))
            .andExpect(jsonPath("$.elastika3").value(DEFAULT_ELASTIKA_3))
            .andExpect(jsonPath("$.elastika4").value(DEFAULT_ELASTIKA_4))
            .andExpect(jsonPath("$.elastika5").value(DEFAULT_ELASTIKA_5))
            .andExpect(jsonPath("$.elastika6").value(DEFAULT_ELASTIKA_6))
            .andExpect(jsonPath("$.elastika7").value(DEFAULT_ELASTIKA_7))
            .andExpect(jsonPath("$.elastika8").value(DEFAULT_ELASTIKA_8))
            .andExpect(jsonPath("$.elastika9").value(DEFAULT_ELASTIKA_9))
            .andExpect(jsonPath("$.elastika10").value(DEFAULT_ELASTIKA_10))
            .andExpect(jsonPath("$.elastika11").value(DEFAULT_ELASTIKA_11))
            .andExpect(jsonPath("$.elastika12").value(DEFAULT_ELASTIKA_12))
            .andExpect(jsonPath("$.elastika13").value(DEFAULT_ELASTIKA_13));
    }

    @Test
    @Transactional
    void getNewordersByIdFiltering() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        Long id = neworders.getId();

        defaultNewordersShouldBeFound("id.equals=" + id);
        defaultNewordersShouldNotBeFound("id.notEquals=" + id);

        defaultNewordersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNewordersShouldNotBeFound("id.greaterThan=" + id);

        defaultNewordersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNewordersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNewordersByOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where orderDate equals to DEFAULT_ORDER_DATE
        defaultNewordersShouldBeFound("orderDate.equals=" + DEFAULT_ORDER_DATE);

        // Get all the newordersList where orderDate equals to UPDATED_ORDER_DATE
        defaultNewordersShouldNotBeFound("orderDate.equals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllNewordersByOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where orderDate in DEFAULT_ORDER_DATE or UPDATED_ORDER_DATE
        defaultNewordersShouldBeFound("orderDate.in=" + DEFAULT_ORDER_DATE + "," + UPDATED_ORDER_DATE);

        // Get all the newordersList where orderDate equals to UPDATED_ORDER_DATE
        defaultNewordersShouldNotBeFound("orderDate.in=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void getAllNewordersByOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where orderDate is not null
        defaultNewordersShouldBeFound("orderDate.specified=true");

        // Get all the newordersList where orderDate is null
        defaultNewordersShouldNotBeFound("orderDate.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika1IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika1 equals to DEFAULT_ELASTIKA_1
        defaultNewordersShouldBeFound("elastika1.equals=" + DEFAULT_ELASTIKA_1);

        // Get all the newordersList where elastika1 equals to UPDATED_ELASTIKA_1
        defaultNewordersShouldNotBeFound("elastika1.equals=" + UPDATED_ELASTIKA_1);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika1IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika1 in DEFAULT_ELASTIKA_1 or UPDATED_ELASTIKA_1
        defaultNewordersShouldBeFound("elastika1.in=" + DEFAULT_ELASTIKA_1 + "," + UPDATED_ELASTIKA_1);

        // Get all the newordersList where elastika1 equals to UPDATED_ELASTIKA_1
        defaultNewordersShouldNotBeFound("elastika1.in=" + UPDATED_ELASTIKA_1);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika1IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika1 is not null
        defaultNewordersShouldBeFound("elastika1.specified=true");

        // Get all the newordersList where elastika1 is null
        defaultNewordersShouldNotBeFound("elastika1.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika1ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika1 contains DEFAULT_ELASTIKA_1
        defaultNewordersShouldBeFound("elastika1.contains=" + DEFAULT_ELASTIKA_1);

        // Get all the newordersList where elastika1 contains UPDATED_ELASTIKA_1
        defaultNewordersShouldNotBeFound("elastika1.contains=" + UPDATED_ELASTIKA_1);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika1NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika1 does not contain DEFAULT_ELASTIKA_1
        defaultNewordersShouldNotBeFound("elastika1.doesNotContain=" + DEFAULT_ELASTIKA_1);

        // Get all the newordersList where elastika1 does not contain UPDATED_ELASTIKA_1
        defaultNewordersShouldBeFound("elastika1.doesNotContain=" + UPDATED_ELASTIKA_1);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika2IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika2 equals to DEFAULT_ELASTIKA_2
        defaultNewordersShouldBeFound("elastika2.equals=" + DEFAULT_ELASTIKA_2);

        // Get all the newordersList where elastika2 equals to UPDATED_ELASTIKA_2
        defaultNewordersShouldNotBeFound("elastika2.equals=" + UPDATED_ELASTIKA_2);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika2IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika2 in DEFAULT_ELASTIKA_2 or UPDATED_ELASTIKA_2
        defaultNewordersShouldBeFound("elastika2.in=" + DEFAULT_ELASTIKA_2 + "," + UPDATED_ELASTIKA_2);

        // Get all the newordersList where elastika2 equals to UPDATED_ELASTIKA_2
        defaultNewordersShouldNotBeFound("elastika2.in=" + UPDATED_ELASTIKA_2);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika2IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika2 is not null
        defaultNewordersShouldBeFound("elastika2.specified=true");

        // Get all the newordersList where elastika2 is null
        defaultNewordersShouldNotBeFound("elastika2.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika2ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika2 contains DEFAULT_ELASTIKA_2
        defaultNewordersShouldBeFound("elastika2.contains=" + DEFAULT_ELASTIKA_2);

        // Get all the newordersList where elastika2 contains UPDATED_ELASTIKA_2
        defaultNewordersShouldNotBeFound("elastika2.contains=" + UPDATED_ELASTIKA_2);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika2NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika2 does not contain DEFAULT_ELASTIKA_2
        defaultNewordersShouldNotBeFound("elastika2.doesNotContain=" + DEFAULT_ELASTIKA_2);

        // Get all the newordersList where elastika2 does not contain UPDATED_ELASTIKA_2
        defaultNewordersShouldBeFound("elastika2.doesNotContain=" + UPDATED_ELASTIKA_2);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika3IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika3 equals to DEFAULT_ELASTIKA_3
        defaultNewordersShouldBeFound("elastika3.equals=" + DEFAULT_ELASTIKA_3);

        // Get all the newordersList where elastika3 equals to UPDATED_ELASTIKA_3
        defaultNewordersShouldNotBeFound("elastika3.equals=" + UPDATED_ELASTIKA_3);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika3IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika3 in DEFAULT_ELASTIKA_3 or UPDATED_ELASTIKA_3
        defaultNewordersShouldBeFound("elastika3.in=" + DEFAULT_ELASTIKA_3 + "," + UPDATED_ELASTIKA_3);

        // Get all the newordersList where elastika3 equals to UPDATED_ELASTIKA_3
        defaultNewordersShouldNotBeFound("elastika3.in=" + UPDATED_ELASTIKA_3);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika3IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika3 is not null
        defaultNewordersShouldBeFound("elastika3.specified=true");

        // Get all the newordersList where elastika3 is null
        defaultNewordersShouldNotBeFound("elastika3.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika3ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika3 contains DEFAULT_ELASTIKA_3
        defaultNewordersShouldBeFound("elastika3.contains=" + DEFAULT_ELASTIKA_3);

        // Get all the newordersList where elastika3 contains UPDATED_ELASTIKA_3
        defaultNewordersShouldNotBeFound("elastika3.contains=" + UPDATED_ELASTIKA_3);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika3NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika3 does not contain DEFAULT_ELASTIKA_3
        defaultNewordersShouldNotBeFound("elastika3.doesNotContain=" + DEFAULT_ELASTIKA_3);

        // Get all the newordersList where elastika3 does not contain UPDATED_ELASTIKA_3
        defaultNewordersShouldBeFound("elastika3.doesNotContain=" + UPDATED_ELASTIKA_3);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika4IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika4 equals to DEFAULT_ELASTIKA_4
        defaultNewordersShouldBeFound("elastika4.equals=" + DEFAULT_ELASTIKA_4);

        // Get all the newordersList where elastika4 equals to UPDATED_ELASTIKA_4
        defaultNewordersShouldNotBeFound("elastika4.equals=" + UPDATED_ELASTIKA_4);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika4IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika4 in DEFAULT_ELASTIKA_4 or UPDATED_ELASTIKA_4
        defaultNewordersShouldBeFound("elastika4.in=" + DEFAULT_ELASTIKA_4 + "," + UPDATED_ELASTIKA_4);

        // Get all the newordersList where elastika4 equals to UPDATED_ELASTIKA_4
        defaultNewordersShouldNotBeFound("elastika4.in=" + UPDATED_ELASTIKA_4);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika4IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika4 is not null
        defaultNewordersShouldBeFound("elastika4.specified=true");

        // Get all the newordersList where elastika4 is null
        defaultNewordersShouldNotBeFound("elastika4.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika4ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika4 contains DEFAULT_ELASTIKA_4
        defaultNewordersShouldBeFound("elastika4.contains=" + DEFAULT_ELASTIKA_4);

        // Get all the newordersList where elastika4 contains UPDATED_ELASTIKA_4
        defaultNewordersShouldNotBeFound("elastika4.contains=" + UPDATED_ELASTIKA_4);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika4NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika4 does not contain DEFAULT_ELASTIKA_4
        defaultNewordersShouldNotBeFound("elastika4.doesNotContain=" + DEFAULT_ELASTIKA_4);

        // Get all the newordersList where elastika4 does not contain UPDATED_ELASTIKA_4
        defaultNewordersShouldBeFound("elastika4.doesNotContain=" + UPDATED_ELASTIKA_4);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika5IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika5 equals to DEFAULT_ELASTIKA_5
        defaultNewordersShouldBeFound("elastika5.equals=" + DEFAULT_ELASTIKA_5);

        // Get all the newordersList where elastika5 equals to UPDATED_ELASTIKA_5
        defaultNewordersShouldNotBeFound("elastika5.equals=" + UPDATED_ELASTIKA_5);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika5IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika5 in DEFAULT_ELASTIKA_5 or UPDATED_ELASTIKA_5
        defaultNewordersShouldBeFound("elastika5.in=" + DEFAULT_ELASTIKA_5 + "," + UPDATED_ELASTIKA_5);

        // Get all the newordersList where elastika5 equals to UPDATED_ELASTIKA_5
        defaultNewordersShouldNotBeFound("elastika5.in=" + UPDATED_ELASTIKA_5);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika5IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika5 is not null
        defaultNewordersShouldBeFound("elastika5.specified=true");

        // Get all the newordersList where elastika5 is null
        defaultNewordersShouldNotBeFound("elastika5.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika5ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika5 contains DEFAULT_ELASTIKA_5
        defaultNewordersShouldBeFound("elastika5.contains=" + DEFAULT_ELASTIKA_5);

        // Get all the newordersList where elastika5 contains UPDATED_ELASTIKA_5
        defaultNewordersShouldNotBeFound("elastika5.contains=" + UPDATED_ELASTIKA_5);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika5NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika5 does not contain DEFAULT_ELASTIKA_5
        defaultNewordersShouldNotBeFound("elastika5.doesNotContain=" + DEFAULT_ELASTIKA_5);

        // Get all the newordersList where elastika5 does not contain UPDATED_ELASTIKA_5
        defaultNewordersShouldBeFound("elastika5.doesNotContain=" + UPDATED_ELASTIKA_5);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika6IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika6 equals to DEFAULT_ELASTIKA_6
        defaultNewordersShouldBeFound("elastika6.equals=" + DEFAULT_ELASTIKA_6);

        // Get all the newordersList where elastika6 equals to UPDATED_ELASTIKA_6
        defaultNewordersShouldNotBeFound("elastika6.equals=" + UPDATED_ELASTIKA_6);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika6IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika6 in DEFAULT_ELASTIKA_6 or UPDATED_ELASTIKA_6
        defaultNewordersShouldBeFound("elastika6.in=" + DEFAULT_ELASTIKA_6 + "," + UPDATED_ELASTIKA_6);

        // Get all the newordersList where elastika6 equals to UPDATED_ELASTIKA_6
        defaultNewordersShouldNotBeFound("elastika6.in=" + UPDATED_ELASTIKA_6);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika6IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika6 is not null
        defaultNewordersShouldBeFound("elastika6.specified=true");

        // Get all the newordersList where elastika6 is null
        defaultNewordersShouldNotBeFound("elastika6.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika6ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika6 contains DEFAULT_ELASTIKA_6
        defaultNewordersShouldBeFound("elastika6.contains=" + DEFAULT_ELASTIKA_6);

        // Get all the newordersList where elastika6 contains UPDATED_ELASTIKA_6
        defaultNewordersShouldNotBeFound("elastika6.contains=" + UPDATED_ELASTIKA_6);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika6NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika6 does not contain DEFAULT_ELASTIKA_6
        defaultNewordersShouldNotBeFound("elastika6.doesNotContain=" + DEFAULT_ELASTIKA_6);

        // Get all the newordersList where elastika6 does not contain UPDATED_ELASTIKA_6
        defaultNewordersShouldBeFound("elastika6.doesNotContain=" + UPDATED_ELASTIKA_6);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika7IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika7 equals to DEFAULT_ELASTIKA_7
        defaultNewordersShouldBeFound("elastika7.equals=" + DEFAULT_ELASTIKA_7);

        // Get all the newordersList where elastika7 equals to UPDATED_ELASTIKA_7
        defaultNewordersShouldNotBeFound("elastika7.equals=" + UPDATED_ELASTIKA_7);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika7IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika7 in DEFAULT_ELASTIKA_7 or UPDATED_ELASTIKA_7
        defaultNewordersShouldBeFound("elastika7.in=" + DEFAULT_ELASTIKA_7 + "," + UPDATED_ELASTIKA_7);

        // Get all the newordersList where elastika7 equals to UPDATED_ELASTIKA_7
        defaultNewordersShouldNotBeFound("elastika7.in=" + UPDATED_ELASTIKA_7);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika7IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika7 is not null
        defaultNewordersShouldBeFound("elastika7.specified=true");

        // Get all the newordersList where elastika7 is null
        defaultNewordersShouldNotBeFound("elastika7.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika7ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika7 contains DEFAULT_ELASTIKA_7
        defaultNewordersShouldBeFound("elastika7.contains=" + DEFAULT_ELASTIKA_7);

        // Get all the newordersList where elastika7 contains UPDATED_ELASTIKA_7
        defaultNewordersShouldNotBeFound("elastika7.contains=" + UPDATED_ELASTIKA_7);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika7NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika7 does not contain DEFAULT_ELASTIKA_7
        defaultNewordersShouldNotBeFound("elastika7.doesNotContain=" + DEFAULT_ELASTIKA_7);

        // Get all the newordersList where elastika7 does not contain UPDATED_ELASTIKA_7
        defaultNewordersShouldBeFound("elastika7.doesNotContain=" + UPDATED_ELASTIKA_7);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika8IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika8 equals to DEFAULT_ELASTIKA_8
        defaultNewordersShouldBeFound("elastika8.equals=" + DEFAULT_ELASTIKA_8);

        // Get all the newordersList where elastika8 equals to UPDATED_ELASTIKA_8
        defaultNewordersShouldNotBeFound("elastika8.equals=" + UPDATED_ELASTIKA_8);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika8IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika8 in DEFAULT_ELASTIKA_8 or UPDATED_ELASTIKA_8
        defaultNewordersShouldBeFound("elastika8.in=" + DEFAULT_ELASTIKA_8 + "," + UPDATED_ELASTIKA_8);

        // Get all the newordersList where elastika8 equals to UPDATED_ELASTIKA_8
        defaultNewordersShouldNotBeFound("elastika8.in=" + UPDATED_ELASTIKA_8);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika8IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika8 is not null
        defaultNewordersShouldBeFound("elastika8.specified=true");

        // Get all the newordersList where elastika8 is null
        defaultNewordersShouldNotBeFound("elastika8.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika8ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika8 contains DEFAULT_ELASTIKA_8
        defaultNewordersShouldBeFound("elastika8.contains=" + DEFAULT_ELASTIKA_8);

        // Get all the newordersList where elastika8 contains UPDATED_ELASTIKA_8
        defaultNewordersShouldNotBeFound("elastika8.contains=" + UPDATED_ELASTIKA_8);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika8NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika8 does not contain DEFAULT_ELASTIKA_8
        defaultNewordersShouldNotBeFound("elastika8.doesNotContain=" + DEFAULT_ELASTIKA_8);

        // Get all the newordersList where elastika8 does not contain UPDATED_ELASTIKA_8
        defaultNewordersShouldBeFound("elastika8.doesNotContain=" + UPDATED_ELASTIKA_8);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika9IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika9 equals to DEFAULT_ELASTIKA_9
        defaultNewordersShouldBeFound("elastika9.equals=" + DEFAULT_ELASTIKA_9);

        // Get all the newordersList where elastika9 equals to UPDATED_ELASTIKA_9
        defaultNewordersShouldNotBeFound("elastika9.equals=" + UPDATED_ELASTIKA_9);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika9IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika9 in DEFAULT_ELASTIKA_9 or UPDATED_ELASTIKA_9
        defaultNewordersShouldBeFound("elastika9.in=" + DEFAULT_ELASTIKA_9 + "," + UPDATED_ELASTIKA_9);

        // Get all the newordersList where elastika9 equals to UPDATED_ELASTIKA_9
        defaultNewordersShouldNotBeFound("elastika9.in=" + UPDATED_ELASTIKA_9);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika9IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika9 is not null
        defaultNewordersShouldBeFound("elastika9.specified=true");

        // Get all the newordersList where elastika9 is null
        defaultNewordersShouldNotBeFound("elastika9.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika9ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika9 contains DEFAULT_ELASTIKA_9
        defaultNewordersShouldBeFound("elastika9.contains=" + DEFAULT_ELASTIKA_9);

        // Get all the newordersList where elastika9 contains UPDATED_ELASTIKA_9
        defaultNewordersShouldNotBeFound("elastika9.contains=" + UPDATED_ELASTIKA_9);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika9NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika9 does not contain DEFAULT_ELASTIKA_9
        defaultNewordersShouldNotBeFound("elastika9.doesNotContain=" + DEFAULT_ELASTIKA_9);

        // Get all the newordersList where elastika9 does not contain UPDATED_ELASTIKA_9
        defaultNewordersShouldBeFound("elastika9.doesNotContain=" + UPDATED_ELASTIKA_9);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika10IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika10 equals to DEFAULT_ELASTIKA_10
        defaultNewordersShouldBeFound("elastika10.equals=" + DEFAULT_ELASTIKA_10);

        // Get all the newordersList where elastika10 equals to UPDATED_ELASTIKA_10
        defaultNewordersShouldNotBeFound("elastika10.equals=" + UPDATED_ELASTIKA_10);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika10IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika10 in DEFAULT_ELASTIKA_10 or UPDATED_ELASTIKA_10
        defaultNewordersShouldBeFound("elastika10.in=" + DEFAULT_ELASTIKA_10 + "," + UPDATED_ELASTIKA_10);

        // Get all the newordersList where elastika10 equals to UPDATED_ELASTIKA_10
        defaultNewordersShouldNotBeFound("elastika10.in=" + UPDATED_ELASTIKA_10);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika10IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika10 is not null
        defaultNewordersShouldBeFound("elastika10.specified=true");

        // Get all the newordersList where elastika10 is null
        defaultNewordersShouldNotBeFound("elastika10.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika10ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika10 contains DEFAULT_ELASTIKA_10
        defaultNewordersShouldBeFound("elastika10.contains=" + DEFAULT_ELASTIKA_10);

        // Get all the newordersList where elastika10 contains UPDATED_ELASTIKA_10
        defaultNewordersShouldNotBeFound("elastika10.contains=" + UPDATED_ELASTIKA_10);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika10NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika10 does not contain DEFAULT_ELASTIKA_10
        defaultNewordersShouldNotBeFound("elastika10.doesNotContain=" + DEFAULT_ELASTIKA_10);

        // Get all the newordersList where elastika10 does not contain UPDATED_ELASTIKA_10
        defaultNewordersShouldBeFound("elastika10.doesNotContain=" + UPDATED_ELASTIKA_10);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika11IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika11 equals to DEFAULT_ELASTIKA_11
        defaultNewordersShouldBeFound("elastika11.equals=" + DEFAULT_ELASTIKA_11);

        // Get all the newordersList where elastika11 equals to UPDATED_ELASTIKA_11
        defaultNewordersShouldNotBeFound("elastika11.equals=" + UPDATED_ELASTIKA_11);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika11IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika11 in DEFAULT_ELASTIKA_11 or UPDATED_ELASTIKA_11
        defaultNewordersShouldBeFound("elastika11.in=" + DEFAULT_ELASTIKA_11 + "," + UPDATED_ELASTIKA_11);

        // Get all the newordersList where elastika11 equals to UPDATED_ELASTIKA_11
        defaultNewordersShouldNotBeFound("elastika11.in=" + UPDATED_ELASTIKA_11);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika11IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika11 is not null
        defaultNewordersShouldBeFound("elastika11.specified=true");

        // Get all the newordersList where elastika11 is null
        defaultNewordersShouldNotBeFound("elastika11.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika11ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika11 contains DEFAULT_ELASTIKA_11
        defaultNewordersShouldBeFound("elastika11.contains=" + DEFAULT_ELASTIKA_11);

        // Get all the newordersList where elastika11 contains UPDATED_ELASTIKA_11
        defaultNewordersShouldNotBeFound("elastika11.contains=" + UPDATED_ELASTIKA_11);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika11NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika11 does not contain DEFAULT_ELASTIKA_11
        defaultNewordersShouldNotBeFound("elastika11.doesNotContain=" + DEFAULT_ELASTIKA_11);

        // Get all the newordersList where elastika11 does not contain UPDATED_ELASTIKA_11
        defaultNewordersShouldBeFound("elastika11.doesNotContain=" + UPDATED_ELASTIKA_11);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika12IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika12 equals to DEFAULT_ELASTIKA_12
        defaultNewordersShouldBeFound("elastika12.equals=" + DEFAULT_ELASTIKA_12);

        // Get all the newordersList where elastika12 equals to UPDATED_ELASTIKA_12
        defaultNewordersShouldNotBeFound("elastika12.equals=" + UPDATED_ELASTIKA_12);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika12IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika12 in DEFAULT_ELASTIKA_12 or UPDATED_ELASTIKA_12
        defaultNewordersShouldBeFound("elastika12.in=" + DEFAULT_ELASTIKA_12 + "," + UPDATED_ELASTIKA_12);

        // Get all the newordersList where elastika12 equals to UPDATED_ELASTIKA_12
        defaultNewordersShouldNotBeFound("elastika12.in=" + UPDATED_ELASTIKA_12);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika12IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika12 is not null
        defaultNewordersShouldBeFound("elastika12.specified=true");

        // Get all the newordersList where elastika12 is null
        defaultNewordersShouldNotBeFound("elastika12.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika12ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika12 contains DEFAULT_ELASTIKA_12
        defaultNewordersShouldBeFound("elastika12.contains=" + DEFAULT_ELASTIKA_12);

        // Get all the newordersList where elastika12 contains UPDATED_ELASTIKA_12
        defaultNewordersShouldNotBeFound("elastika12.contains=" + UPDATED_ELASTIKA_12);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika12NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika12 does not contain DEFAULT_ELASTIKA_12
        defaultNewordersShouldNotBeFound("elastika12.doesNotContain=" + DEFAULT_ELASTIKA_12);

        // Get all the newordersList where elastika12 does not contain UPDATED_ELASTIKA_12
        defaultNewordersShouldBeFound("elastika12.doesNotContain=" + UPDATED_ELASTIKA_12);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika13IsEqualToSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika13 equals to DEFAULT_ELASTIKA_13
        defaultNewordersShouldBeFound("elastika13.equals=" + DEFAULT_ELASTIKA_13);

        // Get all the newordersList where elastika13 equals to UPDATED_ELASTIKA_13
        defaultNewordersShouldNotBeFound("elastika13.equals=" + UPDATED_ELASTIKA_13);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika13IsInShouldWork() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika13 in DEFAULT_ELASTIKA_13 or UPDATED_ELASTIKA_13
        defaultNewordersShouldBeFound("elastika13.in=" + DEFAULT_ELASTIKA_13 + "," + UPDATED_ELASTIKA_13);

        // Get all the newordersList where elastika13 equals to UPDATED_ELASTIKA_13
        defaultNewordersShouldNotBeFound("elastika13.in=" + UPDATED_ELASTIKA_13);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika13IsNullOrNotNull() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika13 is not null
        defaultNewordersShouldBeFound("elastika13.specified=true");

        // Get all the newordersList where elastika13 is null
        defaultNewordersShouldNotBeFound("elastika13.specified=false");
    }

    @Test
    @Transactional
    void getAllNewordersByElastika13ContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika13 contains DEFAULT_ELASTIKA_13
        defaultNewordersShouldBeFound("elastika13.contains=" + DEFAULT_ELASTIKA_13);

        // Get all the newordersList where elastika13 contains UPDATED_ELASTIKA_13
        defaultNewordersShouldNotBeFound("elastika13.contains=" + UPDATED_ELASTIKA_13);
    }

    @Test
    @Transactional
    void getAllNewordersByElastika13NotContainsSomething() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        // Get all the newordersList where elastika13 does not contain DEFAULT_ELASTIKA_13
        defaultNewordersShouldNotBeFound("elastika13.doesNotContain=" + DEFAULT_ELASTIKA_13);

        // Get all the newordersList where elastika13 does not contain UPDATED_ELASTIKA_13
        defaultNewordersShouldBeFound("elastika13.doesNotContain=" + UPDATED_ELASTIKA_13);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNewordersShouldBeFound(String filter) throws Exception {
        restNewordersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(neworders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].elastika1").value(hasItem(DEFAULT_ELASTIKA_1)))
            .andExpect(jsonPath("$.[*].elastika2").value(hasItem(DEFAULT_ELASTIKA_2)))
            .andExpect(jsonPath("$.[*].elastika3").value(hasItem(DEFAULT_ELASTIKA_3)))
            .andExpect(jsonPath("$.[*].elastika4").value(hasItem(DEFAULT_ELASTIKA_4)))
            .andExpect(jsonPath("$.[*].elastika5").value(hasItem(DEFAULT_ELASTIKA_5)))
            .andExpect(jsonPath("$.[*].elastika6").value(hasItem(DEFAULT_ELASTIKA_6)))
            .andExpect(jsonPath("$.[*].elastika7").value(hasItem(DEFAULT_ELASTIKA_7)))
            .andExpect(jsonPath("$.[*].elastika8").value(hasItem(DEFAULT_ELASTIKA_8)))
            .andExpect(jsonPath("$.[*].elastika9").value(hasItem(DEFAULT_ELASTIKA_9)))
            .andExpect(jsonPath("$.[*].elastika10").value(hasItem(DEFAULT_ELASTIKA_10)))
            .andExpect(jsonPath("$.[*].elastika11").value(hasItem(DEFAULT_ELASTIKA_11)))
            .andExpect(jsonPath("$.[*].elastika12").value(hasItem(DEFAULT_ELASTIKA_12)))
            .andExpect(jsonPath("$.[*].elastika13").value(hasItem(DEFAULT_ELASTIKA_13)));

        // Check, that the count call also returns 1
        restNewordersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNewordersShouldNotBeFound(String filter) throws Exception {
        restNewordersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNewordersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNeworders() throws Exception {
        // Get the neworders
        restNewordersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNeworders() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        int databaseSizeBeforeUpdate = newordersRepository.findAll().size();

        // Update the neworders
        Neworders updatedNeworders = newordersRepository.findById(neworders.getId()).get();
        // Disconnect from session so that the updates on updatedNeworders are not directly saved in db
        em.detach(updatedNeworders);
        updatedNeworders
            .orderDate(UPDATED_ORDER_DATE)
            .elastika1(UPDATED_ELASTIKA_1)
            .elastika2(UPDATED_ELASTIKA_2)
            .elastika3(UPDATED_ELASTIKA_3)
            .elastika4(UPDATED_ELASTIKA_4)
            .elastika5(UPDATED_ELASTIKA_5)
            .elastika6(UPDATED_ELASTIKA_6)
            .elastika7(UPDATED_ELASTIKA_7)
            .elastika8(UPDATED_ELASTIKA_8)
            .elastika9(UPDATED_ELASTIKA_9)
            .elastika10(UPDATED_ELASTIKA_10)
            .elastika11(UPDATED_ELASTIKA_11)
            .elastika12(UPDATED_ELASTIKA_12)
            .elastika13(UPDATED_ELASTIKA_13);

        restNewordersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNeworders.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNeworders))
            )
            .andExpect(status().isOk());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeUpdate);
        Neworders testNeworders = newordersList.get(newordersList.size() - 1);
        assertThat(testNeworders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testNeworders.getElastika1()).isEqualTo(UPDATED_ELASTIKA_1);
        assertThat(testNeworders.getElastika2()).isEqualTo(UPDATED_ELASTIKA_2);
        assertThat(testNeworders.getElastika3()).isEqualTo(UPDATED_ELASTIKA_3);
        assertThat(testNeworders.getElastika4()).isEqualTo(UPDATED_ELASTIKA_4);
        assertThat(testNeworders.getElastika5()).isEqualTo(UPDATED_ELASTIKA_5);
        assertThat(testNeworders.getElastika6()).isEqualTo(UPDATED_ELASTIKA_6);
        assertThat(testNeworders.getElastika7()).isEqualTo(UPDATED_ELASTIKA_7);
        assertThat(testNeworders.getElastika8()).isEqualTo(UPDATED_ELASTIKA_8);
        assertThat(testNeworders.getElastika9()).isEqualTo(UPDATED_ELASTIKA_9);
        assertThat(testNeworders.getElastika10()).isEqualTo(UPDATED_ELASTIKA_10);
        assertThat(testNeworders.getElastika11()).isEqualTo(UPDATED_ELASTIKA_11);
        assertThat(testNeworders.getElastika12()).isEqualTo(UPDATED_ELASTIKA_12);
        assertThat(testNeworders.getElastika13()).isEqualTo(UPDATED_ELASTIKA_13);
    }

    @Test
    @Transactional
    void putNonExistingNeworders() throws Exception {
        int databaseSizeBeforeUpdate = newordersRepository.findAll().size();
        neworders.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewordersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, neworders.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(neworders))
            )
            .andExpect(status().isBadRequest());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNeworders() throws Exception {
        int databaseSizeBeforeUpdate = newordersRepository.findAll().size();
        neworders.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewordersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(neworders))
            )
            .andExpect(status().isBadRequest());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNeworders() throws Exception {
        int databaseSizeBeforeUpdate = newordersRepository.findAll().size();
        neworders.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewordersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(neworders)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNewordersWithPatch() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        int databaseSizeBeforeUpdate = newordersRepository.findAll().size();

        // Update the neworders using partial update
        Neworders partialUpdatedNeworders = new Neworders();
        partialUpdatedNeworders.setId(neworders.getId());

        partialUpdatedNeworders
            .orderDate(UPDATED_ORDER_DATE)
            .elastika1(UPDATED_ELASTIKA_1)
            .elastika2(UPDATED_ELASTIKA_2)
            .elastika3(UPDATED_ELASTIKA_3)
            .elastika5(UPDATED_ELASTIKA_5)
            .elastika11(UPDATED_ELASTIKA_11);

        restNewordersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNeworders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNeworders))
            )
            .andExpect(status().isOk());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeUpdate);
        Neworders testNeworders = newordersList.get(newordersList.size() - 1);
        assertThat(testNeworders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testNeworders.getElastika1()).isEqualTo(UPDATED_ELASTIKA_1);
        assertThat(testNeworders.getElastika2()).isEqualTo(UPDATED_ELASTIKA_2);
        assertThat(testNeworders.getElastika3()).isEqualTo(UPDATED_ELASTIKA_3);
        assertThat(testNeworders.getElastika4()).isEqualTo(DEFAULT_ELASTIKA_4);
        assertThat(testNeworders.getElastika5()).isEqualTo(UPDATED_ELASTIKA_5);
        assertThat(testNeworders.getElastika6()).isEqualTo(DEFAULT_ELASTIKA_6);
        assertThat(testNeworders.getElastika7()).isEqualTo(DEFAULT_ELASTIKA_7);
        assertThat(testNeworders.getElastika8()).isEqualTo(DEFAULT_ELASTIKA_8);
        assertThat(testNeworders.getElastika9()).isEqualTo(DEFAULT_ELASTIKA_9);
        assertThat(testNeworders.getElastika10()).isEqualTo(DEFAULT_ELASTIKA_10);
        assertThat(testNeworders.getElastika11()).isEqualTo(UPDATED_ELASTIKA_11);
        assertThat(testNeworders.getElastika12()).isEqualTo(DEFAULT_ELASTIKA_12);
        assertThat(testNeworders.getElastika13()).isEqualTo(DEFAULT_ELASTIKA_13);
    }

    @Test
    @Transactional
    void fullUpdateNewordersWithPatch() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        int databaseSizeBeforeUpdate = newordersRepository.findAll().size();

        // Update the neworders using partial update
        Neworders partialUpdatedNeworders = new Neworders();
        partialUpdatedNeworders.setId(neworders.getId());

        partialUpdatedNeworders
            .orderDate(UPDATED_ORDER_DATE)
            .elastika1(UPDATED_ELASTIKA_1)
            .elastika2(UPDATED_ELASTIKA_2)
            .elastika3(UPDATED_ELASTIKA_3)
            .elastika4(UPDATED_ELASTIKA_4)
            .elastika5(UPDATED_ELASTIKA_5)
            .elastika6(UPDATED_ELASTIKA_6)
            .elastika7(UPDATED_ELASTIKA_7)
            .elastika8(UPDATED_ELASTIKA_8)
            .elastika9(UPDATED_ELASTIKA_9)
            .elastika10(UPDATED_ELASTIKA_10)
            .elastika11(UPDATED_ELASTIKA_11)
            .elastika12(UPDATED_ELASTIKA_12)
            .elastika13(UPDATED_ELASTIKA_13);

        restNewordersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNeworders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNeworders))
            )
            .andExpect(status().isOk());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeUpdate);
        Neworders testNeworders = newordersList.get(newordersList.size() - 1);
        assertThat(testNeworders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testNeworders.getElastika1()).isEqualTo(UPDATED_ELASTIKA_1);
        assertThat(testNeworders.getElastika2()).isEqualTo(UPDATED_ELASTIKA_2);
        assertThat(testNeworders.getElastika3()).isEqualTo(UPDATED_ELASTIKA_3);
        assertThat(testNeworders.getElastika4()).isEqualTo(UPDATED_ELASTIKA_4);
        assertThat(testNeworders.getElastika5()).isEqualTo(UPDATED_ELASTIKA_5);
        assertThat(testNeworders.getElastika6()).isEqualTo(UPDATED_ELASTIKA_6);
        assertThat(testNeworders.getElastika7()).isEqualTo(UPDATED_ELASTIKA_7);
        assertThat(testNeworders.getElastika8()).isEqualTo(UPDATED_ELASTIKA_8);
        assertThat(testNeworders.getElastika9()).isEqualTo(UPDATED_ELASTIKA_9);
        assertThat(testNeworders.getElastika10()).isEqualTo(UPDATED_ELASTIKA_10);
        assertThat(testNeworders.getElastika11()).isEqualTo(UPDATED_ELASTIKA_11);
        assertThat(testNeworders.getElastika12()).isEqualTo(UPDATED_ELASTIKA_12);
        assertThat(testNeworders.getElastika13()).isEqualTo(UPDATED_ELASTIKA_13);
    }

    @Test
    @Transactional
    void patchNonExistingNeworders() throws Exception {
        int databaseSizeBeforeUpdate = newordersRepository.findAll().size();
        neworders.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewordersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, neworders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(neworders))
            )
            .andExpect(status().isBadRequest());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNeworders() throws Exception {
        int databaseSizeBeforeUpdate = newordersRepository.findAll().size();
        neworders.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewordersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(neworders))
            )
            .andExpect(status().isBadRequest());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNeworders() throws Exception {
        int databaseSizeBeforeUpdate = newordersRepository.findAll().size();
        neworders.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewordersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(neworders))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Neworders in the database
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNeworders() throws Exception {
        // Initialize the database
        newordersRepository.saveAndFlush(neworders);

        int databaseSizeBeforeDelete = newordersRepository.findAll().size();

        // Delete the neworders
        restNewordersMockMvc
            .perform(delete(ENTITY_API_URL_ID, neworders.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Neworders> newordersList = newordersRepository.findAll();
        assertThat(newordersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
