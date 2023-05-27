package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
}
