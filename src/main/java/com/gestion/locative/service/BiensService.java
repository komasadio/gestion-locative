package com.gestion.locative.service;

import com.gestion.locative.service.dto.BiensDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gestion.locative.domain.Biens}.
 */
public interface BiensService {

    /**
     * Save a biens.
     *
     * @param biensDTO the entity to save.
     * @return the persisted entity.
     */
    BiensDTO save(BiensDTO biensDTO);

    /**
     * Get all the biens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BiensDTO> findAll(Pageable pageable);


    /**
     * Get the "id" biens.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BiensDTO> findOne(Long id);

    /**
     * Delete the "id" biens.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
