package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.web.dto.ArticleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(source = "period", target = "period")
    Article toArticle(ArticleDTO articleDTO, Integer period);

    Article toArticle(ArticleDTO articleDTO);

}
