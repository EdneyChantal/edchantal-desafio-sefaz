package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.service.ParametroService;
import com.edchantalsefaz.apibank.web.rest.errors.BadRequestAlertException;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.edchantalsefaz.apibank.domain.Parametro}.
 */
@RestController
@RequestMapping("/api")
public class ParametroResource {

    private final Logger log = LoggerFactory.getLogger(ParametroResource.class);

    private static final String ENTITY_NAME = "apibankParametro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametroService parametroService;

    public ParametroResource(ParametroService parametroService) {
        this.parametroService = parametroService;
    }

    /**
     * {@code POST  /parametros} : Create a new parametro.
     *
     * @param parametroDTO the parametroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametroDTO, or with status {@code 400 (Bad Request)} if the parametro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parametros")
    public ResponseEntity<ParametroDTO> createParametro(@Valid @RequestBody ParametroDTO parametroDTO) throws URISyntaxException {
        log.debug("REST request to save Parametro : {}", parametroDTO);
        if (parametroDTO.getId() != null) {
            throw new BadRequestAlertException("A new parametro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // garante que só irá ter um registro de parâmetro 
        LinkedList<ParametroDTO> list = (LinkedList) parametroService.findAll();
        if (list.size()>=1) {
            throw new BadRequestAlertException("Parametro Só pode Ter 1 Registro",ENTITY_NAME,"onlyRegister");
        }
  
 
        ParametroDTO result = parametroService.save(parametroDTO);
        return ResponseEntity.created(new URI("/api/parametros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parametros} : Updates an existing parametro.
     *
     * @param parametroDTO the parametroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametroDTO,
     * or with status {@code 400 (Bad Request)} if the parametroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parametros")
    public ResponseEntity<ParametroDTO> updateParametro(@Valid @RequestBody ParametroDTO parametroDTO) throws URISyntaxException {
        log.debug("REST request to update Parametro : {}", parametroDTO);
        if (parametroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParametroDTO result = parametroService.save(parametroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametroDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parametros} : get all the parametros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametros in body.
     */
    @GetMapping("/parametros")
    public List<ParametroDTO> getAllParametros() {
        log.debug("REST request to get all Parametros");
        return parametroService.findAll();
    }

    /**
     * {@code GET  /parametros/:id} : get the "id" parametro.
     *
     * @param id the id of the parametroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parametros/{id}")
    public ResponseEntity<ParametroDTO> getParametro(@PathVariable Long id) {
        log.debug("REST request to get Parametro : {}", id);
        Optional<ParametroDTO> parametroDTO = parametroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametroDTO);
    }

    /**
     * {@code DELETE  /parametros/:id} : delete the "id" parametro.
     *
     * @param id the id of the parametroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parametros/{id}")
    public ResponseEntity<Void> deleteParametro(@PathVariable Long id) {
        log.debug("REST request to delete Parametro : {}", id);
        parametroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
