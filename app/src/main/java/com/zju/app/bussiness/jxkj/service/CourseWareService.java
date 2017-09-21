package com.zju.app.bussiness.jxkj.service;

import com.zju.model.CourseWareDO;
import com.zju.repository.CourseWareRepos;
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
 * Created by xujingfeng on 2017/8/11.
 */
@Service
public class CourseWareService {

    @Autowired
    CourseWareRepos courseWareRepos;

    //后台查询课件列表
    public Page<CourseWareDO> findAll(Integer id,CourseWareDO courseWareDO, Pageable pageable) {
        Specification querySpecification = (Specification<CourseWareDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            //名称
            //查询
            //Integer idInt = Integer.parseInt(id);
            predicates.add(criteriaBuilder.equal(root.get("typeid").as(Integer.class),id));
            if (!Lang.isEmpty(courseWareDO.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), String.format("%%%s%%", courseWareDO.getName()
                        .trim()
                )));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return courseWareRepos.findAll(querySpecification, pageable);
    }


    public List<CourseWareDO> findLpList(Integer id)
    {
        Specification querySpecification = (Specification<CourseWareDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            predicates.add(criteriaBuilder.equal(root.get("typeid").as(Integer.class),id));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return courseWareRepos.findAll(querySpecification);
    }
    public CourseWareDO findOne(Integer id) {
        return courseWareRepos.findOne(id);
    }

    public CourseWareDO save(CourseWareDO courseWare) {
        return courseWareRepos.save(courseWare);
    }

    public void save(List<CourseWareDO> courseWareDOList) {
        courseWareRepos.save(courseWareDOList);
    }
}
