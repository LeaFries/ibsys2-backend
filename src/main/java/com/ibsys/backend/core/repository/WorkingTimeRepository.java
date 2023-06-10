package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.OverTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingTimeRepository extends JpaRepository<OverTime, Long> {

    OverTime findByStation(int station);

}
