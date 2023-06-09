package com.ibsys.backend.core.repository;

import com.ibsys.backend.core.domain.entity.Article;
import com.ibsys.backend.core.domain.entity.ArticleWorkstationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleWorkstationPlanRepository extends JpaRepository<ArticleWorkstationPlan, Integer> {

    List<ArticleWorkstationPlan> findByArticleNumber(int articleNumber);

    ArticleWorkstationPlan findByWorkstationNumberAndArticleNumber(int workstationNumber, int articleNumber);

}
