package com.gestion.locative.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BiensMapperTest {

    private BiensMapper biensMapper;

    @BeforeEach
    public void setUp() {
        biensMapper = new BiensMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(biensMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(biensMapper.fromId(null)).isNull();
    }
}
