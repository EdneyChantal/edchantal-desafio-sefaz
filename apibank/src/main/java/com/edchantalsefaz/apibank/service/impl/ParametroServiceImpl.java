package com.edchantalsefaz.apibank.service.impl;

import com.edchantalsefaz.apibank.service.ParametroService;
import com.edchantalsefaz.apibank.domain.Parametro;
import com.edchantalsefaz.apibank.repository.ParametroRepository;
import com.edchantalsefaz.apibank.service.dto.ParametroDTO;
import com.edchantalsefaz.apibank.service.mapper.ParametroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Parametro}.
 */
@Service
@Transactional
public class ParametroServiceImpl implements ParametroService {

    private final Logger log = LoggerFactory.getLogger(ParametroServiceImpl.class);

    private final ParametroRepository parametroRepository;

    private final ParametroMapper parametroMapper;

    public ParametroServiceImpl(ParametroRepository parametroRepository, ParametroMapper parametroMapper) {
        this.parametroRepository = parametroRepository;
        this.parametroMapper = parametroMapper;
    }

    @Override
    public ParametroDTO save(ParametroDTO parametroDTO)  {
        log.debug("Request to save Parametro : {}", parametroDTO);
        Parametro parametro = parametroMapper.toEntity(parametroDTO);
       
        parametro = parametroRepository.save(parametro);
        return parametroMapper.toDto(parametro);
        
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParametroDTO> findAll() {
        log.debug("Request to get all Parametros");
        return parametroRepository.findAll().stream()
            .map(parametroMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ParametroDTO> findOne(Long id) {
        log.debug("Request to get Parametro : {}", id);
        return parametroRepository.findById(id)
            .map(parametroMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parametro : {}", id);
        parametroRepository.deleteById(id);
    }
    @Override 
    public String valorMinimo(Double pvalor) {
         // tem parametro ?
         LinkedList<ParametroDTO> lparametro = (LinkedList<ParametroDTO>) this.findAll();
        
         if ((lparametro==null) || (lparametro.size()==0)) {
             return  "missinParameter";
         }
         ParametroDTO parametro = lparametro.getFirst();
         
         if (parametro.getVlrMinAbreConta()>pvalor) {
            return  "vlrundermin";
         }
    
         return "ok";
    };
    @Override 
    public String valorMaximo(Double pvalor) {
        // tem parametro ?
        LinkedList<ParametroDTO> lparametro = (LinkedList<ParametroDTO>) this.findAll();
       
        if ((lparametro==null) || (lparametro.size()==0)) {
            return  "missinParameter";
        }
        ParametroDTO parametro = lparametro.getFirst();
        
        if (parametro.getVlrMaxTransfer()<pvalor) {
           return  "valuemaxoperation";
        }
   
        return "ok";
   };
}
