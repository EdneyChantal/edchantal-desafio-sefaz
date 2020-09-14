package com.edchantalsefaz.apibank.service;

import com.edchantalsefaz.apibank.service.dto.ParametroDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.edchantalsefaz.apibank.domain.Parametro}.
 */
public interface ParametroService {

    /**
     * Save a parametro.
     *
     * @param parametroDTO the entity to save.
     * @return the persisted entity.
     */
    ParametroDTO save(ParametroDTO parametroDTO);

    /**
     * Get all the parametros.
     *
     * @return the list of entities.
     */
    List<ParametroDTO> findAll();


    /**
     * Get the "id" parametro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParametroDTO> findOne(Long id);

    /**
     * Delete the "id" parametro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    String valorMinimo(Double pvalor);
    String valorMaximo(Double pvalor);
}
