package com.drugstore.app.repository;

import com.drugstore.app.domain.Representant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Representant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepresentantRepository extends JpaRepository<Representant, Long> {}
