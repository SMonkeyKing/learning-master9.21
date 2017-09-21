package com.zju.app.business.tbxuean.web;

import com.zju.app.bussiness.jxkj.service.CourseWareService;
import com.zju.model.CourseWareDO;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lujie on 2017/8/19.
 */
@Controller
@RequestMapping("/syncLearningPlan")
public class SyncLearningPlanController {

    private static Logger logger = LoggerFactory.getLogger(SyncLearningPlanController.class);

    @Autowired
    CourseWareService courseWareService;

    @ModelAttribute
    public void model(@RequestParam(name = "id", required = false) Integer id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            CourseWareDO courseWare = courseWareService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("courseWareDO", courseWare);
        }
    }

    @RequestMapping(value = {"/config"})
    public String list(@RequestParam(name = "typeid")Integer id, CourseWareDO courseWare, DwzPageVo page, Map model, HttpServletRequest request) {
        Page<CourseWareDO> pageLists = courseWareService.findAll(id,courseWare, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("courseWare", courseWare);
        model.put("page", page);
        model.put("courseWares", pageLists.getContent());
        //把菜单的id传到前端
        model.put("typeid",id);
        Integer roleId = (Integer) request.getSession().getAttribute("role");
        model.put("role",roleId);
        return "/syncLearningPlan/learningPlanList";
    }

    @RequestMapping(value = {"/prepareAdd"})
    public String prepareAdd(@RequestParam(name = "typeid")String id,Map model)
    {
        model.put("typeid",id);
        return "/syncLearningPlan/addLearningPlan";
    }

    @RequestMapping(value = {"/testAdd"})
    public String testAdd(@RequestParam(name = "learningPlanId")String id,Map model)
    {
        model.put("learningPlanId",id);
        return "/syncLearningPlan/addSyncTest";
    }

    @RequestMapping(value = {"/prepareUpdate"})
    public String prepareUpdate(CourseWareDO courseWare, Map model) {
        model.put("courseWare", courseWare);
        return "/syncLearningPlan/update";
    }


    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(@RequestParam(name = "file") MultipartFile[] files, @RequestParam(name="typeid")String id) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", "学案", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);

        try {
            List<CourseWareDO> courseWareDOList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file != null) {
                    CourseWareDO courseWare = new CourseWareDO();
                    String oldname = file.getOriginalFilename();
                    courseWare.setName(oldname);
                    String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

                    //String newFileName = "C:\\file\\" + UUID.randomUUID().toString() + suffixName;
                    String newFileName = "D:\\work\\courseWare\\" + oldname;
                    System.out.print("0000000"+newFileName);
                    FileCopyUtils.copy(file.getBytes(), new File(newFileName));
                    String url = "http://localhost/courseWare/"+oldname;
                    courseWare.setUrl(url);
                    courseWare.setTypeid(Integer.parseInt(id));
                    courseWareDOList.add(courseWare);
                }
            }
            courseWareService.save(courseWareDOList);
        } catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }

    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo update(CourseWareDO courseWare) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功", "学案", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try {
            courseWareService.save(courseWare);
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
    public AjaxResponseVo delete(CourseWareDO courseWare) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "已经作废", "课件列表");
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
    }
}
