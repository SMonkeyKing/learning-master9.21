package com.zju.app.business.jxhd.web;

import com.zju.app.business.jxhd.service.JxhdImgService;
import com.zju.app.business.jxhd.service.JxhdPushImgService;
import com.zju.app.business.stjj.web.QuestionController;
import com.zju.model.JxhdImgDO;
import com.zju.model.JxhdPushImgDO;
import com.zju.model.JxhdTeacherQuestionDO;
import com.zju.utils.Lang;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.dwz.DwzPageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Pageable;
import java.util.Date;
import java.util.Map;


/*
 *截图推送
 */
@Controller
@RequestMapping("/jxhd")
public class JxhdImgController {

    private static Logger logger = LoggerFactory.getLogger(JxhdImgController.class);

    @Autowired
    JxhdImgService jxhdImgService;

    @Autowired
    JxhdPushImgService jxhdPushImgService;


    @ModelAttribute
    public void model(@RequestParam(name = "id", required = false) Integer id, Map<String, Object> model) {
        if (!Lang.isEmpty(id)) {
            JxhdImgDO jxhdImgDO = jxhdImgService.findOne(id);
            //如果有id存在，首先查出数据库中的对象，避免表单中未出现的空属性覆盖掉原有的值
            model.put("jxhdImgDO", jxhdImgDO);
        }
    }

    //查看截图列表
    @RequestMapping(value = {"/img/list"})
    public String imgQuestionList(JxhdImgDO jxhdImgDO, Map model, DwzPageVo page)
    {
        page.setNumPerPage(1);
        Page<JxhdImgDO> pageLists = jxhdImgService.findAllImg(jxhdImgDO,page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());

        model.put("jxhdImgDO", jxhdImgDO);
        model.put("page", page);
        model.put("jxhdImgDOs", pageLists.getContent());
        return "/jxhd/imgQuestionList";
    }

    //保存截图
    @RequestMapping(value = {"/img/add"})
    public String addImgQuestion(Map model)
    {
        return "/jxhd/addImgQuestion";
    }

    @RequestMapping(value = {"/img/save"})
    @ResponseBody
    public AjaxResponseVo save(HttpServletRequest request)
    {
        String content = request.getParameter("question");
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", "上传截图", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try{
            JxhdImgDO jxhdImgDO = new JxhdImgDO();
            jxhdImgDO.setImage(content);
            jxhdImgService.save(jxhdImgDO);
        }catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;
    }

    @RequestMapping(value = {"/img/push"})
    @ResponseBody
    public Integer imgPush(@RequestParam(name = "id")Integer id)
    {
        try {
            if (!Lang.isEmpty(id)) {
                JxhdImgDO jxhdImgDO= jxhdImgService.findOne(id);
                if(jxhdImgDO.getVersion()==1)
                {
                    JxhdPushImgDO jxhdPushImgDO = new JxhdPushImgDO();
                    String image = jxhdImgDO.getImage();
                    jxhdPushImgDO.setQid(id);
                    jxhdPushImgDO.setImage(image);
                    jxhdPushImgDO.setDateCreated(new Date());
                    jxhdPushImgService.save(jxhdPushImgDO);
                    //加入推送表后，就把version置为2，表示已加入
                    jxhdImgDO.setVersion(2);
                    jxhdImgService.save(jxhdImgDO);
                }
            }
            return 1;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
