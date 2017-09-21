package com.zju.repository;

import com.zju.model.AcademicJournalDO;
import com.zju.model.JxhdTeacherQuestionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/9/11.
 */
public interface JxhdTeacherQuestionRepos extends JpaRepository<JxhdTeacherQuestionDO, Integer>, JpaSpecificationExecutor<JxhdTeacherQuestionDO> {
}
