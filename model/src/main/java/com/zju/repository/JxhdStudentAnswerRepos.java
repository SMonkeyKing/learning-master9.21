package com.zju.repository;

import com.zju.model.JudgeAnswerDO;
import com.zju.model.JxhdStudentAnswerDO;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by lujie on 2017/8/26.
 */
public interface JxhdStudentAnswerRepos extends JpaRepository<JxhdStudentAnswerDO,Integer>,JpaSpecificationExecutor<JxhdStudentAnswerDO> {


    @Query(value = "select paper_id,\n" +
            "sum(case when answer='A' then 1 else 0 end) A,\n" +
            "sum(case when answer='B' then 1 else 0 end) B,\n" +
            "sum(case when answer='C' then 1 else 0 end) C,\n" +
            "sum(case when answer='D' then 1 else 0 end) D,\n" +
            "sum(case when correct=1 then 1 else 0 end) correct\n" +
            "from learning.jxhd_student_answer\n" +
            "group by paper_id;", nativeQuery = true)
    List<JudgeAnswerDO> getJudgeQuestionAnswerList();


}
