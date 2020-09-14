package com.edchantalsefaz.apibank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.edchantalsefaz.apibank.web.rest.TestUtil;

public class SolicabertContaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicabertConta.class);
        SolicabertConta solicabertConta1 = new SolicabertConta();
        solicabertConta1.setId(1L);
        SolicabertConta solicabertConta2 = new SolicabertConta();
        solicabertConta2.setId(solicabertConta1.getId());
        assertThat(solicabertConta1).isEqualTo(solicabertConta2);
        solicabertConta2.setId(2L);
        assertThat(solicabertConta1).isNotEqualTo(solicabertConta2);
        solicabertConta1.setId(null);
        assertThat(solicabertConta1).isNotEqualTo(solicabertConta2);
    }
}
