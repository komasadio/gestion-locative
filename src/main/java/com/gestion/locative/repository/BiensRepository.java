package com.gestion.locative.repository;

import com.gestion.locative.domain.Biens;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Biens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BiensRepository extends JpaRepository<Biens, Long> {
}
