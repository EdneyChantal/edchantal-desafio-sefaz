package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.domain.AccountBank;
import com.edchantalsefaz.apibank.service.AccountBankService;
import com.edchantalsefaz.apibank.service.DepositoService;
import com.edchantalsefaz.apibank.web.rest.errors.BadRequestAlertException;
import com.edchantalsefaz.apibank.service.dto.AccountBankDTO;
import com.edchantalsefaz.apibank.service.dto.DepositoDTO;

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
 * REST controller for managing {@link com.edchantalsefaz.apibank.domain.Deposito}.
 */
@RestController
@RequestMapping("/api")
public class DepositoResource {

    private final Logger log = LoggerFactory.getLogger(DepositoResource.class);

    private static final String ENTITY_NAME = "apibankDeposito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepositoService depositoService;
    private final AccountBankService accountBankService; 

    public DepositoResource(DepositoService depositoService, AccountBankService accountBankService) {
        this.depositoService = depositoService;
        this.accountBankService = accountBankService;
    }

    /**
     * {@code POST  /depositos} : Create a new deposito.
     *
     * @param depositoDTO the depositoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depositoDTO, or with status {@code 400 (Bad Request)} if the deposito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/depositos")
    public ResponseEntity<DepositoDTO> createDeposito(@Valid @RequestBody DepositoDTO depositoDTO) throws URISyntaxException {
        log.debug("REST request to save Deposito : {}", depositoDTO);
        if (depositoDTO.getId() != null) {
            throw new BadRequestAlertException("A new deposito cannot already have an ID", ENTITY_NAME, "idexists");
        }

        String res = accountBankService.deposito(depositoDTO.getAccountId(), depositoDTO.getValor());
        if (res=="accountinexist"){
            throw new BadRequestAlertException("Conta Bancária Inexistente", ENTITY_NAME, "accountinexist");
        }

        /*Optional<AccountBankDTO> opAccountbDTO = accountBankService.findOne(depositoDTO.getAccountId());
        if (opAccountbDTO.isEmpty()){
            throw new BadRequestAlertException("Conta Bancária Inexistente", ENTITY_NAME, "accountinexist");
        }
        AccountBankDTO accountDTO = opAccountbDTO.get();
        
        accountDTO.setSaldo(accountDTO.getSaldo()+depositoDTO.getValor());

        accountBankService.save(accountDTO);*/

        DepositoDTO result = depositoService.save(depositoDTO);
        return ResponseEntity.created(new URI("/api/depositos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /depositos} : Updates an existing deposito.
     *
     * @param depositoDTO the depositoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depositoDTO,
     * or with status {@code 400 (Bad Request)} if the depositoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depositoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/depositos")
    public ResponseEntity<DepositoDTO> updateDeposito(@Valid @RequestBody DepositoDTO depositoDTO) throws URISyntaxException {
        log.debug("REST request to update Deposito : {}", depositoDTO);
        if (depositoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepositoDTO result = depositoService.save(depositoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depositoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /depositos} : get all the depositos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depositos in body.
     */
    @GetMapping("/depositos")
    public List<DepositoDTO> getAllDepositos() {
        log.debug("REST request to get all Depositos");
        return depositoService.findAll();
    }

    /**
     * {@code GET  /depositos/:id} : get the "id" deposito.
     *
     * @param id the id of the depositoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depositoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/depositos/{id}")
    public ResponseEntity<DepositoDTO> getDeposito(@PathVariable Long id) {
        log.debug("REST request to get Deposito : {}", id);
        Optional<DepositoDTO> depositoDTO = depositoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depositoDTO);
    }

    /**
     * {@code DELETE  /depositos/:id} : delete the "id" deposito.
     *
     * @param id the id of the depositoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/depositos/{id}")
    public ResponseEntity<Void> deleteDeposito(@PathVariable Long id) {
        log.debug("REST request to delete Deposito : {}", id);
        
        
        
        
        
        depositoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
