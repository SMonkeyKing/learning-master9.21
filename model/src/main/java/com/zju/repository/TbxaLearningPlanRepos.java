package com.zju.repository;

import com.zju.model.TbxaLearningPlanDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/8/27.
 */
public interface TbxaLearningPlanRepos extends JpaRepository<TbxaLearningPlanDO, Integer>, JpaSpecificationExecutor<TbxaLearningPlanDO> {
}
