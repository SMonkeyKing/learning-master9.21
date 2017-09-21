package com.zju.repository;

import com.zju.model.JxhdPushQuestionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/9/11.
 */
public interface JxhdPushQuestionRepos extends JpaRepository<JxhdPushQuestionDO, Integer>, JpaSpecificationExecutor<JxhdPushQuestionDO> {
}
