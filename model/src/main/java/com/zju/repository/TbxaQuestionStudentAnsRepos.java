package com.zju.repository;

import com.zju.model.TbxaQuestionStudentAnsDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/9/6.
 */
public interface TbxaQuestionStudentAnsRepos extends JpaRepository<TbxaQuestionStudentAnsDO, Integer>, JpaSpecificationExecutor<TbxaQuestionStudentAnsDO> {
}
