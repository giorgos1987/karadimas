package com.karadimas.tyres.service;

import com.karadimas.tyres.domain.Neworders;
import com.karadimas.tyres.repository.NewordersRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Neworders}.
 */
@Service
@Transactional
public class NewordersService {

    private final Logger log = LoggerFactory.getLogger(NewordersService.class);

    private final NewordersRepository newordersRepository;

    public NewordersService(NewordersRepository newordersRepository) {
        this.newordersRepository = newordersRepository;
    }

    /**
     * Save a neworders.
     *
     * @param neworders the entity to save.
     * @return the persisted entity.
     */
    public Neworders save(Neworders neworders) {
        log.debug("Request to save Neworders : {}", neworders);
        return newordersRepository.save(neworders);
    }

    /**
     * Update a neworders.
     *
     * @param neworders the entity to save.
     * @return the persisted entity.
     */
    public Neworders update(Neworders neworders) {
        log.debug("Request to save Neworders : {}", neworders);
        return newordersRepository.save(neworders);
    }

    /**
     * Partially update a neworders.
     *
     * @param neworders the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Neworders> partialUpdate(Neworders neworders) {
        log.debug("Request to partially update Neworders : {}", neworders);

        return newordersRepository
            .findById(neworders.getId())
            .map(existingNeworders -> {
                if (neworders.getOrderDate() != null) {
                    existingNeworders.setOrderDate(neworders.getOrderDate());
                }
                if (neworders.getElastika1() != null) {
                    existingNeworders.setElastika1(neworders.getElastika1());
                }
                if (neworders.getElastika2() != null) {
                    existingNeworders.setElastika2(neworders.getElastika2());
                }
                if (neworders.getElastika3() != null) {
                    existingNeworders.setElastika3(neworders.getElastika3());
                }
                if (neworders.getElastika4() != null) {
                    existingNeworders.setElastika4(neworders.getElastika4());
                }
                if (neworders.getElastika5() != null) {
                    existingNeworders.setElastika5(neworders.getElastika5());
                }
                if (neworders.getElastika6() != null) {
                    existingNeworders.setElastika6(neworders.getElastika6());
                }
                if (neworders.getElastika7() != null) {
                    existingNeworders.setElastika7(neworders.getElastika7());
                }
                if (neworders.getElastika8() != null) {
                    existingNeworders.setElastika8(neworders.getElastika8());
                }
                if (neworders.getElastika9() != null) {
                    existingNeworders.setElastika9(neworders.getElastika9());
                }
                if (neworders.getElastika10() != null) {
                    existingNeworders.setElastika10(neworders.getElastika10());
                }
                if (neworders.getElastika11() != null) {
                    existingNeworders.setElastika11(neworders.getElastika11());
                }
                if (neworders.getElastika12() != null) {
                    existingNeworders.setElastika12(neworders.getElastika12());
                }
                if (neworders.getElastika13() != null) {
                    existingNeworders.setElastika13(neworders.getElastika13());
                }

                return existingNeworders;
            })
            .map(newordersRepository::save);
    }

    /**
     * Get all the neworders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Neworders> findAll(Pageable pageable) {
        log.debug("Request to get all Neworders");
        return newordersRepository.findAll(pageable);
    }

    /**
     * Get one neworders by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Neworders> findOne(Long id) {
        log.debug("Request to get Neworders : {}", id);
        return newordersRepository.findById(id);
    }

    /**
     * Delete the neworders by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Neworders : {}", id);
        newordersRepository.deleteById(id);
    }
}
