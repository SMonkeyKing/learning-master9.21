package com.zju.app.business.jxhd.service;

import com.zju.model.JxhdPushDO;
import com.zju.repository.JxhdPushRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/8/25.
 */
@Service
public class JxhdPushService {

    @Autowired
    JxhdPushRepos jxhdPushRepos;

    public void save(JxhdPushDO jxhdPushDO)
    {
        jxhdPushRepos.save(jxhdPushDO);
    }

    public List<JxhdPushDO> findAll()
    {
        Specification querySpecification = new Specification<JxhdPushDO>()
        {
            @Override
            public Predicate toPredicate(Root<JxhdPushDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return jxhdPushRepos.findAll(querySpecification,new Sort(Sort.Direction.ASC, "category"));
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
        return jxhdPushRepos.count(querySpecification);
    }
}
