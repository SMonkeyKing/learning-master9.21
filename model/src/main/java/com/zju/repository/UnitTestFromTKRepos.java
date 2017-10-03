package com.zju.repository;

import com.zju.model.SyncTestFromTKDO;
import com.zju.model.UnitTestFromTKDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lujie on 2017/9/28.
 */
public interface UnitTestFromTKRepos extends JpaRepository<UnitTestFromTKDO,Integer>,JpaSpecificationExecutor<UnitTestFromTKDO> {

    @Query(value = "SELECT * FROM zzxl_unit_test where question_id = ?1", nativeQuery = true)
    public UnitTestFromTKDO selectByQuestionId(Integer qId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE UnitTestFromTKDO SET stuAns = '' where typeId = ?1")
    public void updateStuAns(Integer typeid);
}
