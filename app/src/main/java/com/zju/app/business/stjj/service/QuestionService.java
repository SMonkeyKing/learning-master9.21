package com.zju.app.business.stjj.service;

import com.zju.model.QuestionDO;
import com.zju.repository.QuestionRepos;
import com.zju.utils.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/8/13.
 */

@Service
public class QuestionService {

    @Autowired
    QuestionRepos questionRepos;

    public QuestionDO save(QuestionDO questionDO)
    {
        return  questionRepos.save(questionDO);
    }

    //type=0表示查询所有的题目
    //返回Page<>
    public Page<QuestionDO> findAll(Integer typeid,Integer type,QuestionDO questionDO, Pageable pageable)
    {
        Specification querySpecification = new Specification<QuestionDO>()
        {
            @Override
            public Predicate toPredicate(Root<QuestionDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
                //查询
                //按题型进行查询
                if (type!=null)
                {
                    if(type!=0)
                    {
                        predicates.add(criteriaBuilder.equal(root.get("type").as(Integer.class),type));
                    }
                }

                if(!Lang.isEmpty(typeid))
                {
                    predicates.add(criteriaBuilder.equal(root.get("typeid").as(Integer.class),typeid));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return questionRepos.findAll(querySpecification, pageable);
    }


    //type=0表示查询所有的题目
    //返回List<>
    public List<QuestionDO> findAllList(Integer type,QuestionDO questionDO)
    {
        Specification querySpecification = new Specification<QuestionDO>()
        {
            @Override
            public Predicate toPredicate(Root<QuestionDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
                //查询
                //按题型进行查询
                if(type>0)
                {
                    predicates.add(criteriaBuilder.equal(root.get("type").as(Integer.class),type));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return questionRepos.findAll(querySpecification);
    }

    public List<QuestionDO> findAllQuestionList(Integer typeid,Integer type,QuestionDO questionDO)
    {
        Specification querySpecification = new Specification<QuestionDO>()
        {
            @Override
            public Predicate toPredicate(Root<QuestionDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
                //查询
                //按题型进行查询
                if(type>0)
                {
                    predicates.add(criteriaBuilder.equal(root.get("type").as(Integer.class),type));
                }

                if(!Lang.isEmpty(typeid))
                {
                    predicates.add(criteriaBuilder.equal(root.get("typeid").as(Integer.class),typeid));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return questionRepos.findAll(querySpecification);
    }
    public QuestionDO findOne(Integer id)
    {
        return  questionRepos.findOne(id);
    }


}
