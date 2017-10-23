package com.zju.app.business.jxhd.service;

import com.zju.model.JxhdPushDO;
import com.zju.model.JxhdPushQuestionDO;
import com.zju.repository.JxhdPushQuestionRepos;
import com.zju.repository.JxhdPushRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/9/11.
 */
@Service
@Transactional
public class JxhdPushQuestionService {

    @Autowired
    JxhdPushQuestionRepos jxhdPushQuestionRepos;

    public void save(JxhdPushQuestionDO jxhdPushQuestionDO)
    {
        jxhdPushQuestionRepos.save(jxhdPushQuestionDO);
    }

    public void delete(String date)
    {
        jxhdPushQuestionRepos.delete(date);
    }

    public void deletePushQuestion(Integer id)
    {
        jxhdPushQuestionRepos.delete(id);
    }
    //查推送表中的数据
    public List<JxhdPushQuestionDO> findAll()
    {
        Specification querySpecification = new Specification<JxhdPushQuestionDO>()
        {
            @Override
            public Predicate toPredicate(Root<JxhdPushQuestionDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return jxhdPushQuestionRepos.findAll(querySpecification,new Sort(Sort.Direction.ASC, "id"));
    }

    public long count()
    {
        Specification querySpecification = new Specification<JxhdPushDO>()
        {
            @Override
            public Predicate toPredicate(Root<JxhdPushDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return jxhdPushQuestionRepos.count(querySpecification);
    }
}
