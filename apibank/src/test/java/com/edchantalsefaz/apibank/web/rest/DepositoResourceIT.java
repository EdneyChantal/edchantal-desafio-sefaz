package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.ApibankApp;
import com.edchantalsefaz.apibank.domain.Deposito;
import com.edchantalsefaz.apibank.domain.AccountBank;
import com.edchantalsefaz.apibank.repository.DepositoRepository;
import com.edchantalsefaz.apibank.service.DepositoService;
import com.edchantalsefaz.apibank.service.dto.DepositoDTO;
import com.edchantalsefaz.apibank.service.mapper.DepositoMapper;

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
 * Integration tests for the {@link DepositoResource} REST controller.
 */
@SpringBootTest(classes = ApibankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DepositoResourceIT {

    private static final LocalDate DEFAULT_DATA_DEPOSITO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_DEPOSITO = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    @Autowired
    private DepositoRepository depositoRepository;

    @Autowired
    private DepositoMapper depositoMapper;

    @Autowired
    private DepositoService depositoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepositoMockMvc;

    private Deposito deposito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deposito createEntity(EntityManager em) {
        Deposito deposito = new Deposito()
            .dataDeposito(DEFAULT_DATA_DEPOSITO)
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
        deposito.setAccount(accountBank);
        return deposito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deposito createUpdatedEntity(EntityManager em) {
        Deposito deposito = new Deposito()
            .dataDeposito(UPDATED_DATA_DEPOSITO)
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
        deposito.setAccount(accountBank);
        return deposito;
    }

    @BeforeEach
    public void initTest() {
        deposito = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeposito() throws Exception {
        int databaseSizeBeforeCreate = depositoRepository.findAll().size();
        // Create the Deposito
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);
        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isCreated());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeCreate + 1);
        Deposito testDeposito = depositoList.get(depositoList.size() - 1);
        assertThat(testDeposito.getDataDeposito()).isEqualTo(DEFAULT_DATA_DEPOSITO);
        assertThat(testDeposito.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createDepositoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depositoRepository.findAll().size();

        // Create the Deposito with an existing ID
        deposito.setId(1L);
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataDepositoIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoRepository.findAll().size();
        // set the field null
        deposito.setDataDeposito(null);

        // Create the Deposito, which fails.
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);


        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = depositoRepository.findAll().size();
        // set the field null
        deposito.setValor(null);

        // Create the Deposito, which fails.
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);


        restDepositoMockMvc.perform(post("/api/depositos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepositos() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        // Get all the depositoList
        restDepositoMockMvc.perform(get("/api/depositos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deposito.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataDeposito").value(hasItem(DEFAULT_DATA_DEPOSITO.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        // Get the deposito
        restDepositoMockMvc.perform(get("/api/depositos/{id}", deposito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deposito.getId().intValue()))
            .andExpect(jsonPath("$.dataDeposito").value(DEFAULT_DATA_DEPOSITO.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDeposito() throws Exception {
        // Get the deposito
        restDepositoMockMvc.perform(get("/api/depositos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();

        // Update the deposito
        Deposito updatedDeposito = depositoRepository.findById(deposito.getId()).get();
        // Disconnect from session so that the updates on updatedDeposito are not directly saved in db
        em.detach(updatedDeposito);
        updatedDeposito
            .dataDeposito(UPDATED_DATA_DEPOSITO)
            .valor(UPDATED_VALOR);
        DepositoDTO depositoDTO = depositoMapper.toDto(updatedDeposito);

        restDepositoMockMvc.perform(put("/api/depositos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isOk());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
        Deposito testDeposito = depositoList.get(depositoList.size() - 1);
        assertThat(testDeposito.getDataDeposito()).isEqualTo(UPDATED_DATA_DEPOSITO);
        assertThat(testDeposito.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingDeposito() throws Exception {
        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();

        // Create the Deposito
        DepositoDTO depositoDTO = depositoMapper.toDto(deposito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositoMockMvc.perform(put("/api/depositos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(depositoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        int databaseSizeBeforeDelete = depositoRepository.findAll().size();

        // Delete the deposito
        restDepositoMockMvc.perform(delete("/api/depositos/{id}", deposito.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
