package com.gestion.locative.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gestion.locative.web.rest.TestUtil;

public class BiensTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Biens.class);
        Biens biens1 = new Biens();
        biens1.setId(1L);
        Biens biens2 = new Biens();
        biens2.setId(biens1.getId());
        assertThat(biens1).isEqualTo(biens2);
        biens2.setId(2L);
        assertThat(biens1).isNotEqualTo(biens2);
        biens1.setId(null);
        assertThat(biens1).isNotEqualTo(biens2);
    }
}
