package com.edchantalsefaz.apibank.service;

import com.edchantalsefaz.apibank.service.dto.SolicabertContaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.edchantalsefaz.apibank.domain.SolicabertConta}.
 */
public interface SolicabertContaService {

    /**
     * Save a solicabertConta.
     *
     * @param solicabertContaDTO the entity to save.
     * @return the persisted entity.
     */
    SolicabertContaDTO save(SolicabertContaDTO solicabertContaDTO);

    /**
     * Get all the solicabertContas.
     *
     * @return the list of entities.
     */
    List<SolicabertContaDTO> findAll();


    /**
     * Get the "id" solicabertConta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SolicabertContaDTO> findOne(Long id);

    /**
     * Delete the "id" solicabertConta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    boolean cpf(String strCpf);
}
