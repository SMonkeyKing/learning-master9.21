package com.zju.repository;

import com.zju.model.SyncTestDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/8/19.
 */
public interface SysnTestRepos extends JpaRepository<SyncTestDO,Integer>,JpaSpecificationExecutor<SyncTestDO> {
}
