package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.Order;
import com.ibsys.backend.core.domain.entity.PlannedWarehouseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedWarehouseStockRepository extends JpaRepository<PlannedWarehouseStock, Long> {
}
