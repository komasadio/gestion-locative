package com.gestion.locative.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gestion.locative.web.rest.TestUtil;

public class BiensDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiensDTO.class);
        BiensDTO biensDTO1 = new BiensDTO();
        biensDTO1.setId(1L);
        BiensDTO biensDTO2 = new BiensDTO();
        assertThat(biensDTO1).isNotEqualTo(biensDTO2);
        biensDTO2.setId(biensDTO1.getId());
        assertThat(biensDTO1).isEqualTo(biensDTO2);
        biensDTO2.setId(2L);
        assertThat(biensDTO1).isNotEqualTo(biensDTO2);
        biensDTO1.setId(null);
        assertThat(biensDTO1).isNotEqualTo(biensDTO2);
    }
}
