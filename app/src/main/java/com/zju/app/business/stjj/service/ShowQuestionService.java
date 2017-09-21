package com.zju.app.business.stjj.service;

import com.zju.model.PaperDO;
import com.zju.model.QuestionDO;
import com.zju.repository.PaperRepos;
import com.zju.repository.QuestionRepos;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/8/20.
 */
@Service
public class ShowQuestionService {
    @Autowired
    PaperRepos paperRepos;
    @Autowired
    QuestionRepos questionRepos;

    public List<QuestionDO> findAllPaper(Integer paperId) {
        PaperDO paperDO1 = paperRepos.findOne(paperId);
        String ids = paperDO1.getQuestion_list();
        if (StringUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        String[] paperIds = ids.split(" ");
        List<Integer> questionIds = new ArrayList<>();
        for (String s : paperIds) {
            Integer id = Integer.valueOf(s);
            questionIds.add(id);
        }
        List<QuestionDO> questionDOs = questionRepos.findAll(questionIds);
        Specification querySpecification = new Specification<QuestionDO>()
        {
            @Override
            public Predicate toPredicate(Root<QuestionDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
                CriteriaBuilder.In in = criteriaBuilder.in(root.get("id").as(Integer.class));
                for (Integer qId : questionIds) {
                    in.value(qId);
                }
                predicates.add(in);
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        //List<QuestionDO> questionDOList = questionRepos.findAll(questionIds,new Sort(Sort.Direction.ASC, "type"));
        return questionRepos.findAll(querySpecification,new Sort(Sort.Direction.ASC, "type"));

    }
}
