package com.edchantalsefaz.apibank.service.impl;

import com.edchantalsefaz.apibank.service.DepositoService;
import com.edchantalsefaz.apibank.domain.Deposito;
import com.edchantalsefaz.apibank.repository.DepositoRepository;
import com.edchantalsefaz.apibank.service.dto.DepositoDTO;
import com.edchantalsefaz.apibank.service.mapper.DepositoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Deposito}.
 */
@Service
@Transactional
public class DepositoServiceImpl implements DepositoService {

    private final Logger log = LoggerFactory.getLogger(DepositoServiceImpl.class);

    private final DepositoRepository depositoRepository;

    private final DepositoMapper depositoMapper;

    public DepositoServiceImpl(DepositoRepository depositoRepository, DepositoMapper depositoMapper) {
        this.depositoRepository = depositoRepository;
        this.depositoMapper = depositoMapper;
    }

    @Override
    public DepositoDTO save(DepositoDTO depositoDTO) {
        log.debug("Request to save Deposito : {}", depositoDTO);
        Deposito deposito = depositoMapper.toEntity(depositoDTO);
        deposito = depositoRepository.save(deposito);
        return depositoMapper.toDto(deposito);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepositoDTO> findAll() {
        log.debug("Request to get all Depositos");
        return depositoRepository.findAll().stream()
            .map(depositoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DepositoDTO> findOne(Long id) {
        log.debug("Request to get Deposito : {}", id);
        return depositoRepository.findById(id)
            .map(depositoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Deposito : {}", id);
        depositoRepository.deleteById(id);
    }
}
