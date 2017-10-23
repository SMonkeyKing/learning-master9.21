package com.zju.app.business.tbxuean.web;

import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.app.business.stjj.service.QuestionService;
import com.zju.app.business.stjj.web.QuestionController;
import com.zju.app.business.tbxuean.service.SyncTestFromTKService;
import com.zju.model.LeftMenuDO;
import com.zju.model.QuestionDO;
import com.zju.model.SyncTestFromTKDO;
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
import java.util.List;
import java.util.Map;

/**
 * Created by lujie on 2017/9/25.
 */
@Controller
@RequestMapping("/syncTestFromTK")
public class SyncTestFromTKController {

    private static Logger logger = LoggerFactory.getLogger(SyncTestFromTKController.class);

    @Autowired
    SyncTestFromTKService syncTestFromTKService;

    @Autowired
    QuestionService questionService;

    @Autowired
    LeftMenuService leftMenuService;

    @ModelAttribute
    public void model(@RequestParam(name = "id", required = false) Integer id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            SyncTestFromTKDO syncTestFromTKDO = syncTestFromTKService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("syncTestFromTKDO", syncTestFromTKDO);
        }
    }
    //通过点击左侧菜单进入添加同步测试题列表
    //pId 学案的id
    @RequestMapping(value = {"/config"})
    public String addSyncTestFromTK(@RequestParam(name = "pid") Integer id, HttpServletRequest request,
                                    QuestionDO questionDO, DwzPageVo page, Map model)
    {
        Integer typeid = Integer.parseInt(request.getParameter("typeid"));
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
        model.put("learningPlanId",id);
        return "syncLearningPlan/addSyncTestFromTK";
    }

    //从题库中选择题目加入同步测试中
    @RequestMapping(value = "/addQuestion")
    @ResponseBody
    public Integer addQuestion(@RequestParam(name = "questionId")Integer questionId,
                               @RequestParam(name = "learningPlanId")Integer learingPlanId)
    {
        try{
            QuestionDO questionDO = questionService.findOne(questionId);
            //如果已经加入同步测试，把题库中的sync_flag置为1
            questionDO.setSyncFlag(1);
            questionService.save(questionDO);
            SyncTestFromTKDO syncTestFromTKDO = new SyncTestFromTKDO();
            syncTestFromTKDO.setQuestionContent(questionDO.getContent());
            Integer questionType = questionDO.getType();
            syncTestFromTKDO.setQuestionType(questionType);
            //questionType = 1表示是选择题
            if(questionType == 1)
            {
                syncTestFromTKDO.setQuestionAnswer(questionDO.getJudgeAnswer());
            }else
            {
                syncTestFromTKDO.setQuestionAnswer(questionDO.getAnswer());
            }

            //syncTestFromTKDO.setQuestionAnswer(questionDO.getAnswer());
            //learingPlanId为学案id，用于过滤改学案下的同步测试
            syncTestFromTKDO.setLearningPlanId(learingPlanId);
            //syncTestFromTKDO.setTypeId(learingPlanId);
            
            syncTestFromTKDO.setQuestionId(questionDO.getId());
            syncTestFromTKService.save(syncTestFromTKDO);
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
                                        SyncTestFromTKDO syncTestFromTKDO, Map model)
    {
        //这里的typeid为学案id
        String type = request.getParameter("questionType");
        Integer roleId = (Integer) request.getSession().getAttribute("role");
        Integer typeInt = 0;
        if(!Lang.isEmpty(type))
        {
            typeInt = Integer.parseInt(type);
        }
        //根据typeid获取同步测试列表
        List<SyncTestFromTKDO> syncTestFromTKList = syncTestFromTKService.findAllList(typeid,typeInt,syncTestFromTKDO);
        model.put("syncTestFromTKDO", syncTestFromTKDO);
        model.put("syncTestFromTKDOs", syncTestFromTKList);
        model.put("type", typeInt);
        model.put("typeid", typeid);
        model.put("role",roleId);
        model.put("listSize",syncTestFromTKList.size());
        return "syncLearningPlan/doTestQuestion";
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
    public AjaxResponseVo delete(SyncTestFromTKDO syncTestFromTKDO) {
        //Integer typeId = syncTestFromTKDO.getTypeId();
        //LeftMenuDO leftMenuDO = leftMenuService.findOne(typeId);
        //String typeName = leftMenuDO.getTitle();
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "已经作废", "开始测试");
        try {
            //假删除
            /*syncTestFromTKDO.setDeleteFlag(true);
            syncTestFromTKService.save(syncTestFromTKDO)*/;
            //直接删除
            syncTestFromTKService.deleteDO(syncTestFromTKDO);
            //删除后要去题库表中把version改为1，这样可以再次加入同步测试
            QuestionDO questionDO = questionService.findOne(syncTestFromTKDO.getQuestionId());
            logger.info(questionDO.toString());
            questionDO.setVersion(1);
            questionService.save(questionDO);
            QuestionDO questionDO1 = questionService.findOne(syncTestFromTKDO.getQuestionId());
            logger.info("version"+questionDO1.getVersion());
        } catch (Exception e) {
            logger.error("删除题目失败:{}", e.getMessage());
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
        }
        return ajaxResponseVo;
    }

}
