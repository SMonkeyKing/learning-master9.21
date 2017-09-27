package com.zju.repository;

import com.zju.model.SyncTestDO;
import com.zju.model.SyncTestFromTKDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lujie on 2017/8/19.
 */
public interface SyncTestFromTKRepos extends JpaRepository<SyncTestFromTKDO,Integer>,JpaSpecificationExecutor<SyncTestFromTKDO> {

    @Query(value = "SELECT * FROM tbxa_sync_test where question_id = ?1", nativeQuery = true)
    public SyncTestFromTKDO selectByQuestionId(Integer qId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE SyncTestFromTKDO SET stuAns = '' where typeId = ?1")
    public void updateStuAns(Integer typeid);
}
