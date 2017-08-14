package com.zju.repository;

import com.zju.model.LeftMenuDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * Created by lujie on 2017/8/14.
 */
public interface LeftMenuRepos extends JpaRepository<LeftMenuDO,String>,JpaSpecificationExecutor<LeftMenuDO> {
}
