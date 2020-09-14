package com.edchantalsefaz.apibank.service.impl;

import com.edchantalsefaz.apibank.service.AccountBankService;
import com.edchantalsefaz.apibank.domain.AccountBank;
import com.edchantalsefaz.apibank.repository.AccountBankRepository;
import com.edchantalsefaz.apibank.service.dto.AccountBankDTO;
import com.edchantalsefaz.apibank.service.mapper.AccountBankMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AccountBank}.
 */
@Service
@Transactional
public class AccountBankServiceImpl implements AccountBankService {

    private final Logger log = LoggerFactory.getLogger(AccountBankServiceImpl.class);

    private final AccountBankRepository accountBankRepository;

    private final AccountBankMapper accountBankMapper;

    public AccountBankServiceImpl(AccountBankRepository accountBankRepository, AccountBankMapper accountBankMapper) {
        this.accountBankRepository = accountBankRepository;
        this.accountBankMapper = accountBankMapper;
    }

    @Override
    public AccountBankDTO save(AccountBankDTO accountBankDTO) {
        log.debug("Request to save AccountBank : {}", accountBankDTO);
        AccountBank accountBank = accountBankMapper.toEntity(accountBankDTO);
        accountBank = accountBankRepository.save(accountBank);
        return accountBankMapper.toDto(accountBank);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBankDTO> findAll() {
        log.debug("Request to get all AccountBanks");
        return accountBankRepository.findAll().stream()
            .map(accountBankMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AccountBankDTO> findOne(Long id) {
        log.debug("Request to get AccountBank : {}", id);
        return accountBankRepository.findById(id)
            .map(accountBankMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AccountBank : {}", id);
        accountBankRepository.deleteById(id);
    }

    @Override
    public String deposito(Long pid , Double pvalor) {

        Optional<AccountBankDTO> opAccountbDTO = this.findOne(pid);
        if (opAccountbDTO.isEmpty()){
            return "accountinexist";
        }
        AccountBankDTO accountDTO = opAccountbDTO.get();
        
        accountDTO.setSaldo(accountDTO.getSaldo()+pvalor);

        this.save(accountDTO);


        return "ok";

    }
    @Override
    public String saque(Long pid , Double pvalor) {
     
        Optional<AccountBankDTO> opAccountbDTO = this.findOne(pid);
        if (opAccountbDTO.isEmpty()){
           return "accountinexist" ;
        }
        AccountBankDTO accountDTO = opAccountbDTO.get();
        
        if (accountDTO.getSaldo()<pvalor) {
            return  "insulficientfunds";
        }
        accountDTO.setSaldo(accountDTO.getSaldo()-pvalor);

        this.save(accountDTO);

        return "ok";

    }
}
