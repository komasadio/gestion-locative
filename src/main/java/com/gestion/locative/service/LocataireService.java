package com.gestion.locative.service;

import com.gestion.locative.service.dto.LocataireDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.gestion.locative.domain.Locataire}.
 */
public interface LocataireService {

    /**
     * Save a locataire.
     *
     * @param locataireDTO the entity to save.
     * @return the persisted entity.
     */
    LocataireDTO save(LocataireDTO locataireDTO);

    /**
     * Get all the locataires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LocataireDTO> findAll(Pageable pageable);


    /**
     * Get the "id" locataire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocataireDTO> findOne(Long id);

    /**
     * Delete the "id" locataire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
