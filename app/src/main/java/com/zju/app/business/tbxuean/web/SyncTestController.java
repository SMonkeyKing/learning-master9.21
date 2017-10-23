package com.zju.app.business.tbxuean.web;

import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.app.business.stjj.service.QuestionService;
import com.zju.app.business.tbxuean.service.PaperTestService;
import com.zju.app.business.tbxuean.service.StudentAnswerService;
import com.zju.app.business.tbxuean.service.SyncTestFromTKService;
import com.zju.app.business.tbxuean.service.SyncTestService;
import com.zju.app.bussiness.jxkj.service.CourseWareService;
import com.zju.app.constant.Constants;
import com.zju.model.*;
import com.zju.utils.Lang;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.dwz.DwzPageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lujie on 2017/8/19.
 */
@Controller
@RequestMapping("/syncTest")
public class SyncTestController {

    private static Logger logger = LoggerFactory.getLogger(SyncTestController.class);

    @Autowired
    SyncTestService syncTestService;

    @Autowired
    CourseWareService courseWareService;

    @Autowired
    LeftMenuService leftMenuService;

    @Autowired
    QuestionService questionService;

    @Autowired
    StudentAnswerService studentAnswerService;

    @Autowired
    SyncTestFromTKService syncTestFromTKService;

    @Autowired
    PaperTestService paperTestService;

    @ModelAttribute
    public void model(@RequestParam(name = "id", required = false) Integer id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            SyncTestDO syncTestDO = syncTestService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("syncTestDO", syncTestDO);
        }
    }

    //同步测试列表
    @RequestMapping(value = {"/config"})
    public String list(@RequestParam(name = "typeid")Integer typeId, SyncTestDO syncTestDO, DwzPageVo page, Map model) {
        //LeftMenuDO leftMenuDO = leftMenuService.findOne(typeId);
        //Integer parentId = leftMenuDO.getParentid();
        Integer lptypeId = typeId-1;
        List<CourseWareDO> courseWareDOList = courseWareService.findLpList(typeId);
        List<Integer> lpList = new ArrayList<Integer>();
        for (CourseWareDO courseWare:courseWareDOList
             ) {
            lpList.add(courseWare.getId());
        }
        Page<SyncTestDO> pageLists = syncTestService.findAll1(lpList,syncTestDO, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("syncTestDO", syncTestDO);
        model.put("page", page);
        model.put("syncTestDOs", pageLists.getContent());

        return "/syncLearningPlan/syncTestList";
    }

    @RequestMapping(value = {"/question/config"})
    public String questionList(@RequestParam(name = "typeid")Integer typeid, HttpServletRequest request,
                               QuestionDO questionDO, DwzPageVo page, Map model) {
        String type = request.getParameter("type");
        Integer typeInt = 1;
        Integer newTypeid = 0;
        if(!Lang.isEmpty(type))
        {
             typeInt = Integer.parseInt(type);
        }
        switch (typeid)
        {
            case 188:
                newTypeid = 183;
                break;
            case 223:
                newTypeid = 184;
                break;
            default:
                break;

        }
        //Page<QuestionDO> pageLists = questionService.findAll(typeid,typeInt,questionDO, page.getPageable());
        List<QuestionDO> questionDOList = questionService.findAllQuestionList(newTypeid,typeInt,questionDO);
        //page.setTotalCount(pageLists.getTotalElements());
        model.put("questionDO", questionDO);
        //model.put("page", page);
        //model.put("questionDOs", pageLists.getContent());
        model.put("questionDOs", questionDOList);
        //logger.info(page.getTotalCount()+"");
        model.put("type", typeInt);
        model.put("typeid", typeid);
        model.put("listSize",questionDOList.size());
        return "/syncLearningPlan/doTestQuestion";
    }
    @RequestMapping(value = {"/prepareAdd"})
    public String prepareAdd(@RequestParam(name = "typeid")String id,Map model)
    {
        model.put("typeid",id);
        return "/syncLearningPlan/addLearningPlan";
    }


    @RequestMapping(value = {"/add"})
    public String prepareAddSchoolTest(@RequestParam(name = "typeid")String id,Map model)
    {
        model.put("typeid",id);
        return "/syncLearningPlan/addSyncTest";
    }

    //本来是提交后直接返回测试结果，因为要实现target="navTab",必须使用<a>,但总是提示无法提交、
    //目前是先点击提交答案按钮提交表单，再点击查看结果进行结果查询。
    //如果两个学生同时在两台电脑上操作，可能会有问题、
    //需要设定一个时间戳，并把这个值传回页面进行判断，暂时没做处理
    @RequestMapping(value = {"/submitAnswer"})
    public String submitAnswer(HttpServletRequest request,Map model,SyncTestFromTKDO syncTestFromTKDO1)
    {
        //LeftMenuDO leftMenuDO = leftMenuService.findOne(id);
        //String typeName = leftMenuDO.getTitle();
        //AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,"答案提交成功", "同步测试");
        //listSize--题目的数量，如果没取到该值，就默认为20
        String length = request.getParameter("listSize");
        Integer typeidInt = Integer.parseInt(request.getParameter("typeid"));

        List<SyncTestFromTKDO> syncTestFromTKDOList = syncTestFromTKService.findAllList(typeidInt,0,syncTestFromTKDO1);
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
        syncTestFromTKService.updateStuAns(typeidInt);
        try{
            /*for (int i = 0; i < judgeLen; i++) {
                temp = request.getParameter("answer"+i);
            }*/
            for(int i=0;i<len;i++)
            {
                //把答案存入一个数组
                temp = request.getParameter("answer"+i);
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
                    SyncTestFromTKDO syncTestFromTKDO = syncTestFromTKService.findOneByQuestionId(qId);
                    syncTestFromTKDO.setStuAns(stuAns);
                    syncTestFromTKService.save(syncTestFromTKDO);
                }

                //其他题
                if(!Lang.isEmpty(temp1) && !Lang.isEmpty(id))
                {
                    //answerArr.add(temp);
                    //studentAnswerService.save1(temp1,id);
                    qId = Integer.parseInt(id);
                    SyncTestFromTKDO syncTestFromTKDO = syncTestFromTKService.findOneByQuestionId(qId);
                    syncTestFromTKDO.setStuAns(temp1);
                    syncTestFromTKService.save(syncTestFromTKDO);
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

        List<SyncTestFromTKDO> syncTestFromTKDOS = syncTestFromTKService.findAllList(typeidInt,0,syncTestFromTKDO1);
        model.put("syncTestFromTKDOS",syncTestFromTKDOS);
        return "/syncLearningPlan/doTestQuestionResult";

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
    public String getStudentAns(Map model,HttpServletRequest request,SyncTestFromTKDO syncTestFromTKDO)
    {
        String typeid = request.getParameter("typeid");
        Integer typeidInt = 0;
        if (!Lang.isEmpty(typeid))
        {
            typeidInt = Integer.parseInt(typeid);
        }
        List<SyncTestFromTKDO> syncTestFromTKDOS = syncTestFromTKService.findAllList(typeidInt,0,syncTestFromTKDO);
        model.put("syncTestFromTKDOS",syncTestFromTKDOS);
        return "/syncLearningPlan/doTestQuestionResult";
    }

    @RequestMapping(value = {"/prepareUpdate"})
    public String prepareUpdate(SyncTestDO syncTestDO, Map model) {
        model.put("syncTestDO", syncTestDO);
        return "/syncLearningPlan/update";
    }

    //学生做测试
    @RequestMapping(value = {"/doTest"})
    public String doTest(@RequestParam(name = "syncTestId")Integer id,Map model)
    {

        SyncTestDO syncTestDO = syncTestService.findOne(id);
        //Integer lpId = syncTestDO.getTypeid();
        //CourseWareDO courseWareDO = courseWareService.findOne(lpId);
        //String lpName = courseWareDO.getName();
        //String pdfUrl = courseWareDO.getUrl();
        //model.put("lpName",lpName);
        model.put("syncTestDO",syncTestDO);
        /*model.put("lpId",lpId);
        model.put("pdfUrl",pdfUrl);
        logger.info("pdfUrl"+pdfUrl);*/
        return "/syncLearningPlan/doTest";
    }


    //添加试卷类的测试
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(@RequestParam(name = "file1") MultipartFile file1,@RequestParam(name = "file2") MultipartFile file2,
                               @RequestParam(name = "typeid")Integer id, @RequestParam(name = "xzt_num")Integer xztNum,HttpServletRequest request)

    {
        //这里的navTabId要改
        LeftMenuDO leftMenuDO = leftMenuService.findOne(id);
        String tabName = leftMenuDO.getTitle();
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", tabName, AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);

        try {
            SyncTestDO syncTestDO = new SyncTestDO();
            //上传题目
            if (file1 != null) {
                String oldname1 = file1.getOriginalFilename();
                syncTestDO.setName(oldname1);
                String suffixName = file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf("."));

                //String newFileName = "C:\\file\\" + UUID.randomUUID().toString() + suffixName;
                String newFileName1 = Constants.FILE_ROAD_KEY + oldname1;
                //System.out.print("0000000" + newFileName);
                FileCopyUtils.copy(file1.getBytes(), new File(newFileName1));
                String url1 = Constants.NGINX_ROAD_KEY+ oldname1;
                syncTestDO.setUrl(url1);

            }
            syncTestDO.setTypeid(id);
            syncTestDO.setXztNum(xztNum);
            //上传答案
            if (file2.getSize()>0 ||file2 != null  ) {
                String oldname2 = file2.getOriginalFilename();
                syncTestDO.setAnswerName(oldname2);
                //String suffixName = file2.getOriginalFilename().substring(file2.getOriginalFilename().lastIndexOf("."));

                //String newFileName = "C:\\file\\" + UUID.randomUUID().toString() + suffixName;
                String newFileName2 =Constants.FILE_ROAD_KEY + oldname2;
                //System.out.print("0000000" + newFileName);
                FileCopyUtils.copy(file2.getBytes(), new File(newFileName2));
                String url2 = Constants.NGINX_ROAD_KEY + oldname2;
                syncTestDO.setAnswerUrl(url2);
            }

            syncTestService.save(syncTestDO);
            /*Thread thread = new Thread()
            {
                public void run(){
                    try {
                        SyncTestDO syncTestDO = new SyncTestDO();
                        //上传题目
                        if (file1 != null) {
                            String oldname1 = file1.getOriginalFilename();
                            syncTestDO.setName(oldname1);
                            String suffixName = file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf("."));

                            //String newFileName = "C:\\file\\" + UUID.randomUUID().toString() + suffixName;
                            String newFileName1 = "D:\\work\\courseWare\\" + oldname1;
                            //System.out.print("0000000" + newFileName);
                            FileCopyUtils.copy(file1.getBytes(), new File(newFileName1));
                            String url1 = "http://localhost/courseWare/" + oldname1;
                            syncTestDO.setUrl(url1);

                        }
                        syncTestDO.setTypeid(id);
                        syncTestDO.setXztNum(xztNum);
                        //上传答案
                        if (file2 != null) {
                            String oldname2 = file2.getOriginalFilename();
                            syncTestDO.setName(oldname2);
                            String suffixName = file2.getOriginalFilename().substring(file2.getOriginalFilename().lastIndexOf("."));

                            //String newFileName = "C:\\file\\" + UUID.randomUUID().toString() + suffixName;
                            String newFileName2 = "D:\\work\\courseWare\\" + oldname2;
                            //System.out.print("0000000" + newFileName);
                            FileCopyUtils.copy(file2.getBytes(), new File(newFileName2));
                            String url2 = "http://localhost/courseWare/" + oldname2;
                            syncTestDO.setAnswerUrl(url2);
                        }

                        syncTestService.save(syncTestDO);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();;
                    }
                }
            };
            thread.start();
            //保证上面的线程先执行完
            thread.join();*/
            //找到最新插入的试卷的id
            SyncTestDO syncTestDO1 = syncTestService.findLatest();

            Integer paperId = syncTestDO1.getId();

            //把选择题答案存进paper_test_question表
            for (int i=1;i<=xztNum;i++)
            {
                String ans = request.getParameter("xzt_num"+i);
                PaperTestQuestionDO paperTestQuestionDO = new PaperTestQuestionDO();
                paperTestQuestionDO.setPaperid(paperId);
                paperTestQuestionDO.setQuestionid(i);
                paperTestQuestionDO.setAnswer(ans);
                paperTestService.save(paperTestQuestionDO);
            }

        } catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo delete(SyncTestDO syncTestDO) {
        Integer typeId = syncTestDO.getTypeid();
        LeftMenuDO leftMenuDO = leftMenuService.findOne(typeId);
        String typeName = leftMenuDO.getTitle();
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "已经作废", typeName);
        try {
            logger.info("0000000000000");
            syncTestDO.setDeleteFlag(true);
            syncTestService.save(syncTestDO);
        } catch (Exception e) {
            logger.error("删除课件失败:{}", e.getMessage());
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
        }
        return ajaxResponseVo;
    }
}
