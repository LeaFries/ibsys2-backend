package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.ProductionInPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionInPeriodRepository extends JpaRepository<ProductionInPeriod, Long> {
}
