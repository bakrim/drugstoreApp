package com.drugstore.app.repository;

import com.drugstore.app.domain.DossierPharmacie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DossierPharmacieRepositoryWithBagRelationships {
    Optional<DossierPharmacie> fetchBagRelationships(Optional<DossierPharmacie> dossierPharmacie);

    List<DossierPharmacie> fetchBagRelationships(List<DossierPharmacie> dossierPharmacies);

    Page<DossierPharmacie> fetchBagRelationships(Page<DossierPharmacie> dossierPharmacies);
}
