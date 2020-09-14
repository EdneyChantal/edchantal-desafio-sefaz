package com.edchantalsefaz.apibank.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SolicabertContaMapperTest {

    private SolicabertContaMapper solicabertContaMapper;

    @BeforeEach
    public void setUp() {
        solicabertContaMapper = new SolicabertContaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(solicabertContaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(solicabertContaMapper.fromId(null)).isNull();
    }
}
