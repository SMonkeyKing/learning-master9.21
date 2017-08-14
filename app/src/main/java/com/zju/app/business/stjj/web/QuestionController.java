package com.zju.app.business.stjj.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zju.app.business.stjj.service.QuestionService;
import com.zju.model.QuestionDO;
import com.zju.utils.IDUtils;
import com.zju.utils.Lang;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.lang.Https;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lujie on 2017/8/13.
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

    private static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @ModelAttribute
    public void model(@RequestParam(value = "id", required = false) String id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            QuestionDO question = questionService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("questionDO", question);
        }
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(HttpServletRequest request)
    {
        String content = request.getParameter("content");
        String type = request.getParameter("type");

        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功", "题目列表", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try{
            QuestionDO questionDO = new QuestionDO();
            questionDO.setType(Integer.parseInt(type));
            questionDO.setContent(content);
            questionService.save(questionDO);
        }catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }


    @RequestMapping(value = {"/prepareAdd"})
    public String prepareAdd() {
        return "/question/add";
    }


    @RequestMapping(value = "uploadImg",method = RequestMethod.POST)
    @ResponseBody
    public JSON upload(@RequestParam(name = "myimgfile")MultipartFile multipartFile,HttpServletRequest request) throws IOException {
        String oldName=multipartFile.getOriginalFilename();
        System.out.println("oldname"+oldName);
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        String newName = IDUtils.genImageName()+suffix;
        String path = request.getContextPath();
        String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        System.out.print("path"+basepath);
        //String realPath = request.getServletContext().getRealPath("/");
        String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String path1 = "C:/Users/lujie/Desktop/learning-master/learning-master/imgfile/";
        String path2 = "C:\\Users\\lujie\\Desktop\\learning-master\\learning-master\\imgfile\\";
        System.out.print("realPath"+realPath);
        File file = new File(path2+newName);
        file.createNewFile();
        if (!file.exists())
        {
            file.mkdirs();
        }
        multipartFile.transferTo(file);
        JSONObject result = new JSONObject();
        result.put("url",basepath+"imgfile/"+newName);
        return result;
    }

}
