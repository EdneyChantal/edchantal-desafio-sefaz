package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.service.SaqueService;
import com.edchantalsefaz.apibank.service.AccountBankService;
import com.edchantalsefaz.apibank.service.ParametroService;
import com.edchantalsefaz.apibank.web.rest.errors.BadRequestAlertException;
import com.edchantalsefaz.apibank.service.dto.AccountBankDTO;
import com.edchantalsefaz.apibank.service.dto.SaqueDTO;

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
 * REST controller for managing {@link com.edchantalsefaz.apibank.domain.Saque}.
 */
@RestController
@RequestMapping("/api")
public class SaqueResource {

    private final Logger log = LoggerFactory.getLogger(SaqueResource.class);

    private static final String ENTITY_NAME = "apibankSaque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SaqueService saqueService;
    private final AccountBankService accountBankService;
    private final ParametroService   parametroService;

    public SaqueResource(SaqueService saqueService,AccountBankService accountBankService,ParametroService parametroService) {
        this.saqueService = saqueService;
        this.accountBankService = accountBankService;
        this.parametroService = parametroService;
    }

    /**
     * {@code POST  /saques} : Create a new saque.
     *
     * @param saqueDTO the saqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new saqueDTO, or with status {@code 400 (Bad Request)} if the saque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/saques")
    public ResponseEntity<SaqueDTO> createSaque(@Valid @RequestBody SaqueDTO saqueDTO) throws URISyntaxException {
        log.debug("REST request to save Saque : {}", saqueDTO);
        if (saqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new saque cannot already have an ID", ENTITY_NAME, "idexists");
        }

        String res = accountBankService.saque(saqueDTO.getAccountId(), saqueDTO.getValor());
      
        if (res == "accountinexist"){
            throw new BadRequestAlertException("Conta Bancária Inexistente", ENTITY_NAME, "accountinexist");
        }
        if (res=="insulficientfunds") {
            throw new BadRequestAlertException("Conta Bancária Inexistente", ENTITY_NAME, "insulficientfunds");
        }
       SaqueDTO result = saqueService.save(saqueDTO);
        return ResponseEntity.created(new URI("/api/saques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /saques} : Updates an existing saque.
     *
     * @param saqueDTO the saqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated saqueDTO,
     * or with status {@code 400 (Bad Request)} if the saqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the saqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/saques")
    public ResponseEntity<SaqueDTO> updateSaque(@Valid @RequestBody SaqueDTO saqueDTO) throws URISyntaxException {
        log.debug("REST request to update Saque : {}", saqueDTO);
        if (saqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SaqueDTO result = saqueService.save(saqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, saqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /saques} : get all the saques.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saques in body.
     */
    @GetMapping("/saques")
    public List<SaqueDTO> getAllSaques() {
        log.debug("REST request to get all Saques");
        return saqueService.findAll();
    }

    /**
     * {@code GET  /saques/:id} : get the "id" saque.
     *
     * @param id the id of the saqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the saqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/saques/{id}")
    public ResponseEntity<SaqueDTO> getSaque(@PathVariable Long id) {
        log.debug("REST request to get Saque : {}", id);
        Optional<SaqueDTO> saqueDTO = saqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(saqueDTO);
    }

    /**
     * {@code DELETE  /saques/:id} : delete the "id" saque.
     *
     * @param id the id of the saqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/saques/{id}")
    public ResponseEntity<Void> deleteSaque(@PathVariable Long id) {
        log.debug("REST request to delete Saque : {}", id);
        saqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
