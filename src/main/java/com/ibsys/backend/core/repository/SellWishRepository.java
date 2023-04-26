package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.SellWish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellWishRepository extends JpaRepository<SellWish, Long> {
}
