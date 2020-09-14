package com.edchantalsefaz.apibank.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.edchantalsefaz.apibank.web.rest.TestUtil;

public class AccountBankDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountBankDTO.class);
        AccountBankDTO accountBankDTO1 = new AccountBankDTO();
        accountBankDTO1.setId(1L);
        AccountBankDTO accountBankDTO2 = new AccountBankDTO();
        assertThat(accountBankDTO1).isNotEqualTo(accountBankDTO2);
        accountBankDTO2.setId(accountBankDTO1.getId());
        assertThat(accountBankDTO1).isEqualTo(accountBankDTO2);
        accountBankDTO2.setId(2L);
        assertThat(accountBankDTO1).isNotEqualTo(accountBankDTO2);
        accountBankDTO1.setId(null);
        assertThat(accountBankDTO1).isNotEqualTo(accountBankDTO2);
    }
}
