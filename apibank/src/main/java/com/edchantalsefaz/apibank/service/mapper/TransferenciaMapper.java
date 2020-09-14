package com.edchantalsefaz.apibank.service.mapper;


import com.edchantalsefaz.apibank.domain.*;
import com.edchantalsefaz.apibank.service.dto.TransferenciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transferencia} and its DTO {@link TransferenciaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountBankMapper.class})
public interface TransferenciaMapper extends EntityMapper<TransferenciaDTO, Transferencia> {

    @Mapping(source = "accountSaque.id", target = "accountSaqueId")
    @Mapping(source = "accountSaque.numeroConta", target = "accountSaqueNumeroConta")
    @Mapping(source = "accountDeposito.id", target = "accountDepositoId")
    @Mapping(source = "accountDeposito.numeroConta", target = "accountDepositoNumeroConta")
    TransferenciaDTO toDto(Transferencia transferencia);

    @Mapping(source = "accountSaqueId", target = "accountSaque")
    @Mapping(source = "accountDepositoId", target = "accountDeposito")
    Transferencia toEntity(TransferenciaDTO transferenciaDTO);

    default Transferencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transferencia transferencia = new Transferencia();
        transferencia.setId(id);
        return transferencia;
    }
}
