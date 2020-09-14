package com.edchantalsefaz.apibank.service;

import com.edchantalsefaz.apibank.service.dto.PersonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.edchantalsefaz.apibank.domain.Person}.
 */
public interface PersonService {

    /**
     * Save a person.
     *
     * @param personDTO the entity to save.
     * @return the persisted entity.
     */
    PersonDTO save(PersonDTO personDTO);

    /**
     * Get all the people.
     *
     * @return the list of entities.
     */
    List<PersonDTO> findAll();


    /**
     * Get the "id" person.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonDTO> findOne(Long id);

    Optional<PersonDTO> findByCpf(Long id);

    /**
     * Delete the "id" person.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
