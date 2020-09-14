package com.edchantalsefaz.apibank.service.mapper;


import com.edchantalsefaz.apibank.domain.*;
import com.edchantalsefaz.apibank.service.dto.SaqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Saque} and its DTO {@link SaqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountBankMapper.class})
public interface SaqueMapper extends EntityMapper<SaqueDTO, Saque> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.numeroConta", target = "accountNumeroConta")
    SaqueDTO toDto(Saque saque);

    @Mapping(source = "accountId", target = "account")
    Saque toEntity(SaqueDTO saqueDTO);

    default Saque fromId(Long id) {
        if (id == null) {
            return null;
        }
        Saque saque = new Saque();
        saque.setId(id);
        return saque;
    }
}
