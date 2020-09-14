package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.service.AccountBankService;
import com.edchantalsefaz.apibank.web.rest.errors.BadRequestAlertException;
import com.edchantalsefaz.apibank.service.dto.AccountBankDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.edchantalsefaz.apibank.domain.AccountBank}.
 */
@RestController
@RequestMapping("/api")
public class AccountBankResource {

    private final Logger log = LoggerFactory.getLogger(AccountBankResource.class);

    private static final String ENTITY_NAME = "apibankAccountBank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountBankService accountBankService;

    public AccountBankResource(AccountBankService accountBankService) {
        this.accountBankService = accountBankService;
    }

    /**
     * {@code POST  /account-banks} : Create a new accountBank.
     *
     * @param accountBankDTO the accountBankDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountBankDTO, or with status {@code 400 (Bad Request)} if the accountBank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-banks")
    public ResponseEntity<AccountBankDTO> createAccountBank(@Valid @RequestBody AccountBankDTO accountBankDTO) throws URISyntaxException {
        log.debug("REST request to save AccountBank : {}", accountBankDTO);
        if (accountBankDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountBank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountBankDTO result = accountBankService.save(accountBankDTO);
        return ResponseEntity.created(new URI("/api/account-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-banks} : Updates an existing accountBank.
     *
     * @param accountBankDTO the accountBankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountBankDTO,
     * or with status {@code 400 (Bad Request)} if the accountBankDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountBankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-banks")
    public ResponseEntity<AccountBankDTO> updateAccountBank(@Valid @RequestBody AccountBankDTO accountBankDTO) throws URISyntaxException {
        log.debug("REST request to update AccountBank : {}", accountBankDTO);
        if (accountBankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountBankDTO result = accountBankService.save(accountBankDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountBankDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /account-banks} : get all the accountBanks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountBanks in body.
     */
    @GetMapping("/account-banks")
    public List<AccountBankDTO> getAllAccountBanks() {
        log.debug("REST request to get all AccountBanks");
        return accountBankService.findAll();
    }

    /**
     * {@code GET  /account-banks/:id} : get the "id" accountBank.
     *
     * @param id the id of the accountBankDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountBankDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-banks/{id}")
    public ResponseEntity<AccountBankDTO> getAccountBank(@PathVariable Long id) {
        log.debug("REST request to get AccountBank : {}", id);
        Optional<AccountBankDTO> accountBankDTO = accountBankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountBankDTO);
    }

    /**
     * {@code DELETE  /account-banks/:id} : delete the "id" accountBank.
     *
     * @param id the id of the accountBankDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-banks/{id}")
    public ResponseEntity<Void> deleteAccountBank(@PathVariable Long id) {
        log.debug("REST request to delete AccountBank : {}", id);
        accountBankService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
