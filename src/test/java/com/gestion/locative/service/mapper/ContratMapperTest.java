package com.gestion.locative.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ContratMapperTest {

    private ContratMapper contratMapper;

    @BeforeEach
    public void setUp() {
        contratMapper = new ContratMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(contratMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contratMapper.fromId(null)).isNull();
    }
}
