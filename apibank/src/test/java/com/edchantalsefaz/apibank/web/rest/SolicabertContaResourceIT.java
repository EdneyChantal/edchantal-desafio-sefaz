package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.ApibankApp;
import com.edchantalsefaz.apibank.domain.SolicabertConta;
import com.edchantalsefaz.apibank.repository.SolicabertContaRepository;
import com.edchantalsefaz.apibank.service.SolicabertContaService;
import com.edchantalsefaz.apibank.service.dto.SolicabertContaDTO;
import com.edchantalsefaz.apibank.service.mapper.SolicabertContaMapper;

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
 * Integration tests for the {@link SolicabertContaResource} REST controller.
 */
@SpringBootTest(classes = ApibankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SolicabertContaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Long DEFAULT_CPF = 1L;
    private static final Long UPDATED_CPF = 2L;

    private static final Double DEFAULT_SALDOINICIAL = 0.1D;
    private static final Double UPDATED_SALDOINICIAL = 1D;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private SolicabertContaRepository solicabertContaRepository;

    @Autowired
    private SolicabertContaMapper solicabertContaMapper;

    @Autowired
    private SolicabertContaService solicabertContaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSolicabertContaMockMvc;

    private SolicabertConta solicabertConta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicabertConta createEntity(EntityManager em) {
        SolicabertConta solicabertConta = new SolicabertConta()
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF)
            .saldoinicial(DEFAULT_SALDOINICIAL)
            .status(DEFAULT_STATUS);
        return solicabertConta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicabertConta createUpdatedEntity(EntityManager em) {
        SolicabertConta solicabertConta = new SolicabertConta()
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .saldoinicial(UPDATED_SALDOINICIAL)
            .status(UPDATED_STATUS);
        return solicabertConta;
    }

    @BeforeEach
    public void initTest() {
        solicabertConta = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicabertConta() throws Exception {
        int databaseSizeBeforeCreate = solicabertContaRepository.findAll().size();
        // Create the SolicabertConta
        SolicabertContaDTO solicabertContaDTO = solicabertContaMapper.toDto(solicabertConta);
        restSolicabertContaMockMvc.perform(post("/api/solicabert-contas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicabertContaDTO)))
            .andExpect(status().isCreated());

        // Validate the SolicabertConta in the database
        List<SolicabertConta> solicabertContaList = solicabertContaRepository.findAll();
        assertThat(solicabertContaList).hasSize(databaseSizeBeforeCreate + 1);
        SolicabertConta testSolicabertConta = solicabertContaList.get(solicabertContaList.size() - 1);
        assertThat(testSolicabertConta.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSolicabertConta.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testSolicabertConta.getSaldoinicial()).isEqualTo(DEFAULT_SALDOINICIAL);
        assertThat(testSolicabertConta.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createSolicabertContaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicabertContaRepository.findAll().size();

        // Create the SolicabertConta with an existing ID
        solicabertConta.setId(1L);
        SolicabertContaDTO solicabertContaDTO = solicabertContaMapper.toDto(solicabertConta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicabertContaMockMvc.perform(post("/api/solicabert-contas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicabertContaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolicabertConta in the database
        List<SolicabertConta> solicabertContaList = solicabertContaRepository.findAll();
        assertThat(solicabertContaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicabertContaRepository.findAll().size();
        // set the field null
        solicabertConta.setNome(null);

        // Create the SolicabertConta, which fails.
        SolicabertContaDTO solicabertContaDTO = solicabertContaMapper.toDto(solicabertConta);


        restSolicabertContaMockMvc.perform(post("/api/solicabert-contas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicabertContaDTO)))
            .andExpect(status().isBadRequest());

        List<SolicabertConta> solicabertContaList = solicabertContaRepository.findAll();
        assertThat(solicabertContaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicabertContaRepository.findAll().size();
        // set the field null
        solicabertConta.setCpf(null);

        // Create the SolicabertConta, which fails.
        SolicabertContaDTO solicabertContaDTO = solicabertContaMapper.toDto(solicabertConta);


        restSolicabertContaMockMvc.perform(post("/api/solicabert-contas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicabertContaDTO)))
            .andExpect(status().isBadRequest());

        List<SolicabertConta> solicabertContaList = solicabertContaRepository.findAll();
        assertThat(solicabertContaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaldoinicialIsRequired() throws Exception {
        int databaseSizeBeforeTest = solicabertContaRepository.findAll().size();
        // set the field null
        solicabertConta.setSaldoinicial(null);

        // Create the SolicabertConta, which fails.
        SolicabertContaDTO solicabertContaDTO = solicabertContaMapper.toDto(solicabertConta);


        restSolicabertContaMockMvc.perform(post("/api/solicabert-contas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicabertContaDTO)))
            .andExpect(status().isBadRequest());

        List<SolicabertConta> solicabertContaList = solicabertContaRepository.findAll();
        assertThat(solicabertContaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolicabertContas() throws Exception {
        // Initialize the database
        solicabertContaRepository.saveAndFlush(solicabertConta);

        // Get all the solicabertContaList
        restSolicabertContaMockMvc.perform(get("/api/solicabert-contas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicabertConta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.intValue())))
            .andExpect(jsonPath("$.[*].saldoinicial").value(hasItem(DEFAULT_SALDOINICIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getSolicabertConta() throws Exception {
        // Initialize the database
        solicabertContaRepository.saveAndFlush(solicabertConta);

        // Get the solicabertConta
        restSolicabertContaMockMvc.perform(get("/api/solicabert-contas/{id}", solicabertConta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solicabertConta.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.intValue()))
            .andExpect(jsonPath("$.saldoinicial").value(DEFAULT_SALDOINICIAL.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingSolicabertConta() throws Exception {
        // Get the solicabertConta
        restSolicabertContaMockMvc.perform(get("/api/solicabert-contas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicabertConta() throws Exception {
        // Initialize the database
        solicabertContaRepository.saveAndFlush(solicabertConta);

        int databaseSizeBeforeUpdate = solicabertContaRepository.findAll().size();

        // Update the solicabertConta
        SolicabertConta updatedSolicabertConta = solicabertContaRepository.findById(solicabertConta.getId()).get();
        // Disconnect from session so that the updates on updatedSolicabertConta are not directly saved in db
        em.detach(updatedSolicabertConta);
        updatedSolicabertConta
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .saldoinicial(UPDATED_SALDOINICIAL)
            .status(UPDATED_STATUS);
        SolicabertContaDTO solicabertContaDTO = solicabertContaMapper.toDto(updatedSolicabertConta);

        restSolicabertContaMockMvc.perform(put("/api/solicabert-contas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicabertContaDTO)))
            .andExpect(status().isOk());

        // Validate the SolicabertConta in the database
        List<SolicabertConta> solicabertContaList = solicabertContaRepository.findAll();
        assertThat(solicabertContaList).hasSize(databaseSizeBeforeUpdate);
        SolicabertConta testSolicabertConta = solicabertContaList.get(solicabertContaList.size() - 1);
        assertThat(testSolicabertConta.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSolicabertConta.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testSolicabertConta.getSaldoinicial()).isEqualTo(UPDATED_SALDOINICIAL);
        assertThat(testSolicabertConta.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicabertConta() throws Exception {
        int databaseSizeBeforeUpdate = solicabertContaRepository.findAll().size();

        // Create the SolicabertConta
        SolicabertContaDTO solicabertContaDTO = solicabertContaMapper.toDto(solicabertConta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicabertContaMockMvc.perform(put("/api/solicabert-contas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicabertContaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SolicabertConta in the database
        List<SolicabertConta> solicabertContaList = solicabertContaRepository.findAll();
        assertThat(solicabertContaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSolicabertConta() throws Exception {
        // Initialize the database
        solicabertContaRepository.saveAndFlush(solicabertConta);

        int databaseSizeBeforeDelete = solicabertContaRepository.findAll().size();

        // Delete the solicabertConta
        restSolicabertContaMockMvc.perform(delete("/api/solicabert-contas/{id}", solicabertConta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SolicabertConta> solicabertContaList = solicabertContaRepository.findAll();
        assertThat(solicabertContaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
