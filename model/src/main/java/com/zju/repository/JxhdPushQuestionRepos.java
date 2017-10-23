package com.zju.repository;

import com.zju.model.JxhdPushQuestionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lujie on 2017/9/11.
 */
public interface JxhdPushQuestionRepos extends JpaRepository<JxhdPushQuestionDO, Integer>, JpaSpecificationExecutor<JxhdPushQuestionDO> {

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE jxhd_question  where  " +
            "round((UNIX_TIMESTAMP('?1')-UNIX_TIMESTAMP(date_created))/60)>10", nativeQuery = true)
    public void delete(String date);
}
