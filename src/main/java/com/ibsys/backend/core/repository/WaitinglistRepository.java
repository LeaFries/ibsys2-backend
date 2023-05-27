package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.Waitinglist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitinglistRepository extends JpaRepository<Waitinglist, Long> {
}
