package com.zju.repository;

import com.zju.model.PaperDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/8/17.
 */
public interface PaperRepos extends JpaRepository<PaperDO,Integer>,JpaSpecificationExecutor<PaperDO> {
}
