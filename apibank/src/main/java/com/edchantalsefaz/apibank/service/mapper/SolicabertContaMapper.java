package com.edchantalsefaz.apibank.service.mapper;


import com.edchantalsefaz.apibank.domain.*;
import com.edchantalsefaz.apibank.service.dto.SolicabertContaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SolicabertConta} and its DTO {@link SolicabertContaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SolicabertContaMapper extends EntityMapper<SolicabertContaDTO, SolicabertConta> {



    default SolicabertConta fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolicabertConta solicabertConta = new SolicabertConta();
        solicabertConta.setId(id);
        return solicabertConta;
    }
}
