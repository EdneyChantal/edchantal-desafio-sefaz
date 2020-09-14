package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.service.AccountBankService;
import com.edchantalsefaz.apibank.service.ParametroService;
import com.edchantalsefaz.apibank.service.PersonService;
import com.edchantalsefaz.apibank.service.SolicabertContaService;
import com.edchantalsefaz.apibank.web.rest.errors.BadRequestAlertException;
import com.edchantalsefaz.apibank.service.dto.PersonDTO;
import com.edchantalsefaz.apibank.service.dto.SolicabertContaDTO;
import com.edchantalsefaz.apibank.service.dto.AccountBankDTO;
import com.edchantalsefaz.apibank.service.dto.ParametroDTO;

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
import java.util.LinkedList;
import java.util.Random;

/**
 * REST controller for managing {@link com.edchantalsefaz.apibank.domain.SolicabertConta}.
 */
@RestController
@RequestMapping("/api")
public class SolicabertContaResource {

    private final Logger log = LoggerFactory.getLogger(SolicabertContaResource.class);

    private static final String ENTITY_NAME = "apibankSolicabertConta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicabertContaService solicabertContaService;
    private final PersonService    personService;
    private final ParametroService parametroService; 
    private final AccountBankService accountbankService;

    public SolicabertContaResource(SolicabertContaService solicabertContaService,PersonService personService,ParametroService parametroService, AccountBankService accountbankService) {
        this.solicabertContaService = solicabertContaService;
        this.personService = personService ;
        this.parametroService = parametroService ;
        this.accountbankService = accountbankService;
    }

    /**
     * {@code POST  /solicabert-contas} : Create a new solicabertConta.
     *
     * @param solicabertContaDTO the solicabertContaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicabertContaDTO, or with status {@code 400 (Bad Request)} if the solicabertConta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicabert-contas")
    public ResponseEntity<SolicabertContaDTO> createSolicabertConta(@Valid @RequestBody SolicabertContaDTO solicabertContaDTO) throws URISyntaxException {
        log.debug("REST request to save SolicabertConta : {}", solicabertContaDTO);
        if (solicabertContaDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicabertConta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // tem parametro ?


        String resp = parametroService.valorMinimo(solicabertContaDTO.getSaldoinicial());

        
        
        if (resp=="missinParameter") {
            throw new BadRequestAlertException("Falta Informar Parâmetro do Sistema", ENTITY_NAME, "missinParameter");
        }
        if (resp=="vlrundermin") {
            throw new BadRequestAlertException("Valor menor que o mínimo para abertura de Contas", ENTITY_NAME, "vlrundermin");
        }

        if (!this.solicabertContaService.cpf(solicabertContaDTO.getCpf().toString())) {
            throw new BadRequestAlertException("CPF informado para criação de conta está inválido.", ENTITY_NAME, "cpfinvalid");


        }



        // grava o Cliente----
        Optional<PersonDTO> oppersonDTO = this.personService.findByCpf(solicabertContaDTO.getCpf());
        if (oppersonDTO.isEmpty()) {
          PersonDTO personDTO = new PersonDTO();
          personDTO.setCpf(solicabertContaDTO.getCpf());
          personDTO.setNome(solicabertContaDTO.getNome());
          PersonDTO presunt= this.personService.save(personDTO);
          oppersonDTO = Optional.ofNullable(presunt);
        }
        Random random = new Random();
        AccountBankDTO accountD = new AccountBankDTO();
        accountD.setPersonId(oppersonDTO.get().getId());
        accountD.setSaldo(solicabertContaDTO.getSaldoinicial());
        accountD.setNumeroConta( Math.abs(random.nextLong()) );
        accountbankService.save(accountD);
        solicabertContaDTO.setStatus("Conta cadastrada com sucesso Número "+accountD.getNumeroConta());

        SolicabertContaDTO result = solicabertContaService.save(solicabertContaDTO);
        return ResponseEntity.created(new URI("/api/solicabert-contas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicabert-contas} : Updates an existing solicabertConta.
     *
     * @param solicabertContaDTO the solicabertContaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicabertContaDTO,
     * or with status {@code 400 (Bad Request)} if the solicabertContaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicabertContaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicabert-contas")
    public ResponseEntity<SolicabertContaDTO> updateSolicabertConta(@Valid @RequestBody SolicabertContaDTO solicabertContaDTO) throws URISyntaxException {
        log.debug("REST request to update SolicabertConta : {}", solicabertContaDTO);
        if (solicabertContaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolicabertContaDTO result = solicabertContaService.save(solicabertContaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, solicabertContaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /solicabert-contas} : get all the solicabertContas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicabertContas in body.
     */
    @GetMapping("/solicabert-contas")
    public List<SolicabertContaDTO> getAllSolicabertContas() {
        log.debug("REST request to get all SolicabertContas");
        return solicabertContaService.findAll();
    }

    /**
     * {@code GET  /solicabert-contas/:id} : get the "id" solicabertConta.
     *
     * @param id the id of the solicabertContaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicabertContaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicabert-contas/{id}")
    public ResponseEntity<SolicabertContaDTO> getSolicabertConta(@PathVariable Long id) {
        log.debug("REST request to get SolicabertConta : {}", id);
        Optional<SolicabertContaDTO> solicabertContaDTO = solicabertContaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicabertContaDTO);
    }

    /**
     * {@code DELETE  /solicabert-contas/:id} : delete the "id" solicabertConta.
     *
     * @param id the id of the solicabertContaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicabert-contas/{id}")
    public ResponseEntity<Void> deleteSolicabertConta(@PathVariable Long id) {
        log.debug("REST request to delete SolicabertConta : {}", id);
        solicabertContaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
