package com.edchantalsefaz.apibank.repository;

import com.edchantalsefaz.apibank.domain.Transferencia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Transferencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
}
