package com.zju.repository;

import com.zju.model.AcademicJournalDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lujie on 2017/8/16.
 */
public interface AcademicJournalRepos extends JpaRepository<AcademicJournalDO, Integer>, JpaSpecificationExecutor<AcademicJournalDO> {
}
