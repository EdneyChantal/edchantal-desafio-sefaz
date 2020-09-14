package com.edchantalsefaz.apibank.service.mapper;


import com.edchantalsefaz.apibank.domain.*;
import com.edchantalsefaz.apibank.service.dto.DepositoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deposito} and its DTO {@link DepositoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountBankMapper.class})
public interface DepositoMapper extends EntityMapper<DepositoDTO, Deposito> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.numeroConta", target = "accountNumeroConta")
    DepositoDTO toDto(Deposito deposito);

    @Mapping(source = "accountId", target = "account")
    Deposito toEntity(DepositoDTO depositoDTO);

    default Deposito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Deposito deposito = new Deposito();
        deposito.setId(id);
        return deposito;
    }
}
