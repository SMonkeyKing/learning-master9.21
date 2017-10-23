package com.zju.app.web;

import com.zju.model.AcademicJournalDO;
import com.zju.utils.dwz.AjaxResponseVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xujingfeng on 2017/10/4.
 */
@Controller
public class GlobalErrorHandler {

    @RequestMapping(value = "/error/ajax/401")
    @ResponseBody
    public AjaxResponseVo ajaxOvertime()
    {
        AjaxResponseVo ajaxResponseVo = new AjaxResponseVo();
        //ajaxResponseVo.setCallbackType(AjaxResponseVo.CALLBACK_TYPE_FORWARD);
        ajaxResponseVo.setMessage("登录超时,请重新登录！");
        ajaxResponseVo.setStatusCode(AjaxResponseVo.STATUS_CODE_ERROR);

        ajaxResponseVo.setCallbackType(AjaxResponseVo.CALLBACK_TYPE_CLOSE_CURRENT);
        ajaxResponseVo.setForwardUrl("http://localhost:8080/learning");
        return ajaxResponseVo;

    }

}
