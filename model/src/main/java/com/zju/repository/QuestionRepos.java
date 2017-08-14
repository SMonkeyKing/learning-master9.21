package com.zju.repository;

import com.zju.model.QuestionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/8/13.
 */
public interface QuestionRepos extends JpaRepository<QuestionDO,String>,JpaSpecificationExecutor<QuestionDO>{
}
