package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.Waitinglist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaitinglistRepository extends JpaRepository<Waitinglist, Integer> {

}
