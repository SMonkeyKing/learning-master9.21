package com.zju.repository;

import com.zju.model.SyncTestDO;
import com.zju.model.SyncTestFromTKDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lujie on 2017/8/19.
 */
public interface SysnTestRepos extends JpaRepository<SyncTestDO,Integer>,JpaSpecificationExecutor<SyncTestDO> {
    @Query(value = "SELECT * FROM paper_test where id =(select max(id) from paper_test)", nativeQuery = true)
    SyncTestDO findLatestSyncTestDO();
}
