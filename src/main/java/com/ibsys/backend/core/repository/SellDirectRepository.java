package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.SellDirect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellDirectRepository extends JpaRepository<SellDirect, Long> {
}
