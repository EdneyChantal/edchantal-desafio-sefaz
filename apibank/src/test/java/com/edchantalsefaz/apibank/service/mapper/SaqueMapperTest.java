package com.edchantalsefaz.apibank.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SaqueMapperTest {

    private SaqueMapper saqueMapper;

    @BeforeEach
    public void setUp() {
        saqueMapper = new SaqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(saqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(saqueMapper.fromId(null)).isNull();
    }
}
