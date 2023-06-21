package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.DispositinEigenfertigungResultId;
import com.ibsys.backend.core.domain.entity.DispositionEigenfertigungResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositionEigenfertigungRepository extends JpaRepository<DispositionEigenfertigungResult, DispositinEigenfertigungResultId> {
}
