package com.zju.app.business.stjj.service;

import com.zju.model.QuestionDO;
import com.zju.repository.QuestionRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lujie on 2017/8/13.
 */

@Service
public class QuestionService {

    @Autowired
    QuestionRepos questionRepos;

    public QuestionDO save(QuestionDO questionDO)
    {
        return  questionRepos.save(questionDO);
    }

    public QuestionDO findOne(String id)
    {
        return  questionRepos.findOne(id);
    }
}
