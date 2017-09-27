package com.zju.app.business.tbxuean.service;

import com.zju.model.SyncTestDO;
import com.zju.model.SyncTestFromTKDO;
import com.zju.repository.SysnTestRepos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/8/19.
 */
@Service
public class SyncTestService {

    private static Logger logger = LoggerFactory.getLogger(SyncTestService.class);
    @Autowired
    SysnTestRepos sysnTestRepos;

    public Page<SyncTestDO> findAll(List<Integer> lpIds, SyncTestDO syncTestDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<SyncTestFromTKDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));

            //Path<Integer> path = root.get("lpId");

            //名称
            //查询
            /*if (!Lang.isEmpty(syncTestDO.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), String.format("%%%s%%", syncTestDO.getName()
                        .trim()
                )));
            }*/

            /*CriteriaBuilder.In in = criteriaBuilder.in(root.get("lpId").as(Integer.class));
            for (Integer lpId : lpIds) {
                in.value(lpId);
            }
            predicates.add(in);*/

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return sysnTestRepos.findAll(querySpecification, pageable);
    }

    public SyncTestDO save(SyncTestDO syncTestDO)
    {
        return sysnTestRepos.save(syncTestDO);
    }

    public SyncTestDO findOne(Integer id) {
        return sysnTestRepos.findOne(id);
    }

}
