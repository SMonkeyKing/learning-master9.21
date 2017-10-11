package com.zju.repository;

import com.zju.model.CourseWareDO;
import com.zju.model.PaperTestQuestionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by xujingfeng on 2017/8/11.
 */
public interface PaperTestQuestionRepos extends JpaRepository<PaperTestQuestionDO, Integer>, JpaSpecificationExecutor<PaperTestQuestionDO> {
}
