package com.zju.app.business.tbxuean.service;

import com.zju.model.TbxaLearningPlanDO;
import com.zju.repository.TbxaLearningPlanRepos;
import com.zju.utils.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class TbxaLearningPlanService {

    @Autowired
    TbxaLearningPlanRepos tbxaLearningPlanRepos;

    public Page<TbxaLearningPlanDO> findAll(Integer typeId, TbxaLearningPlanDO tbxaLearningPlanDO,Pageable pageable) {
        Specification querySpecification = (Specification<TbxaLearningPlanDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("typeid").as(Integer.class),typeId));
            //查询的条件 名称
            if (!Lang.isEmpty(tbxaLearningPlanDO.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), String.format("%%%s%%", tbxaLearningPlanDO.getName()
                        .trim()
                )));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return tbxaLearningPlanRepos.findAll(querySpecification,pageable);
    }

    public TbxaLearningPlanDO findOne(Integer id) {
        return tbxaLearningPlanRepos.findOne(id);
    }

}
