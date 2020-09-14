package com.edchantalsefaz.apibank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.edchantalsefaz.apibank.web.rest.TestUtil;

public class SaqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Saque.class);
        Saque saque1 = new Saque();
        saque1.setId(1L);
        Saque saque2 = new Saque();
        saque2.setId(saque1.getId());
        assertThat(saque1).isEqualTo(saque2);
        saque2.setId(2L);
        assertThat(saque1).isNotEqualTo(saque2);
        saque1.setId(null);
        assertThat(saque1).isNotEqualTo(saque2);
    }
}
