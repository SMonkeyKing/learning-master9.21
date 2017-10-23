package com.zju.app.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zju.app.business.jxhd.service.JxhdPushImgService;
import com.zju.app.business.jxhd.service.JxhdPushQuestionService;
import com.zju.app.business.jxhd.service.JxhdPushService;
import com.zju.app.business.jxhd.service.JxhdStudentAnswerService;
import com.zju.app.business.stjj.service.QuestionService;
import com.zju.app.bussiness.jxkj.service.CourseWareService;
import com.zju.app.constant.Constants;
import com.zju.model.*;
import com.zju.utils.DataResult;
import com.zju.utils.JsonUtils;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    JxhdPushImgService jxhdPushImgService;
    //平板端获取推送的题目
    @RequestMapping(value = {"/getPushQuestion"})
    @ResponseBody
    public List<JxhdPushQuestionDO> getPushQuestion()
    {
        return jxhdPushQuestionService.findAll();
    }

    //平板端获取推送的截图
    @RequestMapping(value = {"/getPushImg"})
    @ResponseBody
    public List<JxhdPushImgDO> getPushImg()
    {
        return jxhdPushImgService.findAll();
    }

    //学生的答案提交
    @RequestMapping(value = {"/submitStuAns"},method = {RequestMethod.POST},produces = "application/json")
    @ResponseBody
    public String submitStuAns(@RequestBody String jxhdStudentAnswerDOs)
    {
        List<JxhdStudentAnswerPhoneDO> jxhdStudentAnswerPhoneDOS1 = JSONArray.parseArray(jxhdStudentAnswerDOs,JxhdStudentAnswerPhoneDO.class);
        //jxhdStudentAnswerService.saveList(jxhdStudentAnswerDOS1);

        for (JxhdStudentAnswerPhoneDO jxhdStudentAnswerPhoneDO:
                jxhdStudentAnswerPhoneDOS1) {
            JxhdStudentAnswerDO jxhdStudentAnswerDO = new JxhdStudentAnswerDO();
            jxhdStudentAnswerDO.setPaperId(jxhdStudentAnswerPhoneDO.getPaperId());

            if (jxhdStudentAnswerPhoneDO.getQuestionType() == 1){
                jxhdStudentAnswerDO.setAnswer(jxhdStudentAnswerPhoneDO.getAnswer());
            }else {
                jxhdStudentAnswerDO.setAnswer(ToImage(jxhdStudentAnswerPhoneDO.getAnswer()));
            }

            jxhdStudentAnswerDO.setCorrect(jxhdStudentAnswerPhoneDO.getCorrect());
            jxhdStudentAnswerDO.setCorrectAnswer(jxhdStudentAnswerPhoneDO.getCorrectAnswer());
            jxhdStudentAnswerDO.setUsername(jxhdStudentAnswerPhoneDO.getUsername());
            jxhdStudentAnswerDO.setUserno(jxhdStudentAnswerPhoneDO.getUserno());
            jxhdStudentAnswerDO.setQuestionType(jxhdStudentAnswerPhoneDO.getQuestionType());
            jxhdStudentAnswerService.save(jxhdStudentAnswerDO);
        }

        DataResult result = new DataResult();
        result.setCode(0);
        result.setMessage("提交成功");
        JSONObject json = new JSONObject();
        json.put("code","0");
        json.put("message","提交成功");
        return json.toString();
    }

    public String ToImage(String imageStr){
        String str[]=imageStr.split(","); // avatar base64字符串
        String type=str[0].split(";")[0].split(":")[1].split("/")[1];  //图片类型
        imageStr=str[1];
        byte[] b= decode(imageStr);  //文件流
        //生成jpeg图片
        System.out.println(type);
        String name = UUID.randomUUID().toString()+"."+type;
        String imgFilePath = Constants.FILE_ROAD_KEY+name;//新生成的图片
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Constants.NGINX_ROAD_KEY+name;
    }
    public static byte[] decode(String encodeStr)  {
        byte[] bt = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            bt = decoder.decodeBuffer(encodeStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bt;
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
