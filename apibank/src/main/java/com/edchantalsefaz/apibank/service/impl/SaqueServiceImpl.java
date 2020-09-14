package com.edchantalsefaz.apibank.service.impl;

import com.edchantalsefaz.apibank.service.SaqueService;
import com.edchantalsefaz.apibank.domain.Saque;
import com.edchantalsefaz.apibank.repository.SaqueRepository;
import com.edchantalsefaz.apibank.service.dto.SaqueDTO;
import com.edchantalsefaz.apibank.service.mapper.SaqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Saque}.
 */
@Service
@Transactional
public class SaqueServiceImpl implements SaqueService {

    private final Logger log = LoggerFactory.getLogger(SaqueServiceImpl.class);

    private final SaqueRepository saqueRepository;

    private final SaqueMapper saqueMapper;

    public SaqueServiceImpl(SaqueRepository saqueRepository, SaqueMapper saqueMapper) {
        this.saqueRepository = saqueRepository;
        this.saqueMapper = saqueMapper;
    }

    @Override
    public SaqueDTO save(SaqueDTO saqueDTO) {
        log.debug("Request to save Saque : {}", saqueDTO);
        Saque saque = saqueMapper.toEntity(saqueDTO);
        saque = saqueRepository.save(saque);
        return saqueMapper.toDto(saque);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaqueDTO> findAll() {
        log.debug("Request to get all Saques");
        return saqueRepository.findAll().stream()
            .map(saqueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SaqueDTO> findOne(Long id) {
        log.debug("Request to get Saque : {}", id);
        return saqueRepository.findById(id)
            .map(saqueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Saque : {}", id);
        saqueRepository.deleteById(id);
    }
}
