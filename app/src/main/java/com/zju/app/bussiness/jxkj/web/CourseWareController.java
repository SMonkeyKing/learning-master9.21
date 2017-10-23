package com.zju.app.bussiness.jxkj.web;

import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.app.bussiness.jxkj.service.CourseWareService;
import com.zju.app.constant.Constants;
import com.zju.model.CourseWareDO;
import com.zju.model.LeftMenuDO;
import com.zju.utils.Lang;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.dwz.DwzPageVo;
import com.zju.utils.log.Log;
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

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    LeftMenuService leftMenuService;

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
        return "/courseWare/list";
    }


    @RequestMapping(value = {"/prepareAdd"})
    public String prepareAdd(@RequestParam(name = "typeid")String id,Map model)
    {
        model.put("typeid",id);
        return "/courseWare/add";
    }

    @RequestMapping(value = {"/prepareUpdate"})
    public String prepareUpdate(CourseWareDO courseWare, Map model) {
        model.put("courseWare", courseWare);
        return "/courseWare/update";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(@RequestParam(name = "file") MultipartFile[] files,
                               @RequestParam(name = "uploadName")String uploadName,@RequestParam(name="typeid")Integer id) {
        LeftMenuDO leftMenuDO = leftMenuService.findOne(id);
        String typeName = leftMenuDO.getTitle();
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", typeName, AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);


        try {
            List<CourseWareDO> courseWareDOList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file != null) {
                    CourseWareDO courseWare = new CourseWareDO();
                    String oldname = file.getOriginalFilename();
                    courseWare.setName(oldname);
                    String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

                    //String newFileName = "C:\\file\\" + UUID.randomUUID().toString() + suffixName;
                    String newFileName = Constants.FILE_ROAD_KEY + oldname;
                    FileCopyUtils.copy(file.getBytes(), new File(newFileName));
                    String url = Constants.NGINX_ROAD_KEY+oldname;
                    courseWare.setUrl(url);
                    courseWare.setTypeid(id);
                    courseWare.setUploadName(uploadName);
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
        LeftMenuDO leftMenuDO = leftMenuService.findOne(courseWare.getTypeid());
        String typeName = leftMenuDO.getTitle();
        logger.info("typeName:"+typeName);
        //logger.info("1233333333333333");
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", typeName, AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
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
    }

}
