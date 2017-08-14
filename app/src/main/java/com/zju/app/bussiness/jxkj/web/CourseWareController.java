package com.zju.app.bussiness.jxkj.web;

import com.zju.app.bussiness.jxkj.service.CourseWareService;
import com.zju.model.CourseWareDO;
import com.zju.utils.Lang;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.dwz.DwzPageVo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * Created by xujingfeng on 2017/8/11.
 * <p>
 * 教学课件
 */
@Controller
@RequestMapping("/courseWare")
public class CourseWareController {

    private static Logger logger = LoggerFactory.getLogger(CourseWareController.class);

    @Autowired
    CourseWareService courseWareService;

    @ModelAttribute
    public void model(@RequestParam(value = "id", required = false) String id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            CourseWareDO courseWare = courseWareService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("courseWareDO", courseWare);
        }
    }

    @RequestMapping(value = {"/config"})
    public String list(CourseWareDO courseWare, DwzPageVo page, Map model) {
        Page<CourseWareDO> pageLists = courseWareService.findAll(courseWare, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("courseWare", courseWare);
        model.put("page", page);
        model.put("courseWares", pageLists.getContent());
        return "/courseWare/list";
    }

    @RequestMapping(value = {"/prepareAdd"})
    public String prepareAdd() {
        return "/courseWare/add";
    }

    @RequestMapping(value = {"/prepareUpdate"})
    public String prepareUpdate(CourseWareDO courseWare, Map model) {
        model.put("courseWare", courseWare);
        return "/courseWare/update";
    }


    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(@RequestParam(name = "file") MultipartFile[] files) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功", "课件列表", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);

        try {
            List<CourseWareDO> courseWareDOList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file != null) {
                    CourseWareDO courseWare = new CourseWareDO();
                    courseWare.setName(file.getOriginalFilename());
                    String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

                    String newFileName = "C:\\file\\" + UUID.randomUUID().toString() + suffixName;
                    System.out.print("0000000"+newFileName);
                    FileCopyUtils.copy(file.getBytes(), new File(newFileName));
                    courseWare.setUrl(newFileName);
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
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功", "课件列表", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
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
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "已经作废", "课件列表");
        try {
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
