package com.edchantalsefaz.apibank.repository;

import com.edchantalsefaz.apibank.domain.Deposito;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Deposito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {
}
