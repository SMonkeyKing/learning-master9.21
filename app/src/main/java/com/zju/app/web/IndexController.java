package com.zju.app.web;

import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.app.constant.Constants;
import com.zju.app.web.service.UserTService;
import com.zju.model.ConfigTDo;
import com.zju.model.LeftMenuDO;
import com.zju.model.UserDO;
import com.zju.repository.ConfigTRepos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

/*
* Created by lujie on 2017/8/14.
*/

@Controller
public class IndexController {

    /*@RequestMapping(value = {"", "index.htm", "index.html"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }*/
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    LeftMenuService leftMenuService;

    @Autowired
    ConfigTRepos configTRepos;

    @Autowired
    UserTService userTService;
    @RequestMapping(value = {"", "index.htm", "index.html"}, method = RequestMethod.GET)
    public String mainIndex() {
        List<ConfigTDo> configTDos =(List<ConfigTDo>) configTRepos.findAll();
        if(configTDos!=null)
        {
            Constants.NGINX_ROAD_KEY = "http://"+configTDos.get(0).getIp()+"/"+configTDos.get(0).getDictionary()+"/";
            Constants.FILE_ROAD_KEY = configTDos.get(0).getFullFileRoad();
        }else
        {
            try {
                Constants.NGINX_ROAD_KEY = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return "main";
    }

    @RequestMapping(value = {"/index"}, method = RequestMethod.POST)
    public ModelAndView index(@RequestParam(name = "role") Integer roleId, HttpServletRequest request) throws UnknownHostException {
        ModelAndView mv = new ModelAndView("allIndex");
        //学生登录不用判断
        if(roleId!=2)
        {
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            UserDO userDO = userTService.getUserByName(username);
            if(userDO!=null && userDO.getPassWord().equals(password))
            {
                userDO.setLastLoginTime(new Date());
                userTService.save(userDO);
                request.getSession().setAttribute("user",userDO);
                List<LeftMenuDO> leftmenu = leftMenuService.findAll(roleId);
                //把role存在session中
                request.getSession().setAttribute(Constants.ROLE_KEY, roleId);
                mv.addObject("leftmenu", leftmenu);
                mv.addObject("role", roleId);
                String ip1 = InetAddress.getLocalHost().getHostAddress().toString();
                mv.addObject("ip", ip1);
            }else{
                mv = new ModelAndView("loginIndex");
                mv.addObject("tips","用户名或者密码错误，请重新输入");
            }
        }
        return mv;
    }

    //教师专区需要登录
    //学生专区不用登录
    @RequestMapping(value = {"/loginIndex"})
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("loginIndex");
        mv.addObject("tips","");
        return mv;
    }


}
