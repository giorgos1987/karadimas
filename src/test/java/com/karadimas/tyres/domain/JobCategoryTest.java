package com.karadimas.tyres.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.karadimas.tyres.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobCategory.class);
        JobCategory jobCategory1 = new JobCategory();
        jobCategory1.setId(1L);
        JobCategory jobCategory2 = new JobCategory();
        jobCategory2.setId(jobCategory1.getId());
        assertThat(jobCategory1).isEqualTo(jobCategory2);
        jobCategory2.setId(2L);
        assertThat(jobCategory1).isNotEqualTo(jobCategory2);
        jobCategory1.setId(null);
        assertThat(jobCategory1).isNotEqualTo(jobCategory2);
    }
}
