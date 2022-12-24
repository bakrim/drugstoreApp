package com.drugstore.app.repository;

import com.drugstore.app.domain.EtapeValidation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EtapeValidation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapeValidationRepository extends JpaRepository<EtapeValidation, Long> {}
