package com.zju.app.business.jxhd.service;

import com.zju.model.JxhdPushImgDO;
import com.zju.model.JxhdQuestionDO;
import com.zju.repository.JxhdImgRepos;
import com.zju.repository.JxhdPushImgRepos;
import com.zju.utils.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
public class JxhdPushImgService {

    @Autowired
    JxhdPushImgRepos jxhdPushImgRepos;

    public void save(JxhdPushImgDO jxhdPushImgDO)
    {
        jxhdPushImgRepos.save(jxhdPushImgDO);
    }

    public List<JxhdPushImgDO> findAll()
    {
        Specification querySpecification = new Specification<JxhdPushImgDO>()
        {
            @Override
            public Predicate toPredicate(Root<JxhdPushImgDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return jxhdPushImgRepos.findAll(querySpecification,new Sort(Sort.Direction.ASC, "qid"));
    }
}
