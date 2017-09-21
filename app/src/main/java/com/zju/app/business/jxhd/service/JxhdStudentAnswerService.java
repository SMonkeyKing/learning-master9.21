package com.zju.app.business.jxhd.service;

import com.zju.model.JudgeAnswerDO;
import com.zju.model.JxhdStudentAnswerDO;
import com.zju.repository.JxhdStudentAnswerRepos;
import com.zju.utils.Lang;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/8/26.
 */
@Service
public class JxhdStudentAnswerService {

    @Autowired
    JxhdStudentAnswerRepos jxhdStudentAnswerRepos;

    //private static EntityManagerFactory entityManagerFactory;
    //EntityManager em = entityManagerFactory.createEntityManager();

    @PersistenceContext
    private EntityManager entityManager;

    public void save(JxhdStudentAnswerDO jxhdStudentAnswerDO)
    {
        jxhdStudentAnswerRepos.save(jxhdStudentAnswerDO);
    }

    public Page<JxhdStudentAnswerDO> findAll(Integer paperid,JxhdStudentAnswerDO jxhdStudentAnswerDO,Pageable pageable)
    {
        Specification querySpecification = (Specification<JxhdStudentAnswerDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            if(!Lang.isEmpty(paperid))
            {
                predicates.add(criteriaBuilder.equal(root.get("paperId").as(Integer.class),paperid));
            }
            //predicates.add((Predicate) criteriaBuilder.asc(root.get("answer")).getExpression());
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return jxhdStudentAnswerRepos.findAll(querySpecification, pageable);
    }

    //获取每道题选A，B，C,D的人数和正确的人数（正确率统计）
    public List<JudgeAnswerDO> findJudgeAnswerList()
    {
        //return jxhdStudentAnswerRepos.getJudgeQuestionAnswerList();
        String sql="select paper_id,\n" +
                "sum(case when answer='A' then 1 else 0 end) as answerA,\n" +
                "sum(case when answer='B' then 1 else 0 end) as answerB,\n" +
                "sum(case when answer='C' then 1 else 0 end) as answerC,\n" +
                "sum(case when answer='D' then 1 else 0 end) as answerD,\n" +
                "sum(case when correct=1 then 1 else 0 end) as correct,\n" +
                "1 as rate "+
                "from learning.jxhd_student_answer\n" +
                "group by paper_id";
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class)
                .addScalar("paper_id", StandardBasicTypes.INTEGER)
                .addScalar("answerA",StandardBasicTypes.INTEGER)
                .addScalar("answerB",StandardBasicTypes.INTEGER)
                .addScalar("answerC",StandardBasicTypes.INTEGER)
                .addScalar("answerD",StandardBasicTypes.INTEGER)
                .addScalar("correct",StandardBasicTypes.INTEGER)
                .addScalar("rate",StandardBasicTypes.DOUBLE)
                .setResultTransformer(Transformers.aliasToBean(JudgeAnswerDO.class));;
        return query.getResultList();
    }

    public List<String> findOneQuestionStuList(Integer paperid,String type)
    {
        String sql="select username from learning.jxhd_student_answer WHERE  paper_id="+paperid+"and answer='"+type+"''";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

}
