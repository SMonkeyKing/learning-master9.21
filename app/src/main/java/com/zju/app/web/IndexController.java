package com.zju.app.web;

import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.model.LeftMenuDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    @RequestMapping(value = {"", "index.htm", "index.html"}, method = RequestMethod.GET)
    public String mainIndex()
    {
        return "main";
    }

    @RequestMapping(value = {"/index"})
    public ModelAndView index(@RequestParam(name="role")Integer roleId, HttpServletRequest request) throws UnknownHostException {
        ModelAndView mv = new ModelAndView("index");
        List<LeftMenuDO> leftmenu = leftMenuService.findAll(roleId);

        //把role存在session中
        request.getSession().setAttribute("role",roleId);
        /*for (LeftMenuDO menu:leftmenu
                ) {
            logger.info(menu.getTitle());
            List<LeftMenuDO> submenu = menu.getSubmenus();
            logger.info("sub1.size()"+submenu.size());
            for (LeftMenuDO sub:submenu
                 ) {
                logger.info(sub.getTitle());
                List<LeftMenuDO> submenu1 = sub.getSubmenus();
                logger.info("sub2.size()"+submenu1.size());
                for (LeftMenuDO sub1:submenu1
                     ) {
                    logger.info(sub1.getTitle());
                }
            }

        }*/
        mv.addObject("leftmenu",leftmenu);
        mv.addObject("role",roleId);
        //String loginIp = request.getRemoteAddr();
        //String ip = request.getRemoteHost();

        //mv.addObject("ip",loginIp);
        String ip1 = InetAddress.getLocalHost().getHostAddress().toString();
        mv.addObject("ip",ip1);
        return mv;
    }

    //教师专区需要登录
    //学生专区不用登录
    @RequestMapping(value = {"/loginIndex"})
    public String login()
    {
        return "loginIndex";
    }


}
