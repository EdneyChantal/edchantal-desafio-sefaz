package com.edchantalsefaz.apibank.service.mapper;


import com.edchantalsefaz.apibank.domain.*;
import com.edchantalsefaz.apibank.service.dto.ParametroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parametro} and its DTO {@link ParametroDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParametroMapper extends EntityMapper<ParametroDTO, Parametro> {



    default Parametro fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parametro parametro = new Parametro();
        parametro.setId(id);
        return parametro;
    }
}
