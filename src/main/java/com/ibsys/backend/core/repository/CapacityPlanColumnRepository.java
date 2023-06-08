package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.ArticleWorkstationPlan;
import com.ibsys.backend.core.domain.entity.CapacityPlanColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapacityPlanColumnRepository extends JpaRepository<CapacityPlanColumn, Integer> {

    //@Query(value = "select workstation_number, sum(working_time) from capacity_plan group by workstation_number;")
    //public int sumWorkingTime();

    List<CapacityPlanColumn> findByWorkstationNumber(int workstationNumber);

}
