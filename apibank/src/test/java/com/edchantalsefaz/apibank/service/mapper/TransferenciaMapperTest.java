package com.edchantalsefaz.apibank.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransferenciaMapperTest {

    private TransferenciaMapper transferenciaMapper;

    @BeforeEach
    public void setUp() {
        transferenciaMapper = new TransferenciaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transferenciaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transferenciaMapper.fromId(null)).isNull();
    }
}
