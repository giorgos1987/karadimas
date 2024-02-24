package com.karadimas.tyres.web.rest;

import static com.karadimas.tyres.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Supplier;
import com.karadimas.tyres.domain.Supplierpayments;
import com.karadimas.tyres.domain.enumeration.Paystatus;
import com.karadimas.tyres.repository.SupplierpaymentsRepository;
import com.karadimas.tyres.service.SupplierpaymentsService;
import com.karadimas.tyres.service.criteria.SupplierpaymentsCriteria;
import java.math.BigDecimal;
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
 * Integration tests for the {@link SupplierpaymentsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SupplierpaymentsResourceIT {

    private static final Instant DEFAULT_INVOICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Paystatus DEFAULT_ISPAID = Paystatus.YES;
    private static final Paystatus UPDATED_ISPAID = Paystatus.NO;

    private static final BigDecimal DEFAULT_AMOUNT_DUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_DUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_DUE = new BigDecimal(1 - 1);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENTS_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TAX = "AAAAAAAAAA";
    private static final String UPDATED_TAX = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING = "BBBBBBBBBB";

    private static final Instant DEFAULT_LASTUPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LASTUPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/supplierpayments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SupplierpaymentsRepository supplierpaymentsRepository;

    @Mock
    private SupplierpaymentsRepository supplierpaymentsRepositoryMock;

    @Mock
    private SupplierpaymentsService supplierpaymentsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierpaymentsMockMvc;

    private Supplierpayments supplierpayments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplierpayments createEntity(EntityManager em) {
        Supplierpayments supplierpayments = new Supplierpayments()
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .ispaid(DEFAULT_ISPAID)
            .amountDue(DEFAULT_AMOUNT_DUE)
            .notes(DEFAULT_NOTES)
            .attachments(DEFAULT_ATTACHMENTS)
            .attachmentsContentType(DEFAULT_ATTACHMENTS_CONTENT_TYPE)
            .tax(DEFAULT_TAX)
            .shipping(DEFAULT_SHIPPING)
            .lastupdated(DEFAULT_LASTUPDATED);
        // Add required entity
        Supplier supplier;
        if (TestUtil.findAll(em, Supplier.class).isEmpty()) {
            supplier = SupplierResourceIT.createEntity(em);
            em.persist(supplier);
            em.flush();
        } else {
            supplier = TestUtil.findAll(em, Supplier.class).get(0);
        }
        supplierpayments.setSupplier(supplier);
        return supplierpayments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplierpayments createUpdatedEntity(EntityManager em) {
        Supplierpayments supplierpayments = new Supplierpayments()
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .ispaid(UPDATED_ISPAID)
            .amountDue(UPDATED_AMOUNT_DUE)
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .tax(UPDATED_TAX)
            .shipping(UPDATED_SHIPPING)
            .lastupdated(UPDATED_LASTUPDATED);
        // Add required entity
        Supplier supplier;
        if (TestUtil.findAll(em, Supplier.class).isEmpty()) {
            supplier = SupplierResourceIT.createUpdatedEntity(em);
            em.persist(supplier);
            em.flush();
        } else {
            supplier = TestUtil.findAll(em, Supplier.class).get(0);
        }
        supplierpayments.setSupplier(supplier);
        return supplierpayments;
    }

    @BeforeEach
    public void initTest() {
        supplierpayments = createEntity(em);
    }

    @Test
    @Transactional
    void createSupplierpayments() throws Exception {
        int databaseSizeBeforeCreate = supplierpaymentsRepository.findAll().size();
        // Create the Supplierpayments
        restSupplierpaymentsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierpayments))
            )
            .andExpect(status().isCreated());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeCreate + 1);
        Supplierpayments testSupplierpayments = supplierpaymentsList.get(supplierpaymentsList.size() - 1);
        assertThat(testSupplierpayments.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testSupplierpayments.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testSupplierpayments.getIspaid()).isEqualTo(DEFAULT_ISPAID);
        assertThat(testSupplierpayments.getAmountDue()).isEqualByComparingTo(DEFAULT_AMOUNT_DUE);
        assertThat(testSupplierpayments.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testSupplierpayments.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testSupplierpayments.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testSupplierpayments.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testSupplierpayments.getShipping()).isEqualTo(DEFAULT_SHIPPING);
        assertThat(testSupplierpayments.getLastupdated()).isEqualTo(DEFAULT_LASTUPDATED);
    }

    @Test
    @Transactional
    void createSupplierpaymentsWithExistingId() throws Exception {
        // Create the Supplierpayments with an existing ID
        supplierpayments.setId(1L);

        int databaseSizeBeforeCreate = supplierpaymentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierpaymentsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSupplierpayments() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList
        restSupplierpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierpayments.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].ispaid").value(hasItem(DEFAULT_ISPAID.toString())))
            .andExpect(jsonPath("$.[*].amountDue").value(hasItem(sameNumber(DEFAULT_AMOUNT_DUE))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX)))
            .andExpect(jsonPath("$.[*].shipping").value(hasItem(DEFAULT_SHIPPING)))
            .andExpect(jsonPath("$.[*].lastupdated").value(hasItem(DEFAULT_LASTUPDATED.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSupplierpaymentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(supplierpaymentsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupplierpaymentsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(supplierpaymentsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSupplierpaymentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(supplierpaymentsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSupplierpaymentsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(supplierpaymentsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSupplierpayments() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get the supplierpayments
        restSupplierpaymentsMockMvc
            .perform(get(ENTITY_API_URL_ID, supplierpayments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplierpayments.getId().intValue()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.ispaid").value(DEFAULT_ISPAID.toString()))
            .andExpect(jsonPath("$.amountDue").value(sameNumber(DEFAULT_AMOUNT_DUE)))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.attachmentsContentType").value(DEFAULT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachments").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS)))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX))
            .andExpect(jsonPath("$.shipping").value(DEFAULT_SHIPPING))
            .andExpect(jsonPath("$.lastupdated").value(DEFAULT_LASTUPDATED.toString()));
    }

    @Test
    @Transactional
    void getSupplierpaymentsByIdFiltering() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        Long id = supplierpayments.getId();

        defaultSupplierpaymentsShouldBeFound("id.equals=" + id);
        defaultSupplierpaymentsShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierpaymentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierpaymentsShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierpaymentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierpaymentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultSupplierpaymentsShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the supplierpaymentsList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultSupplierpaymentsShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultSupplierpaymentsShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the supplierpaymentsList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultSupplierpaymentsShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where invoiceDate is not null
        defaultSupplierpaymentsShouldBeFound("invoiceDate.specified=true");

        // Get all the supplierpaymentsList where invoiceDate is null
        defaultSupplierpaymentsShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where dueDate equals to DEFAULT_DUE_DATE
        defaultSupplierpaymentsShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the supplierpaymentsList where dueDate equals to UPDATED_DUE_DATE
        defaultSupplierpaymentsShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultSupplierpaymentsShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the supplierpaymentsList where dueDate equals to UPDATED_DUE_DATE
        defaultSupplierpaymentsShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where dueDate is not null
        defaultSupplierpaymentsShouldBeFound("dueDate.specified=true");

        // Get all the supplierpaymentsList where dueDate is null
        defaultSupplierpaymentsShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByIspaidIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where ispaid equals to DEFAULT_ISPAID
        defaultSupplierpaymentsShouldBeFound("ispaid.equals=" + DEFAULT_ISPAID);

        // Get all the supplierpaymentsList where ispaid equals to UPDATED_ISPAID
        defaultSupplierpaymentsShouldNotBeFound("ispaid.equals=" + UPDATED_ISPAID);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByIspaidIsInShouldWork() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where ispaid in DEFAULT_ISPAID or UPDATED_ISPAID
        defaultSupplierpaymentsShouldBeFound("ispaid.in=" + DEFAULT_ISPAID + "," + UPDATED_ISPAID);

        // Get all the supplierpaymentsList where ispaid equals to UPDATED_ISPAID
        defaultSupplierpaymentsShouldNotBeFound("ispaid.in=" + UPDATED_ISPAID);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByIspaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where ispaid is not null
        defaultSupplierpaymentsShouldBeFound("ispaid.specified=true");

        // Get all the supplierpaymentsList where ispaid is null
        defaultSupplierpaymentsShouldNotBeFound("ispaid.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByAmountDueIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where amountDue equals to DEFAULT_AMOUNT_DUE
        defaultSupplierpaymentsShouldBeFound("amountDue.equals=" + DEFAULT_AMOUNT_DUE);

        // Get all the supplierpaymentsList where amountDue equals to UPDATED_AMOUNT_DUE
        defaultSupplierpaymentsShouldNotBeFound("amountDue.equals=" + UPDATED_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByAmountDueIsInShouldWork() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where amountDue in DEFAULT_AMOUNT_DUE or UPDATED_AMOUNT_DUE
        defaultSupplierpaymentsShouldBeFound("amountDue.in=" + DEFAULT_AMOUNT_DUE + "," + UPDATED_AMOUNT_DUE);

        // Get all the supplierpaymentsList where amountDue equals to UPDATED_AMOUNT_DUE
        defaultSupplierpaymentsShouldNotBeFound("amountDue.in=" + UPDATED_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByAmountDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where amountDue is not null
        defaultSupplierpaymentsShouldBeFound("amountDue.specified=true");

        // Get all the supplierpaymentsList where amountDue is null
        defaultSupplierpaymentsShouldNotBeFound("amountDue.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByAmountDueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where amountDue is greater than or equal to DEFAULT_AMOUNT_DUE
        defaultSupplierpaymentsShouldBeFound("amountDue.greaterThanOrEqual=" + DEFAULT_AMOUNT_DUE);

        // Get all the supplierpaymentsList where amountDue is greater than or equal to UPDATED_AMOUNT_DUE
        defaultSupplierpaymentsShouldNotBeFound("amountDue.greaterThanOrEqual=" + UPDATED_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByAmountDueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where amountDue is less than or equal to DEFAULT_AMOUNT_DUE
        defaultSupplierpaymentsShouldBeFound("amountDue.lessThanOrEqual=" + DEFAULT_AMOUNT_DUE);

        // Get all the supplierpaymentsList where amountDue is less than or equal to SMALLER_AMOUNT_DUE
        defaultSupplierpaymentsShouldNotBeFound("amountDue.lessThanOrEqual=" + SMALLER_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByAmountDueIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where amountDue is less than DEFAULT_AMOUNT_DUE
        defaultSupplierpaymentsShouldNotBeFound("amountDue.lessThan=" + DEFAULT_AMOUNT_DUE);

        // Get all the supplierpaymentsList where amountDue is less than UPDATED_AMOUNT_DUE
        defaultSupplierpaymentsShouldBeFound("amountDue.lessThan=" + UPDATED_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByAmountDueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where amountDue is greater than DEFAULT_AMOUNT_DUE
        defaultSupplierpaymentsShouldNotBeFound("amountDue.greaterThan=" + DEFAULT_AMOUNT_DUE);

        // Get all the supplierpaymentsList where amountDue is greater than SMALLER_AMOUNT_DUE
        defaultSupplierpaymentsShouldBeFound("amountDue.greaterThan=" + SMALLER_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where tax equals to DEFAULT_TAX
        defaultSupplierpaymentsShouldBeFound("tax.equals=" + DEFAULT_TAX);

        // Get all the supplierpaymentsList where tax equals to UPDATED_TAX
        defaultSupplierpaymentsShouldNotBeFound("tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where tax in DEFAULT_TAX or UPDATED_TAX
        defaultSupplierpaymentsShouldBeFound("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX);

        // Get all the supplierpaymentsList where tax equals to UPDATED_TAX
        defaultSupplierpaymentsShouldNotBeFound("tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where tax is not null
        defaultSupplierpaymentsShouldBeFound("tax.specified=true");

        // Get all the supplierpaymentsList where tax is null
        defaultSupplierpaymentsShouldNotBeFound("tax.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByTaxContainsSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where tax contains DEFAULT_TAX
        defaultSupplierpaymentsShouldBeFound("tax.contains=" + DEFAULT_TAX);

        // Get all the supplierpaymentsList where tax contains UPDATED_TAX
        defaultSupplierpaymentsShouldNotBeFound("tax.contains=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByTaxNotContainsSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where tax does not contain DEFAULT_TAX
        defaultSupplierpaymentsShouldNotBeFound("tax.doesNotContain=" + DEFAULT_TAX);

        // Get all the supplierpaymentsList where tax does not contain UPDATED_TAX
        defaultSupplierpaymentsShouldBeFound("tax.doesNotContain=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByShippingIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where shipping equals to DEFAULT_SHIPPING
        defaultSupplierpaymentsShouldBeFound("shipping.equals=" + DEFAULT_SHIPPING);

        // Get all the supplierpaymentsList where shipping equals to UPDATED_SHIPPING
        defaultSupplierpaymentsShouldNotBeFound("shipping.equals=" + UPDATED_SHIPPING);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByShippingIsInShouldWork() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where shipping in DEFAULT_SHIPPING or UPDATED_SHIPPING
        defaultSupplierpaymentsShouldBeFound("shipping.in=" + DEFAULT_SHIPPING + "," + UPDATED_SHIPPING);

        // Get all the supplierpaymentsList where shipping equals to UPDATED_SHIPPING
        defaultSupplierpaymentsShouldNotBeFound("shipping.in=" + UPDATED_SHIPPING);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByShippingIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where shipping is not null
        defaultSupplierpaymentsShouldBeFound("shipping.specified=true");

        // Get all the supplierpaymentsList where shipping is null
        defaultSupplierpaymentsShouldNotBeFound("shipping.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByShippingContainsSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where shipping contains DEFAULT_SHIPPING
        defaultSupplierpaymentsShouldBeFound("shipping.contains=" + DEFAULT_SHIPPING);

        // Get all the supplierpaymentsList where shipping contains UPDATED_SHIPPING
        defaultSupplierpaymentsShouldNotBeFound("shipping.contains=" + UPDATED_SHIPPING);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByShippingNotContainsSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where shipping does not contain DEFAULT_SHIPPING
        defaultSupplierpaymentsShouldNotBeFound("shipping.doesNotContain=" + DEFAULT_SHIPPING);

        // Get all the supplierpaymentsList where shipping does not contain UPDATED_SHIPPING
        defaultSupplierpaymentsShouldBeFound("shipping.doesNotContain=" + UPDATED_SHIPPING);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByLastupdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where lastupdated equals to DEFAULT_LASTUPDATED
        defaultSupplierpaymentsShouldBeFound("lastupdated.equals=" + DEFAULT_LASTUPDATED);

        // Get all the supplierpaymentsList where lastupdated equals to UPDATED_LASTUPDATED
        defaultSupplierpaymentsShouldNotBeFound("lastupdated.equals=" + UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByLastupdatedIsInShouldWork() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where lastupdated in DEFAULT_LASTUPDATED or UPDATED_LASTUPDATED
        defaultSupplierpaymentsShouldBeFound("lastupdated.in=" + DEFAULT_LASTUPDATED + "," + UPDATED_LASTUPDATED);

        // Get all the supplierpaymentsList where lastupdated equals to UPDATED_LASTUPDATED
        defaultSupplierpaymentsShouldNotBeFound("lastupdated.in=" + UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsByLastupdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        // Get all the supplierpaymentsList where lastupdated is not null
        defaultSupplierpaymentsShouldBeFound("lastupdated.specified=true");

        // Get all the supplierpaymentsList where lastupdated is null
        defaultSupplierpaymentsShouldNotBeFound("lastupdated.specified=false");
    }

    @Test
    @Transactional
    void getAllSupplierpaymentsBySupplierIsEqualToSomething() throws Exception {
        Supplier supplier;
        if (TestUtil.findAll(em, Supplier.class).isEmpty()) {
            supplierpaymentsRepository.saveAndFlush(supplierpayments);
            supplier = SupplierResourceIT.createEntity(em);
        } else {
            supplier = TestUtil.findAll(em, Supplier.class).get(0);
        }
        em.persist(supplier);
        em.flush();
        supplierpayments.setSupplier(supplier);
        supplierpaymentsRepository.saveAndFlush(supplierpayments);
        Long supplierId = supplier.getId();

        // Get all the supplierpaymentsList where supplier equals to supplierId
        defaultSupplierpaymentsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the supplierpaymentsList where supplier equals to (supplierId + 1)
        defaultSupplierpaymentsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierpaymentsShouldBeFound(String filter) throws Exception {
        restSupplierpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierpayments.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].ispaid").value(hasItem(DEFAULT_ISPAID.toString())))
            .andExpect(jsonPath("$.[*].amountDue").value(hasItem(sameNumber(DEFAULT_AMOUNT_DUE))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX)))
            .andExpect(jsonPath("$.[*].shipping").value(hasItem(DEFAULT_SHIPPING)))
            .andExpect(jsonPath("$.[*].lastupdated").value(hasItem(DEFAULT_LASTUPDATED.toString())));

        // Check, that the count call also returns 1
        restSupplierpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierpaymentsShouldNotBeFound(String filter) throws Exception {
        restSupplierpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierpaymentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSupplierpayments() throws Exception {
        // Get the supplierpayments
        restSupplierpaymentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSupplierpayments() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        int databaseSizeBeforeUpdate = supplierpaymentsRepository.findAll().size();

        // Update the supplierpayments
        Supplierpayments updatedSupplierpayments = supplierpaymentsRepository.findById(supplierpayments.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierpayments are not directly saved in db
        em.detach(updatedSupplierpayments);
        updatedSupplierpayments
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .ispaid(UPDATED_ISPAID)
            .amountDue(UPDATED_AMOUNT_DUE)
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .tax(UPDATED_TAX)
            .shipping(UPDATED_SHIPPING)
            .lastupdated(UPDATED_LASTUPDATED);

        restSupplierpaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSupplierpayments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSupplierpayments))
            )
            .andExpect(status().isOk());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeUpdate);
        Supplierpayments testSupplierpayments = supplierpaymentsList.get(supplierpaymentsList.size() - 1);
        assertThat(testSupplierpayments.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testSupplierpayments.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testSupplierpayments.getIspaid()).isEqualTo(UPDATED_ISPAID);
        assertThat(testSupplierpayments.getAmountDue()).isEqualByComparingTo(UPDATED_AMOUNT_DUE);
        assertThat(testSupplierpayments.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testSupplierpayments.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testSupplierpayments.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testSupplierpayments.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testSupplierpayments.getShipping()).isEqualTo(UPDATED_SHIPPING);
        assertThat(testSupplierpayments.getLastupdated()).isEqualTo(UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void putNonExistingSupplierpayments() throws Exception {
        int databaseSizeBeforeUpdate = supplierpaymentsRepository.findAll().size();
        supplierpayments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierpaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierpayments.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupplierpayments() throws Exception {
        int databaseSizeBeforeUpdate = supplierpaymentsRepository.findAll().size();
        supplierpayments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierpaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupplierpayments() throws Exception {
        int databaseSizeBeforeUpdate = supplierpaymentsRepository.findAll().size();
        supplierpayments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierpaymentsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierpayments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplierpaymentsWithPatch() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        int databaseSizeBeforeUpdate = supplierpaymentsRepository.findAll().size();

        // Update the supplierpayments using partial update
        Supplierpayments partialUpdatedSupplierpayments = new Supplierpayments();
        partialUpdatedSupplierpayments.setId(supplierpayments.getId());

        partialUpdatedSupplierpayments
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .shipping(UPDATED_SHIPPING)
            .lastupdated(UPDATED_LASTUPDATED);

        restSupplierpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplierpayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplierpayments))
            )
            .andExpect(status().isOk());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeUpdate);
        Supplierpayments testSupplierpayments = supplierpaymentsList.get(supplierpaymentsList.size() - 1);
        assertThat(testSupplierpayments.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testSupplierpayments.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testSupplierpayments.getIspaid()).isEqualTo(DEFAULT_ISPAID);
        assertThat(testSupplierpayments.getAmountDue()).isEqualByComparingTo(DEFAULT_AMOUNT_DUE);
        assertThat(testSupplierpayments.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testSupplierpayments.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testSupplierpayments.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testSupplierpayments.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testSupplierpayments.getShipping()).isEqualTo(UPDATED_SHIPPING);
        assertThat(testSupplierpayments.getLastupdated()).isEqualTo(UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void fullUpdateSupplierpaymentsWithPatch() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        int databaseSizeBeforeUpdate = supplierpaymentsRepository.findAll().size();

        // Update the supplierpayments using partial update
        Supplierpayments partialUpdatedSupplierpayments = new Supplierpayments();
        partialUpdatedSupplierpayments.setId(supplierpayments.getId());

        partialUpdatedSupplierpayments
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .ispaid(UPDATED_ISPAID)
            .amountDue(UPDATED_AMOUNT_DUE)
            .notes(UPDATED_NOTES)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE)
            .tax(UPDATED_TAX)
            .shipping(UPDATED_SHIPPING)
            .lastupdated(UPDATED_LASTUPDATED);

        restSupplierpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplierpayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplierpayments))
            )
            .andExpect(status().isOk());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeUpdate);
        Supplierpayments testSupplierpayments = supplierpaymentsList.get(supplierpaymentsList.size() - 1);
        assertThat(testSupplierpayments.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testSupplierpayments.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testSupplierpayments.getIspaid()).isEqualTo(UPDATED_ISPAID);
        assertThat(testSupplierpayments.getAmountDue()).isEqualByComparingTo(UPDATED_AMOUNT_DUE);
        assertThat(testSupplierpayments.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testSupplierpayments.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testSupplierpayments.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        assertThat(testSupplierpayments.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testSupplierpayments.getShipping()).isEqualTo(UPDATED_SHIPPING);
        assertThat(testSupplierpayments.getLastupdated()).isEqualTo(UPDATED_LASTUPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingSupplierpayments() throws Exception {
        int databaseSizeBeforeUpdate = supplierpaymentsRepository.findAll().size();
        supplierpayments.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplierpayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupplierpayments() throws Exception {
        int databaseSizeBeforeUpdate = supplierpaymentsRepository.findAll().size();
        supplierpayments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierpayments))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupplierpayments() throws Exception {
        int databaseSizeBeforeUpdate = supplierpaymentsRepository.findAll().size();
        supplierpayments.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierpaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierpayments))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supplierpayments in the database
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupplierpayments() throws Exception {
        // Initialize the database
        supplierpaymentsRepository.saveAndFlush(supplierpayments);

        int databaseSizeBeforeDelete = supplierpaymentsRepository.findAll().size();

        // Delete the supplierpayments
        restSupplierpaymentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, supplierpayments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supplierpayments> supplierpaymentsList = supplierpaymentsRepository.findAll();
        assertThat(supplierpaymentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
