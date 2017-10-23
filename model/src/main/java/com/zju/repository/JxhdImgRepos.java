package com.zju.repository;

import com.zju.model.JxhdImgDO;
import com.zju.model.JxhdPushDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JxhdImgRepos extends JpaRepository<JxhdImgDO,Integer>,JpaSpecificationExecutor<JxhdImgDO> {
}
