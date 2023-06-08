package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.KQuantityNeed;
import com.ibsys.backend.core.domain.entity.PurchasePartDisposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KQuantityNeedRepository extends JpaRepository<KQuantityNeed, Long> {
}
