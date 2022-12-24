package com.drugstore.app.repository;

import com.drugstore.app.domain.Commission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CommissionRepositoryWithBagRelationships {
    Optional<Commission> fetchBagRelationships(Optional<Commission> commission);

    List<Commission> fetchBagRelationships(List<Commission> commissions);

    Page<Commission> fetchBagRelationships(Page<Commission> commissions);
}
