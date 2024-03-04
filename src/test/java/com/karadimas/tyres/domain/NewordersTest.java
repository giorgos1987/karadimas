package com.karadimas.tyres.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.karadimas.tyres.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NewordersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Neworders.class);
        Neworders neworders1 = new Neworders();
        neworders1.setId(1L);
        Neworders neworders2 = new Neworders();
        neworders2.setId(neworders1.getId());
        assertThat(neworders1).isEqualTo(neworders2);
        neworders2.setId(2L);
        assertThat(neworders1).isNotEqualTo(neworders2);
        neworders1.setId(null);
        assertThat(neworders1).isNotEqualTo(neworders2);
    }
}
