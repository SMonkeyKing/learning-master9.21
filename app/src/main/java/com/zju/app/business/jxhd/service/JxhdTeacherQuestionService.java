package com.zju.app.business.jxhd.service;

import com.zju.model.JxhdTeacherQuestionDO;
import com.zju.model.JxhdTeacherQuestionDO;
import com.zju.repository.JxhdQuestionRepos;
import com.zju.repository.JxhdTeacherQuestionRepos;
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
 * Created by lujie on 2017/9/10.
 */
@Service
public class JxhdTeacherQuestionService {

    @Autowired
    JxhdTeacherQuestionRepos jxhdTeacherQuestionRepos;

    public Page<JxhdTeacherQuestionDO> findAllQuestion(Integer type,JxhdTeacherQuestionDO JxhdTeacherQuestionDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<JxhdTeacherQuestionDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            //predicates.add(criteriaBuilder.equal(root.get("category").as(Integer.class),2));
            if(type>0)
            {
                predicates.add(criteriaBuilder.equal(root.get("type").as(Integer.class),type));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return jxhdTeacherQuestionRepos.findAll(querySpecification, pageable);
    }

    public JxhdTeacherQuestionDO save(JxhdTeacherQuestionDO JxhdTeacherQuestionDO)
    {
        return jxhdTeacherQuestionRepos.save(JxhdTeacherQuestionDO);
    }

    public void save(List<JxhdTeacherQuestionDO> JxhdTeacherQuestionDOList) {
        jxhdTeacherQuestionRepos.save(JxhdTeacherQuestionDOList);
    }

    public JxhdTeacherQuestionDO findOne(Integer id) {
        return jxhdTeacherQuestionRepos.findOne(id);
    }

    public void delete(Integer id)
    {
        jxhdTeacherQuestionRepos.delete(id);
    }
}
