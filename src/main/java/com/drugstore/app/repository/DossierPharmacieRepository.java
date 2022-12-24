package com.drugstore.app.repository;

import com.drugstore.app.domain.DossierPharmacie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DossierPharmacie entity.
 *
 * When extending this class, extend DossierPharmacieRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DossierPharmacieRepository extends DossierPharmacieRepositoryWithBagRelationships, JpaRepository<DossierPharmacie, Long> {
    @Query("select dossierPharmacie from DossierPharmacie dossierPharmacie where dossierPharmacie.user.login = ?#{principal.username}")
    List<DossierPharmacie> findByUserIsCurrentUser();

    default Optional<DossierPharmacie> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<DossierPharmacie> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<DossierPharmacie> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
