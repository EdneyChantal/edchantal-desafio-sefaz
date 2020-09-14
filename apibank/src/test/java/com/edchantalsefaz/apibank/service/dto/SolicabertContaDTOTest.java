package com.edchantalsefaz.apibank.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.edchantalsefaz.apibank.web.rest.TestUtil;

public class SolicabertContaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicabertContaDTO.class);
        SolicabertContaDTO solicabertContaDTO1 = new SolicabertContaDTO();
        solicabertContaDTO1.setId(1L);
        SolicabertContaDTO solicabertContaDTO2 = new SolicabertContaDTO();
        assertThat(solicabertContaDTO1).isNotEqualTo(solicabertContaDTO2);
        solicabertContaDTO2.setId(solicabertContaDTO1.getId());
        assertThat(solicabertContaDTO1).isEqualTo(solicabertContaDTO2);
        solicabertContaDTO2.setId(2L);
        assertThat(solicabertContaDTO1).isNotEqualTo(solicabertContaDTO2);
        solicabertContaDTO1.setId(null);
        assertThat(solicabertContaDTO1).isNotEqualTo(solicabertContaDTO2);
    }
}
