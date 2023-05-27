package com.ibsys.backend.web;

import com.ibsys.backend.core.service.DispositionEigenfertigungService;
import com.ibsys.backend.web.dto.ArticleDTO;
import com.ibsys.backend.web.dto.mapper.ArticleMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/disposition-eigenfertigung")
public class DispositionEigenfertigungResource {

    private final DispositionEigenfertigungService dispositionEigenfertigungService;
    private final ArticleMapper articleMapper;

    @Operation(summary = "Starts the Disposition Eigenfertigung")
    @PostMapping
    public void startDispositionEigenfertigung(@RequestBody List<ArticleDTO> articleDTO) {
        dispositionEigenfertigungService.updateArticles(
                articleDTO.stream()
                        .map(articleMapper::toArticle)
                        .toList()
        );
    }

}
