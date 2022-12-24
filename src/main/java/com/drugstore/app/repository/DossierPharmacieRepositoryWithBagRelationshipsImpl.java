package com.drugstore.app.repository;

import com.drugstore.app.domain.DossierPharmacie;
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
public class DossierPharmacieRepositoryWithBagRelationshipsImpl implements DossierPharmacieRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DossierPharmacie> fetchBagRelationships(Optional<DossierPharmacie> dossierPharmacie) {
        return dossierPharmacie.map(this::fetchDocumentLists);
    }

    @Override
    public Page<DossierPharmacie> fetchBagRelationships(Page<DossierPharmacie> dossierPharmacies) {
        return new PageImpl<>(
            fetchBagRelationships(dossierPharmacies.getContent()),
            dossierPharmacies.getPageable(),
            dossierPharmacies.getTotalElements()
        );
    }

    @Override
    public List<DossierPharmacie> fetchBagRelationships(List<DossierPharmacie> dossierPharmacies) {
        return Optional.of(dossierPharmacies).map(this::fetchDocumentLists).orElse(Collections.emptyList());
    }

    DossierPharmacie fetchDocumentLists(DossierPharmacie result) {
        return entityManager
            .createQuery(
                "select dossierPharmacie from DossierPharmacie dossierPharmacie left join fetch dossierPharmacie.documentLists where dossierPharmacie is :dossierPharmacie",
                DossierPharmacie.class
            )
            .setParameter("dossierPharmacie", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DossierPharmacie> fetchDocumentLists(List<DossierPharmacie> dossierPharmacies) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, dossierPharmacies.size()).forEach(index -> order.put(dossierPharmacies.get(index).getId(), index));
        List<DossierPharmacie> result = entityManager
            .createQuery(
                "select distinct dossierPharmacie from DossierPharmacie dossierPharmacie left join fetch dossierPharmacie.documentLists where dossierPharmacie in :dossierPharmacies",
                DossierPharmacie.class
            )
            .setParameter("dossierPharmacies", dossierPharmacies)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
