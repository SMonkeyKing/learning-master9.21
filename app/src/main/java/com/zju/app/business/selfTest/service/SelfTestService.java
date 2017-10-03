package com.zju.app.business.selfTest.service;

import com.zju.app.business.tbxuean.service.SyncTestFromTKService;
import com.zju.model.UnitTestFromTKDO;
import com.zju.repository.SyncTestFromTKRepos;
import com.zju.repository.UnitTestFromTKRepos;
import com.zju.utils.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/9/28.
 */
@Service
@Transactional
public class SelfTestService {

    private static Logger logger = LoggerFactory.getLogger(SelfTestService.class);

    @Autowired
    UnitTestFromTKRepos unitTestFromTKRepos;

    public List<UnitTestFromTKDO> findAllList(Integer typeid, Integer questionType,UnitTestFromTKDO UnitTestFromTKDO)
    {
        Specification querySpecification = (Specification<UnitTestFromTKDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            if(!Lang.isEmpty(typeid))
            {
                predicates.add(criteriaBuilder.equal(root.get("typeId").as(Integer.class),typeid));
            }
            if(!Lang.isEmpty(questionType) && questionType!=0)
            {
                predicates.add(criteriaBuilder.equal(root.get("questionType").as(Integer.class),questionType));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        //按照题型进行排序（选择题、填空题、计算题）
        return unitTestFromTKRepos.findAll(querySpecification,new Sort(Sort.Direction.ASC, "questionType"));
    }

    public Page<UnitTestFromTKDO> findAllSyncTests(Integer typeid, Integer questionType,UnitTestFromTKDO UnitTestFromTKDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<UnitTestFromTKDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            if(!Lang.isEmpty(typeid))
            {
                predicates.add(criteriaBuilder.equal(root.get("typeId").as(Integer.class),typeid));
            }
            if(!Lang.isEmpty(questionType) && questionType!=0)
            {
                predicates.add(criteriaBuilder.equal(root.get("questionType").as(Integer.class),questionType));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return unitTestFromTKRepos.findAll(querySpecification, pageable);
    }

    public UnitTestFromTKDO save(UnitTestFromTKDO unitTestFromTKDO)
    {
        return unitTestFromTKRepos.save(unitTestFromTKDO);
    }

    public UnitTestFromTKDO findOneByQuestionId(Integer qId)
    {
        return unitTestFromTKRepos.selectByQuestionId(qId);
    }
    public UnitTestFromTKDO findOne(Integer id) {
        return unitTestFromTKRepos.findOne(id);
    }

    public void delete(Integer id){
        unitTestFromTKRepos.delete(id);
    }

    public void deleteDO(UnitTestFromTKDO UnitTestFromTKDO){
        unitTestFromTKRepos.delete(UnitTestFromTKDO);
    }

    public void updateStuAns(Integer typeid)
    {
        unitTestFromTKRepos.updateStuAns(typeid);
    }
}


