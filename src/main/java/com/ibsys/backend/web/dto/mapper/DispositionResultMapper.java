package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.DispositionEigenfertigungResult;
import com.ibsys.backend.web.dto.DispositionEigenfertigungArticleResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DispositionResultMapper {

    @Mapping(source = "dispositinEigenfertigungResultId.articleNumber", target = "articleNumber")
    DispositionEigenfertigungArticleResultDTO toDTO(DispositionEigenfertigungResult dispositionEigenfertigungResult);

}
