package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.ArticleWorkstationPlan;
import com.ibsys.backend.core.domain.entity.CapacityPlanColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacityPlanColumnRepository extends JpaRepository<CapacityPlanColumn, Integer> {

}
