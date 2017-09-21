package com.zju.app.business.jxhd.service;

import com.zju.model.JxhdQuestionDO;
import com.zju.repository.JxhdQuestionRepos;
import com.zju.utils.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/8/25.
 */
@Service
public class JxhdQuestionService {

    @Autowired
    JxhdQuestionRepos jxhdQuestionRepos;

    public Page<JxhdQuestionDO> findAllPaper(JxhdQuestionDO jxhdQuestionDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<JxhdQuestionDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            predicates.add(criteriaBuilder.equal(root.get("category").as(Integer.class),1));
            if (!Lang.isEmpty(jxhdQuestionDO.getPaperName())) {
                predicates.add(criteriaBuilder.like(root.get("paperName"), String.format("%%%s%%", jxhdQuestionDO.getPaperName()
                        .trim()
                )));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return jxhdQuestionRepos.findAll(querySpecification, pageable);
    }

    public Page<JxhdQuestionDO> findAllQuestion(Integer type,JxhdQuestionDO jxhdQuestionDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<JxhdQuestionDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            predicates.add(criteriaBuilder.equal(root.get("category").as(Integer.class),2));
            if(type>0)
            {
                predicates.add(criteriaBuilder.equal(root.get("type").as(Integer.class),type));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return jxhdQuestionRepos.findAll(querySpecification, pageable);
    }

    public JxhdQuestionDO save(JxhdQuestionDO jxhdQuestionDO)
    {
        return jxhdQuestionRepos.save(jxhdQuestionDO);
    }

    public void save(List<JxhdQuestionDO> jxhdQuestionDOList) {
        jxhdQuestionRepos.save(jxhdQuestionDOList);
    }

    public JxhdQuestionDO findOne(Integer id) {
        return jxhdQuestionRepos.findOne(id);
    }

}
