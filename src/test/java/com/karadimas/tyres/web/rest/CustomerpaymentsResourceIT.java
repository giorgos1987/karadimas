package com.karadimas.tyres.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Customerpayments;
import com.karadimas.tyres.domain.Customers;
import com.karadimas.tyres.domain.enumeration.Paystatus;
import com.karadimas.tyres.repository.CustomerpaymentsRepository;
import com.karadimas.tyres.service.CustomerpaymentsService;
import com.karadimas.tyres.service.criteria.CustomerpaymentsCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CustomerpaymentsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CustomerpaymentsResourceIT {

    private static final String DEFAULT_TOTAL_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_REMAINDER = "AAAAAAAAAA";
    private static final String UPDATED_REMAINDER = "BBBBBBBBBB";

    private static final String DEFAULT_DOWN_PAYMENT = "AAAAAAAAAA";
    private static final String UPDATED_DOWN_PAYMENT = "BBBBBBBBBB";

    private static final Paystatus DEFAULT_ISPAID = Paystatus.YES;
    private static final Paystatus UPDATED_ISPAID = Paystatus.NO;

    private static final Instant DEFAULT_INVOICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENTS_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_LASTUPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LASTUPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/customerpayments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerpaymentsRepository customerpaymentsRepository;

    @Mock
    private CustomerpaymentsRepository customerpaymentsRepositoryMock;

    @Mock
    private CustomerpaymentsService customerpaymentsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerpaymentsMockMvc;

    private Customerpayments customerpayments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customerpayments createEntity(EntityManager em) {
        Customerpayments customerpayments = new Customerpayments()
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .remainder(DEFAULT_REMAINDER)
            .downPayment(DEFAULT_DOWN_PAYMENT)
            .ispaid(DEFAULT_ISPAID)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .notes(DEFAULT_NOTES)
            .attachments(DEFAULT_ATTACHMENTS)
            .attachmentsContentType(DEFAULT_ATTACHMENTS_CONTENT_TYPE)
            .lastupdated(DEFAULT_LASTUPDATED);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        customerpayments.setCustomers(customers);
        return customerpayments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customerpayments createUpdatedEntity(EntityManager em) {
        Customerpayments customerpayments = new Customerpayments()
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .remainder(UPDATED_REMAINDER)
            .downPayment(UPDATED_DOWN_PAYMENT)
            .ispaid(UPDATED_ISPAID)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .lastupdated(UPDATED_LASTUPDATED);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createUpdatedEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        customerpayments.setCustomers(customers);
        return customerpayments;
    }

    @BeforeEach
    public void initTest() {
        customerpayments = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomerpayments() throws Exception {
        int databaseSizeBeforeCreate = customerpaymentsRepository.findAll().size();
        // Create the Customerpayments
        restCustomerpaymentsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerpayments))
            )
            .andExpect(status().isCreated());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeCreate + 1);
        Customerpayments testCustomerpayments = customerpaymentsList.get(customerpaymentsList.size() - 1);
        assertThat(testCustomerpayments.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testCustomerpayments.getRemainder()).isEqualTo(DEFAULT_REMAINDER);
        assertThat(testCustomerpayments.getDownPayment()).isEqualTo(DEFAULT_DOWN_PAYMENT);
        assertThat(testCustomerpayments.getIspaid()).isEqualTo(DEFAULT_ISPAID);
        assertThat(testCustomerpayments.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testCustomerpayments.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCustomerpayments.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCustomerpayments.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testCustomerpayments.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testCustomerpayments.getLastupdated()).isEqualTo(DEFAULT_LASTUPDATED);
    }

    @Test
    @Transactional
    void createCustomerpaymentsWithExistingId() throws Exception {
        // Create the Customerpayments with an existing ID
        customerpayments.setId(1L);

        int databaseSizeBeforeCreate = customerpaymentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerpaymentsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCustomerpayments() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList
        restCustomerpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerpayments.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.[*].remainder").value(hasItem(DEFAULT_REMAINDER)))
            .andExpect(jsonPath("$.[*].downPayment").value(hasItem(DEFAULT_DOWN_PAYMENT)))
            .andExpect(jsonPath("$.[*].ispaid").value(hasItem(DEFAULT_ISPAID.toString())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))))
            .andExpect(jsonPath("$.[*].lastupdated").value(hasItem(DEFAULT_LASTUPDATED.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCustomerpaymentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(customerpaymentsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCustomerpaymentsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(customerpaymentsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCustomerpaymentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(customerpaymentsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCustomerpaymentsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(customerpaymentsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCustomerpayments() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get the customerpayments
        restCustomerpaymentsMockMvc
            .perform(get(ENTITY_API_URL_ID, customerpayments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerpayments.getId().intValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT))
            .andExpect(jsonPath("$.remainder").value(DEFAULT_REMAINDER))
            .andExpect(jsonPath("$.downPayment").value(DEFAULT_DOWN_PAYMENT))
            .andExpect(jsonPath("$.ispaid").value(DEFAULT_ISPAID.toString()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.attachmentsContentType").value(DEFAULT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachments").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS)))
            .andExpect(jsonPath("$.lastupdated").value(DEFAULT_LASTUPDATED.toString()));
    }

    @Test
    @Transactional
    void getCustomerpaymentsByIdFiltering() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        Long id = customerpayments.getId();

        defaultCustomerpaymentsShouldBeFound("id.equals=" + id);
        defaultCustomerpaymentsShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerpaymentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerpaymentsShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerpaymentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerpaymentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultCustomerpaymentsShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the customerpaymentsList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultCustomerpaymentsShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultCustomerpaymentsShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the customerpaymentsList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultCustomerpaymentsShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where totalAmount is not null
        defaultCustomerpaymentsShouldBeFound("totalAmount.specified=true");

        // Get all the customerpaymentsList where totalAmount is null
        defaultCustomerpaymentsShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByTotalAmountContainsSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where totalAmount contains DEFAULT_TOTAL_AMOUNT
        defaultCustomerpaymentsShouldBeFound("totalAmount.contains=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the customerpaymentsList where totalAmount contains UPDATED_TOTAL_AMOUNT
        defaultCustomerpaymentsShouldNotBeFound("totalAmount.contains=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByTotalAmountNotContainsSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where totalAmount does not contain DEFAULT_TOTAL_AMOUNT
        defaultCustomerpaymentsShouldNotBeFound("totalAmount.doesNotContain=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the customerpaymentsList where totalAmount does not contain UPDATED_TOTAL_AMOUNT
        defaultCustomerpaymentsShouldBeFound("totalAmount.doesNotContain=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByRemainderIsEqualToSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where remainder equals to DEFAULT_REMAINDER
        defaultCustomerpaymentsShouldBeFound("remainder.equals=" + DEFAULT_REMAINDER);

        // Get all the customerpaymentsList where remainder equals to UPDATED_REMAINDER
        defaultCustomerpaymentsShouldNotBeFound("remainder.equals=" + UPDATED_REMAINDER);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByRemainderIsInShouldWork() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where remainder in DEFAULT_REMAINDER or UPDATED_REMAINDER
        defaultCustomerpaymentsShouldBeFound("remainder.in=" + DEFAULT_REMAINDER + "," + UPDATED_REMAINDER);

        // Get all the customerpaymentsList where remainder equals to UPDATED_REMAINDER
        defaultCustomerpaymentsShouldNotBeFound("remainder.in=" + UPDATED_REMAINDER);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByRemainderIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where remainder is not null
        defaultCustomerpaymentsShouldBeFound("remainder.specified=true");

        // Get all the customerpaymentsList where remainder is null
        defaultCustomerpaymentsShouldNotBeFound("remainder.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByRemainderContainsSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where remainder contains DEFAULT_REMAINDER
        defaultCustomerpaymentsShouldBeFound("remainder.contains=" + DEFAULT_REMAINDER);

        // Get all the customerpaymentsList where remainder contains UPDATED_REMAINDER
        defaultCustomerpaymentsShouldNotBeFound("remainder.contains=" + UPDATED_REMAINDER);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByRemainderNotContainsSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where remainder does not contain DEFAULT_REMAINDER
        defaultCustomerpaymentsShouldNotBeFound("remainder.doesNotContain=" + DEFAULT_REMAINDER);

        // Get all the customerpaymentsList where remainder does not contain UPDATED_REMAINDER
        defaultCustomerpaymentsShouldBeFound("remainder.doesNotContain=" + UPDATED_REMAINDER);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByDownPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where downPayment equals to DEFAULT_DOWN_PAYMENT
        defaultCustomerpaymentsShouldBeFound("downPayment.equals=" + DEFAULT_DOWN_PAYMENT);

        // Get all the customerpaymentsList where downPayment equals to UPDATED_DOWN_PAYMENT
        defaultCustomerpaymentsShouldNotBeFound("downPayment.equals=" + UPDATED_DOWN_PAYMENT);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByDownPaymentIsInShouldWork() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where downPayment in DEFAULT_DOWN_PAYMENT or UPDATED_DOWN_PAYMENT
        defaultCustomerpaymentsShouldBeFound("downPayment.in=" + DEFAULT_DOWN_PAYMENT + "," + UPDATED_DOWN_PAYMENT);

        // Get all the customerpaymentsList where downPayment equals to UPDATED_DOWN_PAYMENT
        defaultCustomerpaymentsShouldNotBeFound("downPayment.in=" + UPDATED_DOWN_PAYMENT);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByDownPaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where downPayment is not null
        defaultCustomerpaymentsShouldBeFound("downPayment.specified=true");

        // Get all the customerpaymentsList where downPayment is null
        defaultCustomerpaymentsShouldNotBeFound("downPayment.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByDownPaymentContainsSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where downPayment contains DEFAULT_DOWN_PAYMENT
        defaultCustomerpaymentsShouldBeFound("downPayment.contains=" + DEFAULT_DOWN_PAYMENT);

        // Get all the customerpaymentsList where downPayment contains UPDATED_DOWN_PAYMENT
        defaultCustomerpaymentsShouldNotBeFound("downPayment.contains=" + UPDATED_DOWN_PAYMENT);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByDownPaymentNotContainsSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where downPayment does not contain DEFAULT_DOWN_PAYMENT
        defaultCustomerpaymentsShouldNotBeFound("downPayment.doesNotContain=" + DEFAULT_DOWN_PAYMENT);

        // Get all the customerpaymentsList where downPayment does not contain UPDATED_DOWN_PAYMENT
        defaultCustomerpaymentsShouldBeFound("downPayment.doesNotContain=" + UPDATED_DOWN_PAYMENT);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByIspaidIsEqualToSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where ispaid equals to DEFAULT_ISPAID
        defaultCustomerpaymentsShouldBeFound("ispaid.equals=" + DEFAULT_ISPAID);

        // Get all the customerpaymentsList where ispaid equals to UPDATED_ISPAID
        defaultCustomerpaymentsShouldNotBeFound("ispaid.equals=" + UPDATED_ISPAID);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByIspaidIsInShouldWork() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where ispaid in DEFAULT_ISPAID or UPDATED_ISPAID
        defaultCustomerpaymentsShouldBeFound("ispaid.in=" + DEFAULT_ISPAID + "," + UPDATED_ISPAID);

        // Get all the customerpaymentsList where ispaid equals to UPDATED_ISPAID
        defaultCustomerpaymentsShouldNotBeFound("ispaid.in=" + UPDATED_ISPAID);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByIspaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where ispaid is not null
        defaultCustomerpaymentsShouldBeFound("ispaid.specified=true");

        // Get all the customerpaymentsList where ispaid is null
        defaultCustomerpaymentsShouldNotBeFound("ispaid.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultCustomerpaymentsShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the customerpaymentsList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultCustomerpaymentsShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultCustomerpaymentsShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the customerpaymentsList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultCustomerpaymentsShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where invoiceDate is not null
        defaultCustomerpaymentsShouldBeFound("invoiceDate.specified=true");

        // Get all the customerpaymentsList where invoiceDate is null
        defaultCustomerpaymentsShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where dueDate equals to DEFAULT_DUE_DATE
        defaultCustomerpaymentsShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the customerpaymentsList where dueDate equals to UPDATED_DUE_DATE
        defaultCustomerpaymentsShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultCustomerpaymentsShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the customerpaymentsList where dueDate equals to UPDATED_DUE_DATE
        defaultCustomerpaymentsShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where dueDate is not null
        defaultCustomerpaymentsShouldBeFound("dueDate.specified=true");

        // Get all the customerpaymentsList where dueDate is null
        defaultCustomerpaymentsShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByLastupdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where lastupdated equals to DEFAULT_LASTUPDATED
        defaultCustomerpaymentsShouldBeFound("lastupdated.equals=" + DEFAULT_LASTUPDATED);

        // Get all the customerpaymentsList where lastupdated equals to UPDATED_LASTUPDATED
        defaultCustomerpaymentsShouldNotBeFound("lastupdated.equals=" + UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByLastupdatedIsInShouldWork() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where lastupdated in DEFAULT_LASTUPDATED or UPDATED_LASTUPDATED
        defaultCustomerpaymentsShouldBeFound("lastupdated.in=" + DEFAULT_LASTUPDATED + "," + UPDATED_LASTUPDATED);

        // Get all the customerpaymentsList where lastupdated equals to UPDATED_LASTUPDATED
        defaultCustomerpaymentsShouldNotBeFound("lastupdated.in=" + UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByLastupdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        // Get all the customerpaymentsList where lastupdated is not null
        defaultCustomerpaymentsShouldBeFound("lastupdated.specified=true");

        // Get all the customerpaymentsList where lastupdated is null
        defaultCustomerpaymentsShouldNotBeFound("lastupdated.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomerpaymentsByCustomersIsEqualToSomething() throws Exception {
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customerpaymentsRepository.saveAndFlush(customerpayments);
            customers = CustomersResourceIT.createEntity(em);
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        em.persist(customers);
        em.flush();
        customerpayments.setCustomers(customers);
        customerpaymentsRepository.saveAndFlush(customerpayments);
        Long customersId = customers.getId();

        // Get all the customerpaymentsList where customers equals to customersId
        defaultCustomerpaymentsShouldBeFound("customersId.equals=" + customersId);

        // Get all the customerpaymentsList where customers equals to (customersId + 1)
        defaultCustomerpaymentsShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerpaymentsShouldBeFound(String filter) throws Exception {
        restCustomerpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerpayments.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.[*].remainder").value(hasItem(DEFAULT_REMAINDER)))
            .andExpect(jsonPath("$.[*].downPayment").value(hasItem(DEFAULT_DOWN_PAYMENT)))
            .andExpect(jsonPath("$.[*].ispaid").value(hasItem(DEFAULT_ISPAID.toString())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))))
            .andExpect(jsonPath("$.[*].lastupdated").value(hasItem(DEFAULT_LASTUPDATED.toString())));

        // Check, that the count call also returns 1
        restCustomerpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerpaymentsShouldNotBeFound(String filter) throws Exception {
        restCustomerpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomerpayments() throws Exception {
        // Get the customerpayments
        restCustomerpaymentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomerpayments() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        int databaseSizeBeforeUpdate = customerpaymentsRepository.findAll().size();

        // Update the customerpayments
        Customerpayments updatedCustomerpayments = customerpaymentsRepository.findById(customerpayments.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerpayments are not directly saved in db
        em.detach(updatedCustomerpayments);
        updatedCustomerpayments
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .remainder(UPDATED_REMAINDER)
            .downPayment(UPDATED_DOWN_PAYMENT)
            .ispaid(UPDATED_ISPAID)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .lastupdated(UPDATED_LASTUPDATED);

        restCustomerpaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomerpayments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomerpayments))
            )
            .andExpect(status().isOk());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeUpdate);
        Customerpayments testCustomerpayments = customerpaymentsList.get(customerpaymentsList.size() - 1);
        assertThat(testCustomerpayments.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testCustomerpayments.getRemainder()).isEqualTo(UPDATED_REMAINDER);
        assertThat(testCustomerpayments.getDownPayment()).isEqualTo(UPDATED_DOWN_PAYMENT);
        assertThat(testCustomerpayments.getIspaid()).isEqualTo(UPDATED_ISPAID);
        assertThat(testCustomerpayments.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testCustomerpayments.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCustomerpayments.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomerpayments.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testCustomerpayments.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testCustomerpayments.getLastupdated()).isEqualTo(UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void putNonExistingCustomerpayments() throws Exception {
        int databaseSizeBeforeUpdate = customerpaymentsRepository.findAll().size();
        customerpayments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerpaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerpayments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerpayments() throws Exception {
        int databaseSizeBeforeUpdate = customerpaymentsRepository.findAll().size();
        customerpayments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerpaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerpayments() throws Exception {
        int databaseSizeBeforeUpdate = customerpaymentsRepository.findAll().size();
        customerpayments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerpaymentsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerpayments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerpaymentsWithPatch() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        int databaseSizeBeforeUpdate = customerpaymentsRepository.findAll().size();

        // Update the customerpayments using partial update
        Customerpayments partialUpdatedCustomerpayments = new Customerpayments();
        partialUpdatedCustomerpayments.setId(customerpayments.getId());

        partialUpdatedCustomerpayments
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .remainder(UPDATED_REMAINDER)
            .ispaid(UPDATED_ISPAID)
            .notes(UPDATED_NOTES)
            .lastupdated(UPDATED_LASTUPDATED);

        restCustomerpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerpayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerpayments))
            )
            .andExpect(status().isOk());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeUpdate);
        Customerpayments testCustomerpayments = customerpaymentsList.get(customerpaymentsList.size() - 1);
        assertThat(testCustomerpayments.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testCustomerpayments.getRemainder()).isEqualTo(UPDATED_REMAINDER);
        assertThat(testCustomerpayments.getDownPayment()).isEqualTo(DEFAULT_DOWN_PAYMENT);
        assertThat(testCustomerpayments.getIspaid()).isEqualTo(UPDATED_ISPAID);
        assertThat(testCustomerpayments.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testCustomerpayments.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCustomerpayments.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomerpayments.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testCustomerpayments.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testCustomerpayments.getLastupdated()).isEqualTo(UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void fullUpdateCustomerpaymentsWithPatch() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        int databaseSizeBeforeUpdate = customerpaymentsRepository.findAll().size();

        // Update the customerpayments using partial update
        Customerpayments partialUpdatedCustomerpayments = new Customerpayments();
        partialUpdatedCustomerpayments.setId(customerpayments.getId());

        partialUpdatedCustomerpayments
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .remainder(UPDATED_REMAINDER)
            .downPayment(UPDATED_DOWN_PAYMENT)
            .ispaid(UPDATED_ISPAID)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .lastupdated(UPDATED_LASTUPDATED);

        restCustomerpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerpayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerpayments))
            )
            .andExpect(status().isOk());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeUpdate);
        Customerpayments testCustomerpayments = customerpaymentsList.get(customerpaymentsList.size() - 1);
        assertThat(testCustomerpayments.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testCustomerpayments.getRemainder()).isEqualTo(UPDATED_REMAINDER);
        assertThat(testCustomerpayments.getDownPayment()).isEqualTo(UPDATED_DOWN_PAYMENT);
        assertThat(testCustomerpayments.getIspaid()).isEqualTo(UPDATED_ISPAID);
        assertThat(testCustomerpayments.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testCustomerpayments.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCustomerpayments.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCustomerpayments.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testCustomerpayments.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testCustomerpayments.getLastupdated()).isEqualTo(UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingCustomerpayments() throws Exception {
        int databaseSizeBeforeUpdate = customerpaymentsRepository.findAll().size();
        customerpayments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerpayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerpayments() throws Exception {
        int databaseSizeBeforeUpdate = customerpaymentsRepository.findAll().size();
        customerpayments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerpayments() throws Exception {
        int databaseSizeBeforeUpdate = customerpaymentsRepository.findAll().size();
        customerpayments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerpayments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customerpayments in the database
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerpayments() throws Exception {
        // Initialize the database
        customerpaymentsRepository.saveAndFlush(customerpayments);

        int databaseSizeBeforeDelete = customerpaymentsRepository.findAll().size();

        // Delete the customerpayments
        restCustomerpaymentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerpayments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customerpayments> customerpaymentsList = customerpaymentsRepository.findAll();
        assertThat(customerpaymentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
