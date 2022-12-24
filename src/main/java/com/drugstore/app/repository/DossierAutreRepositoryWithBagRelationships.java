package com.drugstore.app.repository;

import com.drugstore.app.domain.DossierAutre;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DossierAutreRepositoryWithBagRelationships {
    Optional<DossierAutre> fetchBagRelationships(Optional<DossierAutre> dossierAutre);

    List<DossierAutre> fetchBagRelationships(List<DossierAutre> dossierAutres);

    Page<DossierAutre> fetchBagRelationships(Page<DossierAutre> dossierAutres);
}
