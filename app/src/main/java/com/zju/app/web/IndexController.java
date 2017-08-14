package com.zju.app.web;

import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.model.LeftMenuDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        List<LeftMenuDO> leftmenu = leftMenuService.findAll();
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
        return mv;
    }




}
