package com.zju.repository;

import com.zju.model.CourseVideoDO;
import com.zju.model.CourseWareDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/8/27.
 */
public interface CourseVideoRepos extends JpaRepository<CourseVideoDO, Integer>, JpaSpecificationExecutor<CourseVideoDO> {
}
