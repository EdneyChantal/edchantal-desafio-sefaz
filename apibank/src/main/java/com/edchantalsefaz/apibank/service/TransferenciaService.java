package com.edchantalsefaz.apibank.service;

import com.edchantalsefaz.apibank.service.dto.TransferenciaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.edchantalsefaz.apibank.domain.Transferencia}.
 */
public interface TransferenciaService {

    /**
     * Save a transferencia.
     *
     * @param transferenciaDTO the entity to save.
     * @return the persisted entity.
     */
    TransferenciaDTO save(TransferenciaDTO transferenciaDTO);

    /**
     * Get all the transferencias.
     *
     * @return the list of entities.
     */
    List<TransferenciaDTO> findAll();


    /**
     * Get the "id" transferencia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransferenciaDTO> findOne(Long id);

    /**
     * Delete the "id" transferencia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
