package com.zju.app.bussiness.jxkj.service;

import com.zju.model.CourseVideoDO;
import com.zju.model.CourseWareDO;
import com.zju.repository.CourseVideoRepos;
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


@Service
public class CourseVideoService {

    @Autowired
    CourseVideoRepos courseVideoRepos;

    //后台查询课件列表
    public Page<CourseWareDO> findAll(Integer id,CourseVideoDO courseVideoDO, Pageable pageable) {
        Specification querySpecification = (Specification<CourseWareDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            //名称
            //查询
            //Integer idInt = Integer.parseInt(id);
            predicates.add(criteriaBuilder.equal(root.get("typeid").as(Integer.class),id));
            if (!Lang.isEmpty(courseVideoDO.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), String.format("%%%s%%", courseVideoDO.getName()
                        .trim()
                )));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return courseVideoRepos.findAll(querySpecification, pageable);
    }


    public List<CourseVideoDO> findLpList(Integer id)
    {
        Specification querySpecification = (Specification<CourseWareDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            predicates.add(criteriaBuilder.equal(root.get("typeid").as(Integer.class),id));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return courseVideoRepos.findAll(querySpecification);
    }
    public CourseVideoDO findOne(Integer id) {
        return courseVideoRepos.findOne(id);
    }

    public CourseVideoDO save(CourseVideoDO courseVideoDO) {
        return courseVideoRepos.save(courseVideoDO);
    }

    public void save(List<CourseVideoDO> courseVideoDOList) {
        courseVideoRepos.save(courseVideoDOList);
    }
}
