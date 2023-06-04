package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.web.dto.ArticleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    Article toArticle(ArticleDTO articleDTO);

}
