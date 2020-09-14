package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.ApibankApp;
import com.edchantalsefaz.apibank.domain.Parametro;
import com.edchantalsefaz.apibank.repository.ParametroRepository;
import com.edchantalsefaz.apibank.service.ParametroService;
import com.edchantalsefaz.apibank.service.dto.ParametroDTO;
import com.edchantalsefaz.apibank.service.mapper.ParametroMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ParametroResource} REST controller.
 */
@SpringBootTest(classes = ApibankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParametroResourceIT {

    private static final Double DEFAULT_VLR_MIN_ABRE_CONTA = 1D;
    private static final Double UPDATED_VLR_MIN_ABRE_CONTA = 2D;

    private static final Double DEFAULT_VLR_MAX_TRANSFER = 1D;
    private static final Double UPDATED_VLR_MAX_TRANSFER = 2D;

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private ParametroMapper parametroMapper;

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParametroMockMvc;

    private Parametro parametro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parametro createEntity(EntityManager em) {
        Parametro parametro = new Parametro()
            .vlrMinAbreConta(DEFAULT_VLR_MIN_ABRE_CONTA)
            .vlrMaxTransfer(DEFAULT_VLR_MAX_TRANSFER);
        return parametro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parametro createUpdatedEntity(EntityManager em) {
        Parametro parametro = new Parametro()
            .vlrMinAbreConta(UPDATED_VLR_MIN_ABRE_CONTA)
            .vlrMaxTransfer(UPDATED_VLR_MAX_TRANSFER);
        return parametro;
    }

    @BeforeEach
    public void initTest() {
        parametro = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametro() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();
        // Create the Parametro
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);
        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isCreated());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate + 1);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getVlrMinAbreConta()).isEqualTo(DEFAULT_VLR_MIN_ABRE_CONTA);
        assertThat(testParametro.getVlrMaxTransfer()).isEqualTo(DEFAULT_VLR_MAX_TRANSFER);
    }

    @Test
    @Transactional
    public void createParametroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametroRepository.findAll().size();

        // Create the Parametro with an existing ID
        parametro.setId(1L);
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkVlrMinAbreContaIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroRepository.findAll().size();
        // set the field null
        parametro.setVlrMinAbreConta(null);

        // Create the Parametro, which fails.
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);


        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVlrMaxTransferIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroRepository.findAll().size();
        // set the field null
        parametro.setVlrMaxTransfer(null);

        // Create the Parametro, which fails.
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);


        restParametroMockMvc.perform(post("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametros() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get all the parametroList
        restParametroMockMvc.perform(get("/api/parametros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametro.getId().intValue())))
            .andExpect(jsonPath("$.[*].vlrMinAbreConta").value(hasItem(DEFAULT_VLR_MIN_ABRE_CONTA.doubleValue())))
            .andExpect(jsonPath("$.[*].vlrMaxTransfer").value(hasItem(DEFAULT_VLR_MAX_TRANSFER.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", parametro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parametro.getId().intValue()))
            .andExpect(jsonPath("$.vlrMinAbreConta").value(DEFAULT_VLR_MIN_ABRE_CONTA.doubleValue()))
            .andExpect(jsonPath("$.vlrMaxTransfer").value(DEFAULT_VLR_MAX_TRANSFER.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingParametro() throws Exception {
        // Get the parametro
        restParametroMockMvc.perform(get("/api/parametros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Update the parametro
        Parametro updatedParametro = parametroRepository.findById(parametro.getId()).get();
        // Disconnect from session so that the updates on updatedParametro are not directly saved in db
        em.detach(updatedParametro);
        updatedParametro
            .vlrMinAbreConta(UPDATED_VLR_MIN_ABRE_CONTA)
            .vlrMaxTransfer(UPDATED_VLR_MAX_TRANSFER);
        ParametroDTO parametroDTO = parametroMapper.toDto(updatedParametro);

        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isOk());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate);
        Parametro testParametro = parametroList.get(parametroList.size() - 1);
        assertThat(testParametro.getVlrMinAbreConta()).isEqualTo(UPDATED_VLR_MIN_ABRE_CONTA);
        assertThat(testParametro.getVlrMaxTransfer()).isEqualTo(UPDATED_VLR_MAX_TRANSFER);
    }

    @Test
    @Transactional
    public void updateNonExistingParametro() throws Exception {
        int databaseSizeBeforeUpdate = parametroRepository.findAll().size();

        // Create the Parametro
        ParametroDTO parametroDTO = parametroMapper.toDto(parametro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametroMockMvc.perform(put("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parametro in the database
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParametro() throws Exception {
        // Initialize the database
        parametroRepository.saveAndFlush(parametro);

        int databaseSizeBeforeDelete = parametroRepository.findAll().size();

        // Delete the parametro
        restParametroMockMvc.perform(delete("/api/parametros/{id}", parametro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parametro> parametroList = parametroRepository.findAll();
        assertThat(parametroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
