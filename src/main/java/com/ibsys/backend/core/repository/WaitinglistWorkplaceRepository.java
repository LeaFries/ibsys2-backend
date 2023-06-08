package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.WaitinglistWorkplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface WaitinglistWorkplaceRepository extends JpaRepository<WaitinglistWorkplace, Long> {

    ArrayList<WaitinglistWorkplace> findByItem(final int item);

}
