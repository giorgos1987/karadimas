package com.karadimas.tyres.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.karadimas.tyres.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerpaymentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customerpayments.class);
        Customerpayments customerpayments1 = new Customerpayments();
        customerpayments1.setId(1L);
        Customerpayments customerpayments2 = new Customerpayments();
        customerpayments2.setId(customerpayments1.getId());
        assertThat(customerpayments1).isEqualTo(customerpayments2);
        customerpayments2.setId(2L);
        assertThat(customerpayments1).isNotEqualTo(customerpayments2);
        customerpayments1.setId(null);
        assertThat(customerpayments1).isNotEqualTo(customerpayments2);
    }
}
