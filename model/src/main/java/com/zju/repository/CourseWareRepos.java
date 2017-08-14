package com.zju.repository;

import com.zju.model.CourseWareDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by xujingfeng on 2017/8/11.
 */
public interface CourseWareRepos extends JpaRepository<CourseWareDO, String>, JpaSpecificationExecutor<CourseWareDO> {
}
