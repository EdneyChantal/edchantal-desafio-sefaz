package com.edchantalsefaz.apibank.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.edchantalsefaz.apibank.web.rest.TestUtil;

public class TransferenciaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransferenciaDTO.class);
        TransferenciaDTO transferenciaDTO1 = new TransferenciaDTO();
        transferenciaDTO1.setId(1L);
        TransferenciaDTO transferenciaDTO2 = new TransferenciaDTO();
        assertThat(transferenciaDTO1).isNotEqualTo(transferenciaDTO2);
        transferenciaDTO2.setId(transferenciaDTO1.getId());
        assertThat(transferenciaDTO1).isEqualTo(transferenciaDTO2);
        transferenciaDTO2.setId(2L);
        assertThat(transferenciaDTO1).isNotEqualTo(transferenciaDTO2);
        transferenciaDTO1.setId(null);
        assertThat(transferenciaDTO1).isNotEqualTo(transferenciaDTO2);
    }
}
