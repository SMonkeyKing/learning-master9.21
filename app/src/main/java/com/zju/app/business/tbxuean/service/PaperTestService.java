package com.zju.app.business.tbxuean.service;

import com.zju.model.PaperTestQuestionDO;
import com.zju.repository.PaperTestQuestionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/10/7.
 */
@Service
public class PaperTestService {

    @Autowired
    PaperTestQuestionRepos paperTestQuestionRepos;

    public void save(PaperTestQuestionDO paperTestQuestionDO)
    {
        paperTestQuestionRepos.save(paperTestQuestionDO);
    }

    //找出某张试卷的所有选择题
    public List<PaperTestQuestionDO> findAllByPaperId(Integer paperid)
    {
        Specification querySpecification = (Specification<PaperTestQuestionDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("paperid").as(Integer.class),paperid));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return paperTestQuestionRepos.findAll(querySpecification);
    }
}
