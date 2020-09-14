package com.edchantalsefaz.apibank.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ParametroMapperTest {

    private ParametroMapper parametroMapper;

    @BeforeEach
    public void setUp() {
        parametroMapper = new ParametroMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(parametroMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(parametroMapper.fromId(null)).isNull();
    }
}
