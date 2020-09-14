package com.edchantalsefaz.apibank.repository;

import com.edchantalsefaz.apibank.domain.SolicabertConta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SolicabertConta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicabertContaRepository extends JpaRepository<SolicabertConta, Long> {
}
