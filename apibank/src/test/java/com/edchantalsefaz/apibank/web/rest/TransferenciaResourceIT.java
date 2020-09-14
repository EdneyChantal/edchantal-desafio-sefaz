package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.ApibankApp;
import com.edchantalsefaz.apibank.domain.Transferencia;
import com.edchantalsefaz.apibank.domain.AccountBank;
import com.edchantalsefaz.apibank.repository.TransferenciaRepository;
import com.edchantalsefaz.apibank.service.TransferenciaService;
import com.edchantalsefaz.apibank.service.dto.TransferenciaDTO;
import com.edchantalsefaz.apibank.service.mapper.TransferenciaMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransferenciaResource} REST controller.
 */
@SpringBootTest(classes = ApibankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransferenciaResourceIT {

    private static final LocalDate DEFAULT_DATA_TRANSFERENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_TRANSFERENCIA = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Autowired
    private TransferenciaMapper transferenciaMapper;

    @Autowired
    private TransferenciaService transferenciaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferenciaMockMvc;

    private Transferencia transferencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transferencia createEntity(EntityManager em) {
        Transferencia transferencia = new Transferencia()
            .dataTransferencia(DEFAULT_DATA_TRANSFERENCIA)
            .valor(DEFAULT_VALOR);
        // Add required entity
        AccountBank accountBank;
        if (TestUtil.findAll(em, AccountBank.class).isEmpty()) {
            accountBank = AccountBankResourceIT.createEntity(em);
            em.persist(accountBank);
            em.flush();
        } else {
            accountBank = TestUtil.findAll(em, AccountBank.class).get(0);
        }
        transferencia.setAccountSaque(accountBank);
        // Add required entity
        transferencia.setAccountDeposito(accountBank);
        return transferencia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transferencia createUpdatedEntity(EntityManager em) {
        Transferencia transferencia = new Transferencia()
            .dataTransferencia(UPDATED_DATA_TRANSFERENCIA)
            .valor(UPDATED_VALOR);
        // Add required entity
        AccountBank accountBank;
        if (TestUtil.findAll(em, AccountBank.class).isEmpty()) {
            accountBank = AccountBankResourceIT.createUpdatedEntity(em);
            em.persist(accountBank);
            em.flush();
        } else {
            accountBank = TestUtil.findAll(em, AccountBank.class).get(0);
        }
        transferencia.setAccountSaque(accountBank);
        // Add required entity
        transferencia.setAccountDeposito(accountBank);
        return transferencia;
    }

    @BeforeEach
    public void initTest() {
        transferencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransferencia() throws Exception {
        int databaseSizeBeforeCreate = transferenciaRepository.findAll().size();
        // Create the Transferencia
        TransferenciaDTO transferenciaDTO = transferenciaMapper.toDto(transferencia);
        restTransferenciaMockMvc.perform(post("/api/transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Transferencia in the database
        List<Transferencia> transferenciaList = transferenciaRepository.findAll();
        assertThat(transferenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Transferencia testTransferencia = transferenciaList.get(transferenciaList.size() - 1);
        assertThat(testTransferencia.getDataTransferencia()).isEqualTo(DEFAULT_DATA_TRANSFERENCIA);
        assertThat(testTransferencia.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createTransferenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transferenciaRepository.findAll().size();

        // Create the Transferencia with an existing ID
        transferencia.setId(1L);
        TransferenciaDTO transferenciaDTO = transferenciaMapper.toDto(transferencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferenciaMockMvc.perform(post("/api/transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transferencia in the database
        List<Transferencia> transferenciaList = transferenciaRepository.findAll();
        assertThat(transferenciaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataTransferenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferenciaRepository.findAll().size();
        // set the field null
        transferencia.setDataTransferencia(null);

        // Create the Transferencia, which fails.
        TransferenciaDTO transferenciaDTO = transferenciaMapper.toDto(transferencia);


        restTransferenciaMockMvc.perform(post("/api/transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Transferencia> transferenciaList = transferenciaRepository.findAll();
        assertThat(transferenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferenciaRepository.findAll().size();
        // set the field null
        transferencia.setValor(null);

        // Create the Transferencia, which fails.
        TransferenciaDTO transferenciaDTO = transferenciaMapper.toDto(transferencia);


        restTransferenciaMockMvc.perform(post("/api/transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaDTO)))
            .andExpect(status().isBadRequest());

        List<Transferencia> transferenciaList = transferenciaRepository.findAll();
        assertThat(transferenciaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransferencias() throws Exception {
        // Initialize the database
        transferenciaRepository.saveAndFlush(transferencia);

        // Get all the transferenciaList
        restTransferenciaMockMvc.perform(get("/api/transferencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataTransferencia").value(hasItem(DEFAULT_DATA_TRANSFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTransferencia() throws Exception {
        // Initialize the database
        transferenciaRepository.saveAndFlush(transferencia);

        // Get the transferencia
        restTransferenciaMockMvc.perform(get("/api/transferencias/{id}", transferencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transferencia.getId().intValue()))
            .andExpect(jsonPath("$.dataTransferencia").value(DEFAULT_DATA_TRANSFERENCIA.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTransferencia() throws Exception {
        // Get the transferencia
        restTransferenciaMockMvc.perform(get("/api/transferencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransferencia() throws Exception {
        // Initialize the database
        transferenciaRepository.saveAndFlush(transferencia);

        int databaseSizeBeforeUpdate = transferenciaRepository.findAll().size();

        // Update the transferencia
        Transferencia updatedTransferencia = transferenciaRepository.findById(transferencia.getId()).get();
        // Disconnect from session so that the updates on updatedTransferencia are not directly saved in db
        em.detach(updatedTransferencia);
        updatedTransferencia
            .dataTransferencia(UPDATED_DATA_TRANSFERENCIA)
            .valor(UPDATED_VALOR);
        TransferenciaDTO transferenciaDTO = transferenciaMapper.toDto(updatedTransferencia);

        restTransferenciaMockMvc.perform(put("/api/transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaDTO)))
            .andExpect(status().isOk());

        // Validate the Transferencia in the database
        List<Transferencia> transferenciaList = transferenciaRepository.findAll();
        assertThat(transferenciaList).hasSize(databaseSizeBeforeUpdate);
        Transferencia testTransferencia = transferenciaList.get(transferenciaList.size() - 1);
        assertThat(testTransferencia.getDataTransferencia()).isEqualTo(UPDATED_DATA_TRANSFERENCIA);
        assertThat(testTransferencia.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingTransferencia() throws Exception {
        int databaseSizeBeforeUpdate = transferenciaRepository.findAll().size();

        // Create the Transferencia
        TransferenciaDTO transferenciaDTO = transferenciaMapper.toDto(transferencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferenciaMockMvc.perform(put("/api/transferencias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transferenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transferencia in the database
        List<Transferencia> transferenciaList = transferenciaRepository.findAll();
        assertThat(transferenciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransferencia() throws Exception {
        // Initialize the database
        transferenciaRepository.saveAndFlush(transferencia);

        int databaseSizeBeforeDelete = transferenciaRepository.findAll().size();

        // Delete the transferencia
        restTransferenciaMockMvc.perform(delete("/api/transferencias/{id}", transferencia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transferencia> transferenciaList = transferenciaRepository.findAll();
        assertThat(transferenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
