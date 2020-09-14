package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.service.TransferenciaService;
import com.edchantalsefaz.apibank.service.AccountBankService;
import com.edchantalsefaz.apibank.service.ParametroService;
import com.edchantalsefaz.apibank.web.rest.errors.BadRequestAlertException;
import com.edchantalsefaz.apibank.service.dto.ParametroDTO;
import com.edchantalsefaz.apibank.service.dto.TransferenciaDTO;

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
 * REST controller for managing {@link com.edchantalsefaz.apibank.domain.Transferencia}.
 */
@RestController
@RequestMapping("/api")
public class TransferenciaResource {

    private final Logger log = LoggerFactory.getLogger(TransferenciaResource.class);

    private static final String ENTITY_NAME = "apibankTransferencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferenciaService transferenciaService;
    private final AccountBankService accountBankService;
    private final ParametroService   parametroService;


    public TransferenciaResource(TransferenciaService transferenciaService,AccountBankService accountBankService,ParametroService parametroService) {
        this.transferenciaService = transferenciaService;
        this.accountBankService = accountBankService;
        this.parametroService = parametroService;
    }

    /**
     * {@code POST  /transferencias} : Create a new transferencia.
     *
     * @param transferenciaDTO the transferenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferenciaDTO, or with status {@code 400 (Bad Request)} if the transferencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transferencias")
    public ResponseEntity<TransferenciaDTO> createTransferencia(@Valid @RequestBody TransferenciaDTO transferenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Transferencia : {}", transferenciaDTO);
        if (transferenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferencia cannot already have an ID", ENTITY_NAME, "idexists");
        }

        String resp = parametroService.valorMaximo(transferenciaDTO.getValor());
        if (resp=="missinParameter") {
            throw new BadRequestAlertException("Falta Informar Parâmetro do Sistema", ENTITY_NAME, "missinParameter");
        }
        if (resp=="valuemaxoperation") {
            throw new BadRequestAlertException("Valor acima do limite por operação", ENTITY_NAME, "valuemaxoperation");  
        }

        if (transferenciaDTO.getAccountSaqueId()==transferenciaDTO.getAccountDepositoId()) {
           throw new BadRequestAlertException("Conta de Saque e Deposito não pode ser iguais", ENTITY_NAME, "accountequals");  
        }

        String respSaque =  accountBankService.saque(transferenciaDTO.getAccountSaqueId(), transferenciaDTO.getValor());
 
        if (respSaque == "accountinexist"){
            throw new BadRequestAlertException("Conta Bancária Inexistente", ENTITY_NAME, "accountinexist");
        }
        if (respSaque =="insulficientfunds") {
            throw new BadRequestAlertException("Conta de Saque Sem Fundos", ENTITY_NAME, "insulficientfunds");
        }

        String respDepo=  accountBankService.deposito(transferenciaDTO.getAccountDepositoId(), transferenciaDTO.getValor());
 
        if (respDepo == "accountinexist"){
            throw new BadRequestAlertException("Conta Bancária Inexistente", ENTITY_NAME, "accountinexist");
        }
        
        TransferenciaDTO result = transferenciaService.save(transferenciaDTO);
        return ResponseEntity.created(new URI("/api/transferencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transferencias} : Updates an existing transferencia.
     *
     * @param transferenciaDTO the transferenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferenciaDTO,
     * or with status {@code 400 (Bad Request)} if the transferenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transferencias")
    public ResponseEntity<TransferenciaDTO> updateTransferencia(@Valid @RequestBody TransferenciaDTO transferenciaDTO) throws URISyntaxException {
        log.debug("REST request to update Transferencia : {}", transferenciaDTO);
        if (transferenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransferenciaDTO result = transferenciaService.save(transferenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transferencias} : get all the transferencias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferencias in body.
     */
    @GetMapping("/transferencias")
    public List<TransferenciaDTO> getAllTransferencias() {
        log.debug("REST request to get all Transferencias");
        return transferenciaService.findAll();
    }

    /**
     * {@code GET  /transferencias/:id} : get the "id" transferencia.
     *
     * @param id the id of the transferenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transferencias/{id}")
    public ResponseEntity<TransferenciaDTO> getTransferencia(@PathVariable Long id) {
        log.debug("REST request to get Transferencia : {}", id);
        Optional<TransferenciaDTO> transferenciaDTO = transferenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferenciaDTO);
    }

    /**
     * {@code DELETE  /transferencias/:id} : delete the "id" transferencia.
     *
     * @param id the id of the transferenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transferencias/{id}")
    public ResponseEntity<Void> deleteTransferencia(@PathVariable Long id) {
        log.debug("REST request to delete Transferencia : {}", id);
        transferenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
