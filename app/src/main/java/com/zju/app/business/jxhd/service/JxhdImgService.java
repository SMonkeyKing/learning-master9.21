package com.zju.app.business.jxhd.service;

import com.zju.model.JxhdImgDO;
import com.zju.model.JxhdQuestionDO;
import com.zju.repository.JxhdImgRepos;
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
public class JxhdImgService {

    @Autowired
    JxhdImgRepos jxhdImgRepos;

    //找出截图推送表中所有截图信息
    public Page<JxhdImgDO> findAllImg(JxhdImgDO jxhdImgDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<JxhdQuestionDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return jxhdImgRepos.findAll(querySpecification, pageable);
    }

    public JxhdImgDO findOne(Integer id)
    {
        return jxhdImgRepos.findOne(id);
    }
    public void save(JxhdImgDO jxhdImgDO)
    {
        jxhdImgRepos.save(jxhdImgDO);
    }
}
