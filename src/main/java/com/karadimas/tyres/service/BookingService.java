package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.Booking;
import com.karadimas.tyres.repository.BookingRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Booking}.
 */
@Service
@Transactional
public class BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Save a booking.
     *
     * @param booking the entity to save.
     * @return the persisted entity.
     */
    public Booking save(Booking booking) {
        log.debug("Request to save Booking : {}", booking);
        return bookingRepository.save(booking);
    }

    /**
     * Update a booking.
     *
     * @param booking the entity to save.
     * @return the persisted entity.
     */
    public Booking update(Booking booking) {
        log.debug("Request to save Booking : {}", booking);
        return bookingRepository.save(booking);
    }

    /**
     * Partially update a booking.
     *
     * @param booking the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Booking> partialUpdate(Booking booking) {
        log.debug("Request to partially update Booking : {}", booking);

        return bookingRepository
            .findById(booking.getId())
            .map(existingBooking -> {
                if (booking.getEventdate() != null) {
                    existingBooking.setEventdate(booking.getEventdate());
                }
                if (booking.getMobile() != null) {
                    existingBooking.setMobile(booking.getMobile());
                }
                if (booking.getBrand() != null) {
                    existingBooking.setBrand(booking.getBrand());
                }
                if (booking.getModel() != null) {
                    existingBooking.setModel(booking.getModel());
                }
                if (booking.getEstimatedhours() != null) {
                    existingBooking.setEstimatedhours(booking.getEstimatedhours());
                }
                if (booking.getNote() != null) {
                    existingBooking.setNote(booking.getNote());
                }

                return existingBooking;
            })
            .map(bookingRepository::save);
    }

    /**
     * Get all the bookings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        log.debug("Request to get all Bookings");
        return bookingRepository.findAll();
    }

    /**
     * Get one booking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Booking> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findById(id);
    }

    /**
     * Delete the booking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
    }
}
