package com.zju.app.business.stjj.web;

import com.zju.app.business.stjj.service.PaperService;
import com.zju.app.business.stjj.service.QuestionService;
import com.zju.model.PaperDO;
import com.zju.model.QuestionDO;
import com.zju.utils.CookieUtils;
import com.zju.utils.dwz.AjaxResponseVo;
import com.zju.utils.lang.Https;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by lujie on 2017/8/17.
 */
@Controller
@RequestMapping("/paper")
public class PaperController {

    private static Logger logger = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    PaperService paperService;

    @Autowired
    QuestionService questionService;

    /*@ModelAttribute
    public void createCookie(HttpServletResponse response,HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();
        Boolean flag = false;
        for (Cookie cookie:cookies
             ) {
            if(cookie.getName().equals("questionId"))
            {
                flag = true;
                break;
            }
        }
        logger.info(flag.toString());
        if(!flag) {
            Cookie cookie = new Cookie("questionId", null);
            cookie.setPath("/");
            cookie.setMaxAge(30*60);
            response.addCookie(cookie);
            logger.info("0000000" + cookie.getName());
        }
    }*/

    //当前页面全部题目加入试卷
    //Set没有重复元素
    //这个方法实现的是数据库中有效的全部题目加入试卷，不是选中当前页的
    @RequestMapping(value = {"/addAll"})
    public void addAll(@RequestParam(name = "typeid")Integer typeid, QuestionDO questionDO, HttpServletRequest request, HttpServletResponse response) {
        List<QuestionDO> questionDOList = new ArrayList<QuestionDO>();
        questionDOList = questionService.findAllList(typeid, questionDO);
        Integer size = questionDOList.size();
        String value = "";
        HashSet qIdSet = new HashSet();
        for (QuestionDO question : questionDOList
                ) {
            //value+=question.getId().toString()+" ";
            qIdSet.add(question.getId().toString());
        }
        value = StringUtils.join(qIdSet.toArray(), " ");
        CookieUtils.deleteCookie(request,response,"questionId");
        CookieUtils.setCookie(request, response, "questionId",
                value, 60480, "utf-8");
        logger.info("questionSize" + size);

        String oldValue = CookieUtils.getCookieValue(request, "questionId", true);
        //logger.info("22222222" + oldValue);
    }

    //先把加入试卷的题目存入cookie中
    @RequestMapping(value = {"/addCookies"})
    public void addCookie(@RequestParam(name = "questionId") Integer id,
                          HttpServletRequest request, HttpServletResponse response) {
        String oldValue = "";
        /*QuestionDO questionDO = questionService.findOne(id);
        Integer typeId = questionDO.getType();
        logger.info("typeId"+typeId);
        switch (typeId){
            case 1:
                Integer xzt_numInt = 0;

                String xzt_num = CookieUtils.getCookieValue(request, "xzt_num",true);
                if(StringUtils.isNotBlank(xzt_num)) {
                    xzt_numInt+=(Integer.parseInt(xzt_num));
                }else {
                    xzt_numInt++;
                }
                logger.info(xzt_numInt+"000000000000000000");
                CookieUtils.setCookie(request, response, "xzt_num",
                        xzt_numInt.toString(),60480,"utf-8");
                break;
            case 2:
                Integer tkt_numInt = 0;

                String tkt_num = CookieUtils.getCookieValue(request, "tkt_num",true);
                if(StringUtils.isNotBlank(tkt_num)) {
                    tkt_numInt+=(Integer.parseInt(tkt_num));
                }else {
                    tkt_numInt++;
                }
                logger.info(tkt_numInt+"222222222222222222222");
                CookieUtils.setCookie(request, response, "xzt_num",
                        tkt_numInt.toString(),60480,"utf-8");
                break;
        }*/
        oldValue = CookieUtils.getCookieValue(request, "questionId", true);
        logger.info("11111111" + oldValue);
        HashSet hashSet = new HashSet();
        if (StringUtils.isNotBlank(oldValue)) {
            String[] oldValueArr = oldValue.split(" ");
            for (String oValue : oldValueArr) {
                hashSet.add(oValue);
            }
        }
        hashSet.add(id.toString());
        String newValue = StringUtils.join(hashSet.toArray(), " ");
        CookieUtils.deleteCookie(request,response,"questionId");
        CookieUtils.setCookie(request, response, "questionId",
                newValue, 60480, "utf-8");
    }

    //从cookie中取出题号，生成试卷
    @RequestMapping(value = {"/save"},method = RequestMethod.POST)
    public AjaxResponseVo save(HttpServletRequest request, HttpServletResponse response) {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo(AjaxResponseVo.STATUS_CODE_SUCCESS,
                "操作成功", "生成试卷", AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        try {
            String value = CookieUtils.getCookieValue(request, "questionId", true);
            PaperDO paperDO = new PaperDO();
            paperDO.setQuestion_list(value);
            paperService.save(paperDO);
            //清除cookie
            CookieUtils.deleteCookie(request, response, "questionId");
        } catch (Exception e) {
            ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);
            ajaxResponseVo.setMessage("操作失败!");
            ajaxResponseVo.setCallbackType(null);
            logger.error(e.getMessage(), e);
        }
        return ajaxResponseVo;

    }


    @RequestMapping(value = {"/paperBasket"})
    public String paperBasket(HttpServletRequest request, Map model) {
        String value = CookieUtils.getCookieValue(request, "questionId", true);

        Integer xztNum = 0, tktNum = 0, jstNum = 0;
        if(StringUtils.isNotBlank(value))
        {
            String[] valueArr = value.split(" ");
            for (String qId : valueArr
                    ) {
                QuestionDO questionDO = questionService.findOne(Integer.parseInt(qId));
                Integer typeId = questionDO.getType();
                switch (typeId) {
                    case 1:
                        xztNum++;
                        break;
                    case 2:
                        tktNum++;
                        break;
                    case 3:
                        jstNum++;
                }
            }
        }

        model.put("xztNum", xztNum);
        model.put("tktNum", tktNum);
        model.put("jstNum", jstNum);
        return "/question/QuestionBasket";
    }
}
