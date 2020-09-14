package com.edchantalsefaz.apibank.service.impl;

import com.edchantalsefaz.apibank.service.TransferenciaService;
import com.edchantalsefaz.apibank.domain.Transferencia;
import com.edchantalsefaz.apibank.repository.TransferenciaRepository;
import com.edchantalsefaz.apibank.service.dto.TransferenciaDTO;
import com.edchantalsefaz.apibank.service.mapper.TransferenciaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Transferencia}.
 */
@Service
@Transactional
public class TransferenciaServiceImpl implements TransferenciaService {

    private final Logger log = LoggerFactory.getLogger(TransferenciaServiceImpl.class);

    private final TransferenciaRepository transferenciaRepository;

    private final TransferenciaMapper transferenciaMapper;

    public TransferenciaServiceImpl(TransferenciaRepository transferenciaRepository, TransferenciaMapper transferenciaMapper) {
        this.transferenciaRepository = transferenciaRepository;
        this.transferenciaMapper = transferenciaMapper;
    }

    @Override
    public TransferenciaDTO save(TransferenciaDTO transferenciaDTO) {
        log.debug("Request to save Transferencia : {}", transferenciaDTO);
        Transferencia transferencia = transferenciaMapper.toEntity(transferenciaDTO);
        transferencia = transferenciaRepository.save(transferencia);
        return transferenciaMapper.toDto(transferencia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferenciaDTO> findAll() {
        log.debug("Request to get all Transferencias");
        return transferenciaRepository.findAll().stream()
            .map(transferenciaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TransferenciaDTO> findOne(Long id) {
        log.debug("Request to get Transferencia : {}", id);
        return transferenciaRepository.findById(id)
            .map(transferenciaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transferencia : {}", id);
        transferenciaRepository.deleteById(id);
    }
}
