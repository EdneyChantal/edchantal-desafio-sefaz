package com.edchantalsefaz.apibank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.edchantalsefaz.apibank.web.rest.TestUtil;

public class AccountBankTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountBank.class);
        AccountBank accountBank1 = new AccountBank();
        accountBank1.setId(1L);
        AccountBank accountBank2 = new AccountBank();
        accountBank2.setId(accountBank1.getId());
        assertThat(accountBank1).isEqualTo(accountBank2);
        accountBank2.setId(2L);
        assertThat(accountBank1).isNotEqualTo(accountBank2);
        accountBank1.setId(null);
        assertThat(accountBank1).isNotEqualTo(accountBank2);
    }
}
