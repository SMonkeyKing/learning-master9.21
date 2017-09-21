package com.zju.app.business.stjj.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.app.business.stjj.service.QuestionService;
import com.zju.model.LeftMenuDO;
import com.zju.model.QuestionDO;
import com.zju.utils.IDUtils;
import com.zju.utils.Lang;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.dwz.DwzPageVo;
import org.apache.poi.ss.formula.functions.Na;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    LeftMenuService leftMenuService;

    @ModelAttribute
    public void model(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            QuestionDO question = questionService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("questionDO", question);
        }
    }

    //type-题型
    //typeid-左侧菜单的id，用于列表显示的判断
    @RequestMapping(value = {"/config"})
    public String alllist(@RequestParam(name = "typeid") Integer typeid,HttpServletRequest request,
                          QuestionDO questionDO, DwzPageVo page, Map model) {
        String type = request.getParameter("type");
        logger.info("type:"+type);
        Integer typeInt = 0;
        if(!Lang.isEmpty(type))
        {
            typeInt = Integer.parseInt(type);
            logger.info("typeInfo:"+typeInt);
        }
        Page<QuestionDO> pageLists = questionService.findAll(typeid,typeInt,questionDO, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("questionDO", questionDO);
        model.put("page", page);
        model.put("questionDOs", pageLists.getContent());
        logger.info(page.getTotalCount()+"");
        model.put("type", typeInt);
        model.put("typeid", typeid);
        return "/question/list";
    }

    /*@RequestMapping(value = {"/config1"})
    public String list(@RequestParam(name = "type")Integer type, QuestionDO questionDO, DwzPageVo page, Map model) {
        logger.info("type: "+type);
        //Integer typeidInt = Integer.parseInt(typeid);
        //Integer typeInt = Integer.parseInt(type);
        Page<QuestionDO> pageLists = questionService.findAll(type,questionDO, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("questionDO", questionDO);
        model.put("page", page);
        model.put("questionDOs", pageLists.getContent());
        logger.info(page.getTotalCount()+"");
        model.put("type", type);

        return "/question/list1";
    }*/
    /*@RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(HttpServletRequest request)
    {
        String content = request.getParameter("content");
        logger.info(content);
        String type = request.getParameter("type");
        String answer = request.getParameter("answer");
        String judgeAnswer = request.getParameter("judgeAnswer");
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功", "题目录入", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try{
            QuestionDO questionDO = new QuestionDO();
            questionDO.setType(Integer.parseInt(type));
            questionDO.setContent(content);
            questionDO.setAnswer(answer);
            questionDO.setJudgeAnswer(judgeAnswer);
            questionService.save(questionDO);
        }catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }
*/

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo save(HttpServletRequest request)
    {
        String content = request.getParameter("question");
        //logger.info(content);
        String typeid =  request.getParameter("typeid");
        Integer typeidInt = Integer.parseInt(typeid);
        String type = request.getParameter("type");
        Integer typeInt = Integer.parseInt(type);
        String answer = request.getParameter("answer");
        String judgeAnswer = request.getParameter("xztAnswer");
        logger.info(content);
        logger.info(answer);
        //AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功", "精选试题库", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        LeftMenuDO leftMenuDO = leftMenuService.findOne(typeidInt);
        String typeName = leftMenuDO.getTitle();
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", typeName, AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try{
            QuestionDO questionDO = new QuestionDO();
            //type--题型
            questionDO.setType(typeInt);
            //typeid--题目分类（属于哪一个专题的题目），左侧菜单的id
            questionDO.setTypeid(typeidInt);
            questionDO.setContent(content);
            questionDO.setAnswer(answer);
            questionDO.setJudgeAnswer(judgeAnswer);
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
    public String prepareAdd(@RequestParam(name = "typeid")Integer id,Map model)
    {
        model.put("typeid",id);
        return "/question/add";
    }

    @RequestMapping(value = {"/prepareUpdate"})
    public String prepareUpdate(QuestionDO questionDO, Map model) {
        model.put("questionDO", questionDO);
        return "/question/update";
    }
    @RequestMapping(value = "uploadImg",method = RequestMethod.POST)
    @ResponseBody
    public JSON upload(@RequestParam(name = "myimgfile")MultipartFile multipartFile,HttpServletRequest request) throws IOException {
        String oldName=multipartFile.getOriginalFilename();
        System.out.println("oldname"+oldName);
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        String newName = IDUtils.genImageName()+suffix;
        String path = request.getContextPath();
        //String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String basepath = "http://localhost:80/file/";
        System.out.print("path"+basepath);
        //String realPath = request.getServletContext().getRealPath("/");
        String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();

        String path1 = "C:/Users/lujie/Desktop/learning-master/learning-master/imgfile/";
        //String path2 = "C:\\Users\\lujie\\Desktop\\learning-master\\learning-master\\imgfile\\";
        String path3 = "D:\\work\\file\\";
        System.out.print("realPath"+realPath);
        File file = new File(path3+newName);
        file.createNewFile();
        if (!file.exists())
        {
            file.mkdirs();
        }
        multipartFile.transferTo(file);
        JSONObject result = new JSONObject();
        result.put("url",basepath+newName);
        return result;
    }

    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo update(QuestionDO questionDO) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功",
                "选择题", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try {
            questionService.save(questionDO);
        } catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }
    /*@RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponseVo update(HttpServletRequest request) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS, "操作成功",
                "选择题", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try {
            String id = request.getParameter("id");
            logger.info("id"+id);
            String content = request.getParameter("content");
            logger.info(content);
            String type = request.getParameter("type");
            String answer = request.getParameter("answer");
            logger.info(answer);
            String judgeAnswer = request.getParameter("judgeAnswer");
            QuestionDO questionDO = questionService.findOne(Integer.parseInt(id));
            questionDO.setType(Integer.parseInt(type));
            questionDO.setJudgeAnswer(judgeAnswer);
            questionDO.setContent(content);
            questionDO.setAnswer(answer);
            questionService.save(questionDO);
        } catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }*/

}
