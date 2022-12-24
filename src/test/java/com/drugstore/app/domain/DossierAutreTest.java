package com.drugstore.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.drugstore.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DossierAutreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DossierAutre.class);
        DossierAutre dossierAutre1 = new DossierAutre();
        dossierAutre1.setId(1L);
        DossierAutre dossierAutre2 = new DossierAutre();
        dossierAutre2.setId(dossierAutre1.getId());
        assertThat(dossierAutre1).isEqualTo(dossierAutre2);
        dossierAutre2.setId(2L);
        assertThat(dossierAutre1).isNotEqualTo(dossierAutre2);
        dossierAutre1.setId(null);
        assertThat(dossierAutre1).isNotEqualTo(dossierAutre2);
    }
}
