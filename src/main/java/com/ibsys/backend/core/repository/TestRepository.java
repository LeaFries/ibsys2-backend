package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
