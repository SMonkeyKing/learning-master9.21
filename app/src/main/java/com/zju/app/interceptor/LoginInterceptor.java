package com.zju.app.interceptor;

import com.zju.app.constant.Constants;
import com.zju.utils.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by xujingfeng on 2017/10/4.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    LoginConfigVO loginConfigVO;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        if(Lang.isEmpty(session.getAttribute(Constants.ROLE_KEY))){
            httpServletResponse.sendRedirect(loginConfigVO.getLoginInEntry());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
