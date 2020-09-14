package com.edchantalsefaz.apibank.service.mapper;


import com.edchantalsefaz.apibank.domain.*;
import com.edchantalsefaz.apibank.service.dto.AccountBankDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountBank} and its DTO {@link AccountBankDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface AccountBankMapper extends EntityMapper<AccountBankDTO, AccountBank> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.nome", target = "personNome")
    AccountBankDTO toDto(AccountBank accountBank);

    @Mapping(source = "personId", target = "person")
    AccountBank toEntity(AccountBankDTO accountBankDTO);

    default AccountBank fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountBank accountBank = new AccountBank();
        accountBank.setId(id);
        return accountBank;
    }
}
