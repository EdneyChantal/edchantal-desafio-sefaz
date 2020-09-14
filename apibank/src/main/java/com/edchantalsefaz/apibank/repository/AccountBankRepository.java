package com.edchantalsefaz.apibank.repository;

import com.edchantalsefaz.apibank.domain.AccountBank;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AccountBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountBankRepository extends JpaRepository<AccountBank, Long> {
}
