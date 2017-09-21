package com.zju.app.business.stjj.web;

import com.zju.app.business.stjj.service.PaperService;
import com.zju.app.business.stjj.service.ShowQuestionService;
import com.zju.model.PaperDO;
import com.zju.model.QuestionDO;
import com.zju.repository.PaperRepos;
import com.zju.repository.QuestionRepos;
import com.zju.utils.CookieUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/19.
 */
@Controller
@RequestMapping("/showQuestion")
public class QuestionShowController {
    private static Logger logger = LoggerFactory.getLogger(PaperController.class);
    @Autowired
    ShowQuestionService showQuestionService;
    //获取数据库中题库
    @Autowired
    PaperService paperService;

    @RequestMapping(value = {"/PreShowPaper"})
    public String getAllQuestions(Map model) {
        Integer paperId = paperService.findId();
        List<String> list = new ArrayList<>();
        int sum = 0; //条数
        if (null != paperId) {
            List<QuestionDO> questionDOs = showQuestionService.findAllPaper(paperId);
            if (CollectionUtils.isNotEmpty(questionDOs)) {
                for (QuestionDO questionDO : questionDOs) {
                    String content = questionDO.getContent();
                    list.add(content);
                    sum++;
                }
            }
        }

        //清除缓存里的试卷
        paperService.deleteAllPaper();
        model.put("contents", list);
        model.put("size", sum);
        return "question/questionShow";
    }

}
