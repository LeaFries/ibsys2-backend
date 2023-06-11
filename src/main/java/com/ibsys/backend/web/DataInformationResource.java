package com.ibsys.backend.web;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.core.service.DataInformationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/datainfo")
public class DataInformationResource {
    private final DataInformationService dataInformationService;

    @Operation(summary = "This endpoint retrieves all articles and their information")
    @GetMapping("/articles")
    public List<Article> findArticles() {
        return dataInformationService.findAtricles();
    }
}
