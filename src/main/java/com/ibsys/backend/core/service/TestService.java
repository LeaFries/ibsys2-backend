package com.ibsys.backend.core.service;

import com.ibsys.backend.core.domain.Test;
import com.ibsys.backend.core.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public List<Test> findAll() {
        return testRepository.findAll();
    }

}
