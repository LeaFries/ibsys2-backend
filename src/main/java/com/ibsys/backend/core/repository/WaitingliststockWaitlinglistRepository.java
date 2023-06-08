package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.WaitingliststockWaitinglist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface WaitingliststockWaitlinglistRepository extends JpaRepository<WaitingliststockWaitinglist, Long> {

    ArrayList<WaitingliststockWaitinglist> findByItem(final int item);

}
