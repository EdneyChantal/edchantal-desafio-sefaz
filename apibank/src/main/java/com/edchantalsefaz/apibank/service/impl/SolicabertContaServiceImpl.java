package com.edchantalsefaz.apibank.service.impl;

import com.edchantalsefaz.apibank.service.SolicabertContaService;
import com.edchantalsefaz.apibank.domain.SolicabertConta;
import com.edchantalsefaz.apibank.repository.SolicabertContaRepository;
import com.edchantalsefaz.apibank.service.dto.SolicabertContaDTO;
import com.edchantalsefaz.apibank.service.mapper.SolicabertContaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SolicabertConta}.
 */
@Service
@Transactional
public class SolicabertContaServiceImpl implements SolicabertContaService {

    private final Logger log = LoggerFactory.getLogger(SolicabertContaServiceImpl.class);

    private final SolicabertContaRepository solicabertContaRepository;

    private final SolicabertContaMapper solicabertContaMapper;

    public SolicabertContaServiceImpl(SolicabertContaRepository solicabertContaRepository, SolicabertContaMapper solicabertContaMapper) {
        this.solicabertContaRepository = solicabertContaRepository;
        this.solicabertContaMapper = solicabertContaMapper;
    }

    @Override
    public SolicabertContaDTO save(SolicabertContaDTO solicabertContaDTO) {
        log.debug("Request to save SolicabertConta : {}", solicabertContaDTO);
        SolicabertConta solicabertConta = solicabertContaMapper.toEntity(solicabertContaDTO);
        solicabertConta = solicabertContaRepository.save(solicabertConta);
        return solicabertContaMapper.toDto(solicabertConta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicabertContaDTO> findAll() {
        log.debug("Request to get all SolicabertContas");
        return solicabertContaRepository.findAll().stream()
            .map(solicabertContaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SolicabertContaDTO> findOne(Long id) {
        log.debug("Request to get SolicabertConta : {}", id);
        return solicabertContaRepository.findById(id)
            .map(solicabertContaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SolicabertConta : {}", id);
        solicabertContaRepository.deleteById(id);
    }

    @Override
    public boolean cpf(String strCpf) {

        int     d1, d2;
        int     digito1, digito2, resto;
        int     digitoCPF;
        String  nDigResult;
  
        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;
  
        for (int nCount = 1; nCount < strCpf.length() -1; nCount++)
        {
           digitoCPF = Integer.valueOf (strCpf.substring(nCount -1, nCount)).intValue();
  
           //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
           d1 = d1 + ( 11 - nCount ) * digitoCPF;
  
           //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
           d2 = d2 + ( 12 - nCount ) * digitoCPF;
        };
  
        //Primeiro resto da divisão por 11.
        resto = (d1 % 11);
  
        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2)
           digito1 = 0;
        else
           digito1 = 11 - resto;
  
        d2 += 2 * digito1;
  
        //Segundo resto da divisão por 11.
        resto = (d2 % 11);
  
        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2)
           digito2 = 0;
        else
           digito2 = 11 - resto;
  
        //Digito verificador do CPF que está sendo validado.
        String nDigVerific = strCpf.substring (strCpf.length()-2, strCpf.length());
  
        //Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
  
        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
        return nDigVerific.equals(nDigResult);

    }
}
