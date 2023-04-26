package com.ibsys.backend.web;

import com.ibsys.backend.core.domain.Test;
import com.ibsys.backend.core.service.TestService;
import com.ibsys.backend.web.dto.TestDTO;
import com.ibsys.backend.web.dto.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestResource {

    private final TestService testService;
    private final TestMapper testMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> testConnection() {
        return ResponseEntity.ok("Successfully tested connection to Tomcat Webservice");
    }

    @GetMapping(path = {"/databaseconnection"},
                produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<TestDTO>> testDatabaseConnection() {
        List<Test> foundTest = testService.findAll();
        List<TestDTO> resultTests = foundTest.stream().map(testMapper::toTestDTO).toList();
        return ResponseEntity.ok(resultTests);
    }
}
