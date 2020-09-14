package com.edchantalsefaz.apibank.service;

import com.edchantalsefaz.apibank.service.dto.SaqueDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.edchantalsefaz.apibank.domain.Saque}.
 */
public interface SaqueService {

    /**
     * Save a saque.
     *
     * @param saqueDTO the entity to save.
     * @return the persisted entity.
     */
    SaqueDTO save(SaqueDTO saqueDTO);

    /**
     * Get all the saques.
     *
     * @return the list of entities.
     */
    List<SaqueDTO> findAll();


    /**
     * Get the "id" saque.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SaqueDTO> findOne(Long id);

    /**
     * Delete the "id" saque.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
