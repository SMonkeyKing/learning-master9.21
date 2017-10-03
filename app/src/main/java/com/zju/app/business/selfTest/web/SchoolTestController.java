package com.zju.app.business.selfTest.web;

import com.zju.app.business.stjj.service.LeftMenuService;
import com.zju.app.business.tbxuean.service.SyncTestService;
import com.zju.app.bussiness.jxkj.service.CourseWareService;
import com.zju.app.bussiness.jxkj.web.CourseWareController;
import com.zju.model.CourseWareDO;
import com.zju.model.SyncTestDO;
import com.zju.utils.dwz.DwzPageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lujie on 2017/10/3.
 */
@Controller
@RequestMapping("/schoolTest")
public class SchoolTestController {
    private static Logger logger = LoggerFactory.getLogger(SchoolTestController.class);

    @Autowired
    CourseWareService courseWareService;

    @Autowired
    LeftMenuService leftMenuService;

    @Autowired
    SyncTestService syncTestService;

    @RequestMapping(value = {"/config"})
    public String list(@RequestParam(name = "typeid")Integer id, SyncTestDO syncTestDO, DwzPageVo page, Map model, HttpServletRequest request) {
        /*Page<CourseWareDO> pageLists = courseWareService.findAll(id,courseWare, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("courseWare", courseWare);
        model.put("page", page);
        model.put("courseWares", pageLists.getContent());*/
        //把菜单的id传到前端
        model.put("typeid",id);
        Integer roleId = (Integer) request.getSession().getAttribute("role");
        model.put("role",roleId);

        Page<SyncTestDO> pageLists = syncTestService.findAll(id,syncTestDO, page.getPageable());
        page.setTotalCount(pageLists.getTotalElements());
        model.put("courseWare", syncTestDO);
        model.put("page", page);
        model.put("courseWares", pageLists.getContent());
        return "/selfTest/schoolTestList";
    }

    @RequestMapping(value = {"/add"})
    public String testAdd(@RequestParam(name = "typeid")String id,Map model)
    {
        model.put("typeid",id);
        return "/syncLearningPlan/addSyncTest";
    }

}
