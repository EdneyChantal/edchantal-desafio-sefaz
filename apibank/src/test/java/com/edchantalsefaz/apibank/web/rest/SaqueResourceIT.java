package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.ApibankApp;
import com.edchantalsefaz.apibank.domain.Saque;
import com.edchantalsefaz.apibank.domain.AccountBank;
import com.edchantalsefaz.apibank.repository.SaqueRepository;
import com.edchantalsefaz.apibank.service.SaqueService;
import com.edchantalsefaz.apibank.service.dto.SaqueDTO;
import com.edchantalsefaz.apibank.service.mapper.SaqueMapper;

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
 * Integration tests for the {@link SaqueResource} REST controller.
 */
@SpringBootTest(classes = ApibankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SaqueResourceIT {

    private static final LocalDate DEFAULT_DATA_SAQUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_SAQUE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    @Autowired
    private SaqueRepository saqueRepository;

    @Autowired
    private SaqueMapper saqueMapper;

    @Autowired
    private SaqueService saqueService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaqueMockMvc;

    private Saque saque;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Saque createEntity(EntityManager em) {
        Saque saque = new Saque()
            .dataSaque(DEFAULT_DATA_SAQUE)
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
        saque.setAccount(accountBank);
        return saque;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Saque createUpdatedEntity(EntityManager em) {
        Saque saque = new Saque()
            .dataSaque(UPDATED_DATA_SAQUE)
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
        saque.setAccount(accountBank);
        return saque;
    }

    @BeforeEach
    public void initTest() {
        saque = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaque() throws Exception {
        int databaseSizeBeforeCreate = saqueRepository.findAll().size();
        // Create the Saque
        SaqueDTO saqueDTO = saqueMapper.toDto(saque);
        restSaqueMockMvc.perform(post("/api/saques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saqueDTO)))
            .andExpect(status().isCreated());

        // Validate the Saque in the database
        List<Saque> saqueList = saqueRepository.findAll();
        assertThat(saqueList).hasSize(databaseSizeBeforeCreate + 1);
        Saque testSaque = saqueList.get(saqueList.size() - 1);
        assertThat(testSaque.getDataSaque()).isEqualTo(DEFAULT_DATA_SAQUE);
        assertThat(testSaque.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createSaqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saqueRepository.findAll().size();

        // Create the Saque with an existing ID
        saque.setId(1L);
        SaqueDTO saqueDTO = saqueMapper.toDto(saque);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaqueMockMvc.perform(post("/api/saques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Saque in the database
        List<Saque> saqueList = saqueRepository.findAll();
        assertThat(saqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataSaqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = saqueRepository.findAll().size();
        // set the field null
        saque.setDataSaque(null);

        // Create the Saque, which fails.
        SaqueDTO saqueDTO = saqueMapper.toDto(saque);


        restSaqueMockMvc.perform(post("/api/saques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saqueDTO)))
            .andExpect(status().isBadRequest());

        List<Saque> saqueList = saqueRepository.findAll();
        assertThat(saqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = saqueRepository.findAll().size();
        // set the field null
        saque.setValor(null);

        // Create the Saque, which fails.
        SaqueDTO saqueDTO = saqueMapper.toDto(saque);


        restSaqueMockMvc.perform(post("/api/saques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saqueDTO)))
            .andExpect(status().isBadRequest());

        List<Saque> saqueList = saqueRepository.findAll();
        assertThat(saqueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaques() throws Exception {
        // Initialize the database
        saqueRepository.saveAndFlush(saque);

        // Get all the saqueList
        restSaqueMockMvc.perform(get("/api/saques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saque.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataSaque").value(hasItem(DEFAULT_DATA_SAQUE.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSaque() throws Exception {
        // Initialize the database
        saqueRepository.saveAndFlush(saque);

        // Get the saque
        restSaqueMockMvc.perform(get("/api/saques/{id}", saque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saque.getId().intValue()))
            .andExpect(jsonPath("$.dataSaque").value(DEFAULT_DATA_SAQUE.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSaque() throws Exception {
        // Get the saque
        restSaqueMockMvc.perform(get("/api/saques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaque() throws Exception {
        // Initialize the database
        saqueRepository.saveAndFlush(saque);

        int databaseSizeBeforeUpdate = saqueRepository.findAll().size();

        // Update the saque
        Saque updatedSaque = saqueRepository.findById(saque.getId()).get();
        // Disconnect from session so that the updates on updatedSaque are not directly saved in db
        em.detach(updatedSaque);
        updatedSaque
            .dataSaque(UPDATED_DATA_SAQUE)
            .valor(UPDATED_VALOR);
        SaqueDTO saqueDTO = saqueMapper.toDto(updatedSaque);

        restSaqueMockMvc.perform(put("/api/saques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saqueDTO)))
            .andExpect(status().isOk());

        // Validate the Saque in the database
        List<Saque> saqueList = saqueRepository.findAll();
        assertThat(saqueList).hasSize(databaseSizeBeforeUpdate);
        Saque testSaque = saqueList.get(saqueList.size() - 1);
        assertThat(testSaque.getDataSaque()).isEqualTo(UPDATED_DATA_SAQUE);
        assertThat(testSaque.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSaque() throws Exception {
        int databaseSizeBeforeUpdate = saqueRepository.findAll().size();

        // Create the Saque
        SaqueDTO saqueDTO = saqueMapper.toDto(saque);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaqueMockMvc.perform(put("/api/saques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Saque in the database
        List<Saque> saqueList = saqueRepository.findAll();
        assertThat(saqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSaque() throws Exception {
        // Initialize the database
        saqueRepository.saveAndFlush(saque);

        int databaseSizeBeforeDelete = saqueRepository.findAll().size();

        // Delete the saque
        restSaqueMockMvc.perform(delete("/api/saques/{id}", saque.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Saque> saqueList = saqueRepository.findAll();
        assertThat(saqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
