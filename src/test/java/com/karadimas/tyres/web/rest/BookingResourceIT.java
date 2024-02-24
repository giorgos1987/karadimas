package com.karadimas.tyres.web.rest;

import static com.karadimas.tyres.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.karadimas.tyres.IntegrationTest;
import com.karadimas.tyres.domain.Booking;
import com.karadimas.tyres.repository.BookingRepository;
import com.karadimas.tyres.service.criteria.BookingCriteria;
import java.math.BigDecimal;
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
 * Integration tests for the {@link BookingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookingResourceIT {

    private static final Instant DEFAULT_EVENTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EVENTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ESTIMATEDHOURS = new BigDecimal(1);
    private static final BigDecimal UPDATED_ESTIMATEDHOURS = new BigDecimal(2);
    private static final BigDecimal SMALLER_ESTIMATEDHOURS = new BigDecimal(1 - 1);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bookings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookingMockMvc;

    private Booking booking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Booking createEntity(EntityManager em) {
        Booking booking = new Booking()
            .eventdate(DEFAULT_EVENTDATE)
            .mobile(DEFAULT_MOBILE)
            .brand(DEFAULT_BRAND)
            .model(DEFAULT_MODEL)
            .estimatedhours(DEFAULT_ESTIMATEDHOURS)
            .note(DEFAULT_NOTE);
        return booking;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Booking createUpdatedEntity(EntityManager em) {
        Booking booking = new Booking()
            .eventdate(UPDATED_EVENTDATE)
            .mobile(UPDATED_MOBILE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .estimatedhours(UPDATED_ESTIMATEDHOURS)
            .note(UPDATED_NOTE);
        return booking;
    }

    @BeforeEach
    public void initTest() {
        booking = createEntity(em);
    }

    @Test
    @Transactional
    void createBooking() throws Exception {
        int databaseSizeBeforeCreate = bookingRepository.findAll().size();
        // Create the Booking
        restBookingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isCreated());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate + 1);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getEventdate()).isEqualTo(DEFAULT_EVENTDATE);
        assertThat(testBooking.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testBooking.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testBooking.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testBooking.getEstimatedhours()).isEqualByComparingTo(DEFAULT_ESTIMATEDHOURS);
        assertThat(testBooking.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createBookingWithExistingId() throws Exception {
        // Create the Booking with an existing ID
        booking.setId(1L);

        int databaseSizeBeforeCreate = bookingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBookings() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList
        restBookingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(booking.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventdate").value(hasItem(DEFAULT_EVENTDATE.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].estimatedhours").value(hasItem(sameNumber(DEFAULT_ESTIMATEDHOURS))))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    @Transactional
    void getBooking() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get the booking
        restBookingMockMvc
            .perform(get(ENTITY_API_URL_ID, booking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(booking.getId().intValue()))
            .andExpect(jsonPath("$.eventdate").value(DEFAULT_EVENTDATE.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.estimatedhours").value(sameNumber(DEFAULT_ESTIMATEDHOURS)))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    void getBookingsByIdFiltering() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        Long id = booking.getId();

        defaultBookingShouldBeFound("id.equals=" + id);
        defaultBookingShouldNotBeFound("id.notEquals=" + id);

        defaultBookingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookingShouldNotBeFound("id.greaterThan=" + id);

        defaultBookingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBookingsByEventdateIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where eventdate equals to DEFAULT_EVENTDATE
        defaultBookingShouldBeFound("eventdate.equals=" + DEFAULT_EVENTDATE);

        // Get all the bookingList where eventdate equals to UPDATED_EVENTDATE
        defaultBookingShouldNotBeFound("eventdate.equals=" + UPDATED_EVENTDATE);
    }

    @Test
    @Transactional
    void getAllBookingsByEventdateIsInShouldWork() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where eventdate in DEFAULT_EVENTDATE or UPDATED_EVENTDATE
        defaultBookingShouldBeFound("eventdate.in=" + DEFAULT_EVENTDATE + "," + UPDATED_EVENTDATE);

        // Get all the bookingList where eventdate equals to UPDATED_EVENTDATE
        defaultBookingShouldNotBeFound("eventdate.in=" + UPDATED_EVENTDATE);
    }

    @Test
    @Transactional
    void getAllBookingsByEventdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where eventdate is not null
        defaultBookingShouldBeFound("eventdate.specified=true");

        // Get all the bookingList where eventdate is null
        defaultBookingShouldNotBeFound("eventdate.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where mobile equals to DEFAULT_MOBILE
        defaultBookingShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the bookingList where mobile equals to UPDATED_MOBILE
        defaultBookingShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllBookingsByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultBookingShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the bookingList where mobile equals to UPDATED_MOBILE
        defaultBookingShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllBookingsByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where mobile is not null
        defaultBookingShouldBeFound("mobile.specified=true");

        // Get all the bookingList where mobile is null
        defaultBookingShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByMobileContainsSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where mobile contains DEFAULT_MOBILE
        defaultBookingShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the bookingList where mobile contains UPDATED_MOBILE
        defaultBookingShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllBookingsByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where mobile does not contain DEFAULT_MOBILE
        defaultBookingShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the bookingList where mobile does not contain UPDATED_MOBILE
        defaultBookingShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllBookingsByBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where brand equals to DEFAULT_BRAND
        defaultBookingShouldBeFound("brand.equals=" + DEFAULT_BRAND);

        // Get all the bookingList where brand equals to UPDATED_BRAND
        defaultBookingShouldNotBeFound("brand.equals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllBookingsByBrandIsInShouldWork() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where brand in DEFAULT_BRAND or UPDATED_BRAND
        defaultBookingShouldBeFound("brand.in=" + DEFAULT_BRAND + "," + UPDATED_BRAND);

        // Get all the bookingList where brand equals to UPDATED_BRAND
        defaultBookingShouldNotBeFound("brand.in=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllBookingsByBrandIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where brand is not null
        defaultBookingShouldBeFound("brand.specified=true");

        // Get all the bookingList where brand is null
        defaultBookingShouldNotBeFound("brand.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByBrandContainsSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where brand contains DEFAULT_BRAND
        defaultBookingShouldBeFound("brand.contains=" + DEFAULT_BRAND);

        // Get all the bookingList where brand contains UPDATED_BRAND
        defaultBookingShouldNotBeFound("brand.contains=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllBookingsByBrandNotContainsSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where brand does not contain DEFAULT_BRAND
        defaultBookingShouldNotBeFound("brand.doesNotContain=" + DEFAULT_BRAND);

        // Get all the bookingList where brand does not contain UPDATED_BRAND
        defaultBookingShouldBeFound("brand.doesNotContain=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllBookingsByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where model equals to DEFAULT_MODEL
        defaultBookingShouldBeFound("model.equals=" + DEFAULT_MODEL);

        // Get all the bookingList where model equals to UPDATED_MODEL
        defaultBookingShouldNotBeFound("model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllBookingsByModelIsInShouldWork() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where model in DEFAULT_MODEL or UPDATED_MODEL
        defaultBookingShouldBeFound("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL);

        // Get all the bookingList where model equals to UPDATED_MODEL
        defaultBookingShouldNotBeFound("model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllBookingsByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where model is not null
        defaultBookingShouldBeFound("model.specified=true");

        // Get all the bookingList where model is null
        defaultBookingShouldNotBeFound("model.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByModelContainsSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where model contains DEFAULT_MODEL
        defaultBookingShouldBeFound("model.contains=" + DEFAULT_MODEL);

        // Get all the bookingList where model contains UPDATED_MODEL
        defaultBookingShouldNotBeFound("model.contains=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllBookingsByModelNotContainsSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where model does not contain DEFAULT_MODEL
        defaultBookingShouldNotBeFound("model.doesNotContain=" + DEFAULT_MODEL);

        // Get all the bookingList where model does not contain UPDATED_MODEL
        defaultBookingShouldBeFound("model.doesNotContain=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    void getAllBookingsByEstimatedhoursIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where estimatedhours equals to DEFAULT_ESTIMATEDHOURS
        defaultBookingShouldBeFound("estimatedhours.equals=" + DEFAULT_ESTIMATEDHOURS);

        // Get all the bookingList where estimatedhours equals to UPDATED_ESTIMATEDHOURS
        defaultBookingShouldNotBeFound("estimatedhours.equals=" + UPDATED_ESTIMATEDHOURS);
    }

    @Test
    @Transactional
    void getAllBookingsByEstimatedhoursIsInShouldWork() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where estimatedhours in DEFAULT_ESTIMATEDHOURS or UPDATED_ESTIMATEDHOURS
        defaultBookingShouldBeFound("estimatedhours.in=" + DEFAULT_ESTIMATEDHOURS + "," + UPDATED_ESTIMATEDHOURS);

        // Get all the bookingList where estimatedhours equals to UPDATED_ESTIMATEDHOURS
        defaultBookingShouldNotBeFound("estimatedhours.in=" + UPDATED_ESTIMATEDHOURS);
    }

    @Test
    @Transactional
    void getAllBookingsByEstimatedhoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where estimatedhours is not null
        defaultBookingShouldBeFound("estimatedhours.specified=true");

        // Get all the bookingList where estimatedhours is null
        defaultBookingShouldNotBeFound("estimatedhours.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingsByEstimatedhoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where estimatedhours is greater than or equal to DEFAULT_ESTIMATEDHOURS
        defaultBookingShouldBeFound("estimatedhours.greaterThanOrEqual=" + DEFAULT_ESTIMATEDHOURS);

        // Get all the bookingList where estimatedhours is greater than or equal to UPDATED_ESTIMATEDHOURS
        defaultBookingShouldNotBeFound("estimatedhours.greaterThanOrEqual=" + UPDATED_ESTIMATEDHOURS);
    }

    @Test
    @Transactional
    void getAllBookingsByEstimatedhoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where estimatedhours is less than or equal to DEFAULT_ESTIMATEDHOURS
        defaultBookingShouldBeFound("estimatedhours.lessThanOrEqual=" + DEFAULT_ESTIMATEDHOURS);

        // Get all the bookingList where estimatedhours is less than or equal to SMALLER_ESTIMATEDHOURS
        defaultBookingShouldNotBeFound("estimatedhours.lessThanOrEqual=" + SMALLER_ESTIMATEDHOURS);
    }

    @Test
    @Transactional
    void getAllBookingsByEstimatedhoursIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where estimatedhours is less than DEFAULT_ESTIMATEDHOURS
        defaultBookingShouldNotBeFound("estimatedhours.lessThan=" + DEFAULT_ESTIMATEDHOURS);

        // Get all the bookingList where estimatedhours is less than UPDATED_ESTIMATEDHOURS
        defaultBookingShouldBeFound("estimatedhours.lessThan=" + UPDATED_ESTIMATEDHOURS);
    }

    @Test
    @Transactional
    void getAllBookingsByEstimatedhoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where estimatedhours is greater than DEFAULT_ESTIMATEDHOURS
        defaultBookingShouldNotBeFound("estimatedhours.greaterThan=" + DEFAULT_ESTIMATEDHOURS);

        // Get all the bookingList where estimatedhours is greater than SMALLER_ESTIMATEDHOURS
        defaultBookingShouldBeFound("estimatedhours.greaterThan=" + SMALLER_ESTIMATEDHOURS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookingShouldBeFound(String filter) throws Exception {
        restBookingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(booking.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventdate").value(hasItem(DEFAULT_EVENTDATE.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].estimatedhours").value(hasItem(sameNumber(DEFAULT_ESTIMATEDHOURS))))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));

        // Check, that the count call also returns 1
        restBookingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookingShouldNotBeFound(String filter) throws Exception {
        restBookingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBooking() throws Exception {
        // Get the booking
        restBookingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBooking() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking
        Booking updatedBooking = bookingRepository.findById(booking.getId()).get();
        // Disconnect from session so that the updates on updatedBooking are not directly saved in db
        em.detach(updatedBooking);
        updatedBooking
            .eventdate(UPDATED_EVENTDATE)
            .mobile(UPDATED_MOBILE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .estimatedhours(UPDATED_ESTIMATEDHOURS)
            .note(UPDATED_NOTE);

        restBookingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBooking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBooking))
            )
            .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getEventdate()).isEqualTo(UPDATED_EVENTDATE);
        assertThat(testBooking.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testBooking.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testBooking.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testBooking.getEstimatedhours()).isEqualByComparingTo(UPDATED_ESTIMATEDHOURS);
        assertThat(testBooking.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, booking.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(booking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(booking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookingWithPatch() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking using partial update
        Booking partialUpdatedBooking = new Booking();
        partialUpdatedBooking.setId(booking.getId());

        partialUpdatedBooking.mobile(UPDATED_MOBILE).estimatedhours(UPDATED_ESTIMATEDHOURS).note(UPDATED_NOTE);

        restBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBooking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBooking))
            )
            .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getEventdate()).isEqualTo(DEFAULT_EVENTDATE);
        assertThat(testBooking.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testBooking.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testBooking.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testBooking.getEstimatedhours()).isEqualByComparingTo(UPDATED_ESTIMATEDHOURS);
        assertThat(testBooking.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateBookingWithPatch() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking using partial update
        Booking partialUpdatedBooking = new Booking();
        partialUpdatedBooking.setId(booking.getId());

        partialUpdatedBooking
            .eventdate(UPDATED_EVENTDATE)
            .mobile(UPDATED_MOBILE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .estimatedhours(UPDATED_ESTIMATEDHOURS)
            .note(UPDATED_NOTE);

        restBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBooking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBooking))
            )
            .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getEventdate()).isEqualTo(UPDATED_EVENTDATE);
        assertThat(testBooking.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testBooking.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testBooking.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testBooking.getEstimatedhours()).isEqualByComparingTo(UPDATED_ESTIMATEDHOURS);
        assertThat(testBooking.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, booking.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(booking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(booking))
            )
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();
        booking.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBooking() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        int databaseSizeBeforeDelete = bookingRepository.findAll().size();

        // Delete the booking
        restBookingMockMvc
            .perform(delete(ENTITY_API_URL_ID, booking.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
