package com.zju.app.business.stjj.service;

import com.zju.model.PaperDO;
import com.zju.repository.PaperRepos;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


/**
 * Created by lujie on 2017/8/17.
 */
@Service
public class PaperService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(PaperService.class);

    @Autowired
    PaperRepos paperRepos;

    public PaperDO save(PaperDO paperDO) {
        return paperRepos.save(paperDO);
    }

    public Integer findId() {
        if (CollectionUtils.isEmpty(paperRepos.findAll())) {
            return null;
        }
        return paperRepos.findAll().get(0).getId();
    }

    public void deleteAllPaper() {
        if (CollectionUtils.isEmpty(paperRepos.findAll())) {
            return;
        }
        paperRepos.deleteAll();
    }
}
