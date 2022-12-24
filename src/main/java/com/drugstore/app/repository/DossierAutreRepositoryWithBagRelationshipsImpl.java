package com.drugstore.app.repository;

import com.drugstore.app.domain.DossierAutre;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DossierAutreRepositoryWithBagRelationshipsImpl implements DossierAutreRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DossierAutre> fetchBagRelationships(Optional<DossierAutre> dossierAutre) {
        return dossierAutre.map(this::fetchDocumentLists);
    }

    @Override
    public Page<DossierAutre> fetchBagRelationships(Page<DossierAutre> dossierAutres) {
        return new PageImpl<>(
            fetchBagRelationships(dossierAutres.getContent()),
            dossierAutres.getPageable(),
            dossierAutres.getTotalElements()
        );
    }

    @Override
    public List<DossierAutre> fetchBagRelationships(List<DossierAutre> dossierAutres) {
        return Optional.of(dossierAutres).map(this::fetchDocumentLists).orElse(Collections.emptyList());
    }

    DossierAutre fetchDocumentLists(DossierAutre result) {
        return entityManager
            .createQuery(
                "select dossierAutre from DossierAutre dossierAutre left join fetch dossierAutre.documentLists where dossierAutre is :dossierAutre",
                DossierAutre.class
            )
            .setParameter("dossierAutre", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DossierAutre> fetchDocumentLists(List<DossierAutre> dossierAutres) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, dossierAutres.size()).forEach(index -> order.put(dossierAutres.get(index).getId(), index));
        List<DossierAutre> result = entityManager
            .createQuery(
                "select distinct dossierAutre from DossierAutre dossierAutre left join fetch dossierAutre.documentLists where dossierAutre in :dossierAutres",
                DossierAutre.class
            )
            .setParameter("dossierAutres", dossierAutres)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
