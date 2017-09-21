package com.zju.repository;

import com.zju.model.JxhdQuestionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/8/25.
 */
public interface JxhdQuestionRepos extends JpaRepository<JxhdQuestionDO,Integer>,JpaSpecificationExecutor<JxhdQuestionDO> {
}
