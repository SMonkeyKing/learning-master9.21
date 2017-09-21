package com.zju.app.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zju.app.business.jxhd.service.JxhdPushQuestionService;
import com.zju.app.business.jxhd.service.JxhdPushService;
import com.zju.app.business.jxhd.service.JxhdStudentAnswerService;
import com.zju.app.business.stjj.service.QuestionService;
import com.zju.app.bussiness.jxkj.service.CourseWareService;
import com.zju.model.*;
import com.zju.utils.DataResult;
import com.zju.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lujie on 2017/8/23.
 */
@Controller
@RequestMapping("/phone")
public class AndroidController {

    @Autowired
    CourseWareService courseWareService;

    @Autowired
    QuestionService questionService;

    @Autowired
    JxhdPushService jxhdPushService;

    @Autowired
    JxhdStudentAnswerService jxhdStudentAnswerService;

    @Autowired
    JxhdPushQuestionService jxhdPushQuestionService;


    //平板端获取推送的题目
    @RequestMapping(value = {"/getPushQuestion"})
    @ResponseBody
    public List<JxhdPushQuestionDO> getPushQuestion()
    {
        return jxhdPushQuestionService.findAll();
    }

    //学生的答案提交
    @RequestMapping(value = {"/submitAns"},method = {RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public DataResult submitStudentAns(@RequestBody String jxhdStudentAnswerDOs)
    {
        String newJxhdStudentAnswerDOs1 = jxhdStudentAnswerDOs.replace("\\","");
        String newJxhdStudentAnswerDOs = newJxhdStudentAnswerDOs1.substring(1, newJxhdStudentAnswerDOs1.length() - 1);

        System.out.print(newJxhdStudentAnswerDOs);

        List<JxhdStudentAnswerPhoneDO> jxhdStudentAnswerPhoneDOS1 = JSONArray.parseArray(newJxhdStudentAnswerDOs,JxhdStudentAnswerPhoneDO.class);
        /*JSONArray json = JSONArray.parseArray(newJxhdStudentAnswerDOs);
        List<JxhdStudentAnswerPhoneDO> jxhdStudentAnswerPhoneDOS = new ArrayList<>();
        //JSONArray json = JSONArray.fromObject(newJxhdStudentAnswerDOs ); // 首先把字符串转成 JSONArray  对象
        if(json.size()>0) {
            for (int i = 0; i < json.size(); i++) {
                JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象

                //JxhdStudentAnswerPhoneDO jxhdStudentAnswerPhoneDO = new JxhdStudentAnswerPhoneDO();
                //jxhdStudentAnswerPhoneDO.setAnswer(job.get("answer").toString());
                JxhdStudentAnswerPhoneDO stu = JSON.parseObject(job.toJSONString(), JxhdStudentAnswerPhoneDO.class);
                jxhdStudentAnswerPhoneDOS.add(stu);
            }
        }*/
        //List<JxhdStudentAnswerPhoneDO> jxhdStudentAnswerDOList = JsonUtils.jsonToList(newJxhdStudentAnswerDOs,JxhdStudentAnswerPhoneDO.class);

        //List<JxhdStudentAnswerDO> jxhdStudentAnswerDOList = (List<JxhdStudentAnswerDO>)jxhdStudentAnswerDOs;
        /*if(jxhdStudentAnswerPhoneDOS!=null || jxhdStudentAnswerPhoneDOS.size()>0)
        {
            for (JxhdStudentAnswerPhoneDO jxhdStudentAnswerDO: jxhdStudentAnswerPhoneDOS
                 ) {
                //jxhdStudentAnswerService.save(jxhdStudentAnswerDO);
                System.out.print("11111111111111"+jxhdStudentAnswerPhoneDOS.size());
            }

        }*/
        if(jxhdStudentAnswerPhoneDOS1!=null || jxhdStudentAnswerPhoneDOS1.size()>0)
        {
            for (JxhdStudentAnswerPhoneDO jxhdStudentAnswerPhoneDO: jxhdStudentAnswerPhoneDOS1
                    ) {
                JxhdStudentAnswerDO jxhdStudentAnswerDO = new JxhdStudentAnswerDO();
                jxhdStudentAnswerDO.setUserno(jxhdStudentAnswerPhoneDO.getUserno());
                jxhdStudentAnswerDO.setUsername(jxhdStudentAnswerPhoneDO.getUsername());
                jxhdStudentAnswerDO.setAnswer(jxhdStudentAnswerPhoneDO.getAnswer());
                jxhdStudentAnswerDO.setCorrectAnswer(jxhdStudentAnswerPhoneDO.getCorrectAnswer());
                jxhdStudentAnswerDO.setPaperId(jxhdStudentAnswerPhoneDO.getPaperId());
                jxhdStudentAnswerDO.setCorrect(jxhdStudentAnswerPhoneDO.getCorrect());
                jxhdStudentAnswerService.save(jxhdStudentAnswerDO);
                System.out.print("11111111111111"+jxhdStudentAnswerPhoneDOS1.size());
            }

        }

        DataResult result = new DataResult();
        result.setCode(0);
        result.setMessage("提交成功");
        return result;
    }

    @RequestMapping(value = {"/submitAnswer"},method = {RequestMethod.POST})
    @ResponseBody
    public DataResult submitStuAns(@RequestBody List<JxhdStudentAnswerDO> jxhdStudentAnswerDOList)
    {
        if(jxhdStudentAnswerDOList!=null || jxhdStudentAnswerDOList.size()>0)
        {
            for (JxhdStudentAnswerDO jxhdStudentAnswerDO: jxhdStudentAnswerDOList
                    ) {
                jxhdStudentAnswerService.save(jxhdStudentAnswerDO);
            }
        }
        DataResult result = new DataResult();
        result.setCode(0);
        result.setMessage("提交成功");
        return result;
    }
    @RequestMapping(value = {"/getInfo"})
    @ResponseBody
    public DataResult getInfo()
    {
        DataResult result = new DataResult();
        List<JxhdPushDO> jxhdPushDOList = jxhdPushService.findAll();
        if (jxhdPushDOList.size()>0)
        {
            long count = jxhdPushDOList.size();
            result.setCode(0);
            result.setMessage("获取成功");

        }else
        {
            /*result.setCode(1);
            result.setMessage("获取失败");*/
        }

        long count1 = jxhdPushService.count();

        return result;
    }

    @RequestMapping(value = {"/returnAns"})
    @ResponseBody
    public DataResult returnAns(@RequestParam(name = "userNo")String userNo,@RequestParam(name = "userName")
                                String userName,@RequestParam(name = "ans")String ans)
    {
        JxhdStudentAnswerDO jxhdStudentAnswerDO = new JxhdStudentAnswerDO();
        jxhdStudentAnswerDO.setAnswer(ans);
        jxhdStudentAnswerDO.setUsername(userName);
        jxhdStudentAnswerDO.setUserno(userNo);
        jxhdStudentAnswerService.save(jxhdStudentAnswerDO);
        return null;
    }

    //发送试卷
    @RequestMapping(value = {"/sendPaper"})
    @ResponseBody
    public CourseWareDO sendPaper(@RequestParam(name = "paparId")Integer id)
    {
        CourseWareDO courseWareDO = courseWareService.findOne(id);
        return courseWareDO;
    }

    //发送题目（一题，多题）
    @RequestMapping(value = {"/sendQuestion"})
    @ResponseBody
    public List<QuestionDO> sendQuestion(@RequestParam(name = "questionIds")Integer[] ids)
    {
        List<QuestionDO> questionDOList = new ArrayList<>();
        if (ids!=null)
        {
            for (Integer id:ids
                 ) {
                QuestionDO questionDO = new QuestionDO();
                questionDO = questionService.findOne(id);
                if(questionDO!=null)
                {
                    questionDOList.add(questionDO);
                }
            }
        }
        return questionDOList;
    }

    //判断正误（平板传id和answer过来）,对于一题或者多题的判断
    //返回List<Integer> 0-错误 1-正确
    @RequestMapping(value = {"/judge"})
    @ResponseBody
    public List<Integer> judge(@RequestParam(name = "questionid")Integer[] ids,
                               @RequestParam(name="answer")String answer)
    {

        return null;
    }


}
