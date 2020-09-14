package com.edchantalsefaz.apibank.web.rest;

import com.edchantalsefaz.apibank.ApibankApp;
import com.edchantalsefaz.apibank.domain.AccountBank;
import com.edchantalsefaz.apibank.domain.Person;
import com.edchantalsefaz.apibank.repository.AccountBankRepository;
import com.edchantalsefaz.apibank.service.AccountBankService;
import com.edchantalsefaz.apibank.service.dto.AccountBankDTO;
import com.edchantalsefaz.apibank.service.mapper.AccountBankMapper;

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
 * Integration tests for the {@link AccountBankResource} REST controller.
 */
@SpringBootTest(classes = ApibankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccountBankResourceIT {

    private static final Double DEFAULT_SALDO = 1D;
    private static final Double UPDATED_SALDO = 2D;

    private static final Long DEFAULT_NUMERO_CONTA = 1L;
    private static final Long UPDATED_NUMERO_CONTA = 2L;

    @Autowired
    private AccountBankRepository accountBankRepository;

    @Autowired
    private AccountBankMapper accountBankMapper;

    @Autowired
    private AccountBankService accountBankService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountBankMockMvc;

    private AccountBank accountBank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountBank createEntity(EntityManager em) {
        AccountBank accountBank = new AccountBank()
            .saldo(DEFAULT_SALDO)
            .numeroConta(DEFAULT_NUMERO_CONTA);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        accountBank.setPerson(person);
        return accountBank;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountBank createUpdatedEntity(EntityManager em) {
        AccountBank accountBank = new AccountBank()
            .saldo(UPDATED_SALDO)
            .numeroConta(UPDATED_NUMERO_CONTA);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        accountBank.setPerson(person);
        return accountBank;
    }

    @BeforeEach
    public void initTest() {
        accountBank = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountBank() throws Exception {
        int databaseSizeBeforeCreate = accountBankRepository.findAll().size();
        // Create the AccountBank
        AccountBankDTO accountBankDTO = accountBankMapper.toDto(accountBank);
        restAccountBankMockMvc.perform(post("/api/account-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountBankDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountBank in the database
        List<AccountBank> accountBankList = accountBankRepository.findAll();
        assertThat(accountBankList).hasSize(databaseSizeBeforeCreate + 1);
        AccountBank testAccountBank = accountBankList.get(accountBankList.size() - 1);
        assertThat(testAccountBank.getSaldo()).isEqualTo(DEFAULT_SALDO);
        assertThat(testAccountBank.getNumeroConta()).isEqualTo(DEFAULT_NUMERO_CONTA);
    }

    @Test
    @Transactional
    public void createAccountBankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountBankRepository.findAll().size();

        // Create the AccountBank with an existing ID
        accountBank.setId(1L);
        AccountBankDTO accountBankDTO = accountBankMapper.toDto(accountBank);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountBankMockMvc.perform(post("/api/account-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountBankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountBank in the database
        List<AccountBank> accountBankList = accountBankRepository.findAll();
        assertThat(accountBankList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSaldoIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountBankRepository.findAll().size();
        // set the field null
        accountBank.setSaldo(null);

        // Create the AccountBank, which fails.
        AccountBankDTO accountBankDTO = accountBankMapper.toDto(accountBank);


        restAccountBankMockMvc.perform(post("/api/account-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountBankDTO)))
            .andExpect(status().isBadRequest());

        List<AccountBank> accountBankList = accountBankRepository.findAll();
        assertThat(accountBankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroContaIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountBankRepository.findAll().size();
        // set the field null
        accountBank.setNumeroConta(null);

        // Create the AccountBank, which fails.
        AccountBankDTO accountBankDTO = accountBankMapper.toDto(accountBank);


        restAccountBankMockMvc.perform(post("/api/account-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountBankDTO)))
            .andExpect(status().isBadRequest());

        List<AccountBank> accountBankList = accountBankRepository.findAll();
        assertThat(accountBankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountBanks() throws Exception {
        // Initialize the database
        accountBankRepository.saveAndFlush(accountBank);

        // Get all the accountBankList
        restAccountBankMockMvc.perform(get("/api/account-banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO.doubleValue())))
            .andExpect(jsonPath("$.[*].numeroConta").value(hasItem(DEFAULT_NUMERO_CONTA.intValue())));
    }
    
    @Test
    @Transactional
    public void getAccountBank() throws Exception {
        // Initialize the database
        accountBankRepository.saveAndFlush(accountBank);

        // Get the accountBank
        restAccountBankMockMvc.perform(get("/api/account-banks/{id}", accountBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountBank.getId().intValue()))
            .andExpect(jsonPath("$.saldo").value(DEFAULT_SALDO.doubleValue()))
            .andExpect(jsonPath("$.numeroConta").value(DEFAULT_NUMERO_CONTA.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAccountBank() throws Exception {
        // Get the accountBank
        restAccountBankMockMvc.perform(get("/api/account-banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountBank() throws Exception {
        // Initialize the database
        accountBankRepository.saveAndFlush(accountBank);

        int databaseSizeBeforeUpdate = accountBankRepository.findAll().size();

        // Update the accountBank
        AccountBank updatedAccountBank = accountBankRepository.findById(accountBank.getId()).get();
        // Disconnect from session so that the updates on updatedAccountBank are not directly saved in db
        em.detach(updatedAccountBank);
        updatedAccountBank
            .saldo(UPDATED_SALDO)
            .numeroConta(UPDATED_NUMERO_CONTA);
        AccountBankDTO accountBankDTO = accountBankMapper.toDto(updatedAccountBank);

        restAccountBankMockMvc.perform(put("/api/account-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountBankDTO)))
            .andExpect(status().isOk());

        // Validate the AccountBank in the database
        List<AccountBank> accountBankList = accountBankRepository.findAll();
        assertThat(accountBankList).hasSize(databaseSizeBeforeUpdate);
        AccountBank testAccountBank = accountBankList.get(accountBankList.size() - 1);
        assertThat(testAccountBank.getSaldo()).isEqualTo(UPDATED_SALDO);
        assertThat(testAccountBank.getNumeroConta()).isEqualTo(UPDATED_NUMERO_CONTA);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountBank() throws Exception {
        int databaseSizeBeforeUpdate = accountBankRepository.findAll().size();

        // Create the AccountBank
        AccountBankDTO accountBankDTO = accountBankMapper.toDto(accountBank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountBankMockMvc.perform(put("/api/account-banks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accountBankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountBank in the database
        List<AccountBank> accountBankList = accountBankRepository.findAll();
        assertThat(accountBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountBank() throws Exception {
        // Initialize the database
        accountBankRepository.saveAndFlush(accountBank);

        int databaseSizeBeforeDelete = accountBankRepository.findAll().size();

        // Delete the accountBank
        restAccountBankMockMvc.perform(delete("/api/account-banks/{id}", accountBank.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountBank> accountBankList = accountBankRepository.findAll();
        assertThat(accountBankList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
