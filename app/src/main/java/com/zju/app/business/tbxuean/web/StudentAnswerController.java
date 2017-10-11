package com.zju.app.business.tbxuean.web;

import com.zju.app.business.tbxuean.service.PaperTestService;
import com.zju.app.business.tbxuean.service.StudentAnswerService;
import com.zju.app.business.tbxuean.service.SyncTestService;
import com.zju.model.PaperTestQuestionDO;
import com.zju.model.SyncTestDO;
import com.zju.model.SyncTestDO;
import com.zju.model.TbxaStudentAnswerDO;
import com.zju.utils.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lujie on 2017/8/20.
 */
@Controller
@RequestMapping("/syncTest/studentAnswer")
public class StudentAnswerController {

    private static Logger logger = LoggerFactory.getLogger(StudentAnswerController.class);

    @Autowired
    StudentAnswerService studentAnswerService;

    @Autowired
    SyncTestService syncTestService;

    @Autowired
    PaperTestService paperTestService;

    //做试卷的结果
    @RequestMapping(value = {"/showTestResult"})
    public String showResult(Map model)
    {
        /*String xztAnswer = request.getAttribute("xztAnswer").toString();
        String tkjsAnswer = request.getAttribute("tkjsAnswer").toString();*/
        //找最后一个学生做的
        TbxaStudentAnswerDO tbxaStudentAnswerDO = studentAnswerService.findLatest();
        Integer testId = tbxaStudentAnswerDO.getTestId();
        logger.info("tbxaStudentAnswerDO.getXzt_answer()"+tbxaStudentAnswerDO.getXzt_answer());
        model.put("xztAnswer",tbxaStudentAnswerDO.getXzt_answer());
        model.put("testId",testId);
        model.put("correctRate",tbxaStudentAnswerDO.getCorrectRate());
        SyncTestDO syncTestDO = syncTestService.findOne(testId);
        //model.put("correctXztAnswer",syncTestDO.getXztAnswer());
        model.put("answerUrl",syncTestDO.getAnswerUrl());
        return "/syncLearningPlan/stuAnswerFeedback";
    }


    //提交答案后直接跳至结果界面
    @RequestMapping(value = {"/save"})
    public String save(HttpServletRequest request, @RequestParam(name = "tkjsAnswer")String tkjsAnswer
            , @RequestParam(name="testId")Integer testId,Map model) {
        try {
            //保存填空计算题答案
            logger.info("testId: "+testId);
            SyncTestDO syncTestDO = syncTestService.findOne(testId);
            syncTestDO.setAnswer(tkjsAnswer);
            syncTestService.save(syncTestDO);
            //这个学生提交答案之前先把上一个学生的答案删掉，包括选择题和填空计算题


            //选择题数目
            /*Integer num = Integer.parseInt(request.getParameter("xztNum"));
            for (int i = 1; i < num; i++) {
                String ans = request.getParameter("xztAnswer"+i);

            }*/
            //保存选择题答案
            List<PaperTestQuestionDO> questionDOList = paperTestService.findAllByPaperId(testId);
            for (PaperTestQuestionDO paperTestQuestion:questionDOList
                 ) {
                logger.info("paperTestQuestion.getQuestionid()"+paperTestQuestion.getQuestionid());
                String ans = request.getParameter("xztAnswer"+paperTestQuestion.getQuestionid());
                paperTestQuestion.setStuAns(ans);
                Integer correctNum = paperTestQuestion.getCorrectNum();
                Integer doNum = paperTestQuestion.getDoNum();
                logger.info("stuAns------------"+ans);
                if(ans.equals(paperTestQuestion.getAnswer()))
                {
                    paperTestQuestion.setCorrectNum(++correctNum);
                }
                paperTestQuestion.setDoNum(++doNum);
                double rate = (double)correctNum/doNum;
                paperTestQuestion.setCorrectRate(Lang.formattedDecimalToPercentage(rate));
                paperTestService.save(paperTestQuestion);
            }
            /*SyncTestDO syncTestDO = new SyncTestDO();
            syncTestDO = syncTestService.findOne(testId);
            String correctAnswer = syncTestDO.getXztAnswer();
            char [] correctAnswerArr = correctAnswer.toCharArray();
            char [] stuAnswerArr = xztAnswer.toCharArray();
            Integer correctCount = 0;
            Integer correctLength = correctAnswerArr.length;
            Integer stuLength = stuAnswerArr.length;
            Integer length = Math.min(correctLength,stuLength);
            if (stuAnswerArr.length>0 || stuAnswerArr!=null)
            {
                for (int i=0;i<length;i++)
                {
                    if(correctAnswerArr[i] == stuAnswerArr[i])
                    {
                        correctCount++;
                    }
                }
            }

            String correctRate = correctCount.toString()+"/"+correctAnswerArr.length;
            TbxaStudentAnswerDO studentAnswerDO = new TbxaStudentAnswerDO();
            studentAnswerDO.setTestId(testId);
            studentAnswerDO.setXzt_answer(xztAnswer);
            studentAnswerDO.setTkjs_answer(tkjsAnswer);
            studentAnswerDO.setCorrectNum(correctCount);
            studentAnswerDO.setCorrectRate(correctRate);

            studentAnswerService.save(studentAnswerDO);*/

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        SyncTestDO syncTestDOBack = syncTestService.findOne(testId);
        List<PaperTestQuestionDO> paperTestQuestionDOS = paperTestService.findAllByPaperId(testId);
        model.put("tkjsAnswer",syncTestDOBack.getAnswer());
        model.put("paperTestQuestionDOS",paperTestQuestionDOS);
        return "/syncLearningPlan/stuAnswerResult";
    }

    //保存学生答案的同时要判断选择题的正确性
    /*@RequestMapping(value = {"/save"})
    @ResponseBody
    public AjaxResponseVo save(@RequestParam(name = "xztAnswer")String xztAnswer,@RequestParam(name = "tkjsAnswer")String tkjsAnswer
                               , @RequestParam(name="testId")Integer testId) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", "开始测试", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);

        try {
            logger.info("testId: "+testId);
            SyncTestDO syncTestDO = new SyncTestDO();
            syncTestDO = syncTestService.findOne(testId);
            String correctAnswer = syncTestDO.getXztAnswer();
            char [] correctAnswerArr = correctAnswer.toCharArray();
            char [] stuAnswerArr = xztAnswer.toCharArray();
            Integer correctCount = 0;
            if (stuAnswerArr.length>0 || stuAnswerArr!=null)
            {
                for (int i=0;i<correctAnswerArr.length;i++)
                {
                    if(correctAnswerArr[i] == stuAnswerArr[i])
                    {
                        correctCount++;
                    }
                }
            }

            String correctRate = correctCount.toString()+"/"+correctAnswerArr.length;
            TbxaStudentAnswerDO studentAnswerDO = new TbxaStudentAnswerDO();
            studentAnswerDO.setTestId(testId);
            studentAnswerDO.setXzt_answer(xztAnswer);
            studentAnswerDO.setTkjs_answer(tkjsAnswer);
            studentAnswerDO.setCorrectNum(correctCount);
            studentAnswerDO.setCorrectRate(correctRate);
            studentAnswerService.save(studentAnswerDO);

        } catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }*/
}
