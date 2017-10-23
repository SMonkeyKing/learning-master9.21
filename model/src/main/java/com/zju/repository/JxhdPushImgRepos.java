package com.zju.repository;

import com.zju.model.JxhdPushDO;
import com.zju.model.JxhdPushImgDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JxhdPushImgRepos extends JpaRepository<JxhdPushImgDO,Integer>,JpaSpecificationExecutor<JxhdPushImgDO> {
}
