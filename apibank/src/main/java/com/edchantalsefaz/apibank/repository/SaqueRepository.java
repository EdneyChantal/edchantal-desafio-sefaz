package com.edchantalsefaz.apibank.repository;

import com.edchantalsefaz.apibank.domain.Saque;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Saque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaqueRepository extends JpaRepository<Saque, Long> {
}
