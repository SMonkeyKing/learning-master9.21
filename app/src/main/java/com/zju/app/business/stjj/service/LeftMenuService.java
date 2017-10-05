package com.zju.app.business.stjj.service;

import com.zju.app.constant.Constants;
import com.zju.model.LeftMenuDO;
import com.zju.repository.LeftMenuRepos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
* Created by lujie on 2017/8/14.
*/

@Service
public class LeftMenuService {

    private static Logger logger = LoggerFactory.getLogger(LeftMenuService.class);
    @Autowired
    LeftMenuRepos leftMenuRepos;

    public List<LeftMenuDO> findAll(Integer roleId) {
        Specification querySpecification = (Specification<LeftMenuDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //教师包含了两种列表
            if (Constants.Role.teacher.equals(roleId)) {
                predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("role").as(Integer.class), Constants.Role.teacher),
                        criteriaBuilder.equal(root.get("role").as(Integer.class), Constants.Role.student)));
            } else {
                predicates.add(criteriaBuilder.equal(root.get("role").as(Integer.class), Constants.Role.student));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        List<LeftMenuDO> leftmenulist = new ArrayList<>();
        leftmenulist = leftMenuRepos.findAll(querySpecification);
        logger.info("leftmenulist.size()" + leftmenulist.size());
        Map<Integer, LeftMenuDO> indexMenuMap = new HashMap<Integer, LeftMenuDO>();
        for (LeftMenuDO menu : leftmenulist) {
            indexMenuMap.put(menu.getId(), menu);
        }

        List<LeftMenuDO> roots = new ArrayList<LeftMenuDO>();
        for (LeftMenuDO leftmenu : leftmenulist) {
            if (leftmenu.getParentid() == 0) {
                roots.add(leftmenu);
            } else {
                LeftMenuDO parent = indexMenuMap.get(leftmenu.getParentid());
                if (parent != null) {
                    parent.getSubmenus().add(leftmenu);
                }
            }

        }
        return roots;
    }

    public LeftMenuDO findOne(Integer id) {
        return leftMenuRepos.findOne(id);
    }
}
