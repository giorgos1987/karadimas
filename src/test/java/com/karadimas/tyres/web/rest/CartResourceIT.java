package com.karadimas.tyres.web.rest;

import static com.karadimas.tyres.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Cart;
import com.karadimas.tyres.domain.Customers;
import com.karadimas.tyres.domain.Job;
import com.karadimas.tyres.domain.enumeration.OrderStatus;
import com.karadimas.tyres.repository.CartRepository;
import com.karadimas.tyres.service.CartService;
import com.karadimas.tyres.service.criteria.CartCriteria;
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
 * Integration tests for the {@link CartResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CartResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_PLATE = "BBBBBBBBBB";

    private static final Instant DEFAULT_PLACEDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLACEDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SCHEDULEDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SCHEDULEDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELIVERYDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIVERYDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBERTYRES = 0;
    private static final Integer UPDATED_NUMBERTYRES = 1;
    private static final Integer SMALLER_NUMBERTYRES = 0 - 1;

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.PENDING;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.INPROGRESS;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_MECHANIC = "AAAAAAAAAA";
    private static final String UPDATED_MECHANIC = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORKORDER = 1;
    private static final Integer UPDATED_WORKORDER = 2;
    private static final Integer SMALLER_WORKORDER = 1 - 1;

    private static final String DEFAULT_KILOMETERS = "AAAAAAAAAA";
    private static final String UPDATED_KILOMETERS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal SMALLER_TOTAL_PRICE = new BigDecimal(0 - 1);

    private static final String ENTITY_API_URL = "/api/carts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CartRepository cartRepository;

    @Mock
    private CartRepository cartRepositoryMock;

    @Mock
    private CartService cartServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartMockMvc;

    private Cart cart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createEntity(EntityManager em) {
        Cart cart = new Cart()
            .name(DEFAULT_NAME)
            .plate(DEFAULT_PLATE)
            .placeddate(DEFAULT_PLACEDDATE)
            .scheduleddate(DEFAULT_SCHEDULEDDATE)
            .deliverydate(DEFAULT_DELIVERYDATE)
            .brand(DEFAULT_BRAND)
            .model(DEFAULT_MODEL)
            .numbertyres(DEFAULT_NUMBERTYRES)
            .status(DEFAULT_STATUS)
            .notes(DEFAULT_NOTES)
            .mechanic(DEFAULT_MECHANIC)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS)
            .workorder(DEFAULT_WORKORDER)
            .kilometers(DEFAULT_KILOMETERS)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .totalPrice(DEFAULT_TOTAL_PRICE);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        cart.setCustomers(customers);
        return cart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createUpdatedEntity(EntityManager em) {
        Cart cart = new Cart()
            .name(UPDATED_NAME)
            .plate(UPDATED_PLATE)
            .placeddate(UPDATED_PLACEDDATE)
            .scheduleddate(UPDATED_SCHEDULEDDATE)
            .deliverydate(UPDATED_DELIVERYDATE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .numbertyres(UPDATED_NUMBERTYRES)
            .status(UPDATED_STATUS)
            .notes(UPDATED_NOTES)
            .mechanic(UPDATED_MECHANIC)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .workorder(UPDATED_WORKORDER)
            .kilometers(UPDATED_KILOMETERS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .totalPrice(UPDATED_TOTAL_PRICE);
        // Add required entity
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            customers = CustomersResourceIT.createUpdatedEntity(em);
            em.persist(customers);
            em.flush();
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        cart.setCustomers(customers);
        return cart;
    }

    @BeforeEach
    public void initTest() {
        cart = createEntity(em);
    }

    @Test
    @Transactional
    void createCart() throws Exception {
        int databaseSizeBeforeCreate = cartRepository.findAll().size();
        // Create the Cart
        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cart)))
            .andExpect(status().isCreated());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate + 1);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCart.getPlate()).isEqualTo(DEFAULT_PLATE);
        assertThat(testCart.getPlaceddate()).isEqualTo(DEFAULT_PLACEDDATE);
        assertThat(testCart.getScheduleddate()).isEqualTo(DEFAULT_SCHEDULEDDATE);
        assertThat(testCart.getDeliverydate()).isEqualTo(DEFAULT_DELIVERYDATE);
        assertThat(testCart.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCart.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCart.getNumbertyres()).isEqualTo(DEFAULT_NUMBERTYRES);
        assertThat(testCart.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCart.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCart.getMechanic()).isEqualTo(DEFAULT_MECHANIC);
        assertThat(testCart.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCart.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCart.getWorkorder()).isEqualTo(DEFAULT_WORKORDER);
        assertThat(testCart.getKilometers()).isEqualTo(DEFAULT_KILOMETERS);
        assertThat(testCart.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testCart.getTotalPrice()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void createCartWithExistingId() throws Exception {
        // Create the Cart with an existing ID
        cart.setId(1L);

        int databaseSizeBeforeCreate = cartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cart)))
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setStatus(null);

        // Create the Cart, which fails.

        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cart)))
            .andExpect(status().isBadRequest());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarts() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList
        restCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].plate").value(hasItem(DEFAULT_PLATE)))
            .andExpect(jsonPath("$.[*].placeddate").value(hasItem(DEFAULT_PLACEDDATE.toString())))
            .andExpect(jsonPath("$.[*].scheduleddate").value(hasItem(DEFAULT_SCHEDULEDDATE.toString())))
            .andExpect(jsonPath("$.[*].deliverydate").value(hasItem(DEFAULT_DELIVERYDATE.toString())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].numbertyres").value(hasItem(DEFAULT_NUMBERTYRES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].mechanic").value(hasItem(DEFAULT_MECHANIC)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].workorder").value(hasItem(DEFAULT_WORKORDER)))
            .andExpect(jsonPath("$.[*].kilometers").value(hasItem(DEFAULT_KILOMETERS)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCartsWithEagerRelationshipsIsEnabled() throws Exception {
        when(cartServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCartMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cartServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCartsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cartServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCartMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cartRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get the cart
        restCartMockMvc
            .perform(get(ENTITY_API_URL_ID, cart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cart.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.plate").value(DEFAULT_PLATE))
            .andExpect(jsonPath("$.placeddate").value(DEFAULT_PLACEDDATE.toString()))
            .andExpect(jsonPath("$.scheduleddate").value(DEFAULT_SCHEDULEDDATE.toString()))
            .andExpect(jsonPath("$.deliverydate").value(DEFAULT_DELIVERYDATE.toString()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.numbertyres").value(DEFAULT_NUMBERTYRES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.mechanic").value(DEFAULT_MECHANIC))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.workorder").value(DEFAULT_WORKORDER))
            .andExpect(jsonPath("$.kilometers").value(DEFAULT_KILOMETERS))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
            .andExpect(jsonPath("$.totalPrice").value(sameNumber(DEFAULT_TOTAL_PRICE)));
    }

    @Test
    @Transactional
    void getCartsByIdFiltering() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        Long id = cart.getId();

        defaultCartShouldBeFound("id.equals=" + id);
        defaultCartShouldNotBeFound("id.notEquals=" + id);

        defaultCartShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCartShouldNotBeFound("id.greaterThan=" + id);

        defaultCartShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCartShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCartsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where name equals to DEFAULT_NAME
        defaultCartShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cartList where name equals to UPDATED_NAME
        defaultCartShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCartsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCartShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cartList where name equals to UPDATED_NAME
        defaultCartShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCartsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where name is not null
        defaultCartShouldBeFound("name.specified=true");

        // Get all the cartList where name is null
        defaultCartShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByNameContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where name contains DEFAULT_NAME
        defaultCartShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cartList where name contains UPDATED_NAME
        defaultCartShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCartsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where name does not contain DEFAULT_NAME
        defaultCartShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cartList where name does not contain UPDATED_NAME
        defaultCartShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCartsByPlateIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where plate equals to DEFAULT_PLATE
        defaultCartShouldBeFound("plate.equals=" + DEFAULT_PLATE);

        // Get all the cartList where plate equals to UPDATED_PLATE
        defaultCartShouldNotBeFound("plate.equals=" + UPDATED_PLATE);
    }

    @Test
    @Transactional
    void getAllCartsByPlateIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where plate in DEFAULT_PLATE or UPDATED_PLATE
        defaultCartShouldBeFound("plate.in=" + DEFAULT_PLATE + "," + UPDATED_PLATE);

        // Get all the cartList where plate equals to UPDATED_PLATE
        defaultCartShouldNotBeFound("plate.in=" + UPDATED_PLATE);
    }

    @Test
    @Transactional
    void getAllCartsByPlateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where plate is not null
        defaultCartShouldBeFound("plate.specified=true");

        // Get all the cartList where plate is null
        defaultCartShouldNotBeFound("plate.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByPlateContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where plate contains DEFAULT_PLATE
        defaultCartShouldBeFound("plate.contains=" + DEFAULT_PLATE);

        // Get all the cartList where plate contains UPDATED_PLATE
        defaultCartShouldNotBeFound("plate.contains=" + UPDATED_PLATE);
    }

    @Test
    @Transactional
    void getAllCartsByPlateNotContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where plate does not contain DEFAULT_PLATE
        defaultCartShouldNotBeFound("plate.doesNotContain=" + DEFAULT_PLATE);

        // Get all the cartList where plate does not contain UPDATED_PLATE
        defaultCartShouldBeFound("plate.doesNotContain=" + UPDATED_PLATE);
    }

    @Test
    @Transactional
    void getAllCartsByPlaceddateIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where placeddate equals to DEFAULT_PLACEDDATE
        defaultCartShouldBeFound("placeddate.equals=" + DEFAULT_PLACEDDATE);

        // Get all the cartList where placeddate equals to UPDATED_PLACEDDATE
        defaultCartShouldNotBeFound("placeddate.equals=" + UPDATED_PLACEDDATE);
    }

    @Test
    @Transactional
    void getAllCartsByPlaceddateIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where placeddate in DEFAULT_PLACEDDATE or UPDATED_PLACEDDATE
        defaultCartShouldBeFound("placeddate.in=" + DEFAULT_PLACEDDATE + "," + UPDATED_PLACEDDATE);

        // Get all the cartList where placeddate equals to UPDATED_PLACEDDATE
        defaultCartShouldNotBeFound("placeddate.in=" + UPDATED_PLACEDDATE);
    }

    @Test
    @Transactional
    void getAllCartsByPlaceddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where placeddate is not null
        defaultCartShouldBeFound("placeddate.specified=true");

        // Get all the cartList where placeddate is null
        defaultCartShouldNotBeFound("placeddate.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByScheduleddateIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where scheduleddate equals to DEFAULT_SCHEDULEDDATE
        defaultCartShouldBeFound("scheduleddate.equals=" + DEFAULT_SCHEDULEDDATE);

        // Get all the cartList where scheduleddate equals to UPDATED_SCHEDULEDDATE
        defaultCartShouldNotBeFound("scheduleddate.equals=" + UPDATED_SCHEDULEDDATE);
    }

    @Test
    @Transactional
    void getAllCartsByScheduleddateIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where scheduleddate in DEFAULT_SCHEDULEDDATE or UPDATED_SCHEDULEDDATE
        defaultCartShouldBeFound("scheduleddate.in=" + DEFAULT_SCHEDULEDDATE + "," + UPDATED_SCHEDULEDDATE);

        // Get all the cartList where scheduleddate equals to UPDATED_SCHEDULEDDATE
        defaultCartShouldNotBeFound("scheduleddate.in=" + UPDATED_SCHEDULEDDATE);
    }

    @Test
    @Transactional
    void getAllCartsByScheduleddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where scheduleddate is not null
        defaultCartShouldBeFound("scheduleddate.specified=true");

        // Get all the cartList where scheduleddate is null
        defaultCartShouldNotBeFound("scheduleddate.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByDeliverydateIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where deliverydate equals to DEFAULT_DELIVERYDATE
        defaultCartShouldBeFound("deliverydate.equals=" + DEFAULT_DELIVERYDATE);

        // Get all the cartList where deliverydate equals to UPDATED_DELIVERYDATE
        defaultCartShouldNotBeFound("deliverydate.equals=" + UPDATED_DELIVERYDATE);
    }

    @Test
    @Transactional
    void getAllCartsByDeliverydateIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where deliverydate in DEFAULT_DELIVERYDATE or UPDATED_DELIVERYDATE
        defaultCartShouldBeFound("deliverydate.in=" + DEFAULT_DELIVERYDATE + "," + UPDATED_DELIVERYDATE);

        // Get all the cartList where deliverydate equals to UPDATED_DELIVERYDATE
        defaultCartShouldNotBeFound("deliverydate.in=" + UPDATED_DELIVERYDATE);
    }

    @Test
    @Transactional
    void getAllCartsByDeliverydateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where deliverydate is not null
        defaultCartShouldBeFound("deliverydate.specified=true");

        // Get all the cartList where deliverydate is null
        defaultCartShouldNotBeFound("deliverydate.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where brand equals to DEFAULT_BRAND
        defaultCartShouldBeFound("brand.equals=" + DEFAULT_BRAND);

        // Get all the cartList where brand equals to UPDATED_BRAND
        defaultCartShouldNotBeFound("brand.equals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllCartsByBrandIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where brand in DEFAULT_BRAND or UPDATED_BRAND
        defaultCartShouldBeFound("brand.in=" + DEFAULT_BRAND + "," + UPDATED_BRAND);

        // Get all the cartList where brand equals to UPDATED_BRAND
        defaultCartShouldNotBeFound("brand.in=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllCartsByBrandIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where brand is not null
        defaultCartShouldBeFound("brand.specified=true");

        // Get all the cartList where brand is null
        defaultCartShouldNotBeFound("brand.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByBrandContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where brand contains DEFAULT_BRAND
        defaultCartShouldBeFound("brand.contains=" + DEFAULT_BRAND);

        // Get all the cartList where brand contains UPDATED_BRAND
        defaultCartShouldNotBeFound("brand.contains=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllCartsByBrandNotContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where brand does not contain DEFAULT_BRAND
        defaultCartShouldNotBeFound("brand.doesNotContain=" + DEFAULT_BRAND);

        // Get all the cartList where brand does not contain UPDATED_BRAND
        defaultCartShouldBeFound("brand.doesNotContain=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllCartsByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where model equals to DEFAULT_MODEL
        defaultCartShouldBeFound("model.equals=" + DEFAULT_MODEL);

        // Get all the cartList where model equals to UPDATED_MODEL
        defaultCartShouldNotBeFound("model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllCartsByModelIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where model in DEFAULT_MODEL or UPDATED_MODEL
        defaultCartShouldBeFound("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL);

        // Get all the cartList where model equals to UPDATED_MODEL
        defaultCartShouldNotBeFound("model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllCartsByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where model is not null
        defaultCartShouldBeFound("model.specified=true");

        // Get all the cartList where model is null
        defaultCartShouldNotBeFound("model.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByModelContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where model contains DEFAULT_MODEL
        defaultCartShouldBeFound("model.contains=" + DEFAULT_MODEL);

        // Get all the cartList where model contains UPDATED_MODEL
        defaultCartShouldNotBeFound("model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllCartsByModelNotContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where model does not contain DEFAULT_MODEL
        defaultCartShouldNotBeFound("model.doesNotContain=" + DEFAULT_MODEL);

        // Get all the cartList where model does not contain UPDATED_MODEL
        defaultCartShouldBeFound("model.doesNotContain=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllCartsByNumbertyresIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where numbertyres equals to DEFAULT_NUMBERTYRES
        defaultCartShouldBeFound("numbertyres.equals=" + DEFAULT_NUMBERTYRES);

        // Get all the cartList where numbertyres equals to UPDATED_NUMBERTYRES
        defaultCartShouldNotBeFound("numbertyres.equals=" + UPDATED_NUMBERTYRES);
    }

    @Test
    @Transactional
    void getAllCartsByNumbertyresIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where numbertyres in DEFAULT_NUMBERTYRES or UPDATED_NUMBERTYRES
        defaultCartShouldBeFound("numbertyres.in=" + DEFAULT_NUMBERTYRES + "," + UPDATED_NUMBERTYRES);

        // Get all the cartList where numbertyres equals to UPDATED_NUMBERTYRES
        defaultCartShouldNotBeFound("numbertyres.in=" + UPDATED_NUMBERTYRES);
    }

    @Test
    @Transactional
    void getAllCartsByNumbertyresIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where numbertyres is not null
        defaultCartShouldBeFound("numbertyres.specified=true");

        // Get all the cartList where numbertyres is null
        defaultCartShouldNotBeFound("numbertyres.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByNumbertyresIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where numbertyres is greater than or equal to DEFAULT_NUMBERTYRES
        defaultCartShouldBeFound("numbertyres.greaterThanOrEqual=" + DEFAULT_NUMBERTYRES);

        // Get all the cartList where numbertyres is greater than or equal to UPDATED_NUMBERTYRES
        defaultCartShouldNotBeFound("numbertyres.greaterThanOrEqual=" + UPDATED_NUMBERTYRES);
    }

    @Test
    @Transactional
    void getAllCartsByNumbertyresIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where numbertyres is less than or equal to DEFAULT_NUMBERTYRES
        defaultCartShouldBeFound("numbertyres.lessThanOrEqual=" + DEFAULT_NUMBERTYRES);

        // Get all the cartList where numbertyres is less than or equal to SMALLER_NUMBERTYRES
        defaultCartShouldNotBeFound("numbertyres.lessThanOrEqual=" + SMALLER_NUMBERTYRES);
    }

    @Test
    @Transactional
    void getAllCartsByNumbertyresIsLessThanSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where numbertyres is less than DEFAULT_NUMBERTYRES
        defaultCartShouldNotBeFound("numbertyres.lessThan=" + DEFAULT_NUMBERTYRES);

        // Get all the cartList where numbertyres is less than UPDATED_NUMBERTYRES
        defaultCartShouldBeFound("numbertyres.lessThan=" + UPDATED_NUMBERTYRES);
    }

    @Test
    @Transactional
    void getAllCartsByNumbertyresIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where numbertyres is greater than DEFAULT_NUMBERTYRES
        defaultCartShouldNotBeFound("numbertyres.greaterThan=" + DEFAULT_NUMBERTYRES);

        // Get all the cartList where numbertyres is greater than SMALLER_NUMBERTYRES
        defaultCartShouldBeFound("numbertyres.greaterThan=" + SMALLER_NUMBERTYRES);
    }

    @Test
    @Transactional
    void getAllCartsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where status equals to DEFAULT_STATUS
        defaultCartShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the cartList where status equals to UPDATED_STATUS
        defaultCartShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCartsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCartShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the cartList where status equals to UPDATED_STATUS
        defaultCartShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCartsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where status is not null
        defaultCartShouldBeFound("status.specified=true");

        // Get all the cartList where status is null
        defaultCartShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByMechanicIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where mechanic equals to DEFAULT_MECHANIC
        defaultCartShouldBeFound("mechanic.equals=" + DEFAULT_MECHANIC);

        // Get all the cartList where mechanic equals to UPDATED_MECHANIC
        defaultCartShouldNotBeFound("mechanic.equals=" + UPDATED_MECHANIC);
    }

    @Test
    @Transactional
    void getAllCartsByMechanicIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where mechanic in DEFAULT_MECHANIC or UPDATED_MECHANIC
        defaultCartShouldBeFound("mechanic.in=" + DEFAULT_MECHANIC + "," + UPDATED_MECHANIC);

        // Get all the cartList where mechanic equals to UPDATED_MECHANIC
        defaultCartShouldNotBeFound("mechanic.in=" + UPDATED_MECHANIC);
    }

    @Test
    @Transactional
    void getAllCartsByMechanicIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where mechanic is not null
        defaultCartShouldBeFound("mechanic.specified=true");

        // Get all the cartList where mechanic is null
        defaultCartShouldNotBeFound("mechanic.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByMechanicContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where mechanic contains DEFAULT_MECHANIC
        defaultCartShouldBeFound("mechanic.contains=" + DEFAULT_MECHANIC);

        // Get all the cartList where mechanic contains UPDATED_MECHANIC
        defaultCartShouldNotBeFound("mechanic.contains=" + UPDATED_MECHANIC);
    }

    @Test
    @Transactional
    void getAllCartsByMechanicNotContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where mechanic does not contain DEFAULT_MECHANIC
        defaultCartShouldNotBeFound("mechanic.doesNotContain=" + DEFAULT_MECHANIC);

        // Get all the cartList where mechanic does not contain UPDATED_MECHANIC
        defaultCartShouldBeFound("mechanic.doesNotContain=" + UPDATED_MECHANIC);
    }

    @Test
    @Transactional
    void getAllCartsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where phone equals to DEFAULT_PHONE
        defaultCartShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the cartList where phone equals to UPDATED_PHONE
        defaultCartShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCartsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultCartShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the cartList where phone equals to UPDATED_PHONE
        defaultCartShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCartsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where phone is not null
        defaultCartShouldBeFound("phone.specified=true");

        // Get all the cartList where phone is null
        defaultCartShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where phone contains DEFAULT_PHONE
        defaultCartShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the cartList where phone contains UPDATED_PHONE
        defaultCartShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCartsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where phone does not contain DEFAULT_PHONE
        defaultCartShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the cartList where phone does not contain UPDATED_PHONE
        defaultCartShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllCartsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where address equals to DEFAULT_ADDRESS
        defaultCartShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the cartList where address equals to UPDATED_ADDRESS
        defaultCartShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCartsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCartShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the cartList where address equals to UPDATED_ADDRESS
        defaultCartShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCartsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where address is not null
        defaultCartShouldBeFound("address.specified=true");

        // Get all the cartList where address is null
        defaultCartShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByAddressContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where address contains DEFAULT_ADDRESS
        defaultCartShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the cartList where address contains UPDATED_ADDRESS
        defaultCartShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCartsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where address does not contain DEFAULT_ADDRESS
        defaultCartShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the cartList where address does not contain UPDATED_ADDRESS
        defaultCartShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCartsByWorkorderIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where workorder equals to DEFAULT_WORKORDER
        defaultCartShouldBeFound("workorder.equals=" + DEFAULT_WORKORDER);

        // Get all the cartList where workorder equals to UPDATED_WORKORDER
        defaultCartShouldNotBeFound("workorder.equals=" + UPDATED_WORKORDER);
    }

    @Test
    @Transactional
    void getAllCartsByWorkorderIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where workorder in DEFAULT_WORKORDER or UPDATED_WORKORDER
        defaultCartShouldBeFound("workorder.in=" + DEFAULT_WORKORDER + "," + UPDATED_WORKORDER);

        // Get all the cartList where workorder equals to UPDATED_WORKORDER
        defaultCartShouldNotBeFound("workorder.in=" + UPDATED_WORKORDER);
    }

    @Test
    @Transactional
    void getAllCartsByWorkorderIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where workorder is not null
        defaultCartShouldBeFound("workorder.specified=true");

        // Get all the cartList where workorder is null
        defaultCartShouldNotBeFound("workorder.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByWorkorderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where workorder is greater than or equal to DEFAULT_WORKORDER
        defaultCartShouldBeFound("workorder.greaterThanOrEqual=" + DEFAULT_WORKORDER);

        // Get all the cartList where workorder is greater than or equal to UPDATED_WORKORDER
        defaultCartShouldNotBeFound("workorder.greaterThanOrEqual=" + UPDATED_WORKORDER);
    }

    @Test
    @Transactional
    void getAllCartsByWorkorderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where workorder is less than or equal to DEFAULT_WORKORDER
        defaultCartShouldBeFound("workorder.lessThanOrEqual=" + DEFAULT_WORKORDER);

        // Get all the cartList where workorder is less than or equal to SMALLER_WORKORDER
        defaultCartShouldNotBeFound("workorder.lessThanOrEqual=" + SMALLER_WORKORDER);
    }

    @Test
    @Transactional
    void getAllCartsByWorkorderIsLessThanSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where workorder is less than DEFAULT_WORKORDER
        defaultCartShouldNotBeFound("workorder.lessThan=" + DEFAULT_WORKORDER);

        // Get all the cartList where workorder is less than UPDATED_WORKORDER
        defaultCartShouldBeFound("workorder.lessThan=" + UPDATED_WORKORDER);
    }

    @Test
    @Transactional
    void getAllCartsByWorkorderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where workorder is greater than DEFAULT_WORKORDER
        defaultCartShouldNotBeFound("workorder.greaterThan=" + DEFAULT_WORKORDER);

        // Get all the cartList where workorder is greater than SMALLER_WORKORDER
        defaultCartShouldBeFound("workorder.greaterThan=" + SMALLER_WORKORDER);
    }

    @Test
    @Transactional
    void getAllCartsByKilometersIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where kilometers equals to DEFAULT_KILOMETERS
        defaultCartShouldBeFound("kilometers.equals=" + DEFAULT_KILOMETERS);

        // Get all the cartList where kilometers equals to UPDATED_KILOMETERS
        defaultCartShouldNotBeFound("kilometers.equals=" + UPDATED_KILOMETERS);
    }

    @Test
    @Transactional
    void getAllCartsByKilometersIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where kilometers in DEFAULT_KILOMETERS or UPDATED_KILOMETERS
        defaultCartShouldBeFound("kilometers.in=" + DEFAULT_KILOMETERS + "," + UPDATED_KILOMETERS);

        // Get all the cartList where kilometers equals to UPDATED_KILOMETERS
        defaultCartShouldNotBeFound("kilometers.in=" + UPDATED_KILOMETERS);
    }

    @Test
    @Transactional
    void getAllCartsByKilometersIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where kilometers is not null
        defaultCartShouldBeFound("kilometers.specified=true");

        // Get all the cartList where kilometers is null
        defaultCartShouldNotBeFound("kilometers.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByKilometersContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where kilometers contains DEFAULT_KILOMETERS
        defaultCartShouldBeFound("kilometers.contains=" + DEFAULT_KILOMETERS);

        // Get all the cartList where kilometers contains UPDATED_KILOMETERS
        defaultCartShouldNotBeFound("kilometers.contains=" + UPDATED_KILOMETERS);
    }

    @Test
    @Transactional
    void getAllCartsByKilometersNotContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where kilometers does not contain DEFAULT_KILOMETERS
        defaultCartShouldNotBeFound("kilometers.doesNotContain=" + DEFAULT_KILOMETERS);

        // Get all the cartList where kilometers does not contain UPDATED_KILOMETERS
        defaultCartShouldBeFound("kilometers.doesNotContain=" + UPDATED_KILOMETERS);
    }

    @Test
    @Transactional
    void getAllCartsByPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where paymentMethod equals to DEFAULT_PAYMENT_METHOD
        defaultCartShouldBeFound("paymentMethod.equals=" + DEFAULT_PAYMENT_METHOD);

        // Get all the cartList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultCartShouldNotBeFound("paymentMethod.equals=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void getAllCartsByPaymentMethodIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where paymentMethod in DEFAULT_PAYMENT_METHOD or UPDATED_PAYMENT_METHOD
        defaultCartShouldBeFound("paymentMethod.in=" + DEFAULT_PAYMENT_METHOD + "," + UPDATED_PAYMENT_METHOD);

        // Get all the cartList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultCartShouldNotBeFound("paymentMethod.in=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void getAllCartsByPaymentMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where paymentMethod is not null
        defaultCartShouldBeFound("paymentMethod.specified=true");

        // Get all the cartList where paymentMethod is null
        defaultCartShouldNotBeFound("paymentMethod.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByPaymentMethodContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where paymentMethod contains DEFAULT_PAYMENT_METHOD
        defaultCartShouldBeFound("paymentMethod.contains=" + DEFAULT_PAYMENT_METHOD);

        // Get all the cartList where paymentMethod contains UPDATED_PAYMENT_METHOD
        defaultCartShouldNotBeFound("paymentMethod.contains=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void getAllCartsByPaymentMethodNotContainsSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where paymentMethod does not contain DEFAULT_PAYMENT_METHOD
        defaultCartShouldNotBeFound("paymentMethod.doesNotContain=" + DEFAULT_PAYMENT_METHOD);

        // Get all the cartList where paymentMethod does not contain UPDATED_PAYMENT_METHOD
        defaultCartShouldBeFound("paymentMethod.doesNotContain=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void getAllCartsByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultCartShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the cartList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultCartShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllCartsByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultCartShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the cartList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultCartShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllCartsByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where totalPrice is not null
        defaultCartShouldBeFound("totalPrice.specified=true");

        // Get all the cartList where totalPrice is null
        defaultCartShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllCartsByTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where totalPrice is greater than or equal to DEFAULT_TOTAL_PRICE
        defaultCartShouldBeFound("totalPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the cartList where totalPrice is greater than or equal to UPDATED_TOTAL_PRICE
        defaultCartShouldNotBeFound("totalPrice.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllCartsByTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where totalPrice is less than or equal to DEFAULT_TOTAL_PRICE
        defaultCartShouldBeFound("totalPrice.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the cartList where totalPrice is less than or equal to SMALLER_TOTAL_PRICE
        defaultCartShouldNotBeFound("totalPrice.lessThanOrEqual=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllCartsByTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where totalPrice is less than DEFAULT_TOTAL_PRICE
        defaultCartShouldNotBeFound("totalPrice.lessThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the cartList where totalPrice is less than UPDATED_TOTAL_PRICE
        defaultCartShouldBeFound("totalPrice.lessThan=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllCartsByTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where totalPrice is greater than DEFAULT_TOTAL_PRICE
        defaultCartShouldNotBeFound("totalPrice.greaterThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the cartList where totalPrice is greater than SMALLER_TOTAL_PRICE
        defaultCartShouldBeFound("totalPrice.greaterThan=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void getAllCartsByJobIsEqualToSomething() throws Exception {
        Job job;
        if (TestUtil.findAll(em, Job.class).isEmpty()) {
            cartRepository.saveAndFlush(cart);
            job = JobResourceIT.createEntity(em);
        } else {
            job = TestUtil.findAll(em, Job.class).get(0);
        }
        em.persist(job);
        em.flush();
        cart.addJob(job);
        cartRepository.saveAndFlush(cart);
        Long jobId = job.getId();

        // Get all the cartList where job equals to jobId
        defaultCartShouldBeFound("jobId.equals=" + jobId);

        // Get all the cartList where job equals to (jobId + 1)
        defaultCartShouldNotBeFound("jobId.equals=" + (jobId + 1));
    }

    @Test
    @Transactional
    void getAllCartsByCustomersIsEqualToSomething() throws Exception {
        Customers customers;
        if (TestUtil.findAll(em, Customers.class).isEmpty()) {
            cartRepository.saveAndFlush(cart);
            customers = CustomersResourceIT.createEntity(em);
        } else {
            customers = TestUtil.findAll(em, Customers.class).get(0);
        }
        em.persist(customers);
        em.flush();
        cart.setCustomers(customers);
        cartRepository.saveAndFlush(cart);
        Long customersId = customers.getId();

        // Get all the cartList where customers equals to customersId
        defaultCartShouldBeFound("customersId.equals=" + customersId);

        // Get all the cartList where customers equals to (customersId + 1)
        defaultCartShouldNotBeFound("customersId.equals=" + (customersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCartShouldBeFound(String filter) throws Exception {
        restCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].plate").value(hasItem(DEFAULT_PLATE)))
            .andExpect(jsonPath("$.[*].placeddate").value(hasItem(DEFAULT_PLACEDDATE.toString())))
            .andExpect(jsonPath("$.[*].scheduleddate").value(hasItem(DEFAULT_SCHEDULEDDATE.toString())))
            .andExpect(jsonPath("$.[*].deliverydate").value(hasItem(DEFAULT_DELIVERYDATE.toString())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].numbertyres").value(hasItem(DEFAULT_NUMBERTYRES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].mechanic").value(hasItem(DEFAULT_MECHANIC)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].workorder").value(hasItem(DEFAULT_WORKORDER)))
            .andExpect(jsonPath("$.[*].kilometers").value(hasItem(DEFAULT_KILOMETERS)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE))));

        // Check, that the count call also returns 1
        restCartMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCartShouldNotBeFound(String filter) throws Exception {
        restCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCartMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCart() throws Exception {
        // Get the cart
        restCartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart
        Cart updatedCart = cartRepository.findById(cart.getId()).get();
        // Disconnect from session so that the updates on updatedCart are not directly saved in db
        em.detach(updatedCart);
        updatedCart
            .name(UPDATED_NAME)
            .plate(UPDATED_PLATE)
            .placeddate(UPDATED_PLACEDDATE)
            .scheduleddate(UPDATED_SCHEDULEDDATE)
            .deliverydate(UPDATED_DELIVERYDATE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .numbertyres(UPDATED_NUMBERTYRES)
            .status(UPDATED_STATUS)
            .notes(UPDATED_NOTES)
            .mechanic(UPDATED_MECHANIC)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .workorder(UPDATED_WORKORDER)
            .kilometers(UPDATED_KILOMETERS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .totalPrice(UPDATED_TOTAL_PRICE);

        restCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCart))
            )
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCart.getPlate()).isEqualTo(UPDATED_PLATE);
        assertThat(testCart.getPlaceddate()).isEqualTo(UPDATED_PLACEDDATE);
        assertThat(testCart.getScheduleddate()).isEqualTo(UPDATED_SCHEDULEDDATE);
        assertThat(testCart.getDeliverydate()).isEqualTo(UPDATED_DELIVERYDATE);
        assertThat(testCart.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCart.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCart.getNumbertyres()).isEqualTo(UPDATED_NUMBERTYRES);
        assertThat(testCart.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCart.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCart.getMechanic()).isEqualTo(UPDATED_MECHANIC);
        assertThat(testCart.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCart.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCart.getWorkorder()).isEqualTo(UPDATED_WORKORDER);
        assertThat(testCart.getKilometers()).isEqualTo(UPDATED_KILOMETERS);
        assertThat(testCart.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testCart.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cart.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCartWithPatch() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart using partial update
        Cart partialUpdatedCart = new Cart();
        partialUpdatedCart.setId(cart.getId());

        partialUpdatedCart
            .name(UPDATED_NAME)
            .placeddate(UPDATED_PLACEDDATE)
            .deliverydate(UPDATED_DELIVERYDATE)
            .model(UPDATED_MODEL)
            .numbertyres(UPDATED_NUMBERTYRES)
            .notes(UPDATED_NOTES)
            .mechanic(UPDATED_MECHANIC)
            .workorder(UPDATED_WORKORDER)
            .paymentMethod(UPDATED_PAYMENT_METHOD);

        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCart))
            )
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCart.getPlate()).isEqualTo(DEFAULT_PLATE);
        assertThat(testCart.getPlaceddate()).isEqualTo(UPDATED_PLACEDDATE);
        assertThat(testCart.getScheduleddate()).isEqualTo(DEFAULT_SCHEDULEDDATE);
        assertThat(testCart.getDeliverydate()).isEqualTo(UPDATED_DELIVERYDATE);
        assertThat(testCart.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCart.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCart.getNumbertyres()).isEqualTo(UPDATED_NUMBERTYRES);
        assertThat(testCart.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCart.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCart.getMechanic()).isEqualTo(UPDATED_MECHANIC);
        assertThat(testCart.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCart.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCart.getWorkorder()).isEqualTo(UPDATED_WORKORDER);
        assertThat(testCart.getKilometers()).isEqualTo(DEFAULT_KILOMETERS);
        assertThat(testCart.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testCart.getTotalPrice()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateCartWithPatch() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart using partial update
        Cart partialUpdatedCart = new Cart();
        partialUpdatedCart.setId(cart.getId());

        partialUpdatedCart
            .name(UPDATED_NAME)
            .plate(UPDATED_PLATE)
            .placeddate(UPDATED_PLACEDDATE)
            .scheduleddate(UPDATED_SCHEDULEDDATE)
            .deliverydate(UPDATED_DELIVERYDATE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .numbertyres(UPDATED_NUMBERTYRES)
            .status(UPDATED_STATUS)
            .notes(UPDATED_NOTES)
            .mechanic(UPDATED_MECHANIC)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .workorder(UPDATED_WORKORDER)
            .kilometers(UPDATED_KILOMETERS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .totalPrice(UPDATED_TOTAL_PRICE);

        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCart))
            )
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCart.getPlate()).isEqualTo(UPDATED_PLATE);
        assertThat(testCart.getPlaceddate()).isEqualTo(UPDATED_PLACEDDATE);
        assertThat(testCart.getScheduleddate()).isEqualTo(UPDATED_SCHEDULEDDATE);
        assertThat(testCart.getDeliverydate()).isEqualTo(UPDATED_DELIVERYDATE);
        assertThat(testCart.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCart.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCart.getNumbertyres()).isEqualTo(UPDATED_NUMBERTYRES);
        assertThat(testCart.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCart.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCart.getMechanic()).isEqualTo(UPDATED_MECHANIC);
        assertThat(testCart.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCart.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCart.getWorkorder()).isEqualTo(UPDATED_WORKORDER);
        assertThat(testCart.getKilometers()).isEqualTo(UPDATED_KILOMETERS);
        assertThat(testCart.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testCart.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cart))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cart)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeDelete = cartRepository.findAll().size();

        // Delete the cart
        restCartMockMvc
            .perform(delete(ENTITY_API_URL_ID, cart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
