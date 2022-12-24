package com.drugstore.app.repository;

import com.drugstore.app.domain.DossierAutre;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DossierAutre entity.
 *
 * When extending this class, extend DossierAutreRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DossierAutreRepository extends DossierAutreRepositoryWithBagRelationships, JpaRepository<DossierAutre, Long> {
    @Query("select dossierAutre from DossierAutre dossierAutre where dossierAutre.user.login = ?#{principal.username}")
    List<DossierAutre> findByUserIsCurrentUser();

    default Optional<DossierAutre> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<DossierAutre> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<DossierAutre> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
