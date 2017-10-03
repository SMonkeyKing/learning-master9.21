package com.zju.app.business.tbxuean.web;

import com.zju.app.business.tbxuean.service.StudentAnswerService;
import com.zju.app.business.tbxuean.service.SyncTestService;
import com.zju.model.SyncTestDO;
import com.zju.model.SyncTestDO;
import com.zju.model.TbxaStudentAnswerDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        model.put("correctXztAnswer",syncTestDO.getXztAnswer());
        model.put("answerUrl",syncTestDO.getAnswerUrl());
        return "/syncLearningPlan/stuAnswerFeedback";
    }


    @RequestMapping(value = {"/save"})
    @ResponseBody
    public void save(@RequestParam(name = "xztAnswer")String xztAnswer,@RequestParam(name = "tkjsAnswer")String tkjsAnswer
            , @RequestParam(name="testId")Integer testId) {
        try {
            logger.info("testId: "+testId);
            SyncTestDO syncTestDO = new SyncTestDO();
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

            studentAnswerService.save(studentAnswerDO);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        //return "";
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
