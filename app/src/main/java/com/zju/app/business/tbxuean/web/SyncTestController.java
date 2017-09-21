package com.zju.app.business.tbxuean.web;

import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.app.business.stjj.service.QuestionService;
import com.zju.app.business.tbxuean.service.StudentAnswerService;
import com.zju.app.business.tbxuean.service.SyncTestService;
import com.zju.app.bussiness.jxkj.service.CourseWareService;
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
        Page<SyncTestDO> pageLists = syncTestService.findAll(lpList,syncTestDO, page.getPageable());
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
    /*@RequestMapping(value = {"/prepareAdd"})
    public String prepareAdd(@RequestParam(name = "typeid")String id,Map model)
    {
        model.put("typeid",id);
        return "/syncLearningPlan/addLearningPlan";
    }*/

    //本来是提交后直接返回测试结果，因为要实现target="navTab",必须使用<a>,但总是提示无法提交、
    //目前是先点击提交答案按钮提交表单，再点击查看结果进行结果查询。
    //如果两个学生同时在两台电脑上操作，可能会有问题、
    //需要设定一个时间戳，并把这个值传回页面进行判断，暂时没做处理
    @RequestMapping(value = {"/submitAnswer"})
    @ResponseBody
    public AjaxResponseVo submitAnswer(HttpServletRequest request,Map model)
    {
        //LeftMenuDO leftMenuDO = leftMenuService.findOne(id);
        //String typeName = leftMenuDO.getTitle();
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", "", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);


        //listSize--题目的数量，如果没取到该值，就默认为20
        String length = request.getParameter("listSize");
        Integer len = 20;
        if(!Lang.isEmpty(length))
        {
            len = Integer.parseInt(length);
        }
        //用于解析选择题的答案
        String temp="";
        //解析填空题答案
        String temp1 = "";
        String id = "";
        List<TbxaQuestionStudentAnsDO> tbxaQuestionStudentAnsDOs = new ArrayList<>();
        try{
            for(int i=1;i<=len;i++)
            {
                temp = request.getParameter("result"+i);
                logger.info("temp"+temp);
                temp1 = request.getParameter("answer"+i);
                logger.info("temp1"+temp1);
                id = request.getParameter("id"+i);
                logger.info("id"+temp1);
                if (!Lang.isEmpty(temp))
                {
                    //tbxaQuestionStudentAnsDOs.add(studentAnswerService.saveStudentQuestionAns(temp));
                    studentAnswerService.save(temp);
                }
                if(!Lang.isEmpty(temp1) && !Lang.isEmpty(id))
                {
                    studentAnswerService.save1(temp1,id);
                }
            }
        }catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;

        //model.put("tbxaQuestionStudentAnsDOs",tbxaQuestionStudentAnsDOs);
        //return "/syncLearningPlan/doTestQuestionResult";
    }

    @RequestMapping(value = {"/backResult"})
    public String getStudentAns(Map model,TbxaQuestionStudentAnsDO tbxaQuestionStudentAnsDO)
    {
        List<TbxaQuestionStudentAnsDO> tbxaQuestionStudentAnsDOList = studentAnswerService.findStudentAns(tbxaQuestionStudentAnsDO);
        model.put("tbxaQuestionStudentAnsDOs",tbxaQuestionStudentAnsDOList);
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
        Integer lpId = syncTestDO.getLpId();
        CourseWareDO courseWareDO = courseWareService.findOne(lpId);
        String lpName = courseWareDO.getName();
        String pdfUrl = syncTestDO.getUrl();
        model.put("lpName",lpName);
        model.put("syncTestDO",syncTestDO);
        /*model.put("lpId",lpId);
        model.put("pdfUrl",pdfUrl);
        logger.info("pdfUrl"+pdfUrl);*/
        return "/syncLearningPlan/doTest";
    }


    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(@RequestParam(name = "file1") MultipartFile file1,@RequestParam(name = "file2") MultipartFile file2,
                               @RequestParam(name = "xzt_answer")String answer,@RequestParam(name = "learningPlanId")Integer id) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", "同步测试", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);

        try {
            SyncTestDO syncTestDO = new SyncTestDO();
            //上传题目
            if(file1!=null) {
                String oldname1 = file1.getOriginalFilename();
                syncTestDO.setName(oldname1);
                String suffixName = file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf("."));

                //String newFileName = "C:\\file\\" + UUID.randomUUID().toString() + suffixName;
                String newFileName1 = "D:\\work\\courseWare\\" + oldname1;
                //System.out.print("0000000" + newFileName);
                FileCopyUtils.copy(file1.getBytes(), new File(newFileName1));
                String url1 = "http://localhost/courseWare/" + oldname1;
                syncTestDO.setUrl(url1);
                syncTestDO.setLpId(id);
                syncTestDO.setXztAnswer(answer);
            }
            //上传答案
            if(file2!=null) {
                String oldname2 = file1.getOriginalFilename();
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
        } catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }

    /*@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo delete(SyncTestDO syncTestDO) {
        Integer typeId = courseWare.getTypeid();
        LeftMenuDO leftMenuDO = leftMenuService.findOne(typeId);
        String typeName = leftMenuDO.getTitle();
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "已经作废", typeName);
        try {
            logger.info("0000000000000"+courseWare.getName()+courseWare.getUrl());
            courseWare.setDeleteFlag(true);
            courseWareService.save(courseWare);
        } catch (Exception e) {
            logger.error("删除课件失败:{}", e.getMessage());
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
        }
        return ajaxResponseVo;
    }*/
}
