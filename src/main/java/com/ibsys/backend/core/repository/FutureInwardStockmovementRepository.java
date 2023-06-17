package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.FutureInwardStockmovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FutureInwardStockmovementRepository extends JpaRepository<FutureInwardStockmovement, Long> {
}
