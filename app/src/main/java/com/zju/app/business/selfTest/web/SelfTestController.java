package com.zju.app.business.selfTest.web;

import com.zju.app.business.selfTest.service.SelfTestService;
import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.app.business.stjj.service.QuestionService;
import com.zju.app.business.tbxuean.service.SyncTestFromTKService;
import com.zju.app.business.tbxuean.web.SyncTestFromTKController;
import com.zju.model.*;
import com.zju.utils.Lang;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.dwz.DwzPageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lujie on 2017/9/28.
 * 自主训练
 * ——单元测试
 */
@Controller
@RequestMapping("/unitTest")
public class SelfTestController {

    private static Logger logger = LoggerFactory.getLogger(SelfTestController.class);

    @Autowired
    SelfTestService selfTestService;

    @Autowired
    QuestionService questionService;

    @Autowired
    LeftMenuService leftMenuService;

    @ModelAttribute
    public void model(@RequestParam(name = "id", required = false) Integer id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            UnitTestFromTKDO unitTestFromTKDO = selfTestService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("unitTestFromTKDO", unitTestFromTKDO);
        }
    }
    //通过点击左侧菜单进入添加同步测试题列表
    //显示该分类下的题库中所有题目
    @RequestMapping(value = {"/config"})
    public String addSyncTestFromTK(@RequestParam(name = "typeid") Integer typeid, HttpServletRequest request,
                                    QuestionDO questionDO, DwzPageVo page, Map model)
    {
        String type = request.getParameter("type");
        logger.info("type:"+type);
        Integer typeInt = 0;
        if(!Lang.isEmpty(type))
        {
            typeInt = Integer.parseInt(type);
        }
        Page<QuestionDO> pageLists = questionService.findAll(typeid,typeInt,questionDO, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("questionDO", questionDO);
        model.put("page", page);
        model.put("questionDOs", pageLists.getContent());
        model.put("type", typeInt);
        model.put("typeid", typeid);
        return "selfTest/addUnitTestFromTK";
    }

    //从题库中选择题目加入同步测试中
    @RequestMapping(value = "/addQuestion")
    @ResponseBody
    public Integer addQuestion(@RequestParam(name = "questionId")Integer questionId)
    {
        try{
            QuestionDO questionDO = questionService.findOne(questionId);
            //加入单元测试后，把unitFlag字段设为true，表示已加入
            //questionDO.setUnitFlag(true);
            questionDO.setUnitFlag(1);
            questionService.save(questionDO);
            UnitTestFromTKDO unitTestFromTKDO = new UnitTestFromTKDO();
            unitTestFromTKDO.setQuestionContent(questionDO.getContent());
            Integer questionType = questionDO.getType();
            unitTestFromTKDO.setQuestionType(questionType);
            //questionType==1表示选择题
            if(questionType==1)
            {
                unitTestFromTKDO.setQuestionAnswer(questionDO.getJudgeAnswer());
            }
            unitTestFromTKDO.setQuestionAnswer(questionDO.getAnswer());
            //typeid左侧菜单的id，为了列表显示时过滤出同一专题下的题目
            unitTestFromTKDO.setTypeId(questionDO.getTypeid());
            unitTestFromTKDO.setQuestionId(questionDO.getId());
            selfTestService.save(unitTestFromTKDO);
            return 1;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    //返回list
    @RequestMapping(value = "/list")
    public String getSyncTestFromYKList(@RequestParam(name = "typeid")Integer typeid,HttpServletRequest request,
                                        UnitTestFromTKDO unitTestFromTKDO, Map model)
    {
        String type = request.getParameter("questionType");
        Integer roleId = (Integer) request.getSession().getAttribute("role");
        Integer typeInt = 0;
        if(!Lang.isEmpty(type))
        {
            typeInt = Integer.parseInt(type);
        }
        //根据typeid获取同步测试列表
        List<UnitTestFromTKDO> unitTestFromTKDOList = selfTestService.findAllList(typeid,typeInt,unitTestFromTKDO);
        model.put("syncTestFromTKDO", unitTestFromTKDO);
        model.put("syncTestFromTKDOs", unitTestFromTKDOList);
        model.put("type", typeInt);
        model.put("typeid", typeid);
        model.put("role",roleId);
        model.put("listSize",unitTestFromTKDOList.size());
        return "selfTest/doUnitTest";
    }

    //返回page
    /*@RequestMapping(value = "/list")
    public String getSyncTestFromYKList(@RequestParam(name = "typeid")Integer typeid,HttpServletRequest request,
                                        SyncTestFromTKDO syncTestFromTKDO, DwzPageVo page, Map model)
    {
        String type = request.getParameter("questionType");
        Integer roleId = (Integer) request.getSession().getAttribute("role");

        logger.info("typeid:"+typeid);

        logger.info("type:"+type);
        Integer typeInt = 0;
        if(!Lang.isEmpty(type))
        {
            typeInt = Integer.parseInt(type);
            logger.info("typeInfo:"+typeInt);
        }
        //根据typeid获取同步测试列表
        Page<SyncTestFromTKDO> pageLists = syncTestFromTKService.findAllSyncTests(typeid,typeInt,syncTestFromTKDO,page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("syncTestFromTKDO", syncTestFromTKDO);
        model.put("page", page);
        logger.info("page",page);
        model.put("syncTestFromTKDOs", pageLists.getContent());
        model.put("type", typeInt);
        model.put("typeid", typeid);
        model.put("role",roleId);
        model.put("listSize",pageLists.getTotalElements());
        return "syncLearningPlan/doTestQuestion";
    }*/

    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo delete( UnitTestFromTKDO unitTestFromTKDO) {
        Integer typeId = unitTestFromTKDO.getTypeId();
        LeftMenuDO leftMenuDO = leftMenuService.findOne(typeId);
        String typeName = leftMenuDO.getTitle();
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "已经作废", "单元测试");
        try {
            //假删除
            /*syncTestFromTKDO.setDeleteFlag(true);
            syncTestFromTKService.save(syncTestFromTKDO)*/;
            //直接删除
            selfTestService.deleteDO(unitTestFromTKDO);

            QuestionDO questionDO = questionService.findOne(unitTestFromTKDO.getQuestionId());
            //从单元测试中删除后，把unitFlag置为0.表示还可以再次加入
            questionDO.setUnitFlag(0);
            questionService.save(questionDO);
            QuestionDO questionDO1 = questionService.findOne(unitTestFromTKDO.getQuestionId());
            logger.info("version"+questionDO1.getVersion());
        } catch (Exception e) {
            logger.error("删除题目失败:{}", e.getMessage());
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
        }
        return ajaxResponseVo;
    }

    @RequestMapping(value = {"/submitAnswer"})
    public String submitAnswer(HttpServletRequest request,Map model,UnitTestFromTKDO syncTestFromTKDO1)
    {
        //LeftMenuDO leftMenuDO = leftMenuService.findOne(id);
        //String typeName = leftMenuDO.getTitle();
        //AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,"答案提交成功", "同步测试");
        //listSize--题目的数量，如果没取到该值，就默认为20
        String length = request.getParameter("listSize");
        Integer typeidInt = Integer.parseInt(request.getParameter("typeid"));
        List<UnitTestFromTKDO> syncTestFromTKDOList = selfTestService.findAllList(typeidInt,0,syncTestFromTKDO1);
        Integer len = syncTestFromTKDOList.size();
        //List<SyncTestFromTKDO> syncTestFromTKDOJudgeList = syncTestFromTKService.findAllList(typeidInt,1,syncTestFromTKDO1);
        //Integer judgeLen = syncTestFromTKDOJudgeList.size();
        if(!Lang.isEmpty(length))
        {
            len = Integer.parseInt(length);
        }
        //用于解析选择题的答案
        String temp="";
        //解析填空题答案
        String temp1 = "";
        String id ="";
        List<String> answerArr = new ArrayList<>();
        //在存学生答案之前先把上一次的答案清掉
        selfTestService.updateStuAns(typeidInt);
        try{
            /*for (int i = 0; i < judgeLen; i++) {
                temp = request.getParameter("answer"+i);
            }*/
            for(int i=0;i<len;i++)
            {
                //把答案存入一个数组
                temp = request.getParameter("answer"+i);
                logger.info("temp+++++"+temp);
                temp1= request.getParameter("answer1"+i);
                //用于存其他题型的题目id
                id = request.getParameter("id"+i);
                Integer qId = 0;
                //选择题
                if (!Lang.isEmpty(temp))
                {
                    //answerArr.add(temp);
                    caculateCorrectRate(temp);
                    qId = Integer.parseInt(temp.substring(2,temp.length()));
                    String stuAns = temp.substring(0,1);
                    logger.info("stuAns============="+stuAns);
                    UnitTestFromTKDO syncTestFromTKDO = selfTestService.findOneByQuestionId(qId);
                    syncTestFromTKDO.setStuAns(stuAns);
                    selfTestService.save(syncTestFromTKDO);
                }

                //其他题
                if(!Lang.isEmpty(temp1) && !Lang.isEmpty(id))
                {
                    //answerArr.add(temp);
                    //studentAnswerService.save1(temp1,id);
                    qId = Integer.parseInt(id);
                    UnitTestFromTKDO syncTestFromTKDO = selfTestService.findOneByQuestionId(qId);
                    syncTestFromTKDO.setStuAns(temp1);
                    selfTestService.save(syncTestFromTKDO);
                }
            }
        }catch (Exception e) {
            /*ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);*/
            logger.error(e.getMessage(), e);
        }
        //model.put("answerArr",answerArr);
        //return "/syncLearningPlan/doTestQuestionResult";

        List<UnitTestFromTKDO> syncTestFromTKDOS = selfTestService.findAllList(typeidInt,0,syncTestFromTKDO1);
        model.put("syncTestFromTKDOS",syncTestFromTKDOS);
        return "/selfTest/doUnitTestResult";

        //model.put("tbxaQuestionStudentAnsDOs",tbxaQuestionStudentAnsDOs);
        //return "/syncLearningPlan/doTestQuestionResult";
    }

    //计算正确率
    //temp 学生答案+正确答案+questionid
    public void caculateCorrectRate(String temp)
    {
        String stuAns = temp.substring(0,1);
        String correctAns = temp.substring(1,2);
        //String questionId = temp.substring(2,temp.length());
        Integer qId = Integer.parseInt(temp.substring(2,temp.length()));
        QuestionDO questionDO = questionService.findOne(qId);
        Integer result = 0;
        Integer correctNum = questionDO.getCorrectNum();
        Integer ansSum = questionDO.getAnsNum();
        if(stuAns.equals(correctAns))
        {
            result = 1;
            correctNum = correctNum+1;
            questionDO.setCorrectNum(correctNum);
        }
        ansSum+=1;
        questionDO.setAnsNum(ansSum);
        //正确率保留两位小数
        double f = (double)correctNum/ansSum;
        /*BigDecimal b = new BigDecimal(f);
        double rate = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();*/
        questionDO.setCorrectRate(Lang.formattedDecimalToPercentage(f));
        questionService.save(questionDO);

    }

    /*@RequestMapping(value = {"/backResult"})
    public String getStudentAns(Map model,TbxaQuestionStudentAnsDO tbxaQuestionStudentAnsDO)
    {
        List<TbxaQuestionStudentAnsDO> tbxaQuestionStudentAnsDOList = studentAnswerService.findStudentAns(tbxaQuestionStudentAnsDO);
        model.put("tbxaQuestionStudentAnsDOs",tbxaQuestionStudentAnsDOList);
        return "/syncLearningPlan/doTestQuestionResult";
    }*/
    @RequestMapping(value = {"/backResult"})
    public String getStudentAns(Map model,HttpServletRequest request,UnitTestFromTKDO syncTestFromTKDO)
    {
        String typeid = request.getParameter("typeid");
        Integer typeidInt = 0;
        if (!Lang.isEmpty(typeid))
        {
            typeidInt = Integer.parseInt(typeid);
        }
        List<UnitTestFromTKDO> syncTestFromTKDOS = selfTestService.findAllList(typeidInt,0,syncTestFromTKDO);
        model.put("syncTestFromTKDOS",syncTestFromTKDOS);
        return "/selfTest/doUnitTestResult";
    }

}
