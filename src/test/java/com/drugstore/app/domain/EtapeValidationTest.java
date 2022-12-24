package com.drugstore.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.drugstore.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EtapeValidationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapeValidation.class);
        EtapeValidation etapeValidation1 = new EtapeValidation();
        etapeValidation1.setId(1L);
        EtapeValidation etapeValidation2 = new EtapeValidation();
        etapeValidation2.setId(etapeValidation1.getId());
        assertThat(etapeValidation1).isEqualTo(etapeValidation2);
        etapeValidation2.setId(2L);
        assertThat(etapeValidation1).isNotEqualTo(etapeValidation2);
        etapeValidation1.setId(null);
        assertThat(etapeValidation1).isNotEqualTo(etapeValidation2);
    }
}
