package com.zju.app.business.tbxuean.service;

import com.zju.model.QuestionDO;
import com.zju.model.TbxaQuestionStudentAnsDO;
import com.zju.model.TbxaStudentAnswerDO;
import com.zju.repository.QuestionRepos;
import com.zju.repository.TbxaQuestionStudentAnsRepos;
import com.zju.repository.TbxaStudentAnswerRepos;
import com.zju.utils.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/8/19.
 */
@Service
public class StudentAnswerService {

    private static Logger logger = LoggerFactory.getLogger(StudentAnswerService.class);

    @Autowired
    TbxaStudentAnswerRepos tbxaStudentAnswerRepos;

    @Autowired
    TbxaQuestionStudentAnsRepos tbxaQuestionStudentAnsRepos;

    @Autowired
    QuestionRepos questionRepos;

    public Page<TbxaStudentAnswerDO> findAll(TbxaStudentAnswerDO tbxaStudentAnswerDO, Pageable pageable)
    {
        Specification querySpecification = (Specification<TbxaStudentAnswerDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            //predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));
            //名称
            //查询
            /*if (!Lang.isEmpty(tbxaStudentAnswerDO.getTestId())) {
                predicates.add(criteriaBuilder.like(root.get("testId"), tbxaStudentAnswerDO.getTestId()));

            }*/
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return tbxaStudentAnswerRepos.findAll(querySpecification, pageable);
    }

    public TbxaStudentAnswerDO save(TbxaStudentAnswerDO tbxaStudentAnswerDO)
    {
        logger.info("hello");
        return tbxaStudentAnswerRepos.save(tbxaStudentAnswerDO);
    }

    public TbxaStudentAnswerDO findOne(Integer id) {
        return tbxaStudentAnswerRepos.findOne(id);
    }

    /*public List<TbxaStudentAnswerDO> findLatest()
    {
        Specification querySpecification = (Specification<TbxaStudentAnswerDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Expression<Integer> id = criteriaBuilder.max(root.get("id"));
            predicates.add(criteriaBuilder.in(id));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return tbxaStudentAnswerRepos.findAll(querySpecification);
    }*/

    public TbxaStudentAnswerDO findLatest()
    {
        return tbxaStudentAnswerRepos.findLatest();
    }

    //学生的答案存取下来，解析url中的参数的值
    public TbxaQuestionStudentAnsDO saveStudentQuestionAns(String temp)
    {
        TbxaQuestionStudentAnsDO tbxaQuestionStudentAnsDO = new TbxaQuestionStudentAnsDO();

        tbxaQuestionStudentAnsDO.setAnswer(temp.substring(0,1));
        tbxaQuestionStudentAnsDO.setCorrectAnswer(temp.substring(1,2));
        Integer questionId = Integer.parseInt(temp.substring(2,temp.length()));
        tbxaQuestionStudentAnsDO.setQuestionId(questionId);
        QuestionDO questionDO = questionRepos.getOne(questionId);
        tbxaQuestionStudentAnsDO.setQuestionContent(questionDO.getContent());
        if(temp.substring(0,1).equals(temp.substring(1,2)))
        {
            tbxaQuestionStudentAnsDO.setCorrect(1);
        }
        return tbxaQuestionStudentAnsRepos.save(tbxaQuestionStudentAnsDO);
    }


    //保存学生的填空计算题答案
    public void save1(String temp,String id)
    {
        QuestionDO questionDO = questionRepos.findOne(Integer.parseInt(id));
        TbxaQuestionStudentAnsDO tbxaQuestionStudentAnsDO = new TbxaQuestionStudentAnsDO();
        tbxaQuestionStudentAnsDO.setQuestionId(questionDO.getId());
        tbxaQuestionStudentAnsDO.setQuestionContent(questionDO.getContent());
        tbxaQuestionStudentAnsDO.setCorrectAnswer(questionDO.getAnswer());
        tbxaQuestionStudentAnsDO.setAnswer(temp);
        tbxaQuestionStudentAnsDO.setQuestionType(questionDO.getType());
        tbxaQuestionStudentAnsRepos.save(tbxaQuestionStudentAnsDO);
    }

    //保存学生选择题答案，无返回值~
    public void save(String temp)
    {
        TbxaQuestionStudentAnsDO tbxaQuestionStudentAnsDO = new TbxaQuestionStudentAnsDO();

        tbxaQuestionStudentAnsDO.setAnswer(temp.substring(0,1));
        tbxaQuestionStudentAnsDO.setCorrectAnswer(temp.substring(1,2));
        Integer questionId = Integer.parseInt(temp.substring(2,temp.length()));
        logger.info("questionId1111111111111"+questionId);
        tbxaQuestionStudentAnsDO.setQuestionId(questionId);
        QuestionDO questionDO = questionRepos.getOne(questionId);
        tbxaQuestionStudentAnsDO.setQuestionContent(questionDO.getContent());
        tbxaQuestionStudentAnsDO.setQuestionType(questionDO.getType());
        if(temp.substring(0,1).equals(temp.substring(1,2)))
        {
            tbxaQuestionStudentAnsDO.setCorrect(1);
        }
        tbxaQuestionStudentAnsRepos.save(tbxaQuestionStudentAnsDO);
    }

    public List<TbxaQuestionStudentAnsDO> findStudentAns(TbxaQuestionStudentAnsDO tbxaQuestionStudentAnsDO)
    {
        Specification querySpecification = new Specification<TbxaQuestionStudentAnsDO>()
        {
            @Override
            public Predicate toPredicate(Root<TbxaQuestionStudentAnsDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //predicates.add(criteriaBuilder.isFalse(root.get("deleteFlag")));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return tbxaQuestionStudentAnsRepos.findAll(querySpecification);
    }
}
