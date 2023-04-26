package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.QualityControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualityControlRepository extends JpaRepository<QualityControl, Long> {
}
