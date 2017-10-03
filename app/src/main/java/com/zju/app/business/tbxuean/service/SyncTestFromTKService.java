package com.zju.app.business.tbxuean.service;

import com.zju.model.SyncTestFromTKDO;
import com.zju.repository.SyncTestFromTKRepos;
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
 * Created by lujie on 2017/9/25.
 */
@Service
@Transactional
public class SyncTestFromTKService {

    private static Logger logger = LoggerFactory.getLogger(SyncTestFromTKService.class);

    @Autowired
    SyncTestFromTKRepos syncTestFromTKRepos;

    public Page<SyncTestFromTKDO> findAll(List<Integer> lpIds, SyncTestFromTKDO syncTestFromTKDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<SyncTestFromTKDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return syncTestFromTKRepos.findAll(querySpecification, pageable);
    }

    public List<SyncTestFromTKDO> findAllList(Integer typeid, Integer questionType,SyncTestFromTKDO syncTestFromTKDO)
    {
        Specification querySpecification = (Specification<SyncTestFromTKDO>) (root, criteriaQuery, criteriaBuilder) -> {
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
        return syncTestFromTKRepos.findAll(querySpecification,new Sort(Sort.Direction.ASC, "questionType"));
    }

    public Page<SyncTestFromTKDO> findAllSyncTests(Integer typeid, Integer questionType,SyncTestFromTKDO syncTestFromTKDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<SyncTestFromTKDO>) (root, criteriaQuery, criteriaBuilder) -> {
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
        return syncTestFromTKRepos.findAll(querySpecification, pageable);
    }

    public SyncTestFromTKDO save(SyncTestFromTKDO syncTestFromTKDO)
    {
        return syncTestFromTKRepos.save(syncTestFromTKDO);
    }

    public SyncTestFromTKDO findOneByQuestionId(Integer qId)
    {
        return syncTestFromTKRepos.selectByQuestionId(qId);
    }
    public SyncTestFromTKDO findOne(Integer id) {
        return syncTestFromTKRepos.findOne(id);
    }

    public void delete(Integer id){
        syncTestFromTKRepos.delete(id);
    }

    public void deleteDO(SyncTestFromTKDO syncTestFromTKDO){
        syncTestFromTKRepos.delete(syncTestFromTKDO);
    }

    public void updateStuAns(Integer typeid)
    {
        syncTestFromTKRepos.updateStuAns(typeid);
    }
}
