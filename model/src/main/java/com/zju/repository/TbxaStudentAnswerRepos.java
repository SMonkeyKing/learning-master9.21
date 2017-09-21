package com.zju.repository;

import com.zju.model.TbxaStudentAnswerDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lujie on 2017/8/19.
 */
public interface TbxaStudentAnswerRepos extends JpaRepository<TbxaStudentAnswerDO,Integer>,JpaSpecificationExecutor<TbxaStudentAnswerDO> {
    @Query(value = "SELECT * FROM tbxa_student_answer where id =(select max(id) from tbxa_student_answer)", nativeQuery = true)
    TbxaStudentAnswerDO findLatest();
}
