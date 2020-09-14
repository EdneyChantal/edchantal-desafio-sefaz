package com.edchantalsefaz.apibank.repository;

import java.math.BigInteger;

import com.edchantalsefaz.apibank.domain.Person;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data  repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
 
   @Query(value="Select u from Person u where u.cpf = ?1")
   Person findByCpf(Long cpf);
}
