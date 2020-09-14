package com.edchantalsefaz.apibank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.edchantalsefaz.apibank.web.rest.TestUtil;

public class TransferenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transferencia.class);
        Transferencia transferencia1 = new Transferencia();
        transferencia1.setId(1L);
        Transferencia transferencia2 = new Transferencia();
        transferencia2.setId(transferencia1.getId());
        assertThat(transferencia1).isEqualTo(transferencia2);
        transferencia2.setId(2L);
        assertThat(transferencia1).isNotEqualTo(transferencia2);
        transferencia1.setId(null);
        assertThat(transferencia1).isNotEqualTo(transferencia2);
    }
}
