package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {
    Optional<Production> findProductionByArticle(Integer article);
}
