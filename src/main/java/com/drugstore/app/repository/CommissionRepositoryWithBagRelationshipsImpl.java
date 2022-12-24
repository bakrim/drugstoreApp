package com.drugstore.app.repository;

import com.drugstore.app.domain.Commission;
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
public class CommissionRepositoryWithBagRelationshipsImpl implements CommissionRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Commission> fetchBagRelationships(Optional<Commission> commission) {
        return commission.map(this::fetchRepresentantLists);
    }

    @Override
    public Page<Commission> fetchBagRelationships(Page<Commission> commissions) {
        return new PageImpl<>(fetchBagRelationships(commissions.getContent()), commissions.getPageable(), commissions.getTotalElements());
    }

    @Override
    public List<Commission> fetchBagRelationships(List<Commission> commissions) {
        return Optional.of(commissions).map(this::fetchRepresentantLists).orElse(Collections.emptyList());
    }

    Commission fetchRepresentantLists(Commission result) {
        return entityManager
            .createQuery(
                "select commission from Commission commission left join fetch commission.representantLists where commission is :commission",
                Commission.class
            )
            .setParameter("commission", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Commission> fetchRepresentantLists(List<Commission> commissions) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, commissions.size()).forEach(index -> order.put(commissions.get(index).getId(), index));
        List<Commission> result = entityManager
            .createQuery(
                "select distinct commission from Commission commission left join fetch commission.representantLists where commission in :commissions",
                Commission.class
            )
            .setParameter("commissions", commissions)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
