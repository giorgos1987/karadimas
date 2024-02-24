package com.karadimas.tyres.web.rest;

import static com.karadimas.tyres.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Statistics;
import com.karadimas.tyres.repository.StatisticsRepository;
import com.karadimas.tyres.service.criteria.StatisticsCriteria;
import java.math.BigDecimal;
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
 * Integration tests for the {@link StatisticsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatisticsResourceIT {

    private static final BigDecimal DEFAULT_TODAYSALES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TODAYSALES = new BigDecimal(2);
    private static final BigDecimal SMALLER_TODAYSALES = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_TOTAL_CUSTOMERS_NUMB = 0;
    private static final Integer UPDATED_TOTAL_CUSTOMERS_NUMB = 1;
    private static final Integer SMALLER_TOTAL_CUSTOMERS_NUMB = 0 - 1;

    private static final BigDecimal DEFAULT_CUSTMER_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTMER_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_CUSTMER_TOTAL = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_SCHED_TOTAL_NEX_WEEK = 1;
    private static final Integer UPDATED_SCHED_TOTAL_NEX_WEEK = 2;
    private static final Integer SMALLER_SCHED_TOTAL_NEX_WEEK = 1 - 1;

    private static final Integer DEFAULT_TOTAL_CARTS = 1;
    private static final Integer UPDATED_TOTAL_CARTS = 2;
    private static final Integer SMALLER_TOTAL_CARTS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_PENDING = 1;
    private static final Integer UPDATED_TOTAL_PENDING = 2;
    private static final Integer SMALLER_TOTAL_PENDING = 1 - 1;

    private static final BigDecimal DEFAULT_TOTAL_PAYMENTS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PAYMENTS = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PAYMENTS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PENDING_PAYMENTS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PENDING_PAYMENTS = new BigDecimal(2);
    private static final BigDecimal SMALLER_PENDING_PAYMENTS = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_PENDING_NUMBER_PAYMENTS = 1;
    private static final Integer UPDATED_PENDING_NUMBER_PAYMENTS = 2;
    private static final Integer SMALLER_PENDING_NUMBER_PAYMENTS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_CARS = 1;
    private static final Integer UPDATED_TOTAL_CARS = 2;
    private static final Integer SMALLER_TOTAL_CARS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_TRUCKS = 1;
    private static final Integer UPDATED_TOTAL_TRUCKS = 2;
    private static final Integer SMALLER_TOTAL_TRUCKS = 1 - 1;

    private static final Integer DEFAULT_TOTAL_OTHER_1 = 1;
    private static final Integer UPDATED_TOTAL_OTHER_1 = 2;
    private static final Integer SMALLER_TOTAL_OTHER_1 = 1 - 1;

    private static final Integer DEFAULT_TOTAL_OTHER_2 = 1;
    private static final Integer UPDATED_TOTAL_OTHER_2 = 2;
    private static final Integer SMALLER_TOTAL_OTHER_2 = 1 - 1;

    private static final String DEFAULT_LATESTPAYMENTS = "AAAAAAAAAA";
    private static final String UPDATED_LATESTPAYMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_LTSTSUPPLIERPAYM = "AAAAAAAAAA";
    private static final String UPDATED_LTSTSUPPLIERPAYM = "BBBBBBBBBB";

    private static final String DEFAULT_RECENTRLYCOMPLTD = "AAAAAAAAAA";
    private static final String UPDATED_RECENTRLYCOMPLTD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/statistics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatisticsMockMvc;

    private Statistics statistics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistics createEntity(EntityManager em) {
        Statistics statistics = new Statistics()
            .todaysales(DEFAULT_TODAYSALES)
            .totalCustomersNumb(DEFAULT_TOTAL_CUSTOMERS_NUMB)
            .custmerTotal(DEFAULT_CUSTMER_TOTAL)
            .schedTotalNexWeek(DEFAULT_SCHED_TOTAL_NEX_WEEK)
            .totalCarts(DEFAULT_TOTAL_CARTS)
            .totalPending(DEFAULT_TOTAL_PENDING)
            .totalPayments(DEFAULT_TOTAL_PAYMENTS)
            .pendingPayments(DEFAULT_PENDING_PAYMENTS)
            .pendingNumberPayments(DEFAULT_PENDING_NUMBER_PAYMENTS)
            .totalCars(DEFAULT_TOTAL_CARS)
            .totalTrucks(DEFAULT_TOTAL_TRUCKS)
            .totalOther1(DEFAULT_TOTAL_OTHER_1)
            .totalOther2(DEFAULT_TOTAL_OTHER_2)
            .latestpayments(DEFAULT_LATESTPAYMENTS)
            .ltstsupplierpaym(DEFAULT_LTSTSUPPLIERPAYM)
            .recentrlycompltd(DEFAULT_RECENTRLYCOMPLTD);
        return statistics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistics createUpdatedEntity(EntityManager em) {
        Statistics statistics = new Statistics()
            .todaysales(UPDATED_TODAYSALES)
            .totalCustomersNumb(UPDATED_TOTAL_CUSTOMERS_NUMB)
            .custmerTotal(UPDATED_CUSTMER_TOTAL)
            .schedTotalNexWeek(UPDATED_SCHED_TOTAL_NEX_WEEK)
            .totalCarts(UPDATED_TOTAL_CARTS)
            .totalPending(UPDATED_TOTAL_PENDING)
            .totalPayments(UPDATED_TOTAL_PAYMENTS)
            .pendingPayments(UPDATED_PENDING_PAYMENTS)
            .pendingNumberPayments(UPDATED_PENDING_NUMBER_PAYMENTS)
            .totalCars(UPDATED_TOTAL_CARS)
            .totalTrucks(UPDATED_TOTAL_TRUCKS)
            .totalOther1(UPDATED_TOTAL_OTHER_1)
            .totalOther2(UPDATED_TOTAL_OTHER_2)
            .latestpayments(UPDATED_LATESTPAYMENTS)
            .ltstsupplierpaym(UPDATED_LTSTSUPPLIERPAYM)
            .recentrlycompltd(UPDATED_RECENTRLYCOMPLTD);
        return statistics;
    }

    @BeforeEach
    public void initTest() {
        statistics = createEntity(em);
    }

    @Test
    @Transactional
    void createStatistics() throws Exception {
        int databaseSizeBeforeCreate = statisticsRepository.findAll().size();
        // Create the Statistics
        restStatisticsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statistics)))
            .andExpect(status().isCreated());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeCreate + 1);
        Statistics testStatistics = statisticsList.get(statisticsList.size() - 1);
        assertThat(testStatistics.getTodaysales()).isEqualByComparingTo(DEFAULT_TODAYSALES);
        assertThat(testStatistics.getTotalCustomersNumb()).isEqualTo(DEFAULT_TOTAL_CUSTOMERS_NUMB);
        assertThat(testStatistics.getCustmerTotal()).isEqualByComparingTo(DEFAULT_CUSTMER_TOTAL);
        assertThat(testStatistics.getSchedTotalNexWeek()).isEqualTo(DEFAULT_SCHED_TOTAL_NEX_WEEK);
        assertThat(testStatistics.getTotalCarts()).isEqualTo(DEFAULT_TOTAL_CARTS);
        assertThat(testStatistics.getTotalPending()).isEqualTo(DEFAULT_TOTAL_PENDING);
        assertThat(testStatistics.getTotalPayments()).isEqualByComparingTo(DEFAULT_TOTAL_PAYMENTS);
        assertThat(testStatistics.getPendingPayments()).isEqualByComparingTo(DEFAULT_PENDING_PAYMENTS);
        assertThat(testStatistics.getPendingNumberPayments()).isEqualTo(DEFAULT_PENDING_NUMBER_PAYMENTS);
        assertThat(testStatistics.getTotalCars()).isEqualTo(DEFAULT_TOTAL_CARS);
        assertThat(testStatistics.getTotalTrucks()).isEqualTo(DEFAULT_TOTAL_TRUCKS);
        assertThat(testStatistics.getTotalOther1()).isEqualTo(DEFAULT_TOTAL_OTHER_1);
        assertThat(testStatistics.getTotalOther2()).isEqualTo(DEFAULT_TOTAL_OTHER_2);
        assertThat(testStatistics.getLatestpayments()).isEqualTo(DEFAULT_LATESTPAYMENTS);
        assertThat(testStatistics.getLtstsupplierpaym()).isEqualTo(DEFAULT_LTSTSUPPLIERPAYM);
        assertThat(testStatistics.getRecentrlycompltd()).isEqualTo(DEFAULT_RECENTRLYCOMPLTD);
    }

    @Test
    @Transactional
    void createStatisticsWithExistingId() throws Exception {
        // Create the Statistics with an existing ID
        statistics.setId(1L);

        int databaseSizeBeforeCreate = statisticsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statistics)))
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatistics() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].todaysales").value(hasItem(sameNumber(DEFAULT_TODAYSALES))))
            .andExpect(jsonPath("$.[*].totalCustomersNumb").value(hasItem(DEFAULT_TOTAL_CUSTOMERS_NUMB)))
            .andExpect(jsonPath("$.[*].custmerTotal").value(hasItem(sameNumber(DEFAULT_CUSTMER_TOTAL))))
            .andExpect(jsonPath("$.[*].schedTotalNexWeek").value(hasItem(DEFAULT_SCHED_TOTAL_NEX_WEEK)))
            .andExpect(jsonPath("$.[*].totalCarts").value(hasItem(DEFAULT_TOTAL_CARTS)))
            .andExpect(jsonPath("$.[*].totalPending").value(hasItem(DEFAULT_TOTAL_PENDING)))
            .andExpect(jsonPath("$.[*].totalPayments").value(hasItem(sameNumber(DEFAULT_TOTAL_PAYMENTS))))
            .andExpect(jsonPath("$.[*].pendingPayments").value(hasItem(sameNumber(DEFAULT_PENDING_PAYMENTS))))
            .andExpect(jsonPath("$.[*].pendingNumberPayments").value(hasItem(DEFAULT_PENDING_NUMBER_PAYMENTS)))
            .andExpect(jsonPath("$.[*].totalCars").value(hasItem(DEFAULT_TOTAL_CARS)))
            .andExpect(jsonPath("$.[*].totalTrucks").value(hasItem(DEFAULT_TOTAL_TRUCKS)))
            .andExpect(jsonPath("$.[*].totalOther1").value(hasItem(DEFAULT_TOTAL_OTHER_1)))
            .andExpect(jsonPath("$.[*].totalOther2").value(hasItem(DEFAULT_TOTAL_OTHER_2)))
            .andExpect(jsonPath("$.[*].latestpayments").value(hasItem(DEFAULT_LATESTPAYMENTS)))
            .andExpect(jsonPath("$.[*].ltstsupplierpaym").value(hasItem(DEFAULT_LTSTSUPPLIERPAYM)))
            .andExpect(jsonPath("$.[*].recentrlycompltd").value(hasItem(DEFAULT_RECENTRLYCOMPLTD)));
    }

    @Test
    @Transactional
    void getStatistics() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get the statistics
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL_ID, statistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statistics.getId().intValue()))
            .andExpect(jsonPath("$.todaysales").value(sameNumber(DEFAULT_TODAYSALES)))
            .andExpect(jsonPath("$.totalCustomersNumb").value(DEFAULT_TOTAL_CUSTOMERS_NUMB))
            .andExpect(jsonPath("$.custmerTotal").value(sameNumber(DEFAULT_CUSTMER_TOTAL)))
            .andExpect(jsonPath("$.schedTotalNexWeek").value(DEFAULT_SCHED_TOTAL_NEX_WEEK))
            .andExpect(jsonPath("$.totalCarts").value(DEFAULT_TOTAL_CARTS))
            .andExpect(jsonPath("$.totalPending").value(DEFAULT_TOTAL_PENDING))
            .andExpect(jsonPath("$.totalPayments").value(sameNumber(DEFAULT_TOTAL_PAYMENTS)))
            .andExpect(jsonPath("$.pendingPayments").value(sameNumber(DEFAULT_PENDING_PAYMENTS)))
            .andExpect(jsonPath("$.pendingNumberPayments").value(DEFAULT_PENDING_NUMBER_PAYMENTS))
            .andExpect(jsonPath("$.totalCars").value(DEFAULT_TOTAL_CARS))
            .andExpect(jsonPath("$.totalTrucks").value(DEFAULT_TOTAL_TRUCKS))
            .andExpect(jsonPath("$.totalOther1").value(DEFAULT_TOTAL_OTHER_1))
            .andExpect(jsonPath("$.totalOther2").value(DEFAULT_TOTAL_OTHER_2))
            .andExpect(jsonPath("$.latestpayments").value(DEFAULT_LATESTPAYMENTS))
            .andExpect(jsonPath("$.ltstsupplierpaym").value(DEFAULT_LTSTSUPPLIERPAYM))
            .andExpect(jsonPath("$.recentrlycompltd").value(DEFAULT_RECENTRLYCOMPLTD));
    }

    @Test
    @Transactional
    void getStatisticsByIdFiltering() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        Long id = statistics.getId();

        defaultStatisticsShouldBeFound("id.equals=" + id);
        defaultStatisticsShouldNotBeFound("id.notEquals=" + id);

        defaultStatisticsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatisticsShouldNotBeFound("id.greaterThan=" + id);

        defaultStatisticsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatisticsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStatisticsByTodaysalesIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where todaysales equals to DEFAULT_TODAYSALES
        defaultStatisticsShouldBeFound("todaysales.equals=" + DEFAULT_TODAYSALES);

        // Get all the statisticsList where todaysales equals to UPDATED_TODAYSALES
        defaultStatisticsShouldNotBeFound("todaysales.equals=" + UPDATED_TODAYSALES);
    }

    @Test
    @Transactional
    void getAllStatisticsByTodaysalesIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where todaysales in DEFAULT_TODAYSALES or UPDATED_TODAYSALES
        defaultStatisticsShouldBeFound("todaysales.in=" + DEFAULT_TODAYSALES + "," + UPDATED_TODAYSALES);

        // Get all the statisticsList where todaysales equals to UPDATED_TODAYSALES
        defaultStatisticsShouldNotBeFound("todaysales.in=" + UPDATED_TODAYSALES);
    }

    @Test
    @Transactional
    void getAllStatisticsByTodaysalesIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where todaysales is not null
        defaultStatisticsShouldBeFound("todaysales.specified=true");

        // Get all the statisticsList where todaysales is null
        defaultStatisticsShouldNotBeFound("todaysales.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByTodaysalesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where todaysales is greater than or equal to DEFAULT_TODAYSALES
        defaultStatisticsShouldBeFound("todaysales.greaterThanOrEqual=" + DEFAULT_TODAYSALES);

        // Get all the statisticsList where todaysales is greater than or equal to UPDATED_TODAYSALES
        defaultStatisticsShouldNotBeFound("todaysales.greaterThanOrEqual=" + UPDATED_TODAYSALES);
    }

    @Test
    @Transactional
    void getAllStatisticsByTodaysalesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where todaysales is less than or equal to DEFAULT_TODAYSALES
        defaultStatisticsShouldBeFound("todaysales.lessThanOrEqual=" + DEFAULT_TODAYSALES);

        // Get all the statisticsList where todaysales is less than or equal to SMALLER_TODAYSALES
        defaultStatisticsShouldNotBeFound("todaysales.lessThanOrEqual=" + SMALLER_TODAYSALES);
    }

    @Test
    @Transactional
    void getAllStatisticsByTodaysalesIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where todaysales is less than DEFAULT_TODAYSALES
        defaultStatisticsShouldNotBeFound("todaysales.lessThan=" + DEFAULT_TODAYSALES);

        // Get all the statisticsList where todaysales is less than UPDATED_TODAYSALES
        defaultStatisticsShouldBeFound("todaysales.lessThan=" + UPDATED_TODAYSALES);
    }

    @Test
    @Transactional
    void getAllStatisticsByTodaysalesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where todaysales is greater than DEFAULT_TODAYSALES
        defaultStatisticsShouldNotBeFound("todaysales.greaterThan=" + DEFAULT_TODAYSALES);

        // Get all the statisticsList where todaysales is greater than SMALLER_TODAYSALES
        defaultStatisticsShouldBeFound("todaysales.greaterThan=" + SMALLER_TODAYSALES);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCustomersNumbIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCustomersNumb equals to DEFAULT_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldBeFound("totalCustomersNumb.equals=" + DEFAULT_TOTAL_CUSTOMERS_NUMB);

        // Get all the statisticsList where totalCustomersNumb equals to UPDATED_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldNotBeFound("totalCustomersNumb.equals=" + UPDATED_TOTAL_CUSTOMERS_NUMB);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCustomersNumbIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCustomersNumb in DEFAULT_TOTAL_CUSTOMERS_NUMB or UPDATED_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldBeFound("totalCustomersNumb.in=" + DEFAULT_TOTAL_CUSTOMERS_NUMB + "," + UPDATED_TOTAL_CUSTOMERS_NUMB);

        // Get all the statisticsList where totalCustomersNumb equals to UPDATED_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldNotBeFound("totalCustomersNumb.in=" + UPDATED_TOTAL_CUSTOMERS_NUMB);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCustomersNumbIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCustomersNumb is not null
        defaultStatisticsShouldBeFound("totalCustomersNumb.specified=true");

        // Get all the statisticsList where totalCustomersNumb is null
        defaultStatisticsShouldNotBeFound("totalCustomersNumb.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCustomersNumbIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCustomersNumb is greater than or equal to DEFAULT_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldBeFound("totalCustomersNumb.greaterThanOrEqual=" + DEFAULT_TOTAL_CUSTOMERS_NUMB);

        // Get all the statisticsList where totalCustomersNumb is greater than or equal to UPDATED_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldNotBeFound("totalCustomersNumb.greaterThanOrEqual=" + UPDATED_TOTAL_CUSTOMERS_NUMB);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCustomersNumbIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCustomersNumb is less than or equal to DEFAULT_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldBeFound("totalCustomersNumb.lessThanOrEqual=" + DEFAULT_TOTAL_CUSTOMERS_NUMB);

        // Get all the statisticsList where totalCustomersNumb is less than or equal to SMALLER_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldNotBeFound("totalCustomersNumb.lessThanOrEqual=" + SMALLER_TOTAL_CUSTOMERS_NUMB);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCustomersNumbIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCustomersNumb is less than DEFAULT_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldNotBeFound("totalCustomersNumb.lessThan=" + DEFAULT_TOTAL_CUSTOMERS_NUMB);

        // Get all the statisticsList where totalCustomersNumb is less than UPDATED_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldBeFound("totalCustomersNumb.lessThan=" + UPDATED_TOTAL_CUSTOMERS_NUMB);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCustomersNumbIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCustomersNumb is greater than DEFAULT_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldNotBeFound("totalCustomersNumb.greaterThan=" + DEFAULT_TOTAL_CUSTOMERS_NUMB);

        // Get all the statisticsList where totalCustomersNumb is greater than SMALLER_TOTAL_CUSTOMERS_NUMB
        defaultStatisticsShouldBeFound("totalCustomersNumb.greaterThan=" + SMALLER_TOTAL_CUSTOMERS_NUMB);
    }

    @Test
    @Transactional
    void getAllStatisticsByCustmerTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where custmerTotal equals to DEFAULT_CUSTMER_TOTAL
        defaultStatisticsShouldBeFound("custmerTotal.equals=" + DEFAULT_CUSTMER_TOTAL);

        // Get all the statisticsList where custmerTotal equals to UPDATED_CUSTMER_TOTAL
        defaultStatisticsShouldNotBeFound("custmerTotal.equals=" + UPDATED_CUSTMER_TOTAL);
    }

    @Test
    @Transactional
    void getAllStatisticsByCustmerTotalIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where custmerTotal in DEFAULT_CUSTMER_TOTAL or UPDATED_CUSTMER_TOTAL
        defaultStatisticsShouldBeFound("custmerTotal.in=" + DEFAULT_CUSTMER_TOTAL + "," + UPDATED_CUSTMER_TOTAL);

        // Get all the statisticsList where custmerTotal equals to UPDATED_CUSTMER_TOTAL
        defaultStatisticsShouldNotBeFound("custmerTotal.in=" + UPDATED_CUSTMER_TOTAL);
    }

    @Test
    @Transactional
    void getAllStatisticsByCustmerTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where custmerTotal is not null
        defaultStatisticsShouldBeFound("custmerTotal.specified=true");

        // Get all the statisticsList where custmerTotal is null
        defaultStatisticsShouldNotBeFound("custmerTotal.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByCustmerTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where custmerTotal is greater than or equal to DEFAULT_CUSTMER_TOTAL
        defaultStatisticsShouldBeFound("custmerTotal.greaterThanOrEqual=" + DEFAULT_CUSTMER_TOTAL);

        // Get all the statisticsList where custmerTotal is greater than or equal to UPDATED_CUSTMER_TOTAL
        defaultStatisticsShouldNotBeFound("custmerTotal.greaterThanOrEqual=" + UPDATED_CUSTMER_TOTAL);
    }

    @Test
    @Transactional
    void getAllStatisticsByCustmerTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where custmerTotal is less than or equal to DEFAULT_CUSTMER_TOTAL
        defaultStatisticsShouldBeFound("custmerTotal.lessThanOrEqual=" + DEFAULT_CUSTMER_TOTAL);

        // Get all the statisticsList where custmerTotal is less than or equal to SMALLER_CUSTMER_TOTAL
        defaultStatisticsShouldNotBeFound("custmerTotal.lessThanOrEqual=" + SMALLER_CUSTMER_TOTAL);
    }

    @Test
    @Transactional
    void getAllStatisticsByCustmerTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where custmerTotal is less than DEFAULT_CUSTMER_TOTAL
        defaultStatisticsShouldNotBeFound("custmerTotal.lessThan=" + DEFAULT_CUSTMER_TOTAL);

        // Get all the statisticsList where custmerTotal is less than UPDATED_CUSTMER_TOTAL
        defaultStatisticsShouldBeFound("custmerTotal.lessThan=" + UPDATED_CUSTMER_TOTAL);
    }

    @Test
    @Transactional
    void getAllStatisticsByCustmerTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where custmerTotal is greater than DEFAULT_CUSTMER_TOTAL
        defaultStatisticsShouldNotBeFound("custmerTotal.greaterThan=" + DEFAULT_CUSTMER_TOTAL);

        // Get all the statisticsList where custmerTotal is greater than SMALLER_CUSTMER_TOTAL
        defaultStatisticsShouldBeFound("custmerTotal.greaterThan=" + SMALLER_CUSTMER_TOTAL);
    }

    @Test
    @Transactional
    void getAllStatisticsBySchedTotalNexWeekIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where schedTotalNexWeek equals to DEFAULT_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldBeFound("schedTotalNexWeek.equals=" + DEFAULT_SCHED_TOTAL_NEX_WEEK);

        // Get all the statisticsList where schedTotalNexWeek equals to UPDATED_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldNotBeFound("schedTotalNexWeek.equals=" + UPDATED_SCHED_TOTAL_NEX_WEEK);
    }

    @Test
    @Transactional
    void getAllStatisticsBySchedTotalNexWeekIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where schedTotalNexWeek in DEFAULT_SCHED_TOTAL_NEX_WEEK or UPDATED_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldBeFound("schedTotalNexWeek.in=" + DEFAULT_SCHED_TOTAL_NEX_WEEK + "," + UPDATED_SCHED_TOTAL_NEX_WEEK);

        // Get all the statisticsList where schedTotalNexWeek equals to UPDATED_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldNotBeFound("schedTotalNexWeek.in=" + UPDATED_SCHED_TOTAL_NEX_WEEK);
    }

    @Test
    @Transactional
    void getAllStatisticsBySchedTotalNexWeekIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where schedTotalNexWeek is not null
        defaultStatisticsShouldBeFound("schedTotalNexWeek.specified=true");

        // Get all the statisticsList where schedTotalNexWeek is null
        defaultStatisticsShouldNotBeFound("schedTotalNexWeek.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsBySchedTotalNexWeekIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where schedTotalNexWeek is greater than or equal to DEFAULT_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldBeFound("schedTotalNexWeek.greaterThanOrEqual=" + DEFAULT_SCHED_TOTAL_NEX_WEEK);

        // Get all the statisticsList where schedTotalNexWeek is greater than or equal to UPDATED_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldNotBeFound("schedTotalNexWeek.greaterThanOrEqual=" + UPDATED_SCHED_TOTAL_NEX_WEEK);
    }

    @Test
    @Transactional
    void getAllStatisticsBySchedTotalNexWeekIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where schedTotalNexWeek is less than or equal to DEFAULT_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldBeFound("schedTotalNexWeek.lessThanOrEqual=" + DEFAULT_SCHED_TOTAL_NEX_WEEK);

        // Get all the statisticsList where schedTotalNexWeek is less than or equal to SMALLER_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldNotBeFound("schedTotalNexWeek.lessThanOrEqual=" + SMALLER_SCHED_TOTAL_NEX_WEEK);
    }

    @Test
    @Transactional
    void getAllStatisticsBySchedTotalNexWeekIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where schedTotalNexWeek is less than DEFAULT_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldNotBeFound("schedTotalNexWeek.lessThan=" + DEFAULT_SCHED_TOTAL_NEX_WEEK);

        // Get all the statisticsList where schedTotalNexWeek is less than UPDATED_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldBeFound("schedTotalNexWeek.lessThan=" + UPDATED_SCHED_TOTAL_NEX_WEEK);
    }

    @Test
    @Transactional
    void getAllStatisticsBySchedTotalNexWeekIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where schedTotalNexWeek is greater than DEFAULT_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldNotBeFound("schedTotalNexWeek.greaterThan=" + DEFAULT_SCHED_TOTAL_NEX_WEEK);

        // Get all the statisticsList where schedTotalNexWeek is greater than SMALLER_SCHED_TOTAL_NEX_WEEK
        defaultStatisticsShouldBeFound("schedTotalNexWeek.greaterThan=" + SMALLER_SCHED_TOTAL_NEX_WEEK);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCartsIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCarts equals to DEFAULT_TOTAL_CARTS
        defaultStatisticsShouldBeFound("totalCarts.equals=" + DEFAULT_TOTAL_CARTS);

        // Get all the statisticsList where totalCarts equals to UPDATED_TOTAL_CARTS
        defaultStatisticsShouldNotBeFound("totalCarts.equals=" + UPDATED_TOTAL_CARTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCartsIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCarts in DEFAULT_TOTAL_CARTS or UPDATED_TOTAL_CARTS
        defaultStatisticsShouldBeFound("totalCarts.in=" + DEFAULT_TOTAL_CARTS + "," + UPDATED_TOTAL_CARTS);

        // Get all the statisticsList where totalCarts equals to UPDATED_TOTAL_CARTS
        defaultStatisticsShouldNotBeFound("totalCarts.in=" + UPDATED_TOTAL_CARTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCartsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCarts is not null
        defaultStatisticsShouldBeFound("totalCarts.specified=true");

        // Get all the statisticsList where totalCarts is null
        defaultStatisticsShouldNotBeFound("totalCarts.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCartsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCarts is greater than or equal to DEFAULT_TOTAL_CARTS
        defaultStatisticsShouldBeFound("totalCarts.greaterThanOrEqual=" + DEFAULT_TOTAL_CARTS);

        // Get all the statisticsList where totalCarts is greater than or equal to UPDATED_TOTAL_CARTS
        defaultStatisticsShouldNotBeFound("totalCarts.greaterThanOrEqual=" + UPDATED_TOTAL_CARTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCartsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCarts is less than or equal to DEFAULT_TOTAL_CARTS
        defaultStatisticsShouldBeFound("totalCarts.lessThanOrEqual=" + DEFAULT_TOTAL_CARTS);

        // Get all the statisticsList where totalCarts is less than or equal to SMALLER_TOTAL_CARTS
        defaultStatisticsShouldNotBeFound("totalCarts.lessThanOrEqual=" + SMALLER_TOTAL_CARTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCartsIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCarts is less than DEFAULT_TOTAL_CARTS
        defaultStatisticsShouldNotBeFound("totalCarts.lessThan=" + DEFAULT_TOTAL_CARTS);

        // Get all the statisticsList where totalCarts is less than UPDATED_TOTAL_CARTS
        defaultStatisticsShouldBeFound("totalCarts.lessThan=" + UPDATED_TOTAL_CARTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCartsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCarts is greater than DEFAULT_TOTAL_CARTS
        defaultStatisticsShouldNotBeFound("totalCarts.greaterThan=" + DEFAULT_TOTAL_CARTS);

        // Get all the statisticsList where totalCarts is greater than SMALLER_TOTAL_CARTS
        defaultStatisticsShouldBeFound("totalCarts.greaterThan=" + SMALLER_TOTAL_CARTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPendingIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPending equals to DEFAULT_TOTAL_PENDING
        defaultStatisticsShouldBeFound("totalPending.equals=" + DEFAULT_TOTAL_PENDING);

        // Get all the statisticsList where totalPending equals to UPDATED_TOTAL_PENDING
        defaultStatisticsShouldNotBeFound("totalPending.equals=" + UPDATED_TOTAL_PENDING);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPendingIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPending in DEFAULT_TOTAL_PENDING or UPDATED_TOTAL_PENDING
        defaultStatisticsShouldBeFound("totalPending.in=" + DEFAULT_TOTAL_PENDING + "," + UPDATED_TOTAL_PENDING);

        // Get all the statisticsList where totalPending equals to UPDATED_TOTAL_PENDING
        defaultStatisticsShouldNotBeFound("totalPending.in=" + UPDATED_TOTAL_PENDING);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPendingIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPending is not null
        defaultStatisticsShouldBeFound("totalPending.specified=true");

        // Get all the statisticsList where totalPending is null
        defaultStatisticsShouldNotBeFound("totalPending.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPendingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPending is greater than or equal to DEFAULT_TOTAL_PENDING
        defaultStatisticsShouldBeFound("totalPending.greaterThanOrEqual=" + DEFAULT_TOTAL_PENDING);

        // Get all the statisticsList where totalPending is greater than or equal to UPDATED_TOTAL_PENDING
        defaultStatisticsShouldNotBeFound("totalPending.greaterThanOrEqual=" + UPDATED_TOTAL_PENDING);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPendingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPending is less than or equal to DEFAULT_TOTAL_PENDING
        defaultStatisticsShouldBeFound("totalPending.lessThanOrEqual=" + DEFAULT_TOTAL_PENDING);

        // Get all the statisticsList where totalPending is less than or equal to SMALLER_TOTAL_PENDING
        defaultStatisticsShouldNotBeFound("totalPending.lessThanOrEqual=" + SMALLER_TOTAL_PENDING);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPendingIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPending is less than DEFAULT_TOTAL_PENDING
        defaultStatisticsShouldNotBeFound("totalPending.lessThan=" + DEFAULT_TOTAL_PENDING);

        // Get all the statisticsList where totalPending is less than UPDATED_TOTAL_PENDING
        defaultStatisticsShouldBeFound("totalPending.lessThan=" + UPDATED_TOTAL_PENDING);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPendingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPending is greater than DEFAULT_TOTAL_PENDING
        defaultStatisticsShouldNotBeFound("totalPending.greaterThan=" + DEFAULT_TOTAL_PENDING);

        // Get all the statisticsList where totalPending is greater than SMALLER_TOTAL_PENDING
        defaultStatisticsShouldBeFound("totalPending.greaterThan=" + SMALLER_TOTAL_PENDING);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPaymentsIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPayments equals to DEFAULT_TOTAL_PAYMENTS
        defaultStatisticsShouldBeFound("totalPayments.equals=" + DEFAULT_TOTAL_PAYMENTS);

        // Get all the statisticsList where totalPayments equals to UPDATED_TOTAL_PAYMENTS
        defaultStatisticsShouldNotBeFound("totalPayments.equals=" + UPDATED_TOTAL_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPaymentsIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPayments in DEFAULT_TOTAL_PAYMENTS or UPDATED_TOTAL_PAYMENTS
        defaultStatisticsShouldBeFound("totalPayments.in=" + DEFAULT_TOTAL_PAYMENTS + "," + UPDATED_TOTAL_PAYMENTS);

        // Get all the statisticsList where totalPayments equals to UPDATED_TOTAL_PAYMENTS
        defaultStatisticsShouldNotBeFound("totalPayments.in=" + UPDATED_TOTAL_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPaymentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPayments is not null
        defaultStatisticsShouldBeFound("totalPayments.specified=true");

        // Get all the statisticsList where totalPayments is null
        defaultStatisticsShouldNotBeFound("totalPayments.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPaymentsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPayments is greater than or equal to DEFAULT_TOTAL_PAYMENTS
        defaultStatisticsShouldBeFound("totalPayments.greaterThanOrEqual=" + DEFAULT_TOTAL_PAYMENTS);

        // Get all the statisticsList where totalPayments is greater than or equal to UPDATED_TOTAL_PAYMENTS
        defaultStatisticsShouldNotBeFound("totalPayments.greaterThanOrEqual=" + UPDATED_TOTAL_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPaymentsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPayments is less than or equal to DEFAULT_TOTAL_PAYMENTS
        defaultStatisticsShouldBeFound("totalPayments.lessThanOrEqual=" + DEFAULT_TOTAL_PAYMENTS);

        // Get all the statisticsList where totalPayments is less than or equal to SMALLER_TOTAL_PAYMENTS
        defaultStatisticsShouldNotBeFound("totalPayments.lessThanOrEqual=" + SMALLER_TOTAL_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPaymentsIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPayments is less than DEFAULT_TOTAL_PAYMENTS
        defaultStatisticsShouldNotBeFound("totalPayments.lessThan=" + DEFAULT_TOTAL_PAYMENTS);

        // Get all the statisticsList where totalPayments is less than UPDATED_TOTAL_PAYMENTS
        defaultStatisticsShouldBeFound("totalPayments.lessThan=" + UPDATED_TOTAL_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalPaymentsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalPayments is greater than DEFAULT_TOTAL_PAYMENTS
        defaultStatisticsShouldNotBeFound("totalPayments.greaterThan=" + DEFAULT_TOTAL_PAYMENTS);

        // Get all the statisticsList where totalPayments is greater than SMALLER_TOTAL_PAYMENTS
        defaultStatisticsShouldBeFound("totalPayments.greaterThan=" + SMALLER_TOTAL_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingPaymentsIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingPayments equals to DEFAULT_PENDING_PAYMENTS
        defaultStatisticsShouldBeFound("pendingPayments.equals=" + DEFAULT_PENDING_PAYMENTS);

        // Get all the statisticsList where pendingPayments equals to UPDATED_PENDING_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingPayments.equals=" + UPDATED_PENDING_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingPaymentsIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingPayments in DEFAULT_PENDING_PAYMENTS or UPDATED_PENDING_PAYMENTS
        defaultStatisticsShouldBeFound("pendingPayments.in=" + DEFAULT_PENDING_PAYMENTS + "," + UPDATED_PENDING_PAYMENTS);

        // Get all the statisticsList where pendingPayments equals to UPDATED_PENDING_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingPayments.in=" + UPDATED_PENDING_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingPaymentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingPayments is not null
        defaultStatisticsShouldBeFound("pendingPayments.specified=true");

        // Get all the statisticsList where pendingPayments is null
        defaultStatisticsShouldNotBeFound("pendingPayments.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingPaymentsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingPayments is greater than or equal to DEFAULT_PENDING_PAYMENTS
        defaultStatisticsShouldBeFound("pendingPayments.greaterThanOrEqual=" + DEFAULT_PENDING_PAYMENTS);

        // Get all the statisticsList where pendingPayments is greater than or equal to UPDATED_PENDING_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingPayments.greaterThanOrEqual=" + UPDATED_PENDING_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingPaymentsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingPayments is less than or equal to DEFAULT_PENDING_PAYMENTS
        defaultStatisticsShouldBeFound("pendingPayments.lessThanOrEqual=" + DEFAULT_PENDING_PAYMENTS);

        // Get all the statisticsList where pendingPayments is less than or equal to SMALLER_PENDING_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingPayments.lessThanOrEqual=" + SMALLER_PENDING_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingPaymentsIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingPayments is less than DEFAULT_PENDING_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingPayments.lessThan=" + DEFAULT_PENDING_PAYMENTS);

        // Get all the statisticsList where pendingPayments is less than UPDATED_PENDING_PAYMENTS
        defaultStatisticsShouldBeFound("pendingPayments.lessThan=" + UPDATED_PENDING_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingPaymentsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingPayments is greater than DEFAULT_PENDING_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingPayments.greaterThan=" + DEFAULT_PENDING_PAYMENTS);

        // Get all the statisticsList where pendingPayments is greater than SMALLER_PENDING_PAYMENTS
        defaultStatisticsShouldBeFound("pendingPayments.greaterThan=" + SMALLER_PENDING_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingNumberPaymentsIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingNumberPayments equals to DEFAULT_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldBeFound("pendingNumberPayments.equals=" + DEFAULT_PENDING_NUMBER_PAYMENTS);

        // Get all the statisticsList where pendingNumberPayments equals to UPDATED_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingNumberPayments.equals=" + UPDATED_PENDING_NUMBER_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingNumberPaymentsIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingNumberPayments in DEFAULT_PENDING_NUMBER_PAYMENTS or UPDATED_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldBeFound(
            "pendingNumberPayments.in=" + DEFAULT_PENDING_NUMBER_PAYMENTS + "," + UPDATED_PENDING_NUMBER_PAYMENTS
        );

        // Get all the statisticsList where pendingNumberPayments equals to UPDATED_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingNumberPayments.in=" + UPDATED_PENDING_NUMBER_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingNumberPaymentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingNumberPayments is not null
        defaultStatisticsShouldBeFound("pendingNumberPayments.specified=true");

        // Get all the statisticsList where pendingNumberPayments is null
        defaultStatisticsShouldNotBeFound("pendingNumberPayments.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingNumberPaymentsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingNumberPayments is greater than or equal to DEFAULT_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldBeFound("pendingNumberPayments.greaterThanOrEqual=" + DEFAULT_PENDING_NUMBER_PAYMENTS);

        // Get all the statisticsList where pendingNumberPayments is greater than or equal to UPDATED_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingNumberPayments.greaterThanOrEqual=" + UPDATED_PENDING_NUMBER_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingNumberPaymentsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingNumberPayments is less than or equal to DEFAULT_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldBeFound("pendingNumberPayments.lessThanOrEqual=" + DEFAULT_PENDING_NUMBER_PAYMENTS);

        // Get all the statisticsList where pendingNumberPayments is less than or equal to SMALLER_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingNumberPayments.lessThanOrEqual=" + SMALLER_PENDING_NUMBER_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingNumberPaymentsIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingNumberPayments is less than DEFAULT_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingNumberPayments.lessThan=" + DEFAULT_PENDING_NUMBER_PAYMENTS);

        // Get all the statisticsList where pendingNumberPayments is less than UPDATED_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldBeFound("pendingNumberPayments.lessThan=" + UPDATED_PENDING_NUMBER_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByPendingNumberPaymentsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where pendingNumberPayments is greater than DEFAULT_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldNotBeFound("pendingNumberPayments.greaterThan=" + DEFAULT_PENDING_NUMBER_PAYMENTS);

        // Get all the statisticsList where pendingNumberPayments is greater than SMALLER_PENDING_NUMBER_PAYMENTS
        defaultStatisticsShouldBeFound("pendingNumberPayments.greaterThan=" + SMALLER_PENDING_NUMBER_PAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCarsIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCars equals to DEFAULT_TOTAL_CARS
        defaultStatisticsShouldBeFound("totalCars.equals=" + DEFAULT_TOTAL_CARS);

        // Get all the statisticsList where totalCars equals to UPDATED_TOTAL_CARS
        defaultStatisticsShouldNotBeFound("totalCars.equals=" + UPDATED_TOTAL_CARS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCarsIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCars in DEFAULT_TOTAL_CARS or UPDATED_TOTAL_CARS
        defaultStatisticsShouldBeFound("totalCars.in=" + DEFAULT_TOTAL_CARS + "," + UPDATED_TOTAL_CARS);

        // Get all the statisticsList where totalCars equals to UPDATED_TOTAL_CARS
        defaultStatisticsShouldNotBeFound("totalCars.in=" + UPDATED_TOTAL_CARS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCarsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCars is not null
        defaultStatisticsShouldBeFound("totalCars.specified=true");

        // Get all the statisticsList where totalCars is null
        defaultStatisticsShouldNotBeFound("totalCars.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCarsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCars is greater than or equal to DEFAULT_TOTAL_CARS
        defaultStatisticsShouldBeFound("totalCars.greaterThanOrEqual=" + DEFAULT_TOTAL_CARS);

        // Get all the statisticsList where totalCars is greater than or equal to UPDATED_TOTAL_CARS
        defaultStatisticsShouldNotBeFound("totalCars.greaterThanOrEqual=" + UPDATED_TOTAL_CARS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCarsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCars is less than or equal to DEFAULT_TOTAL_CARS
        defaultStatisticsShouldBeFound("totalCars.lessThanOrEqual=" + DEFAULT_TOTAL_CARS);

        // Get all the statisticsList where totalCars is less than or equal to SMALLER_TOTAL_CARS
        defaultStatisticsShouldNotBeFound("totalCars.lessThanOrEqual=" + SMALLER_TOTAL_CARS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCarsIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCars is less than DEFAULT_TOTAL_CARS
        defaultStatisticsShouldNotBeFound("totalCars.lessThan=" + DEFAULT_TOTAL_CARS);

        // Get all the statisticsList where totalCars is less than UPDATED_TOTAL_CARS
        defaultStatisticsShouldBeFound("totalCars.lessThan=" + UPDATED_TOTAL_CARS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalCarsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalCars is greater than DEFAULT_TOTAL_CARS
        defaultStatisticsShouldNotBeFound("totalCars.greaterThan=" + DEFAULT_TOTAL_CARS);

        // Get all the statisticsList where totalCars is greater than SMALLER_TOTAL_CARS
        defaultStatisticsShouldBeFound("totalCars.greaterThan=" + SMALLER_TOTAL_CARS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalTrucksIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalTrucks equals to DEFAULT_TOTAL_TRUCKS
        defaultStatisticsShouldBeFound("totalTrucks.equals=" + DEFAULT_TOTAL_TRUCKS);

        // Get all the statisticsList where totalTrucks equals to UPDATED_TOTAL_TRUCKS
        defaultStatisticsShouldNotBeFound("totalTrucks.equals=" + UPDATED_TOTAL_TRUCKS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalTrucksIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalTrucks in DEFAULT_TOTAL_TRUCKS or UPDATED_TOTAL_TRUCKS
        defaultStatisticsShouldBeFound("totalTrucks.in=" + DEFAULT_TOTAL_TRUCKS + "," + UPDATED_TOTAL_TRUCKS);

        // Get all the statisticsList where totalTrucks equals to UPDATED_TOTAL_TRUCKS
        defaultStatisticsShouldNotBeFound("totalTrucks.in=" + UPDATED_TOTAL_TRUCKS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalTrucksIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalTrucks is not null
        defaultStatisticsShouldBeFound("totalTrucks.specified=true");

        // Get all the statisticsList where totalTrucks is null
        defaultStatisticsShouldNotBeFound("totalTrucks.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalTrucksIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalTrucks is greater than or equal to DEFAULT_TOTAL_TRUCKS
        defaultStatisticsShouldBeFound("totalTrucks.greaterThanOrEqual=" + DEFAULT_TOTAL_TRUCKS);

        // Get all the statisticsList where totalTrucks is greater than or equal to UPDATED_TOTAL_TRUCKS
        defaultStatisticsShouldNotBeFound("totalTrucks.greaterThanOrEqual=" + UPDATED_TOTAL_TRUCKS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalTrucksIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalTrucks is less than or equal to DEFAULT_TOTAL_TRUCKS
        defaultStatisticsShouldBeFound("totalTrucks.lessThanOrEqual=" + DEFAULT_TOTAL_TRUCKS);

        // Get all the statisticsList where totalTrucks is less than or equal to SMALLER_TOTAL_TRUCKS
        defaultStatisticsShouldNotBeFound("totalTrucks.lessThanOrEqual=" + SMALLER_TOTAL_TRUCKS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalTrucksIsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalTrucks is less than DEFAULT_TOTAL_TRUCKS
        defaultStatisticsShouldNotBeFound("totalTrucks.lessThan=" + DEFAULT_TOTAL_TRUCKS);

        // Get all the statisticsList where totalTrucks is less than UPDATED_TOTAL_TRUCKS
        defaultStatisticsShouldBeFound("totalTrucks.lessThan=" + UPDATED_TOTAL_TRUCKS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalTrucksIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalTrucks is greater than DEFAULT_TOTAL_TRUCKS
        defaultStatisticsShouldNotBeFound("totalTrucks.greaterThan=" + DEFAULT_TOTAL_TRUCKS);

        // Get all the statisticsList where totalTrucks is greater than SMALLER_TOTAL_TRUCKS
        defaultStatisticsShouldBeFound("totalTrucks.greaterThan=" + SMALLER_TOTAL_TRUCKS);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther1IsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther1 equals to DEFAULT_TOTAL_OTHER_1
        defaultStatisticsShouldBeFound("totalOther1.equals=" + DEFAULT_TOTAL_OTHER_1);

        // Get all the statisticsList where totalOther1 equals to UPDATED_TOTAL_OTHER_1
        defaultStatisticsShouldNotBeFound("totalOther1.equals=" + UPDATED_TOTAL_OTHER_1);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther1IsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther1 in DEFAULT_TOTAL_OTHER_1 or UPDATED_TOTAL_OTHER_1
        defaultStatisticsShouldBeFound("totalOther1.in=" + DEFAULT_TOTAL_OTHER_1 + "," + UPDATED_TOTAL_OTHER_1);

        // Get all the statisticsList where totalOther1 equals to UPDATED_TOTAL_OTHER_1
        defaultStatisticsShouldNotBeFound("totalOther1.in=" + UPDATED_TOTAL_OTHER_1);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther1IsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther1 is not null
        defaultStatisticsShouldBeFound("totalOther1.specified=true");

        // Get all the statisticsList where totalOther1 is null
        defaultStatisticsShouldNotBeFound("totalOther1.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther1 is greater than or equal to DEFAULT_TOTAL_OTHER_1
        defaultStatisticsShouldBeFound("totalOther1.greaterThanOrEqual=" + DEFAULT_TOTAL_OTHER_1);

        // Get all the statisticsList where totalOther1 is greater than or equal to UPDATED_TOTAL_OTHER_1
        defaultStatisticsShouldNotBeFound("totalOther1.greaterThanOrEqual=" + UPDATED_TOTAL_OTHER_1);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther1 is less than or equal to DEFAULT_TOTAL_OTHER_1
        defaultStatisticsShouldBeFound("totalOther1.lessThanOrEqual=" + DEFAULT_TOTAL_OTHER_1);

        // Get all the statisticsList where totalOther1 is less than or equal to SMALLER_TOTAL_OTHER_1
        defaultStatisticsShouldNotBeFound("totalOther1.lessThanOrEqual=" + SMALLER_TOTAL_OTHER_1);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther1IsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther1 is less than DEFAULT_TOTAL_OTHER_1
        defaultStatisticsShouldNotBeFound("totalOther1.lessThan=" + DEFAULT_TOTAL_OTHER_1);

        // Get all the statisticsList where totalOther1 is less than UPDATED_TOTAL_OTHER_1
        defaultStatisticsShouldBeFound("totalOther1.lessThan=" + UPDATED_TOTAL_OTHER_1);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther1 is greater than DEFAULT_TOTAL_OTHER_1
        defaultStatisticsShouldNotBeFound("totalOther1.greaterThan=" + DEFAULT_TOTAL_OTHER_1);

        // Get all the statisticsList where totalOther1 is greater than SMALLER_TOTAL_OTHER_1
        defaultStatisticsShouldBeFound("totalOther1.greaterThan=" + SMALLER_TOTAL_OTHER_1);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther2IsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther2 equals to DEFAULT_TOTAL_OTHER_2
        defaultStatisticsShouldBeFound("totalOther2.equals=" + DEFAULT_TOTAL_OTHER_2);

        // Get all the statisticsList where totalOther2 equals to UPDATED_TOTAL_OTHER_2
        defaultStatisticsShouldNotBeFound("totalOther2.equals=" + UPDATED_TOTAL_OTHER_2);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther2IsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther2 in DEFAULT_TOTAL_OTHER_2 or UPDATED_TOTAL_OTHER_2
        defaultStatisticsShouldBeFound("totalOther2.in=" + DEFAULT_TOTAL_OTHER_2 + "," + UPDATED_TOTAL_OTHER_2);

        // Get all the statisticsList where totalOther2 equals to UPDATED_TOTAL_OTHER_2
        defaultStatisticsShouldNotBeFound("totalOther2.in=" + UPDATED_TOTAL_OTHER_2);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther2IsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther2 is not null
        defaultStatisticsShouldBeFound("totalOther2.specified=true");

        // Get all the statisticsList where totalOther2 is null
        defaultStatisticsShouldNotBeFound("totalOther2.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther2 is greater than or equal to DEFAULT_TOTAL_OTHER_2
        defaultStatisticsShouldBeFound("totalOther2.greaterThanOrEqual=" + DEFAULT_TOTAL_OTHER_2);

        // Get all the statisticsList where totalOther2 is greater than or equal to UPDATED_TOTAL_OTHER_2
        defaultStatisticsShouldNotBeFound("totalOther2.greaterThanOrEqual=" + UPDATED_TOTAL_OTHER_2);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther2 is less than or equal to DEFAULT_TOTAL_OTHER_2
        defaultStatisticsShouldBeFound("totalOther2.lessThanOrEqual=" + DEFAULT_TOTAL_OTHER_2);

        // Get all the statisticsList where totalOther2 is less than or equal to SMALLER_TOTAL_OTHER_2
        defaultStatisticsShouldNotBeFound("totalOther2.lessThanOrEqual=" + SMALLER_TOTAL_OTHER_2);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther2IsLessThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther2 is less than DEFAULT_TOTAL_OTHER_2
        defaultStatisticsShouldNotBeFound("totalOther2.lessThan=" + DEFAULT_TOTAL_OTHER_2);

        // Get all the statisticsList where totalOther2 is less than UPDATED_TOTAL_OTHER_2
        defaultStatisticsShouldBeFound("totalOther2.lessThan=" + UPDATED_TOTAL_OTHER_2);
    }

    @Test
    @Transactional
    void getAllStatisticsByTotalOther2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where totalOther2 is greater than DEFAULT_TOTAL_OTHER_2
        defaultStatisticsShouldNotBeFound("totalOther2.greaterThan=" + DEFAULT_TOTAL_OTHER_2);

        // Get all the statisticsList where totalOther2 is greater than SMALLER_TOTAL_OTHER_2
        defaultStatisticsShouldBeFound("totalOther2.greaterThan=" + SMALLER_TOTAL_OTHER_2);
    }

    @Test
    @Transactional
    void getAllStatisticsByLatestpaymentsIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where latestpayments equals to DEFAULT_LATESTPAYMENTS
        defaultStatisticsShouldBeFound("latestpayments.equals=" + DEFAULT_LATESTPAYMENTS);

        // Get all the statisticsList where latestpayments equals to UPDATED_LATESTPAYMENTS
        defaultStatisticsShouldNotBeFound("latestpayments.equals=" + UPDATED_LATESTPAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByLatestpaymentsIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where latestpayments in DEFAULT_LATESTPAYMENTS or UPDATED_LATESTPAYMENTS
        defaultStatisticsShouldBeFound("latestpayments.in=" + DEFAULT_LATESTPAYMENTS + "," + UPDATED_LATESTPAYMENTS);

        // Get all the statisticsList where latestpayments equals to UPDATED_LATESTPAYMENTS
        defaultStatisticsShouldNotBeFound("latestpayments.in=" + UPDATED_LATESTPAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByLatestpaymentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where latestpayments is not null
        defaultStatisticsShouldBeFound("latestpayments.specified=true");

        // Get all the statisticsList where latestpayments is null
        defaultStatisticsShouldNotBeFound("latestpayments.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByLatestpaymentsContainsSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where latestpayments contains DEFAULT_LATESTPAYMENTS
        defaultStatisticsShouldBeFound("latestpayments.contains=" + DEFAULT_LATESTPAYMENTS);

        // Get all the statisticsList where latestpayments contains UPDATED_LATESTPAYMENTS
        defaultStatisticsShouldNotBeFound("latestpayments.contains=" + UPDATED_LATESTPAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByLatestpaymentsNotContainsSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where latestpayments does not contain DEFAULT_LATESTPAYMENTS
        defaultStatisticsShouldNotBeFound("latestpayments.doesNotContain=" + DEFAULT_LATESTPAYMENTS);

        // Get all the statisticsList where latestpayments does not contain UPDATED_LATESTPAYMENTS
        defaultStatisticsShouldBeFound("latestpayments.doesNotContain=" + UPDATED_LATESTPAYMENTS);
    }

    @Test
    @Transactional
    void getAllStatisticsByLtstsupplierpaymIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where ltstsupplierpaym equals to DEFAULT_LTSTSUPPLIERPAYM
        defaultStatisticsShouldBeFound("ltstsupplierpaym.equals=" + DEFAULT_LTSTSUPPLIERPAYM);

        // Get all the statisticsList where ltstsupplierpaym equals to UPDATED_LTSTSUPPLIERPAYM
        defaultStatisticsShouldNotBeFound("ltstsupplierpaym.equals=" + UPDATED_LTSTSUPPLIERPAYM);
    }

    @Test
    @Transactional
    void getAllStatisticsByLtstsupplierpaymIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where ltstsupplierpaym in DEFAULT_LTSTSUPPLIERPAYM or UPDATED_LTSTSUPPLIERPAYM
        defaultStatisticsShouldBeFound("ltstsupplierpaym.in=" + DEFAULT_LTSTSUPPLIERPAYM + "," + UPDATED_LTSTSUPPLIERPAYM);

        // Get all the statisticsList where ltstsupplierpaym equals to UPDATED_LTSTSUPPLIERPAYM
        defaultStatisticsShouldNotBeFound("ltstsupplierpaym.in=" + UPDATED_LTSTSUPPLIERPAYM);
    }

    @Test
    @Transactional
    void getAllStatisticsByLtstsupplierpaymIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where ltstsupplierpaym is not null
        defaultStatisticsShouldBeFound("ltstsupplierpaym.specified=true");

        // Get all the statisticsList where ltstsupplierpaym is null
        defaultStatisticsShouldNotBeFound("ltstsupplierpaym.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByLtstsupplierpaymContainsSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where ltstsupplierpaym contains DEFAULT_LTSTSUPPLIERPAYM
        defaultStatisticsShouldBeFound("ltstsupplierpaym.contains=" + DEFAULT_LTSTSUPPLIERPAYM);

        // Get all the statisticsList where ltstsupplierpaym contains UPDATED_LTSTSUPPLIERPAYM
        defaultStatisticsShouldNotBeFound("ltstsupplierpaym.contains=" + UPDATED_LTSTSUPPLIERPAYM);
    }

    @Test
    @Transactional
    void getAllStatisticsByLtstsupplierpaymNotContainsSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where ltstsupplierpaym does not contain DEFAULT_LTSTSUPPLIERPAYM
        defaultStatisticsShouldNotBeFound("ltstsupplierpaym.doesNotContain=" + DEFAULT_LTSTSUPPLIERPAYM);

        // Get all the statisticsList where ltstsupplierpaym does not contain UPDATED_LTSTSUPPLIERPAYM
        defaultStatisticsShouldBeFound("ltstsupplierpaym.doesNotContain=" + UPDATED_LTSTSUPPLIERPAYM);
    }

    @Test
    @Transactional
    void getAllStatisticsByRecentrlycompltdIsEqualToSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where recentrlycompltd equals to DEFAULT_RECENTRLYCOMPLTD
        defaultStatisticsShouldBeFound("recentrlycompltd.equals=" + DEFAULT_RECENTRLYCOMPLTD);

        // Get all the statisticsList where recentrlycompltd equals to UPDATED_RECENTRLYCOMPLTD
        defaultStatisticsShouldNotBeFound("recentrlycompltd.equals=" + UPDATED_RECENTRLYCOMPLTD);
    }

    @Test
    @Transactional
    void getAllStatisticsByRecentrlycompltdIsInShouldWork() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where recentrlycompltd in DEFAULT_RECENTRLYCOMPLTD or UPDATED_RECENTRLYCOMPLTD
        defaultStatisticsShouldBeFound("recentrlycompltd.in=" + DEFAULT_RECENTRLYCOMPLTD + "," + UPDATED_RECENTRLYCOMPLTD);

        // Get all the statisticsList where recentrlycompltd equals to UPDATED_RECENTRLYCOMPLTD
        defaultStatisticsShouldNotBeFound("recentrlycompltd.in=" + UPDATED_RECENTRLYCOMPLTD);
    }

    @Test
    @Transactional
    void getAllStatisticsByRecentrlycompltdIsNullOrNotNull() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where recentrlycompltd is not null
        defaultStatisticsShouldBeFound("recentrlycompltd.specified=true");

        // Get all the statisticsList where recentrlycompltd is null
        defaultStatisticsShouldNotBeFound("recentrlycompltd.specified=false");
    }

    @Test
    @Transactional
    void getAllStatisticsByRecentrlycompltdContainsSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where recentrlycompltd contains DEFAULT_RECENTRLYCOMPLTD
        defaultStatisticsShouldBeFound("recentrlycompltd.contains=" + DEFAULT_RECENTRLYCOMPLTD);

        // Get all the statisticsList where recentrlycompltd contains UPDATED_RECENTRLYCOMPLTD
        defaultStatisticsShouldNotBeFound("recentrlycompltd.contains=" + UPDATED_RECENTRLYCOMPLTD);
    }

    @Test
    @Transactional
    void getAllStatisticsByRecentrlycompltdNotContainsSomething() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        // Get all the statisticsList where recentrlycompltd does not contain DEFAULT_RECENTRLYCOMPLTD
        defaultStatisticsShouldNotBeFound("recentrlycompltd.doesNotContain=" + DEFAULT_RECENTRLYCOMPLTD);

        // Get all the statisticsList where recentrlycompltd does not contain UPDATED_RECENTRLYCOMPLTD
        defaultStatisticsShouldBeFound("recentrlycompltd.doesNotContain=" + UPDATED_RECENTRLYCOMPLTD);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatisticsShouldBeFound(String filter) throws Exception {
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].todaysales").value(hasItem(sameNumber(DEFAULT_TODAYSALES))))
            .andExpect(jsonPath("$.[*].totalCustomersNumb").value(hasItem(DEFAULT_TOTAL_CUSTOMERS_NUMB)))
            .andExpect(jsonPath("$.[*].custmerTotal").value(hasItem(sameNumber(DEFAULT_CUSTMER_TOTAL))))
            .andExpect(jsonPath("$.[*].schedTotalNexWeek").value(hasItem(DEFAULT_SCHED_TOTAL_NEX_WEEK)))
            .andExpect(jsonPath("$.[*].totalCarts").value(hasItem(DEFAULT_TOTAL_CARTS)))
            .andExpect(jsonPath("$.[*].totalPending").value(hasItem(DEFAULT_TOTAL_PENDING)))
            .andExpect(jsonPath("$.[*].totalPayments").value(hasItem(sameNumber(DEFAULT_TOTAL_PAYMENTS))))
            .andExpect(jsonPath("$.[*].pendingPayments").value(hasItem(sameNumber(DEFAULT_PENDING_PAYMENTS))))
            .andExpect(jsonPath("$.[*].pendingNumberPayments").value(hasItem(DEFAULT_PENDING_NUMBER_PAYMENTS)))
            .andExpect(jsonPath("$.[*].totalCars").value(hasItem(DEFAULT_TOTAL_CARS)))
            .andExpect(jsonPath("$.[*].totalTrucks").value(hasItem(DEFAULT_TOTAL_TRUCKS)))
            .andExpect(jsonPath("$.[*].totalOther1").value(hasItem(DEFAULT_TOTAL_OTHER_1)))
            .andExpect(jsonPath("$.[*].totalOther2").value(hasItem(DEFAULT_TOTAL_OTHER_2)))
            .andExpect(jsonPath("$.[*].latestpayments").value(hasItem(DEFAULT_LATESTPAYMENTS)))
            .andExpect(jsonPath("$.[*].ltstsupplierpaym").value(hasItem(DEFAULT_LTSTSUPPLIERPAYM)))
            .andExpect(jsonPath("$.[*].recentrlycompltd").value(hasItem(DEFAULT_RECENTRLYCOMPLTD)));

        // Check, that the count call also returns 1
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatisticsShouldNotBeFound(String filter) throws Exception {
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStatistics() throws Exception {
        // Get the statistics
        restStatisticsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStatistics() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();

        // Update the statistics
        Statistics updatedStatistics = statisticsRepository.findById(statistics.getId()).get();
        // Disconnect from session so that the updates on updatedStatistics are not directly saved in db
        em.detach(updatedStatistics);
        updatedStatistics
            .todaysales(UPDATED_TODAYSALES)
            .totalCustomersNumb(UPDATED_TOTAL_CUSTOMERS_NUMB)
            .custmerTotal(UPDATED_CUSTMER_TOTAL)
            .schedTotalNexWeek(UPDATED_SCHED_TOTAL_NEX_WEEK)
            .totalCarts(UPDATED_TOTAL_CARTS)
            .totalPending(UPDATED_TOTAL_PENDING)
            .totalPayments(UPDATED_TOTAL_PAYMENTS)
            .pendingPayments(UPDATED_PENDING_PAYMENTS)
            .pendingNumberPayments(UPDATED_PENDING_NUMBER_PAYMENTS)
            .totalCars(UPDATED_TOTAL_CARS)
            .totalTrucks(UPDATED_TOTAL_TRUCKS)
            .totalOther1(UPDATED_TOTAL_OTHER_1)
            .totalOther2(UPDATED_TOTAL_OTHER_2)
            .latestpayments(UPDATED_LATESTPAYMENTS)
            .ltstsupplierpaym(UPDATED_LTSTSUPPLIERPAYM)
            .recentrlycompltd(UPDATED_RECENTRLYCOMPLTD);

        restStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStatistics.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStatistics))
            )
            .andExpect(status().isOk());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
        Statistics testStatistics = statisticsList.get(statisticsList.size() - 1);
        assertThat(testStatistics.getTodaysales()).isEqualByComparingTo(UPDATED_TODAYSALES);
        assertThat(testStatistics.getTotalCustomersNumb()).isEqualTo(UPDATED_TOTAL_CUSTOMERS_NUMB);
        assertThat(testStatistics.getCustmerTotal()).isEqualByComparingTo(UPDATED_CUSTMER_TOTAL);
        assertThat(testStatistics.getSchedTotalNexWeek()).isEqualTo(UPDATED_SCHED_TOTAL_NEX_WEEK);
        assertThat(testStatistics.getTotalCarts()).isEqualTo(UPDATED_TOTAL_CARTS);
        assertThat(testStatistics.getTotalPending()).isEqualTo(UPDATED_TOTAL_PENDING);
        assertThat(testStatistics.getTotalPayments()).isEqualByComparingTo(UPDATED_TOTAL_PAYMENTS);
        assertThat(testStatistics.getPendingPayments()).isEqualByComparingTo(UPDATED_PENDING_PAYMENTS);
        assertThat(testStatistics.getPendingNumberPayments()).isEqualTo(UPDATED_PENDING_NUMBER_PAYMENTS);
        assertThat(testStatistics.getTotalCars()).isEqualTo(UPDATED_TOTAL_CARS);
        assertThat(testStatistics.getTotalTrucks()).isEqualTo(UPDATED_TOTAL_TRUCKS);
        assertThat(testStatistics.getTotalOther1()).isEqualTo(UPDATED_TOTAL_OTHER_1);
        assertThat(testStatistics.getTotalOther2()).isEqualTo(UPDATED_TOTAL_OTHER_2);
        assertThat(testStatistics.getLatestpayments()).isEqualTo(UPDATED_LATESTPAYMENTS);
        assertThat(testStatistics.getLtstsupplierpaym()).isEqualTo(UPDATED_LTSTSUPPLIERPAYM);
        assertThat(testStatistics.getRecentrlycompltd()).isEqualTo(UPDATED_RECENTRLYCOMPLTD);
    }

    @Test
    @Transactional
    void putNonExistingStatistics() throws Exception {
        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();
        statistics.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statistics.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statistics))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatistics() throws Exception {
        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();
        statistics.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statistics))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatistics() throws Exception {
        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();
        statistics.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statistics)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatisticsWithPatch() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();

        // Update the statistics using partial update
        Statistics partialUpdatedStatistics = new Statistics();
        partialUpdatedStatistics.setId(statistics.getId());

        partialUpdatedStatistics
            .custmerTotal(UPDATED_CUSTMER_TOTAL)
            .schedTotalNexWeek(UPDATED_SCHED_TOTAL_NEX_WEEK)
            .totalCarts(UPDATED_TOTAL_CARTS)
            .totalPending(UPDATED_TOTAL_PENDING)
            .pendingNumberPayments(UPDATED_PENDING_NUMBER_PAYMENTS)
            .totalOther2(UPDATED_TOTAL_OTHER_2)
            .ltstsupplierpaym(UPDATED_LTSTSUPPLIERPAYM)
            .recentrlycompltd(UPDATED_RECENTRLYCOMPLTD);

        restStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatistics))
            )
            .andExpect(status().isOk());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
        Statistics testStatistics = statisticsList.get(statisticsList.size() - 1);
        assertThat(testStatistics.getTodaysales()).isEqualByComparingTo(DEFAULT_TODAYSALES);
        assertThat(testStatistics.getTotalCustomersNumb()).isEqualTo(DEFAULT_TOTAL_CUSTOMERS_NUMB);
        assertThat(testStatistics.getCustmerTotal()).isEqualByComparingTo(UPDATED_CUSTMER_TOTAL);
        assertThat(testStatistics.getSchedTotalNexWeek()).isEqualTo(UPDATED_SCHED_TOTAL_NEX_WEEK);
        assertThat(testStatistics.getTotalCarts()).isEqualTo(UPDATED_TOTAL_CARTS);
        assertThat(testStatistics.getTotalPending()).isEqualTo(UPDATED_TOTAL_PENDING);
        assertThat(testStatistics.getTotalPayments()).isEqualByComparingTo(DEFAULT_TOTAL_PAYMENTS);
        assertThat(testStatistics.getPendingPayments()).isEqualByComparingTo(DEFAULT_PENDING_PAYMENTS);
        assertThat(testStatistics.getPendingNumberPayments()).isEqualTo(UPDATED_PENDING_NUMBER_PAYMENTS);
        assertThat(testStatistics.getTotalCars()).isEqualTo(DEFAULT_TOTAL_CARS);
        assertThat(testStatistics.getTotalTrucks()).isEqualTo(DEFAULT_TOTAL_TRUCKS);
        assertThat(testStatistics.getTotalOther1()).isEqualTo(DEFAULT_TOTAL_OTHER_1);
        assertThat(testStatistics.getTotalOther2()).isEqualTo(UPDATED_TOTAL_OTHER_2);
        assertThat(testStatistics.getLatestpayments()).isEqualTo(DEFAULT_LATESTPAYMENTS);
        assertThat(testStatistics.getLtstsupplierpaym()).isEqualTo(UPDATED_LTSTSUPPLIERPAYM);
        assertThat(testStatistics.getRecentrlycompltd()).isEqualTo(UPDATED_RECENTRLYCOMPLTD);
    }

    @Test
    @Transactional
    void fullUpdateStatisticsWithPatch() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();

        // Update the statistics using partial update
        Statistics partialUpdatedStatistics = new Statistics();
        partialUpdatedStatistics.setId(statistics.getId());

        partialUpdatedStatistics
            .todaysales(UPDATED_TODAYSALES)
            .totalCustomersNumb(UPDATED_TOTAL_CUSTOMERS_NUMB)
            .custmerTotal(UPDATED_CUSTMER_TOTAL)
            .schedTotalNexWeek(UPDATED_SCHED_TOTAL_NEX_WEEK)
            .totalCarts(UPDATED_TOTAL_CARTS)
            .totalPending(UPDATED_TOTAL_PENDING)
            .totalPayments(UPDATED_TOTAL_PAYMENTS)
            .pendingPayments(UPDATED_PENDING_PAYMENTS)
            .pendingNumberPayments(UPDATED_PENDING_NUMBER_PAYMENTS)
            .totalCars(UPDATED_TOTAL_CARS)
            .totalTrucks(UPDATED_TOTAL_TRUCKS)
            .totalOther1(UPDATED_TOTAL_OTHER_1)
            .totalOther2(UPDATED_TOTAL_OTHER_2)
            .latestpayments(UPDATED_LATESTPAYMENTS)
            .ltstsupplierpaym(UPDATED_LTSTSUPPLIERPAYM)
            .recentrlycompltd(UPDATED_RECENTRLYCOMPLTD);

        restStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatistics))
            )
            .andExpect(status().isOk());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
        Statistics testStatistics = statisticsList.get(statisticsList.size() - 1);
        assertThat(testStatistics.getTodaysales()).isEqualByComparingTo(UPDATED_TODAYSALES);
        assertThat(testStatistics.getTotalCustomersNumb()).isEqualTo(UPDATED_TOTAL_CUSTOMERS_NUMB);
        assertThat(testStatistics.getCustmerTotal()).isEqualByComparingTo(UPDATED_CUSTMER_TOTAL);
        assertThat(testStatistics.getSchedTotalNexWeek()).isEqualTo(UPDATED_SCHED_TOTAL_NEX_WEEK);
        assertThat(testStatistics.getTotalCarts()).isEqualTo(UPDATED_TOTAL_CARTS);
        assertThat(testStatistics.getTotalPending()).isEqualTo(UPDATED_TOTAL_PENDING);
        assertThat(testStatistics.getTotalPayments()).isEqualByComparingTo(UPDATED_TOTAL_PAYMENTS);
        assertThat(testStatistics.getPendingPayments()).isEqualByComparingTo(UPDATED_PENDING_PAYMENTS);
        assertThat(testStatistics.getPendingNumberPayments()).isEqualTo(UPDATED_PENDING_NUMBER_PAYMENTS);
        assertThat(testStatistics.getTotalCars()).isEqualTo(UPDATED_TOTAL_CARS);
        assertThat(testStatistics.getTotalTrucks()).isEqualTo(UPDATED_TOTAL_TRUCKS);
        assertThat(testStatistics.getTotalOther1()).isEqualTo(UPDATED_TOTAL_OTHER_1);
        assertThat(testStatistics.getTotalOther2()).isEqualTo(UPDATED_TOTAL_OTHER_2);
        assertThat(testStatistics.getLatestpayments()).isEqualTo(UPDATED_LATESTPAYMENTS);
        assertThat(testStatistics.getLtstsupplierpaym()).isEqualTo(UPDATED_LTSTSUPPLIERPAYM);
        assertThat(testStatistics.getRecentrlycompltd()).isEqualTo(UPDATED_RECENTRLYCOMPLTD);
    }

    @Test
    @Transactional
    void patchNonExistingStatistics() throws Exception {
        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();
        statistics.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statistics))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatistics() throws Exception {
        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();
        statistics.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statistics))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatistics() throws Exception {
        int databaseSizeBeforeUpdate = statisticsRepository.findAll().size();
        statistics.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(statistics))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statistics in the database
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatistics() throws Exception {
        // Initialize the database
        statisticsRepository.saveAndFlush(statistics);

        int databaseSizeBeforeDelete = statisticsRepository.findAll().size();

        // Delete the statistics
        restStatisticsMockMvc
            .perform(delete(ENTITY_API_URL_ID, statistics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Statistics> statisticsList = statisticsRepository.findAll();
        assertThat(statisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
