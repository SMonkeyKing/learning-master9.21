package com.zju.app.business.sczy.service;

import com.zju.model.AcademicJournalDO;
import com.zju.repository.AcademicJournalRepos;
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
 * Created by lujie on 2017/8/16.
 * 学科期刊Service
 */
@Service
public class AcademicJournalService {

    @Autowired
    AcademicJournalRepos academicJournalRepos;

    public Page<AcademicJournalDO> findAll(AcademicJournalDO academicJournalDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<AcademicJournalDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            //名称
            //查询
            if (!Lang.isEmpty(academicJournalDO.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), String.format("%%%s%%", academicJournalDO.getName()
                        .trim()
                )));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return academicJournalRepos.findAll(querySpecification, pageable);
    }

    public AcademicJournalDO save(AcademicJournalDO academicJournalDO)
    {
        return academicJournalRepos.save(academicJournalDO);
    }

    public AcademicJournalDO findOne(Integer id) {
        return academicJournalRepos.findOne(id);
    }
}
