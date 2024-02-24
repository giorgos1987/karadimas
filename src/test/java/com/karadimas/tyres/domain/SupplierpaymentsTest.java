package com.karadimas.tyres.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.karadimas.tyres.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplierpaymentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supplierpayments.class);
        Supplierpayments supplierpayments1 = new Supplierpayments();
        supplierpayments1.setId(1L);
        Supplierpayments supplierpayments2 = new Supplierpayments();
        supplierpayments2.setId(supplierpayments1.getId());
        assertThat(supplierpayments1).isEqualTo(supplierpayments2);
        supplierpayments2.setId(2L);
        assertThat(supplierpayments1).isNotEqualTo(supplierpayments2);
        supplierpayments1.setId(null);
        assertThat(supplierpayments1).isNotEqualTo(supplierpayments2);
    }
}
