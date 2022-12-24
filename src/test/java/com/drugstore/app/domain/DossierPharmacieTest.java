package com.drugstore.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.drugstore.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DossierPharmacieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DossierPharmacie.class);
        DossierPharmacie dossierPharmacie1 = new DossierPharmacie();
        dossierPharmacie1.setId(1L);
        DossierPharmacie dossierPharmacie2 = new DossierPharmacie();
        dossierPharmacie2.setId(dossierPharmacie1.getId());
        assertThat(dossierPharmacie1).isEqualTo(dossierPharmacie2);
        dossierPharmacie2.setId(2L);
        assertThat(dossierPharmacie1).isNotEqualTo(dossierPharmacie2);
        dossierPharmacie1.setId(null);
        assertThat(dossierPharmacie1).isNotEqualTo(dossierPharmacie2);
    }
}
