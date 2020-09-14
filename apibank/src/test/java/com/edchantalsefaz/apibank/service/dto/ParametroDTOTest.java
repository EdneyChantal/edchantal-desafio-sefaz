package com.edchantalsefaz.apibank.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.edchantalsefaz.apibank.web.rest.TestUtil;

public class ParametroDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametroDTO.class);
        ParametroDTO parametroDTO1 = new ParametroDTO();
        parametroDTO1.setId(1L);
        ParametroDTO parametroDTO2 = new ParametroDTO();
        assertThat(parametroDTO1).isNotEqualTo(parametroDTO2);
        parametroDTO2.setId(parametroDTO1.getId());
        assertThat(parametroDTO1).isEqualTo(parametroDTO2);
        parametroDTO2.setId(2L);
        assertThat(parametroDTO1).isNotEqualTo(parametroDTO2);
        parametroDTO1.setId(null);
        assertThat(parametroDTO1).isNotEqualTo(parametroDTO2);
    }
}
